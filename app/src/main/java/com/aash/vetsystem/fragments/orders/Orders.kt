package com.aash.vetsystem.fragments.orders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aash.vetsystem.R
import com.aash.vetsystem.databinding.FragmentOrdersBinding
import com.aash.vetsystem.fragments.BaseFragment

class Orders : BaseFragment() {
    lateinit var binding: FragmentOrdersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrdersBinding.inflate(inflater, container , false)
        initActivityView(false , "My Orders")



        return binding.root
    }
}