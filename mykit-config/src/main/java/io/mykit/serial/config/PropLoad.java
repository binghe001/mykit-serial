package io.mykit.serial.config;

import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Properties;

/**
 * @author binghe
 * @version 1.0.0
 * @description 定义加载params.properties文件的工具类
 */
public class PropLoad extends BaseLoad {
	private volatile static Properties instance;

    static {
        InputStream in = PropLoad.class.getClassLoader().getResourceAsStream(PARAMS_FILE_NAME);
        instance = new Properties();
        try {
            instance.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getStringValue(String key){
        if(instance == null) return "";
        return instance.getProperty(key, "");
    }

    public static Integer getIntegerValue(String key){
       String v = getStringValue(key);
       return StringUtils.isEmpty(v) ? 0 : Integer.parseInt(v);
    }
    public static Long getLongValue(String key){
       String v = getStringValue(key);
       return StringUtils.isEmpty(v) ? 0L : Long.parseLong(v);
    }

    public static Boolean getBooleanValue(String key){
       String v = getStringValue(key);
       return StringUtils.isEmpty(v) ? false : Boolean.parseBoolean(key);
    }
    public static void main(String[] args){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        System.out.println(calendar.getTime().getTime());
    }
}
