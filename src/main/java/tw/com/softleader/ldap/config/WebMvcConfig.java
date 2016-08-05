package tw.com.softleader.ldap.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import tw.com.softleader.web.mvc.config.WebMvcConfiguration;

@Lazy
@Configuration
@EnableWebMvc
@Primary
@ComponentScan(basePackages = "tw.com.softleader.**.web", useDefaultFilters = false, includeFilters = @Filter(Controller.class))
public class WebMvcConfig extends WebMvcConfiguration {

}
