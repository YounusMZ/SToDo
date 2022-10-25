package com.example.stodo.ui.main

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.stodo.PagerFragmentOne
import com.example.stodo.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : Fragment() {


    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel: MainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        val swiper = view.findViewById<ViewPager2>(R.id.swipy)
        val tabLayoutSwiper = view.findViewById<TabLayout>(R.id.swipytab)
        val addFragmentButton = view.findViewById<AppCompatButton>(R.id.addButton)
        val deleteFragmentButton = view.findViewById<AppCompatButton>(R.id.deleteButton)

        addFragmentButton.setOnClickListener {
            viewModel.addFragmentsInPager()
        }
        deleteFragmentButton.setOnClickListener {
            viewModel.deleteFragmentInPager()
        }
        if(viewModel.fragmentsInPager.value == null){
            viewModel.createFirstFragmentInPager()
        }
        swiper.adapter = SwiperCollection(this)

        TabLayoutMediator(tabLayoutSwiper, swiper){tab, position ->
            tab.text = position.toString()
        }.attach()
    }
}


class SwiperCollection(val fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val viewModel: MainViewModel = ViewModelProvider(fragment.requireActivity())[MainViewModel::class.java]
    private var itemCount : ArrayList<Fragment> = ArrayList()

    init {
        val preferences : SharedPreferences  =  fragment.requireActivity().applicationContext.getSharedPreferences("application", Context.MODE_PRIVATE)
        val noOfFragments = preferences.getInt("noOfFragments", 0)

        if (noOfFragments > 0) {
            for (fragmentNo in 0 until noOfFragments) {
                viewModel.addFragmentsInPager()
            }
        }

        viewModel.fragmentsInPager.observe(fragment.requireActivity()) {
            val currentItemCount = itemCount.size
            itemCount = it
            if(itemCount.size > currentItemCount) {
                this.notifyItemInserted(itemCount.size)
            }
            else{
                this.notifyItemRemoved(currentItemCount)
            }
        }
    }

    override fun getItemCount(): Int {
        return itemCount.size
    }

    override fun createFragment(position: Int): Fragment {
        val newFragment : Fragment = PagerFragmentOne.newInstance()
        setFragmentNumber(itemCount.size - 1)
        val bundle = Bundle()
        bundle.putInt("FragNumber", position)
        newFragment.arguments = bundle
        return newFragment
    }

    private fun setFragmentNumber(noOfFragments: Int){
        val preferences : SharedPreferences  =  fragment.requireActivity().applicationContext.getSharedPreferences("application", Context.MODE_PRIVATE)
        val preferencesEdit: SharedPreferences.Editor =  preferences.edit()
        preferencesEdit.putInt("noOfFragments", noOfFragments)
        preferencesEdit.apply()
    }
}
