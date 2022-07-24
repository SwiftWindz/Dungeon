package MUD.Model.Map;
/**
 * Interface which establishes the expected attributes and methods of a Map component.
 * This class serves as a Composite Interface for the Composite pattern.
 * @author Nathan Perez
 */

public interface MapElement {

    public boolean hasEntity(Object entity);
    public boolean isGoal();
    public String toString();

}
