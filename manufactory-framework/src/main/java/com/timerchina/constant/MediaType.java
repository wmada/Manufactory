package com.timerchina.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author will.ma
 * @date 2017-5-7
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public enum MediaType {

    BLOG(1, "Blog", "博客"),
    BBS(2, "BBS", "论坛"),
    NEWS(3, "News", "新闻网站"),
    WEIBO(4, "Weibo", "微博"),
    VIDEO(5, "Video", "视频"),
    WEB_PAGE(6, "Web Page", "网页"),
    QA(7, "QA", "问答"),
    ELECTRONIC_PRESS(8, "Electronic Press", "电子报刊"),
    WECHAT(9, "Wechat", "微信"),
    APP(10, "APP", "APP"),
    E_COMMERCE(11, "E-Commerce", "电商"),
    LIVE(12, "Live", "直播");

    private Integer mediaTypeId;
    private String  enName;
    private String  cnName;

    MediaType(Integer mediaTypeId, String enName, String cnName) {
        this.mediaTypeId = mediaTypeId;
        this.enName = enName;
        this.cnName = cnName;
    }

    public Integer getMediaTypeId() {
        return mediaTypeId;
    }

    public String getEnName() {
        return enName;
    }

    public String getCnName() {
        return cnName;
    }

    private static final Map<Integer, MediaType> idCache;
    private static final Map<String, MediaType>  nameCache;

    static {
        idCache = new HashMap<>();
        nameCache = new HashMap<>();

        for (MediaType mediaType : values()) {
            idCache.put(mediaType.getMediaTypeId(), mediaType);
            nameCache.put(mediaType.getEnName().toLowerCase(), mediaType);
            nameCache.put(mediaType.getCnName().toLowerCase(), mediaType);
        }
    }

    public static MediaType parse(Integer mediaTypeId) {
        return mediaTypeId != null ? idCache.get(mediaTypeId) : null;
    }

    public static MediaType parse(String name) {
        return name != null ? nameCache.get(name.toLowerCase()) : null;
    }
}
