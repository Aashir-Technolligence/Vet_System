package com.aash.vetsystem.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.aash.vetsystem.R
import com.aash.vetsystem.adapters.MainPageAdapter
import com.aash.vetsystem.databinding.FragmentHomeBinding
import com.aash.vetsystem.models.MenuModel

class HomeFragment : BaseFragment() {
    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_home ,container ,false)
        initActivityView(true , getShopName())
        setRecyclerView()
        return binding.root
    }

    private fun setRecyclerView() {
        val model: ArrayList<MenuModel> = ArrayList()
        model.add(MenuModel("first" , R.drawable.aboutus))
        model.add(MenuModel("sec" , R.drawable.accountandkyc))
        model.add(MenuModel("third" , R.drawable.facedetection))
        model.add(MenuModel("fount" , R.drawable.email))
        model.add(MenuModel("first" , R.drawable.aboutus))
        model.add(MenuModel("sec" , R.drawable.accountandkyc))
        model.add(MenuModel("third" , R.drawable.facedetection))
        model.add(MenuModel("App Setting" , R.drawable.settings))
        val myRecyclerViewAdapter = MainPageAdapter(model)
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(),calculateNoOfColumns(requireContext()))
        binding.recyclerView.adapter = myRecyclerViewAdapter
        myRecyclerViewAdapter.SetOnValue(object : MainPageAdapter.onclickListener {
            override fun onclick(obj: MenuModel) {
              val bundle = Bundle()
                bundle.putString("type" , obj.head)
                if(obj.head == "App Setting")
                loadFragment(R.id.action_homeFragment_to_appSetting)
            }
        })
    }
}