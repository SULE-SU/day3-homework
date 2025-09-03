package pos.machine;

public class ReceiptItem {
    private final Item item;
    private final int quantity;

    public ReceiptItem(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public String getName() {
        return item.getName();
    }

    public int getUnitPrice() {
        return item.getPrice();
    }

    public int getQuantity() {
        return quantity;
    }

    public int getSubtotal() {
        return item.getPrice() * quantity;
    }
}