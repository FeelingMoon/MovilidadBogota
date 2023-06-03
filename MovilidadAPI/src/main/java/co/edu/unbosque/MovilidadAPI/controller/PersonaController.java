package co.edu.unbosque.MovilidadAPI.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.MovilidadAPI.persistence.Persona;
import co.edu.unbosque.MovilidadAPI.persistence.Vehiculo;
import co.edu.unbosque.MovilidadAPI.repository.PersonaRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/Persona")
public class PersonaController {

	@Autowired
	private PersonaRepository persona;

	@PostMapping(path = "/Add")
	public ResponseEntity<String> add(@RequestParam String nombre, @RequestParam Integer documento,
			@RequestParam LocalDate fechaExpedicion) {
		Persona tmp = new Persona();
		tmp.setNombre(nombre);
		tmp.setDocumento(documento);
		tmp.setFechaExpedicion(fechaExpedicion);
		persona.save(tmp);
		return ResponseEntity.status(HttpStatus.CREATED).body("CREATED (CODE 201)");
	}

	@PutMapping(path = "/AddVehicle")
	public ResponseEntity<String> addVehicle(@RequestParam Integer documento, @RequestParam String placa,
			@RequestParam String color) {
		Optional<Persona> tmp = persona.findByDocumento(documento);
		if (!tmp.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND (404)");
		} else {
			Persona pr = tmp.get();
			Vehiculo vh = new Vehiculo();
			vh.setColor(color);
			vh.setPlaca(placa);
			vh.setPersona(pr);
			vh.setMultas(new ArrayList<>());
			pr.getVehiculos().add(vh);
			persona.save(pr);
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("CREATED VEHICLE (CODE 202)");
	}

	@PutMapping(path = "/Update")
	public ResponseEntity<String> update(@RequestParam Integer id, @RequestParam String nombre,
			@RequestParam Integer documento, @RequestParam LocalDate fechaExpedicion) {
		Optional<Persona> tmp = persona.findById(id);
		if (!tmp.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND (CODE 206)");
		} else {
			Persona pr = tmp.get();
			pr.setNombre(nombre);
			pr.setDocumento(documento);
			pr.setFechaExpedicion(fechaExpedicion);
			persona.save(pr);
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("UPDATED (CODE 202)");
	}

	@GetMapping(path = "/GetAll")
	public ResponseEntity<Iterable<Persona>> getAll() {
		List<Persona> all = (List<Persona>) persona.findAll();
		if (all.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(all);
		}
		return ResponseEntity.status(HttpStatus.FOUND).body(all);
	}

	@GetMapping(path = "/GetOneByDocument")
	public ResponseEntity<Persona> getOneByDocumento(@RequestParam Integer documento) {
		Optional<Persona> tmp = persona.findByDocumento(documento);
		if (!tmp.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.status(HttpStatus.FOUND).body(tmp.get());
	}

	@DeleteMapping(path = "/Delete")
	public ResponseEntity<String> delete(@RequestParam Integer documento) {
		Optional<Persona> pr = persona.findByDocumento(documento);
		if (!pr.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND (CODE 404)");
		}
		persona.delete(pr.get());
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("DELETED (CODE 202)");
	}

	@PutMapping("/TransferVehicle")
	public ResponseEntity<String> transferVehicle(@RequestParam String placa, @RequestParam Integer docOrigen,
			@RequestParam Integer docDestino) {
		Optional<Persona> p1 = persona.findByDocumento(docOrigen);
		Optional<Persona> p2 = persona.findByDocumento(docDestino);
		if (!p1.isPresent() || !p2.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND (CODE 404)");
		}
		Persona tmp1 = p1.get();
		Persona tmp2 = p2.get();
		Vehiculo vh = null;
		for (int i = 0; i < tmp1.getVehiculos().size(); i++) {
			Vehiculo tmp = tmp1.getVehiculos().get(i);
			if (tmp.getPlaca().equals(placa)) {
				vh = tmp;
				break;
			}
		}
		if (vh != null) {
			tmp1.getVehiculos().remove(vh);
			vh.setPersona(tmp2);
			tmp2.getVehiculos().add(vh);
			persona.save(tmp1);
			persona.save(tmp2);
			return ResponseEntity.status(HttpStatus.OK).body("OK (CODE 200)");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD REQUEST (CODE 400)");
	}

}
