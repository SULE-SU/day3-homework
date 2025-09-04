package pos.machine;

import java.util.*;

public class PosMachine {

    public String printReceipt(List<String> barcodes) {
        List<Item> allItems = ItemsLoader.loadAllItems();
        Map<String, Item> itemIndex = buildItemIndex(allItems);

        validateBarcodes(barcodes, itemIndex);

        Map<String, Integer> counts = countBarcodes(barcodes);
        List<ReceiptItem> receiptItems = createReceiptItems(counts, itemIndex);

        return formatReceipt(receiptItems);
    }
    // 将商品列表索引为 Map，为什么不用findItemByBarcode原因是当商品数量很大的时候，会反复遍历列表，耗时很长
    // 如果购物车有10件商品，需要查找10次
    // 每次查找最多需要遍历1000个商品
    // 最大查找总次数：10,000次
    Map<String, Item> buildItemIndex(List<Item> items) {
        Map<String, Item> index = new LinkedHashMap<>();
        for (Item it : items) {
            index.put(it.getBarcode(), it);
        }
        return index;
    }



    // 统计条码数量
    Map<String, Integer> countBarcodes(List<String> barcodes) {
        Map<String, Integer> counts = new LinkedHashMap<>();
        for (String code : barcodes) {
            counts.put(code, counts.getOrDefault(code, 0) + 1);
        }
        return counts;
    }


    // 小票条目
    List<ReceiptItem> createReceiptItems(Map<String, Integer> counts, Map<String, Item> itemIndex) {
        List<ReceiptItem> lines = new ArrayList<>();
        Set<String> keys = counts.keySet();

        for (String code : keys) {
            Integer qty = counts.get(code);
            Item item = itemIndex.get(code);
            lines.add(new ReceiptItem(item, qty));
        }

        return lines;
    }

    // 校验条码
    void validateBarcodes(List<String> barcodes, Map<String, Item> itemIndex) {
        for (String code : barcodes) {
            if (!itemIndex.containsKey(code)) {
                throw new IllegalArgumentException("Unknown barcode: " + code);
            }
        }
    }

    // 计算总价
    int calculateTotal(List<ReceiptItem> items) {
        int total = 0;
        for (ReceiptItem it : items) {
            total += it.getSubtotal();
        }
        return total;
    }

    // 格式化整张小票
    String formatReceipt(List<ReceiptItem> items) {
        StringBuilder sb = new StringBuilder();
        sb.append("***<store earning no money>Receipt***\n");
        for (ReceiptItem it : items) {
            sb.append(formatReceiptLine(it)).append("\n");
        }
        sb.append("----------------------\n");
        sb.append("Total: ").append(calculateTotal(items)).append(" (yuan)\n");
        sb.append("**********************");
        return sb.toString();
    }

    // 格式化单行
    String formatReceiptLine(ReceiptItem it) {
        return "Name: " + it.getName() +
                ", Quantity: " + it.getQuantity() +
                ", Unit price: " + it.getUnitPrice() + " (yuan)" +
                ", Subtotal: " + it.getSubtotal() + " (yuan)";
    }

}