package pl.wojo.sudokusolver.api

import com.google.gson.JsonObject
import pl.wojo.sudokusolver.model.Image
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SudokuSolverService {

    @GET("/echo/{text}")
    fun echo(@Path("text") text: String): Call<JsonObject>

    @POST("/uploadImg")
    fun uploadImg(@Body image: Image): Call<JsonObject>
}