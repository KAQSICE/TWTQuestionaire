package com.tranced.login

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import es.dmoral.toasty.Toasty

class LoginActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var toolbarTitleTv: TextView
    private lateinit var usernameEt: EditText
    private lateinit var passwdEt: EditText
    private lateinit var forgetPasswdTv: TextView
    private lateinit var loginBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        findViews()
        setToolbar()
    }

    private fun findViews() {
        toolbar = findViewById(R.id.common_toolbar)
        toolbarTitleTv = findViewById(R.id.common_toolbar_title)
        usernameEt = findViewById(R.id.usernameEt)
        passwdEt = findViewById(R.id.passwdEt)
        forgetPasswdTv = findViewById(R.id.forgetPasswdTv)
        loginBtn = findViewById(R.id.loginBtn)
    }

    private fun setToolbar() {
        toolbar.title = ""
        toolbarTitleTv.text = "登录"
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setForgetPasswdTvListener() {
        forgetPasswdTv.setOnClickListener {
            Toasty.error(this, "WIP", Toasty.LENGTH_LONG).show()
        }
    }

    private fun setLoginListener() {
        loginBtn.setOnClickListener {
            if (usernameEt.text.toString().isNotEmpty() && passwdEt.text.toString().isNotEmpty()) {
                val user = User(usernameEt.text.toString(), passwdEt.text.toString(), "-1", "-1")
            } else if (usernameEt.text.toString().isEmpty()) {
                Toasty.error(this, "用户名不能为空", Toasty.LENGTH_SHORT).show()
            } else {
                Toasty.error(this, "密码不能为空", Toasty.LENGTH_SHORT).show()
            }
        }
    }
}

data class User(
    val username: String,
    val passwd: String,
    val faculty: String,
    val profession: String
)