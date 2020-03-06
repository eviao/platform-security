package xyz.eviao.platform.security.ui.home

import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.textView


class HomeActivityUi : AnkoComponent<HomeActivity> {

    override fun createView(ui: AnkoContext<HomeActivity>) = with(ui) {
        linearLayout {
            textView("home activity")
        }
    }
}