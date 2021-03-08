package com.mg.iqlance_practical

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Student")
class Student(
    @PrimaryKey(autoGenerate = true)
    val id:Int? = null,
    val name: String,
    val phone_number: String = "",
    val email: String = "",
    val address: String = "",
    val image: ByteArray?


) : Serializable{

}
