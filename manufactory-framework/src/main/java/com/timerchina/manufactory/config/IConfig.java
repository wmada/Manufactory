package com.timerchina.manufactory.config;

/**
 * @author will.ma
 * @date 2017-5-7
 */
public interface IConfig {

    void reload();

    String get(String key);

    String get(String key, String defaultValue);

    Integer getInt(String key);

    Integer getInt(String key, Integer defaultValue);

    Long getLong(String key);

    Long getLong(String key, Long defaultValue);

    Boolean getBoolean(String key);

    Boolean getBoolean(String key, Boolean defaultValue);

    boolean containsKey(String key);

}
