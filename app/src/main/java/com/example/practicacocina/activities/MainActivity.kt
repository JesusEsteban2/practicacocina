package com.example.practicacocina.activities

import DaoReceta
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.example.practicacocina.R
import com.example.practicacocina.adapter.ReciclerAdapter
import com.example.practicacocina.data.ApiRetrofit
import com.example.practicacocina.databinding.ActivityMainBinding


@Suppress("UNUSED_EXPRESSION")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var recipeList:List<DaoReceta>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Inicializar adapter
        var adapter=ReciclerAdapter({ onClick(it) })

        // Enicializar binding para acceso a los componentes
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Asignar el adapter al reciclerView
        binding.recipeReciclerW.adapter = adapter
        binding.recipeReciclerW.layoutManager = GridLayoutManager(this, 1)

        // Hacer la llamada al API-Rest de Retrofit
        var retro=ApiRetrofit()
        var response=retro.getResponse()

        runOnUiThread {
            // Modificar UI en primer plano
            if ((response?.body()?.recipes != null)) {
                Log.i("HTTP", "respuesta correcta :)")
                Log.i("HTTP", "La llamada acabo: "+response?.isSuccessful.toString())
                recipeList = response?.body()!!.recipes
                adapter.updateItems(recipeList)
            } else {
                Log.i("HTTP", "respuesta erronea :(")
                Log.i("HTTP", "La llamada acabo: "+response?.isSuccessful.toString())
            }
        }


    }

    fun onClick(posi:Int){

    }

    companion object{
        var dataSet: List<DaoReceta> = listOf<DaoReceta>()
    }

}