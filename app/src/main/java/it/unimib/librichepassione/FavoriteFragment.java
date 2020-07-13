package it.unimib.librichepassione;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import it.unimib.librichepassione.adapters.SearchAdapter;
import it.unimib.librichepassione.model.BookInfo;

public class FavoriteFragment extends Fragment {

    private static final String TAG = "FavFragment: ";
    private SearchAdapter searchAdapter;
    private RecyclerView recyclerView;
    private TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<BookInfo> bookInfoList = new ArrayList<>();

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("MY_USER_PREF", Context.MODE_PRIVATE);
        String gString = sharedPreferences.getString("list", "");

        Gson gson = new Gson();

        Type type = new TypeToken<List<BookInfo>>(){}.getType();

        bookInfoList = gson.fromJson(gString, type);

        Log.d(TAG, "booklist: " + bookInfoList);

        textView = view.findViewById(R.id.textViewFavoriteFragment);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView = view.findViewById(R.id.recyclerViewFavoriteFragment);
        recyclerView.setLayoutManager(layoutManager);

        if(bookInfoList == null || bookInfoList.isEmpty()){
            textView.setText("Nessun elemento aggiunto ai preferiti");
        }
        else{
            SearchAdapter searchAdapter = new SearchAdapter(bookInfoList, getActivity(), new SearchAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BookInfo book) {
                    FavoriteFragmentDirections.ShowFavoriteBookDetailAction action =
                            FavoriteFragmentDirections.showFavoriteBookDetailAction(book);
                    Navigation.findNavController(view).navigate(action);
                }
            });
            recyclerView.setAdapter(searchAdapter);
        }

        //Log.d(TAG, "booklist title: " + bookInfoList.get(0).getTitle());

    }

}
