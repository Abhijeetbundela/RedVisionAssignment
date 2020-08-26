package com.example.redvisionassignment.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.redvisionassignment.R
import com.example.redvisionassignment.model.Data
import com.example.redvisionassignment.viewModel.DataVM
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {

    private lateinit var data: Data

    private var uri: Uri? = null

    private val dataViewModel by lazy { ViewModelProvider(this).get(DataVM::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        if (intent.hasExtra("isUpdate")) {
            submit_btn.text = "Update"

            data = intent.getParcelableExtra("data")!!

            Glide.with(this@RegistrationActivity)
                .load(Uri.parse(data.profileImage))
                .placeholder(R.drawable.default_user)
                .into(profile_image)

            register_name.editText!!.setText(data.name)
            register_contact.editText!!.setText(data.contact)
            register_email.editText!!.setText(data.email)
            register_address.editText!!.setText(data.address)
            register_profile_link.editText!!.setText(data.profileLink)

        }

//        data = intent.getParcelableExtra("isUpdate")!!
//
//        dataViewModel.update(data)

        profile_image.setOnClickListener {
            CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1, 1)
                .setCropShape(CropImageView.CropShape.OVAL)
                .start(this)
        }

        submit_btn.setOnClickListener {
            val name: String = register_name.editText!!.text.toString()
            val contact: String = register_contact.editText!!.text.toString()
            val email: String = register_email.editText!!.text.toString()
            val address: String = register_address.editText!!.text.toString()
            val profileLink: String = register_profile_link.editText!!.text.toString()

            when {
                name.isBlank() ->
                    Toast.makeText(
                        this@RegistrationActivity,
                        "Enter Name",
                        Toast.LENGTH_SHORT
                    ).show()

                contact.isBlank() || contact.length != 10 ->
                    Toast.makeText(
                        this@RegistrationActivity,
                        "Enter contact",
                        Toast.LENGTH_SHORT
                    ).show()

                email.isBlank() ->
                    Toast.makeText(
                        this@RegistrationActivity,
                        "Enter email",
                        Toast.LENGTH_SHORT
                    ).show()

                address.isBlank() ->
                    Toast.makeText(
                        this@RegistrationActivity,
                        "Enter address",
                        Toast.LENGTH_SHORT
                    ).show()

                profileLink.isBlank() ->
                    Toast.makeText(
                        this@RegistrationActivity,
                        "Enter profile link",
                        Toast.LENGTH_SHORT
                    ).show()

                else -> {
                    if (intent.hasExtra("isUpdate")) {
                        updateData()
                    } else {
                        saveData()
                    }

                }
            }

        }

    }

    private fun updateData() {

        val name: String = register_name.editText!!.text.toString()
        val contact: String = register_contact.editText!!.text.toString()
        val email: String = register_email.editText!!.text.toString()
        val address: String = register_address.editText!!.text.toString()
        val profileLink: String = register_profile_link.editText!!.text.toString()

        val data = Data(
            data.id, name, contact, email, address, profileLink, uri.toString(), false
        )

        Log.d("MYT", "Register $data")

        dataViewModel.update(data)

        startActivity(Intent(this@RegistrationActivity, HomeActivity::class.java))
        finish()

    }

    private fun saveData() {

        val name: String = register_name.editText!!.text.toString()
        val contact: String = register_contact.editText!!.text.toString()
        val email: String = register_email.editText!!.text.toString()
        val address: String = register_address.editText!!.text.toString()
        val profileLink: String = register_profile_link.editText!!.text.toString()

        val todo = Data(
            0, name, contact, email, address, profileLink, uri.toString(), false
        )
        dataViewModel.insert(todo)

        startActivity(Intent(this@RegistrationActivity, HomeActivity::class.java))
        finish()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            val result = CropImage.getActivityResult(data)

            if (resultCode == RESULT_OK) {
                uri = result.uri
                profile_image.setImageURI(uri)

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
                Toast.makeText(this@RegistrationActivity, error.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}