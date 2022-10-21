package uk.ac.ed.inf;

/**
 * Simple class to throw an Invalid Pizza Combination Exception
 */
public class InvalidPizzaCombination extends Exception
{
    public InvalidPizzaCombination(String message)
    {
        super(message);
    }
}
