package com.example.practicacocina.activities

import DaoCocina
import DaoReceta
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practicacocina.R
import com.example.practicacocina.adapter.ReciclerAdapter
import com.example.practicacocina.data.ApiRetrofit
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

        // Enicializar binding para acceso a los componentes
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Inicializar adapter
        adapter = ReciclerAdapter{ onClick(it) }

        // Asignar el adapter al reciclerView
        binding.recipeReciclerW.adapter = adapter
        binding.recipeReciclerW.layoutManager = LinearLayoutManager(this)

        // Hacer la llamada al API-Rest de Retrofit
        // para obtener los datos del API
        getFromRetro()


    }

    //Cargar Action Bar Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        // Carga menu de Scr/menu
        menuInflater.inflate(R.menu.menu_action_bar, menu)

        // carga el control para la accion buscar
        val itemSe = menu?.findItem(R.id.search_button)
        // Asigna el SearchView al control
        val searchView=itemSe?.actionView as SearchView
        //Define las funciones abstractas del objeto
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query.isNullOrEmpty()) {
                    getFromRetro()
                    Log.i("BUSCA","Busqueda Todos")
                } else {
                    val data= dataSet.filterIndexed{ index, daoReceta ->
                        dataSet[index].name.contains(query as CharSequence) }
                    Log.i("BUSCA","Busqueda con texto")
                    updateView(data,adapter)
                }

                return true
            }

        })

        //Termina la configuración del menu en Super
        return super.onCreateOptionsMenu(menu)

    }


    /**
     * Funcion para obtener los datos del API con Retrofit
     * Los datos recuperados se almacenan en el companion objet dataSet
     */
    fun getFromRetro(){
        val serv = ApiRetrofit().getServ()
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

    /**
     * Función OnClick para el adapter del ReciclerView
     * Recibe la posición (Elemento) al que se ha hecho click y carga la
     * pantalla de detalle (DetaidActivity) de dicho elemento.
     */
    fun onClick(posi:Int){
        val intent = Intent(this, DetailActivity::class.java)

        intent.putExtra("EXTRA_ID", posi.toString())
        startActivity(intent)

    }

    /**
     * Actualiza el Reciclerview
     * @param data: Nuevos datos con los que actualizar la vista
     * @param adap: Adapter de la vista
     */
    fun updateView(data:List<DaoReceta>,adap:ReciclerAdapter){
        adap.updateItems(data)
    }

    // Objeto para almacenar los datos a mostrar en ReciclerView
    companion object{
        var dataSet: List<DaoReceta> = listOf<DaoReceta>()
    }

}





