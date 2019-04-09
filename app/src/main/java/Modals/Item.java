package Modals;

public class Item {
    public final int icon;
    public final String text;

    public Item(String text, Integer icon) {
        this.text = text;
        this.icon = icon.intValue();
    }

    public String toString() {
        return this.text;
    }
}
