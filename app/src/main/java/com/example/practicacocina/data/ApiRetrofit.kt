package com.example.practicacocina.data

import DaoCocina
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

class ApiRetrofit () {
    lateinit var respon:Response<DaoCocina>

    fun getServ(): RetrofitService {
        var serv=getService()
        return serv
    }

    fun getResponse(): Response<DaoCocina> {

        // Lanza la petición de internet en 2º plano
        CoroutineScope(Dispatchers.IO).launch {

            respon = getService().searchAll()

            Log.i("HTTP", "GRAR La llamada acabo: " + respon?.isSuccessful.toString())
        }

        return respon
    }



    fun searchById(id:String): Response<DaoCocina>? {

        // Lanza la petición de internet en 2º plano
        CoroutineScope(Dispatchers.IO).launch {

            respon = getService().searchById(id)

        }

        return respon
    }

    fun searchByQuery(query:String): Response<DaoCocina>? {

        // Lanza la petición de internet en 2º plano
        CoroutineScope(Dispatchers.IO).launch {

            respon = getService().searchByName(query)

        }

        return respon
    }

    private fun getService():RetrofitService{
        val retrofit = Retrofit.Builder()
            .baseUrl(RETROFIT_BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: RetrofitService = retrofit.create(RetrofitService::class.java)

        return service
    }

    companion object {
        val RETROFIT_BASEURL="https://dummyjson.com"
    }
}


interface RetrofitService {

    @GET("/recipes/search?q={query}")
    suspend fun searchByName(@Path("query") query: String?): Response<DaoCocina>

    @GET("/recipes/{id}")
    suspend fun searchById(@Path("id") id:String?):Response<DaoCocina>

    @GET("/recipes/")
    suspend fun searchAll():Response<DaoCocina>

}

