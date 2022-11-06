package com.kelompoktiga.kotdota

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btnLogin: Button
    private lateinit var tvRegister: TextView
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin = findViewById(R.id.btn_login)
        tvRegister = findViewById(R.id.tv_register)
        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_login -> {
                val email: String = etEmail.text.toString()
                val password: String = etPassword.text.toString()

                if (email.isEmpty() || password.isEmpty()) {
                    Snackbar.make(
                        this.btnLogin,
                        "Email and password mustn't be empty",
                        Snackbar.LENGTH_SHORT
                    ).show()
                } else {
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                Log.d("LOGIN", "signInWithEmailAndPassword:success")
                                val homeIntent = Intent(this, HomeActivity::class.java)
                                startActivity(homeIntent)
                            } else {
                                Log.w("LOGIN", "signInWithEmailAndPassword:failure", task.exception)
                                Snackbar.make(
                                    this.btnLogin,
                                    "Failed to login",
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            }

            R.id.tv_register -> {
                val registerIntent = Intent(this, RegisterActivity::class.java)
                startActivity(registerIntent)
            }
        }
    }
}