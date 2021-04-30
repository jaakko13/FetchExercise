package com.example.fetchcodingexercise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.google.gson.Gson
import org.json.JSONObject
import java.net.URL
import java.nio.file.Files.find
import java.util.regex.Pattern
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    lateinit var showList: Button
    lateinit var dataList: ListView
    lateinit var test: TextView
    private var arrayAdapter: ArrayAdapter<Ids> ? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showList = findViewById(R.id.showList)
        dataList = findViewById(R.id.dataList)
        test = findViewById(R.id.test)

        showList.setOnClickListener{
            thread {
                getData()
            }
        }
    }

    //calls api and gets all json data
    private fun getData(){
        var response: String? = ""
        response = URL("https://fetch-hiring.s3.amazonaws.com/hiring.json").readText(Charsets.UTF_8)
        //Log.d("msg", response!!)
        runOnUiThread { postExecute(response) }
    }

    //parses data and puts it into a mutable list, gets rid of null or empty names, and sorts it.
    fun postExecute(result: String?) {
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






