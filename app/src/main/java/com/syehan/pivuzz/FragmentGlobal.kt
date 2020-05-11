package com.syehan.pivuzz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_global.*

class FragmentGlobal : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_global, container, false)
        val entry = ArrayList<PieEntry>()
        //val entry = MutableList<PieEntry>(4)

        entry.add(PieEntry(18.5f, "Green"))
        entry.add(PieEntry(26.7f, "Red"))
        entry.add(PieEntry(24.0f, "Blue"))
        entry.add(PieEntry(30.8f, "Yellow"))

        val set = PieDataSet(entry, "Result")
        val data = PieData(set)
        //chartGlobal.data = data
        //chartGlobal.invalidate()

/*
        val set = PieDataSet(entry, "Election Result")
        val data = PieData(set)
        chartGlobal.data = data
        chartGlobal.invalidate()

 */

        return view
    }

}
