package kr.co.pionnet.SampleProject

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class BarcodeResultFragment: Fragment(R.layout.fragment_barcode_result) {
    private lateinit var resultTxt : TextView
    private lateinit var resultImg : ImageView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resultTxt = view.findViewById(R.id.result_txt)
        resultImg = view.findViewById(R.id.result_img)

        resultTxt.text = BarcodeResult.result
        resultImg.setImageURI(BarcodeResult.imageUri)
    }
}