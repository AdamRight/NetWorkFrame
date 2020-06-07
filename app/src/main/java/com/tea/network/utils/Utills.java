package com.tea.network.utils;

/**
 * Created by jiangtea on 2020/6/6.
 */

public class Utills {


    public static boolean isExist(String className, ClassLoader loader) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
