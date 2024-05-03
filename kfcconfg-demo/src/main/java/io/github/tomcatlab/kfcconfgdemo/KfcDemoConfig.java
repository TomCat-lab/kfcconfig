package io.github.tomcatlab.kfcconfgdemo;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "kfc")
public class KfcDemoConfig {
    private String a;


}
