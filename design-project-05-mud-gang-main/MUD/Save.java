package MUD;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import MUD.View.PTUI;

public class Save {

    /**
     * Creates a MUD save file.
     */
    public Save(PTUI ptui, String saveName) throws FileNotFoundException, IOException {

        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("MUD\\Saves\\" + saveName + ".data")))) {
            oos.writeObject(ptui);
        }

    }

}
