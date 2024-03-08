package com.mubarak.newscastmb.ui.headlines

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.mubarak.newscastmb.R
import com.mubarak.newscastmb.databinding.FragmentHeadlineNewsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HeadlineNewsFragment : Fragment() {

    private lateinit var binding: FragmentHeadlineNewsBinding
    private lateinit var fragmentStateAdapter: HeadlineFragmentStatePager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHeadlineNewsBinding.inflate(
            layoutInflater,
            container,
            false
        )

        fragmentStateAdapter = HeadlineFragmentStatePager(childFragmentManager, lifecycle)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.newsHeadlineToolbar.setOnMenuItemClickListener {

          return@setOnMenuItemClickListener when(it.itemId){
                R.id.opt_search_headlines ->{
                    findNavController().navigate(R.id.action_headlineNewsFragment_to_searchNewsFragment)
                    true
                }else -> false
            }

        }
        binding.tbLayoutHeadline.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    binding.viewPagerHeadline.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        binding.viewPagerHeadline.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tbLayoutHeadline.selectTab(binding.tbLayoutHeadline.getTabAt(position))
            }
        })


        binding.viewPagerHeadline.adapter = fragmentStateAdapter
    }

}