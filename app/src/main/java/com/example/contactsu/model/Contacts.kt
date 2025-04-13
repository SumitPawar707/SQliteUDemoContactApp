package com.example.contactsu.model

data class Contacts(var id:Int,var name:String,var phoneNumber:String){
    constructor(name: String="",phoneNumber: String="") : this(-1,name,phoneNumber)
}
