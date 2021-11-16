package com.example.kotlinwork3_1

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var dialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLogin.setOnClickListener {
            when {
                !isValidUsername(enterLogin.text.toString()) -> {
                    Toast.makeText(this@MainActivity, "Не корректный логин", Toast.LENGTH_LONG).show()
                }
                !isValidPassword(enterPassword.text.toString()) -> {
                    Toast.makeText(this@MainActivity, "Не корректный пароль", Toast.LENGTH_LONG).show()
                }
                else -> {
                    lifecycleScope.launch {
                        dialog = ProgressDialog(this@MainActivity).apply {
                            Toast.makeText(this@MainActivity, "Пожалуйста подождите", Toast.LENGTH_LONG).show()
                            Toast.makeText(this@MainActivity, "Идёт загрузка данных", Toast.LENGTH_LONG).show()
                            setCancelable(false)
                        }


                        val login = enterLogin.text?.toString().orEmpty()
                        val password = enterPassword.text?.toString().orEmpty()
                        try {
                            val token = Repository.authenticate(login, password)

                            dialog?.dismiss()
                            if (token.isSuccessful) {
                                setUserAuth(requireNotNull(token.body()).toString())
                            } else {
                                Toast.makeText(this@MainActivity, "Ошибка авторизации", Toast.LENGTH_LONG).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(this@MainActivity, "Ошибка подключения", Toast.LENGTH_LONG).show()
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



    private fun isAuthenticated(): Boolean =
        getSharedPreferences(API_SHARED_FILE, Context.MODE_PRIVATE).getString(
            AUTHENTICATED_SHARED_KEY, ""
        )?.isNotEmpty() ?: false

    private fun setUserAuth(token: String) =
        getSharedPreferences(API_SHARED_FILE, Context.MODE_PRIVATE).edit().putString(AUTHENTICATED_SHARED_KEY, token).apply()
    }


