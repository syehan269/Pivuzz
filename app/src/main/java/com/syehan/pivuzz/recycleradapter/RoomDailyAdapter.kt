package com.syehan.pivuzz.recycleradapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.syehan.pivuzz.R
import com.syehan.pivuzz.roomdir.DailyRep
import java.text.NumberFormat

class RoomDailyAdapter(
    private val context: Context,
    private var list: List<DailyRep>
): RecyclerView.Adapter<RoomDailyAdapter.ViewAdapter>() {

    class ViewAdapter(item: View): RecyclerView.ViewHolder(item) {
        val tvDeath: TextView = itemView.findViewById(R.id.tv_death_over)
        val tvConfirm: TextView = itemView.findViewById(R.id.tv_confirm_over)
        val tvRecov: TextView = itemView.findViewById(R.id.tv_recover_over)
        val tvDate: TextView = itemView.findViewById(R.id.tv_daily_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewAdapter {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_daily_data, parent, false)
        return ViewAdapter(view)
    }

    internal fun setList(list: List<DailyRep>){
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewAdapter, position: Int) {
        val confirm = list[position].totalConfirmed
        val death = list[position].totalRecovered
        val recov = list[position].deaths
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