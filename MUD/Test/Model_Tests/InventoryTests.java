package MUD.Test.Model_Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import MUD.Model.Inventory.Armor;
import MUD.Model.Inventory.Item;
import MUD.Model.Inventory.Weapon;

@Testable
public class InventoryTests {
    
    @Test
    /**
     * Test the item constructor
     */
    public void testItemConstructor() {
        Item item = new Item("Test", "Test", 1);

        assertEquals("Test", item.getName());
        assertEquals("Test", item.getDescription());
        assertEquals(1, item.getValue());

    }

    @Test
    /**
     * Test the weapon constructor
     */
    public void testWeaponConstructor() {
        Weapon weapon = new Weapon("Test", "Test", 1, 0);

        assertEquals("Test", weapon.getName());
        assertEquals("Test", weapon.getDescription());
        assertEquals(1, weapon.getAttack());
        assertEquals(0, weapon.getValue());
    }

    @Test
    /**
     * Test the armor constructor
     */
    public void testArmorConstructor() {
        Armor armor = new Armor("Test", "Test", 1.0, 0);

        assertEquals("Test", armor.getName());
        assertEquals("Test", armor.getDescription());
        assertEquals(1, armor.getDefense());
        assertEquals(0, armor.getValue());

    }
}
