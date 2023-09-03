package com.example.realtodolist

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBaseHandler(context: Context) :
    SQLiteOpenHelper(context, NAME, null, VERSION){

    companion object{
        private val VERSION: Int = 1
        private val NAME: String  = "toDoListDatabase"
        private val TODO_TABLE: String = "todo"
        private val ID:String = "id"
        private val TASK:String = "task"
        private val STATUS :String = "status"

        private lateinit var db: SQLiteDatabase

    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTodoTableSql =
            ("CREATE TABLE " + TODO_TABLE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TASK + " TEXT, "
                    + STATUS + " INTEGER)")
        db.execSQL(createTodoTableSql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TODO_TABLE")
        if (db != null) {
            onCreate(db)
        }
    }

    fun openDatabase() {
        db = this.writableDatabase
    }

    fun insertTask(task: Todo){
        val values = ContentValues()
        values.put(TASK, task.todo_task)
        values.put(STATUS, 0)
        val db = this.writableDatabase
        db.insert(TODO_TABLE, null, values)

    }

    @SuppressLint("Range")
    fun getAllTasks(): List<Todo>{
        val db = this.readableDatabase
        val cursor = db.query(TODO_TABLE, null, null, null, null, null, null, null)
        val taskList = ArrayList<Todo>()
        with(cursor) {
            while (moveToNext()) {
                val todoID = cursor.getInt(cursor.getColumnIndex(ID))
                val todoTask = cursor.getString(cursor.getColumnIndex(TASK))
                val todoChecked = cursor.getInt(cursor.getColumnIndex(STATUS))
                val task = Todo(todoID, todoTask, todoChecked)
                taskList.add(task)
            }
        }
        cursor.close()
        return taskList
    }



    fun updateStatus(id:Int, status: Int){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(STATUS,status)
        db.update(TODO_TABLE, values, "$ID= ?", arrayOf(id.toString()))

    }

    fun updateTask(id: Int, task: String){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(TASK,task)
        db.update(TODO_TABLE, values, "$ID= ?", arrayOf(id.toString()))

    }

    fun deleteTask(id: Int){
        val db = this.writableDatabase
        db.delete(TODO_TABLE, "$ID= ?", arrayOf(id.toString()))

    }


}

