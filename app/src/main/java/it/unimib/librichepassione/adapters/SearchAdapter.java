package it.unimib.librichepassione.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.unimib.librichepassione.R;
import it.unimib.librichepassione.model.BookInfo;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    public interface OnItemClickListener{
        void onItemClick(BookInfo book);
    }

    private List<BookInfo> bookList;
    private LayoutInflater layoutInflater;
    private static final String TAG = "SearchAdapter: ";
    private OnItemClickListener onItemClickListener;

    public static class SearchViewHolder extends RecyclerView.ViewHolder {

        TextView textViewBookTitle;
        TextView textViewBookAuthors;

        public SearchViewHolder(View view) {
            super(view);
            textViewBookTitle = view.findViewById(R.id.textViewSearchItemTitle);
            textViewBookAuthors = view.findViewById(R.id.textViewSearchItemAuthors);
        }

        public void bind(BookInfo book, OnItemClickListener onItemClickListener){
            textViewBookTitle.setText(book.getTitle());
            textViewBookAuthors.setText(book.getAuthor());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(book);
                }
            });
        }

    }

    public SearchAdapter(List<BookInfo> bookList, Context context, OnItemClickListener onItemClickListener) {
        if(bookList != null) {
            this.bookList = bookList;
            this.layoutInflater = LayoutInflater.from(context);
            this.onItemClickListener = onItemClickListener;
            Log.d(TAG, "libri: " + bookList);
        }
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.search_item, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
//        holder.textViewBookTitle.setText(bookList.get(position).getTitle());
//        holder.textViewBookAuthors.setText(bookList.get(position).getAuthor());
        holder.bind(bookList.get(position), this.onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }
}
