/*
 * Ma Xin
 * write by 2017.5.5  6:11:3
 */

package com.testDemo;

import java.io.*;

/**
 * Created by Administrator on 2017/5/5.
 */
public class test {
    public static void main(String[] args) {
        try {
            File file = new File("../test/dome.properties");
            System.out.println("===================================");
            System.out.println(file.getAbsolutePath());
            FileInputStream fr = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
