/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    Room outside, gym, movieHall, bar, office, restaurant, shop, bathroom;
    Items guinea1, guinea2, guinea3, sunglasses, bag, knife, watch;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        
      
        // create the rooms
        outside = new Room("outside the main entrance of the mall");
        gym = new Room("in the gym");
        movieHall = new Room("in the movie hall");
        bar = new Room("in the bar");
        office = new Room("in the customer support office");
        restaurant = new Room("in the restaurant");
        shop = new Room("in the shop");
        bathroom = new Room("in the bathroom");
        
        
        // initialise room exits
        outside.setExit("east", gym);
        outside.setExit("south", shop);
        outside.setExit("north", restaurant);

        gym.setExit("south", movieHall);
        gym.setExit("north", bar);
        gym.setExit("west", outside);

        bar.setExit("west", restaurant);
        bar.setExit("south", gym);
        
        movieHall.setExit("north", gym);

        shop.setExit("north", outside);
        shop.setExit("west", bathroom);
        shop.setExit("south", office);

        bathroom.setExit("east", shop);
        
        office.setExit("north", shop);
        
        restaurant.setExit("south", outside);
        restaurant.setExit("east", bar);
        
        
        currentRoom = outside;  // start game outside
    }

    private void createItems()
    {
       
       
       guinea1 = new Items("Guinea I", 3);
       
       guinea2 = new Items("Guinea II",3);
       
       guinea3 = new Items("Guinea III", 3);
       
       sunglasses = new Items("Sunglasses", 2);
       
       bag = new Items("Bag", 6);
       
       knife = new Items("Knife", 1);
       
       watch = new Items("Watch", 4);
       
       bathroom.putItem(guinea1);
       
       bar.putItem(guinea2);
       bar.putItem(knife);
       
       movieHall.putItem(guinea3);
       
       gym.putItem(bag);
       
       shop.putItem(sunglasses);
       shop.putItem(watch);
       
       
       
       
    }
    
    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("check")) {
            checkRoom();
        }
        
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }
    
    private void checkRoom()
    {
        if(currentRoom.hasItem()){
            System.out.println("The Items in the Room are:");
            currentRoom.getItem();
        }
        else
        System.out.println("This room does not have any items!");
        
    }
    
    /**
     * PICKUP
     */
    private void pickUp(Command command)
    {
        
    }
    

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
