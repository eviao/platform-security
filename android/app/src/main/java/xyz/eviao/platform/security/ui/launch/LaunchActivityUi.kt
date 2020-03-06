package xyz.eviao.platform.security.ui.launch

import android.graphics.Color
import android.view.Gravity.CENTER
import android.view.Gravity.CENTER_VERTICAL
import androidx.core.content.ContextCompat
import org.jetbrains.anko.*
import xyz.eviao.platform.security.R


class LaunchActivityUi : AnkoComponent<LaunchActivity> {

    override fun createView(ui: AnkoContext<LaunchActivity>) = with(ui) {
        linearLayout {
            backgroundColor = ContextCompat.getColor(context, R.color.colorPrimary)

            gravity = CENTER or CENTER_VERTICAL

            textView("Loading..") {
                textSize = sp(8).toFloat()
                textColor = Color.WHITE
            }
        }
    }
}