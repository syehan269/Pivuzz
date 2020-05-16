package com.syehan.pivuzz

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.syehan.pivuzz.api.ApiClient
import com.syehan.pivuzz.model.Local
import kotlinx.android.synthetic.main.fragment_local.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        setNum()
    }

    private fun setNum() {
        val accountPreference = AccountPreference(this.context!!)
        val getCount = accountPreference.getString("location").toString()

        val call: Call<Local> = ApiClient.getClient.getLocal(getCount)
        call.enqueue(object : Callback<Local>{
            override fun onFailure(call: Call<Local>, t: Throwable) {
                toast(t.localizedMessage)
                log(t.message!!)
            }

            override fun onResponse(call: Call<Local>, response: Response<Local>) {
                val confirm = response.body()!!.confirmed.value.toString()
                val death = response.body()!!.deaths.value.toString()
                val recov = response.body()!!.recovered.value.toString()

                tv_nmb_confirm_local.text = confirm
                tv_nmb_death_local.text = death
                tv_nmb_recover_local.text = recov
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
