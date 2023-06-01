package moises.com.usersapp.repository.base

import moises.com.usersapp.repository.response.UsersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(ApiUrl.USERS)
    suspend fun getUserList(@Query("page") page: Int, @Query("results") results: Int): UsersResponse
}
