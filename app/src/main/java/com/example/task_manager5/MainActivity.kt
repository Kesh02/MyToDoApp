package com.example.task_manager5



import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task_manager5.Utils.DatabaseHandler
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.task_manager5.Adapters.ToDoAdapter
import com.example.task_manager5.Model.ToDoModel

import java.util.*

class MainActivity : AppCompatActivity(), DialogCloseListener {

    private lateinit var db: DatabaseHandler

    private lateinit var tasksRecyclerView: RecyclerView
    private lateinit var tasksAdapter: ToDoAdapter
    private lateinit var fab: FloatingActionButton

    private lateinit var taskList: MutableList<ToDoModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        db = DatabaseHandler(this)
        db.openDatabase()

        tasksRecyclerView = findViewById(R.id.tasksRecyclerView)
        tasksRecyclerView.layoutManager = LinearLayoutManager(this)
        tasksAdapter = ToDoAdapter(db, this@MainActivity)
        tasksRecyclerView.adapter = tasksAdapter

        val itemTouchHelper = ItemTouchHelper(RecyclerItemTouchHelper(tasksAdapter))
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView)

        fab = findViewById(R.id.fab)

        taskList = db.getAllTasks().toMutableList()
        taskList.reverse()

        tasksAdapter.setTasks(taskList)

        fab.setOnClickListener {
            AddNewTask.newInstance().show(supportFragmentManager, AddNewTask.TAG)
        }
    }

    override fun handleDialogClose(dialog: DialogInterface) {
        taskList = db.getAllTasks().toMutableList()
        taskList.reverse()
        tasksAdapter.setTasks(taskList)
        tasksAdapter.notifyDataSetChanged()
    }
}
