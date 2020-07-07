package it.unimib.librichepassione.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import it.unimib.librichepassione.model.Book;
import it.unimib.librichepassione.repositories.SearchRepository;

public class SearchViewModel extends ViewModel {
    private MutableLiveData<List<Book>> books;

    public LiveData<List<Book>> getBooks(String q) {

//        if (books == null) {
        books = new MutableLiveData<>();
        SearchRepository.getInstance().getSearch(books, q);

        //}
        return books;
    }

}
