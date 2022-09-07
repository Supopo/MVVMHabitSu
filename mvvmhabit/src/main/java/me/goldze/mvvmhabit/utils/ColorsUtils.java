package me.goldze.mvvmhabit.utils;

import android.graphics.Color;

import java.util.Random;


public class ColorsUtils {

    //获取颜色的16进制字符串
    public static String randomHexStr(int len) {
        try {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < len; i++) {
                //随机生成0-15的数值并转换成16进制
                result.append(Integer.toHexString(new Random().nextInt(16)));
            }
            return result.toString().toUpperCase();
        } catch (Exception e) {
            System.out.println("获取16进制字符串异常，返回默认...");
            return "00CCCC";
        }
    }

    //随机获取颜色
    public static int randomColor() {
        return Color.argb(255, new Random().nextInt(256), new Random().nextInt(256), new Random().nextInt(256));
    }
}
