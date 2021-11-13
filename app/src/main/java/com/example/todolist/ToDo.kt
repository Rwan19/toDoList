package com.example.todolist

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.craimFragment.TODO_ID
import com.example.todolist.craimFragment.ToDoList
import com.example.todolist.datebase.ToDoListInfo
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

const val ToDo_DATE_KEY="toDoDate"
const val dateFormat="dd/mm/yyyy"
const val UPDATE_BUTTON="update enable"
const val SAVE_BUTTON="save enable"

class ToDo : Fragment(),DatePickerFragment.DatePickerCallback ,DialogFragment(){

    private lateinit var toDoList: ToDoListInfo
    private lateinit var taskTitle: EditText
    private lateinit var taskDetails: EditText
    private lateinit var dateBtn: Button
    private lateinit var isSolvedCh: CheckBox
    private lateinit var saveBtn:Button
    private lateinit var deletBtn:Button
    private lateinit var updateBtn:Button

    private val toDoListView by lazy { ViewModelProvider(this).get(ToDoViewModel::class.java) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toDoList = ToDoListInfo()

            val toDoID = arguments?.getSerializable(TODO_ID) as? UUID
        if (toDoID != null) {
            toDoListView.loadToDo(toDoID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_to_do_list, container, false)
        taskTitle = view.findViewById(R.id.title_task)
        taskDetails = view.findViewById(R.id.task_detailPT)
        dateBtn = view.findViewById(R.id.dateEnd_Btn)
        saveBtn=view.findViewById(R.id.save_Btn)
            deletBtn=view.findViewById(R.id.delet_Btn)
            updateBtn=view.findViewById(R.id.update_Btn)


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

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                toDoList.titleTask = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }

        val descriptionWatcher=object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                toDoList.descriptionTask = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }

        taskTitle.addTextChangedListener(textWatcher)
        taskDetails.addTextChangedListener(descriptionWatcher)

        saveBtn.setOnClickListener {
            toDoListView.add(toDoList)
            val fragment = ToDoList()
            activity?.let {
                it.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container1, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
        deletBtn.setOnClickListener {
            toDoListView.del(toDoList)
            val fragment = ToDoList()
            activity?.let {
                it.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container1, fragment)
                    .commit()
            }
        }
        updateBtn.setOnClickListener {
            toDoListView.update(toDoList)
            val fragment = ToDoList()
            activity?.let {
                it.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container1, fragment)
                    .commit()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toDoListView.toDoliveData.observe(viewLifecycleOwner,androidx.lifecycle.Observer {
    it?.let {
        toDoList = it
        taskTitle.setText(it.titleTask)
        dateBtn.text = it.date.toString()

    }
        })
    }

    override fun onDateSelected(date: Date) {
        toDoList.date=date
        dateBtn.text= DateFormat.format(dateFormat,toDoList.date)
    }
}