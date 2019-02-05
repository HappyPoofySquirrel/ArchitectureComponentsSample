package com.example.guyverhopkins.task.ui.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.example.guyverhopkins.task.entities.ITaskRepository
import com.example.guyverhopkins.task.models.Task
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

/**
 * Created 1/29/19.
 */
class TasksViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewmodel: TasksViewModel

    @Before
    fun setUp() {
    }

    @Test
    fun `my test`() {
        val repo = mock(ITaskRepository::class.java)
        val mList = mock(LiveData::class.java) as LiveData<MutableList<Task>>

        val task = Task(id = 1)
        val mutableList = mutableListOf(task)
        `when`(mList.value).thenReturn(mutableList)
        `when`(repo.getAll()).thenReturn(mList)

        viewmodel = TasksViewModel(repo)

        viewmodel.onTaskPressed(0)
        assertEquals(viewmodel.goToEdit.value, 1)
    }

    @Test
    fun `repo hit`() {
        val repo = mock(ITaskRepository::class.java)

        viewmodel = TasksViewModel(repo)

        verify(repo).getAll()
    }
}