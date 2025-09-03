package pos.machine;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PosMachineTest {

    @Test
    void should_return_receipt() {
        PosMachine posMachine = new PosMachine();

        String expected = "***<store earning no money>Receipt***\n" +
                "Name: Coca-Cola, Quantity: 4, Unit price: 3 (yuan), Subtotal: 12 (yuan)\n" +
                "Name: Sprite, Quantity: 2, Unit price: 3 (yuan), Subtotal: 6 (yuan)\n" +
                "Name: Battery, Quantity: 3, Unit price: 2 (yuan), Subtotal: 6 (yuan)\n" +
                "----------------------\n" +
                "Total: 24 (yuan)\n" +
                "**********************";

        String actual = posMachine.printReceipt(loadBarcodes());
        System.out.println("=== Baseline Receipt ===\n" + actual);
        assertEquals(expected, actual);
    }

    @Test
    void should_throw_on_unknown_barcode() {
        PosMachine pos = new PosMachine();
        assertThrows(IllegalArgumentException.class,
                () -> pos.printReceipt(Arrays.asList("ITEM000000", "UNKNOWN")));
    }

    @Test
    void should_return_receipt_for_empty_input() {
        PosMachine pos = new PosMachine();
        String actual = pos.printReceipt(Collections.emptyList());
        System.out.println("=== Empty Receipt ===\n" + actual);

        String expected = "***<store earning no money>Receipt***\n" +
                "----------------------\n" +
                "Total: 0 (yuan)\n" +
                "**********************";
        assertEquals(expected, actual);
    }

    @Test
    void should_return_receipt_for_single_known_item() {
        PosMachine pos = new PosMachine();
        String actual = pos.printReceipt(Arrays.asList("ITEM000004"));
        System.out.println("=== Single Item Receipt ===\n" + actual);

        String expected = "***<store earning no money>Receipt***\n" +
                "Name: Battery, Quantity: 1, Unit price: 2 (yuan), Subtotal: 2 (yuan)\n" +
                "----------------------\n" +
                "Total: 2 (yuan)\n" +
                "**********************";
        assertEquals(expected, actual);
    }

    @Test
    void should_preserve_first_appearance_order_and_print_receipt() {
        PosMachine pos = new PosMachine();
        var barcodes = Arrays.asList(
                "ITEM000001", // Sprite
                "ITEM000000", // Coca-Cola
                "ITEM000000", // Coca-Cola
                "ITEM000004", // Battery
                "ITEM000004"  // Battery
        );

        String actual = pos.printReceipt(barcodes);
        System.out.println("=== Order Preservation Receipt ===\n" + actual);

        String expected = "***<store earning no money>Receipt***\n" +
                "Name: Sprite, Quantity: 1, Unit price: 3 (yuan), Subtotal: 3 (yuan)\n" +
                "Name: Coca-Cola, Quantity: 2, Unit price: 3 (yuan), Subtotal: 6 (yuan)\n" +
                "Name: Battery, Quantity: 2, Unit price: 2 (yuan), Subtotal: 4 (yuan)\n" +
                "----------------------\n" +
                "Total: 13 (yuan)\n" +
                "**********************";

        assertEquals(expected, actual);
    }

    @Test
    void should_throw_when_unknown_barcode_in_the_middle() {
        PosMachine pos = new PosMachine();
        assertThrows(IllegalArgumentException.class,
                () -> pos.printReceipt(Arrays.asList("ITEM000000", "UNKNOWN", "ITEM000001")));
    }

    private static List<String> loadBarcodes() {
        return Arrays.asList(
                "ITEM000000", "ITEM000000", "ITEM000000", "ITEM000000",
                "ITEM000001", "ITEM000001",
                "ITEM000004", "ITEM000004", "ITEM000004"
        );
    }


}
