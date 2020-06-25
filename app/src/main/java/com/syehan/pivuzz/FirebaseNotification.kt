package com.syehan.pivuzz

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService

class FirebaseNotification : FirebaseMessagingService() {

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.d("FCM", p0)
    }

}