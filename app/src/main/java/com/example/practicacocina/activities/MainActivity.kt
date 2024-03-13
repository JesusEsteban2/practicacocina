package com.example.practicacocina.activities

import DaoReceta
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Hacer la llamada al API-Rest de Retrofit
        var retro=ApiRetrofit()
        var response=retro.getResponse()

        runOnUiThread {
            // Modificar UI en primer plano
            if ((response?.body()?.recipes != null)) {
                Log.i("HTTP", "respuesta correcta :)")
                Log.i("HTTP", response.body()?.limit.toString())
                recipeList = response?.body()!!.recipes
                var adapter=ReciclerAdapter(recipeList,{ onClick(it) })
                adapter.updateItems(recipeList)
            } else {
                Log.i("HTTP", "respuesta erronea :(")
            }
        }


    }

    fun onClick(posi:Int){

    }

}