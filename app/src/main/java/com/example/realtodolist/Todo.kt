package com.example.realtodolist

class Todo(
    private var id: Int?,
    private val title: String,
    private var isChecked: Int
){

    var todo_id: Int? = id
        get() {
            return id
        }
        set(value) {
            field = id
        }

    var todo_task: String = title
        set(value) {
            field = title
        }

    var todo_checked: Int = isChecked
        set(value) {
            field = isChecked
        }
}