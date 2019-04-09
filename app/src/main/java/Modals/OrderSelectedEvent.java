package Modals;

public class OrderSelectedEvent {
    String order;

    public OrderSelectedEvent(String order) {
        this.order = order;
    }

    public String getOrder() {
        return this.order;
    }
}
