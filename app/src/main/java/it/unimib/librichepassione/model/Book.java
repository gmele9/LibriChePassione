package it.unimib.librichepassione.model;

import java.util.List;

public class Book {
    private Object volumeInfo;
//    private String title;
//    private String subtitle;
//    private List<String> author;
//    private String publisher;
//    private String publishedDate;
//    private List<ISBN> industryIdentifiers;
//    private List<String> categories;
//    private String thumbnail;

    public Book(Object volumeInfo, String title, String subtitle, List<String> author, String publisher, String publishedDate, List<ISBN> industryIdentifiers, List<String> categories, String thumbnail) {
        this.volumeInfo = volumeInfo;
//        this.title = title;
//        this.subtitle = subtitle;
//        this.author = author;
//        this.publisher = publisher;
//        this.publishedDate = publishedDate;
//        this.industryIdentifiers = industryIdentifiers;
//        this.categories = categories;
//        this.thumbnail = thumbnail;
    }

//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getSubtitle() {
//        return subtitle;
//    }
//
//    public void setSubtitle(String subtitle) {
//        this.subtitle = subtitle;
//    }
//
//    public List<String> getAuthor() {
//        return author;
//    }
//
//    public void setAuthor(List<String> author) {
//        this.author = author;
//    }
//
//    public String getPublisher() {
//        return publisher;
//    }
//
//    public void setPublisher(String publisher) {
//        this.publisher = publisher;
//    }
//
//    public String getPublishedDate() {
//        return publishedDate;
//    }
//
//    public void setPublishedDate(String publishedDate) {
//        this.publishedDate = publishedDate;
//    }
//
//    public List<ISBN> getIndustryIdentifiers() {
//        return industryIdentifiers;
//    }
//
//    public void setIndustryIdentifiers(List<ISBN> industryIdentifiers) {
//        this.industryIdentifiers = industryIdentifiers;
//    }
//
//    public List<String> getCategories() {
//        return categories;
//    }
//
//    public void setCategories(List<String> categories) {
//        this.categories = categories;
//    }
//
//    public String getThumbnail() {
//        return thumbnail;
//    }
//
//    public void setThumbnail(String thumbnail) {
//        this.thumbnail = thumbnail;
//    }

    public Object getVolumeInfo() {
        return volumeInfo;
    }

    public void setVolumeInfo(Object volumeInfo) {
        this.volumeInfo = volumeInfo;
    }

    @Override
    public String toString() {
        return "Book{" +
                "volume ='" + volumeInfo + '\'' +
//                "title='" + title + '\'' +
//                ", subtitle='" + subtitle + '\'' +
//                ", author=" + author +
//                ", publisher='" + publisher + '\'' +
//                ", publishedDate='" + publishedDate + '\'' +
//                ", industryIdentifiers=" + industryIdentifiers +
//                ", categories=" + categories +
//                ", thumbnail='" + thumbnail + '\'' +
                '}';
    }
}
