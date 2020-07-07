package it.unimib.librichepassione;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import it.unimib.librichepassione.databinding.FragmentSearchBinding;
import it.unimib.librichepassione.model.Book;
import it.unimib.librichepassione.model.BookInfo;
import it.unimib.librichepassione.viewModels.SearchViewModel;

public class SearchFragment extends Fragment {

    private static final String TAG = "SearchFragment: ";
    private FragmentSearchBinding binding;
    private SearchViewModel searchViewModel;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View view = binding.getRoot();



        binding.searchViewId.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.length() != 0){
                    loadBooks(newText);
                }

                return false;
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void loadBooks(String query){
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        final Observer<List<Book>> observer = new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {

                //Log.d(TAG, "msg: " + books.get(i));

                takeInfoList(books);


            }
        };

        searchViewModel.getBooks(query).observe(getViewLifecycleOwner(), observer);
    }

    private void takeInfoList(List<Book> books) {
        Book book;
        Object obj;
        for (int i = 0; i < books.size(); i++){
            book = books.get(i);
            String content = book.toString();
            takeInfoBook(content);
        }
    }

    private void takeInfoBook(String str) {
        Log.d(TAG, "stringa: " + str);
        String title;
        int indexTitle = str.indexOf("title=") + 6;
        int indexEqual = str.indexOf("=", indexTitle);
        String subString = str.substring(indexTitle, indexEqual);
        int indexComma = subString.lastIndexOf(",");
        title = subString.substring(0, indexComma);
        Log.d(TAG, "titolo: " + title);

        String authors;
        int indexAuthor = str.indexOf("authors=");
        if(indexAuthor > 0){
            int indexSquare1 = str.indexOf("[", indexAuthor) + 1;
            int indexSquare2 = str.indexOf("]", indexAuthor);
            authors = str.substring(indexSquare1, indexSquare2);
            Log.d(TAG, "autori: " + authors);
        }
        else{
            authors = null;
        }

        String publisher;
        int indexPublisher = str.indexOf("publisher=");
        if(indexPublisher > 0){
            indexPublisher = indexPublisher + 10;
            indexEqual = str.indexOf("=", indexPublisher);
            subString = str.substring(indexPublisher, indexEqual);
            indexComma = subString.lastIndexOf(",");
            publisher = subString.substring(0, indexComma);
            Log.d(TAG, "editori: " + publisher);
        }
        else{
            publisher = null;
        }

        String publisherDate;
        int indexPublisherDate = str.indexOf("publishedDate=");
        if(indexPublisherDate > 0){
            indexPublisherDate = indexPublisherDate + 14;
            indexEqual = str.indexOf("=", indexPublisherDate);
            subString = str.substring(indexPublisherDate, indexEqual);
            indexComma = subString.lastIndexOf(",");
            publisherDate = subString.substring(0, indexComma);
            Log.d(TAG, "data pubblicazione: " + publisherDate);
        }
        else{
            publisherDate = null;
        }






    }
}


