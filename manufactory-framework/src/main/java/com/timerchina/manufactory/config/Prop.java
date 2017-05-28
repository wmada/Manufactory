package com.timerchina.manufactory.config;

import com.timerchina.toolkit.cache.AsyncAutoUpdateCache;
import com.timerchina.toolkit.time.TSTimeUtils;
import com.timerchina.toolkit.utils.ConvertUtils;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;
import java.util.jar.JarFile;

/**
 * Prop. Prop can load properties file from CLASSPATH or File object.
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class Prop implements IConfig {

    public static final String DEFAULT_ENCODING = "UTF-8";
    public static       Long   EXPIRE_TIME      = TSTimeUtils.MINUTE_IN_MILLISECONDS;

    public static void setExpireTime(Long expireTime) {
        EXPIRE_TIME = expireTime;
    }

    private AsyncAutoUpdateCache<Properties> cache;

    private static class FilenamePropertiesCache extends AsyncAutoUpdateCache<Properties> {
        private String fileName;
        private String encoding;
        private Properties properties = new Properties();

        public FilenamePropertiesCache(String fileName, String encoding) {
            super(fileName, EXPIRE_TIME);
            this.fileName = fileName;
            this.encoding = encoding;
        }

        @Override
        protected Properties update() throws Exception {
            try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)) {
                if (inputStream == null) throw new IllegalArgumentException("Properties file not found in classpath: " + fileName);
                properties.load(new InputStreamReader(inputStream, encoding));
            } catch (IOException e) {
                throw new RuntimeException("Error loading properties file.", e);
            }
            return properties;
        }
    }

    private static class FilePropertiesCache extends AsyncAutoUpdateCache<Properties> {
        private File   file;
        private String encoding;
        private Properties properties = new Properties();

        public FilePropertiesCache(File file, String encoding) {
            super(file.getName(), EXPIRE_TIME);
            this.file = file;
            this.encoding = encoding;
        }

        @Override
        protected Properties update() throws Exception {
            if (file == null) throw new IllegalArgumentException("File can not be null.");
            if (!file.isFile()) throw new IllegalArgumentException("File not found : " + file.getName());

            try (InputStream inputStream = new FileInputStream(file)) {
                properties = new Properties();
                properties.load(new InputStreamReader(inputStream, encoding));
            } catch (IOException e) {
                throw new RuntimeException("Error loading properties file.", e);
            }
            return properties;
        }
    }

    private static class ClassPropertiesCache extends AsyncAutoUpdateCache<Properties> {
        private String   fileName;
        private Class<?> callee;
        private String   encoding;
        private Properties properties = new Properties();

        public ClassPropertiesCache(String fileName, Class<?> callee, String encoding) {
            super(fileName, EXPIRE_TIME);
            this.fileName = fileName;
            this.callee = callee;
            this.encoding = encoding;
        }

        @Override
        protected Properties update() throws Exception {
            URL         location    = callee.getProtectionDomain().getCodeSource().getLocation();
            InputStream inputStream = null;
            try {
                File file = new File(location.toURI());
                if (file.isFile()) {
                    JarFile jarFile = new JarFile(file);
                    inputStream = jarFile.getInputStream(jarFile.getJarEntry(fileName));
                } else {
                    inputStream = new FileInputStream(file.getPath() + "/" + fileName);
                }
            } catch (URISyntaxException | IOException e) {
                e.printStackTrace();
            }
            if (inputStream != null) {
                try {
                    properties = new Properties();
                    properties.load(new InputStreamReader(inputStream, encoding));
                } catch (IOException e) {
                    throw new RuntimeException("Error loading properties file.", e);
                } finally {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return properties;
        }
    }

    /**
     * Prop constructor.
     *
     * @see #Prop(String, String)
     */
    public Prop(String fileName) {
        this(fileName, DEFAULT_ENCODING);
    }

    /**
     * Prop constructor
     *
     * @param fileName the properties file's name in classpath or the sub directory of classpath
     * @param encoding the encoding
     */
    public Prop(String fileName, String encoding) {
        cache = new FilenamePropertiesCache(fileName, encoding);
    }

    /**
     * Prop constructor.
     *
     * @see #Prop(File, String)
     */
    public Prop(File file) {
        this(file, DEFAULT_ENCODING);
    }

    /**
     * Prop constructor
     *
     * @param file     the properties File object
     * @param encoding the encoding
     */
    public Prop(File file, String encoding) {
        cache = new FilePropertiesCache(file, encoding);
    }

    /**
     * Prop constructor.
     *
     * @see #Prop(String, Class, String)
     */
    public Prop(String fileName, Class<?> callee) {
        this(fileName, callee, DEFAULT_ENCODING);
    }

    /**
     * Instantiates a new Prop.
     *
     * @param fileName the properties file's name in classpath or the sub directory of classpath
     * @param encoding the encoding
     * @param callee   the class's name in classpath or the sub directory of classpath
     */
    public Prop(String fileName, Class<?> callee, String encoding) {
        cache = new ClassPropertiesCache(fileName, callee, encoding);
    }

    @Override
    public void reload() {
        cache.updateCache();
    }

    @Override
    public String get(String key) {
        return cache.getCache().getProperty(key);
    }

    @Override
    public String get(String key, String defaultValue) {
        return cache.getCache().getProperty(key, defaultValue);
    }

    @Override
    public Integer getInt(String key) {
        return getInt(key, null);
    }

    @Override
    public Integer getInt(String key, Integer defaultValue) {
        return ConvertUtils.parseInt(get(key), defaultValue);
    }

    @Override
    public Long getLong(String key) {
        return getLong(key, null);
    }

    @Override
    public Long getLong(String key, Long defaultValue) {
        return ConvertUtils.parseLong(get(key), defaultValue);
    }

    @Override
    public Boolean getBoolean(String key) {
        return getBoolean(key, null);
    }

    @Override
    public Boolean getBoolean(String key, Boolean defaultValue) {
        return ConvertUtils.parseBoolean(get(key), defaultValue);
    }

    @Override
    public boolean containsKey(String key) {
        return cache.getCache().containsKey(key);
    }

    public Properties getProperties() {
        return cache.getCache();
    }
}
