package ma.hiddenfounders.codingchallenge.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.mobile.device.DeviceHandlerMethodArgumentResolver;
import org.springframework.mobile.device.DeviceResolverHandlerInterceptor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@Import({ WebSecurityConfig.class, JPAConfig.class, MongoConfig.class, CORSConfig.class })
public class WebAppConfig implements WebMvcConfigurer {

   private static final String PREFIX = "/WEB-INF/";
   private static final String SUFFIX = ".jsp";

   @Bean
   public ResourceUrlEncodingFilter resourceUrlEncodingFilter() {
      return new ResourceUrlEncodingFilter();
   }

   @Bean
   public DeviceResolverHandlerInterceptor deviceResolverHandlerInterceptor() {
      return new DeviceResolverHandlerInterceptor();
   }

   @Bean
   public DeviceHandlerMethodArgumentResolver deviceHandlerMethodArgumentResolver() {
       return new DeviceHandlerMethodArgumentResolver();
   }
   
   @Override
   public void configureViewResolvers(ViewResolverRegistry registry) {
      InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
      viewResolver.setViewClass(JstlView.class);
      viewResolver.setPrefix(PREFIX);
      viewResolver.setSuffix(SUFFIX);
      registry.viewResolver(viewResolver);
   }

   @Override
   public void addResourceHandlers(ResourceHandlerRegistry registry) {
      registry.addResourceHandler("/src/**").addResourceLocations("/src/");
      registry.addResourceHandler("/node_modules/**").addResourceLocations("/node_modules/");
   }

   @Override
   public void addInterceptors(InterceptorRegistry registry) {
      registry.addInterceptor(deviceResolverHandlerInterceptor());
   }
   
   @Override
   public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
       argumentResolvers.add(deviceHandlerMethodArgumentResolver());
   }

   @Override
   public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
      configurer.enable();
   }
}