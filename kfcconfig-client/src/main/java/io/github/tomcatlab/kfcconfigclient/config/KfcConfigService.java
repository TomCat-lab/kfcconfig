package io.github.tomcatlab.kfcconfigclient.config;

import io.github.tomcatlab.kfcconfigclient.repository.KfcRepository;
import io.github.tomcatlab.kfcconfigclient.repository.KfcRepositoryChangeListener;
import org.springframework.context.ApplicationContext;

public interface KfcConfigService  extends KfcRepositoryChangeListener {
    static KfcConfigService getInstance(ApplicationContext applicationContext, ConfigMeta  configMeta){
        KfcRepository kfcRepository = KfcRepository.getDefault(configMeta);
        KfcConfigService kfcConfigService = new KfcConfigServiceImpl(applicationContext, kfcRepository.getConfig());
        kfcRepository.addListener(kfcConfigService);
        return kfcConfigService;
    }
    public String[] getPropertyNames() ;

    public Object getProperty(String name);


}
