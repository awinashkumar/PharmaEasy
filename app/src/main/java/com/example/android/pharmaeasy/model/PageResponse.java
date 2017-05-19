package com.example.android.pharmaeasy.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Awinash on 17-05-2017.
 */

public class PageResponse {
    @SerializedName("page")
    private int page;
    @SerializedName("per_page")
    private int perPage;
    @SerializedName("total")
    private int total;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("data")
    private ArrayList<Page> data;

    public PageResponse(int page, int perPage, int total, int totalPages, ArrayList<Page> data) {
        this.page = page;
        this.perPage = perPage;
        this.total = total;
        this.totalPages = totalPages;
        this.data = data;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public ArrayList<Page> getData() {
        return data;
    }

    public void setData(ArrayList<Page> data) {
        this.data = data;
    }
}
