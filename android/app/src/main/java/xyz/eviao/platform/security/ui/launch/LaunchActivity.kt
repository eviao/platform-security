package xyz.eviao.platform.security.ui.launch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.jetbrains.anko.*
import xyz.eviao.platform.security.ui.home.HomeActivity
import xyz.eviao.platform.security.ui.login.LoginActivity

class LaunchActivity : AppCompatActivity(), LaunchContract.View {

    override lateinit var presenter: LaunchContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = LaunchPresenter(this)

        LaunchActivityUi().setContentView(this)
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

    override fun navigationToLogin() {
        startActivity(intentFor<LoginActivity>().newTask().clearTask())
    }
}


