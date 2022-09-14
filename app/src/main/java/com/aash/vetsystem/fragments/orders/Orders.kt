package com.aash.vetsystem.fragments.orders

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.aash.vetsystem.adapters.MyOrderAdapter
import com.aash.vetsystem.databinding.AddneworderDialogBinding
import com.aash.vetsystem.databinding.FragmentOrdersBinding
import com.aash.vetsystem.fragments.BaseFragment
import com.aash.vetsystem.interfaces.onclickEvent
import com.aash.vetsystem.models.MyOrderModel

class Orders : BaseFragment() {
    lateinit var binding: FragmentOrdersBinding
    lateinit var model: ArrayList<MyOrderModel>
    lateinit var myRecyclerViewAdapter: MyOrderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrdersBinding.inflate(inflater, container , false)
        initActivityView(false , "Orders")
        model= ArrayList()
        recyclerSetUp()
        clickListener()


        return binding.root
    }

    private fun clickListener() {
        binding.btnAdd.setOnClickListener {
            showAlertAdditem(requireContext(),object : onclickEvent{
                override fun changeValue(value: MyOrderModel) {
                    model.add(value)
                    myRecyclerViewAdapter.notifyDataSetChanged()
                }

            })
        }
    }

    private fun recyclerSetUp() {
        model.clear()
        model.add(MyOrderModel("Andy" , "200" ,"16" , "Paid","","",""))
        model.add(MyOrderModel("Murgi" , "100" ,"250" , "Un-Paid","","",""))
        model.add(MyOrderModel("Dawai" , "50" ,"500" , "Paid","","",""))
        model.add(MyOrderModel("Daro" , "40" ,"5000" , "Un-Paid","","",""))
        model.add(MyOrderModel("Vaccine" , "20" ,"630" , "Paid","","",""))
        myRecyclerViewAdapter = MyOrderAdapter(model)
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = myRecyclerViewAdapter

        myRecyclerViewAdapter.SetOnValue(object : MyOrderAdapter.onclickListener {
            override fun onclick(obj: MyOrderModel, position: Int) {

            }
        })
    }

    fun showAlertAdditem(ctx: Context ,onclick: onclickEvent) {
        val binding = AddneworderDialogBinding.inflate(layoutInflater)
        val dialog = Dialog(requireActivity())
        dialog.setCancelable(false)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(binding.root)

        var itemType: String? = null

        val items = arrayOf(
            "Select Item",
            "first",
            "second"
        )
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            ctx, android.R.layout.simple_spinner_item, items
        )

        // spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        binding.spinnerItem.adapter = adapter

        binding.spinnerItem.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>,
                view: View?,
                position: Int,
                l: Long
            ) {
                if (adapterView.getItemAtPosition(position) == "Select Item") {
                    itemType = ""
                } else {
                    itemType = items[position].toString()
                    binding.edtPrice.text ="10"

                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        })


        binding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        binding.btnAllow.setOnClickListener {
            if(itemType ==""){
                toast("Please select item.")
            }else if(binding.edtQuantity.text.toString().isEmpty()){
                toast("Quantity is missing.")
            }else if(binding.edtComment.text.isEmpty()){
                toast("Comment is missing.")
            } else {
                val price = binding.edtPrice.text.toString()
                val quantity = binding.edtQuantity.text.toString()
                val p = price.toInt() * quantity.toInt()
                onclick.changeValue(MyOrderModel(itemType.toString() , binding.edtQuantity.text.toString() ,
                   p.toString() , "Un-Paid",binding.edtComment.text.toString(),"",""))
                dialog.dismiss()
            }
        }
        dialog.show()
    }
}