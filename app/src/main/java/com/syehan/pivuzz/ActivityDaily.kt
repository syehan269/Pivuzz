package com.syehan.pivuzz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_daily.*
import kotlinx.android.synthetic.main.activity_local.*

class ActivityDaily : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily)

        setSupportActionBar(toolbarDay)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val manager = LinearLayoutManager(this)
        manager.orientation = LinearLayoutManager.VERTICAL
        manager.isSmoothScrollbarEnabled = true

        //rv_daily.layoutManager = manager

    }
}
