package com.vaadin.books;

import com.vaadin.books.ui.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurityConfigurerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@SpringBootApplication
public class BooksApplication extends VaadinWebSecurityConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(BooksApplication.class, args);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		super.configure(http);
		setLoginView(http, LoginView.class);
	}

	@Override
	@Bean
	public UserDetailsService userDetailsServiceBean() throws Exception {
		return new InMemoryUserDetailsManager(
				User.withUsername("Tomek")
						.password("{noop}p")
						.roles("ADMIN")
						.build(),
				User.withUsername("Ania")
						.password("{noop}p")
						.roles("ADMIN")
						.build(),
				User.withUsername("Oskar")
						.password("{noop}p")
						.roles("ADMIN")
						.build()
		);

	}
}
