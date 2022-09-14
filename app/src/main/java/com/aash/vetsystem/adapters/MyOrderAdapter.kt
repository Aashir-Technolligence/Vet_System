package com.aash.vetsystem.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.aash.vetsystem.R
import com.aash.vetsystem.databinding.MainItemBinding
import com.aash.vetsystem.databinding.MyorderBinding
import com.aash.vetsystem.models.MenuModel
import com.aash.vetsystem.models.MyOrderModel


class MyOrderAdapter(val dataModelList: ArrayList<MyOrderModel>) :
    RecyclerView.Adapter<MyOrderAdapter.ViewHolder>() {

    var onValueDone: onclickListener? = null

    class ViewHolder(val item: MyorderBinding) : RecyclerView.ViewHolder(item.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: MyorderBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.getContext()),
            R.layout.myorder, parent, false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataModel: MyOrderModel = dataModelList[position]
        holder.item.order = dataModel
        holder.item.serial = (position + 1).toString()
        holder.item.txtPay.setOnClickListener {
            if (dataModel.payStatus != "Paid")
                onValueDone?.onclick(dataModel, position)
        }
    }

    override fun getItemCount(): Int {
        return dataModelList.size
    }

    interface onclickListener {
        fun onclick(obj: MyOrderModel, position: Int)
    }

    fun SetOnValue(onclick: onclickListener) {
        onValueDone = onclick
    }
}


