package com.syehan.pivuzz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_default.*

class ActivityDefault : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_default)
        val tabAdapter = TabAdapter(supportFragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        viewPager.adapter = tabAdapter
        val accountPreference = AccountPreference(this)

        toolbarDef.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.about ->{
                    startActivity(Intent(this, ActivityAbout::class.java))
                    return@setOnMenuItemClickListener true
                }
                R.id.setting ->{
                    startActivity(Intent(this, ActivityProfile::class.java))
                    return@setOnMenuItemClickListener true
                }
                R.id.logout ->{
                    accountPreference.removeValue("isLogin")
                    FirebaseAuth.getInstance().signOut()
                    startActivity(Intent(this, ActivityLogin::class.java))
                    finish()
                    return@setOnMenuItemClickListener true
                }
                else -> return@setOnMenuItemClickListener false
            }

        }

        setTab()
    }

    private fun setTab() {
        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabMain))
        tabMain!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager!!.currentItem = tab!!.position
            }

        })
    }
}
