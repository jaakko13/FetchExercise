package com.example.fetchcodingexercise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.google.gson.Gson
import org.json.JSONObject
import java.net.URL
import java.nio.file.Files.find
import java.util.regex.Pattern
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    lateinit var showList: Button
    lateinit var dataList: ListView
    private var arrayAdapter: ArrayAdapter<Ids> ? = null
    lateinit var blanks: ToggleButton
    lateinit var listIdSort: ToggleButton
    lateinit var nameSort: ToggleButton
    var response: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showList = findViewById(R.id.showList)
        dataList = findViewById(R.id.dataList)
        blanks = findViewById(R.id.blanks)
        listIdSort = findViewById(R.id.listIdSort)
        nameSort = findViewById(R.id.nameSort)


        showList.setOnClickListener{
            thread {
                getData()
            }
        }

        blanks.setOnClickListener{
            if(blanks.isChecked == true && listIdSort.isChecked == false && nameSort.isChecked == false){
                getBlanks(response)
            }else if(blanks.isChecked == false && listIdSort.isChecked == true && nameSort.isChecked == false){
                sortListId(response)
            }else if(blanks.isChecked == false && listIdSort.isChecked == false && nameSort.isChecked == true){
                Toast.makeText(this, "Blank name filtering needs to be ON", Toast.LENGTH_SHORT).show()
                //sortNames(response)
            }else if (blanks.isChecked == true && listIdSort.isChecked == true && nameSort.isChecked == false){
                blanksAndListId(response)
            }else if(blanks.isChecked == true && listIdSort.isChecked == false && nameSort.isChecked == true){
                blanksAndNames(response)
            }else if (blanks.isChecked == false && listIdSort.isChecked == true && nameSort.isChecked == true){
                Toast.makeText(this, "Blank name filtering needs to be ON", Toast.LENGTH_SHORT).show()
            //listIdAndNames(response)
            }else if(blanks.isChecked == true && listIdSort.isChecked == true && nameSort.isChecked == true){
                allSorted(response)
            }else if(blanks.isChecked == false && listIdSort.isChecked == false && nameSort.isChecked == false){
                thread { getData() }
            }
        }

        listIdSort.setOnClickListener{
            if(blanks.isChecked == true && listIdSort.isChecked == false && nameSort.isChecked == false){
                getBlanks(response)
            }else if(blanks.isChecked == false && listIdSort.isChecked == true && nameSort.isChecked == false){
                sortListId(response)
            }else if(blanks.isChecked == false && listIdSort.isChecked == false && nameSort.isChecked == true){
                Toast.makeText(this, "Blank name filtering needs to be ON", Toast.LENGTH_SHORT).show()
                //sortNames(response)
            }else if (blanks.isChecked == true && listIdSort.isChecked == true && nameSort.isChecked == false){
                blanksAndListId(response)
            }else if(blanks.isChecked == true && listIdSort.isChecked == false && nameSort.isChecked == true){
                blanksAndNames(response)
            }else if (blanks.isChecked == false && listIdSort.isChecked == true && nameSort.isChecked == true){
                Toast.makeText(this, "Blank name filtering needs to be ON", Toast.LENGTH_SHORT).show()
                //listIdAndNames(response)
            }else if(blanks.isChecked == true && listIdSort.isChecked == true && nameSort.isChecked == true){
                allSorted(response)
            }else if(blanks.isChecked == false && listIdSort.isChecked == false && nameSort.isChecked == false){
                thread { getData() }
            }
        }

        nameSort.setOnClickListener{
            if(blanks.isChecked == true && listIdSort.isChecked == false && nameSort.isChecked == false){
                getBlanks(response)
            }else if(blanks.isChecked == false && listIdSort.isChecked == true && nameSort.isChecked == false){
                sortListId(response)
            }else if(blanks.isChecked == false && listIdSort.isChecked == false && nameSort.isChecked == true){
                Toast.makeText(this, "Blank name filtering needs to be ON", Toast.LENGTH_SHORT).show()
                //sortNames(response)
            }else if (blanks.isChecked == true && listIdSort.isChecked == true && nameSort.isChecked == false){
                blanksAndListId(response)
            }else if(blanks.isChecked == true && listIdSort.isChecked == false && nameSort.isChecked == true){
                blanksAndNames(response)
            }else if (blanks.isChecked == false && listIdSort.isChecked == true && nameSort.isChecked == true){
                Toast.makeText(this, "Blank name filtering needs to be ON", Toast.LENGTH_SHORT).show()
                //listIdAndNames(response)
            }else if(blanks.isChecked == true && listIdSort.isChecked == true && nameSort.isChecked == true){
                allSorted(response)
            }else if(blanks.isChecked == false && listIdSort.isChecked == false && nameSort.isChecked == false){
                thread { getData() }
            }
        }
    }

    //calls api and gets all json data
    private fun getData(){
        response = URL("https://fetch-hiring.s3.amazonaws.com/hiring.json").readText(Charsets.UTF_8)
        Log.d("msg", response!!)
        runOnUiThread { postExecute(response) }
    }

    //parses data and puts it into a mutable list, gets rid of null or empty names, and sorts it.
    fun postExecute(result: String?) {
        var gson = Gson()

        var data = gson.fromJson(result, Array<Ids>::class.java).toMutableList()
        Log.d("data", data.toString())

        arrayAdapter = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, data)
        dataList?.adapter = arrayAdapter
    }

    fun getBlanks(result: String?){
        var gson = Gson()

        var data = gson.fromJson(result, Array<Ids>::class.java).toMutableList()
        Log.d("data1", data.toString())

        if (data != null) {
            with(data.iterator()) {
                forEach {
                    if (it.name == null || it.name == "") {
                        remove()
                    }
                }
            }
        }
        Log.d("data3", data.toString())


        arrayAdapter = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, data)
        dataList?.adapter = arrayAdapter

    }

    fun sortListId(result: String?){
        var gson = Gson()

        var data = gson.fromJson(result, Array<Ids>::class.java).toMutableList()
        Log.d("data", data.toString())


        val sortedList = data.sortedWith(compareBy({ it.listId })).toMutableList() // sorts listId

        arrayAdapter = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, sortedList)
        dataList?.adapter = arrayAdapter

    }

    fun sortNames(result: String?){
        var gson = Gson()


        var data = gson.fromJson(result, Array<Ids>::class.java).toMutableList()
        Log.d("data", data.toString())


        val sortedList = data.sortedWith(compareBy(
                { it.name.substring(0, it.name.indexOf(' ')) }, //sorts first part of name as string
                { it.name.substring(it.name.indexOf(' ') + 1).toInt() } // sorts number after "Item" as Int
        )).toMutableList()

        arrayAdapter = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, sortedList)
        dataList?.adapter = arrayAdapter

    }

    fun blanksAndListId(result: String?){
        var gson = Gson()

        var data = gson.fromJson(result, Array<Ids>::class.java).toMutableList()
        Log.d("data1", data.toString())

        if (data != null) {
            with(data.iterator()) {
                forEach {
                    if (it.name == null || it.name == "") {
                        remove()
                    }
                }
            }
        }
        val sortedList = data.sortedWith(compareBy({ it.listId })).toMutableList() // sorts listId


        arrayAdapter = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, sortedList)
        dataList?.adapter = arrayAdapter
    }

    fun blanksAndNames(result: String?){
        var gson = Gson()

        var data = gson.fromJson(result, Array<Ids>::class.java).toMutableList()
        Log.d("data1", data.toString())

        if (data != null) {
            with(data.iterator()) {
                forEach {
                    if (it.name == null || it.name == "") {
                        remove()
                    }
                }
            }
        }
        val sortedList = data.sortedWith(compareBy(
                { it.name.substring(0, it.name.indexOf(' ')) }, //sorts first part of name as string
                { it.name.substring(it.name.indexOf(' ') + 1).toInt() } // sorts number after "Item" as Int
        )).toMutableList()

        arrayAdapter = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, sortedList)
        dataList?.adapter = arrayAdapter
    }

    fun listIdAndNames(result: String?){
        var gson = Gson()

        var data = gson.fromJson(result, Array<Ids>::class.java).toMutableList()
        Log.d("data", data.toString())

        //sorted lexicographically or alphabetically by "name". 1, 10, 2, 3
        //val sortedList = data.sortedWith(compareBy({ it.listId }, { it.name }))

        //sorts names correctly as well
        val sortedList = data.sortedWith(compareBy(
                { it.listId }, // sorts listId
                { it.name.substring(0, it.name.indexOf(' ')) }, //sorts first part of name as string
                { it.name.substring(it.name.indexOf(' ') + 1).toInt() } // sorts number after "Item" as Int
        ))

        arrayAdapter = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, sortedList)
        dataList?.adapter = arrayAdapter
    }

    fun allSorted(result: String?){
        var gson = Gson()

        var data = gson.fromJson(result, Array<Ids>::class.java).toMutableList()
        Log.d("data", data.toString())

        with(data.iterator()) {
            forEach {
                if (it.name == null || it.name == "") {
                    remove()
                }
            }
        }

        //sorted lexicographically or alphabetically by "name". 1, 10, 2, 3
        //val sortedList = data.sortedWith(compareBy({ it.listId }, { it.name }))

        //sorts names correctly as well
        val sortedList = data.sortedWith(compareBy(
                { it.listId }, // sorts listId
                { it.name.substring(0, it.name.indexOf(' ')) }, //sorts first part of name as string
                { it.name.substring(it.name.indexOf(' ') + 1).toInt() } // sorts number after "Item" as Int
        ))

        arrayAdapter = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, sortedList)
        dataList?.adapter = arrayAdapter
    }
}







