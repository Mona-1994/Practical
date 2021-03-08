package com.mg.iqlance_practical.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mg.iqlance_practical.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        tvAddStudent.setOnClickListener({


            startActivity(Intent(applicationContext,AddUserActivity::class.java))


        })
        tvAllStudent.setOnClickListener({


            startActivity(Intent(applicationContext,AllStudentActivity::class.java))


        })

    }
}
