package com.syehan.pivuzz.recycleradapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.syehan.pivuzz.R
import com.syehan.pivuzz.model.DateItem

class DateAdapter internal constructor(
    private val list: List<DateItem>
): RecyclerView.Adapter<DateAdapter.DateHolder>() {

    class DateHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCountry = itemView.findViewById<TextView>(R.id.tv_country_daily)!!
        val tvConfirm = itemView.findViewById<TextView>(R.id.tv_confirm_daily)
        val tvDeath = itemView.findViewById<TextView>(R.id.tv_death_daily)
        val tvRecov = itemView.findViewById<TextView>(R.id.tv_recover_daily)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_global_data, parent, false)
        return DateHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: DateHolder, position: Int) {
        val getPlace = list[position].countryRegion + ", "+ list[position].provinceState
        holder.tvConfirm.text = list[position].confirmed
        holder.tvCountry.text = getPlace
        holder.tvDeath.text = list[position].deaths
        holder.tvRecov.text = list[position].recovered
    }
}