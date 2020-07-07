package it.unimib.librichepassione.services;

import it.unimib.librichepassione.model.TopHeadLinesResponseAPI;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface SearchService {

    @GET("volumes")
    Call<TopHeadLinesResponseAPI> getTopHeadLines(@Query("q") String q,
                                                  @Header("Authorization") String key);
}
