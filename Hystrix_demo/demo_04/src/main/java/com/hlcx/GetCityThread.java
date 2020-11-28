package com.hlcx;

public class GetCityThread implements Runnable{

    public void run() {

        GetCityNameCommand getCityNameCommand = new GetCityNameCommand(1);
        // 获取本地内存(cityName)的代码会被信号量进行资源隔离
        System.out.println(getCityNameCommand.execute());
    }
}
