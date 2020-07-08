package it.unimib.librichepassione.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class BookInfo implements Parcelable {
    private String title;
    private String author;
    private String publisher;
    private String publishedDate;
    private List<ISBN> industryIdentifiers;
    private String categories;
    private String thumbnail;

    public BookInfo(String title, String author, String publisher,
                    String publishedDate, List<ISBN> industryIdentifiers, String categories, String thumbnail) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.industryIdentifiers = industryIdentifiers;
        this.categories = categories;
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public List<ISBN> getIndustryIdentifiers() {
        return industryIdentifiers;
    }

    public void setIndustryIdentifiers(List<ISBN> industryIdentifiers) {
        this.industryIdentifiers = industryIdentifiers;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString() {
        return "BookInfo{" +
                "title='" + title + '\'' +
                ", author=" + author +
                ", publisher='" + publisher + '\'' +
                ", publishedDate='" + publishedDate + '\'' +
                ", industryIdentifiers=" + industryIdentifiers +
                ", categories=" + categories +
                ", thumbnail='" + thumbnail + '\'' +
                '}';
    }

    protected BookInfo(Parcel in) {
        title = in.readString();
        author = in.readString();
        publisher = in.readString();
        publishedDate = in.readString();
        if (in.readByte() == 0x01) {
            industryIdentifiers = new ArrayList<ISBN>();
            in.readList(industryIdentifiers, ISBN.class.getClassLoader());
        } else {
            industryIdentifiers = null;
        }
        categories = in.readString();
        thumbnail = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(publisher);
        dest.writeString(publishedDate);
        if (industryIdentifiers == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(industryIdentifiers);
        }
        dest.writeString(categories);
        dest.writeString(thumbnail);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<BookInfo> CREATOR = new Parcelable.Creator<BookInfo>() {
        @Override
        public BookInfo createFromParcel(Parcel in) {
            return new BookInfo(in);
        }

        @Override
        public BookInfo[] newArray(int size) {
            return new BookInfo[size];
        }
    };
}
