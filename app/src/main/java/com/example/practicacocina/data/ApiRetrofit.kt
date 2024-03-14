package com.example.practicacocina.data

import DaoCocina
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

class ApiRetrofit () {
    var respon: Response<DaoCocina>? = null
    val serv = getService()

    fun getResponse(): Response<DaoCocina>? {

        // Lanza la petición de internet en 2º plano
        CoroutineScope(Dispatchers.IO).launch {

            respon = serv.searchAll()

        }

        return respon
    }

    fun searchByQuery(query:String): Response<DaoCocina>? {

        // Lanza la petición de internet en 2º plano
        CoroutineScope(Dispatchers.IO).launch {

            respon = serv.searchByName(query)

        }

        return respon
    }

    fun getService():RetrofitService{
        val retrofit = Retrofit.Builder()
            .baseUrl(RETROFIT_BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: RetrofitService = retrofit.create(RetrofitService::class.java)

        return service
    }

    companion object {
        val RETROFIT_BASEURL="https://dummyjson.com/recipes"
    }
}


interface RetrofitService {

    @GET("search/{query}")
    suspend fun searchByName(@Path("query") query: String?): Response<DaoCocina>

    @GET("{id}")
    suspend fun searchById(@Path("id") id:String?):Response<DaoCocina>

    @GET()
    suspend fun searchAll():Response<DaoCocina>

}

