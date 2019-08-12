package moises.com.usersapp.repository.base

import io.reactivex.Single
import moises.com.usersapp.model.UserList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(API.API)
    fun getUserList(@Query("page") page: Int, @Query("results") results: Int,
                    @Query("seed") seed: String): Single<UserList>
}
