//package com.example.aos_framework_demo
//
//import android.content.Context
//import android.content.DialogInterface
//import android.content.Intent
//import android.os.Bundle
//import android.os.Handler
//import android.os.Process
//import android.text.TextUtils
//import android.view.KeyEvent
//import android.view.View
//import android.widget.Button
//import android.widget.EditText
//import android.widget.ImageButton
//import android.widget.TextView
//import androidx.appcompat.app.AlertDialog
//
//class testJava {
//    import com.appboy.Appboy
//    import butterknife.BindString
//    import butterknife.BindView
//    import butterknife.ButterKnife
//    import kr.co.elandmall.elandmall.common.AlertDialogFragment
//    import kr.co.elandmall.elandmall.common.CommonConst
//    import kr.co.elandmall.elandmall.common.Constants
//    import kr.co.elandmall.elandmall.common.DialogUtil
//    import kr.co.elandmall.elandmall.common.LogHelper
//    import kr.co.elandmall.elandmall.common.Util
//    import kr.co.elandmall.elandmall.common.ViewUtil
//    import kr.co.elandmall.elandmall.data .LoginData
//    import kr.co.elandmall.elandmall.data .PreferenceManager
//    import kr.co.elandmall.elandmall.data .ResPreload
//    import kr.co.elandmall.elandmall.data .SlideMenuListData
//    import com.appboy.enums.NotificationSubscriptionType.OPTED_IN
//    import com.appboy.enums.NotificationSubscriptionType.UNSUBSCRIBED
//
//
//    /**
//     * Created by sonsukhee on 2016. 2. 18..
//     */
//    class SettingActivity : ElandActivity() {
//        @BindView(R.id.tvName)
//        var mTvName: TextView? = null
//
//        @BindView(R.id.tvNameMsg)
//        var mTvNameMsg: TextView? = null
//
//        @BindView(R.id.btnLogin)
//        var mBtLogin: Button? = null
//
//        @BindView(R.id.tvPushTitle)
//        var mTvPushTitle: TextView? = null
//
//        @BindView(R.id.btnPush)
//        var mBtPushNoti: ImageButton? = null
//
//        @BindView(R.id.btnUpdate)
//        var mBtUpdate: Button? = null
//
//        @BindView(R.id.tvNewVersion)
//        var mTvNewVer: TextView? = null
//
//        @BindView(R.id.tvCurVersion)
//        var mTvCurVer: TextView? = null
//
//        @BindView(R.id.tvAutoLogin)
//        var mTvAuto: TextView? = null
//        private var mHandler: Handler? = null
//        private var mContext: Context? = null
//        private var isWebViewCall = false
//        protected fun onCreate(savedInstanceState: Bundle?) {
//            super.onCreate(savedInstanceState)
//            overridePendingTransition(R.anim.anim_right_in, R.anim.anim_stay)
//            if (savedInstanceState != null) {
//                finish()
//                return
//            }
//            setContentView(R.layout.activity_setting)
//            isWebViewCall = false
//            if (getIntent() != null) {
//                isWebViewCall =
//                    getIntent().getBooleanExtra(CommonConst.INTENT_IS_WEBVIEWACTIVITY, false)
//            }
//            mContext = this
//            mElandData = ElandApplication.get().getElandData()
//            ButterKnife.bind(this)
//            initLayout()
//            mHandler = Handler()
//        }
//
//        fun onDestroy() {
//            super.onDestroy()
//        }
//
//        private fun developerMode() {
//            val builder = AlertDialog.Builder(
//                mContext!!
//            )
//            val editText = EditText(mContext)
//            editText.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
//                when (keyCode) {
//                    KeyEvent.KEYCODE_ENTER -> return@OnKeyListener true
//                }
//                false
//            })
//            builder.setPositiveButton(
//                mContext!!.getText(R.string.alert_dialog_ok)
//            ) { dialog, which ->
//                if ("besteland" == editText.text.toString()) {
//                    DialogUtil.showListDialog(this@SettingActivity, R.array.server_type,
//                        DialogInterface.OnClickListener { dialog, index ->
//                            if (index == 4) {
//                                //4번은 도메인 입력
//                                PreferenceManager.getInstance().setInt(
//                                    this@SettingActivity,
//                                    CommonConst.PREF_KEY_TEST_SERVER,
//                                    index
//                                )
//                                val builder = AlertDialog.Builder(
//                                    mContext!!
//                                )
//                                val editText = EditText(mContext)
//                                builder.setPositiveButton(
//                                    mContext!!.getText(R.string.alert_dialog_ok)
//                                ) { dialog, which ->
//                                    PreferenceManager.getInstance().setString(
//                                        this@SettingActivity,
//                                        CommonConst.PREF_KEY_TEST_DOMAIN,
//                                        editText.text.toString()
//                                    )
//                                    showAlertAppFinish()
//                                }
//                                val mDialog = builder.create()
//                                mDialog.setTitle(CommonConst.DEVELOPERMODE_TITLE)
//                                mDialog.setView(editText)
//                                mDialog.setCanceledOnTouchOutside(false)
//                                mDialog.show()
//                            } else {
//                                dialog.dismiss()
//                                //LogHelper.d("getServerUrl", "server Change = " + index);
//                                PreferenceManager.getInstance().setInt(
//                                    this@SettingActivity,
//                                    CommonConst.PREF_KEY_TEST_SERVER,
//                                    index
//                                )
//                                showAlertAppFinish()
//                            }
//                        })
//                }
//            }
//            builder.setNegativeButton(
//                mContext!!.getText(R.string.alert_dialog_cancel)
//            ) { dialog, which -> }
//            val mDialog = builder.create()
//            mDialog.setTitle(CommonConst.DEVELOPERMODE_TITLE)
//            mDialog.setView(editText)
//            mDialog.setCanceledOnTouchOutside(false)
//            mDialog.show()
//        }
//
//        private fun showAlertAppFinish() {
//            DialogUtil.showAlertDialog(
//                this@SettingActivity,
//                R.string.app_name,
//                R.string.server_test_msg,
//                object : OnDialogButtonClick() {
//                    fun doPositiveClick(tag: String?) {
//                        finishApp()
//                    }
//
//                    fun doNegativeClick(tag: String?) {}
//                })
//        }
//
//        private fun finishApp() {
//            moveTaskToBack(true)
//            finish()
//            Process.killProcess(Process.myPid())
//        }
//
//        fun initLayout() {
//            mBtUpdate!!.setOnClickListener { updateApp() }
//            findViewById(R.id.viewElandCustomer).setOnClickListener(View.OnClickListener {
//                goCustomer(
//                    CommonConst.ELAND_CALL_CENTER
//                )
//            })
//            findViewById(R.id.viewEKimsCustomer).setOnClickListener(View.OnClickListener {
//                goCustomer(
//                    CommonConst.EKIMS_CALL_CENTER
//                )
//            })
//            setLoading()
//            setLoginInfo()
//            setVersionInfo()
//            refreshPushBtn(
//                PreferenceManager.getInstance()
//                    .getBoolean(this, CommonConst.PREF_KEY_PUSH_SET, true)
//            )
//            val developerMode: TextView = findViewById(R.id.developerMode)
//            val serverType: Int = PreferenceManager.getInstance().getInt(
//                this@SettingActivity,
//                CommonConst.PREF_KEY_TEST_SERVER,
//                Constants.SERVER_TYPE_TOBE
//            )
//            if (Constants.SERVER_TYPE_TOBE !== serverType) {
//                var serverKey = ""
//                when (serverType) {
//                    Constants.SERVER_TYPE_DEV -> serverKey = "DEV"
//                    Constants.SERVER_TYPE_QA -> serverKey = "QA"
//                    Constants.SERVER_TYPE_STG -> serverKey = "STG"
//                    Constants.SERVER_TYPE_CUSTOM -> serverKey = "CST"
//                }
//                developerMode.text = serverKey
//            }
//            developerMode.isLongClickable = true
//            developerMode.setOnLongClickListener {
//                developerMode()
//                false
//            }
//        }
//
//        private fun setLoginInfo() {
//            val loginData: LoginData = mElandData.getLoginData()
//            val loadingText = ""
//            if (loginData != null) {
//                //로그인
//                var realName: String = loginData.getRealName()
//                if (TextUtils.isEmpty(realName)) {
//                    val menuListData: SlideMenuListData = ElandApplication.get().getMenuListData()
//                    if (null != menuListData) {
//                        val loginInfo: SlideMenuListData.LoginInfo = menuListData.getLoginInfo()
//                        if (null != loginInfo && !TextUtils.isEmpty(loginInfo.mbrNm)) {
//                            realName = loginInfo.mbrNm
//                        }
//                    }
//                }
//                mTvName!!.text = realName
//                mTvNameMsg.setText(getString(R.string.setting_logined_msg))
//                mBtLogin.setText(R.string.setting_btn_logout)
//                mBtLogin.setTextColor(Util.getColor(this, R.color.color_white))
//                mBtLogin!!.setBackgroundResource(R.drawable.selector_logout)
//                //자동로그인
//                mTvAuto.setText(if (loginData.isAutoLogin()) R.string.setting_auto_on else R.string.setting_auto_off)
//                mTvAuto.setTextColor(
//                    Util.getColor(
//                        this,
//                        if (loginData.isAutoLogin()) R.color.color_autoLogin_text else R.color.color_disable
//                    )
//                )
//                findViewById(R.id.lyAutoLogin).setVisibility(View.VISIBLE)
//            } else {
//                mTvName!!.text = ""
//                mTvNameMsg.setText(getString(R.string.setting_login_msg))
//                mBtLogin.setText(R.string.setting_btn_login)
//                mBtLogin.setTextColor(Util.getColor(this, R.color.color_txt_logn))
//                mBtLogin!!.setBackgroundResource(R.drawable.selector_login)
//                //자동로그인
//                findViewById(R.id.lyAutoLogin).setVisibility(View.GONE)
//            }
//        }
//
//        private fun setVersionInfo() {
//            val currVer: String = Util.getAppVersion(this)
//            var versionNew = ""
//            val resPreload: ResPreload = mElandData.getResPreload()
//            if (resPreload != null) {
//                versionNew = resPreload.getAppVersion()
//            }
//            mTvCurVer.setText(getString(R.string.setting_cur_version, currVer))
//            mTvNewVer.setText(getString(R.string.setting_new_version, versionNew))
//            mBtUpdate!!.visibility =
//                if (currVer.compareTo(versionNew) < 0) View.VISIBLE else View.GONE
//        }
//
//        private fun refreshPushBtn(isPush: Boolean) {
//            LogHelper.d(this, "push refreshPush Btn isPush = $isPush")
//            if (mTvPushTitle == null || mBtPushNoti == null) return
//            if (isPush) {
//                mTvPushTitle.setTextColor(Util.getColor(this, R.color.color_enable))
//                mBtPushNoti!!.isSelected = true
//            } else {
//                mTvPushTitle.setTextColor(Util.getColor(this, R.color.color_disable))
//                mBtPushNoti!!.isSelected = false
//            }
//        }
//
//        fun goLogin(v: View) {
//            val title = (v as Button).text.toString()
//            if (title.equals(getString(R.string.setting_btn_login), ignoreCase = true)) {
//                //로그인
//                if (isWebViewCall) {
//                    // 웹뷰에서 로그인
//                    val intent = Intent()
//                    intent.putExtra(CommonConst.TAG_SLIDE_MENU_URL, CommonConst.LOGIN_PAGE_URL)
//                    intent.putExtra(CommonConst.TAG_SLIDE_LOGIN_PAGE, true)
//                    setResult(CommonConst.SLIDE_RESULT_OK, intent)
//                    finish()
//                } else {
//                    Util.startWebViewActivity(
//                        this@SettingActivity,
//                        CommonConst.LOGIN_PAGE_URL,
//                        true,
//                        true
//                    )
//                    finish()
//                }
//            } else {
//                //로그아웃 하기
//                DialogUtil.showConfirmDialog(
//                    this@SettingActivity,
//                    R.string.setting_dlg_logout_msg,
//                    object : OnDialogButtonClick() {
//                        fun doPositiveClick(tag: String?) {
//                            if (isWebViewCall) {
//                                val intent = Intent()
//                                intent.putExtra(
//                                    CommonConst.TAG_SLIDE_MENU_URL,
//                                    CommonConst.LOGOUT_URL
//                                )
//                                intent.putExtra(CommonConst.TAG_SLIDE_LOGIN_PAGE, true)
//                                setResult(CommonConst.SLIDE_RESULT_OK, intent)
//                                finish()
//                            } else {
//                                ElandApplication.get().callWebView(CommonConst.LOGOUT_URL)
//                                Handler().postDelayed({
//                                    Util.goHomeActivity(this@SettingActivity)
//                                    finish()
//                                }, 100)
//                            }
//                        }
//
//                        fun doNegativeClick(tag: String?) {}
//                    })
//            }
//        }
//
//        fun goPush(v: View?) {
//            val isPush: Boolean = !PreferenceManager.getInstance()
//                .getBoolean(this, CommonConst.PREF_KEY_PUSH_SET, true)
//            Appboy.getInstance(this).getCurrentUser()
//                .setPushNotificationSubscriptionType(if (isPush) OPTED_IN else UNSUBSCRIBED)
//            if (PreferenceManager.getInstance()
//                    .setBoolean(getApplicationContext(), CommonConst.PREF_KEY_PUSH_SET, isPush)
//            ) {
//                DialogUtil.showPushAcceptToast(getApplicationContext(), isPush)
//                refreshPushBtn(isPush)
//            }
//        }
//
//        fun goUpdate(v: View?) {
//            updateApp()
//        }
//
//        fun goClose(v: View?) {
//            finish()
//        }
//
//        fun goCustomer(callNumber: String) {
//            startCallIntent("tel:$callNumber")
//        }
//    }
//
//}