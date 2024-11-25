package com.example.clientesapplication

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "database.db", null, 1 ) {

val sql = arrayOf(
    "CREATE TABLE clientes (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, endereco TEXT, medidas INTEGER)",
    "INSERT INTO clientes (nome, endereco, medidas) VALUES ('nome', 'endereco', 'medidas')",
)

    override fun onCreate(db: SQLiteDatabase) {
        sql.forEach {
            db.execSQL(it)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE clientes")
        onCreate(db)
    }

    fun clientesInsert(nome: String, endereco: String, medidas: Int): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("nome", nome)
        contentValues.put("endereco", endereco)
        contentValues.put("medidas", medidas)
         val res = db.insert("clientes", null, contentValues)
        db.close()
        return res
    }

    fun clientesUpdate(id: Int, nome: String, endereco: String, medidas: Int) : Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("nome", nome)
        contentValues.put("endereco", endereco)
        contentValues.put("medidas", medidas)
        val res = db.update("clientes", contentValues, "id=?", arrayOf(id.toString()))
        db.close()
        return res

    }

    fun clientesDelete(id: Int): Int {
        val db = this.writableDatabase
        val res = db.delete("clientes", "id=?", arrayOf(id.toString()))
        db.close()
        return res
    }

    fun clientesSelectAll(): Cursor {
        val db = this.readableDatabase
        val c = db.rawQuery("SELECT * FROM clientes", null)
        db.close()
        return c
    }

    fun clientesSelectByID(id: Int): Cursor{
        val db = this.readableDatabase
        val c = db.rawQuery("SELECT * FROM clientes WHERE id=?", arrayOf(id.toString()))
        db.close()
        return c

    }

    fun clientesObjectSelectByID(id: Int): Clientes {
        val db = this.readableDatabase
        val c = db.rawQuery("SELECT * FROM clientes WHERE id=?", arrayOf(id.toString()))
        var clientes = Clientes()
        if(c.count == 1){
            c.moveToFirst()
            val idIndex = c.getColumnIndex("id")
            val nomeIndex = c.getColumnIndex("nome")
            val enderecoIndex = c.getColumnIndex("endereco")
            val medidasIndex = c.getColumnIndex("medidas")


            val id = c.getInt(idIndex)
            val nome = c.getString(nomeIndex)
            val endereco = c.getString(enderecoIndex)
            val medidas = c.getInt(medidasIndex)

            clientes = Clientes(id, nome,endereco, medidas)
        }
        db.close()
        return clientes
    }

    fun clientesListSelectAll() : ArrayList<Clientes> {
        val db = this.readableDatabase
        val c = db.rawQuery("SELECT * FROM clientes", null)
        val listaClientes: ArrayList<Clientes> = ArrayList()

       if (c.count> 0){
           c.moveToFirst()
           do{
               val idIndex = c.getColumnIndex("id")
               val nomeIndex = c.getColumnIndex("nome")
               val enderecoIndex = c.getColumnIndex("endereco")
               val medidasIndex = c.getColumnIndex("medidas")


               val id = c.getInt(idIndex)
               val nome = c.getString(nomeIndex)
               val endereco = c.getString(enderecoIndex)
               val medidas = c.getInt(medidasIndex)
               listaClientes.add(Clientes(id, nome, endereco, medidas))
           } while(c.moveToNext())
       }
        db.close()
        return listaClientes
    }
}