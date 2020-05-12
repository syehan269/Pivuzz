package com.syehan.pivuzz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler().postDelayed({

            val accountPreference = AccountPreference(this)
            if (accountPreference.getBoolean("isLogin")){
                startActivity(Intent(this, ActivityDefault::class.java))
                finish()
            }else{
                startActivity(Intent(this, ActivityLogin::class.java))
                finish()
            }

        }, 300)

    }
}
