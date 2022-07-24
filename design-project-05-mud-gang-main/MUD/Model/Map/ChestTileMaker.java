package MUD.Model.Map;

import java.util.ArrayList;
import java.util.Random;

import MUD.Model.Inventory.Armor;
import MUD.Model.Inventory.BuffItem;
import MUD.Model.Inventory.BuffType;
import MUD.Model.Inventory.Food;
import MUD.Model.Inventory.Item;
import MUD.Model.Inventory.Weapon;

public class ChestTileMaker implements TileMaker {

    public ArrayList<Item> generateChestLoot(){
        ArrayList<Item> lootList = new ArrayList<>();

        //possible items to find within a chest, duplicates intended for rarity
        Item[] possibleItems = new Item[]{
                new Weapon("A wet napkin","It's a napkin. It's wet.", 1, 1),
                new Weapon("Toy Dagger","It makes a cute squeak with every hit.", 2, 50),
                new BuffItem("Health Potion", "A swirling yellow elixir, brewed with hope.", 200, BuffType.HEALTH, 20),
                new BuffItem("Strength Potion", "A swirling red elixir, brewed with determination.", 150, BuffType.ATTACK, 20),
                new Armor("Steel bucket.", "A hard helmet, perfect for the new adventurer.", 3, 30),
                new Armor("Adamantine Chestplate", "A Chestplate made of Adamantine. Nearly indestructible.", 20, 3000),
                new Armor("Steel Chestplate", "A Chestplate made of steel.", 8, 1300),
                new Armor("Steel Chestplate", "A Chestplate made of steel.", 8, 1300),
                new Armor("Leather Chestplate", "A Chestplate made of leather.", 5, 500),
                new Armor("Leather Chestplate", "A Chestplate made of leather.", 5, 500),
                new Armor("Leather Chestplate", "A Chestplate made of leather.", 5, 500),
                new Armor("Leather Helmet", "A helmet made of leather.", 3, 300),
                new Armor("Leather Helmet", "A helmet made of leather.", 3, 300),
                new Armor("Leather Helmet", "A helmet made of leather.", 3, 300),
                new Weapon("Adamantine Sword", "A sword made of Adamantine. Nearly indestructible.", 20, 3000),
                new Weapon("Steel Axe", "A axe made of Steel.", 10, 150),
                new Weapon("Steel Axe", "A axe made of Steel.", 10, 150),
                new Weapon("Steel Axe", "A axe made of Steel.", 10, 150),
                new Weapon("Steel Sword", "A sword made of Steel.", 10, 100),
                new Weapon("Steel Dagger", "A dagger made of Steel.", 5, 50),
                new Weapon("Steel Dagger", "A dagger made of Steel.", 5, 50),
                new Weapon("Steel Dagger", "A dagger made of Steel.", 5, 50),
                new Weapon("Gold Broadsword", "A Broadsword made of gold.", 13, 1000),
                new Weapon("Gold Broadsword", "A Broadsword made of gold.", 13, 1000),
                new Weapon("Gold Sword", "A sword made of gold.", 10, 500),
                new Weapon("Gold Dagger", "A dagger made of gold.", 5, 250),
                new Weapon("Broken pasta noodle.","Worthless. More worthless than worthless.", 0, 0),
                new Weapon("Blade of Quel'delar","The strongest blade in the world", 100, 50000),
                new Weapon("Sword of the Dark Knight","The (second) strongest blade in the world", 20, 1000),
                new Food("Hot Dog Water", "Smells good :)", 111, 50),
                new Food("Apple", "Red and juicy", 10, 5),
                new Food("Apple", "Red and juicy", 10, 5),
                new Food("Apple", "Red and juicy", 10, 5),
                new Food("Banana", "Yellow and juicy", 10, 5),
                new Food("Cookie", "A chocolate chip cookie", 50, 25),
                new Food("Ham", "Ham", 10, 5),
                new Food("Cheese", "Cheese", 10, 5),
                new Food("Chicken Leg", "KFC", 20, 10),
                new Food("Chicken Leg", "KFC", 20, 10),
            };

        int size = new Random().nextInt(1, 6);

        for (int i = 0; i < size; i++){
            Item randomItem = possibleItems[new Random().nextInt(possibleItems.length)];

            lootList.add(randomItem);
        }

        return lootList;
    }
    
    @Override
    public Tile makeTile(int r, int c) {
        Tile tile = new Tile(r, c);
        ArrayList<Item> loot = generateChestLoot();
        Chest chest = new Chest("Wooden Chest", "May contain up to 5 items.", loot);
        tile.setContents("C");
        tile.setOccupant(chest);
        return tile;
    }
    
}
