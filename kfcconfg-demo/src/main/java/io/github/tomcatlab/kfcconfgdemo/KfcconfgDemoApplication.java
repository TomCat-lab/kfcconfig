package io.github.tomcatlab.kfcconfgdemo;

import io.github.tomcatlab.kfcconfigclient.annotation.EnableKFCConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableConfigurationProperties(KfcDemoConfig.class)
@EnableKFCConfig
@RestController
public class KfcconfgDemoApplication {
    @Autowired
    private KfcDemoConfig kfcDemoConfig;
    @Autowired
    Environment environment;


    @Value("${kfc.a}")
    private String a;

    @Value("${kfc.b}")
    private String b;
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

    @GetMapping("/demo")
    public String demo() {
        return "kfc.a = " + a + "\n"
                + "kfc.b = " + b + "\n"
                + "demo.a = " + kfcDemoConfig.getA() + "\n"
                + "demo.b = " + kfcDemoConfig.getB() + "\n";
    }
}
