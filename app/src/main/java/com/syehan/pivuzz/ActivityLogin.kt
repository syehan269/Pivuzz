package com.syehan.pivuzz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class ActivityLogin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val accountPreference = AccountPreference(this)

        btn_to_register.setOnClickListener {
            startActivity(Intent(this, ActivityRegister::class.java))
        }

        btn_login.setOnClickListener {
            val getName: String? = accountPreference.getString("username")
            val getPass: String? = accountPreference.getString("password")
            val inputName: String = et_name_log.text.toString()
            val inputPass: String = et_pass_log.text.toString()

            if (inputName == getName && inputPass == getPass){
                startActivity(Intent(this, ActivityDefault::class.java))
                accountPreference.save("isLogin", true)
                finish()
            }else{
                Toast.makeText(this, "No account has been registered", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
