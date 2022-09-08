package com.aash.vetsystem.fragments.setting

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.aash.vetsystem.Preferences.VetPreferences
import com.aash.vetsystem.databinding.FragmentAppSettingBinding
import com.aash.vetsystem.fragments.BaseFragment
import com.aash.vetsystem.utils.HelperClass
import com.aash.vetsystem.utils.UtilKotlin
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class AppSetting : BaseFragment() {
    lateinit var binding: FragmentAppSettingBinding
    lateinit var base64Profile: String
    lateinit var helperClass: HelperClass

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_app_setting, container, false)
        binding = FragmentAppSettingBinding.inflate(inflater , container ,false)
        helperClass = HelperClass()
        pref = VetPreferences(requireContext())
        initActivityView(false , "App Setting")
        clickListener()
        binding.description = pref.description.toString()
        binding.appname = pref.company.toString()
        binding.profileImage.setImageBitmap(helperClass.base64ToBitmap(pref.image.toString()))


        return binding.root
    }

    private fun clickListener() {
        binding.txtChangeAppName.setOnClickListener{
            val onclick = object : onclick{
                override fun changeValue(value: String) {
                    pref.company = value
                    binding.appname = value
                }
            }
            showAlertChange("appname" ,onclick)
        }

        binding.txtChangeDescription.setOnClickListener {
            val onclick  =object : onclick{
                override fun changeValue(value: String) {
                    pref.description = value
                    binding.description = value
                }
            }
            showAlertChange("description" , onclick)
        }

        binding.profileImage.setOnClickListener {
            checkPermissionForGallery()
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
                pref.image = base64Profile
            }
        }
}