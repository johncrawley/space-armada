package com.jacstuff.spacearmada.image;

/*

 when the bitmap loader registers a set of bitmaps, it also registers the details of the animation here

 this class contains a list of animation details
  - for each animation, there's a name, the number of frames, and whether the animation is repeated
  - when a


  - example Enemy Ship
    - let's say it has destroying, moving, and getting_hit animations
    - when the enemy ship gets created

  - level is loaded
    - for each type of ship there's a set of animations
    - so there's an enemy ship java class, but multiple enemy ship types, that get loaded into objects of this class
        - a more complete version could have animations, behaviours, references to waypoint patterns, firing patterns
        - as well as speed, health
        - these could be loaded in from an external file
        - for now let's concentrate on the animation part
        - should there be a separate Template class that defines an enemy ship type?


    - we have a GameView which draws all the actors and backgrounds to the canvas
        - the enemy ship passes it's DrawInfo to the GameView
        - the GameView passes a DrawInfo to the BitmapManager and gets a bitmap in return
        - the BitmapLoader loads images to the BitmapManger at the start of each new Level (not yet implemented)
        -So there should be a LevelLoader

        -  The GameState should contain a Level that corresponds to the current level,
            - some event (like defeating a boss) will cause the next level to get loaded

        - there should be some kind of clock that launches new enemies at certain intervals
            - so there should be some kind of call to currentLevel.getNextWave()
            - enemyShipManager should be registered with the level


 */
public class AnimationDetailsStore {




}
