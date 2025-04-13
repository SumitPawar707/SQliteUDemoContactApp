package com.example.contactsu

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.contactsu.data.DatabaseHandler
import com.example.contactsu.model.Contacts

class MainActivity : AppCompatActivity() {
    private lateinit var  textview:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        textview=findViewById(R.id.textview_data)

        val db=DatabaseHandler(this)

        db.addContacts(Contacts("Alex", "123456"))
        db.addContacts(Contacts("John", "234567"))
        db.addContacts(Contacts("David","345678"))

        var contactList=db.getAllContacts()
        var data=""

        for(contact in contactList){
            data += "Name:${contact.name} \n PhoneNumber : ${contact.phoneNumber}"
        }
        textview.text=data
    }
}