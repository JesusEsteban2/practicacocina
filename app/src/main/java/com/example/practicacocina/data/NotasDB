package com.example.practicacocina.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


class DatabaseHelper (context: Context)
    : SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION){

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES_TAREAS)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        destroyDatabase(db)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        destroyDatabase(db)
        onCreate(db)
    }

    private fun destroyDatabase(db: SQLiteDatabase?){
        db?.execSQL(SQL_DELETE_TABLE_TAREAS)
    }

    fun insertRecipe(r:Recipe):Boolean{

        // Create a new map of values, where column names are the key
        val values=ContentValues()

        values.put(SQL_TAREAS_COLUMS[0],t.task)
        values.put(SQL_TAREAS_COLUMS[1],t.doit)
        values.put(SQL_TAREAS_COLUMS[2],t.cat)

        // Insert the new row, returning the primary key value of the new row
        val db=this.writableDatabase
        val newRowId = db.insert(TABLE_NAME, null, values)
        db.close()
        return (newRowId>0)
    }


    /**
     * Delete record by Id
     * @param i: Id of Task to delete
     * @return: True if the delete is correct.
     */
    fun deleteTask(i:Int):Boolean{

        // Delete row by id
        val db=this.writableDatabase
        val rowDel = db.delete(TABLE_NAME, "ID=$i",null)
        db.close()
        Log.i("DB","Eliminado $rowDel registro")
        return (rowDel>0)
    }

    /**
     * Update record by Id
     * @param ta: Task to update
     * @return: True if the update is correct.
     */
    fun updateTask(ta:Tarea):Boolean {

        val values=ContentValues()
        values.put("ID",ta.id)
        values.put(SQL_TAREAS_COLUMS[0],ta.task)
        values.put(SQL_TAREAS_COLUMS[1],ta.doit)
        values.put(SQL_TAREAS_COLUMS[2],ta.cat)

        //Establecer criterio
        val cri=arrayOf(ta.id.toString())

        val db=this.writableDatabase
        val count = db.update(
            TABLE_NAME,
            values,
            "Id= ?",
            cri
        )
        db.close()

        return (count>0)
    }
    fun searchById(k:Int): Tarea? {

        // Search by id
        val av=arrayOf<String>(k.toString())

        val db=this.writableDatabase
        val cursor:Cursor = db.query(TABLE_NAME,null,"ID = ?",av,
            null,null,null)

        // Log.i("DB","Obtenidos ${cursor.count} registros")
        var t: Tarea? =null

        if (cursor.moveToNext()) {
            t=Tarea(cursor.getInt(0),cursor.getString(1),
                cursor.getInt(2)==1,cursor.getInt(3))
        }
        cursor.close()
        db.close()

        return t
    }

    fun searchByUndo():List<Tarea>{

        // Search by id
        val av=arrayOf<String>(true.toString())

        val db=this.writableDatabase
        val cursor:Cursor = db.query(TABLE_NAME,null,"Doit = ?",av,
            null,null,null)

        // Log.i("DB","Obtenidos ${cursor.count} registros")
        val lt:MutableList<Tarea> = mutableListOf<Tarea>()

        while (cursor.moveToNext()) {
            val t=Tarea(cursor.getInt(0),cursor.getString(1),
                cursor.getInt(2)==1,cursor.getInt(3))
            lt.add(t)
        }
        cursor.close()
        db.close()

        return lt
    }

    fun searchAll():List<Tarea>{

        val db=this.writableDatabase
        val cursor:Cursor = db.query(TABLE_NAME,null,null,null,
            null,null,null)

        // Log.i("DB","Obtenidos ${cursor.count} registros")
        val lt:MutableList<Tarea> = mutableListOf<Tarea>()

        while (cursor.moveToNext()) {
            val t=Tarea(cursor.getInt(0),cursor.getString(1),
                cursor.getInt(2)==1,cursor.getInt(3))
            lt.add(t)
        }
        cursor.close()
        db.close()

        return lt
    }
    companion object {
        const val DATABASE_NAME="tareastest.db"
        const val DATABASE_VERSION=1
        const val TABLE_NAME="Recipes"
        const val SQL_DELETE_TABLE_TAREAS="DROP TABLE IF EXISTS Tareas"
        const val SQL_CREATE_ENTRIES_TAREAS ="CREATE TABLE $TABLE_NAME ("
            +  "Id INTEGER PRIMARY KEY AUTOINCREMENT,"
        // Todo: Decidir si tomar el id o no tomarlo
            + "Id INTEGER,"
            +"caloriesPerServing INTEGER,"
            +"cookTimeMinutes INTEGER,"
            +"cuisine TEXT,"
            +"difficulty TEXT,"
            +"image TEXT,"
            +"ingredients List<TEXT>,"
            +"instructions List<TEXT>,"
            val mealType: List<TEXT>,
            val name: TEXT,
            val prepTimeMinutes: INTEGER,
            val rating: Double,
            val reviewCount: INTEGER,
            val servings: INTEGER,
            val tags: List<TEXT>,
            val userId: INTEGER
        +")"
        val SQL_TAREAS_COLUMS:List<String> = listOf("Task","Doit","Cat")
    }
}