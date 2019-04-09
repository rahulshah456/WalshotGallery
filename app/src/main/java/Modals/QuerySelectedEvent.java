package Modals;

public class QuerySelectedEvent {
    String query;

    public QuerySelectedEvent(String order) {
        this.query = order;
    }

    public String getQuery() {
        return this.query;
    }
}
