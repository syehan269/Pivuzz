package com.syehan.pivuzz

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.syehan.pivuzz.api.ApiClient
import com.syehan.pivuzz.model.CovMain
import com.syehan.pivuzz.model.DailyReportItem
import com.syehan.pivuzz.recycleradapter.DailyAdapter
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_daily.*
import kotlinx.android.synthetic.main.fragment_global.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class FragmentGlobal : Fragment() {

    val dailyList = ArrayList<DailyReportItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_global, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val manager = LinearLayoutManager(context)
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
    }

    private fun setNum() {
        val call: Call<CovMain> = ApiClient.getClient.getMainInfo()
        call.enqueue(object : Callback<CovMain>{
            override fun onFailure(call: Call<CovMain>, t: Throwable) {
                toast(t.localizedMessage)
                log(t.message!!)
            }

            override fun onResponse(call: Call<CovMain>, response: Response<CovMain>) {
                toast(response.body()!!.confirmed.toString())
            }

        })
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

    private fun toast(message: String?) {
        Toast.makeText(activity!!.applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}
