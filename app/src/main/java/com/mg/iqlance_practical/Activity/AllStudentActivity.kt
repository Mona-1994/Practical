package com.mg.iqlance_practical.Activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.mg.iqlance_practical.DataBase

import com.mg.iqlance_practical.Student
import com.nikhilpanju.recyclerviewenhanced.OnActivityTouchListener
import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener
import kotlinx.android.synthetic.main.activity_all_student.*

import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.Toast

import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.View
import com.mg.iqlance_practical.R
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T





class AllStudentActivity : AppCompatActivity() {

    var adapter: AllStudentAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_student)

        rvAllStudent.layoutManager = LinearLayoutManager(applicationContext)

        val list: MutableList<Student> = DataBase.getDbInstance(applicationContext).student.allStudents

        Log.e("list", "" + list)

        adapter = AllStudentAdapter(applicationContext,list)
        rvAllStudent.adapter=adapter

    }

}
