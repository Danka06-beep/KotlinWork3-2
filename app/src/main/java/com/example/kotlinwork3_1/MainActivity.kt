package com.example.kotlinwork3_1

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.kotlinwork3_1.api.App
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var dialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        goToPost()
        btnLogin.setOnClickListener {
            when {
                !isValidUsername(enterLogin.text.toString()) -> {
                    Toast.makeText(this@MainActivity,
                        getString(R.string.Invalidlogin),
                        Toast.LENGTH_LONG).show()
                }
                !isValidPassword(enterPassword.text.toString()) -> {
                    Toast.makeText(this@MainActivity,
                        getString(R.string.InvalidPassword),
                        Toast.LENGTH_LONG).show()
                }
                else -> {
                    lifecycleScope.launch {
                        dialog = ProgressDialog(this@MainActivity).apply {
                        }
                        val login = enterLogin.text?.toString().orEmpty()
                        val password = enterPassword.text?.toString().orEmpty()
                        try {

                                val token = App.repository.authenticate(login, password)
                                dialog?.dismiss()
                                if (token.isSuccessful) {
                                    setUserAuth(requireNotNull(token.body()).token)
                                    requestToken()
                                    goToPost()
                                } else {
                                    Toast.makeText(this@MainActivity,
                                        getString(R.string.erorAuthorization),
                                        Toast.LENGTH_LONG).show()
                                }

                        } catch (e: Exception) {
                            Toast.makeText(this@MainActivity,
                                getString(R.string.erorConnect),
                                Toast.LENGTH_LONG).show()
                            dialog?.dismiss()
                        }
                    }
                }
            }
        }
        btnRegistration.setOnClickListener {
            val intent = Intent(this@MainActivity, RegistrationActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun goToPost() {
        if (authenticated()) {
            val intent = Intent(this@MainActivity, PostActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun authenticated(): Boolean =
        getSharedPreferences(API_SHARED_FILE, Context.MODE_PRIVATE).getString(
            AUTHENTICATED_SHARED_KEY,
            "")?.isNotEmpty() ?: false


    private fun setUserAuth(token: String) =
        getSharedPreferences(API_SHARED_FILE, Context.MODE_PRIVATE).edit()
            .putString(AUTHENTICATED_SHARED_KEY, token).apply()


    private fun requestToken(){
        with(GoogleApiAvailability.getInstance()) {
            val code = isGooglePlayServicesAvailable(this@MainActivity)
            if (code == ConnectionResult.SUCCESS) {
                return@with
            }
            if (isUserResolvableError(code)) {
                getErrorDialog(this@MainActivity, code, 9000).show()
                return
            }
            Toast.makeText(this@MainActivity,
                getString(R.string.google_play_unavailable),
                Toast.LENGTH_LONG).show()
            dialog?.dismiss()
            return
        }
        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            lifecycleScope.launch {
                App.repository.tokenDeviceId(it)
            }
        }
    }
}