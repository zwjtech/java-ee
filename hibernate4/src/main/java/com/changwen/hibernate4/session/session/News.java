package com.changwen.hibernate4.session.session;

import java.sql.Blob;
import java.util.Date;

/**
 * News
 *
 * @author lcw 2015/12/23
 */
public class News {
    private Integer id;
    private String title;
    private String author;
    private Date occurrenceDate;

    public News(String title, String author, Date occurrenceDate) {
        super();
        this.title = title;
        this.author = author;
        this.occurrenceDate = occurrenceDate;
    }

    public News() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getOccurrenceDate() {
        return occurrenceDate;
    }

    public void setOccurrenceDate(Date occurrenceDate) {
        this.occurrenceDate = occurrenceDate;
    }

    @Override
    public String toString() {
        return "News [id=" + id + ", title=" + title + ", author=" + author
                + ", occurrenceDate=" + occurrenceDate + "]";
    }


}
