package io.github.tomcatlab.kfcconfigclient.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * Class: KfcConfigServiceImpl
 * Author: cola
 * Date: 2024/5/2
 * Description:
 */
@Data
@Slf4j
public class KfcConfigServiceImpl implements KfcConfigService{
  public  Map<String,String> config;

    ApplicationContext applicationContext;

    public  KfcConfigServiceImpl(ApplicationContext applicationContext,Map<String,String> config){
        this.config = config;
        this.applicationContext =applicationContext;
    }

    public String[] getPropertyNames() {
        return config.keySet().toArray(new String[0]);
    }

    public Object getProperty(String name) {
        return config.get(name);
    }


    @Override
    public void onChange(ChangeEvent event) {
        this.config = event.config();
        if(!config.isEmpty()) {
           log.info("fire an EnvironmentChangeEvent with keys:{}" , config.keySet());
            applicationContext.publishEvent(new EnvironmentChangeEvent(config.keySet()));
        }
    }
}
