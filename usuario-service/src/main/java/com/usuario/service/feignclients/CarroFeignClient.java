/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.usuario.service.feignclients;

import com.usuario.service.modelos.Carro;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author USUARIO
 */
//Nombre y URL del microservicio   ,url="http://localhost:8002
@FeignClient(name="carro-service")
//Para acceder al ClienteFeign 
@RequestMapping("/carro")
public interface CarroFeignClient {
    
    @PostMapping()
    public Carro save(@RequestBody Carro carro);
    
    @GetMapping("/usuario/{usuarioId}")
    public List<Carro> getCarros(@PathVariable("usuarioId") int usuarioId);
}
