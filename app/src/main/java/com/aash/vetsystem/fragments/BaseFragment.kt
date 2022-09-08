package com.aash.vetsystem.fragments

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.aash.vetsystem.Preferences.VetPreferences
import com.aash.vetsystem.R
import com.aash.vetsystem.databinding.AlertDialogBinding
import com.aash.vetsystem.databinding.ChangeDialogBinding
import com.aash.vetsystem.dialog.ProgressBarDialog
import com.aash.vetsystem.utils.gone
import com.aash.vetsystem.utils.visible


open class BaseFragment : Fragment() {
    lateinit var toolbarBackBtn: ImageView
    lateinit var toolbarNavBtn: ImageView
    lateinit var toolbarText: TextView
    lateinit var pref: VetPreferences
    lateinit var alertDialog: Dialog
    lateinit var progressBarDialogRegister: ProgressBarDialog

    fun getShopName(): String {
        pref = VetPreferences(requireContext())
        return pref.company.toString()
    }

    fun initActivityView(mainPage: Boolean, head: String) {
        toolbarBackBtn = requireActivity().findViewById(R.id.imageBack)
        toolbarNavBtn = requireActivity().findViewById(R.id.imageNav)
        toolbarText = requireActivity().findViewById(R.id.txtToolbarHead)
        toolbarText.text = head
        if (mainPage) {
            toolbarNavBtn.visible()
            toolbarBackBtn.gone()
        } else {
            toolbarBackBtn.visible()
            toolbarNavBtn.gone()
        }
        toolbarBackBtn.setOnClickListener {
            backPressToolbar()
        }
        progressBarDialogRegister = ProgressBarDialog(requireContext())
    }

    fun loadFragment(action: Int) {
        try {
            view?.let {
                Navigation.findNavController(it).navigate(action)
            }
        } catch (e: Exception) {

        }
    }

    fun loadFragment(action: Int, bundle: Bundle) {
        try {
            view?.let {
                Navigation.findNavController(it).navigate(action, bundle)
            }
        } catch (e: Exception) {

        }
    }

    fun backPressToolbar() {
        try {
            view?.let {
                Navigation.findNavController(it).popBackStack()
            }
        } catch (e: Exception) {

        }
    }

    fun showAlertDialog(stMessage: String?) {
        val binding = AlertDialogBinding.inflate(layoutInflater)
        val dialog = Dialog(requireActivity())
        dialog.setCancelable(true)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(binding.root)
        binding.tvMessage.text = stMessage
        binding.btnAllow.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    fun dissmissProgressBar() {
        if (progressBarDialogRegister.isShowing) {
            progressBarDialogRegister.dismiss()
        }
    }

    fun hideKeyboard() {
        val imm: InputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().applicationWindowToken, 0)
    }

    fun toast(value: String) {
        Toast.makeText(context, value, Toast.LENGTH_SHORT).show()
    }

    open fun calculateNoOfColumns(context: Context): Int {
        val displayMetrics = context.resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        // Where 180 is the width of your grid item. You can change it as per your convention.
        return (dpWidth / 180).toInt()
    }

    fun showPermissionDeniedDialog(stMessage: String) {
        val binding = AlertDialogBinding.inflate(layoutInflater)
        val dialog = Dialog(requireActivity())
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(binding.root)
        binding.tvMessage.text = stMessage
        binding.btnAllow.setOnClickListener {
            openApplicationSetting()
            dialog.dismiss()

        }
        dialog.show()
    }

    private fun openApplicationSetting() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", requireActivity().packageName, null)
        intent.data = uri
        startActivity(intent)
    }

    fun showAlertChange(stMessage: String?, onclick: onclick) {
        val binding = ChangeDialogBinding.inflate(layoutInflater)
        val dialog = Dialog(requireActivity())
        dialog.setCancelable(false)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(binding.root)
        when (stMessage) {
            "password" -> {
                binding.txtTitle.text = "Change Password"
                binding.edtText.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.edtText.filters = arrayOf<InputFilter>(LengthFilter(10))
            }
            "email" -> {
                binding.txtTitle.text = "Add/Change Email"
                binding.edtText.inputType = InputType.TYPE_CLASS_TEXT
                binding.edtText.filters = arrayOf<InputFilter>(LengthFilter(30))
            }
            "whatsapp" -> {
                binding.txtTitle.text = "Change Whatsapp"
                binding.edtText.inputType = InputType.TYPE_CLASS_NUMBER
                binding.edtText.filters = arrayOf<InputFilter>(LengthFilter(11))
            }
            "appname" -> {
                binding.txtTitle.text = "Change AppName"
                binding.edtText.inputType = InputType.TYPE_CLASS_TEXT
                binding.edtText.filters = arrayOf<InputFilter>(LengthFilter(12))
            }
            "description" -> {
                binding.txtTitle.text = "Change Description"
                binding.edtText.inputType = InputType.TYPE_CLASS_TEXT
                binding.edtText.filters = arrayOf<InputFilter>(LengthFilter(40))
            }
        }

        binding.btnAllow.setOnClickListener {
            val value = binding.edtText.text.toString()
            if (value.isEmpty())
                toast("Please enter value")
            else if (stMessage.equals("whatsapp")) {
                if (value.length != 11 || !value.startsWith("03"))
                    toast("Please enter valid whatsapp#")
                else{
                    onclick.changeValue(value)
                    dialog.dismiss()
                }
            } else if (stMessage.equals("email")) {
                if (!Patterns.EMAIL_ADDRESS.matcher(value).matches())
                    toast("Please enter valid email address")
                else{
                    onclick.changeValue(value)
                    dialog.dismiss()
                }
            } else {
                onclick.changeValue(value)
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    interface onclick {
        fun changeValue(value: String)
    }
}