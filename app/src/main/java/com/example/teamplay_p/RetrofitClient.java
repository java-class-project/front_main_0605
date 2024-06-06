package com.example.teamplay_p;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;
    private static final String BASE_URL = "http://192.168.0.7:4000/";

    public static String Uuid;

    public static Retrofit getClient(String authToken){
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        if (authToken != null && !authToken.isEmpty()){
            httpClientBuilder.addInterceptor(chain -> {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Authorization", "Bearer "+authToken)
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            });
        }

        OkHttpClient httpClient = httpClientBuilder.build();


        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
    }
}