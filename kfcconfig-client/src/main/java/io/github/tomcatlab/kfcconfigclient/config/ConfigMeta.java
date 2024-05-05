package io.github.tomcatlab.kfcconfigclient.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigMeta {
    private String app;
    private String env;
    private String ns;
    private String configServer;
}
