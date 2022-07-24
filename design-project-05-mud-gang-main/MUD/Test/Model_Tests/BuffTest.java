package MUD.Test.Model_Tests;

import org.junit.Test;
import org.junit.platform.commons.annotation.Testable;
import MUD.Model.Character.Player;
import MUD.Model.Inventory.BuffItem;
import MUD.Model.Inventory.BuffType;

@Testable
public class BuffTest {

    @Test
    public void equipBuff() {
        //setup
        Player player = new Player("Test", "Tesitnd", 100, 10, 0, null);
        BuffItem strengthPotion = new BuffItem("Strength Potion", "Test", 10, BuffType.ATTACK, 20);
        double expected = 30;

        //invoke
        player.equipBuff(strengthPotion);
        double actual = player.getAttack();

        //analyze
        assert(expected == actual);
    }

    @Test
    public void removeBuff() {
        //setup
        Player player = new Player("Test", "Tesitnd", 100, 10, 0, null);
        BuffItem strengthPotion = new BuffItem("Strength Potion", "Test", 10, BuffType.ATTACK, 20);
        player.equipBuff(strengthPotion);
        double expected = 10;
        
        //invoke
        player.removeBuff(strengthPotion);
        double actual = player.getAttack();

        //analyze
        assert(expected == actual);
    }

    @Test
    public void testDecreaseTurnsLeft() {
        //setup
        Player player = new Player("Test", "Tesitnd", 100, 10, 0, null);
        BuffItem strengthPotion = new BuffItem("Strength Potion", "Test", 10, BuffType.ATTACK, 20);
        player.equipBuff(strengthPotion);
        int expected = 9;

        //invoke
        player.attack(player);
        int actual = strengthPotion.getTurnsLeft();

        // analzye
        assert(expected == actual);
        
    }

}
