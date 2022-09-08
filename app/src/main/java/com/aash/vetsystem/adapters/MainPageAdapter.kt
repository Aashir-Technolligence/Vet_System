package com.aash.vetsystem.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.aash.vetsystem.R
import com.aash.vetsystem.databinding.MainItemBinding
import com.aash.vetsystem.models.MenuModel


class MainPageAdapter(val dataModelList: ArrayList<MenuModel>): RecyclerView.Adapter<MainPageAdapter.ViewHolder>() {

    var onValueDone: onclickListener? = null
    class ViewHolder(val item: MainItemBinding) : RecyclerView.ViewHolder(item.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: MainItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.getContext()),
            R.layout.main_item, parent, false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataModel: MenuModel = dataModelList[position]
        holder.item.model = dataModel
        holder.item.imgMenu.setImageResource(dataModel.img)
        holder.item.root.setOnClickListener {
            onValueDone?.onclick(dataModel)
        }
    }

    override fun getItemCount(): Int {
        return dataModelList.size
    }
    interface onclickListener{
        fun onclick(obj: MenuModel)
    }

    fun SetOnValue(onclick: onclickListener) {
        onValueDone = onclick
    }
}


