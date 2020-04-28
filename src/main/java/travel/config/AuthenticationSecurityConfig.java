package travel.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import travel.security.AuthenticationFilter;
import travel.security.TokenAuthenticator;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true
)
public class AuthenticationSecurityConfig extends WebSecurityConfigurerAdapter {

    private TokenAuthenticator tokenAuthenticator;
    private ObjectMapper objectMapper;

    public AuthenticationSecurityConfig(TokenAuthenticator tokenAuthenticator, ObjectMapper objectMapper) {
        super(true);
        this.tokenAuthenticator = tokenAuthenticator;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                .and()
                .anonymous()
                .and()
                .servletApi()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .permitAll()
                .antMatchers("/")
                .permitAll()
                .antMatchers("/favicon.ico")
                .permitAll()
                .antMatchers(HttpMethod.POST,"/signin")
                .permitAll()
                .and()
                .headers()
                .cacheControl();


        http.authorizeRequests().anyRequest().hasRole("USER");

        http.addFilterBefore(new AuthenticationFilter(tokenAuthenticator, objectMapper), UsernamePasswordAuthenticationFilter.class);
    }

}
