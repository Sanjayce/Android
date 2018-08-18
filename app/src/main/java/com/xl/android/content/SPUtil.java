package com.xl.android.content;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

/**
 * sharedpreference自定义工具类
 */
public class SPUtil {
    /**
     * 保存在手机里的SP文件名
     */
    private static final String FILE_NAME = "my_sp";
    private SharedPreferences sp;

    public SPUtil(Context context){
        sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

    }

    /**
     * 保存数据
     */
    public void put(String key, Object obj) {
        SharedPreferences.Editor editor = sp.edit();
        if (obj instanceof Boolean) {
            editor.putBoolean(key, (Boolean) obj);
        } else if (obj instanceof Float) {
            editor.putFloat(key, (Float) obj);
        } else if (obj instanceof Integer) {
            editor.putInt(key, (Integer) obj);
        } else if (obj instanceof Long) {
            editor.putLong(key, (Long) obj);
        } else {
            editor.putString(key, (String) obj);
        }
        //editor.commit();
        editor.apply();
    }

    /**
     * 获取指定数据
     */
    public Object get(String key, Object defaultObj) {

        if (defaultObj instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObj);
        } else if (defaultObj instanceof Float) {
            return sp.getFloat(key, (Float) defaultObj);
        } else if (defaultObj instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObj);
        } else if (defaultObj instanceof Long) {
            return sp.getLong(key, (Long) defaultObj);
        } else if (defaultObj instanceof String) {
            return sp.getString(key, (String) defaultObj);
        }
        return null;
    }

    /**
     * 删除指定数据
     */
    public void remove(String key) {

        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.apply();
    }

    /**
     * 返回所有键值对
     */
    public Map<String, ?> getAll() {

        Map<String, ?> map = sp.getAll();
        return map;
    }

    /**
     * 删除所有数据
     */
    public void clear() {

        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }

    /**
     * 检查key对应的数据是否存在
     */
    public boolean contains(String key) {

        return sp.contains(key);
    }
}
