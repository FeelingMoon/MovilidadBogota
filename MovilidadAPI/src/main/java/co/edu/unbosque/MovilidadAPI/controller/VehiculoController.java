package co.edu.unbosque.MovilidadAPI.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.MovilidadAPI.persistence.Multa;
import co.edu.unbosque.MovilidadAPI.persistence.Persona;
import co.edu.unbosque.MovilidadAPI.persistence.Vehiculo;
import co.edu.unbosque.MovilidadAPI.repository.VehiculoRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/Vehiculo")
public class VehiculoController {

	@Autowired
	private VehiculoRepository vehiculo;

	@DeleteMapping(path = "/Delete")
	public ResponseEntity<String> delete(@RequestParam String placa) {
		Optional<Vehiculo> vh = vehiculo.findByPlaca(placa);
		Persona pr = null;
		if (!vh.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND (CODE 404)");
		}
		Vehiculo tVh = vh.get();
		pr = tVh.getPersona();
		pr.getVehiculos().remove(tVh);
		vehiculo.delete(tVh);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("DELETED (CODE 202)");
	}

	@GetMapping(path = "/GetAll")
	public ResponseEntity<Iterable<Vehiculo>> getAll() {
		List<Vehiculo> all = (List<Vehiculo>) vehiculo.findAll();
		if (all.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(all);
		}
		return ResponseEntity.status(HttpStatus.FOUND).body(all);
	}

	@GetMapping(path = "/GetOneByPlaca")
	public ResponseEntity<Vehiculo> getOneByDocumento(@RequestParam String placa) {
		Optional<Vehiculo> tmp = vehiculo.findByPlaca(placa);
		if (!tmp.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.status(HttpStatus.FOUND).body(tmp.get());
	}

	@PutMapping("/Update")
	public ResponseEntity<String> update(@RequestParam String placa, @RequestParam String color) {
		Optional<Vehiculo> tmp = vehiculo.findByPlaca(placa);
		if (!tmp.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND (CODE 206)");
		} else {
			Vehiculo pr = tmp.get();
			pr.setColor(color);
			vehiculo.save(pr);
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("UPDATED (CODE 202)");
	}

	@PutMapping(path = "/AddMulta")
	public ResponseEntity<String> addMulta(@RequestParam String placa, @RequestParam String codigo,
			@RequestParam String descripcion, @RequestParam String valor, @RequestParam LocalDate fecha) {
		Optional<Vehiculo> tmp = vehiculo.findByPlaca(placa);
		if (!tmp.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND (CODE 404)");
		}
		Vehiculo vh = tmp.get();
		Multa m = new Multa();
		m.setCodigo(codigo);
		m.setDescripcion(descripcion);
		m.setFechaIngreso(fecha);
		m.setPagado(false);
		m.setValor(valor);
		m.setVehiculo(vh);
		vh.getMultas().add(m);
		vehiculo.save(vh);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("CREATED PENALTY FEE (CODE 202)");
	}
}
