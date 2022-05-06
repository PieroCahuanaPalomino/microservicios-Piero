/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carro.service.controlador;

import com.carro.service.entidades.Carro;
import com.carro.service.servicio.CarroService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author USUARIO
 */
@RestController
@RequestMapping("/carro")
public class CarroController {

    @Autowired
    private CarroService carroService;

    @GetMapping
    public ResponseEntity<List<Carro>> listarUsuario() {
        List<Carro> carros = carroService.getAll();
        if (carros.isEmpty()) {//si esta vacio que nos retorne que no hay contenido (para Listas de objetos)
            return ResponseEntity.noContent().build();
        }
        //Sino esta vacio retornamos nuestra lista normal
        return ResponseEntity.ok(carros);
    }

    @GetMapping("/{id}") //El PathVariable igual el id del Get maping con el de la variable
    public ResponseEntity<Carro> obtenerCarro(@PathVariable("id") int id) {
        Carro carro = carroService.getCarrooById(id);
        if (carro == null) {//si esta vacio que nos retorne que no lo ha encontrado (para objetos)
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(carro);
    }

    @PostMapping
    public ResponseEntity<Carro> guardarUsuario(@RequestBody Carro carro) {
        Carro nuevoCarro = carroService.save(carro);
        return ResponseEntity.ok(nuevoCarro);
    }
    
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Carro>> listarCarrosPorUsuarioId(@PathVariable("usuarioId") int id){
        List<Carro> carros=carroService.byUsuarioId(id);
        if(carros.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(carros);
    }
}
