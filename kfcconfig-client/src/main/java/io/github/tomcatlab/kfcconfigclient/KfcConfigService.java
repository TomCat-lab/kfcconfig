package io.github.tomcatlab.kfcconfigclient;

import java.util.Map;

public interface KfcConfigService {
    public String[] getPropertyNames() ;

    public Object getProperty(String name);


}
