package MUD.Test.Model_Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import MUD.Model.Character.Bedtime;
import MUD.Model.Character.Behavior;
import MUD.Model.Character.NPC;
import MUD.Model.Character.Player;
import MUD.Model.DayNight.Day;
import MUD.Model.DayNight.DayNightCycle;
import MUD.Model.DayNight.DayVisitor;
import MUD.Model.DayNight.NightVisitor;
import MUD.Model.DayNight.Visitor;

@Testable
public class DayNightCycleTests {

    @Test
    /**
     * Tests the day visitor on a diurnal NPC.
     */
    public void dayVisitorTest1(){

        // Create a new NPC.
        NPC npc = new NPC("Test Dummy", "Test Dummy", 10.0, 10.0, 10.0,
        Behavior.Hostile, Bedtime.Diurnal);

        // Create a new day visitor.
        Visitor dayVisitor = new DayVisitor();

        // Visit the NPC.
        npc.accept(dayVisitor);
        
        System.out.println("Test" + npc.getAttack());
        // Check the NPC's attack.
        assertEquals(11, npc.getAttack());

    }

    @Test
    /**
     * Tests the day visitor on a nocturnal NPC.
     */
    public void dayVisitorTest2(){
        // Create a new NPC.
        NPC npc = new NPC("Test Dummy", "Test Dummy", 10.0, 10.0, 10.0,
        Behavior.Hostile, Bedtime.Nocturnal);

        // Create a new day visitor.
        Visitor dayVisitor = new DayVisitor();

        // Visit the NPC.
        npc.accept(dayVisitor);
        
        System.out.println("Test" + npc.getAttack());
        // Check the NPC's attack.
        assertEquals(8, npc.getAttack());

    }
    
    @Test
    /**
     * Tests the night visitor on a diurnal NPC.
     */
    public void nightVisitorTest1(){

        // Create a new NPC.
        NPC npc = new NPC("Test Dummy", "Test Dummy", 10.0, 10.0, 10.0,
        Behavior.Hostile, Bedtime.Diurnal);

        // Create a new day visitor.
        Visitor nightVisitor = new NightVisitor();

        // Visit the NPC.
        npc.accept(nightVisitor);
        
        System.out.println("Test" + npc.getAttack());
        // Check the NPC's attack.
        assertEquals(8, npc.getAttack());

    }

    @Test
    /**
     * Tests the night visitor on a nocturnal NPC.
     */
    public void nightVisitorTest2(){
        // Create a new NPC.
        NPC npc = new NPC("Test Dummy", "Test Dummy", 10.0, 10.0, 10.0,
        Behavior.Hostile, Bedtime.Nocturnal);

        // Create a new day visitor.
        Visitor nightVisitor = new NightVisitor();

        // Visit the NPC.
        npc.accept(nightVisitor);
        
        System.out.println("Test" + npc.getAttack());
        // Check the NPC's attack.
        assertEquals(11, npc.getAttack());

    }

    @Test
    /**
     * tests trying to change the time before it will be changed
     */
    public void changeTimeTest(){
        DayNightCycle dnc = new DayNightCycle(new Player("1", "1", 1, 1, 1, null));
        dnc.startCycle();
        dnc.changeTime();
        assertEquals(dnc.getTime().getClass(), new Day().getClass());
    }
    
}
