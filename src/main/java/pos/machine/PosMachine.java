package pos.machine;

import java.util.*;

public class PosMachine {

    public String printReceipt(List<String> barcodes) {

    }
    // 建立条码索引
    Map<String, Item> buildItemIndex(List<Item> items) {

    }


    // 统计条码数量
    Map<String, Integer> countBarcodes(List<String> barcodes) {
    }

    // 小票条目
    List<ReceiptItem> createReceiptItems(Map<String, Integer> counts, Map<String, Item> itemIndex) {

    }

    // 校验条码
    void validateBarcodes(List<String> barcodes, Map<String, Item> itemIndex) {

    }

    // 计算总价
    int calculateTotal(List<ReceiptItem> items) {

    }

    // 格式化整张小票
    String formatReceipt(List<ReceiptItem> items) {

    }

    // 格式化单行
    String formatReceiptLine(ReceiptItem it) {

    }
}