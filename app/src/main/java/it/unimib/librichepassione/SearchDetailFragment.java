package it.unimib.librichepassione;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import it.unimib.librichepassione.model.BookInfo;


public class SearchDetailFragment extends Fragment {

    private TextView textViewTitle;
    private static final String TAG = "SearchDetailFragment";

//    public SearchDetailFragment() {
//        // Required empty public constructor
//    }
//
//    public static SearchDetailFragment newInstance(String param1, String param2) {
//        SearchDetailFragment fragment = new SearchDetailFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

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
        BookInfo book = SearchDetailFragmentArgs.fromBundle(getArguments()).getBook();
        textViewTitle.setText(book.getTitle());
        Log.d(TAG, "dettaglio:" + book);
    }
}
