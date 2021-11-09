package com.example.todolist

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import com.example.todolist.datebase.ToDoListInfo

const val ToDo_DATE_KEY="toDoDate"

class ToDo : Fragment() {

    private lateinit var toDoList: ToDoListInfo
    private lateinit var taskTitle: EditText
    private lateinit var taskDetails: EditText
    private lateinit var taskTime: TextView
    private lateinit var dateBtn: Button
    private lateinit var isSolvedCh: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_to_do_list, container, false)
        taskTitle = view.findViewById(R.id.title_task)
        taskDetails = view.findViewById(R.id.task_detailPT)
        taskTime = view.findViewById(R.id.time_TV)
        dateBtn = view.findViewById(R.id.time_Btn)
        isSolvedCh=view.findViewById(R.id.checkBox)

        dateBtn.apply {
            text = toDoList.date.toString()

        }
        return (view)
    }

    override fun onStart() {
        super.onStart()
        dateBtn.setOnClickListener {

            val args = Bundle()
            args.putSerializable(ToDo_DATE_KEY, toDoList.date)

            val datePicker = DatePickerFragment()
            datePicker.arguments = args
            datePicker.setTargetFragment(this, 0)
            datePicker.show(this.parentFragmentManager, "date")

        }
        val textWatcher=object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("rwan",s.toString())
                toDoList.title=s.toString()

            }

            override fun afterTextChanged(s: Editable?) {

            }

        }


    }


}