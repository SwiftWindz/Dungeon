package MUD;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import MUD.View.PTUI;

/**
 * Loads a MUD save file.
 * @author Phil Ganem
 */
public class Load {
    
    private PTUI ptui;

    /**
     * Loads a MUD save.
     */
    public Load(String saveName) throws FileNotFoundException, IOException, ClassNotFoundException {

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(saveName)));
        this.ptui = (PTUI) ois.readObject();

    }

    public PTUI getPtui() {
        return ptui;
    }

}
