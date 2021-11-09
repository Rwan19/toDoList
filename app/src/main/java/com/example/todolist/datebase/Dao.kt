package com.example.todolist.datebase

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import java.util.*

@Dao
interface Dao {
    @Query( "SELECT * FROM ToDoListInfo")
    fun getAllToDoList():LiveData<List<ToDoListInfo>>
    @Query("SELECT* FROM ToDoListInfo WHERE id =(:id)")
    fun getToDoList(id:UUID):LiveData<List<ToDoListInfo?>>
    @Update
    fun updateToDoList(toDoListInfo: ToDoListInfo)
    @Insert
    fun addToDoLit(toDoListInfo: ToDoListInfo)
    @Delete
    fun delToDoList(toDoListInfo: ToDoListInfo)
}