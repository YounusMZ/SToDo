package com.example.stodo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stodo.R


class PagerFragmentOne() : Fragment() {
    private lateinit var recyclerView : RecyclerView
    private lateinit var recyclerViewAdapter : ToDoArrangement


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pager_one, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentFragmentNumber = this.arguments?.getInt("FragNumber")
        val itemsRepository = ItemsRepository(currentFragmentNumber.toString(), this.requireContext())
        val listOfItems : ArrayList<String> = itemsRepository.getList()
        val buttonNewEditText : Button = view.findViewById(R.id.addNewEditButton)

        recyclerViewAdapter = ToDoArrangement(listOfItems)
        recyclerView = view.findViewById(R.id.recylerView)
        recyclerView.layoutManager = LinearLayoutManager(view.context)

        recyclerViewAdapter.setCustomListener(object : ToDoArrangement.CustomNewListener{
            override fun onSet() {
                itemsRepository.writeList()
            }

            override fun onRemove(position: Int) {
                itemsRepository.deleteItemFromList(position)
                recyclerViewAdapter.notifyDataSetChanged()
            }
        })

        recyclerView.adapter = recyclerViewAdapter

        buttonNewEditText.setOnClickListener {
            listOfItems.add("")
            recyclerViewAdapter.notifyItemInserted(listOfItems.lastIndex)
        }
    }

    companion object {
        fun newInstance() =
            PagerFragmentOne()
    }
}