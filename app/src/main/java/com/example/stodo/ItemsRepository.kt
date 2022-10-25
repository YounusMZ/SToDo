package com.example.stodo

import android.content.Context
import android.os.Build
import android.os.Environment
import java.io.*


class ItemsRepository(fileName: String?, context: Context) {

    private val rootDir: File = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        Environment.getExternalStoragePublicDirectory("Documents")
    }
    else{
        context.applicationContext.filesDir
    }

    private var fileName: String = if(fileName != null){
        "Fragment" + fileName + ".txt"
    }
    else{
        "Fragment.txt"
    }

    private var listOfItems : ArrayList<String> = readFromStorage()


    fun getList() : ArrayList<String>{
        return listOfItems
    }

    fun writeList(){
        writeToStorage(listOfItems)
    }

    private fun writeToStorage(folderList: ArrayList<String>) {

        val directory = File(rootDir, "ToDO")
        val foldersList = File(directory, fileName)

        if (!directory.exists()) {
            directory.mkdirs()
        }
        try {
            foldersList.createNewFile()
            val fileWriter = FileWriter(foldersList)
            val bufferedWriter = BufferedWriter(fileWriter)
            for(folderNameIndex in 0 until (folderList.size)){
                bufferedWriter.write(folderList[folderNameIndex])
                bufferedWriter.flush()
                bufferedWriter.newLine()
            }
            bufferedWriter.close()
            fileWriter.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun readFromStorage(): ArrayList<String> {
        val folderList = ArrayList<String>()
        val directory = File(rootDir, "ToDO")
        val foldersList = File(directory, fileName)
        if (foldersList.exists()) {
            try {
                val folderReader = FileReader(foldersList)
                val bufferedReader = BufferedReader(folderReader)
                bufferedReader.useLines { lines ->
                    for (line in lines){
                        folderList.add(line)
                    }
                }
                bufferedReader.close()
                folderReader.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return folderList
    }

    fun deleteItemFromList(position: Int) {
        var tempPosition = 0
        val temp = ArrayList<String>()
        val directory = File(rootDir, "ToDO")
        val foldersList = File(directory, fileName)

        if (foldersList.exists()) {
            try {
                val folderReader = FileReader(foldersList)
                val bufferedReader = BufferedReader(folderReader)

                bufferedReader.useLines { lines ->
                    for (line in lines){
                        if(tempPosition != position) {
                            temp.add(line)
                        }
                        tempPosition++
                    }
                    if(tempPosition == 0){
                        for(index in 0 until (position - 1)) {
                            temp.add("")
                        }
                    }
                }
                foldersList.delete()
                listOfItems.clear()
                listOfItems.addAll(temp)
                writeToStorage(temp)

                bufferedReader.close()
                folderReader.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}