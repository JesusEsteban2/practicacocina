package com.example.practicacocina.data

import DaoReceta
import Receta
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class Database (context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION){

        override fun onCreate(db: SQLiteDatabase?) {
            db?.execSQL(SQL_CREATE_ENTRIES_RECETAS)
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
            db?.execSQL(SQL_DELETE_TABLE_RECETAS)
        }

    /**
     * Insert new row in DB, returning true if the primary key value is over 0
     * @param r Recipe to insert in DB
     * @return true if insert
     */
    fun insertRecipe(r:DaoReceta):Boolean{

        // Create a new map of values, where column names are the key
        val values= asigValues(r)

        // Insert the new row, returning true if the primary key value is over 0
        val db=this.writableDatabase
        val newRowId = db.insert(TABLE_NAME, null, values)
        db.close()
        return (newRowId>0)
    }


    /**
     * Delete record by Id
     * @param i: Id of Recipe to delete
     * @return: True if the delete is done.
     */
    fun deleteById(i:Int):Boolean{

        // Delete row by id
        val db=this.writableDatabase
        val rowDel = db.delete(TABLE_NAME, "ID=$i",null)
        db.close()
        Log.i("DB","Eliminado $rowDel registro")
        return (rowDel>0)
    }

    /**
     * Update record by Id
     * @param r: Recipe to update
     * @return: True if the update is correct.
     */
    fun updateRecipe(r:DaoReceta):Boolean {

        val values=asigValues(r)
            values.put("ID",r.id)

        //Establecer criterio
        val cri=arrayOf(r.id.toString())

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

    /**
     * Devuelve una lista con 1 elemento que corresponde con el ID Buscado
     * @param k Id a buscar
     * @return Lista con las recetas
     */
    fun searchById(k:Int): List<DaoReceta>? {

        // Search by id
        val av=arrayOf<String>(k.toString())

        val db=this.writableDatabase
        val cursor: Cursor = db.query(TABLE_NAME,null,"ID = ?",av,
            null,null,null)

        // Log.i("DB","Obtenidos ${cursor.count} registros")

        // Llama a la función para crear la lista con los datos recuperados
        val lt:MutableList<DaoReceta> = recupDatos(cursor)

        // Cierra cursor y BBDD
        cursor.close()
        db.close()

        return lt
    }

    /**
     * Devuelve una lista con los elementos de la BBDD que contienen el String pasado
     * @param n String a Buscar
     * @return Lista con las recetas
     */
    fun searchByName(n:String):List<DaoReceta>?{

        // Search by name
        val av=arrayOf<String>("%$n%")

        val db=this.writableDatabase
        val cursor:Cursor = db.query(TABLE_NAME,null,"name LIKE ?",av,
                null,null,null)

        // Log.i("DB","Obtenidos ${cursor.count} registros")

        // Llama a la función para crear la lista con los datos recuperados
        val lt:MutableList<DaoReceta> = recupDatos(cursor)

        // Cierra cursor y BBDD
        cursor.close()
        db.close()

        return lt
    }

    /**
     * Devuelve una lista con todos los elementos de la BBDD
     * @return Lista con las recetas
     */
    fun searchAll():List<DaoReceta>{

        val db=this.writableDatabase
        val cursor:Cursor = db.query(TABLE_NAME,null,null,null,
                null,null,null)

        // Log.i("DB","Obtenidos ${cursor.count} registros")

        // Llama a la función para crear la lista con los datos recuperados
        val lt:MutableList<DaoReceta> = recupDatos(cursor)

        // Cierra cursor y BBDD
        cursor.close()
        db.close()

        return lt
    }

    /**
     * Crea una lista de recetas con los datos devueltos pro la base de datos.
     * @param c Cursor de la BBDD
     * @return Lista de recetas MutableList<DaoReceta>
     */
    fun recupDatos (c:Cursor):MutableList<DaoReceta>{

        val lt:MutableList<DaoReceta> = mutableListOf<DaoReceta>()

        while (c.moveToNext()) {
            val t=DaoReceta(c.getInt(0),c.getString(1),
                c.getInt(2),c.getInt(3),c.getString(4),
                c.getString(5),c.getString(6),listOf(c.getString(7)),
                listOf(c.getString(8)),listOf(c.getString(9)),c.getInt(10),
                c.getDouble(11))
            lt.add(t)
        }

        return lt
    }



    /**
     * Crea un ContentValues (Clave,Valor) con los nombres de los campos de la BD y
     * los valores pasados en la receta.
     * @param re Receta para asignar los valores.
     * @return ContentValues (Clave,Valor) (Columna DB,Valor de Receta)
     */
    fun asigValues(re:DaoReceta):ContentValues{

        val values=ContentValues()

        values.put(SQL_RECETAS_COLUMS[0],re.name)
        values.put(SQL_RECETAS_COLUMS[1],re.calPerServing)
        values.put(SQL_RECETAS_COLUMS[2],re.cookTimeMin)
        values.put(SQL_RECETAS_COLUMS[3],re.cuisine)
        values.put(SQL_RECETAS_COLUMS[4],re.difficulty)
        values.put(SQL_RECETAS_COLUMS[5],re.image)
        values.put(SQL_RECETAS_COLUMS[6],Receta().listToString(re.ingredients))
        values.put(SQL_RECETAS_COLUMS[7],Receta().listToString(re.instructions))
        values.put(SQL_RECETAS_COLUMS[8],Receta().listToString(re.mealType))
        values.put(SQL_RECETAS_COLUMS[9],re.prepTimeMin)
        values.put(SQL_RECETAS_COLUMS[10],re.rating)

        return values
    }

    companion object {
        const val DATABASE_NAME="recetas.db"
        const val DATABASE_VERSION=2
        const val TABLE_NAME="Recipes"
        const val SQL_DELETE_TABLE_RECETAS="DROP TABLE IF EXISTS $TABLE_NAME"
        const val SQL_CREATE_ENTRIES_RECETAS ="CREATE TABLE $TABLE_NAME "+
            "(Id INTEGER PRIMARY KEY AUTOINCREMENT,"+ "name TEXT,"+
            "caloriesPerServing INTEGER,"+"cookTimeMinutes INTEGER,"+
            "cuisine TEXT,"+"difficulty TEXT,"+"image TEXT,"+
            "ingredients TEXT,"+"instructions TEXT,"+
            "mealType TEXT,"+"prepTimeMinutes INTEGER,"+"rating REAL)"
        val SQL_RECETAS_COLUMS:List<String> = listOf("name","caloriesPerServing","cookTimeMinutes",
            "cuisine","difficulty","image","ingredients","instructions",
            "mealType","prepTimeMinutes","rating")
    }
}