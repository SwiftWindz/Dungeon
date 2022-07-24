package MUD.Test.Model_Tests;

import MUD.Model.Map.Map;
import MUD.Model.Map.Obstacle;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.platform.commons.annotation.Testable;
@Testable
public class FloorTest {

    @Test
    public void MapCreation() {
        Map m = new Map(2, null);
        assertEquals(2, m.getNumberOfRooms());

    }

    @Test
    public void PopulationTest() {
        Map m = new Map(2, null);

        m.populate();

        int numOfObstacles= m.getRemainingEntities(new Obstacle("", ""));
        System.out.println(numOfObstacles);
        assert(numOfObstacles > 20);
    }
}