package com.example.clientesapplication

import android.R
import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.clientesapplication.databinding.ActivityMainBinding
import com.example.clientesapplication.ui.theme.ClientesApplicationTheme



class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ArrayAdapter<Clientes>
    private var pos: Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = DBHelper(this)

        val listaClientes = db.clientesListSelectAll()

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listaClientes)

        binding.listView.adapter = adapter

        binding.listView.setOnItemClickListener { _, _, position, _ ->
            binding.textId.setText("ID: ${listaClientes[position].id}")
            binding.editNome.setText(listaClientes[position].nome)
            binding.editEndereco.setText(listaClientes[position].endereco)
            pos = position
        }

        binding.buttonInserir.setOnClickListener {
            val nome = binding.editNome.text.toString()
            val endereco = binding.editEndereco.text.toString()
            //val medidas = binding.editMedidas.text.toString()
            val res = db.clientesInsert(nome, endereco, medidas = 0)

            if (res > 0) {

                Toast.makeText(applicationContext, "Insert OK: $res", Toast.LENGTH_SHORT).show()
                listaClientes.add(Clientes(res.toInt(), nome, endereco, medidas = 0))
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(applicationContext, "Insert Erro: $res", Toast.LENGTH_SHORT).show()

            }


        }
        binding.buttonEditar.setOnClickListener {
            if (pos >= 0) {
                val id = listaClientes[pos].id
                val nome = binding.editNome.text.toString()
                val endereco = binding.editEndereco.text.toString()

                val res = db.clientesUpdate(id, nome, endereco, medidas = 0)
                if (res > 0) {

                    Toast.makeText(applicationContext, "Update OK: $res", Toast.LENGTH_SHORT).show()
                    listaClientes[pos].nome = nome
                    listaClientes[pos].endereco = endereco

                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(applicationContext, "Update Erro: $res", Toast.LENGTH_SHORT)
                        .show()

                }

            }
        }
        binding.buttonDeletar.setOnClickListener {
            if (pos >= 0) {
                val id = listaClientes[pos].id
                var res = db.clientesDelete(id)

                if (res > 0) {

                    Toast.makeText(applicationContext, "Delete OK: $res", Toast.LENGTH_SHORT).show()
                    listaClientes.removeAt(pos)

                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(applicationContext, "Delete Erro: $res", Toast.LENGTH_SHORT)
                        .show()

                }
            }


        }
    }

}