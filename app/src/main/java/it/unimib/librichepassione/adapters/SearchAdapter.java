package it.unimib.librichepassione.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

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
        ImageView imageViewBook;

        public SearchViewHolder(View view) {
            super(view);
            textViewBookTitle = view.findViewById(R.id.textViewSearchItemTitle);
            textViewBookAuthors = view.findViewById(R.id.textViewSearchItemAuthors);
            imageViewBook = view.findViewById(R.id.imageViewSearchItem);
        }

        public void bind(BookInfo book, OnItemClickListener onItemClickListener){
            textViewBookTitle.setText(book.getTitle());
            textViewBookAuthors.setText(book.getAuthor());

            String URL;

            if(book.getThumbnail() != null) {
                Log.d(TAG, "url:" + book.getThumbnail());
                URL = book.getThumbnail().replace("http", "https");
                Picasso.get().load(URL).into(imageViewBook);
            }
            else {
                URL = "https://cdn1.iconfinder.com/data/icons/error-warning-triangles/24/more-alt-triangle-128.png";
                Picasso.get().load(URL).into(imageViewBook);
            }

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
