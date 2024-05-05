package io.github.tomcatlab.kfcconfigclient.repository;

import io.github.tomcatlab.kfcconfigclient.config.ConfigMeta;

import java.util.Map;

/**
 * Class: KfcRepository
 * Author: cola
 * Date: 2024/5/4
 * Description: interface get config from kfc server
 */

public interface KfcRepository {
    static KfcRepository getDefault(ConfigMeta configMeta) {
        return new KfcRepositoryImpl(configMeta);
    }

    Map<String,String> getConfig();
}
