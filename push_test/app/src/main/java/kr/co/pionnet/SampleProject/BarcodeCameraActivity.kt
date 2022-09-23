package kr.co.pionnet.SampleProject

//import com.google.android.gms.vision.CameraSource
//import com.google.android.gms.vision.Detector
//import com.google.android.gms.vision.barcode.Barcode
//import com.google.android.gms.vision.barcode.BarcodeDetector
//import com.google.firebase.ml.vision.FirebaseVision
//import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
//import com.google.firebase.ml.vision.common.FirebaseVisionImage
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.io.IOException
import java.util.concurrent.Executors


class BarcodeCameraActivity : AppCompatActivity() {

    private val options by lazy {
        BarcodeScannerOptions.Builder().setBarcodeFormats(
            Barcode.FORMAT_CODE_128,
            Barcode.FORMAT_CODE_39,
            Barcode.FORMAT_CODE_93,
            Barcode.FORMAT_EAN_8,
            Barcode.FORMAT_EAN_13,
            Barcode.FORMAT_QR_CODE,
            Barcode.FORMAT_UPC_A,
            Barcode.FORMAT_UPC_E,
            Barcode.FORMAT_PDF417
        ).build()
    }

    private val scanner: BarcodeScanner by lazy { BarcodeScanning.getClient(options) }

    private val cameraFuture by lazy { ProcessCameraProvider.getInstance(this) }
    private val provider by lazy { cameraFuture.get() }
    private val imageAnalysis by lazy {
        ImageAnalysis.Builder().apply {
            setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
        }.build()
    }
    private lateinit var cam : Camera

    private val barcodeView: PreviewView by lazy { findViewById(R.id.barcodeview) }

    private val albumBtn: ImageView by lazy { findViewById(R.id.album) }
    private val flashBtn: ImageView by lazy { findViewById(R.id.flash) }

    @RequiresApi(Build.VERSION_CODES.P)
    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { it ->
            if (it.resultCode == RESULT_OK) {
                val selectedUri: Uri = it.data?.data ?: "".toUri()
                val byteArray = readBytes(this, selectedUri) ?: return@registerForActivityResult

                var bitmap = byteArray.toBitmap()
                if (bitmap.width > 1080) {
                    val newHeight = bitmap.height * 1080 / bitmap.width
                    bitmap = Bitmap.createScaledBitmap(bitmap, 1080, newHeight, false)
                }

                val image: InputImage? = try {
                    InputImage.fromBitmap(bitmap, 0)
                } catch (e: IOException) {
                    null
                }

                image?.let {
                    scanner.process(it)
                        .addOnSuccessListener {
                            val barcode = it.getOrNull(0)
                            barcode?.displayValue?.let {
                                BarcodeResult.result = it
                            }
                        }
                        .addOnFailureListener {
                            BarcodeResult.result = "Barcode scanning failed!"
                        }
                        .addOnCompleteListener {
                            resultDialog(::bindCamera)
                        }
                }
            }
        }

    @Throws(IOException::class)
    private fun readBytes(context: Context, uri: Uri): ByteArray? =
        context.contentResolver.openInputStream(uri)?.buffered()?.use { it.readBytes() }

    // extension function to convert byte array to bitmap
    private fun ByteArray.toBitmap(): Bitmap {
        return BitmapFactory.decodeByteArray(this, 0, size)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barcode_camera)

        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        albumBtn.setOnClickListener {
            launcher.launch(intent)
        }

        flashBtn.setOnClickListener {
            when (cam.cameraInfo.torchState.value) {
                TorchState.ON -> {
                    it.background = getDrawable(R.drawable.flashoff)
                    cam.cameraControl.enableTorch(false)
                }
                TorchState.OFF -> {
                    it.background = getDrawable(R.drawable.flashon)
                    cam.cameraControl.enableTorch(true)
                }
            }
        }

        bindCamera()
    }

    private fun bindCamera() {
        if (ContextCompat.checkSelfPermission(
                this@BarcodeCameraActivity,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            cameraFuture.addListener({
                val preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(barcodeView.surfaceProvider)
                }

                imageAnalysis.setAnalyzer(
                    Executors.newSingleThreadExecutor(),
                ) {
                    processImageProxy(it)
                }

                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                try {
                    cam = provider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis)
                } catch (e: Exception) {
                    Logger.e("youngin $e")
                }
            }, ContextCompat.getMainExecutor(this))


        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.CAMERA),
                123
            )
        }
    }

    private fun processImageProxy(
        imageProxy: ImageProxy
    ) {

        imageProxy.image?.let { image ->
            val inputImage =
                InputImage.fromMediaImage(
                    image,
                    imageProxy.imageInfo.rotationDegrees
                )

            scanner.process(inputImage)
                .addOnSuccessListener { barcodeList ->
                    val barcode = barcodeList.getOrNull(0)

                    // `rawValue` is the decoded value of the barcode
                    barcode?.rawValue?.let { value ->

                        Handler(mainLooper).post {
                            provider.unbindAll()
                            AlertDialog.Builder(this)
                                .setMessage("barcode! $value")
                                .setPositiveButton("ok") { d, _ ->
                                    d.dismiss()
                                    bindCamera()
                                }
                                .create().show()
                        }
                    }


                }
                .addOnFailureListener {
                    // This failure will happen if the barcode scanning model
                    // fails to download from Google Play Services

                    Log.e("yougnig", it.message.orEmpty())
                }.addOnCompleteListener {
                    // When the image is from CameraX analysis use case, must
                    // call image.close() on received images when finished
                    // using them. Otherwise, new images may not be received
                    // or the camera may stall.

                    imageProxy.image?.close()
                    imageProxy.close()
                }
        }
    }

    private fun resultDialog(pos: () -> Any = {}) {
        Handler(mainLooper).post {
            provider.unbindAll()
            AlertDialog.Builder(this)
                .setMessage(BarcodeResult.result)
                .setPositiveButton("ok") { d, _ ->
                    d.dismiss()
                    pos.invoke()
                }
                .create().show()

        }
    }

}