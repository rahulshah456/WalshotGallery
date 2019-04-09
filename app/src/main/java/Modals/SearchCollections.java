package Modals;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class SearchCollections {
    @SerializedName("results")
    private List<ResultsItem> results;
    @SerializedName("total")
    private int total;
    @SerializedName("total_pages")
    private int totalPages;

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalPages() {
        return this.totalPages;
    }

    public void setResults(List<ResultsItem> results) {
        this.results = results;
    }

    public List<ResultsItem> getResults() {
        return this.results;
    }
}
