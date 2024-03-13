package com.example.practicacocina.data

import DaoReceta
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
class ApiRetrofit {

    fun getService() {
        val service = RetrofitBuilder.getService()

        CoroutineScope(Dispatchers.IO).launch {

            val response = service.searchByName(query)


            runOnUiThread {
                // Modificar UI
                if ((response.body()!!.listSuperHero != null) && response.isSuccessful == true) {
                    Log.i("HTTP", "respuesta correcta :)")
                    Log.i("HTTP", response.body().toString())
                    superheroList = response.body()!!.listSuperHero
                    adapter.updateItems(superheroList)
                } else {
                    Log.i("HTTP", "respuesta erronea :(")
                }
            }
        }
    }
}


interface RetrofitService {

    @GET("search/{query}")
    suspend fun searchByName(@Path("query") query: String?): Response<SuperHeroResponse>

    @GET("{id}")
    suspend fun searchById(@Path("id") id:String?):Response<DaoReceta>

}

object RetrofitBuilder {

    fun getService():RetrofitService{
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.superheroapi.com/api.php/7252591128153666/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: RetrofitService = retrofit.create(RetrofitService::class.java)

        return service
    }
}