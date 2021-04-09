package pl.wojo.sudokusolver.api

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SudokuSolverService {

    @GET("/echo/{text}")
    fun echo(@Path("text") text: String): Call<JsonObject>
}