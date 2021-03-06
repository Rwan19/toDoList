package com.example.todolist.craimFragment

import android.annotation.SuppressLint
import android.icu.text.MessageFormat.format
import android.os.Bundle
import android.text.format.DateFormat.format
import android.util.Log
import android.view.*
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.*
import com.example.todolist.datebase.ToDoListInfo
import java.lang.String.format
import java.text.DateFormat
import java.text.MessageFormat.format
import java.text.SimpleDateFormat
import java.util.*

const val TODO_ID = "UPDATE"
class ToDoList : Fragment() {
    private lateinit var toDoRV: RecyclerView
    private val toDoListView by lazy { ViewModelProvider(this).get(ToDoListView::class.java) }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_list_menu, menu)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_menu -> {

                val fragment = ToDo()
                activity?.let {
                    it.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container1, fragment)
                        .addToBackStack(null)
                        .commit()
                }
                true
            }

            else -> super.onContextItemSelected(item)
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_to_do_list_l, container, false)
        toDoRV = view.findViewById(R.id.toDo_item)
        val linearLM = LinearLayoutManager(context)
        toDoRV.layoutManager = linearLM
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toDoListView.liveDateToDo.observe(
            viewLifecycleOwner, Observer {
                updateUI(it)
            }
        )
    }
    private fun updateUI(toDo: List<ToDoListInfo>) {
        val todoAdabter = Adapter(toDo)
        toDoRV.adapter = todoAdabter
    }

    private inner class ToDoVH(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private lateinit var toDoListInfo: ToDoListInfo
        val titleTextView: TextView = itemView.findViewById(R.id.titleToDo_item)
        val dateTextView: TextView = itemView.findViewById(R.id.dateToDo_item)
        val isCompleet: TextView = view.findViewById(R.id.isCompleet)
        val taskOver:TextView=view.findViewById(R.id.task_time)

        init {
            itemView.setOnClickListener(this)
            dateTextView.setOnClickListener(this)
        }

        @SuppressLint("SimpleDateFormat")
        fun bind(toDoListInfo2: ToDoListInfo) {
            this.toDoListInfo = toDoListInfo2
            titleTextView.text = toDoListInfo2.titleTask
            var currentDate=Date()


            if (toDoListInfo2.date!=null) {
                if (currentDate.after(toDoListInfo2.date)) {
                    taskOver.visibility = View.VISIBLE
                } else {
                    taskOver.text = ""
                }
            }

            if (toDoListInfo2.date!=null) {
                dateTextView.text= DateFormat.getDateInstance().format(toDoListInfo2.date)
            }else{
                dateTextView.text=""
            }

        }
        override fun onClick(v: View?) {
            if (v == itemView) {
                val args = Bundle()
                args.putSerializable(TODO_ID, toDoListInfo.id)
                val fragment = ToDo()
                fragment.arguments = args
                activity?.let {
                    it.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container1, fragment)
                        .addToBackStack(null)
                        .commit()
                }
            }

        }
    }

    private inner class Adapter(var toDoA:List<ToDoListInfo>):RecyclerView.Adapter<ToDoVH>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoVH {
            val view=layoutInflater.inflate(R.layout.list_of_todo,parent,false)

            return ToDoVH(view)
        }

        override fun onBindViewHolder(holder: ToDoVH, position: Int) {
            val toDo=toDoA[position]
            holder.bind(toDo)
        }

        override fun getItemCount(): Int =toDoA.size
        }
    }