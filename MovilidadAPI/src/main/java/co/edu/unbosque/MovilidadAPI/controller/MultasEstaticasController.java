package co.edu.unbosque.MovilidadAPI.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.MovilidadAPI.persistence.MultasEstaticas;
import co.edu.unbosque.MovilidadAPI.repository.MultasEstaticasRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/MultasEstaticas")
public class MultasEstaticasController {

	@Autowired
	private MultasEstaticasRepository multas;

	@PostMapping(path = "/Add")
	public ResponseEntity<String> add(@RequestParam String codigo, @RequestParam String descripcion,
			@RequestParam String multa, @RequestParam boolean inmovilizacion) {
		MultasEstaticas tmp = new MultasEstaticas();
		tmp.setCodigo(codigo);
		tmp.setDescripcion(descripcion);
		tmp.setMulta(multa);
		tmp.setInmovilizacion(inmovilizacion);
		multas.save(tmp);
		return ResponseEntity.status(HttpStatus.CREATED).body("CREATED (CODE 201)");
	}

	@GetMapping(path = "/GetAll")
	public ResponseEntity<Iterable<MultasEstaticas>> getAll() {
		List<MultasEstaticas> all = (List<MultasEstaticas>) multas.findAll();
		if (all.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(all);
		}
		return ResponseEntity.status(HttpStatus.FOUND).body(all);
	}

	@GetMapping(path = "/GetOneByCode")
	public ResponseEntity<MultasEstaticas> getOneByCode(@RequestParam String codigo) {
		Optional<MultasEstaticas> tmp = multas.findByCodigo(codigo);
		if (!tmp.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.status(HttpStatus.FOUND).body(tmp.get());
	}
}
