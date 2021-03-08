package com.mg.iqlance_practical

import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Student::class], version = 1)
abstract class DataBase : RoomDatabase() {
    abstract val student: StudentDao

    companion object {

        internal var database: DataBase? = null

        fun getDbInstance(context: Context): DataBase {
            if (database == null) {
                database = Room.databaseBuilder(context, DataBase::class.java, "MyDb")
                    .allowMainThreadQueries().build()
                return database as DataBase
            }

            return database as DataBase
        }
    }
}
