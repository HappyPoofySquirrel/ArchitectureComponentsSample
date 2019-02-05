package com.example.guyverhopkins.task.ui.details

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.guyverhopkins.task.R
import com.example.guyverhopkins.task.common.RepositoryProvider
import com.example.guyverhopkins.task.core.network.MainNetworkImpl
import com.example.guyverhopkins.task.models.Task
import com.example.guyverhopkins.task.ui.edit.EditTaskActivity
import com.example.guyverhopkins.task.ui.list.TaskListActivity
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    companion object {
        fun startForResult(context: AppCompatActivity, taskId: Int) {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(TaskListActivity.TASKID_BUNDLE_KEY, taskId)
            context.startActivityForResult(intent, 1)
        }
    }

    private var task = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        val bundle = intent.extras
        task = bundle!!.getInt(TaskListActivity.TASKID_BUNDLE_KEY)

        val repo = RepositoryProvider.instance.getTaskRepository(MainNetworkImpl, this.application)
        val viewModel = ViewModelProviders.of(this, DetailsViewModelFactory(task, repo)).get(DetailsViewModel::class.java)
        viewModel.task.observe(this, Observer {
            updateView(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_details, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.edit_button) {
            EditTaskActivity.startWithEditResult(this, task)
            return true
        } else if (id == R.id.home) {
            val endIntent = Intent()
            val bundle = Bundle()
            bundle.putInt(TaskListActivity.TASKID_BUNDLE_KEY, task)
            endIntent.putExtras(bundle)
            setResult(-1, endIntent)
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun updateView(task: Task) {
        title_tv.text = task.title
        date_picker_tv.text = task.date
        task_tv.text = task.task
    }
}
