package moises.com.usersapp.data;

import moises.com.usersapp.model.UserList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiClient {

    @GET(API.API)
    Call<UserList> getUserList(@Query("page") int page, @Query("results") int results, @Query("seed") String seed);
}
