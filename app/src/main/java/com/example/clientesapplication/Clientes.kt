package com.example.clientesapplication

class Clientes(val id: Int =0, var nome: String ="", var endereco: String = "", val medidas: Int = 0) {

    override fun toString(): String {
        return "Clientes(id=$id, nome='$nome', endereco='$endereco', medidas=$medidas)"
    }
}