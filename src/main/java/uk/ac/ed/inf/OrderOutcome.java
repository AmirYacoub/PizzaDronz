package uk.ac.ed.inf;

/**
 * Enum containing all the possible outcomes of an order to be used for logging.
 */
public enum OrderOutcome { Delivered ,
    ValidButNotDelivered ,
    InvalidCardNumber ,
    InvalidExpiryDate ,
    InvalidCvv ,
    InvalidTotal ,
    InvalidPizzaNotDefined ,
    InvalidPizzaCount , InvalidPizzaCombinationMultipleSuppliers , Invalid
}