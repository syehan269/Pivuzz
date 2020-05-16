package com.syehan.pivuzz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*

class ActivityRegister : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setSupportActionBar(toolbarReg)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val accountPreference = AccountPreference(this)

        btn_register.setOnClickListener {
            val getName: String =  et_name_reg.text.toString()
            val getPass: String = et_pass_reg.text.toString()
            val getLocId: Int = spn_location_reg.selectedItemPosition
            val getLoc: String = spn_location_reg.selectedItem.toString()

            if (getName.isNotEmpty() && getPass.isNotEmpty()){
                accountPreference.save("username",getName)
                accountPreference.save("password", getPass)
                accountPreference.save("locationId", getLocId)
                accountPreference.save("location", getLoc)

                startActivity(Intent(this, ActivityLogin::class.java))
            }else{
                Toast.makeText(this, "Fill all the field", Toast.LENGTH_SHORT).show()
            }

        }

    }
}
