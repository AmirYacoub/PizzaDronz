package uk.ac.ed.inf;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

/**
 * Simple class to create order objects, and calculate the cost of an order.
 */
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
        Restaurant rest = null;

            int count = 0;
            for (Restaurant p:participants)
            {
                for (int i=0; i<p.getMenu().size(); i++)
                {
                    if (pizzas[0].equals(p.getMenu().get(i).getName()))
                    {
                        rest = p;
                        break;
                    }
                }
            }
            if (rest != null)
            {
                for (String pizza : pizzas)
                {
                    for (int i = 0; i < rest.getMenu().size(); i++)
                    {
                        if (pizza.equals(rest.getMenu().get(i).getName()))
                        {
                            count++;
                            cost += rest.getMenu().get(i).getPriceInPence();
                        }
                    }
                }
                if (count != pizzas.length)
                {
                    throw new InvalidPizzaCombination("Pizzas ordered are not from the same restaurant");
                }
            }
            else throw new Exception("Pizza ordered not in any menu");
        return cost;
    }

}
