package com.hlcx;


public class GetCity {
    public static void main(String[] args) throws InterruptedException {
        for (int i=1;i<10;i++){
            GetCityThread getCityThread = new GetCityThread();
            Thread thread = new Thread(getCityThread);
            thread.start();
        }

    }
}
