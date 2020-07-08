package it.unimib.librichepassione;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import it.unimib.librichepassione.adapters.SearchAdapter;
import it.unimib.librichepassione.databinding.FragmentSearchBinding;
import it.unimib.librichepassione.model.Book;
import it.unimib.librichepassione.model.BookInfo;
import it.unimib.librichepassione.model.ISBN;
import it.unimib.librichepassione.viewModels.SearchViewModel;

public class SearchFragment extends Fragment {

    private static final String TAG = "SearchFragment: ";
    private FragmentSearchBinding binding;
    private SearchViewModel searchViewModel;
    private List<BookInfo> bookList;
    private SearchAdapter searchAdapter;

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

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.recyclerViewSearch.setLayoutManager(layoutManager);

        bookList = new ArrayList<>();
        searchAdapter = new SearchAdapter(bookList, getActivity(), new SearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BookInfo book) {
                Log.d(TAG, "item premuto" + book.getTitle());
                SearchFragmentDirections.ShowSearchBookDetailAction action =
                        SearchFragmentDirections.showSearchBookDetailAction(book);
                Navigation.findNavController(view).navigate(action);
            }
        });
        binding.recyclerViewSearch.setAdapter(searchAdapter);

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

//        binding = FragmentSearchBinding.inflate(getLayoutInflater());
//        View view = binding.getRoot();
//
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        binding.recyclerViewSearch.setLayoutManager(layoutManager);

        //List<BookInfo> bookList = new ArrayList<>();

        final Observer<List<Book>> observer = new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {
                //Log.d(TAG, "msg: " + books.get(i));
                takeInfoList(books);
                Log.d(TAG, "msg: " + bookList);

                searchAdapter.notifyDataSetChanged();
            }
        };

        searchViewModel.getBooks(query).observe(getViewLifecycleOwner(), observer);


    }

    private List<BookInfo> takeInfoList(List<Book> books) {
        bookList.clear();
        Book book;
        BookInfo b;
        for (int i = 0; i < books.size(); i++){
            book = books.get(i);
            String content = book.toString();
            b = takeInfoBook(content);
            bookList.add(b);
            //Log.d(TAG, "book: " + b);
        }

        //Log.d(TAG, "book: " + bookList);
        return bookList;

    }

    private BookInfo takeInfoBook(String str) {
        //Log.d(TAG, "stringa: " + str);
        String title;
        int indexTitle = str.indexOf("title=") + 6;
        int indexEqual = str.indexOf("=", indexTitle);
        String subString = str.substring(indexTitle, indexEqual);
        int indexComma = subString.lastIndexOf(",");
        title = subString.substring(0, indexComma);
        //Log.d(TAG, "titolo: " + title);

        String authors;
        int indexSquare1;
        int indexSquare2;
        int indexAuthor = str.indexOf("authors=");
        if(indexAuthor > 0){
            indexSquare1 = str.indexOf("[", indexAuthor) + 1;
            indexSquare2 = str.indexOf("]", indexAuthor);
            authors = str.substring(indexSquare1, indexSquare2);
            //Log.d(TAG, "autori: " + authors);
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
            //Log.d(TAG, "editori: " + publisher);
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
            //Log.d(TAG, "data pubblicazione: " + publisherDate);
        }
        else{
            publisherDate = null;
        }

        String type, type1;
        String identifier, identifier1;
        String secondSub, ss;
        int indexType;
        int indexIdentifier;
        int indexCurly1;
        int indexCurly2;
        int indexCurly3;
        int indexindustryIdentifiers = str.indexOf("industryIdentifiers=");
        if(indexindustryIdentifiers > 0){
            indexSquare1 = str.indexOf("[", indexindustryIdentifiers);
            indexSquare2 = str.indexOf("]", indexindustryIdentifiers);
            subString = str.substring(indexSquare1, indexSquare2);

            indexCurly1 = subString.indexOf("{") + 1;
            indexCurly2 = subString.indexOf("}");
            secondSub = subString.substring(indexCurly1, indexCurly2);

            indexType = secondSub.indexOf("type=") + 5;
            indexEqual = secondSub.indexOf("=", indexType);
            ss = secondSub.substring(indexType, indexEqual);
            indexComma = ss.lastIndexOf(",");
            type = ss.substring(0, indexComma);
            //Log.d(TAG, "type: " + type);

            indexIdentifier = secondSub.indexOf("identifier=") + 11;
            identifier = secondSub.substring(indexIdentifier, secondSub.length());
            //Log.d(TAG, "identifier: " + identifier);

            indexCurly3 = subString.indexOf("}", indexCurly2 + 1);
            if(indexCurly3 > 0){
                secondSub = subString.substring(indexCurly2, indexCurly3);

                indexType = secondSub.indexOf("type=") + 5;
                indexEqual = secondSub.indexOf("=", indexType);
                ss = secondSub.substring(indexType, indexEqual);
                indexComma = ss.lastIndexOf(",");
                type1 = ss.substring(0, indexComma);
                //Log.d(TAG, "type 1: " + type1);

                indexIdentifier = secondSub.indexOf("identifier=") + 11;
                identifier1 = secondSub.substring(indexIdentifier, secondSub.length());
                //Log.d(TAG, "identifier 1: " + identifier1);
            }
            else{
                type1 = null;
                identifier1 = null;
            }

        }
        else{
            type = null;
            identifier = null;
            type1 = null;
            identifier1 = null;
        }
        ISBN isbn = new ISBN(type, identifier);
        ISBN isbn1 = new ISBN(type1, identifier1);
        //Log.d(TAG, "ISBN 1: " + isbn);
        //Log.d(TAG, "ISBN 1: " + isbn1);

        String categories;
        int indexCategories = str.indexOf("categories=");
        if(indexCategories > 0){
            indexSquare1 = str.indexOf("[", indexCategories) + 1;
            indexSquare2 = str.indexOf("]", indexCategories);
            categories = str.substring(indexSquare1, indexSquare2);
            //Log.d(TAG, "categorie: " + categories);
        }
        else{
            categories = null;
        }

        String thumbnail;
        int indexThumbnail = str.indexOf("thumbnail=");
        if(indexThumbnail > 0){
            indexThumbnail = indexThumbnail + 10;
            indexCurly1 = str.indexOf("}", indexThumbnail);
            thumbnail = str.substring(indexThumbnail, indexCurly1);
            //Log.d(TAG, "miniatura: " + thumbnail);
        }
        else{
            thumbnail = null;
        }

        List<ISBN> isbnList = new ArrayList<>();
        //Log.d(TAG, "lista isbn: " + isbnList);
        isbnList.add(isbn);
        isbnList.add(isbn1);
        //Log.d(TAG, "lista isbn: " + isbnList);
        BookInfo book = new BookInfo(title, authors, publisher, publisherDate, isbnList, categories, thumbnail);
        return book;
    }
}


