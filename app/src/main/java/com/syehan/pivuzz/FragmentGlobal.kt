package com.syehan.pivuzz

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.syehan.pivuzz.api.ApiClient
import com.syehan.pivuzz.firestore.Global
import com.syehan.pivuzz.model.CovMain
import com.syehan.pivuzz.model.DailyReportItem
import com.syehan.pivuzz.recycleradapter.DailyAdapter
import com.syehan.pivuzz.roomdir.CovidViewModel
import com.syehan.pivuzz.roomdir.GlobalData
import kotlinx.android.synthetic.main.fragment_global.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat

class FragmentGlobal : Fragment() {

    val dailyList = ArrayList<DailyReportItem>()
    private lateinit var covidViewModel: CovidViewModel
    val sConfirm = "confirmed"
    val sDeath = "death"
    val sRecov = "recovered"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_global, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val manager = LinearLayoutManager(context)
        covidViewModel = ViewModelProvider(this).get(CovidViewModel::class.java)

        manager.isSmoothScrollbarEnabled = true
        manager.orientation = LinearLayoutManager.VERTICAL

        btn_more.setOnClickListener {
            startActivity(Intent(activity, ActivityDaily::class.java))
        }

        rv_global.layoutManager = manager
        rv_global.adapter = DailyAdapter(activity!!.applicationContext, dailyList)

    }

    override fun onStart() {
        super.onStart()
        setList()
        setNum()
        roomGlobalGetItem()
    }

    private fun roomGlobalGetItem() {
        covidViewModel.getGlobalItem("confirmed")
    }

    private fun setNum() {
        val call: Call<CovMain> = ApiClient.getClient.getMainInfo()
        val reformat = NumberFormat.getInstance()
        call.enqueue(object : Callback<CovMain>{
            override fun onFailure(call: Call<CovMain>, t: Throwable) {
                toast(t.localizedMessage)
                log(t.message!!)
            }

            override fun onResponse(call: Call<CovMain>, response: Response<CovMain>) {
                val confirm: Int = response.body()!!.confirmed.value
                val death: Int = response.body()!!.deaths.value
                val recovered: Int = response.body()!!.recovered.value

                val reConfirm = reformat.format(confirm)
                val reDeath = reformat.format(death)
                val reRecov = reformat.format(recovered)

                fireGlobal(confirm, death, recovered)
                covidViewModel.deleteGlobal()
                roomGlobalInsert(reConfirm, reDeath, reRecov)

                tv_nmb_confirm_global.text = reConfirm.toString()
                tv_nmb_death_global.text = reDeath.toString()
                tv_nmb_recover_global.text = reRecov.toString()
            }

        })
    }

    private fun roomGlobalInsert(reConfirm: String?, reDeath: String?, reRecov: String?) {

        var globalData = GlobalData("1", sConfirm, reConfirm!!)
        loge("room", "${globalData.category}: ${globalData.count}")
        covidViewModel.insertGlobal(globalData)

        globalData = GlobalData("2", sRecov, reRecov!!)
        loge("room", "${globalData.category}: ${globalData.count}")
        covidViewModel.insertGlobal(globalData)

        globalData = GlobalData("3", sDeath, reDeath!!)
        loge("room", "${globalData.category}: ${globalData.count}")
        covidViewModel.insertGlobal(globalData)

        loge("room",covidViewModel.getCountGlobal().toString())

    }

    private fun fireGlobal(reConfirm: Int, reDeath: Int, reRecov: Int) {
        val globalRef = FirebaseFirestore.getInstance().collection("GlobalData").document("main")
        val numberFormat = NumberFormat.getInstance()

        globalRef.get().addOnSuccessListener { snapshot ->
            if (snapshot != null){
                val mainData = snapshot.toObject(Global::class.java) ?: Global()
                val mainCon: Int? = mainData.confirmed
                val mainDea: Int? = mainData.death
                val mainRec: Int? = mainData.recovered

                log("con: $mainCon, dea: $mainDea, rec: $mainRec")
                log("con: $reConfirm, dea: $reDeath, rec: $reRecov")


                if (mainCon!! < reConfirm){
                    val confir = numberFormat.format(reConfirm)
                    globalRef.update("confirmNotif", confir).addOnCompleteListener { task ->
                        if (task.isSuccessful){
                            log("global data confirmed update to $confir")
                        }else{
                            log("error global data confirmed ${task.exception.toString()}")
                        }
                    }
                    globalRef.update("confirmed", reConfirm).addOnCompleteListener { task ->
                        if (task.isSuccessful){
                            log("global data confirmed update to $reConfirm")
                        }else{
                            log("error global data confirmed ${task.exception.toString()}")
                        }
                    }
                }
                if (mainDea!! < reDeath){
                    val mati = numberFormat.format(reDeath)
                    globalRef.update("deathNotif", mati).addOnCompleteListener { task ->
                        if (task.isSuccessful){
                            log("global data death update to $mati")
                        }else{
                            log("error global data death ${task.exception.toString()}")
                        }
                    }
                    globalRef.update("death", reDeath).addOnCompleteListener { task ->
                        if (task.isSuccessful){
                            log("global data death update to $reDeath")
                        }else{
                            log("error global data death ${task.exception.toString()}")
                        }
                    }

                }
                if (mainRec!! < reRecov){
                    val waras = numberFormat.format(reRecov)
                    globalRef.update("recoverNotif", waras).addOnCompleteListener { task ->
                        if (task.isSuccessful){
                            log("global data recovered update to $waras")
                        }else{
                            log("error global data recov ${task.exception.toString()}")
                        }
                    }
                    globalRef.update("recovered", reRecov).addOnCompleteListener { task ->
                        if (task.isSuccessful){
                            log("global data recovered update to $reRecov")
                        }else{
                            log("error global data recov ${task.exception.toString()}")
                        }
                    }
                }

            }else{
                toast("Global data is empty")
            }
        }.addOnFailureListener { exception ->
            log(exception.message.toString())
            toast(exception.localizedMessage?.toString())
        }

    }

    private fun setList() {
        val call: Call<List<DailyReportItem>> = ApiClient.getClient.getListDaily()
        call.enqueue(object : Callback<List<DailyReportItem>>{
            override fun onFailure(call: Call<List<DailyReportItem>>, t: Throwable) {
                toast(t.message)
            }

            override fun onResponse(
                call: Call<List<DailyReportItem>>,
                response: Response<List<DailyReportItem>>
            ) {
                dailyList.addAll(response.body()!!)
                dailyList.reverse()
                rv_global.adapter!!.notifyDataSetChanged()
            }

        })
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
