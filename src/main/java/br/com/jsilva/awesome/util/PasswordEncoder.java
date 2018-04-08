package br.com.jsilva.awesome.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.val;

@Component
public class PasswordEncoder {

	public static String Encoded(String password) {
		val passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.encode(password);
	}

	public static void main(String[] args) {
		System.out.println(Encoded("jpp123456"));
	}
}

// $2a$10$D5fCR6ZMTwcIvpHnmNI7veFklx9UAnffh3F8vipb5uWc/w0jv8f6G <= Esse aq
// joao_silva Senha: jpp123456
// $2a$10$fYRmH.cV7.Oxhb.wOP.D/uylpUTdz6pxEsSZ3M754e0U/e75YPEgm <= Administrador
// Senha: admin