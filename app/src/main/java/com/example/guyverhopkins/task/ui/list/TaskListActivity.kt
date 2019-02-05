package com.example.guyverhopkins.task.ui.list

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.guyverhopkins.task.R
import com.example.guyverhopkins.task.common.RepositoryProvider
import com.example.guyverhopkins.task.core.AppDatabase
import com.example.guyverhopkins.task.core.network.MainNetworkImpl
import com.example.guyverhopkins.task.ui.details.DetailsActivity
import com.example.guyverhopkins.task.ui.edit.EditTaskActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_task_list.*

class TaskListActivity : AppCompatActivity(), RecyclerViewTaskAdapter.ItemClickListener {

    private lateinit var viewModel: TasksViewModel

    companion object {
        const val TASKID_BUNDLE_KEY = "taskId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)

        AppDatabase.getAppDataBase(applicationContext)

        val recyclerViewTaskAdapter = RecyclerViewTaskAdapter()
        recyclerViewTaskAdapter.setItemClickListener(this)
        task_recyclerview.adapter = recyclerViewTaskAdapter
        task_recyclerview.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        add_button.setOnClickListener { EditTaskActivity.startWithNewResult(this) }

        val taskRepository = RepositoryProvider.instance.getTaskRepository(MainNetworkImpl, this.application)

        viewModel = ViewModelProviders.of(this, TaskViewModelFactory(taskRepository)).get(TasksViewModel::class.java)

        viewModel.getTasks().observe(this, Observer { tasks ->
            val adapter = task_recyclerview.adapter as RecyclerViewTaskAdapter
            adapter.setItemClickListener(this)
            adapter.submitList(tasks)
        })

        viewModel.state.observe(this, Observer { state ->
            when {
                state.isLoading -> pg.visibility = View.VISIBLE
                !state.isLoading -> pg.visibility = View.GONE
            }
        })

        viewModel.snackBar.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                val snackbar = Snackbar.make(activity_main, it, Snackbar.LENGTH_LONG)
                snackbar.show()
            }
        })

        viewModel.goToEdit.observe(this, Observer {
            it.let {
                DetailsActivity.startForResult(this, it)
            }
        })
    }

    override fun onTaskSelected(position: Int) {
        viewModel.onTaskPressed(position)
    }
}
