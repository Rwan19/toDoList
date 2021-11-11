package com.example.todolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.todolist.datebase.ToDoListInfo
import com.example.todolist.datebase.ToDoListRebo
import java.util.*

class ToDoViewModel:ViewModel() {

    private val toDoListRebo=ToDoListRebo.get()
    private val toDoIdLiveData=MutableLiveData<UUID>()

    var toDoliveData:LiveData<ToDoListInfo?> =
            Transformations.switchMap(toDoIdLiveData){
                toDoListRebo.getToDoList(it)
            }
    fun loadToDo(toDoId:UUID){
        toDoIdLiveData.value=toDoId
    }
    fun add(toDoListInfo: ToDoListInfo){
        toDoListRebo.addToDoLit(toDoListInfo)
    }

    fun del(toDoListInfo: ToDoListInfo){
        toDoListRebo.delToDoList(toDoListInfo)
    }
    fun update(toDoListInfo: ToDoListInfo){
        toDoListRebo.updateToDoList(toDoListInfo)
    }


}