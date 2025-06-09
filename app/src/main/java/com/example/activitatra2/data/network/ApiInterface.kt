package com.example.apilist.data.network
import com.example.activitatra2.data.model.nueva.Character
import com.example.activitatra2.data.model.nueva.Info
import com.example.apilist.data.model.Personajes
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

//API interface para Starwars characters
interface ApiInterface {
    @GET("characters?page=1&limit=10")
    suspend fun getData(): Response<Character>

    companion object {
        private const val BASE_URL = "https://starwars-databank-server.vercel.app/api/v1/"
        fun create(): ApiInterface {
            val client = OkHttpClient.Builder().build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
}