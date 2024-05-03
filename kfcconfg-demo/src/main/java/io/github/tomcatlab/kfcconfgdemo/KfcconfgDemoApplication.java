package io.github.tomcatlab.kfcconfgdemo;

import io.github.tomcatlab.kfcconfigclient.EnableKFCConfig;
import io.github.tomcatlab.kfcconfigclient.KfcConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableConfigurationProperties(KfcDemoConfig.class)
@EnableKFCConfig
public class KfcconfgDemoApplication {
    @Autowired
    private KfcDemoConfig kfcDemoConfig;
    @Autowired
    Environment environment;


    @Value("${kfc.a}")
    private String a;
    public static void main(String[] args) {
        SpringApplication.run(KfcconfgDemoApplication.class, args);
    }

    @Bean
    ApplicationRunner runner() {
        System.out.printf(Arrays.toString(environment.getActiveProfiles()));
        return args -> {
            System.out.println("old value:"+kfcDemoConfig.getA());
            System.out.println(a);
            Map<String,String> map = new HashMap<>();
            map.put("kfc.a","new100");
            System.out.println("new value:"+kfcDemoConfig.getA());

        };
    }
}
