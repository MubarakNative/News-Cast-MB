package com.mubarak.newscastmb.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mubarak.newscastmb.R
import com.mubarak.newscastmb.databinding.FragmentSettingsHostBinding
import com.mubarak.newscastmb.utils.onUpButtonClick
class SettingsHostFragment : Fragment() {

    private lateinit var binding: FragmentSettingsHostBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsHostBinding.inflate(
            layoutInflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.settingsHostToolbar.onUpButtonClick()

        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings_host_container, SettingsFragment())
            .commit()

    }

}