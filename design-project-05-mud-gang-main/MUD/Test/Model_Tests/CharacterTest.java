package MUD.Test.Model_Tests;

import MUD.Model.Character.NPC;
import MUD.Model.Character.Player;
import MUD.Model.Inventory.Armor;
import MUD.Model.Inventory.Weapon;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
public class CharacterTest {

    @Test
    public void NPCinstantiationTest() {

        NPC chr = new NPC("Test", "Test", 100, 100, 100, null, null, "");
        String name = chr.getName();
        String description = chr.getDescription();
        double maxHealth = chr.getMaxHealth();
        double currentHealth = chr.getCurrentHealth();
        double attack = chr.getAttack();
        double defense = chr.getDefense();
        boolean defeated = chr.isDefeated();

        assert(name.equals("Test"));
        assert(description.equals("Test"));
        assert(maxHealth == 100);
        assert(currentHealth == 100);
        assert(attack == 100);
        assert(defense == 100);
        assert(defeated == false);
    
    }


    @Test
    /**
     * Test for the attack method. This test's NPCs have 0 defense.
     */
    public void NPCAttackTest() {

        NPC chr1 = new NPC("Banana", "Test Dummy", 100, 10, 0, null, null, "");
        NPC chr2 = new NPC("Pudding", "Test Dummy", 100, 10.5, 0, null, null, "");

        chr1.attack(chr2);
        chr2.attack(chr1);

        assertEquals(89.5, chr1.getCurrentHealth());
        assertEquals(90, chr2.getCurrentHealth());
    
    }

    @Test
    /**
     * Test for the attack method. This test's NPCs have 5 defense.
     */
    public void NPCAttackTest2() {

        NPC chr1 = new NPC("Banana", "Test Dummy", 100, 10, 5, null, null, "");
        NPC chr2 = new NPC("Pudding", "Test Dummy", 100, 10.5, 5, null, null, "");

        chr1.attack(chr2);
        chr2.attack(chr1);

        assertEquals(94.5, chr1.getCurrentHealth());
        assertEquals(95, chr2.getCurrentHealth());
    
    }

    @Test
    /**
     * Test for the attack method. This test's NPCs have 15 defense.
     */
    public void NPCAttackTest3() {

        NPC chr1 = new NPC("Banana", "Test Dummy", 100, 10, 15, null, null, "");
        NPC chr2 = new NPC("Pudding", "Test Dummy", 100, 10.5, 15, null, null, "");

        chr1.attack(chr2);
        chr2.attack(chr1);

        assertEquals(100, chr1.getCurrentHealth());
        assertEquals(100, chr2.getCurrentHealth());
    
    }

    @Test
    /**
     * Test for equiping a weapon to a player
     */
    public void playerEquipWeapon() {
        //setup
        Player player = new Player("Test", "testing", 100, 10, 0, null);
        Weapon weapon = new Weapon("Sword", "its a sword", 10, 10);
        double expected = 20;

        //invoke
        player.equipWeapon(weapon);
        double actual = player.getAttack();

        //analyze
        assert(expected == actual);
    }

    @Test
    /**
     * This is a test to replace an equipped
     * weapon
     */
    public void playerReplaceWeapon() {
        //setup
        Player player = new Player("Test", "tesing", 100, 10, 0, null);
        Weapon weapon = new Weapon("Sword", "its a sword", 10, 10);
        player.equipWeapon(weapon);
        Weapon newWeapon = new Weapon("Better sword", "it is a better sword", 20, 20);
        double expected = 30;

        //invoke 
        player.equipWeapon(newWeapon);
        double actual = player.getAttack();

        //analyze
        assert(expected == actual);
    }

    @Test
    /**
     * This is a test to equip armor onto the player
     */
    public void playerEquipArmor() {
        //setup
        Player player = new Player("Test", "tesing", 100, 10, 0, null);
        Armor armor = new Armor("armor", "its armor", 10, 0);
        double expected = 10;

        //invoke
        player.equipArmor(armor);
        double actual = player.getDefense();

        //analyze
        assert(expected == actual);
    }

    @Test
    /**
     * This is a test to replace armor on the player
     */
    public void playerReplaceArmor() {
        //setup
        Player player = new Player("Test", "tesing", 100, 10, 0, null);
        Armor armor = new Armor("armor", "its armor", 10, 0);
        player.equipArmor(armor);
        Armor betterArmor = new Armor("Better armor", "it is better armor", 16, 19);
        double expected = 16;

        //invoke
        player.equipArmor(betterArmor);
        double actual = player.getDefense();

        //analyze
        assert(expected == actual);
    }
   
}
