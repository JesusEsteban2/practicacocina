package com.example.practicacocina.data

import DaoCocina
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class ApiRetrofit () {
    lateinit var respon:Response<DaoCocina>

    fun getServ(): RetrofitService {
        var serv=getService()
        return serv
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
        val RETROFIT_BASEURL="https://dummyjson.com/"
    }
}


interface RetrofitService {

    //@GET("recipes/{query}")
    // suspend fun searchByText(@Path("query") query: String?): Response<DaoCocina>

    @GET("recipes/{id}")
    suspend fun searchById(@Path("id") id:String?):Response<DaoCocina>

    @GET("recipes/")
    suspend fun searchAll():Response<DaoCocina>

}

