package ma.hiddenfounders.codingchallenge.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ma.hiddenfounders.codingchallenge.security.filter.AuthenticationTokenFilter;
import ma.hiddenfounders.codingchallenge.security.handler.RestUnauthorizedEntryPoint;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

   public WebSecurityConfig() {
      super();
   }

   @Autowired
   private RestUnauthorizedEntryPoint authenticationEntryPoint;

   @Autowired
   private AccessDeniedHandler restAccessDeniedHandler;

   @Autowired
   private UserDetailsService userDetailsService;

   @Autowired
   public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
      auth
          .userDetailsService(userDetailsService)
          .passwordEncoder(passwordEncoder());
   }

   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

   @Bean
   public AuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
      return new AuthenticationTokenFilter();
   }

   @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
   @Override
   public AuthenticationManager authenticationManagerBean() throws Exception {
      return super.authenticationManagerBean();
   }
   
   @Override
   protected void configure(HttpSecurity http) throws Exception {
      http
         .cors()
            .and()
         .headers().disable()
         .csrf().disable()
         .authorizeRequests()
            .antMatchers(HttpMethod.GET, 
                  "/", 
                  "/WEB-INF/*.jsp", 
                  "/**/favicon.ico", 
                  "/**/*.html", 
                  "/**/*.css",
                  "/**/*.js", 
                  "/**/*.png", 
                  "/**/*.woff",
                  "/**/*.woff2",
                  "/**/*.ttf", 
                  "/**/*.otf", 
                  "/**/*.eot", 
                  "/**/*.svg").permitAll()
               .antMatchers("/auth/**").permitAll()
               .antMatchers("/connect/**").permitAll()
               .antMatchers("/facebook/**").permitAll()
            .anyRequest()
               .authenticated()
               .and()
            .exceptionHandling()
               .authenticationEntryPoint(authenticationEntryPoint)
               .accessDeniedHandler(restAccessDeniedHandler)
               .and()
            .sessionManagement()
               .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

      // Custom JWT based security filter
      http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

      // disable page caching
      http.headers().cacheControl();
   }
}