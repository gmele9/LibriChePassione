package it.unimib.librichepassione.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import it.unimib.librichepassione.model.Book;
import it.unimib.librichepassione.model.TopHeadLinesResponseAPI;
import it.unimib.librichepassione.services.SearchService;
import it.unimib.librichepassione.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchRepository  {
    private static SearchRepository instance;
    private SearchService searchService;
    private static final String TAG = "SearchRepository: ";

    private SearchRepository(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.SEARCH_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        searchService = retrofit.create(SearchService.class);
    }

    public static synchronized SearchRepository getInstance(){
        if(instance == null){
            instance = new SearchRepository();
        }
        return instance;
        //instance = new SearchRepository();
    }

    public void getSearch (MutableLiveData<List<Book>> book, String q){
        Log.d(TAG, "query: " + q);
        Call<TopHeadLinesResponseAPI> call = searchService.getTopHeadLines(q, Constants.SEARCH_API_KEY);

        call.enqueue(new Callback<TopHeadLinesResponseAPI>() {
            @Override
            public void onResponse(Call<TopHeadLinesResponseAPI> call, Response<TopHeadLinesResponseAPI> response) {
                book.postValue(response.body().getItems());
                Log.d(TAG, "ecco: " + response.body());
                Log.d(TAG, "response: " + response);
            }

            @Override
            public void onFailure(Call<TopHeadLinesResponseAPI> call, Throwable t) {
                Log.d(TAG, "messaggio: " + t);
            }
        });
    }
}
