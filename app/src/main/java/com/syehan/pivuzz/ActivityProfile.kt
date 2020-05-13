package com.syehan.pivuzz

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_profile.*

class ActivityProfile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setSupportActionBar(toolbarSet)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val accountPreference = AccountPreference(this)
        val setName: String? = accountPreference.getString("username")
        val setPass: String? = accountPreference.getString("password")
        val setLoc: Int? = accountPreference.getInt("locationId")
        val location = arrayOf("USA", "Argentina", "Indonesia", "Mexico", "Canada", "Russia", "Japan")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, location)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        et_pass_pro.setText(setPass)
        et_name_pro.setText(setName)
        spn_location.setSelection(setLoc!!)
        //spn_location.adapter = adapter

        btn_profile.setOnClickListener {
            val getName: String? = et_name_pro.text.toString()
            val getPass: String? = et_pass_pro.text.toString()
            val getLoc: String? = spn_location.selectedItem.toString()
            val getLocId: Int? = spn_location.selectedItemPosition

            if (getName.isNullOrEmpty() || getPass.isNullOrEmpty()){
                toast("Fill all the field")
            }else if (getName == setName && getPass == setPass && getLocId == setLoc){
                toast("Value is same")
            }else{
                accountPreference.save("username", getName)
                accountPreference.save("password", getPass)
                accountPreference.save("locationId", getLocId!!)
                accountPreference.save("location", getLoc!!)
                toast("Update success")
            }

        }

    }

    private fun toast(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

}
