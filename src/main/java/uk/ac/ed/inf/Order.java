package uk.ac.ed.inf;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Order
{

    @JsonProperty("orderNo")
    public String orderNo;
    @JsonProperty("orderDate")
    public String orderDate;
    @JsonProperty("customer")
    public String customer;
    @JsonProperty("creditCardNumber")
    public String creditCardNumber;
    @JsonProperty("creditCardExpiry")
    public String creditCardExpiry;
    @JsonProperty("cvv")
    public String cvv;
    @JsonProperty("priceTotalInPence")
    public int priceTotalInPence;
    @JsonProperty("orderItems")
    public ArrayList<String> orderItems;

    /**
     * Returns the cost of a specific order.
     * @param participants Array of restaurants participating in the PizzaDronz program.
     * @param pizzas Variable number of strings of the names of the pizzas ordered.
     * @return The total price in pence, including the delivery fee of one pound.
     * @throws Exception
     */
    public int getDeliveryCost(Restaurant[] participants, String... pizzas) throws Exception
    {
        int cost = 100;
        ArrayList<String> combination = new ArrayList<>();
        Restaurant rest = null;
        for (String pizza : pizzas)
        {
            for (Restaurant participant: participants)
            {
                if (rest == null || rest.equals(participant))
                {
                    for (int i = 0; i < participant.getMenu().size(); i++)
                    {
                        if (pizza.equals(participant.getMenu().get(i).getName()))
                        {
                            rest = participant;
                            cost += participant.getMenu().get(i).getPriceInPence();
                        }
                    }
                }
                else
                {
                    throw new InvalidPizzaCombination("Pizzas ordered are not from the same restaurant");
                }
            }
        }
        return cost;
    }

}
