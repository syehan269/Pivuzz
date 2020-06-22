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

class ActivityLogin : AppCompatActivity() {

    private lateinit var accountPreference: AccountPreference
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        accountPreference = AccountPreference(this)

        btn_to_register.setOnClickListener {
            startActivity(Intent(this, ActivityRegister::class.java))
        }

        btn_login.setOnClickListener {
            val getEmail: String? = accountPreference.getString("username")
            val getPass: String? = accountPreference.getString("password")
            val inputName: String = et_name_log.text.toString()
            val inputPass: String = et_pass_log.text.toString()

            showDialog()

            if (inputName == getEmail && inputPass == getPass){
                authLogin(getEmail, getPass)
            }else if(inputName.isEmpty() || inputPass.isEmpty()){
                Toast.makeText(this, "Fill all the field", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "No account has been registered", Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
            }
        }
    }

    private fun authLogin(email: String, pass: String) {
        val fbAuth: FirebaseAuth = FirebaseAuth.getInstance()

        fbAuth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    accountPreference.save("isLogin", true)
                    Toast.makeText(baseContext, "Login success", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, ActivityDefault::class.java))
                }else {
                    Toast.makeText(baseContext, "Login failed", Toast.LENGTH_SHORT).show()
                    Log.d("fbAuth", task.exception.toString())
                    progressDialog.dismiss()
                }
            }.addOnFailureListener {
                Toast.makeText(baseContext, "Login error", Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
            }

    }

    override fun onStart() {
        super.onStart()
        val token: Task<InstanceIdResult> = FirebaseInstanceId.getInstance().instanceId

    }

    private fun showDialog() {
        progressDialog.setMessage("Please wait...")
        progressDialog.show()
    }
}
