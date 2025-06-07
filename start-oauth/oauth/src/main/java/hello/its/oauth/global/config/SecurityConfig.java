package hello.its.oauth.global.config;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.*;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import hello.its.oauth.domain.member.repository.MemberRepository;
import hello.its.oauth.global.security.filter.JwtAuthFilter;
import hello.its.oauth.global.security.handler.CustomOAuth2SuccessHandler;
import hello.its.oauth.global.security.provider.JwtAuthProvider;
import hello.its.oauth.global.security.service.JwtProvider;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtProvider jwtProvider;
	private final MemberRepository memberRepository;

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring()
			.requestMatchers(toH2Console());
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/", "/auth/**", "/login/**", "/members/sign-up").permitAll()
				.anyRequest().authenticated()
			)
			.addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class)
			.oauth2Login(oauth2 -> oauth2
					.successHandler(customOAuth2SuccessHandler())
				// .failureHandler()
			)
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			.csrf(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable);
		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(List.of("http://localhost:3000"));
		configuration.setAllowedMethods(List.of("POST", "GET", "PATCH", "OPTIONS", "DELETE"));
		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(List.of("*"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	public JwtAuthFilter jwtAuthFilter() {
		PathPatternRequestMatcher apiPath = PathPatternRequestMatcher
			.withDefaults()
			.matcher("/members");
		JwtAuthFilter filter = new JwtAuthFilter(apiPath);
		filter.setAuthenticationManager(authenticationManager());
		return filter;
	}

	@Bean
	public AuthenticationManager authenticationManager() {
		JwtAuthProvider provider = new JwtAuthProvider(jwtProvider);
		return new ProviderManager(provider);
	}

	@Bean
	public CustomOAuth2SuccessHandler customOAuth2SuccessHandler() {
		return new CustomOAuth2SuccessHandler(jwtProvider, memberRepository);
	}
}
