package com.example.stodo.ui.main

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val newFragment : ArrayList<Fragment> = ArrayList()
    val fragmentsInPager : MutableLiveData<ArrayList<Fragment>> = MutableLiveData<ArrayList<Fragment>>()

    fun createFirstFragmentInPager(){
        newFragment.add(Fragment())
        fragmentsInPager.value = newFragment
    }

    fun addFragmentsInPager(){
        newFragment.add(Fragment())
        fragmentsInPager.value = newFragment
    }

    fun deleteFragmentInPager(){
        if(newFragment.size > 0) {
            newFragment.removeAt(newFragment.size - 1)
            fragmentsInPager.value = newFragment
        }
    }
}