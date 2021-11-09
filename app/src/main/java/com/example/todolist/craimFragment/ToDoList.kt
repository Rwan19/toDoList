package com.example.todolist.craimFragment

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.ToDo
import com.example.todolist.ToDo_DATE_KEY
import com.example.todolist.datebase.ToDoListInfo


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

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.new_menu ->{
                val toDoListInfo=ToDoListInfo()
                toDoListView.add(toDoListInfo)
                val args=Bundle()
                args.putSerializable(ToDo_DATE_KEY,toDoListInfo.id)
                val fragment= ToDo()
                fragment.arguments=args
                activity?.let {
                    it.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_containerView,fragment)
                        .addToBackStack(null)
                        .commit()
                }
                Log.d("rwan","hi")
                true
            }
            else -> super.onContextItemSelected(item)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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


    private fun updateUI(toDo: List<ToDoListInfo>){
        val todoAdabter=Adapter(toDo)
        toDoRV.adapter=todoAdabter
    }

    private inner class ToDoVH(view: View):RecyclerView.ViewHolder(view),View.OnClickListener{
        private lateinit var toDoListInfo: ToDoListInfo
        val titleTextView:TextView=itemView.findViewById(R.id.titleToDo_item)
        val dateTextView:TextView=itemView.findViewById(R.id.dateToDo_item)
       // private val isSolvedImgV: ImageView =itemView.findViewById(R.id....)


        init {
            itemView.setOnClickListener(this)
        }



        fun bind(toDoListInfo: ToDoListInfo){
            this.toDoListInfo=toDoListInfo
            titleTextView.text=toDoListInfo.title
            dateTextView.text= toDoListInfo.date.toString()
           // isSolvedImgV.visibility=if(crime.isSolved){
             //   View.VISIBLE
            //}else{
              //  View.GONE
            //}
        }

        override fun onClick(v: View?) {
            if(v==itemView){
                val args=Bundle()
                args.putSerializable(ToDo_DATE_KEY,toDoListInfo.id)
                val fragment= ToDo()
                fragment.arguments=args
                activity?.let {
                    it.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_containerView,fragment)
                        .commit()

                }
            }
        }

    }

    private inner class Adapter(var toDoA:List<ToDoListInfo>):RecyclerView.Adapter<ToDoVH>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoVH {
            val view=layoutInflater.inflate(R.layout.fragment_to_do_list,parent,false)
            return ToDoVH(view)

        }

        override fun onBindViewHolder(holder: ToDoVH, position: Int) {
            val toDo=toDoA[position]
            holder.bind(toDo)

        }

        override fun getItemCount(): Int =toDoA.size

        }

    }





