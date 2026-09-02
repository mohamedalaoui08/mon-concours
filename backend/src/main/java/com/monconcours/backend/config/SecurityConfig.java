package com.monconcours.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.monconcours.backend.security.JwtAuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;



@Configuration
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(
                                "/login",
                                "/ping",
                                "/concours/public",
                                "/error"
                        ).permitAll()
                        // Role de demande d'inscription

                        .requestMatchers(HttpMethod.POST, "/demandes-inscription")
                        .permitAll()

                        .requestMatchers("/demandes-inscription/**")
                        .hasRole("ADMIN")

                        // role pour les qcms

                        .requestMatchers(HttpMethod.GET, "/qcms/**")
                        .hasAnyRole("ETUDIANT", "ADMIN")

                        .requestMatchers(HttpMethod.POST, "/qcms/*/passer")
                        .hasRole("ETUDIANT")
                        .requestMatchers(HttpMethod.POST, "/qcms/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/qcms/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/qcms/**")
                        .hasRole("ADMIN")
                        // role pour les exercices
                        .requestMatchers(HttpMethod.GET, "/exercices/**")
                        .hasAnyRole("ETUDIANT", "ADMIN")

                        .requestMatchers(HttpMethod.POST, "/exercices/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/exercices/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/exercices/**")
                        .hasRole("ADMIN")
                        // role pour les offres abonnements
                        .requestMatchers(HttpMethod.GET, "/offres-abonnement/**")
                        .permitAll()

                        .requestMatchers(HttpMethod.POST, "/offres-abonnement/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/offres-abonnement/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/offres-abonnement/**")
                        .hasRole("ADMIN")
                        // ROLE DES ACTUALITES
                        .requestMatchers(HttpMethod.GET, "/actualites/**")
                        .hasAnyRole("ETUDIANT", "ADMIN")

                        .requestMatchers(HttpMethod.POST, "/actualites/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/actualites/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/actualites/**")
                        .hasRole("ADMIN")
                        // ROLE DE FORMATION
                        .requestMatchers(HttpMethod.GET, "/formations/**")
                        .hasAnyRole("ETUDIANT", "ADMIN")

                        .requestMatchers(HttpMethod.POST, "/formations/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/formations/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/formations/**")
                        .hasRole("ADMIN")
                        // ROLE DES ECOLES
                        .requestMatchers(HttpMethod.GET, "/ecoles/**")
                        .permitAll()

                        .requestMatchers(HttpMethod.POST, "/ecoles/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/ecoles/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/ecoles/**")
                        .hasRole("ADMIN")

                        // ROLE DE QCM ET CHOIX

                        .requestMatchers(HttpMethod.GET, "/questions/**", "/choix/**")
                        .hasAnyRole("ETUDIANT", "ADMIN")

                        .requestMatchers(HttpMethod.POST, "/questions/**", "/choix/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/questions/**", "/choix/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/questions/**", "/choix/**")
                        .hasRole("ADMIN")

                        // RECUPER LE RESULTAT DE QCM

                        .requestMatchers(HttpMethod.GET, "/resultats/mes-resultats")
                        .hasRole("ETUDIANT")

                        .requestMatchers("/resultats/**")
                        .hasRole("ADMIN")


                        .requestMatchers(HttpMethod.GET, "/favoris/mes-favoris")
                        .hasRole("ETUDIANT")

                        .requestMatchers(HttpMethod.GET, "/favoris/**")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/favoris")
                        .hasRole("ETUDIANT")
                        .requestMatchers(HttpMethod.PUT, "/favoris/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/favoris/**")
                        .hasAnyRole("ETUDIANT", "ADMIN")

                        .requestMatchers(HttpMethod.POST, "/abonnements/souscrire")
                        .hasRole("ETUDIANT")
                        .requestMatchers(HttpMethod.GET, "/abonnements/mes-abonnements")
                        .hasRole("ETUDIANT")
                        .requestMatchers(HttpMethod.GET, "/abonnements/mon-abonnement-actif")
                        .hasRole("ETUDIANT")
                        .requestMatchers("/abonnements/**")
                        .hasRole("ADMIN")



                        //ROLE DE CONCOURS

                        .requestMatchers(HttpMethod.GET, "/concours/public")
                        .permitAll()

                        .requestMatchers(HttpMethod.GET, "/concours/**")
                        .hasAnyRole("ETUDIANT", "ADMIN")

                        .requestMatchers(HttpMethod.POST, "/concours/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/concours/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/concours/**")
                        .hasRole("ADMIN")

                        //ROLE PROFILE ETUDIANT

                        .requestMatchers(HttpMethod.GET, "/etudiants/mon-profil")
                        .hasRole("ETUDIANT")
                        .requestMatchers(HttpMethod.PUT, "/etudiants/mon-profil")
                        .hasRole("ETUDIANT")

                        .requestMatchers("/etudiants/**")
                        .hasRole("ADMIN")

                        // ROLE D'ADMIN

                        .requestMatchers("/admins/**")
                        .hasRole("ADMIN")
                        .anyRequest().authenticated()
                );
        http.addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
        );
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:4200"));

        configuration.setAllowedMethods(
                List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")
        );

        configuration.setAllowedHeaders(
                List.of("Authorization", "Content-Type")
        );

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}