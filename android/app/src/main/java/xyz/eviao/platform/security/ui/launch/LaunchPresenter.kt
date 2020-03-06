package xyz.eviao.platform.security.ui.launch

import android.content.Context
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import xyz.eviao.platform.security.App
import java.util.concurrent.TimeUnit

class LaunchPresenter(val view: LaunchContract.View): LaunchContract.Presenter {

    private val compositeDisposable: CompositeDisposable

    init {
        compositeDisposable = CompositeDisposable()
    }

    override fun subscribe() {
        this.checkSession()
    }

    override fun checkSession() {
        val session = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE)
        val token = session.getString("access_token", null)

        val navigation = if (token.isNullOrBlank()) {
            view::navigationToLogin
        } else {
            view::navigationToHome
        }

        Observable.timer(2, TimeUnit.SECONDS).subscribe { navigation() }
    }
}