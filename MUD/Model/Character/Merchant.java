package MUD.Model.Character;

import MUD.Model.Inventory.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Merchant extends NPC{

    private ArrayList<Item> inventory;
    private Integer gold;
    private boolean isOpen;
    public static final int SELL_CONSTANT = 3;
    /**
     * Constructor for an NPC.
     *
     * @param name        The name of the NPC.
     * @param description The description of the NPC.
     */

    public Merchant(String name, String description, Integer gold) {
        super(name, description, 1, 0, 0, Behavior.Docile, Bedtime.Diurnal, "MUD/View/images/merchant.png");
        this.inventory = new ArrayList<>();
        this.gold = gold;

        generateInventory();

    }
    private void generateInventory(){

        this.inventory.add(new Weapon("Toy Dagger","It makes a cute squeak with every hit.", 2, 50));
        this.inventory.add(new BuffItem("Health Potion", "A swirling yellow elixir, brewed with hope.", 200, BuffType.HEALTH, 20));
        this.inventory.add(new BuffItem("Strength Potion", "A swirling red elixir, brewed with determination.", 150, BuffType.ATTACK, 20));
        this.inventory.add( new Weapon("Gold Sword", "A sword made of gold.", 10, 500));
        this.inventory.add(new Weapon("Blade of Quel'delar","The strongest blade in the world", 100, 50000));
        this.inventory.add(new Food("Cookie", "A chocolate chip cookie", 10, 5));
        this.inventory.add(new Food("Cheese", "Cheese", 10, 5));
        this.inventory.add(new Food("Apple", "Red and juicy", 10, 5));
        this.inventory.add(new Armor("Steel Chestplate", "A Chestplate made of steel.", 8, 1300));
        this.inventory.add(new Armor("Leather Chestplate", "A Chestplate made of leather.", 5, 500));
        this.inventory.add(new Armor("Leather Helmet", "A helmet made of leather.", 3, 300));
        this.inventory.add(new Armor("Leather Helmet", "A helmet made of leather.", 3, 300));
        this.inventory.add(new Armor("Leather Helmet", "A helmet made of leather.", 3, 300));
        this.inventory.add(new Armor("Leather Helmet", "A helmet made of leather.", 3, 300));
        this.inventory.add(new Armor("Leather Chestplate", "A Chestplate made of leather.", 5, 500));
        this.inventory.add(new Food("Cheese", "Cheese", 10, 5));
        this.inventory.add(new Food("Cheese", "Cheese", 10, 5));
        this.inventory.add(new Food("Cheese", "Cheese", 10, 5));
        this.inventory.add(new Weapon("Gold Sword", "A sword made of gold.", 10, 500));
        this.inventory.add(new BuffItem("Health Potion", "A swirling yellow elixir, brewed with hope.", 200, BuffType.HEALTH, 20));
        this.inventory.add(new BuffItem("Health Potion", "A swirling yellow elixir, brewed with hope.", 200, BuffType.HEALTH, 20));
        this.inventory.add(new BuffItem("Strength Potion", "A swirling red elixir, brewed with determination.", 150, BuffType.ATTACK, 20));
        this.inventory.add(new BuffItem("Strength Potion", "A swirling red elixir, brewed with determination.", 150, BuffType.ATTACK, 20));
        this.inventory.add(new Food("Roasted Chicken", "The whole animal.", 50, 65));
        this.inventory.add(new Food("Roasted Chicken", "The whole animal.", 50, 65));
        this.inventory.add(new Weapon("Toy Dagger","It makes a cute squeak with every hit.", 2, 50));
        this.inventory.add(new Weapon("Toy Dagger","It makes a cute squeak with every hit.", 2, 50));
        this.inventory.add(new Weapon("Diamond Pool Noodle", "Hard, yet soft.", 4, 90));
        this.inventory.add(new Weapon("Diamond Pool Noodle", "Hard, yet soft.", 4, 90));
        this.inventory.add(new Weapon("Diamond Pool Noodle", "Hard, yet soft.", 4, 90));
        this.inventory.add(new Food("Cheese", "Cheese", 10, 5));

    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public Integer getGold() {
        return gold;
    }

    public void buyItem(Item item){
        this.inventory.add(item);
        this.gold -= (Integer)(item.getValue() / 2);
    }

    public void sellItem(Item item, Player buyer){
        buyer.buyItem(item);
        inventory.remove(item);
        this.gold += item.getValue();
    }

    public ArrayList<Item> getWares(){
        ArrayList<Item> wares = new ArrayList<>();
        int count = 0;

        while (count < SELL_CONSTANT){
            Item itemToAdd = selectRandomItem();
            if (!wares.contains(itemToAdd)) {//If the item is not already in Merchant's wares, add it
                wares.add(itemToAdd);
                count++;
            }
        }

        return wares;
    }

    public Item selectRandomItem(){
        Random random = new Random();
        return inventory.get(random.nextInt(inventory.size()));
    }

    public void talk(Player player){
        System.out.println("Whad'ya want? (buy/sell)");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String choice;

        Set<Item> wares = new HashSet(getWares());

        try {
            choice = reader.readLine();
            if(choice.equals("buy")){
                for(Item i : wares){
                    System.out.println(i.getName() + ": " + i.getValue() + " gold");
                }
                System.out.println("Pick what you want.");
                String chosenItem;
                try {
                    chosenItem = reader.readLine();
                    Item soldItem = null;
                    for(Item i : wares){
                        if(i.getName().equals(chosenItem))
                            soldItem = i;
                    }
                    if(!soldItem.equals(null))
                        sellItem(soldItem, player);
                } catch (IOException e) {
                    System.out.println("error");
                }


            }
            else if(choice.equals("sell")){
                Inventory inventory = player.getInventory();
                Map<String, Item> sellableItems = new HashMap<>();
                for(Bag b : inventory.getInventory()){
                    for(Item i : b.getItems()){
                        System.out.println(i.getName() + ": " + i.getValue()/2 + " gold");
                        sellableItems.put(i.getName(), i);
                    }
                }
                System.out.println("What's yer offer?"); 
                try {
                    String item = reader.readLine();
                    for(String i : sellableItems.keySet()){
                        if(i.equals(item)){
                            player.sellItem(sellableItems.get(i), this);
                        }
                    }
                } catch (IOException e) {
                    System.out.println("error");
                }

            }
        } catch (IOException e) {
            System.out.println("error");
        }

    }
}
