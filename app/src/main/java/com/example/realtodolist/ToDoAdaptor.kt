package com.example.realtodolist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView


class ToDoAdaptor (
    private var todos: MutableList<Todo>,
    private var activity: MainActivity
) : RecyclerView.Adapter<ToDoAdaptor.TodoViewHolder>() {
    private lateinit var db: DataBaseHandler


    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskCheckBox: CheckBox = itemView.findViewById(R.id.taskCheckBox)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_layout, parent, false)
        db =  DataBaseHandler(view.context)
        db.openDatabase()
        return TodoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return todos.size
    }

    fun getContext(): Context? {
        return activity
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        var item: Todo = todos[position]
        holder.taskCheckBox.text = item.todo_task
        holder.taskCheckBox.isChecked = toBoolean(item.todo_checked)
        holder.taskCheckBox.setOnClickListener {
            var updateChecked = 0
            if (item.todo_checked == 0) {
                updateChecked = 1
            }
            item.todo_id?.let { it1 -> db.updateStatus(it1,updateChecked) }
        }
    }

    private fun toBoolean(n: Int): Boolean {
        return n != 0;
    }

    fun setTask(todoList: MutableList<Todo>){
        this.todos = todoList
        notifyDataSetChanged()

    }

    fun deleteItem(position: Int) {
        val item = todos[position]
        item.todo_id?.let { db.deleteTask(it) }
        todos.removeAt(position)
        notifyItemRemoved(position)
    }

    fun editItem(position: Int) {
        val item = todos[position]
        val bundle = Bundle()
        item.todo_id?.let { bundle.putInt("id", it) }
        bundle.putString("task", item.todo_task)
        val fragment = AddNewTask()
        fragment.arguments = bundle
        fragment.show(activity.supportFragmentManager, AddNewTask.TAG)
    }




}