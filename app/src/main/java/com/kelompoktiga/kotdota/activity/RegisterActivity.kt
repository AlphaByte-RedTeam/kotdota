package com.kelompoktiga.kotdota.activity

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
import com.kelompoktiga.kotdota.R

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btnRegister: Button
    private lateinit var tvLogin: TextView
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnRegister = findViewById(R.id.btn_register)
        tvLogin = findViewById(R.id.tv_login)
        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_register -> {
                val email: String = etEmail.text.toString()
                val password: String = etPassword.text.toString()

                if (email.isEmpty() || password.isEmpty()) {
                    Snackbar.make(
                        this.btnRegister,
                        "Email and password mustn't be empty",
                        Snackbar.LENGTH_SHORT
                    ).show()
                } else {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                Log.d("REGISTER", "createUserWithEmail:success")
                                val homeIntent = Intent(this, HomeActivity::class.java)
                                startActivity(homeIntent)
                            } else {
                                Log.w("REGISTER", "createUserWithEmail:failure", task.exception)
                                Snackbar.make(
                                    this.btnRegister,
                                    "Failed to register",
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            }

            R.id.tv_login -> {
                val loginIntent = Intent(this, LoginActivity::class.java)
                startActivity(loginIntent)
            }
        }
    }
}