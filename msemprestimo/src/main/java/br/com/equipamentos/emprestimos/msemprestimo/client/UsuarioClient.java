package br.com.equipamentos.emprestimos.msemprestimo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "msusuario")
public interface UsuarioClient {

    @GetMapping("/api/v1/usuarios/{id}")
    void buscarPorId(@PathVariable("id") UUID id);
}
