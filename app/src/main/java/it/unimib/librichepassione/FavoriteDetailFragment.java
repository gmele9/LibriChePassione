package it.unimib.librichepassione;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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

public class FavoriteDetailFragment extends Fragment {
    private TextView textViewTitle;
    private TextView textViewAuthor;
    private TextView textViewPublisher;
    private TextView textViewPublisherDate;
    private TextView textViewISBN;
    private TextView textViewCategories;
    private ImageView imageViewThumbnail;
    private Button buttonRemovePreferences;
    private SharedPreferences sp;
    private static final String TAG = "FavDetailFragment";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewTitle = view.findViewById(R.id.textViewFavoriteDetailTitle);
        textViewAuthor = view.findViewById(R.id.textViewFavoriteDetailAuthor);
        textViewPublisher = view.findViewById(R.id.textViewFavoriteDetailPublisher);
        textViewPublisherDate = view.findViewById(R.id.textViewFavoriteDetailPublisherDate);
        textViewISBN = view.findViewById(R.id.textViewFavoriteDetailISBN);
        textViewCategories = view.findViewById(R.id.textViewFavoriteDetailCategories);
        imageViewThumbnail = view.findViewById(R.id.imageViewFavoriteDetail);

        BookInfo book = FavoriteDetailFragmentArgs.fromBundle(getArguments()).getBook();
        Log.d(TAG, "book: " + book);

        textViewTitle.setText(book.getTitle());
        textViewAuthor.setText(book.getAuthor());
        textViewPublisher.setText(book.getPublisher());
        textViewPublisherDate.setText(book.getPublishedDate());
        textViewCategories.setText(book.getCategories());

        String isbn = "";
        for (int i = 0; i < book.getIndustryIdentifiers().size(); i++) {
            if (i == book.getIndustryIdentifiers().size() - 1)
                isbn = isbn + book.getIndustryIdentifiers().get(i).getType().replace("_", " ") + "= " +
                        book.getIndustryIdentifiers().get(i).getIdentifier();
            else
                isbn = isbn + book.getIndustryIdentifiers().get(i).getType().replace("_", " ") + "= " +
                        book.getIndustryIdentifiers().get(i).getIdentifier() + "\n";
        }
        textViewISBN.setText(isbn);
        String url = book.getThumbnail().replace("http", "https");
        Picasso.get().load(url).into(imageViewThumbnail);

        buttonRemovePreferences = view.findViewById(R.id.buttonRemovePreferences);
        //SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("MY_USER_PREF", Context.MODE_PRIVATE);
        buttonRemovePreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeFavorite(book);
                Navigation.findNavController(view).navigate(FavoriteDetailFragmentDirections.showFavoriteFragmentAction());

                Toast toast = Toast.makeText(getActivity(), book.getTitle() + " rimosso dai preferiti", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

    public void removeFavorite(BookInfo bookInfo) {
        int trovato =  -1;

        String gStringInput;

        List<BookInfo> bookInfoList = new ArrayList<>();

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("MY_USER_PREF", Context.MODE_PRIVATE);
        String gString = sharedPreferences.getString("list", "");

        Gson gson = new Gson();

        Type type = new TypeToken<List<BookInfo>>() {}.getType();

        bookInfoList = gson.fromJson(gString, type);

        Log.d(TAG, "booklist" + bookInfoList);

        for (int i = 0; i < bookInfoList.size(); i++) {
            if (bookInfo.getIndustryIdentifiers().get(0).getIdentifier()
                    .equals(bookInfoList.get(i).getIndustryIdentifiers().get(0).getIdentifier())) {
                trovato = i;
                break;
            }
        }

        bookInfoList.remove(trovato);
        gStringInput = gson.toJson(bookInfoList);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("list", gStringInput);

        editor.commit();

    }
}
