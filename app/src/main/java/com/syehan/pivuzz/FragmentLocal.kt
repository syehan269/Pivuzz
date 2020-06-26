package com.syehan.pivuzz

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.syehan.pivuzz.api.ApiClient
import com.syehan.pivuzz.firestore.Global
import com.syehan.pivuzz.model.Local
import kotlinx.android.synthetic.main.fragment_local.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat

/**
 * A simple [Fragment] subclass.
 */
class FragmentLocal : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_local, container, false)
    }

    override fun onStart() {
        super.onStart()
        setNum()
    }

    private fun setNum() {
        val accountPreference = AccountPreference(this.context!!)
        val getCount = accountPreference.getString("location").toString()
        val reformat = NumberFormat.getInstance()

        val call: Call<Local> = ApiClient.getClient.getLocal(getCount)

        call.enqueue(object : Callback<Local>{
            override fun onFailure(call: Call<Local>, t: Throwable) {
                toast(t.localizedMessage)
                log(t.message!!)
            }

            override fun onResponse(call: Call<Local>, response: Response<Local>) {
                val confirm: Int = response.body()!!.confirmed.value
                val death: Int = response.body()!!.deaths.value
                val recov: Int= response.body()!!.recovered.value

                val confirmed = reformat.format(confirm)
                val death1 = reformat.format(death)
                val recoved = reformat.format(recov)

                fireGlobal(confirm, death, recov)
                
                tv_nmb_confirm_local.text = confirmed
                tv_nmb_death_local.text = death1
                tv_nmb_recover_local.text = recoved
            }

        })
    }

    private fun fireGlobal(confirmed: Int, death: Int, recoved: Int) {
        val userRef = FirebaseFirestore.getInstance().collection("User").document(FirebaseAuth.getInstance().currentUser!!.uid)
        val numberFormat = NumberFormat.getInstance()
        val user = Global(confirmed, numberFormat.format(confirmed), death, numberFormat.format(death), recoved, numberFormat.format(recoved))

        userRef.get().addOnSuccessListener { snapshot ->
            if (snapshot != null){
                val mainData = snapshot.toObject(Global::class.java) ?: Global()
                val mainCon: Int? = mainData.confirmed
                val mainDea: Int? = mainData.death
                val mainRec: Int? = mainData.recovered

                log("con: $mainCon, dea: $mainDea, rec: $mainRec")
                log("con: $confirmed, dea: $death, rec: $recoved")

                if (mainCon == null || mainDea == null || mainRec == null){
                    userRef.set(user).addOnCompleteListener { task ->
                        if (task.isSuccessful){
                            log("new Data added to collection user")
                        }else{
                            log(task.exception.toString())
                        }
                    }
                }else{

                    if (mainCon < confirmed){
                        val confir = numberFormat.format(confirmed)
                        userRef.update("confirmNotif", confir).addOnCompleteListener { task ->
                            if (task.isSuccessful){
                                log("global data confirmed update to $confir")
                            }else{
                                log("error global data confirmed ${task.exception.toString()}")
                            }
                        }
                        userRef.update("confirmed", confirmed).addOnCompleteListener { task ->
                            if (task.isSuccessful){
                                log("global data confirmed update to $confirmed")
                            }else{
                                log("error global data confirmed ${task.exception.toString()}")
                            }
                        }
                    }
                    if (mainDea < death){
                        val mati = numberFormat.format(death)
                        userRef.update("deathNotif", mati).addOnCompleteListener { task ->
                            if (task.isSuccessful){
                                log("global data death update to $mati")
                            }else{
                                log("error global data death ${task.exception.toString()}")
                            }
                        }
                        userRef.update("death", death).addOnCompleteListener { task ->
                            if (task.isSuccessful){
                                log("global data death update to $death")
                            }else{
                                log("error global data death ${task.exception.toString()}")
                            }
                        }
                    }
                    if (mainRec < recoved){
                        val waras = numberFormat.format(recoved)
                        userRef.update("recoverNotif", waras).addOnCompleteListener { task ->
                            if (task.isSuccessful){
                                log("global data recovered update to $waras")
                            }else{
                                log("error global data recov ${task.exception.toString()}")
                            }
                        }
                        userRef.update("recovered", recoved).addOnCompleteListener { task ->
                            if (task.isSuccessful){
                                log("global data recovered update to $recoved")
                            }else{
                                log("error global data recov ${task.exception.toString()}")
                            }
                        }
                    }
                }

            }else{
                log("collection is empty")
            }
        }.addOnFailureListener { exception ->
            log(exception.message.toString())
            toast(exception.localizedMessage?.toString())
        }
    }

    private fun log(message: String) {
        Log.d("retro", message)
    }

    private fun toast(message: String?) {
        Toast.makeText(activity!!.applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}
