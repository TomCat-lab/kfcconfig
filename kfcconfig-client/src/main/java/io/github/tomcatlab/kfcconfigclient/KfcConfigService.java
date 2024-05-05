package io.github.tomcatlab.kfcconfigclient;

import io.github.tomcatlab.kfcconfigclient.config.ConfigMeta;
import io.github.tomcatlab.kfcconfigclient.repository.KfcRepository;

public interface KfcConfigService {
    static KfcConfigService getInstance(ConfigMeta  configMeta){
        KfcRepository kfcRepository = KfcRepository.getDefault(configMeta);
        return new KfcConfigServiceImpl(kfcRepository.getConfig());
    }
    public String[] getPropertyNames() ;

    public Object getProperty(String name);


}
