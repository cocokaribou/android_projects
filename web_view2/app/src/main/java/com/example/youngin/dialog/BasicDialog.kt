package com.example.youngin.dialog

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.youngin.R
import com.example.youngin.activity.MainActivity

class BasicDialog : DialogFragment() {

    //상수들
    enum class Param {
        TYPE_NOT_APP, TYPE_ROOTING, TYPE_NETWORK_ERR, TYPE_UPDATE,
        TYPE_FORCE_UPDATE, TYPE_SERVICE_CHECK, TYPE_SERVER_CHANGE
    }

    companion object{
        private val TYPE = "type"
        private val MSG = "message"

        fun create(type:Param): BasicDialog{
            return create(type, "")
        }

        fun create(type:Param, msg:String?): BasicDialog{
            val bundle = Bundle()
            bundle.putString(TYPE, type.toString())
            bundle.putString(MSG, msg)
            val fragment = BasicDialog()
            fragment.arguments = bundle
            return fragment
        }
    }

    private var activity: Activity?= null
    private lateinit var type: Param
    private lateinit var message: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = getActivity() //fragment가 띄워져있는 액티비티 리턴

        if(arguments != null){
            type = Param.valueOf(requireArguments().getString(TYPE).toString())
            message = requireArguments().getString(MSG).toString()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var msg:String? = null
        var isNegativeBtn = false

        when (type) {
            Param.TYPE_NOT_APP -> msg = getString(R.string.app_unavailable)
            Param.TYPE_ROOTING -> msg = getString(R.string.rooting_app)
            Param.TYPE_NETWORK_ERR -> msg = getString(R.string.meg_network_err)
            Param.TYPE_UPDATE -> {
                msg = getString(R.string.app_update)
                isNegativeBtn = true
            }
            Param.TYPE_FORCE_UPDATE -> msg = getString(R.string.app_force_update)
            Param.TYPE_SERVICE_CHECK -> msg = message
            Param.TYPE_SERVER_CHANGE -> msg = getString(R.string.server_test_msg)
        }

        val builder = AlertDialog.Builder(activity)
        builder.setTitle(R.string.app_name).setMessage(msg)
        builder.setPositiveButton(R.string.ok, onPositiveClickListener)
        builder.setCancelable(false)

        if (isNegativeBtn) {
            builder.setNegativeButton(R.string.cancel, onNegativeClickListener)
        }

        return builder.create()
    }

    private val onPositiveClickListener = DialogInterface.OnClickListener { _, _ ->
        when (type) {
            Param.TYPE_NOT_APP, Param.TYPE_ROOTING, Param.TYPE_NETWORK_ERR, Param.TYPE_SERVER_CHANGE, Param.TYPE_SERVICE_CHECK -> requireActivity().finish()
            Param.TYPE_UPDATE, Param.TYPE_FORCE_UPDATE -> {
                activity?.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=${context?.packageName}")))
                activity?.finish()
            }
        }
    }

    private val onNegativeClickListener = DialogInterface.OnClickListener { dialog, _ ->
        when (type) {
            Param.TYPE_UPDATE -> {
                dialog.dismiss()
                (context as MainActivity).removeSplashFragment()
            }
            else -> dialog.dismiss()
        }
    }

}