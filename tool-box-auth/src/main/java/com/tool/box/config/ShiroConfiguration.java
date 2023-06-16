package com.tool.box.config;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.TextConfigurationRealm;
import org.springframework.context.annotation.Bean;

/**
 * @Author v_haimiyang
 * @Date 2023/5/26 17:03
 * @Version 1.0
 */
public class ShiroConfiguration {

    @Bean
    public Realm realm() {
        TextConfigurationRealm realm = new TextConfigurationRealm();
//        realm.setUserDefinitions("joe.coder=password,user\n" +
//                "jill.coder=password,admin");
//
//        realm.setRoleDefinitions("admin=read,write\n" +
//                "user=read");
//        realm.setCachingEnabled(true);
        return null;
    }
}
