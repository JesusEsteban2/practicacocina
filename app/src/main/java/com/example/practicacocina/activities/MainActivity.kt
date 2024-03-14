package com.example.practicacocina.activities

import DaoCocina
import DaoReceta
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Inicializar adapter
        var adapter = ReciclerAdapter({ onClick(it) })

        // Enicializar binding para acceso a los componentes
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var recip: DaoReceta = DaoReceta(
            1, 300, 15, "Italian", "Easy",
            "https://cdn.dummyjson.com/recipe-images/1.webp", listOf<String>(
                "Pizza dough",
                "Tomato sauce",
                "Fresh mozzarella cheese",
                "Fresh basil leaves",
                "Olive oil",
                "Salt and pepper to taste"
            ), listOf<String>(
                "Preheat the oven to 475°F (245°C).",
                "Roll out the pizza dough and spread tomato sauce evenly.",
                "Top with slices of fresh mozzarella and fresh basil leaves.",
                "Drizzle with olive oil and season with salt and pepper.",
                "Bake in the preheated oven for 12-15 minutes or until the crust is golden brown.",
                "Slice and serve hot."
            ), listOf<String>("Dinner"), "Classic Margherita Pizza",
            20, 4.6, 3, 4, listOf("Pizza", "Italian"), 45
        )

        dataSet = listOf<DaoReceta>(recip)

        // Asignar el adapter al reciclerView
        binding.recipeReciclerW.adapter = adapter
        binding.recipeReciclerW.layoutManager = GridLayoutManager(this, 1)

        // Hacer la llamada al API-Rest de Retrofit
        var retro = ApiRetrofit().getServ()
        var serv = retro
        var respon: Response<DaoCocina>? = null

        // Lanza la petición de internet en 2º plano
        CoroutineScope(Dispatchers.IO).launch {
            respon = serv.searchAll()
            Log.i("HTTP", "La llamada acabo: " + respon?.isSuccessful.toString())

            runOnUiThread {
                // Modificar UI en primer plano
                if (respon == null) {
                    Log.i("HTTP", "MA Respuesta nula")
                    finish()
                }
                if ((respon?.body()?.recipes != null)) {
                    Log.i("HTTP", "MA respuesta correcta :)")
                    Log.i("HTTP", "MA La llamada acabo: " + respon?.isSuccessful.toString())
                    dataSet = respon?.body()!!.recipes
                    adapter.updateItems(dataSet)
                } else {
                    Log.i("HTTP", "MA respuesta erronea :(")
                    Log.i("HTTP", "MA La llamada acabo: " + respon?.isSuccessful.toString())
                }
            }
        }
    }

    fun onClick(posi:Int){

    }

    companion object{
        var dataSet: List<DaoReceta> = listOf<DaoReceta>()
    }

}