package tw.com.softleader.ldap.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.stereotype.Component;

@Configuration
@PropertySource({"classpath:ldap.properties"})
@ComponentScan(basePackages = {"tw.com.softleader.ldap.repository"},
    useDefaultFilters = false, includeFilters = {@Filter(Component.class)})
public class LdapConfiguration {

  @Autowired
  Environment env;

  @Bean
  public LdapContextSource contextSource() {
    LdapContextSource contextSource = new LdapContextSource();
    contextSource.setUrl(env.getRequiredProperty("ldap.url"));
    contextSource.setBase(env.getRequiredProperty("ldap.base"));
    contextSource.setUserDn(env.getRequiredProperty("ldap.userDn"));
    contextSource.setPassword(env.getRequiredProperty("ldap.password"));
    return contextSource;
  }

  @Bean
  public LdapTemplate ldapTemplate(LdapContextSource contextSource) {
    return new LdapTemplate(contextSource);
  }

}
