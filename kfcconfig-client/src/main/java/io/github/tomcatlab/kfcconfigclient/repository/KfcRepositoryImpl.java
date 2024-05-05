package io.github.tomcatlab.kfcconfigclient.repository;

import cn.kimmking.utils.HttpUtils;
import com.alibaba.fastjson.TypeReference;
import io.github.tomcatlab.kfcconfigclient.config.ConfigMeta;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Slf4j
@Data
public class KfcRepositoryImpl implements KfcRepository, ApplicationContextAware {
    ApplicationContext applicationContext;
    ConfigMeta configMeta;
    Map<String,Long> versionMap = new HashMap();

    Map<String, Map<String, String>> configMap = new HashMap<>();

    List<KfcRepositoryChangeListener> listeners = new ArrayList<>();


    static ScheduledExecutorService excutor = Executors.newScheduledThreadPool(1);

    public KfcRepositoryImpl(ConfigMeta configMeta) {
        this.configMeta = configMeta;
        excutor.scheduleWithFixedDelay(this::heartbeat,1000,5000,java.util.concurrent.TimeUnit.MILLISECONDS);
    }

    @Override
    public Map<String, String> getConfig() {

        String key = configMeta.genKey();
        if(configMap.containsKey(key)) {
            return configMap.get(key);
        }
        return findAll();
    }

    @Override
    public void addListener(KfcRepositoryChangeListener listener) {
        listeners.add(listener);
    }

    public void  heartbeat()
    {
        String versionPath = configMeta.versionPath();
        Long newVersion = HttpUtils.httpGet(versionPath, Long.class);
        String versionKey = configMeta.genKey();
        Long oldVersion = versionMap.getOrDefault(versionKey, -1L);
        if (newVersion>oldVersion){
            log.info("version changed, new version is:{} ",newVersion);
            Map<String, String> newconfig = findAll();
            configMap.put(versionKey, newconfig);
            listeners.forEach(listener -> listener.onChange(new KfcRepositoryChangeListener.ChangeEvent(configMeta,newconfig)));
            versionMap.put(versionKey, newVersion);
        }
    }

    private @NotNull Map<String, String> findAll() {
        String listPath = configMeta.listPath();
        log.info(" list all configs from kfc config server.");
        List<Configs> configs = HttpUtils.httpGet(listPath, new TypeReference<List<Configs>>(){});
        Map<String,String> resultMap = new HashMap<>();
        configs.forEach(c -> resultMap.put(c.getPkey(), c.getPval()));
        return resultMap;
    }
}
