package healthWave.data.remote

import healthWave.data.remote.model.SearchDto
import retrofit2.http.GET
import retrofit2.http.Query

interface HealthWaveApi {

    @GET("cgi/search.pl?search_simple=1&json=1&action=process&fields=product_name,nutriments,image_front_thumb_url")
    suspend fun searchFood(
        @Query("search_terms") query: String,
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int
    ): SearchDto
}