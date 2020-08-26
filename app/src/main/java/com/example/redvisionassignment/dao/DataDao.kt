package com.example.redvisionassignment.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.redvisionassignment.model.Data

@Dao
interface DataDao {

    @Query("SELECT * from Data")
    fun getAllItems() : LiveData<List<Data>>

    @Insert
    suspend fun insert(todo : Data)

    @Update
    suspend fun update(todo: Data)

    @Delete
    suspend fun delete(todo: Data)

}