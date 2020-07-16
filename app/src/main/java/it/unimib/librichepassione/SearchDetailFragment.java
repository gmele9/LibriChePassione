package it.unimib.librichepassione;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import it.unimib.librichepassione.model.BookInfo;


public class SearchDetailFragment extends Fragment {

    private TextView textViewTitle;
    private TextView textViewAuthor;
    private TextView textViewPublisher;
    private TextView textViewPublisherDate;
    private TextView textViewISBN;
    private TextView textViewCategories;
    private ImageView imageViewThumbnail;
    private Button buttonAddPreferences;
    private SharedPreferences sp;
    private static final String TAG = "SearchDetailFragment";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewTitle = view.findViewById(R.id.textViewSearchDetailTitle);
        textViewAuthor = view.findViewById(R.id.textViewSearchDetailAuthor);
        textViewPublisher = view.findViewById(R.id.textViewSearchDetailPublisher);
        textViewPublisherDate = view.findViewById(R.id.textViewSearchDetailPublisherDate);
        textViewISBN = view.findViewById(R.id.textViewSearchDetailISBN);
        textViewCategories = view.findViewById(R.id.textViewSearchDetailCategories);
        imageViewThumbnail = view.findViewById(R.id.imageViewSearchDetail);

        BookInfo book = SearchDetailFragmentArgs.fromBundle(getArguments()).getBook();
        Log.d(TAG, "book" + book);

        Log.d(TAG, "titolo: " + book.getTitle());
        if(book.getTitle() != null) {
            textViewTitle.setText(book.getTitle());
        }

        Log.d(TAG, "autore: " + book.getAuthor());
        if(book.getAuthor() != null) {
            textViewAuthor.setText(book.getAuthor());
        }

        Log.d(TAG, "publisher: " + book.getPublisher());
        if(book.getPublisher() != null) {
            textViewPublisher.setText(book.getPublisher());
        }

        Log.d(TAG, "data: " + book.getPublishedDate());
        if(book.getPublishedDate() != null) {
            textViewPublisherDate.setText(book.getPublishedDate());
        }

        Log.d(TAG, "cat: " + book.getCategories());
        if(book.getCategories() != null) {
            textViewCategories.setText(book.getCategories());
        }

        String isbn = "";
        Log.d(TAG, "ID: " + book.getIndustryIdentifiers());
        for (int i = 0; i < book.getIndustryIdentifiers().size(); i++) {
            if (i == book.getIndustryIdentifiers().size() - 1) {
                if(book.getIndustryIdentifiers().get(i).getType() != null)
                isbn = isbn + book.getIndustryIdentifiers().get(i).getType().replace("_", " ") + "= " +
                        book.getIndustryIdentifiers().get(i).getIdentifier();
            }
            else {
                if(book.getIndustryIdentifiers().get(i).getType() != null)
                    isbn = isbn + book.getIndustryIdentifiers().get(i).getType().replace("_", " ") + "= " +
                        book.getIndustryIdentifiers().get(i).getIdentifier() + "\n";
            }
        }
        textViewISBN.setText(isbn);

        String url;
        if(book.getThumbnail() != null) {
            url = book.getThumbnail().replace("http", "https");
            Picasso.get().load(url).into(imageViewThumbnail);
        }
        else{
            url = "https://cdn1.iconfinder.com/data/icons/error-warning-triangles/24/more-alt-triangle-128.png";
            Picasso.get().load(url).into(imageViewThumbnail);
        }

        buttonAddPreferences = view.findViewById(R.id.buttonAddPreferences);
        //SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("MY_USER_PREF", Context.MODE_PRIVATE);
        buttonAddPreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFavorite(book);
            }
        });
    }

    public void addFavorite(BookInfo bookInfo) {
        Boolean trovato = false;

        String gStringInput;

        List<BookInfo> bookInfoList = new ArrayList<>();

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("MY_USER_PREF", Context.MODE_PRIVATE);
        String gString = sharedPreferences.getString("list", "");

        Gson gson = new Gson();

        Type type = new TypeToken<List<BookInfo>>() {}.getType();

        bookInfoList = gson.fromJson(gString, type);

        Log.d(TAG, "booklist" + bookInfoList);

        if (bookInfoList != null) {

            for (int i = 0; i < bookInfoList.size(); i++) {
                if (bookInfo.getIndustryIdentifiers().get(0).getIdentifier()
                        .equals(bookInfoList.get(i).getIndustryIdentifiers().get(0).getIdentifier())) {
                    trovato = true;
                    break;
                }
            }

            if (trovato) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.ErrorTitle);
                builder.setMessage("'" + bookInfo.getTitle() + "' " + getResources().getString(R.string.ErrorFavorites));
                builder.show();
            } else {
                Toast toast = Toast.makeText(getActivity(),  bookInfo.getTitle() + " "
                        + getResources().getString(R.string.AddedToFavorites), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                bookInfoList.add(bookInfo);
                gStringInput = gson.toJson(bookInfoList);

                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("list", gStringInput);

                editor.commit();
            }
        }
        else {
            Log.d(TAG,"dentro else");
            List<BookInfo> infoList = new ArrayList<>();
            infoList.add(bookInfo);
            gStringInput = gson.toJson(infoList);

            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString("list", gStringInput);

            editor.commit();
        }
    }

}