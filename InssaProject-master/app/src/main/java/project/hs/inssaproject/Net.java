package project.hs.inssaproject;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Net {
    private static Net ourInstance = new Net();
    public static Net getInstance() {
        return ourInstance;
    }
    private Net() {
    }
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://54.180.32.249:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    ApiService apiService;

    public ApiService getApiService(){
        if(apiService == null){
            apiService = retrofit.create(ApiService.class);
        }
        return apiService;
    }
}
