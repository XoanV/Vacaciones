package org.afdt.vacaciones;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SeguridadConfig {
	
	 @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

	http.authorizeHttpRequests(auth -> auth
        .requestMatchers("/inicio", "/css/**", "/js/**", "/img/**", "/registro", "/inicioemp", "/imagenes/**", "/perfilUsuario", "/paginaInicio", "/actualizarPerfil").permitAll()
        .anyRequest().authenticated()
    )
    .formLogin(form -> form
        .loginPage("/inicio")
        .loginProcessingUrl("/login")
        .permitAll()
    )
    .logout(logout -> logout
        .logoutSuccessUrl("/inicio")
    );

return http.build();
	 }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
