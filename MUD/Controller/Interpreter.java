package MUD.Controller;

import java.io.Serializable;

/**
 * Interpreter class, collection of interpreters are held by the PTUI
 * to interpret incoming commands from the user. Each interpreter holds an action
 * with a specific target to be invoked when the PTUI feeds in the correct input command.
 * @author Cam Bacon
 */

public class Interpreter implements Serializable {
    
    private Action action;

    public Interpreter(Action action){
        this.action = action;
    }

    public void invoke(){
        action.execute();
    }
}
