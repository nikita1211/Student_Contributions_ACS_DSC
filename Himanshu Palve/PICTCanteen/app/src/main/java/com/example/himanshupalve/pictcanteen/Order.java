package com.example.himanshupalve.pictcanteen;

/**
 * Created by Himanshu Palve on 3/17/2018.
 */

public class Order {
    String name;
    int quantity;
    public  Order(String Name,int quant)
    {
        name=Name;
//        quantity=String.valueOf(quant);
        quantity=quant;
    }
}
