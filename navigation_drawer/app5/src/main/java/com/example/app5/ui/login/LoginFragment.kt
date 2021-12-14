package com.example.app5.ui.login

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.app5.R
import com.example.app5.databinding.FragmentLoginBinding
import com.facebook.CallbackManager
import com.example.app5.MainActivity
import com.example.app5.data.User
import com.example.app5.data.UserDataManager
import com.example.app5.ui.login.LoginUiCallbacks.setTextClear
import com.example.app5.ui.login.LoginUiCallbacks.toggleCheckbox


class LoginFragment : Fragment(R.layout.fragment_login) {
    lateinit var user: User

    lateinit var binding: FragmentLoginBinding
    lateinit var etId: EditText
    lateinit var etPw: EditText
    lateinit var ivIdDelete: ImageView
    lateinit var ivPwDelete: ImageView
    lateinit var cbAutologin: CheckBox
    lateinit var cbSaveid: CheckBox
    lateinit var tvAutologin: TextView
    lateinit var tvSaveid: TextView
    lateinit var btnLogin: Button

    lateinit var callbackManager: CallbackManager


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // init Data instance
        user = User()

        // view component instances
        binding = FragmentLoginBinding.bind(view)
        etId = binding.etId
        etPw = binding.etPw
        ivIdDelete = binding.ivIdDelete
        ivPwDelete = binding.ivPwDelete
        cbAutologin = binding.cbAutologin
        cbSaveid = binding.cbSaveid
        tvAutologin = binding.tvAutologin
        tvSaveid = binding.tvSaveid
        btnLogin = binding.btnLogin

        initFbLoginBtn()
        initUi()
    }

    private fun initFbLoginBtn() {
        callbackManager = CallbackManager.Factory.create() //CallbackManager 초기화
        val loginCallback = LoginCallback()
        val loginButton = binding.btnFacebook
        loginButton.fragment = this
        loginButton.setPermissions(listOf("public_profile", "email")) // 권한설정, 사용자의 어떤 항목 조회할 수 있는지
        loginButton.registerCallback(callbackManager, loginCallback)

//        binding.btnFacebook.setOnClickListener{
//            val loginManager = LoginManager.getInstance()
//            loginManager.logIn(this, listOf("public_profile", "email"))
//        }

    }

    // deprecated!!
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    // registerForActivityResult
    val startForFacebookLogin = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
        Log.e("youngin", activityResult.resultCode.toString())
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun initUi() {
        // ID focus listeners
        etId.apply {
            addTextChangedListener(LoginUiCallbacks.setTextWatcher(ivIdDelete))
            onFocusChangeListener = LoginUiCallbacks.setFocusChangeListener(ivIdDelete)
            setOnKeyListener(LoginUiCallbacks.enterListener) // id 저장
        }

        // PW focus listeners
        etPw.apply {
            addTextChangedListener(LoginUiCallbacks.setTextWatcher(ivPwDelete))
            onFocusChangeListener = LoginUiCallbacks.setFocusChangeListener(ivPwDelete)

        }

        // clear focus when touch elsewhere
        view?.setOnTouchListener { view, _ ->
            if (view !is EditText) {
                clearEditTextFocus()
            }
            false
        }

        // x 버튼
        ivIdDelete.setOnClickListener(etId.setTextClear())
        ivPwDelete.setOnClickListener(etPw.setTextClear())

        // checkbox
        cbAutologin.isChecked = UserDataManager.autoLogin
        cbSaveid.isChecked = UserDataManager.saveId

        // checkbox label
        tvAutologin.setOnClickListener(cbAutologin.toggleCheckbox())
        tvSaveid.setOnClickListener(cbSaveid.toggleCheckbox())

        // custom login
        btnLogin.setOnClickListener {
            login()
        }
    }

    private fun clearEditTextFocus(){
        // hide soft keyboard
        val inputManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (inputManager.isAcceptingText) {
            inputManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        }
        etId.clearFocus()
        etPw.clearFocus()
        ivIdDelete.visibility = View.GONE
        ivPwDelete.visibility = View.GONE

    }

    fun login() {
        clearEditTextFocus()

        if (etId.text.isNotEmpty() && etPw.text.isNotEmpty()) {
            // checkbox 상태저장
            UserDataManager.autoLogin = cbAutologin.isChecked
            UserDataManager.saveId = cbSaveid.isChecked

            val intent = Intent()
            intent.putExtra("id", UserDataManager.user.id)
//            (requireActivity() as MainActivity).addHomeFrag.launch(intent)

        } else {
            AlertDialog.Builder(requireContext())
                .setMessage("ID와 패스워드를 입력하세요.")
                .setPositiveButton("확인") { _, _ -> }
                .show()
        }
    }

}