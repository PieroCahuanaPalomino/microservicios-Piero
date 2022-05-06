/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.usuario.service.feignclients;

import com.usuario.service.modelos.Moto;
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
//Nombre y URL del microservicio
@FeignClient(name = "moto-service", url = "http://localhost:8003")
//Para acceder al ClienteFeign 
@RequestMapping("/moto")
public interface MotoFeignClient {

    @PostMapping()
    public Moto save(@RequestBody Moto moto);
    
    @GetMapping("/usuario/{usuarioId}")
    public List<Moto> getMotos(@PathVariable("usuarioId") int usuarioId);
    
}
