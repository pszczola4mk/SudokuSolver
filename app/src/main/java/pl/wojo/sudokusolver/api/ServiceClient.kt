package pl.wojo.sudokusolver.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ServiceClient {

    var gson: Gson = GsonBuilder()
        .setLenient()
        .create()

        public val retrofitClient: Retrofit
            get() {
                val httpClient = OkHttpClient()
                return Retrofit.Builder()
                    .baseUrl("http://153.19.62.198:8080/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient)
                    .build()
            }
}