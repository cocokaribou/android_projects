package com.pionnet.overpass.customView.dialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.pionnet.overpass.R
import com.pionnet.overpass.databinding.DialogCustomBinding

/**
 * CustomDialog
 * - 자주, SIV, DU와 동일한 UI의 커스텀 다이얼로그
 * - 빌더패턴 적용
 */
class CustomDialog : DialogFragment(R.layout.dialog_custom) {

    var title: String? = null
    var description: String? = null
    var positiveBtnText: String? = null
    var negativeBtnText: String? = null
    var editableText: String? = null
    private lateinit var binding: DialogCustomBinding
    var positiveListener: View.OnClickListener? = null
    var negativeListener: View.OnClickListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = DialogCustomBinding.bind(view)

        if (title.isNullOrEmpty()) {
            binding.textTitle.visibility = View.GONE
        } else {
            binding.textTitle.visibility = View.VISIBLE
            binding.textTitle.text = title
        }

        binding.textDescription.text = description

        if (negativeBtnText.isNullOrEmpty()) {
            binding.btnNegative.visibility = View.GONE
        } else {
            binding.btnNegative.visibility = View.VISIBLE
            binding.btnNegative.text = negativeBtnText
        }

        binding.btnNegative.setOnClickListener {
            dismiss()
            negativeListener?.onClick(it)
        }

        if (positiveBtnText.isNullOrEmpty()) {
            binding.btnPositive.visibility = View.GONE
        } else {
            binding.btnPositive.visibility = View.VISIBLE
            binding.btnPositive.text = positiveBtnText
        }

        binding.btnPositive.setOnClickListener {
            dismiss()
            positiveListener?.onClick(it)
        }

        // js prompt alert 처리
        if (!editableText.isNullOrEmpty()) {
            binding.editValue.visibility = View.VISIBLE
            binding.editValue.setText(editableText)
        }

    }

    class CustomDialogBuilder {
        private val dialog = CustomDialog()

        fun setTitle(title: String): CustomDialogBuilder {
            dialog.title = title
            return this
        }

        fun setDescription(description: String): CustomDialogBuilder {
            dialog.description = description
            return this
        }

        fun setPositiveBtnText(text: String): CustomDialogBuilder {
            dialog.positiveBtnText = text
            return this
        }

        fun setPositiveBtnText(text: String, listener: View.OnClickListener): CustomDialogBuilder {
            dialog.positiveBtnText = text
            dialog.positiveListener = listener
            return this
        }

        fun setNegativeBtnText(text: String): CustomDialogBuilder {
            dialog.negativeBtnText = text
            return this
        }

        fun setNegativeBtnText(text: String, listener: View.OnClickListener): CustomDialogBuilder {
            dialog.negativeBtnText = text
            dialog.negativeListener = listener
            return this
        }

        fun setEditableText(text: String): CustomDialogBuilder {
            dialog.editableText = text
            return this
        }

        fun create(): CustomDialog {
            dialog.isCancelable = false
            return dialog
        }
    }


    override fun show(manager: FragmentManager, tag: String?) {
        if (!manager.isStateSaved) {
            val ft = manager.beginTransaction()
            ft.add(this, tag)
            ft.commit()
        } else {
            val ft = manager.beginTransaction()
            ft.add(this, tag)
            ft.commitAllowingStateLoss()
        }
    }
}


