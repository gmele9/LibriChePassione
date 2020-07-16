package it.unimib.librichepassione;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import it.unimib.librichepassione.adapters.SearchAdapter;
import it.unimib.librichepassione.databinding.FragmentAdvancedSearchListBinding;
import it.unimib.librichepassione.model.Book;
import it.unimib.librichepassione.model.BookInfo;
import it.unimib.librichepassione.model.ISBN;
import it.unimib.librichepassione.viewModels.SearchViewModel;

public class AdvancedSearchListFragment extends Fragment {


    private SearchViewModel searchViewModel;
    private List<BookInfo> bookList;
    private SearchAdapter searchAdapter;
    private RecyclerView recyclerView;
    private TextView textView;

    private static final String TAG = "AdvSearchListFrag: ";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_advanced_search_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        textView = view.findViewById(R.id.textViewAdvancedSearchList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView = view.findViewById(R.id.recyclerViewAdvancedSearch);
        recyclerView.setLayoutManager(layoutManager);

        bookList = new ArrayList<>();

        super.onViewCreated(view, savedInstanceState);
        String query = AdvancedSearchListFragmentArgs.fromBundle(getArguments()).getQuery();

        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        final Observer<List<Book>> observer = new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {
                takeInfoList(books);
                Log.d(TAG, "msg: " + bookList);
                SearchAdapter searchAdapter = new SearchAdapter(bookList, getActivity(), new SearchAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BookInfo book) {
                        Log.d(TAG, "elemento premuto: " + book.getTitle());
                        AdvancedSearchListFragmentDirections.ShowSearchAdvancedBookDetailAction action =
                                AdvancedSearchListFragmentDirections.showSearchAdvancedBookDetailAction(book);
                        Navigation.findNavController(view).navigate(action);
                    }
                });
                recyclerView.setAdapter(searchAdapter);
            }
        };
        searchViewModel.getBooks(query).observe(getViewLifecycleOwner(), observer);
    }

    private Boolean takeInfoList(List<Book> books) {
        bookList.clear();
        Book book;
        BookInfo b;
        Log.d(TAG, "books " + books);
        if(books != null) {
            for (int i = 0; i < books.size(); i++) {
                book = books.get(i);
                String content = book.toString();
                b = takeInfoBook(content);
                bookList.add(b);
                //Log.d(TAG, "book: " + b);
            }
            //Log.d(TAG, "book: " + bookList);
            return true;
        }
        textView.setText(R.string.NoResults);
        textView.setGravity(Gravity.CENTER);
        return false;
    }

    private BookInfo takeInfoBook(String str) {
        //Log.d(TAG, "stringa: " + str);
        String title;
        int indexTitle = str.indexOf("title=") + 6;
        int indexEqual = str.indexOf("=", indexTitle);
        String subString = str.substring(indexTitle, indexEqual);
        int indexComma = subString.lastIndexOf(",");
        title = subString.substring(0, indexComma);
        Log.d(TAG, "titolo: " + title);

        String authors;
        int indexSquare1;
        int indexSquare2;
        int indexAuthor = str.indexOf("authors=");
        if(indexAuthor > 0){
            indexSquare1 = str.indexOf("[", indexAuthor) + 1;
            indexSquare2 = str.indexOf("]", indexAuthor);
            authors = str.substring(indexSquare1, indexSquare2);

        }
        else{
            authors = null;
        }
        Log.d(TAG, "autori: " + authors);

        String publisher;
        int indexPublisher = str.indexOf("publisher=");
        if(indexPublisher > 0){
            indexPublisher = indexPublisher + 10;
            indexEqual = str.indexOf("=", indexPublisher);
            subString = str.substring(indexPublisher, indexEqual);
            indexComma = subString.lastIndexOf(",");
            publisher = subString.substring(0, indexComma);
            //
        }
        else{
            publisher = null;
        }
        Log.d(TAG, "editori: " + publisher);

        String publisherDate;
        int indexPublisherDate = str.indexOf("publishedDate=");
        if(indexPublisherDate > 0){
            indexPublisherDate = indexPublisherDate + 14;
            indexEqual = str.indexOf("=", indexPublisherDate);
            subString = str.substring(indexPublisherDate, indexEqual);
            indexComma = subString.lastIndexOf(",");
            publisherDate = subString.substring(0, indexComma);
            //
        }
        else{
            publisherDate = null;
        }
        Log.d(TAG, "data pubblicazione: " + publisherDate);

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
        Log.d(TAG, "ISBN 1: " + isbn);
        Log.d(TAG, "ISBN 1: " + isbn1);

        String categories;
        int indexCategories = str.indexOf("categories=");
        if(indexCategories > 0){
            indexSquare1 = str.indexOf("[", indexCategories) + 1;
            indexSquare2 = str.indexOf("]", indexCategories);
            categories = str.substring(indexSquare1, indexSquare2);
            //
        }
        else{
            categories = null;
        }
        Log.d(TAG, "categorie: " + categories);

        String thumbnail;
        int indexThumbnail = str.indexOf("thumbnail=");
        if(indexThumbnail > 0){
            indexThumbnail = indexThumbnail + 10;
            indexCurly1 = str.indexOf("}", indexThumbnail);
            thumbnail = str.substring(indexThumbnail, indexCurly1);
            //
        }
        else{
            thumbnail = null;
        }
        Log.d(TAG, "miniatura: " + thumbnail);

        List<ISBN> isbnList = new ArrayList<>();
        //Log.d(TAG, "lista isbn: " + isbnList);
        isbnList.add(isbn);
        isbnList.add(isbn1);
        //Log.d(TAG, "lista isbn: " + isbnList);
        BookInfo book = new BookInfo(title, authors, publisher, publisherDate, isbnList, categories, thumbnail);
        return book;
    }
}
