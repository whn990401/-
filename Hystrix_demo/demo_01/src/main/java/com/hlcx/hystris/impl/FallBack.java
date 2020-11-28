package com.hlcx.hystris.impl;

import com.hlcx.hystris.Hystris01;
import org.springframework.stereotype.Component;

@Component
public class FallBack implements Hystris01 {

    @Override
    public String b() {
        return "hello,I'm b";
    }
}
