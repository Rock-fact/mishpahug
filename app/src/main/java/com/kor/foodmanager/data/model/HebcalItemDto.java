package com.kor.foodmanager.data.model;

public class HebcalItemDto {
    private String link, title, hebrew, date, category, memo;

    public HebcalItemDto(String link, String title, String hebrew, String date, String category, String memo) {
        this.link = link;
        this.title = title;
        this.hebrew = hebrew;
        this.date = date;
        this.category = category;
        this.memo = memo;
    }

    public HebcalItemDto() {
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHebrew() {
        return hebrew;
    }

    public void setHebrew(String hebrew) {
        this.hebrew = hebrew;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "HebcalItemDto{" +
                "link='" + link + '\'' +
                ", title='" + title + '\'' +
                ", hebrew='" + hebrew + '\'' +
                ", date='" + date + '\'' +
                ", category='" + category + '\'' +
                ", memo='" + memo + '\'' +
                '}';
    }
}
