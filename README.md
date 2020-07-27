# Turn-Based-Battle-System
A versatile, customizable, and unit tested turn-based battle system, for all your RPG needs. Remove files from images/music folders and place in same folder as .java files to run.

 This project was intended to be a fun way to practice my OOP and Unit
 testing skills while simulating a combat system I've always held dear:
 traditional turn-based combat. The purpose of this project is to show 
 how one would go about simulating turn-based combat from essentially
 the ground up. This project was not made with the intention of creating a
 balanced and fair combat system, that much I leave to the makers of games 
 like Pokemon or Final Fantasy. In fact, I have intentionally tweaked the 
 combat system to be more on the difficult side, both to avoid additional
 balancing and keep combat interesting.
    
 PennDraw is present merely for basic drawing functionality. Hero is this
 project's primary atomic object, in that heroes compose parties which compose battles.
 Heroes have classic RPG stats and are considered dead when at 0 current health. 
 Parties are the game's secondary atomic object and consist of 4 heroes each. This class
 allows for the sorting of heroes by speed and the random selection of enemy attacks.
 Battles are the game's tertiary atomic object and consist of 2 parties each, thus combat
 exists on a four-on-four basis. This class allows for basic game functionality and the 
 drawing of the GUI, icons, and more. UnitTesting contains various tests pertaining to
 hero's stats and functionalities, as well as the functionality of the battle system itself.
