package xyz.eviao.platform.security.ui.login

import android.graphics.Color
import android.text.InputType
import android.text.InputType.TYPE_CLASS_TEXT
import android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
import android.widget.Button
import android.widget.EditText
import org.jetbrains.anko.*


class LoginActivityUi : AnkoComponent<LoginActivity> {

    lateinit var username: EditText
    lateinit var password: EditText
    lateinit var submit: Button

    override fun createView(ui: AnkoContext<LoginActivity>) = with(ui) {
        verticalLayout() {
            padding = dip(32)

            textView("Platform Security") {
                textSize = sp(9).toFloat()
                textColor = Color.BLACK
            }.lparams {
                bottomMargin = dip(32)
            }

            username = editText() {
                singleLine = true
                inputType = InputType.TYPE_CLASS_TEXT

                hint = "手机号 / 邮箱"
            }.lparams(width = matchParent) {
                bottomMargin = dip(16)
            }

            password = editText() {
                singleLine = true
                inputType = TYPE_CLASS_TEXT or TYPE_TEXT_VARIATION_PASSWORD

                hint = "清输入密码"
            }.lparams(width = matchParent) {
                bottomMargin = dip(32)
            }

            submit = button("登陆")
        }
    }
}
