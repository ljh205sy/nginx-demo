package com.example;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.FileInputStream;

/**
 * @Author: liujinhui
 * @Date: 2019/8/27 14:42
 */
public class JsonUtils {

    public static void main(String[] args) {
        JsonUtils jsonUtils = new JsonUtils();
        jsonUtils.parseJson("D:\\VRV\\security-demo\\security-demo\\src\\main\\java\\com\\example\\module.json");
    }


    public void parseJson(String path) {
        File file = new File(path);
        System.out.println(file.getAbsolutePath());
        Long fileLengthLong = file.length();
        byte[] fileContent = new byte[fileLengthLong.intValue()];
        try {
            FileInputStream inputStream = new FileInputStream(file);
            inputStream.read(fileContent);
            inputStream.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        String s = new String(fileContent);
//        System.out.println(s);

        JSONObject jsonObject = JSON.parseObject(s);
        String items = jsonObject.getString("items");
        JSONArray jarr = JSON.parseArray(items);
        int maxLength = 30;
        forEachPrint(jarr, maxLength, "base");
        System.out.println("-------------------------------------------------");
        forEachPrint(jarr, maxLength, "jar");
        System.out.println("-------------------------------------------------");

    }

    private void forEachPrint(JSONArray jarr, int maxLength, String base) {
        for (int i = 0, len = jarr.size(); i < len; i++) {
            JSONObject temp = jarr.getJSONObject(i);
            String type = temp.getString("type");
            if (base.equalsIgnoreCase(type)) {
                print(maxLength, temp);
            }
        }
    }

    private void print(int maxLength, JSONObject temp) {
        String name = temp.getString("name").trim();
        StringBuffer sb = new StringBuffer(name);
        String version = temp.getString("version").trim();
        if (name.length() < maxLength) {
            for (int j = 0; j < (maxLength - name.length()); j++) {
                sb.append(" ");
            }
            System.out.printf("%s%s\n", sb.toString(), version);
        }
    }
}
