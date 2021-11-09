package com.example.todolist.craimFragment

import androidx.lifecycle.ViewModel
import com.example.todolist.datebase.ToDoListInfo
import com.example.todolist.datebase.ToDoListRebo

class ToDoListView:ViewModel() {
    val toDoRepositry=ToDoListRebo.get()
    val liveDateToDo=toDoRepositry.getAllToDoList()
    fun add(toDoListInfo: ToDoListInfo){
        toDoRepositry.addToDoLit(toDoListInfo)
    }
    fun del(toDoListInfo: ToDoListInfo){
        toDoRepositry.delToDoList(toDoListInfo)
    }
}