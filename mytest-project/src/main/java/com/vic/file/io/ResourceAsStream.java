package com.vic.file.io;

import java.io.*;
import java.util.Scanner;


/**
 * ClassLoader.getSystemResourceAsStream(name);
 * 会缓存文件内容，更改无效
 */

/**
 * @Auther: wangqp
 * @Date: 2018/8/24 14:55
 * @Description:
 */
public class ResourceAsStream {
    public static void main(String[] args) {
        InputStream inputStream = null;
        try {
            readFile("aaa.txt",true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Scanner scan = new Scanner(System.in);
        while(scan.hasNext()) {
            String in = scan.next().toString();
            if("aa".equals(in)
                    || "aa".substring(0, 1).equals(in)) {
                System.out.println("您成功已退出！");
                break;
            }
            System.out.println("您输入的值："+in);
        }
        InputStream in = null;
        try {
            readFile("aaa.txt",false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void readFile(String name,boolean flag) throws IOException {
        BufferedReader in = null;
        InputStream inputStream = null;
        try {
            File file = new File(name);
            if (file.exists()) {
                inputStream = new FileInputStream(file);
            }
            if (flag) {
                if (inputStream == null) {
//            inputStream = ResourceAsStream.class.getClass().getClassLoader().getResource("/aaa.txt").openStream();
                    String path = ResourceAsStream.class.getClassLoader().getResource("aaa.txt").getPath();
                    inputStream = new FileInputStream(path);
                }
            }
            if (inputStream == null) {
                inputStream = ClassLoader.getSystemResourceAsStream(name);
            }

            if (inputStream == null) {
                throw new FileNotFoundException(name);
            }
            in = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        }finally {
            in.close();
            inputStream.close();
        }
    }

    public static  void readConfig(BufferedReader in) throws Exception {
        String line = null;
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }
        in.close();
    }
}
