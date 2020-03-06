package xyz.eviao.platform.security.ui.launch

import xyz.eviao.platform.security.ui.BasePresenter
import xyz.eviao.platform.security.ui.BaseView

interface LaunchContract {

    interface Presenter : BasePresenter {

        fun checkSession()
    }

    interface View : BaseView<Presenter> {

        fun navigationToHome()

        fun navigationToLogin()
    }
}