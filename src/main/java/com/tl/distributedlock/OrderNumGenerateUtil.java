package com.tl.distributedlock;

public class OrderNumGenerateUtil
{
    private static int number = 0;

    public String getOrderNumber()
    {
        return ""+(++number);
    }
}
