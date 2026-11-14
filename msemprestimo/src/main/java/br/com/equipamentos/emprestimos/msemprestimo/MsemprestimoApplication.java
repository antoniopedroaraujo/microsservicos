package br.com.equipamentos.emprestimos.msemprestimo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsemprestimoApplication {
	public static void main(String[] args) {
		SpringApplication.run(MsemprestimoApplication.class, args);
	}
}
