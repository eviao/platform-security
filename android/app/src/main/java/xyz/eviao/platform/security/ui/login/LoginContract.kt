package xyz.eviao.platform.security.ui.login

import xyz.eviao.platform.security.ui.BasePresenter
import xyz.eviao.platform.security.ui.BaseView

interface LoginContract {

    interface Presenter : BasePresenter {

        fun login(username: String?, password: String?)
    }

    interface View : BaseView<Presenter> {

        fun navigationToHome()

        fun showLoading()

        fun hideLoading()

        fun showError(message: String)
    }
}