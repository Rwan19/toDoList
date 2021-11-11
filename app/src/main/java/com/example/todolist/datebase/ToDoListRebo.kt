package com.example.todolist.datebase

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.todolist.datebase.ToDoDataBase
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME="ToDoList_database"
class ToDoListRebo private constructor(context: Context) {

    private val database: ToDoDataBase = Room.databaseBuilder(
        context.applicationContext,
        ToDoDataBase::class.java,
        DATABASE_NAME
    ).build()

    private val dao = database.dao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getAllToDoList(): LiveData<List<ToDoListInfo>> {
        return dao.getAllToDoList()
    }

    fun getToDoList(id: UUID): LiveData<ToDoListInfo?> {
        return dao.getToDoList(id)
    }

    fun updateToDoList(toDoListInfo: ToDoListInfo) {
        executor.execute { dao.updateToDoList(toDoListInfo) }
    }

    fun addToDoLit(toDoListInfo: ToDoListInfo) {
        executor.execute { dao.addToDoLit(toDoListInfo) }
    }

    fun delToDoList(toDoListInfo: ToDoListInfo) {
        executor.execute { dao.delToDoList(toDoListInfo) }
    }

    companion object {
        var INSTANCE: ToDoListRebo? = null

        fun initialize(context: Context)// يقولي مين طلب الانشلايز
        {
            if (INSTANCE == null) {// اذا كان null او اح مسوي منه قبل يتاكد
                INSTANCE = ToDoListRebo(context)
            }
        }

        fun get(): ToDoListRebo {
            return INSTANCE ?: throw IllegalStateException(" the list must be initialize")
        }

    }
}