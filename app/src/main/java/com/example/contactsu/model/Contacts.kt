package com.example.contactsu.model

data class Contacts(var id:Int,var name:String,var phoneNumber:Int){
    constructor(name: String,phoneNumber: Int) : this(-1,name,phoneNumber)
}
