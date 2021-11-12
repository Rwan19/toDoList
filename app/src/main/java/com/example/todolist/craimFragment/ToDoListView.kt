package com.example.todolist.craimFragment

import androidx.lifecycle.ViewModel
import com.example.todolist.datebase.ToDoListInfo
import com.example.todolist.datebase.ToDoListRebo
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.*

class ToDoListView:ViewModel() {
    val toDoRepositry=ToDoListRebo.get()
    val liveDateToDo=toDoRepositry.getAllToDoList()





}