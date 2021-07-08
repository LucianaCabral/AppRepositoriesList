package com.lucianacabral.applembretes.ui

import android.app.Activity
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.lucianacabral.applembretes.dataSource.TaskDataSource
import com.lucianacabral.applembretes.databinding.ActivityAddTaskBinding
import com.lucianacabral.applembretes.extensions.format
import com.lucianacabral.applembretes.extensions.text
import com.lucianacabral.applembretes.model.Task
import java.util.*

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        insertListeners()
    }
    private fun insertListeners() {
        binding.tilDate.editText?.setOnClickListener {
            //Log.e("TAG", "insertListeners: ")
            val dataPicker = MaterialDatePicker.Builder.datePicker().build()
            dataPicker.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) * -1
                binding.tilDate.text = Date(it + offset).format()
                // binding.tilDate.editText?.setText(Date(it).format())tb posso fazer isto direto
            }
            dataPicker.show(supportFragmentManager, "DATA_PICKER-TAG")
        }

        binding.tilHour.editText?.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()
            timePicker.addOnPositiveButtonClickListener {
                val minute = if (timePicker.minute in 0..9) "0${timePicker.minute}" else timePicker.minute
                val hour = if (timePicker.hour in 0..9) "0${timePicker.hour}" else timePicker.hour
                binding.tilHour.text = "$hour:$minute"

            }
            timePicker.show(supportFragmentManager, null)
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }

        binding.btnNewTask.setOnClickListener {

        }

        val task = Task(
            title = binding.tilTitle.text,
            date = binding.tilDate.text,
            hour = binding.tilHour.text
        )
        TaskDataSource.insertTask(task)
        Log.i(TAG, "insertListeners:" + TaskDataSource.getList())

        setResult(Activity.RESULT_OK)
        finish()
    }
}



