package com.tranced.twtquestionaire

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.qmuiteam.qmui.kotlin.onClick
import es.dmoral.toasty.Toasty

class LoginActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var toolbarTitleTv: TextView
    private lateinit var usernameEt: EditText
    private lateinit var passwdEt: EditText
    private lateinit var forgetPasswdTv: TextView
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        findViews()
        setToolbar()
        setOnClickListeners()
    }

    private fun findViews() {
        toolbar = findViewById(R.id.common_toolbar)
        toolbarTitleTv = findViewById(R.id.common_toolbar_title)
        usernameEt = findViewById(R.id.usernameEt)
        passwdEt = findViewById(R.id.passwdEt)
        forgetPasswdTv = findViewById(R.id.forgetPasswdTv)
        loginButton = findViewById(R.id.loginBtn)
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

    private fun setOnClickListeners() {
        forgetPasswdTv.onClick {
            Toasty.error(this, "WIP", Toasty.LENGTH_SHORT).show()
        }
        loginButton.onClick {
            if (usernameEt.text.toString().isNotEmpty() && passwdEt.text.toString().isNotEmpty()) {
                val user: User = User(usernameEt.text.toString(), "-1", "-1")
                GlobalPreference.user = user
                TODO("Get User Info")
            } else if (usernameEt.text.toString().isEmpty()) {
                Toasty.error(this, "用户名不能为空", Toasty.LENGTH_SHORT).show()
            } else {
                Toasty.error(this, "密码不能为空", Toasty.LENGTH_SHORT).show()
            }
        }
    }
}
