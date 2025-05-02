# Plan for Testing the Program

The test plan lays out the actions and data I will use to test the functionality of my program.

Terminology:

- **VALID** data values are those that the program expects
- **BOUNDARY** data values are at the limits of the valid range
- **INVALID** data values are those that the program should reject

---

## Travelling to a room.

Using the GUI the user should be able to travel to a different room.

### Test Data To Use

Starting from room 0 ("The Conveyor") pressing the west button. This is a valid input.
 
### Expected Test Result

The user should be taken to room 1 ("Back Corridor") and the location should be marked as discovered from that point onwards.

---

## Travelling to a room which doesn't exist.

The user should not be able to travel outside the map.

### Test Data To Use

Starting from room 0 ("The Conveyor") pressing the south button. This is an invalid input as there is no location to the south of room 0 ("The Conveyor")

### Expected Test Result

User cannot access button as it was disabled when they entered the room

---

## Travelling to a locked room. (No key)

Using the GUI the user may encounter locked rooms which require keys.

### Test Data To Use

Starting from room 0 ("The Conveyor") pressing the east button. Without a key this is an invalid input.

### Expected Test Result

The user should receive a message telling them that the door is locked and not be taken anywhere. 

---

## Travelling to a locked room. (With key)

Using the GUI the user may encounter locked rooms which require keys.

### Test Data To Use

Starting in room 0 ("The Conveyor") pressing the east button. With key 1 this is a valid input.

### Expected Test Result

The user should be taken to room 3 ("Storage Room") and the location should be marked as discovered from that point onwards.

---

## Travelling to the exit

using the GUI the user can travel to the exit and win the game.

### Test Data To Use

Starting at room 23 (Main Entrance) pressing the North button. With key 6 this is a valid input.

### Expected Test Result

The user should be shown the win window and the game should close.

---

## Travelling to all locations

The User can travel to every location if they have all the keys

### Test Data To Use

Starting from room 0 (The Conveyor) with keys 1, 2, 3, 4, 5, and 6, and traveling to each location. this is a boundary

### Expected Test Result

User should travel to all locations.

---