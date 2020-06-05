package com.vic.lombak.builder;


public class TestBuilder {
    public static void main(String[] args)  {
        TrafficLog.premove().content("a").content("b").build();
    }
}
