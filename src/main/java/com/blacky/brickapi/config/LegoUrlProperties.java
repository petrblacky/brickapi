package com.blacky.brickapi.config;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "lego.url")
public class LegoUrlProperties {
    private String legoRetiredItem;
    private String brickeconomyItem;
}
