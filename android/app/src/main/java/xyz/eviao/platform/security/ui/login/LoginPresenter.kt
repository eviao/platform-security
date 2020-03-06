package xyz.eviao.platform.security.ui.login

import android.content.Context
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import xyz.eviao.platform.security.App
import xyz.eviao.platform.security.service.AuthenticationService
import xyz.eviao.platform.security.service.RetrofitManager
import xyz.eviao.platform.security.utils.CredentialUtils

class LoginPresenter(val view: LoginContract.View) : LoginContract.Presenter {

    private val compositeDisposable: CompositeDisposable
    private val authenticationService: AuthenticationService

    init {
        compositeDisposable = CompositeDisposable()
        authenticationService = RetrofitManager.create(AuthenticationService::class.java)
    }

    override fun unsubscribe() {
        compositeDisposable.clear()
    }

    private fun persistSession(map: Map<String, Any>) {
        App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE).edit()
            .putString("access_token", map.get("access_token")?.toString())
            .putString("refresh_token", map.get("refresh_token")?.toString())
            .apply()
    }

    override fun login(username: String?, password: String?) {
        
        if (username.isNullOrBlank()) {
            view.showError("用户名不能为空")
            return
        }

        if (password.isNullOrBlank()) {
            view.showError("密码不能为空")
            return
        }

        compositeDisposable.add(authenticationService.login(CredentialUtils.basic(), username, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view.showLoading() }
            .doFinally { view.hideLoading() }
            .subscribe({
                persistSession(it)
                view.navigationToHome()
            }, {
                it.printStackTrace()
                view.showError(it.message!!)
            }))
    }
}