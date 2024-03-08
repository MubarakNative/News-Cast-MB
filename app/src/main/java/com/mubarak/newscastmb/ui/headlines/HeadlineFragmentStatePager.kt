package com.mubarak.newscastmb.ui.headlines

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mubarak.newscastmb.ui.headlines.categories.business.BusinessNewsFragment
import com.mubarak.newscastmb.ui.headlines.categories.entertainment.EntertainmentNewsFragment
import com.mubarak.newscastmb.ui.headlines.categories.health.HealthNewsFragment
import com.mubarak.newscastmb.ui.headlines.categories.science.ScienceNewsFragment
import com.mubarak.newscastmb.ui.headlines.categories.sports.SportsNewsFragment
import com.mubarak.newscastmb.ui.headlines.categories.technology.TechNewsFragment
import com.mubarak.newscastmb.utils.AppConstants.FIRST_TAB
import com.mubarak.newscastmb.utils.AppConstants.FOURTH_TABS
import com.mubarak.newscastmb.utils.AppConstants.SECOND_TABS
import com.mubarak.newscastmb.utils.AppConstants.START_TAB
import com.mubarak.newscastmb.utils.AppConstants.THIRD_TABS
import com.mubarak.newscastmb.utils.AppConstants.TOTAL_TABS


class HeadlineFragmentStatePager(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return TOTAL_TABS
    }

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            START_TAB -> BusinessNewsFragment()
            FIRST_TAB -> EntertainmentNewsFragment()
            SECOND_TABS -> HealthNewsFragment()
            THIRD_TABS -> ScienceNewsFragment()
            FOURTH_TABS -> SportsNewsFragment()
            else -> TechNewsFragment()
        }
    }


}