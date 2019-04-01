package com.kor.foodmanager.data.model;

import java.util.List;

public class HebcalDto {
    private String date, link, title;
    private List<HebcalItemDto> items;

    public HebcalDto() {
    }

    public HebcalDto(String date, String link, String title, List<HebcalItemDto> items) {
        this.date = date;
        this.link = link;
        this.title = title;
        this.items = items;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public List<HebcalItemDto> getItems() {
        return items;
    }

    public void setItems(List<HebcalItemDto> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "HebcalDto{" +
                "date='" + date + '\'' +
                ", link='" + link + '\'' +
                ", title='" + title + '\'' +
                ", items=" + items +
                '}';
    }
}
