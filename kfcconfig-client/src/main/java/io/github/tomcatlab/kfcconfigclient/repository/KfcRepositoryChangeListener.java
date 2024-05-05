package io.github.tomcatlab.kfcconfigclient.repository;

import io.github.tomcatlab.kfcconfigclient.config.ConfigMeta;

import java.util.Map;

public interface KfcRepositoryChangeListener {

    record ChangeEvent(ConfigMeta meta, Map<String, String> config) {}

    void onChange(ChangeEvent event);
}
