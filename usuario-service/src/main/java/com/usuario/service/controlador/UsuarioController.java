/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.usuario.service.controlador;

import com.usuario.service.entidades.Usuario;
import com.usuario.service.modelos.Carro;
import com.usuario.service.modelos.Moto;
import com.usuario.service.servicio.UsuarioService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping
	public ResponseEntity<List<Usuario>> listarUsuario() {
		List<Usuario> usuarios = usuarioService.getAll();
		if (usuarios.isEmpty()) {// si esta vacio que nos retorne que no hay contenido (para Listas de objetos)
			return ResponseEntity.noContent().build();
		}
		// Sino esta vacio retornamos nuestra lista normal
		return ResponseEntity.ok(usuarios);
	}

	@GetMapping("/{id}") // El PathVariable igual el id del Get maping con el de la variable
	public ResponseEntity<Usuario> obtenerUsuario(@PathVariable("id") int id) {
		Usuario usuario = usuarioService.getUsuarioById(id);
		if (usuario == null) {// si esta vacio que nos retorne que no lo ha encontrado (para objetos)
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(usuario);
	}

	@PostMapping
	public ResponseEntity<Usuario> guardarUsuario(@RequestBody Usuario usuario) {
		Usuario nuevoUsuario = usuarioService.save(usuario);
		return ResponseEntity.ok(nuevoUsuario);
	}

	@CircuitBreaker(name = "carrosCB", fallbackMethod = "fallBackGetCarros")
	@GetMapping("/carros/{usuarioId}")
	public ResponseEntity<List<Carro>> listarCarros(@PathVariable("usuarioId") int id) {
		Usuario usuario = usuarioService.getUsuarioById(id);
		if (usuario == null) {
			return ResponseEntity.notFound().build();
		}
		List<Carro> carros = usuarioService.getCarros(id);
		return ResponseEntity.ok(carros);
	}

	@CircuitBreaker(name = "motosCB", fallbackMethod = "fallBackGetMotos")
	@GetMapping("/motos/{usuarioId}")
	public ResponseEntity<List<Moto>> listarMotos(@PathVariable("usuarioId") int id) {
		Usuario usuario = usuarioService.getUsuarioById(id);
		if (usuario == null) {
			return ResponseEntity.notFound().build();
		}
		List<Moto> motos = usuarioService.getMotos(id);
		return ResponseEntity.ok(motos);
	}

	@CircuitBreaker(name = "carrosCB", fallbackMethod = "fallBackSaveCarro")
	@PostMapping("/carro/{usuarioId}")
	public ResponseEntity<Carro> guardarCarro(@PathVariable("usuarioId") int usuarioId, @RequestBody Carro carro) {
		Carro nuevoCarro = usuarioService.saveCarro(usuarioId, carro);
		return ResponseEntity.ok(nuevoCarro);
	}

	@CircuitBreaker(name = "motosCB", fallbackMethod = "fallBackSaveMoto")
	@PostMapping("/moto/{usuarioId}")
	public ResponseEntity<Moto> guardarMoto(@PathVariable("usuarioId") int usuarioId, @RequestBody Moto moto) {
		Moto nuevaMoto = usuarioService.saveMoto(usuarioId, moto);
		return ResponseEntity.ok(nuevaMoto);
	}

	@CircuitBreaker(name = "todosCB", fallbackMethod = "fallBackGetTodos")
	@GetMapping("/todos/{usuarioId}")
	public ResponseEntity<Map<String, Object>> listarTodosLosVehiculos(@PathVariable("usuarioId") int usuarioId) {
		Map<String, Object> resultado = usuarioService.getUsuarioAndVehiculos(usuarioId);
		return ResponseEntity.ok(resultado);
	}

	private ResponseEntity<List<Carro>> fallBackGetCarros(@PathVariable("usuarioId") int id,
			RuntimeException exception) {
		return new ResponseEntity("El usuario " + id + " tiene los carros en el taller", HttpStatus.OK);
	}

	private ResponseEntity<Carro> fallBackSaveCarro(@PathVariable("usuarioId") int id, @RequestBody Carro carro,
			RuntimeException exception) {
		return new ResponseEntity("El usuario " + id + " no tiene dinero para los carros", HttpStatus.OK);
	}

	private ResponseEntity<List<Moto>> fallBackGetMotos(@PathVariable("usuarioId") int id, RuntimeException exception) {
		return new ResponseEntity("El usuario " + id + " tiene las motos en el taller", HttpStatus.OK);
	}

	private ResponseEntity<Moto> fallBackSaveMoto(@PathVariable("usuarioId") int id, @RequestBody Moto moto,
			RuntimeException exception) {
		return new ResponseEntity("El usuario " + id + " no tiene dinero para las motos", HttpStatus.OK);
	}

	private ResponseEntity<Map<String,Object>> fallBackGetTodos(@PathVariable("usuarioId") int id,
			RuntimeException exception) {
		return new ResponseEntity("El usuario " + id + " tiene los vehiculos en el taller", HttpStatus.OK);
	}
}
