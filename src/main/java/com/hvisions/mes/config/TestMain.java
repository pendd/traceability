package com.hvisions.mes.config;

import java.util.HashMap;
import java.util.Map;

/**
 * Author swang
 * Date 2019/3/24 16:47
 * Version 1.0
 * Description
 **/
public class TestMain {

    public static void main(String[] args){
        Map<String,String> map = new HashMap<>();
        map.put("AA","1111");
        map.put("BB","2222");
        String aa = map.putIfAbsent("AA","3333");
        String cc = map.putIfAbsent("CC","4444");
        System.out.println("====="+aa);
        System.out.println("====="+cc);
        System.out.println("====="+map.get("AA"));
        System.out.println("====="+map.get("CC"));
        String ccc = map.replace("CC","6666");
        System.out.println("====="+ccc);
        String dd = map.replace("DD","5555");
        System.out.println("====="+dd);
        System.out.println("====="+map.get("DD"));

    }
}
