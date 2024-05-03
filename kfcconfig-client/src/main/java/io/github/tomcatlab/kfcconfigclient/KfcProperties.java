package io.github.tomcatlab.kfcconfigclient;

import org.springframework.core.env.EnumerablePropertySource;

public class KfcProperties extends EnumerablePropertySource<KfcConfigService> {

    public KfcProperties(String name, KfcConfigService source) {
        super(name, source);
    }

    protected KfcProperties(String name) {
        super(name);
    }

    public String[] getPropertyNames() {
        return source.getPropertyNames();
    }

    public Object getProperty(String name) {
        return source.getProperty(name);
    }
}
