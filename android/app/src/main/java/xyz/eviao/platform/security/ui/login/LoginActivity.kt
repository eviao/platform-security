@file:Suppress("DEPRECATION")

package xyz.eviao.platform.security.ui.login

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.*
import xyz.eviao.platform.security.ui.home.HomeActivity

class LoginActivity : AppCompatActivity(), LoginContract.View {

    lateinit override var presenter: LoginContract.Presenter
    lateinit var ui: LoginActivityUi

    private var loading: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = LoginPresenter(this)

        ui = LoginActivityUi()
        ui.setContentView(this)

        ui.submit.setOnClickListener(handleLogin)
    }

    val handleLogin = View.OnClickListener {
        val username = ui.username.text.toString()
        val password = ui.password.text.toString()
        presenter.login(username, password)
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe()
    }

    override fun onPause() {
        super.onPause()
        presenter.unsubscribe()
    }

    override fun navigationToHome() {
        startActivity(intentFor<HomeActivity>().newTask().clearTask())
    }

    override fun showLoading() {
        loading = indeterminateProgressDialog("正在登陆").apply {
            setCancelable(false)
        }
    }

    override fun hideLoading() {
        loading?.dismiss()
    }

    override fun showError(message: String) {
        longToast(message)
    }
}