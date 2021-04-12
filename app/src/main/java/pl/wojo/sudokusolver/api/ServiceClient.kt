package pl.wojo.sudokusolver.api

import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import pl.wojo.sudokusolver.model.Image
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayOutputStream
import java.util.*


class ServiceClient {

    private val TAG: String = "ServiceClient"
    var gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofitClient: Retrofit
        get() {
            val httpClient = OkHttpClient()
            return Retrofit.Builder()
                .baseUrl("http://153.19.62.198:8080/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient)
                .build()
        }

    @RequiresApi(Build.VERSION_CODES.O)
    fun sendImg(bitmap: Bitmap?): String {
        var result: String = ""
        try {
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap?.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            val encoded: String = Base64.getEncoder().encodeToString(byteArray)
            Log.i(TAG, "API call Start")
            val service = this.retrofitClient.create(SudokuSolverService::class.java)
            val echoResult = service.uploadImg(Image("img", encoded))
            val execute = echoResult.execute()
            result = execute.body().get("result").asString
            Log.i(TAG, "API Called: $result")
        } catch (e: Exception) {
            result = "sendImg ex: ${e.message}"
            Log.e(TAG, result)
        }
        return result
    }
}