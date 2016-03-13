package com.changwen.hibernate4.session.sessionPojo;

import java.sql.Blob;
import java.util.Date;

/**
 * <b>function:</b>
 *
 * @author liucw on 2016/3/13.
 */
public class News2 {
    private Integer id;
    private String title;
    private String author;

    private Date occurrenceDate;

    //该属性值为： title: author
    private String desc;

    //大文本
    private String content;
    //二进制数据
    private Blob image;

    public News2(String title, String author, Date occurrenceDate) {
        super();
        this.title = title;
        this.author = author;
        this.occurrenceDate = occurrenceDate;
    }

    public News2() {
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

   public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

}
