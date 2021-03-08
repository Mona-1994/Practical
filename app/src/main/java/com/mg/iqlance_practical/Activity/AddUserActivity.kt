package com.mg.iqlance_practical.Activity

import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import com.mg.iqlance_practical.Constant
import com.mg.iqlance_practical.DataBase
import com.mg.iqlance_practical.R
import com.mg.iqlance_practical.Student
import kotlinx.android.synthetic.main.activity_add_user.*
import kotlinx.android.synthetic.main.dialog_upload_profile.*
import java.io.ByteArrayOutputStream
import java.io.IOException

class AddUserActivity : AppCompatActivity() {
    val PICK_IMAGE_CAMERA = 1
    val PICK_IMAGE_GALLERY = 2

    private var image: ByteArray? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        Constant.address = ""

        var students = intent.getSerializableExtra("data") as? Student
        var isFrom = intent.getBooleanExtra("isFrom", false)


        val bitmap = students?.image?.size?.let {
            BitmapFactory.decodeByteArray(
                students.image,
                0,
                it
            )
        }

        if (bitmap != null) {
            ivStudentImage.setImageBitmap(bitmap)
        }

        edtStudentName.setText(students?.name)
        edtStudentNumber.setText(students?.phone_number)
        edtStudentEmail.setText(students?.email)
        edtStudentAddress.setText(students?.address)

       if (isFrom) {
            btnSubmit.visibility = View.GONE
            btnUpdate.visibility = View.VISIBLE

        }

        ivLocation.setOnClickListener({
            startActivity(Intent(applicationContext, MapsActivity::class.java))
        })


        ivStudentImage.setOnClickListener({
            ShowUploadProfileDialog(this)
        })


        btnUpdate.setOnClickListener({
            var student = Student(
                name = edtStudentName.text.toString(),
                phone_number = edtStudentNumber.text.toString(),
                email = edtStudentEmail.text.toString(),
                address = edtStudentAddress.text.toString(),
                image = image
            )

            DataBase.getDbInstance(applicationContext).student.updateStudent(student)
            finish()
        })


        btnSubmit.setOnClickListener({

            if (edtStudentName.text.toString().trim().equals("")) {
                Toast.makeText(applicationContext, "All fields required", Toast.LENGTH_LONG).show()
            } else
                if (edtStudentName.text.toString().trim().equals("")) {
                    Toast.makeText(applicationContext, "All fields required", Toast.LENGTH_LONG)
                        .show()
                } else
                    if (edtStudentName.text.toString().trim().equals("")) {
                        Toast.makeText(applicationContext, "All fields required", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        var student = Student(
                            name = edtStudentName.text.toString(),
                            phone_number = edtStudentNumber.text.toString(),
                            email = edtStudentEmail.text.toString(),
                            address = edtStudentAddress.text.toString()
                            , image = image
                        )

                        DataBase.getDbInstance(applicationContext).student.insertStudent(student)

                        startActivity(Intent(applicationContext, AllStudentActivity::class.java))

                        finish()
                    }

        })
    }

    override fun onResume() {
        super.onResume()

        if (!Constant.address.equals(""))
            edtStudentAddress.setText(Constant.address)
    }


    fun ShowUploadProfileDialog(mactivity: Context) {
        val npDialog = Dialog(mactivity)
        npDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        npDialog.setContentView(R.layout.dialog_upload_profile)
        npDialog.window!!.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        npDialog.window!!.setGravity(Gravity.CENTER)
        npDialog.window!!.decorView.setBackgroundResource(android.R.color.transparent)
        npDialog.window?.setBackgroundDrawableResource(R.color.transperent)
        npDialog.window!!.setDimAmount(0.0f)
        npDialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
         npDialog.setCancelable(true)

        val tvCamera = npDialog.tvCamera
        tvCamera.setOnClickListener(View.OnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, PICK_IMAGE_CAMERA)
            npDialog.dismiss()
        })
        val tvGellary = npDialog.tvGellary
        tvGellary.setOnClickListener(View.OnClickListener {
            npDialog.dismiss()
            val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY)
        })
        val tvCancel = npDialog.tvCancel
        tvCancel.setOnClickListener(View.OnClickListener { npDialog.dismiss() })
        if (!npDialog.isShowing) {
            npDialog.show()
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        System.gc()

        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE_CAMERA) {


            val bitmap = data!!.extras!!.get("data") as Bitmap?
            ivStudentImage.setImageBitmap(bitmap)

            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap!!.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)

            val bytes = byteArrayOutputStream.toByteArray()
            image = bytes


        } else if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE_GALLERY) {
            val selectedImage = data!!.data
            var bitmap: Bitmap? = null
            try {
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage)
            } catch (e: IOException) {
                e.printStackTrace()
            }

            ivStudentImage.setImageBitmap(bitmap)

            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap!!.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)

            val bytes = byteArrayOutputStream.toByteArray()
            image = bytes
        }
    }
}
