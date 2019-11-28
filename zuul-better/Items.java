import java.util.Set;
import java.util.HashMap;
/**
 * Write a description of class Items here.
 *
 * @author Pushkar Garg
 * @version 27 November 2019
 */
public class Items
{
    // instance variables - replace the example below with your own
    private String description;
    private int weight;
 
    /**
     * Constructor for objects of class Items
     */
    public Items(String description, int weight)
    {
        this.description = description;
        this.weight = weight;
        
    }

    
    public String getShortDescription()
    {
        return description;
    }
    
    public String getLongDescription()
    {
        return "The item that you picked is:" + description;
    }
    
    
}
