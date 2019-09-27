package com.example.demo.services;

import com.example.demo.beans.MyBean;

public class ExampleServices {

    public static void example(MyBean bodyIn) {
        bodyIn.setName( "Hello, " + bodyIn.getName() );
        bodyIn.setId(bodyIn.getId()*10);
    }
}