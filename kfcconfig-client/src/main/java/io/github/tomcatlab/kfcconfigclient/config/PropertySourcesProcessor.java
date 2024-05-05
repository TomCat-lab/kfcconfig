package io.github.tomcatlab.kfcconfigclient.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

@Data
@Slf4j
public class PropertySourcesProcessor implements BeanFactoryPostProcessor, ApplicationContextAware, PriorityOrdered, EnvironmentAware {
    Environment environment;
    ApplicationContext applicationContext;
    private final String PROPERTYSOURCENAME = "KfcPropertiesSource";
    private final String PROPERTYSOURCENAMES = "KfcPropertiesSources";

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        ConfigurableEnvironment ENV = (ConfigurableEnvironment) environment;
        if (ENV.containsProperty(PROPERTYSOURCENAMES)){
            log.info("KfcPropertiesSources already exist");
            return;
        }
        // mock map
//        Map<String,String> config = new HashMap<String, String>();
//        config.put("kfc.a","a100");
//        config.put("kfc.b","b100");
//        config.put("kfc.c","c100");
        String app = ENV.getProperty("kfcconfig.app", "app1");
        String env = ENV.getProperty("kfcconfig.env", "dev");
        String ns = ENV.getProperty("kfcconfig.ns", "public");
        String configServer = ENV.getProperty("kkconfig.configServer", "http://localhost:9129");
        ConfigMeta configMeta = new ConfigMeta(app, env, ns, configServer);
        KfcConfigService kfcConfigService = KfcConfigService.getInstance(applicationContext,configMeta);
        KfcProperties kfcProperties = new KfcProperties(PROPERTYSOURCENAME, kfcConfigService);
        CompositePropertySource composite= new CompositePropertySource(PROPERTYSOURCENAMES);
        composite.addPropertySource(kfcProperties);
        ENV.getPropertySources().addFirst(composite);
    }

    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
