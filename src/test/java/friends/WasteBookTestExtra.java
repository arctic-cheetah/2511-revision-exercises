package friends;

import java.util.Iterator;

import org.junit.jupiter.api.Test;

import unsw.friends.NetworkIterator;
import unsw.friends.WasteBookController;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WasteBookTestExtra {

    @Test
    public void testIteratorAddPersonToNetwork() {
        WasteBookController<String> controller = new WasteBookController<String>();

        controller.addPersonToNetwork("Nathan");
        controller.addPersonToNetwork("Evanlyn");
        controller.addPersonToNetwork("Amelia");
        controller.addPersonToNetwork("Hamish");

        controller.follow("Nathan", "Evanlyn");
        controller.follow("Amelia", "Evanlyn");
        controller.follow("Amelia", "Hamish");
        controller.follow("Hamish", "Amelia");
        controller.follow("Nathan", "Amelia");
        controller.follow("Hamish", "Evanlyn");

        Iterator<String> iter = controller.getIterator("popularity");
        assertEquals("Evanlyn", iter.next());
        controller.addPersonToNetwork("Darcy");
        controller.follow("Nathan", "Darcy");
        controller.follow("Amelia", "Darcy");

        assertEquals("Amelia", iter.next());
        assertEquals("Darcy", iter.next());
        assertEquals("Hamish", iter.next());
        assertEquals("Nathan", iter.next());
        assertEquals(false, iter.hasNext());
    }

    @Test
    public void testIteratorChangeMidway() {
        WasteBookController<String> controller = new WasteBookController<String>();

        controller.addPersonToNetwork("Nathan");
        controller.addPersonToNetwork("Evanlyn");
        controller.addPersonToNetwork("Amelia");
        controller.addPersonToNetwork("Hamish");
        controller.addPersonToNetwork("Darcy");

        controller.follow("Nathan", "Evanlyn");
        controller.follow("Amelia", "Evanlyn");
        controller.follow("Hamish", "Evanlyn");

        controller.follow("Hamish", "Amelia");
        controller.follow("Nathan", "Amelia");

        controller.follow("Nathan", "Darcy");
        controller.follow("Amelia", "Darcy");

        controller.follow("Amelia", "Hamish");

        NetworkIterator<String> iter = controller.getIterator("popularity");
        assertEquals("Evanlyn", iter.next());
        controller.switchIteratorComparisonMethod(iter, "friends");

        assertEquals("Hamish", iter.next());
        assertEquals("Evanlyn", iter.next());
        assertEquals("Darcy", iter.next());
        assertEquals("Nathan", iter.next());
        assertEquals(false, iter.hasNext());
    }
}