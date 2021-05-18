package com.pionnet.overpass.customView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.pionnet.overpass.databinding.DialogBinding

class Dialog : DialogFragment() {

    private lateinit var binding : DialogBinding
    var title: String? = null
    var description: String? = null
    var positiveBtnText: String? = null
    var negativeBtnText: String? = null
    var positiveListener : View.OnClickListener?  = null
    var negativeListener : View.OnClickListener?  = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DialogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view?.apply {
            if (title.isNullOrEmpty()) {
                binding.textTitle.visibility = View.GONE
            } else {
                binding.textTitle.visibility = View.VISIBLE
                binding.textTitle.text = title
            }

            binding.textDescription.text = description
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
        }
    }

    class DialogBuilder {
        private val dialog = Dialog()

        fun setTitle(title: String): DialogBuilder {
            dialog.title = title
            return this
        }

        fun setDescription(description: String): DialogBuilder {
            dialog.description = description
            return this
        }

        fun setPositiveBtnText(text: String): DialogBuilder {
            dialog.positiveBtnText = text
            return this
        }
        fun setPositiveBtnText(text: String, listener: View.OnClickListener): DialogBuilder {
            dialog.positiveBtnText = text
            dialog.positiveListener = listener
            return this
        }

        fun setNegativeBtnText(text: String): DialogBuilder {
            dialog.negativeBtnText = text
            return this
        }
        fun setNegativeBtnText(text: String, listener: View.OnClickListener): DialogBuilder {
            dialog.negativeBtnText = text
            dialog.negativeListener = listener
            return this
        }

        fun create(): Dialog {
            dialog.isCancelable = false
            return dialog
        }

    }
}