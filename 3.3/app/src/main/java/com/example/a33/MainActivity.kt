package com.example.a33

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.JsonReader
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

class MainActivity : AppCompatActivity() {

    lateinit var newRecyclerView: RecyclerView
    lateinit var newArrayList: ArrayList<Person>
    var sex:ArrayList<Int> = ArrayList()
    var name:ArrayList<String> = ArrayList()
    var personPhone:ArrayList<String> = ArrayList()
    var prevSort :String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        f1()

        newRecyclerView = findViewById(R.id.recyclerView)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)

        val ss: Button = findViewById(R.id.ss)
        val sn: Button = findViewById(R.id.sn)
        val sp: Button = findViewById(R.id.sp)

        ss.setOnClickListener(){
            getUserdata("sex")
        }

        sn.setOnClickListener(){
            getUserdata("name")
        }

        sp.setOnClickListener(){
            getUserdata("phoneNumber")
        }

        getUserdata()

    }

    fun f1()
    {
        var j: String? = null
        try {
            val inputStream: InputStream = assets.open("persons.json")
            j = inputStream.bufferedReader().use{it.readText()}

            var jsonarr = JSONArray(j)


            for (i in 0 until jsonarr.length()) {
                var jsonobj = jsonarr.getJSONObject(i)
                sex.add(when(jsonobj.getString("sex")){
                    "male" -> R.drawable.male
                    "female" -> R.drawable.female
                    else -> R.drawable.unknown
                })
                name.add(jsonobj.getString("name"))
                personPhone.add(jsonobj.getString("phoneNumber"))
            }

        }
        catch (e: IOException){

        }
    }

    fun getUserdata(sort : String? = null){
        newArrayList = arrayListOf<Person>()
        for(i in sex.indices){

            val person = Person(sex[i], name[i], personPhone[i])
            newArrayList.add(person)
        }

        when (sort){
            "sex" -> newArrayList.sortBySex()
            "name" -> newArrayList.sortByname()
            "phone" -> newArrayList.sortByphoneNumber()
        }
        if (prevSort == sort){
            prevSort = null
        }
        else
            prevSort = sort
        newRecyclerView.adapter = MyRecyclerViewAdapter(newArrayList)

    }

    fun ArrayList<Person>.sortBySex(){
        for (i in lastIndex-1 downTo 0){
            for (j in 0..i){
                val obj1 = this[j]
                val obj2 = this[j+1]
                if (compareValues(obj1.sex, obj2.sex) > 0 && prevSort != "sex" || compareValues(obj1.sex, obj2.sex) < 0 && prevSort == "sex" )
                {
                    this[j] = this[j+1].also { this[j+1]=this[j] }
                }
            }
        }
    }

    fun ArrayList<Person>.sortByname(){
        for (i in lastIndex-1 downTo 0){
            for (j in 0..i){
                val obj1 = this[j]
                val obj2 = this[j+1]

                if (compareValues(obj1.name, obj2.name) > 0 && prevSort != "name" || compareValues(obj1.name, obj2.name) < 0 && prevSort == "name" )
                {
                    this[j] = this[j+1].also { this[j+1]=this[j] }
                }
            }
        }
    }

    fun ArrayList<Person>.sortByphoneNumber(){
        for (i in lastIndex-1 downTo 0){
            for (j in 0..i){
                val obj1 = this[j]
                val obj2 = this[j+1]
                if (compareValues(obj1.phoneNumber, obj2.phoneNumber) > 0 && prevSort != "phoneNumber" || compareValues(obj1.phoneNumber, obj2.phoneNumber) < 0 && prevSort == "phone" )
                {
                    this[j] = this[j+1].also { this[j+1]=this[j] }
                }
            }
        }
    }

}



