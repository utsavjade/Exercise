package com.example.utsav.assignment;

/**
 * Created by utsav on 14/7/15.
 */
public class Utils {
public static boolean isNotNullOrEmpty(Object object){
    if (object==null)
        return false;
    try{
        return ((String)object).equals("")?false:true;
    }catch (RuntimeException e){
        return true;
    }
}
}
