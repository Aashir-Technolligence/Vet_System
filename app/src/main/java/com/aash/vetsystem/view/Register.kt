package com.aash.vetsystem.view

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.aash.vetsystem.R
import com.aash.vetsystem.utils.TootipWindow
import com.aash.vetsystem.databinding.ActivityRegisterBinding
import java.util.*


class Register : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    var accTypeArray: ArrayList<String>? = null
    var tipWindow: TootipWindow? = null
    var isPasswordVisible: Boolean = true
    var isRePasswordVisible: Boolean = true
    lateinit var userType: String
    lateinit var mobile: String
    lateinit var fullname: String
    lateinit var password: String
    lateinit var repassword: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_register)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        tipWindow = TootipWindow(applicationContext)
        dropDownSetup()
        clickListener()
    }

    private fun dropDownSetup() {
        val items = arrayOf(
            "User Type",
            "first",
            "second"
        )
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this, android.R.layout.simple_spinner_item, items
        )

        // spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        binding.spinnerAccountType.adapter = adapter
    }

    private fun clickListener() {
        binding.btnRegister.setOnClickListener {
            if(!checkFields()){
                //click
            }
        }

        binding.spinnerAccountType.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>,
                view: View?,
                position: Int,
                l: Long
            ) {
                if (adapterView.getItemAtPosition(position) == "User Type") {
                    userType = ""
                } else {
                    userType = accTypeArray?.get(position).toString()

                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        })

        binding.imgUsername.setOnClickListener {
            if (!tipWindow?.isTooltipShown()!!)
                tipWindow!!.showToolTip(
                    it, """Use at least 1 upper case character
Use at least 1 lower case character 
You may use any special character"""
                )
        }

        binding.password.setOnTouchListener(View.OnTouchListener { v, event ->
            val RIGHT = 2
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= binding.password.getRight() - binding.password.getCompoundDrawables()
                        .get(RIGHT).getBounds().width()
                ) {
                    val selection: Int = binding.password.getSelectionEnd()
                    if (isPasswordVisible) {
                        binding.password.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.off_visibility_password,
                            0
                        )
                        // hide PASDF
                        binding.password.setTransformationMethod(
                            PasswordTransformationMethod.getInstance()
                        )
                        isPasswordVisible = false
                    } else {
                        // set drawable image
                        binding.password.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.visible,
                            0
                        )
                        // show Password
                        binding.password.setTransformationMethod(
                            HideReturnsTransformationMethod.getInstance()
                        )
                        isPasswordVisible = true
                    }
                    binding.password.setSelection(selection)
                    return@OnTouchListener true
                }
            }
            false
        })

        binding.password2.setOnTouchListener(View.OnTouchListener { v, event ->
            val RIGHT = 2
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= binding.password2.getRight() - binding.password2.getCompoundDrawables()
                        .get(RIGHT).getBounds().width()
                ) {
                    val selection: Int = binding.password2.getSelectionEnd()
                    if (isRePasswordVisible) {
                        binding.password2.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.off_visibility_password,
                            0
                        )
                        // hide PASDF
                        binding.password2.setTransformationMethod(
                            PasswordTransformationMethod.getInstance()
                        )
                        isRePasswordVisible = false
                    } else {
                        // set drawable image
                        binding.password2.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.visible,
                            0
                        )
                        // show Password
                        binding.password2.setTransformationMethod(
                            HideReturnsTransformationMethod.getInstance()
                        )
                        isRePasswordVisible = true
                    }
                    binding.password2.setSelection(selection)
                    return@OnTouchListener true
                }
            }
            false
        })
    }

    private fun checkFields(): Boolean {
        var cancel = false
        var focusView: View? = null
        mobile = binding.phone.text.toString()
        fullname = binding.fullname.text.toString()
        password = binding.password.text.toString()
        repassword = binding.password2.text.toString()
        if (TextUtils.isEmpty(userType)) {
            ShowAlertDialog("Please select user type")
            focusView = binding.spinnerAccountType
            cancel = true
        }else if (TextUtils.isEmpty(fullname)) {
            ShowAlertDialog("Please enter full name")
            focusView = binding.fullname
            cancel = true
        } else if (TextUtils.isEmpty(mobile)) {
            ShowAlertDialog("Please enter mobile number")
            focusView = binding.phone
            cancel = true
        } else if (mobile.length<11 ||mobile.length>11 || mobile.subSequence(0,2) != "03") {
            ShowAlertDialog("Please enter valid mobile number")
            focusView = binding.phone
            cancel = true
        }else if (TextUtils.isEmpty(password)) {
            ShowAlertDialog("Please enter your password")
            focusView = binding.password
            cancel = true
        }  else if (password.length<6) {
            ShowAlertDialog("Please use atleast 6 characters")
            focusView = binding.password
            cancel = true
        } else if (TextUtils.isEmpty(repassword)) {
            ShowAlertDialog("Please re-enter your password")
            focusView = binding.password
            cancel = true
        } else if (password != repassword) {
            ShowAlertDialog("Password must be same")
            focusView = binding.password2
            cancel = true
        }
        if (cancel) {
            focusView!!.requestFocus()
        }
        return cancel
    }

    protected fun ShowAlertDialog(stMessage: String?) {
        val dialog = Dialog(this@Register)
        dialog.setCancelable(true)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val view: View = layoutInflater.inflate(R.layout.alert_dialog, null)
        dialog.setContentView(view)
        val Message: TextView
        val btnAllow: TextView
        Message = view.findViewById<View>(R.id.tvMessage) as TextView
        btnAllow = view.findViewById<View>(R.id.btnAllow) as TextView
        Message.text = stMessage
        btnAllow.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }
}

