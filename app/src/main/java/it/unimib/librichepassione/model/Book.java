package it.unimib.librichepassione.model;

import java.util.List;

public class Book {
    private Object volumeInfo;

    public Book(Object volumeInfo, String title, String subtitle, List<String> author, String publisher, String publishedDate, List<ISBN> industryIdentifiers, List<String> categories, String thumbnail) {
        this.volumeInfo = volumeInfo;
    }

    public Object getVolumeInfo() {
        return volumeInfo;
    }

    public void setVolumeInfo(Object volumeInfo) {
        this.volumeInfo = volumeInfo;
    }

    @Override
    public String toString() {
        return "Book{" +
                "volume ='" + volumeInfo + '\'' + '}';
    }
}
