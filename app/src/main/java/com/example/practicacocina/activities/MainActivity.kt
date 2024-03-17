package com.example.practicacocina.activities

import DaoCocina
import DaoReceta
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practicacocina.R
import com.example.practicacocina.adapter.ReciclerAdapter
import com.example.practicacocina.data.ApiRetrofit
import com.example.practicacocina.data.RetrofitService
import com.example.practicacocina.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response



class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var adapter: ReciclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Inicializar adapter
        adapter = ReciclerAdapter({ onClick(it) })

        // Enicializar binding para acceso a los componentes
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Asignar el adapter al reciclerView
        binding.recipeReciclerW.adapter = adapter
        binding.recipeReciclerW.layoutManager = LinearLayoutManager(this)

        // Hacer la llamada al API-Rest de Retrofit
        getFromRetro()


    }

    //Cargar Action Bar Menu

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        // Carga menu de Scr/menu
        menuInflater.inflate(R.menu.menu_action_bar, menu)

        // carga la accion buscar
        var itemSe = menu?.findItem(R.id.search_button)

        var searchView=itemSe?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query=="*") {
                    getFromRetro()
                    Log.i("BUSCA","Busqueda Todos")
                } else {
                    var data= dataSet.filterIndexed{ index, daoReceta ->
                        dataSet[index].name.contains(query as CharSequence) }
                    Log.i("BUSCA","Busqueda con texto")
                    updateView(data,adapter)
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return false
            }

        })

        return super.onCreateOptionsMenu(menu)

    }



    fun getFromRetro(){
        var serv = ApiRetrofit().getServ()
        var respon: Response<DaoCocina>?

        CoroutineScope(Dispatchers.IO).launch {
            respon = serv.searchAll()
            Log.i("HTTP", "MA- La llamada acabo: " + respon?.isSuccessful.toString())

            runOnUiThread {
                // Modificar UI en primer plano
                if (respon == null) {
                    Log.i("HTTP", "MA- Respuesta nula")
                    finish()
                }
                if ((respon?.body()?.recipes != null)) {
                    Log.i("HTTP", "MA- Respuesta correcta :)")
                    dataSet = respon?.body()!!.recipes
                    updateView(dataSet,adapter)
                } else {
                    Log.i("HTTP", "MA- Respuesta erronea, no se ha recibido nada :(")
                }
            }
        }
    }

    fun onClick(posi:Int){
        val intent = Intent(this, DetailActivity::class.java)

        intent.putExtra("EXTRA_ID", posi.toString())
        startActivity(intent)

    }

    fun updateView(data:List<DaoReceta>,adap:ReciclerAdapter){
        adap.updateItems(data)
    }

    private fun SearchView.setOnCloseListener(function: () -> Unit) {
        getFromRetro()
    }

    companion object{
        var dataSet: List<DaoReceta> = listOf<DaoReceta>()
    }

}





