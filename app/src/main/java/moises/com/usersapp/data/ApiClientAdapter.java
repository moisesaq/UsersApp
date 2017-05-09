package moises.com.usersapp.data;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientAdapter {

    private static ApiClientAdapter mApiClientAdapter;

    private ApiClientAdapter(){
    }

    public static ApiClientAdapter getInstance(){
        if(mApiClientAdapter == null)
            mApiClientAdapter = new ApiClientAdapter();
        return mApiClientAdapter;
    }

    public ApiClient startConnection(){
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(API.BASE)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpBuilder.build()).build();
        return retrofit.create(ApiClient.class);
    }
}
