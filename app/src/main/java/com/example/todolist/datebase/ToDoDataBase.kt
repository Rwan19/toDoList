package com.example.todolist.datebase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.todolist.ToDo


@Database(entities = [ToDoListInfo::class],version = 1)
@TypeConverters(ToDoListTC::class)
abstract class ToDoDataBase:RoomDatabase() {
    abstract fun dao(): Dao
}
