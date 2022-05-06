/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moto.service.controlador;

import com.moto.service.entidades.Moto;
import com.moto.service.servicios.MotoService;
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
@RequestMapping("/moto")
public class MotoController {
    @Autowired
    private MotoService motoService;
    
      @GetMapping
    public ResponseEntity<List<Moto>> listarUsuario() {
        List<Moto> motos = motoService.getAll();
        if (motos.isEmpty()) {//si esta vacio que nos retorne que no hay contenido (para Listas de objetos)
            return ResponseEntity.noContent().build();
        }
        //Sino esta vacio retornamos nuestra lista normal
        return ResponseEntity.ok(motos);
    }

    @GetMapping("/{id}") //El PathVariable igual el id del Get maping con el de la variable
    public ResponseEntity<Moto> obtenerMoto(@PathVariable("id") int id) {
        Moto moto = motoService.getCarrooById(id);
        if (moto == null) {//si esta vacio que nos retorne que no lo ha encontrado (para objetos)
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(moto);
    }

    @PostMapping
    public ResponseEntity<Moto> guardarUsuario(@RequestBody Moto moto) {
        Moto nuevaMoto = motoService.save(moto);
        return ResponseEntity.ok(nuevaMoto);
    }
    
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Moto>> listarMotosPorUsuarioId(@PathVariable("usuarioId") int id){
        List<Moto> motos=motoService.byUsuarioId(id);
        if(motos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(motos);
    }
    
    
}
