package liangyongqi.iam.Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Service
public class PropertiesEditor {
    private Properties properties; // 移除static
    @Autowired
    private ConfigurableEnvironment environment;
    @Autowired
    private ResourceLoader resourceLoader; // 确保ResourceLoader被正确注入

    /**
     * 读取配置文件（所有项目）
     * @return Map<String, String>，键为配置项名称，值为配置项值
     */
    public Map<String, String> readProperties() {
        Map<String, String> configMap = new HashMap<>();
        properties = new Properties();
        String filePath = "classpath:application.properties"; // 使用classpath路径

        try (InputStream input = resourceLoader.getResource(filePath).getInputStream()) {
            properties.load(input);

            // 遍历Properties对象，将所有配置项存入Map
            for (String key : properties.stringPropertyNames()) {
                configMap.put(key, properties.getProperty(key));
            }
        } catch (IOException e) {
            System.err.println("读取配置文件时发生错误: " + e.getMessage());
        }

        return configMap;
    }

    /**
     * 修改配置文件（application.properties和内存中）
     * @param configName 配置项名称
     * @param configValue 配置项值
     * @return boolean
     */
    public boolean updateConfig(String configName, String configValue) {
        try {
            // 修改application.properties文件
            String filePath = "classpath:application.properties"; // 使用classpath路径
            Properties properties = new Properties();
            Resource resource = resourceLoader.getResource(filePath);
            File file = resource.getFile();

            // 读取现有配置
            if (file.exists()) {
                try (InputStream input = new FileInputStream(file)) {
                    properties.load(input);
                }
            }

            // 更新配置
            properties.setProperty(configName, configValue);

            // 写回文件
            try (OutputStream output = new FileOutputStream(file)) {
                properties.store(output, null);
            }

            // 更新内存中的配置
            MutablePropertySources propertySources = environment.getPropertySources();
            if (propertySources.contains("applicationConfig: [classpath:/application.properties]")) {
                PropertiesPropertySource propertySource = (PropertiesPropertySource) propertySources.get("applicationConfig: [classpath:/application.properties]");
                propertySource.getSource().put(configName, configValue);
            }

            // 更新缓存的配置文件内容
            if (this.properties != null) {
                this.properties.setProperty(configName, configValue);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}