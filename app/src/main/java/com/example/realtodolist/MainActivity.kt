package com.example.realtodolist

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.realtodolist.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), DialogCloseListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adaptor: ToDoAdaptor
    private lateinit var taskList: MutableList<Todo>
    private lateinit var db: DataBaseHandler
    private lateinit var tasksRecyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val rootView = binding.root
        setContentView(rootView)

        db =  DataBaseHandler(this)
        db.openDatabase()

        taskList = db.getAllTasks().toMutableList()
        taskList.reverse()
        adaptor = ToDoAdaptor(taskList,this)
        tasksRecyclerView = findViewById(R.id.rvTaskList)
        tasksRecyclerView.adapter = adaptor
        tasksRecyclerView.layoutManager = LinearLayoutManager(this)

        val itemTouchHelper = ItemTouchHelper(RecyclerItemTouchHelper(adaptor))
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView)

        adaptor.setTask(taskList)



        binding.fabAdd.setOnClickListener{
            val bottomFragment = AddNewTask()
            bottomFragment.show(supportFragmentManager,"TAG")
        }

    }

    override fun handleDialogClose(dialog: DialogInterface?) {
        taskList = db.getAllTasks().toMutableList()
        taskList.reverse()
        adaptor.setTask(taskList)
    }


}