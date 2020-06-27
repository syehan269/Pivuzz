package com.syehan.pivuzz

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.syehan.pivuzz.api.ApiClient
import com.syehan.pivuzz.firestore.Global
import com.syehan.pivuzz.model.Local
import com.syehan.pivuzz.roomdir.CovidViewModel
import com.syehan.pivuzz.roomdir.LocalData
import kotlinx.android.synthetic.main.fragment_global.*
import kotlinx.android.synthetic.main.fragment_local.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat

/**
 * A simple [Fragment] subclass.
 */
class FragmentLocal : Fragment() {

    private val sConfirm = "confirmed"
    private val sDeath = "death"
    private val sRecov = "recovered"
    private lateinit var covidViewModel: CovidViewModel
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_local, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        covidViewModel = ViewModelProvider(this).get(CovidViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        FirebaseFirestore.getInstance().collection("Account").document(FirebaseAuth.getInstance().currentUser!!.uid)
            .addSnapshotListener { snapshot, firestoreException ->
                if (snapshot != null){
                    val country = snapshot.get("nation").toString()
                    setNum(country)
                }else{
                    toast(firestoreException?.message.toString())
                }
            }
    }

    private fun setNum(country: String) {
        val accountPreference = AccountPreference(this.context!!)
        val reformat = NumberFormat.getInstance()

        val call: Call<Local> = ApiClient.getClient.getLocal(country)
        call.enqueue(object : Callback<Local>{
            override fun onFailure(call: Call<Local>, t: Throwable) {
                //toast(t.localizedMessage)
                log(t.message!!)
                setDash()
            }

            override fun onResponse(call: Call<Local>, response: Response<Local>) {

                val confirm: Int = response.body()!!.confirmed.value
                val death: Int = response.body()!!.deaths.value
                val recov: Int= response.body()!!.recovered.value

                val confirmed = reformat.format(confirm)
                val death1 = reformat.format(death)
                val recoved = reformat.format(recov)

                log(confirm.toString())
                log(death.toString())
                log(recov.toString())

                covidViewModel.deleteLocal()
                fireGlobal(confirm, death, recov)
                roomGlobalInsert(confirmed, death1, recoved)
                
                tv_nmb_confirm_local.text = confirmed
                tv_nmb_death_local.text = death1
                tv_nmb_recover_local.text = recoved
            }

        })
    }

    private fun setDash() {
        covidViewModel.allLocal.observe(viewLifecycleOwner, Observer { dailyList ->
            dailyList?.let {
                val confirm = dailyList[0].count
                val recov = dailyList[1].count
                val death = dailyList[2].count

                tv_nmb_confirm_local.text = confirm
                tv_nmb_death_local.text = death
                tv_nmb_recover_local.text = recov
            }
        })
    }

    private fun roomGlobalInsert(reConfirm: String?, reDeath: String?, reRecov: String?) {

        var globalData = LocalData("1", sConfirm, reConfirm!!)
        loge("room", "${globalData.category}: ${globalData.count}")
        covidViewModel.insertLocal(globalData)

        globalData = LocalData("2", sRecov, reRecov!!)
        loge("room", "${globalData.category}: ${globalData.count}")
        covidViewModel.insertLocal(globalData)

        globalData = LocalData("3", sDeath, reDeath!!)
        loge("room", "${globalData.category}: ${globalData.count}")
        covidViewModel.insertLocal(globalData)

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

    private fun loge(tag: String, message: String){
        Log.d(tag, message)
    }


    private fun toast(message: String?) {
        Toast.makeText(activity!!.applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}
