package com.aash.vetsystem.fragments.profile

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.aash.vetsystem.R
import com.aash.vetsystem.databinding.AlertDialogBinding
import com.aash.vetsystem.databinding.ChangeDialogBinding
import com.aash.vetsystem.utils.DataHandler
import com.aash.vetsystem.utils.HelperClass
import com.aash.vetsystem.utils.UtilKotlin
import com.aash.vetsystem.databinding.FragmentMyProfileBinding
import com.aash.vetsystem.fragments.BaseFragment
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener


class myProfileFragment : BaseFragment() {
    lateinit var binding: FragmentMyProfileBinding
    lateinit var bitmap: Bitmap
    lateinit var helperClass: HelperClass
    lateinit var base64Profile: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_profile, container, false)

        helperClass = HelperClass()
        initActivityView(false, "My Profile")
        viewsetting()
        clickListener()

        return binding.root

    }

    private fun clickListener() {
        binding.profileImage.setOnClickListener {
            checkPermissionForGallery()
        }
        binding.txtChangePassword.setOnClickListener {
            showAlertChange("password")
        }
        binding.txtChangeEmail.setOnClickListener {
            showAlertChange("email")
        }
        binding.txtChangeWhatsapp.setOnClickListener {
            showAlertChange("whatsapp")
        }
    }

    private fun viewsetting() {
        bitmap = helperClass.base64ToBitmap(DataHandler.userModel.profileImage)
        binding.user = DataHandler.userModel
        binding.profileImage.setImageBitmap(bitmap)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
//            type= it.getString("type" , "")
        }
    }

    private fun checkPermissionForGallery() {
        Dexter.withContext(requireContext())
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.let {
                        if (report.areAllPermissionsGranted()) {
                            chooseImageFromGallery()
                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied) {
                            showPermissionDeniedDialog("Application need Storage  Permission for this service.You can grant them in app settings")
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }
            })
            .check()
    }

    private fun chooseImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        galleryLauncher.launch(intent)
    }
    private var galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val strUri = data?.data
                var imageBitmap = strUri?.let { UtilKotlin.uriToBitmap(requireContext(), it) }
                imageBitmap = imageBitmap?.let { UtilKotlin.getResizedBitmap(it, 600, 600) }
                binding.profileImage.setImageBitmap(imageBitmap)
                base64Profile = helperClass.bitmapToBase64(imageBitmap)
                DataHandler.userModel.profileImage = base64Profile
            }
        }

    fun showAlertChange(stMessage: String?) {
        val binding = ChangeDialogBinding.inflate(layoutInflater)
        val dialog = Dialog(requireActivity())
        dialog.setCancelable(true)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(binding.root)
        when(stMessage){
            "password"->{
                binding.txtTitle.text = "Change Password"
            }
            "email"->{
                binding.txtTitle.text = "Add/Change Email"
            }
            "whatsapp"->{
                binding.txtTitle.text = "Change Whatsapp"
            }
        }

        binding.btnAllow.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }
}