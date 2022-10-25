package com.example.stodo

import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.example.stodo.R
import kotlin.math.max

class ToDoArrangement(listItems: ArrayList<String>) : RecyclerView.Adapter<ToDoArrangement.ToDoViewHolder>(){

    private val items : ArrayList<String> = listItems
    private lateinit var newlistener: CustomNewListener
    private var random = 0

    class ToDoViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val editText: EditText = view.findViewById(R.id.list_edit)
        val bullet: TextView = view.findViewById(R.id.bullet)
        val deleteListItem: AppCompatButton = view.findViewById(R.id.deleteListButton)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ToDoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todolistarrangement, parent, false)
        return ToDoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return max(0, items.size)
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {

        holder.editText.text = Editable.Factory().newEditable(items[position])

        holder.editText.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                val currentIndex = holder.absoluteAdapterPosition
                items[currentIndex] = holder.editText.text.toString()
                newlistener.onSet()
            }
        })

        holder.bullet.setOnClickListener {
            if(random > 3){
                random = 0
            }
            when(random){
                0 -> holder.bullet.setTextColor(Color.GRAY)
                1 -> holder.bullet.setTextColor(Color.RED)
                2 -> holder.bullet.setTextColor(Color.GREEN)
                3 -> holder.bullet.setTextColor(Color.BLUE)
            }
            random += 1
        }

        holder.deleteListItem.setOnClickListener {
            val currentPosition = holder.absoluteAdapterPosition
            holder.bullet.setTextColor(Color.BLACK)
            newlistener.onRemove(currentPosition)
        }
    }

    fun setCustomListener(customNewListener: CustomNewListener){
        newlistener = customNewListener
    }

    interface CustomNewListener {
        fun onSet()
        fun onRemove(position: Int)
    }
}