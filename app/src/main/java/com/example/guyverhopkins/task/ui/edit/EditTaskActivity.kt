package com.example.guyverhopkins.task.ui.edit

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.guyverhopkins.task.R
import com.example.guyverhopkins.task.common.RepositoryProvider
import com.example.guyverhopkins.task.core.network.MainNetworkImpl
import com.example.guyverhopkins.task.models.Task
import com.example.guyverhopkins.task.ui.list.TaskListActivity
import kotlinx.android.synthetic.main.activity_edit_task.*
import java.text.SimpleDateFormat
import java.util.*

class EditTaskActivity : AppCompatActivity() {

    private var calendar = Calendar.getInstance()

    private var isNewTask: Boolean? = null

    private lateinit var viewModel: EditTaskViewModel

    private var task: Int? = null

    companion object {

        fun startWithNewResult(context: AppCompatActivity) {
            context.startActivityForResult(Intent(context, EditTaskActivity::class.java), 1)
        }

        fun startWithEditResult(context: AppCompatActivity, taskId: Int) {
            val intent = Intent(context, EditTaskActivity::class.java)
            intent.putExtra(TaskListActivity.TASKID_BUNDLE_KEY, taskId)
            context.startActivityForResult(intent, 1)
        }
    }

    private val titleChangeListener = object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            viewModel.onTitleChanged(s.toString())
        }

        override fun afterTextChanged(s: Editable) {}
    }

    private val taskChangeListener = object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            viewModel.onTaskChanged(s.toString())
        }

        override fun afterTextChanged(s: Editable) {}
    }


    private var date: DatePickerDialog.OnDateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        onDateChanged()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        title_et.addTextChangedListener(titleChangeListener)
        task_et.addTextChangedListener(taskChangeListener)

        val bundle = intent.extras
        task = bundle?.getInt(TaskListActivity.TASKID_BUNDLE_KEY)
        isNewTask = taskId == null
        val repo = RepositoryProvider.instance.getTaskRepository(MainNetworkImpl, this.application)

        viewModel = ViewModelProviders.of(this, EditTaskModelFactory(task, repo)).get(EditTaskViewModel::class.java)

        viewModel.taskld.observe(this, androidx.lifecycle.Observer {
            if (it == null) {
                finish()
            } else {
                updateViews(it)
            }
        })

        viewModel.hasSaved.observe(this, androidx.lifecycle.Observer {
            if (it) {
                finish()
            }
        })

        date_picker_et.setOnClickListener { DatePickerDialog(this@EditTaskActivity, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show() }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (isNewTask!!) {
            menuInflater.inflate(R.menu.toolbar_edit_new, menu)
        } else {
            menuInflater.inflate(R.menu.toolbar_edit, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.save_button) {
            viewModel.saveTask()
            return true
        } else if (id == R.id.delete_task) {
            viewModel.deleteTask()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onDateChanged() {
        val format = "MM/dd/yyyy"
        val sdf = SimpleDateFormat(format, Locale.US)
        val date = sdf.format(calendar.time)
        date_picker_et.setText(date)
        viewModel.onDateChanged(date)
    }

    private fun updateViews(task: Task) {
        title_et.setText(task.title)
        date_picker_et.setText(task.date.toString())
        task_et.setText(task.task)
    }
}
