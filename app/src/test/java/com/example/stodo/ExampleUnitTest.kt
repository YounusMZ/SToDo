package com.example.stodo

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
/*
    @Test
    fun ItemsRepositoryDeleteMethod(){
        val newList = ArrayList<String>(3)
        val readList = ArrayList<String>()
        newList.add("hello")
        newList.add("hello1")
        newList.add("hello2")

        val itemsRepository = ItemsRepository(null)
        itemsRepository.writeToStorage(newList)
        itemsRepository.deleteItemFromList("hello")
        readList.addAll(itemsRepository.readFromStorage())
        assertNotEquals(newList, readList)
    }
    */
}