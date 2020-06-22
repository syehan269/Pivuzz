package com.syehan.pivuzz

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.auth.api.Auth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.syehan.pivuzz.firestore.Account
import kotlinx.android.synthetic.main.activity_register.*

class ActivityRegister : AppCompatActivity() {

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setSupportActionBar(toolbarReg)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val accountPreference = AccountPreference(this)
        progressDialog = ProgressDialog(this)

        btn_register.setOnClickListener {
            val getEmail: String =  et_name_reg.text.toString()
            val getPass: String = et_pass_reg.text.toString()
            val getLocId: Int = spn_location_reg.selectedItemPosition
            val getLoc: String = spn_location_reg.selectedItem.toString()

            showDialog()

            if (getEmail.isNotEmpty() && getPass.isNotEmpty()){
                accountPreference.save("username",getEmail)
                accountPreference.save("password", getPass)
                accountPreference.save("locationId", getLocId)
                accountPreference.save("location", getLoc)
                authRegister(getEmail, getPass, getLoc, getLocId)

            }else{
                Toast.makeText(this, "Fill all the field", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun showDialog() {
        progressDialog.setMessage("Please wait...")
        progressDialog.show()
    }

    private fun authRegister(email: String, pass: String, location: String, locId: Int) {
        val fbAuth: FirebaseAuth = FirebaseAuth.getInstance()

        fbAuth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    fireAccount(email, pass, location, locId)
                    Log.d("fbAuth", "Account create success in Auth")
                }else{
                    Toast.makeText(baseContext, "Auth failed", Toast.LENGTH_SHORT).show()
                    Log.d("fbAuth", task.exception.toString())
                    progressDialog.dismiss()
                }
            }.addOnFailureListener { 
                Toast.makeText(baseContext, "Failed to create account (Auth)", Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
            }
    }

    private fun fireAccount(email: String, pass: String, location: String, locId: Int) {

        val fbFire: FirebaseFirestore = FirebaseFirestore.getInstance()
        val userId: String = FirebaseAuth.getInstance().currentUser!!.uid
        val account = Account(userId, email, pass, location, locId)

        fbFire.collection("Account")
            .document(userId)
            .set(account)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    Toast.makeText(baseContext, "Account created", Toast.LENGTH_SHORT).show()
                    Log.d("fbAuth", "Account create success in firestore")
                    startActivity(Intent(this, ActivityLogin::class.java))
                }else{
                    Toast.makeText(baseContext, "Fire failed", Toast.LENGTH_SHORT).show()
                    Log.d("fbAuth", task.exception.toString())
                    progressDialog.dismiss()
                }
            }.addOnFailureListener {
                Toast.makeText(baseContext, "Failed to create account (Fire)", Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
            }

    }

}
