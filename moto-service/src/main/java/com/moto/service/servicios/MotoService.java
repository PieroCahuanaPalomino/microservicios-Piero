/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moto.service.servicios;

import com.moto.service.entidades.Moto;
import com.moto.service.respositorio.MotoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author USUARIO
 */
@Service
public class MotoService {

    @Autowired
    private MotoRepository motoRepository;
    
    
    public List<Moto> getAll() {
        return motoRepository.findAll();
    }

    public Moto getCarrooById(int id) {
        return motoRepository.findById(id).orElse(null);
    }

    public Moto save(Moto moto) {
        Moto nuevaMoto = motoRepository.save(moto);
        return nuevaMoto;
    }
    
    
    //Obetener todos las motos de un Usuario
    public List<Moto> byUsuarioId(int usuarioId){
        return motoRepository.findByUsuarioId(usuarioId);
    }
}
