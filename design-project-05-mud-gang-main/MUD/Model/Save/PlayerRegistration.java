package MUD.Model.Save;

import java.io.*;
import java.util.ArrayList;
import java.util.List; 

public class PlayerRegistration {
    final static String REGISTRATION_FILE = "MUD\\Saves\\Profiles\\Profiles.csv";

    public static String register(String user, String pass){
        try{
            String line;
            BufferedReader buffReader = new BufferedReader(new FileReader(REGISTRATION_FILE));
            while((line = buffReader.readLine()) != null){
                String[] userInfo = line.split(",");
                if (userInfo[0].equals(user)){
                    buffReader.close();
                    return("User with username " + user + " is already registered.");
                }
            }
            
            FileWriter writer = new FileWriter(REGISTRATION_FILE, true);
            BufferedWriter buffWriter = new BufferedWriter(writer);

            String data = user + ", " + pass + "\n";
            buffWriter.write(data);
            buffReader.close();
            buffWriter.close();
            return("Success");
        }
        catch(Exception e){
            return(e.getMessage());
        }
    }

    public static boolean signIn(String user, String pass){
        try{
            String line;
            BufferedReader buffReader = new BufferedReader(new FileReader(REGISTRATION_FILE));
            while((line = buffReader.readLine()) != null){
                String[] userInfo = line.split(", ");
                if (userInfo[0].equals(user)){
                    if (userInfo[1].equals(pass)){
                        buffReader.close();
                        return true;
                    }
                    buffReader.close();
                    return false;
                }
            }
            buffReader.close();
            return false;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean changePassword(String user, String pass, String newPass) {
        try{
            String line;
            BufferedReader buffReader = new BufferedReader(new FileReader(REGISTRATION_FILE));
            Boolean result = false;
            List<String> accounts = new ArrayList<>();
            while((line = buffReader.readLine()) != null){
                String[] userInfo = line.split(", ");
                if (userInfo[0].equals(user)){
                    if (userInfo[1].equals(pass)){
                        userInfo[1] = newPass;
                        accounts.add(userInfo[0] + ", " + userInfo[1] + "\n");
                        result = true;
                        continue;
                    }
                    buffReader.close();
                    return false;
                }
                accounts.add(line + "\n");
            }
            buffReader.close();
            if (result == false) {
                return false;
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(REGISTRATION_FILE, false));
            for (String account : accounts) {
                writer.write(account);
            }
            writer.close();
            return true;

        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
