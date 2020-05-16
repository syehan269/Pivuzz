package com.syehan.pivuzz

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.syehan.pivuzz.api.ApiClient
import com.syehan.pivuzz.model.Date
import com.syehan.pivuzz.model.DateItem
import com.syehan.pivuzz.recycleradapter.DateAdapter
import kotlinx.android.synthetic.main.activity_daily.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class ActivityDaily : AppCompatActivity() {

    val calendar = Calendar.getInstance()
    val year1 = calendar.get(Calendar.YEAR)
    val month1 = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val dailyList = ArrayList<DateItem>()
    var adapter: DateAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily)

        setSupportActionBar(toolbarDay)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val manager = LinearLayoutManager(this)

        manager.orientation = LinearLayoutManager.VERTICAL
        manager.isSmoothScrollbarEnabled = true
        adapter = DateAdapter(dailyList)

        rv_daily.layoutManager = manager
        rv_daily.adapter = adapter

        fab_daily.setOnClickListener {
            showDatePicker(year1, month1, day)
        }

    }

    override fun onStart() {
        super.onStart()
        val getDate = "$month1-$day-$year1"

        getList(getDate)
    }


    private fun showDatePicker(year: Int, month: Int, day: Int) {

        val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            val getDate = "$month-$dayOfMonth-$year"
            getList(getDate)
            //dailyList.clear()

        }, year, month, day)

        datePickerDialog.show()
    }

    private fun getList(date: String) {

        val call: Call<List<DateItem>> = ApiClient.getClient.getDateReport(date)
        call.enqueue(object : Callback<List<DateItem>>{
            override fun onFailure(call: Call<List<DateItem>>, t: Throwable) {
                toast(t.message)
            }

            override fun onResponse(
                call: Call<List<DateItem>>,
                response: Response<List<DateItem>>
            ) {
                dailyList.clear()
                dailyList.addAll(response.body()!!)
                adapter?.notifyDataSetChanged()
            }

        })

    }

    private fun log(message: String) {
        Log.d("retro", message)
    }

    private fun toast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}
