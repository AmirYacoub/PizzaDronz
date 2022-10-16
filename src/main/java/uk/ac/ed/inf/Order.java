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

    public int getDeliveryCost(Restaurant[] participants)
    {
        int cost = 1;
        
        return cost;
    }

}
