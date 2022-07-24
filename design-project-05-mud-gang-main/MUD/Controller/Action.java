package MUD.Controller;
/**
 * Action interface for implementing the Command pattern. 
 * Contains one method: execute(), for executing the concreteCommand's stored action
 * @author Cam Bacon
 */
public interface Action{
    public void execute();
}