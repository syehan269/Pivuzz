package com.syehan.pivuzz

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import kotlinx.android.synthetic.main.activity_login.*
import kotlin.math.log

class ActivityLogin : AppCompatActivity() {

    private lateinit var accountPreference: AccountPreference
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        accountPreference = AccountPreference(this)
        progressDialog = ProgressDialog(this)

        btn_to_register.setOnClickListener {
            startActivity(Intent(this, ActivityRegister::class.java))
        }

        btn_login.setOnClickListener {
            val getEmail: String? = accountPreference.getString("username")
            val getPass: String? = accountPreference.getString("password")
            val inputName: String = et_name_log.text.toString()
            val inputPass: String = et_pass_log.text.toString()

            showDialog()

            if (inputName.isNotEmpty() && inputPass.isNotEmpty()){
                authLogin(inputName, inputPass)
            }else if (inputName.isEmpty() || inputPass.isEmpty()){
                Toast.makeText(this, "Fill all the field", Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
            }else{
                Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
                Log.e("fbAuth","faggot")
            }

        }
    }

    private fun authLogin(email: String, pass: String) {
        val fbAuth: FirebaseAuth = FirebaseAuth.getInstance()

        fbAuth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    accountPreference.save("isLogin", true)
                    accountPreference.save("password", pass)
                    accountPreference.save("email", email)
                    Toast.makeText(baseContext, "Login success", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, ActivityDefault::class.java))
                }else {
                    Toast.makeText(baseContext, "Login failed", Toast.LENGTH_SHORT).show()
                    Log.d("fbAuth", task.exception.toString())
                    progressDialog.dismiss()
                }
            }
    }

    override fun onStart() {
        super.onStart()

    }

    private fun showDialog() {
        progressDialog.setMessage("Please wait...")
        progressDialog.show()
    }
}
