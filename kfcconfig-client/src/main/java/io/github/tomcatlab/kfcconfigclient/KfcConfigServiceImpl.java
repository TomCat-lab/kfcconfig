package io.github.tomcatlab.kfcconfigclient;

import lombok.Data;

import java.util.Map;

/**
 * Class: KfcConfigServiceImpl
 * Author: cola
 * Date: 2024/5/2
 * Description:
 */
@Data
public class KfcConfigServiceImpl implements KfcConfigService{
  public  Map<String,String> config;

    public  KfcConfigServiceImpl(Map<String,String> config){
        this.config = config;
    }

    public String[] getPropertyNames() {
        return config.keySet().toArray(new String[0]);
    }

    public Object getProperty(String name) {
        return config.get(name);
    }



}
