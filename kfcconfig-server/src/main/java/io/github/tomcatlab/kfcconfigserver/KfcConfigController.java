package io.github.tomcatlab.kfcconfigserver;

import io.github.tomcatlab.kfcconfigserver.dal.ConfigsMapper;
import io.github.tomcatlab.kfcconfigserver.model.Configs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class KfcConfigController {
    @Autowired
    ConfigsMapper configsMapper;

    Map<String,Long> Versions = new HashMap<>();
    @GetMapping("/list")
    public List<Configs> list (String app, String env, String ns){
        return configsMapper.list(app, env, ns);
    }

    @PostMapping("/update")
    public List<Configs> update (@RequestParam("app") String app,
                                 @RequestParam("env") String env,
                                 @RequestParam("ns") String ns,
                                 @RequestBody Map<String,String> params){
        params.forEach((k,v)->{
            insertOrUpdate( new Configs(app,env, ns, k, v));
        });
        Versions.put(app+"-"+env+"-"+ns, System.currentTimeMillis());
        return configsMapper.list(app, env, ns);
    }

    private void insertOrUpdate(Configs configs) {
       Configs conf = configsMapper.select(configs.getApp(), configs.getEnv(), configs.getNs(), configs.getPkey());
       if (conf == null){
           configsMapper.insert(configs);
       }else {
           configsMapper.update(configs);
       }
    }

    @GetMapping("/version")
    public long version(String app, String env, String ns){
        return Versions.getOrDefault(app+"-"+env+"-"+ns, -1L);
    }
}
