package com.example.kotlinwork3_1

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.activity_registration.btnRegistration
import kotlinx.coroutines.launch

class RegistrationActivity : AppCompatActivity() {
    private var dialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        btnRegistration.setOnClickListener {
            lifecycleScope.launch {
                val login = registrationLogin.text?.toString().orEmpty()
                val password = registrationPassword.text?.toString().orEmpty()
                val repeatPassword = repeatPassword.text?.toString().orEmpty()
                when {

                    !isValidUsername(enterLogin.text.toString()) -> {
                        Toast.makeText(this@RegistrationActivity, "Не корректный логин", Toast.LENGTH_LONG).show()
                    }
                    !isValidPassword(enterPassword.text.toString()) -> {
                        Toast.makeText(this@RegistrationActivity, "Не корректный пароль", Toast.LENGTH_LONG).show()
                    }
                    login == "" -> {
                        Toast.makeText(this@RegistrationActivity, "Ведите Логин", Toast.LENGTH_LONG).show()
                    }
                    password == "" -> {
                        Toast.makeText(
                            this@RegistrationActivity,
                            "Введите пароль",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    repeatPassword == "" -> {
                        Toast.makeText(
                            this@RegistrationActivity,
                            "повторите пароль",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    else -> {
                        dialog = ProgressDialog(this@RegistrationActivity).apply {
                            Toast.makeText(this@RegistrationActivity, "Пожалуйста подождите", Toast.LENGTH_LONG).show()
                            Toast.makeText(this@RegistrationActivity, "Идёт загрузка данных", Toast.LENGTH_LONG).show()

                            setCancelable(false)
                        }
                        try {
                            Repository.register(login, password)


                            dialog?.dismiss()
                            val intent = Intent(this@RegistrationActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } catch (e: Exception) {
                            Toast.makeText(this@RegistrationActivity, "Ошибка соединения", Toast.LENGTH_LONG).show()
                            dialog?.dismiss()
                        }

                    }
                }
            }
        }

    }

}