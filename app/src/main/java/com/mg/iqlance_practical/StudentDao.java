package com.mg.iqlance_practical;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface StudentDao {
    @Insert
    void insertStudent(Student model);

    @Update
    void updateStudent(Student model);

    @Delete
    void deleteStudent(Student model);

    @Query("select * from Student")
        List<Student> getAllStudents();

}
