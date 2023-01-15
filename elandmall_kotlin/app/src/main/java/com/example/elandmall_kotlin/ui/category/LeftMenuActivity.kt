package com.example.elandmall_kotlin.ui.category

import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.viewModels
import com.example.elandmall_kotlin.BaseActivity
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.databinding.ActivityLeftMenuBinding
import com.example.elandmall_kotlin.model.CategoryResponse
import com.example.elandmall_kotlin.ui.EventBus
import com.example.elandmall_kotlin.util.Logger
import com.example.elandmall_kotlin.view.CategoryButton

class LeftMenuActivity : BaseActivity<ActivityLeftMenuBinding, LeftMenuViewModel>(R.layout.activity_left_menu) {
    override val viewModel: LeftMenuViewModel by viewModels()

    private val mAdapter by lazy { LeftMenuAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
//        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_none)

        super.onCreate(savedInstanceState)

        initUI()
        initObserve()
    }

    private fun initUI() = with(binding){
        list.adapter = mAdapter
    }

    private fun initObserve() {
        EventBus.linkEvent.observe(this) {
            it.getIfNotHandled()?.let { event ->
                Logger.v("잘 왔다 $event")
            }
        }
        viewModel.topMenuData.observe(this) {
            drawTopMenu(it)
        }
        viewModel.uiList.observe(this) {
            mAdapter.submitList(it)
        }
    }

    override fun finish() {
        overridePendingTransition(R.anim.slide_none, R.anim.slide_left_out)
        super.finish()
    }

    private fun drawTopMenu(list: List<CategoryResponse.Data.NavCatTopMenu?>) = with(binding.topMenu) {
        weightSum = (list.size).toFloat()
        list.forEachIndexed { i, menuItem ->
            val btn = CategoryButton(context, null, 0).apply {
                title = menuItem?.menuNm ?: ""

                couponCount = menuItem?.cupnCnt
                cartCount = menuItem?.cartCnt

                click = { finish() }
                icon = when(i) {
                    0 -> R.drawable.ic_user
                    1 -> R.drawable.ic_shipping
                    2 -> R.drawable.ic_shopping_bag
                    3 -> R.drawable.ic_favorite
                    else -> R.drawable.ic_coupon
                }
                layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            }

            addView(btn)
        }
    }
}

