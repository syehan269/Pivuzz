package com.syehan.pivuzz

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_local.*

/**
 * A simple [Fragment] subclass.
 */
class FragmentLocal : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_local, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cv_death_local.setOnClickListener {
            startActivity(Intent(context, ActivityLocal::class.java))
        }

        cv_confirm_local.setOnClickListener {
            startActivity(Intent(context, ActivityLocal::class.java))
        }

    }
}
