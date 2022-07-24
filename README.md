# Multi-User Dungeon
This is a project for RIT's SWEN262 course, Engineering of Software Subsystems.
## Members:
Phil Ganem,
Nathan Perez,
Carter Vail,
Borna Eshraghi,
and Cameron Bacon
## Dependencies: 
JUnit
## Summary: 
Multi-User Dungeon, also known as MUD, is a computer game that is able to be run out of the command line. Within the game there are characters, NPCs and player characters, NPCs are currently all hostile and attack adjacent player characters. Each character has health, attack, defense and a description. NPCs also can be diurnal or nocturnal, an attribute which has been dubbed BedTime.
A player character has multiple actions it can take each turn in the form of commands, the player can move, attack, loot chests, or disarm traps. A player also has an inventory which is made up of bags which contain items. Within each level of the inventory the player can see the current used and available space as well as the total gold value of that composite portion of the inventory. There are different types of items, bags, food items which restore health, buff items, weapons and armor. A player may have one weapon and one armor equipped at a time. The decorator pattern will be used for buffs as they are applied to a player for a limited number of turns and multiple buffs may be applied at the same time. The ability to have many different combinations of buffs makes the decorator pattern the best way to implement buffs. 
A player uses the player character to explore a map, a map is made up of rooms which individually contain a group of tiles. Rooms have a height, width, up to four exits and a description, as well as a string representation. Within a map there must be one “start” room and one “goal” room. Tiles can have traps, a chest, characters, obstacles or nothing. A trap will damage a player for stepping on the tile and can be disarmed from an adjacent tile. Chests can be opened and they contain 1-5 items for the player. If an NPC is occupying a tile a character may not move onto that tile however they may attack the NPC from an adjacent tile. Obstacles take up a tile and make it impassable. 
The game also has a day/night cycle which is controlled by a state machine, it changes between day and night every 5 minutes. When the time changes a visitor is sent by the Day state or Night state to the NPCs in the room and the player. The NPCs affected by the visitor have stat changes depending on whether they are diurnal or nocturnal, and then the player is notified of these changes. 
