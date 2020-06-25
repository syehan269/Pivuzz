package com.syehan.pivuzz.recycleradapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.syehan.pivuzz.R
import com.syehan.pivuzz.model.DailyReportItem
import com.syehan.pivuzz.model.DeathsX
import java.text.NumberFormat

class DailyAdapter internal constructor(
    private val context: Context,
    private val list: List<DailyReportItem>
): RecyclerView.Adapter<DailyAdapter.DailyHolder>() {

    class DailyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDeath: TextView = itemView.findViewById(R.id.tv_death_over)
        val tvConfirm: TextView = itemView.findViewById(R.id.tv_confirm_over)
        val tvRecov: TextView = itemView.findViewById(R.id.tv_recover_over)
        val tvDate: TextView = itemView.findViewById(R.id.tv_daily_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_daily_data, parent, false)
        return DailyHolder(view)
    }

    override fun getItemCount(): Int {
        return if (list.size > 10){
            10
        }else{
            list.size
        }
    }

    override fun onBindViewHolder(holder: DailyHolder, position: Int) {
        val confirm = list[position].totalConfirmed
        val death = list[position].totalRecovered
        val recov = list[position].deaths.total
        val reformat = NumberFormat.getInstance()

        val reRecov = reformat.format(recov)
        val reDeat = reformat.format(death)
        val reCon = reformat.format(confirm)

        holder.tvConfirm.text = reCon
        holder.tvRecov.text = reRecov
        holder.tvDeath.text = reDeat
        holder.tvDate.text = list[position].reportDate
    }
}