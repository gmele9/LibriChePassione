package it.unimib.librichepassione;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import it.unimib.librichepassione.databinding.FragmentAdvancedSearchBinding;
import it.unimib.librichepassione.model.Book;
import it.unimib.librichepassione.model.BookInfo;
import it.unimib.librichepassione.model.ISBN;
import it.unimib.librichepassione.viewModels.SearchViewModel;

public class AdvancedSearchFragment extends Fragment {

    private static final String TAG = "AdvancedSearch: ";
    private FragmentAdvancedSearchBinding binding;
    private String textISBN;
    private String query;
    private String textAuthor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAdvancedSearchBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.buttonISBN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textISBN = binding.editTextISBNId.getText().toString();
                if(!textISBN.isEmpty()) {
                    query = "isbn:" + textISBN;
                    AdvancedSearchFragmentDirections.ShowAdvancedSearchList action =
                            AdvancedSearchFragmentDirections.showAdvancedSearchList(query);
                    Navigation.findNavController(view).navigate(action);

                }
                else{
                    AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                    builder.setTitle(R.string.ErrorTitle);
                    builder.setMessage(R.string.ErrorISBN);
                    builder.show();
                }
            }
        });

        binding.buttonAdvancedSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textAuthor = binding.editTextAuthor.getText().toString();
                if(!textAuthor.isEmpty()){
                    query = "inauthor:" + textAuthor;
                    AdvancedSearchFragmentDirections.ShowAdvancedSearchList action =
                            AdvancedSearchFragmentDirections.showAdvancedSearchList(query);
                    Navigation.findNavController(view).navigate(action);
                }
                else{
                    AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                    builder.setTitle(R.string.ErrorTitle);
                    builder.setMessage(R.string.ErrorAuthor);
                    builder.show();
                }
            }
        });
        return view;
    }
}
