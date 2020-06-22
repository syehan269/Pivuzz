package com.syehan.pivuzz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler().postDelayed({

            val accountPreference = AccountPreference(this)
            val fbAuth: FirebaseAuth = FirebaseAuth.getInstance()

            if (accountPreference.getBoolean("isLogin") && fbAuth.currentUser != null){
                startActivity(Intent(this, ActivityDefault::class.java))
                finish()
            }else{
                startActivity(Intent(this, ActivityLogin::class.java))
                finish()
            }

        }, 300)
    }
}
