package com.example.contactsu.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.contactsu.model.Contacts
import com.example.contactsu.utils.Constants

class DatabaseHandler (val context:Context):SQLiteOpenHelper(context,Constants.DATABASE_NAME,null,Constants.DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_CONTACTS= "CREATE TABLE ${Constants.TABLE_NAME} (" +
                "${Constants.KEY_ID} INTEGER PRIMARY KEY AUTOINCREMENT , "+
                "${Constants.KEY_NAME} TEXT,"+
                "${Constants.KEY_PHONE} TEXT)"
        db?.execSQL(CREATE_TABLE_CONTACTS)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${Constants.TABLE_NAME}")
        onCreate(db)
    }

    //CRUD OPERATIONS

  fun addContacts(contact:Contacts){
      val db=this.writableDatabase
      val values = ContentValues()

      values.put(Constants.KEY_NAME,contact.name)
      values.put(Constants.KEY_PHONE,contact.phoneNumber)

      db.insert(Constants.TABLE_NAME,null,values)
      db.close()
  }

    //fetching single contact
    fun getContact(id:Int):Contacts{
        val db=this.readableDatabase

        val cursor=db.query(Constants.TABLE_NAME,
            arrayOf(Constants.KEY_ID,Constants.KEY_NAME,Constants.KEY_PHONE),
            Constants.KEY_ID+"=?", arrayOf(id.toString()) , null,null,null,null
        )
        cursor?.let {
            it.moveToFirst()
        }
        return Contacts(cursor.getString(0).toInt(),cursor.getString(1),cursor.getString(2))
    }

    //fetching all contacts
   fun getAllContacts():MutableList<Contacts>{
       val db=this.readableDatabase

        var Contactlist = mutableListOf<Contacts>()

        val selectAll = "SELECT * FROM ${Constants.TABLE_NAME}"
        var cursor = db.rawQuery(selectAll, null)

        if(cursor.moveToFirst()){
            do {
                val contact=Contacts(
                    cursor.getString(0).toInt(),
                    cursor.getString(1),
                    cursor.getString(2)
                )
                Contactlist.add(contact)
            }while (cursor.moveToNext())
        }
        return Contactlist
   }

    //Updating contact
    fun updateContact(contact:Contacts):Int{
        val db=this.writableDatabase

        val values = ContentValues()
        values.put(Constants.KEY_NAME,contact.name)
        values.put(Constants.KEY_PHONE,contact.phoneNumber)

        return db.update(Constants.TABLE_NAME,values,Constants.KEY_ID+"=?",
            arrayOf(contact.id.toString()))
    }

    //Deleting COntacts
    fun deleteCOntact(contact:Contacts){
        val db=this.writableDatabase

        db.delete(Constants.TABLE_NAME, Constants.KEY_ID+"=?", arrayOf(contact.id.toString()))
    }

    //Getting count of the records
    fun getContactCount():Int{
        val countQuery="SELECT * FROM ${Constants.TABLE_NAME}"
        val db=this.readableDatabase
        val cursor = db.rawQuery(countQuery, null)

        return cursor.count
    }

}