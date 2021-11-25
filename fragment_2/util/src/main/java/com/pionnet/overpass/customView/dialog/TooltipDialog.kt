package com.pionnet.overpass.customView.dialog
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.widget.*
import com.pionnet.overpass.R
import dpToPx


class ToolTipDialog(
    context: Context,
    parentActivity: Activity,
    themeStyleRes: Int = R.style.TooltipDialogTheme // Optional custom theme
) : Dialog(context, themeStyleRes) {

    private var arrowWidth = context.dpToPx(10f)
    private var contentView : RelativeLayout
    private var container : ViewGroup
    private var upArrow : ImageView
    private var downArrow : ImageView
    private var contentText : TextView
    private var windowHeight: Int
    private var windowWidth: Int

    private var content: String = ""

    init {
        setContentView(R.layout.dialog_tooltip)
        contentView = findViewById(R.id.tooltip_dialog_content_view)
        container = findViewById(R.id.fl_container)
        upArrow = findViewById(R.id.tooltip_top_arrow)
        downArrow = findViewById(R.id.tooltip_top_arrow)

        contentText = findViewById(R.id.tooltip_content)

        val usableView = parentActivity.window.findViewById<View>(Window.ID_ANDROID_CONTENT)
        windowHeight = usableView.height
        windowWidth = usableView.width

        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        contentView.setOnClickListener {
            this.dismiss() }
    }

    override fun show() {
        drawShade()
        super.show()
    }

    private fun drawShade() {
        val bitmap = Bitmap.createBitmap(windowWidth, windowHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(Color.parseColor("#00000000"))
        contentView.background = BitmapDrawable(context.resources, bitmap)
    }

    fun pointTo(x: Int, y: Int) : ToolTipDialog {
        val params = container.layoutParams as RelativeLayout.LayoutParams
        adjustContainerMargin(x)
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP)
        params.topMargin = y
        if (x >= 0) {
            pointArrowTo(upArrow, x)
        }
        container.layoutParams = params
        return this
    }

    private fun pointArrowTo(arrow: ImageView, x: Int) {
        val arrowParams = arrow.layoutParams as FrameLayout.LayoutParams
        arrowParams.leftMargin = x - arrowWidth /2 - context.dpToPx(9f)
        arrow.layoutParams = arrowParams
        arrow.visibility = View.VISIBLE
    }

    private fun adjustContainerMargin(x: Int) {
        val params = container.layoutParams as RelativeLayout.LayoutParams
        params.leftMargin = context.dpToPx(9f)
        params.rightMargin = context.dpToPx(9f)
        container.layoutParams = params
    }

    fun content(content: String): ToolTipDialog {
        contentText.text = content
        this.content = content
        return this
    }

}
