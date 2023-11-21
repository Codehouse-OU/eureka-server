package ee.codehouse.eureka.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.ldap.LdapBindAuthenticationManagerFactory;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.security.web.SecurityFilterChain;

@Profile("ldap")
@Configuration
@EnableWebSecurity
public class LdapAuthConfig {
    @Value("${ldap.password}")
    private String managerPassword;
    @Value("${ldap.username}")
    private String managerDn;
    @Value("${ldap.url}")
    private String url;
    @Value("${ldap.userSearchFilter}")
    private String userSearchFilter;
    @Value("${ldap.groupSearchFilter}")
    private String groupSearchFilter;
    @Value("${ldap.userSearchBase}")
    private String userSearchBase;
    @Value("${ldap.groupSearchBase}")
    private String groupSearchBase;
    @Value("${ldap.allowedRole}")
    private String allowedRole;

    @Bean
    public LdapContextSource contextSource() {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl(url);
        contextSource.setUserDn(managerDn);
        contextSource.setPassword(managerPassword);
        contextSource.setPooled(true);

        return contextSource;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz.anyRequest().hasAuthority(allowedRole.toUpperCase())) // Requests require authentication and correct role
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF
                .httpBasic(Customizer.withDefaults()); // Use Basic authentication

        return http.build();
    }

    @Bean
    public LdapAuthoritiesPopulator authorities(BaseLdapPathContextSource contextSource) {
        var authorities = new DefaultLdapAuthoritiesPopulator(contextSource, groupSearchBase);
        authorities.setGroupSearchFilter(groupSearchFilter);
        return authorities;
    }

    @Bean
    public AuthenticationManager authenticationManager(BaseLdapPathContextSource contextSource, LdapAuthoritiesPopulator authorities) {
        LdapBindAuthenticationManagerFactory factory = new LdapBindAuthenticationManagerFactory(contextSource);
        factory.setUserSearchBase(userSearchBase);
        factory.setUserSearchFilter(userSearchFilter);
        factory.setLdapAuthoritiesPopulator(authorities);
        return factory.createAuthenticationManager();
    }
}
