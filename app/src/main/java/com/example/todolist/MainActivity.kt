package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todolist.craimFragment.ToDoList

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentFragment=// هل هيا موجودة او لا يبحث
            supportFragmentManager
                .findFragmentById(R.id.fragment_container1)

        if (currentFragment==null){
            val fragment= ToDoList()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container1,fragment)
                .commit()

        }
    }
}