package com.syehan.pivuzz

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
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
            val connectManager = baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val checkNetwork = connectManager.activeNetwork
            val networkCapabilities = connectManager.getNetworkCapabilities(checkNetwork)

            if (networkCapabilities != null){
                if (fbAuth.currentUser != null){
                    startActivity(Intent(this, ActivityDefault::class.java))
                    finish()
                }else{
                    startActivity(Intent(this, ActivityLogin::class.java))
                    finish()
                }

            }else if (accountPreference.getBoolean("isLogin")){
                startActivity(Intent(this, ActivityDefault::class.java))
                finish()
            }else{
                startActivity(Intent(this, ActivityLogin::class.java))
                finish()
            }

        }, 300)
    }
}
