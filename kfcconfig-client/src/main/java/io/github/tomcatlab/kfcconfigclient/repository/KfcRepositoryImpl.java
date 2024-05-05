package io.github.tomcatlab.kfcconfigclient.repository;

import cn.kimmking.utils.HttpUtils;
import com.alibaba.fastjson.TypeReference;
import io.github.tomcatlab.kfcconfigclient.config.ConfigMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


public class KfcRepositoryImpl implements KfcRepository{
    ConfigMeta configMeta;
    Map<String,Long> versionMap = new HashMap();

    static ScheduledExecutorService excutor = Executors.newScheduledThreadPool(1);

    public KfcRepositoryImpl(ConfigMeta configMeta) {
        this.configMeta = configMeta;
        excutor.scheduleWithFixedDelay(this::heartbeat,1000,10000,java.util.concurrent.TimeUnit.MILLISECONDS);
    }

    @Override
    public Map<String, String> getConfig() {

        String listPath = configMeta.getConfigServer() + "/list?app=" + configMeta.getApp()
                + "&env=" + configMeta.getEnv() + "&ns=" + configMeta.getNs();
        List<Configs> configs = HttpUtils.httpGet(listPath, new TypeReference<List<Configs>>(){});
        Map<String,String> resultMap = new HashMap<>();
        configs.forEach(c -> resultMap.put(c.getPkey(), c.getPval()));
        return resultMap;
    }

    public void  heartbeat()
    {
        String heartbeatpath = configMeta.getConfigServer()+"/heartbeat?a"
                +configMeta.getApp()+"&e"
                +configMeta.getEnv()+"&n";
    }
}
