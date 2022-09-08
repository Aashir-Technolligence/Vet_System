package com.aash.vetsystem.fragments.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aash.vetsystem.databinding.FragmentOrderManagementBinding
import com.aash.vetsystem.fragments.BaseFragment

class OrderManagement : BaseFragment() {
    lateinit var binding: FragmentOrderManagementBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderManagementBinding.inflate(inflater , container , false)
        initActivityView(false , "Order Management")

        return binding.root
    }
}