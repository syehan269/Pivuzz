package com.syehan.pivuzz

import android.app.ProgressDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.syehan.pivuzz.api.ApiClient
import com.syehan.pivuzz.model.Count
import com.syehan.pivuzz.model.Country
import kotlinx.android.synthetic.main.activity_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityProfile : AppCompatActivity() {

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setSupportActionBar(toolbarSet)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val accountPreference = AccountPreference(this)
        val setName: String? = accountPreference.getString("username")
        val setPass: String? = accountPreference.getString("password")
        val setLoc: Int? = accountPreference.getInt("locationId")
        progressDialog = ProgressDialog(this)

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
                showProg()
                accountPreference.save("username", getName)
                accountPreference.save("password", getPass)
                accountPreference.save("locationId", getLocId!!)
                accountPreference.save("location", getLoc!!)

                authUpdateAcc(getName, getPass, getLoc, getLocId)
            }

        }

    }

    private fun showProg() {
        progressDialog.setMessage("Please wait...")
        progressDialog.show()
    }

    private fun authUpdateAcc(name: String, pass: String, loc: String, locId: Int) {
        val user = FirebaseAuth.getInstance().currentUser

        user!!.updateEmail(name).addOnCompleteListener { task ->
            if (task.isSuccessful){
                log("UpdateAcc", "update email is success")

                user.updatePassword(pass)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful){

                            fireUpdateAcc(name, pass, loc, locId, user.uid)
                            toast("Update success")
                            log("UpdateAcc", "update password is success")

                        }else{
                            toast("Update password failed")
                            log("UpdateAcc", task.exception.toString())
                            progressDialog.dismiss()
                        }
                    }

            }else{
                toast("Update email failed")
                log("UpdateAcc", task.exception.toString())
                progressDialog.dismiss()
            }
        }
    }

    private fun fireUpdateAcc(name: String, pass: String, loc: String, locId: Int, uid: String) {

        val accountRef = FirebaseFirestore.getInstance().collection("Account")
            .document(uid)

        FirebaseFirestore.getInstance().runBatch { batch ->
            batch.update(accountRef, "email", name)
            batch.update(accountRef, "nation", loc)
            batch.update(accountRef, "nationId", locId)
            batch.update(accountRef, "password", pass)
        }.addOnCompleteListener { task ->
            if (task.isSuccessful){
                //toast("Update profile is success")
                log("UpdateAcc", "Update profile in firestore success")
                progressDialog.dismiss()
            }else{
                //toast("Update profile is failed")
                log("UpdateAcc", "error firestore: "+task.exception?.message.toString())
                progressDialog.dismiss()
            }
        }

    }

    private fun toast(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    private fun log(tag: String, message: String) {
        Log.d(tag, message)
    }



}
