package io.github.tomcatlab.kfcconfigclient;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Description for this class.
 *
 * @Author : kimmking(kimmking@apache.org)
 * @create 2024/4/19 00:39
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Import({KfcConfigRegistrar.class})
public @interface EnableKFCConfig {



}
