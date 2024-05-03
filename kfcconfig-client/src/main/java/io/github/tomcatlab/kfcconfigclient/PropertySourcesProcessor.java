package io.github.tomcatlab.kfcconfigclient;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

@Data
@Slf4j
public class PropertySourcesProcessor implements BeanFactoryPostProcessor, PriorityOrdered, EnvironmentAware {
    Environment environment;
    private final String PROPERTYSOURCENAME = "KfcPropertiesSource";
    private final String PROPERTYSOURCENAMES = "KfcPropertiesSources";

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        ConfigurableEnvironment env = (ConfigurableEnvironment) environment;
        if (env.containsProperty(PROPERTYSOURCENAMES)){
            log.info("KfcPropertiesSources already exist");
            return;
        }
        // mock map
        Map<String,String> config = new HashMap<String, String>();
        config.put("kfc.a","a100");
        config.put("kfc.b","b100");
        config.put("kfc.c","c100");
        KfcConfigService kfcConfigService = new KfcConfigServiceImpl(config);
        KfcProperties kfcProperties = new KfcProperties(PROPERTYSOURCENAME, kfcConfigService);
        CompositePropertySource composite= new CompositePropertySource(PROPERTYSOURCENAMES);
        composite.addPropertySource(kfcProperties);
        env.getPropertySources().addFirst(composite);
    }

    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
