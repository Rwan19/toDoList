package com.example.todolist

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment:DialogFragment() {

    interface DatePickerCallback {
        fun onDateSelected(date: Date)
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val date = arguments?.getSerializable(ToDo_DATE_KEY) as? Date  // casting
        val calendar = Calendar.getInstance()

        if (date!=null){
            calendar.time = date
        }
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datelistener = DatePickerDialog.OnDateSetListener { _, year, month,dayOfMonth ->

            val resultDate = GregorianCalendar(year, month, day).time

            targetFragment?.let {

                (it as DatePickerCallback).onDateSelected(resultDate)

            }

        }

        return DatePickerDialog(

            requireContext(),
            datelistener,
            year,
            month,
            day

        )

    }
}