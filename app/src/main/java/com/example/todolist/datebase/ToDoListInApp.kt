package com.example.todolist.datebase

import android.app.Application


class ToDoListInApp:Application() {

    override fun onCreate() {
        super.onCreate()
        ToDoListRebo.initialize(this)
    }
}