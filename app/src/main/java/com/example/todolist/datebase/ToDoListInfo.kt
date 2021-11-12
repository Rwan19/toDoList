package com.example.todolist.datebase



import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
@Entity
data class ToDoListInfo (
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    var titleTask:String="",
    var descriptionTask:String="",
    var date: Date = Date(),
    var dateEnd:Date=Date(),
    var isComplet:Boolean=false,
    //var expand : Boolean = false
        )


