package com.hzsparrow.framework.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

/**
 * 工作日相关工具类
 * 
 * @author Heller.Zhang
 * @since 2019年5月7日 上午9:20:03
 */
public class WorkDateUtils {

    /**
     * @param httpArg "yyyyMMdd" 格式日期
     * @return 工作日对应结果为 0, 休息日对应结果为 1, 节假日对应的结果为 2
     */
    private static JSONObject request(String httpArg) {
        BufferedReader reader = null;
        String result = null;
        JSONObject jsonObjectResult = null;
        StringBuffer sbf = new StringBuffer();
        String httpUrl = "http://api.goseek.cn/Tools/holiday?date=" + httpArg;
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
            jsonObjectResult = JSONObject.parseObject(result);//转为JSONObject对象
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObjectResult;
    }

    /**
     * 判断日期是否工作日
     * 
     * @param dateStr
     * @return
     */
    public static Boolean ifWorkDay(String dateStr) {
        JSONObject json = request(dateStr);
        int data = NumberUtils.toInt(json.get("data").toString());
        if (data == 0) return false;
        else return true;
    }

    /**
     * 第x工作日后的日期
     * 
     * @param x
     * @return String
     */
    public static String getEndDayStr(int x) {
        return DateFormatUtils.format(getEndDay(x), "yyyy-MM-dd");
    }

    /**
     * 第x工作日后的日期
     * 
     * @param x
     * @return Date
     */
    public static Date getEndDay(int x) {
        Date date = new Date();
        if (x == 0) {
            return date;
        }

        for (int i = 0; i < x;) {
            date = DateUtils.addDays(date, 1);
            String dateStr = DateFormatUtils.format(date, "yyyyMMdd");
            if (!ifWorkDay(dateStr)) i++;
        }

        return date;
    }

    public static void main(String[] args) {
        /*JSONObject a = request("20180215");
        System.out.println(a);*/
        String date = getEndDayStr(4);
        System.out.println(date);
    }
}
