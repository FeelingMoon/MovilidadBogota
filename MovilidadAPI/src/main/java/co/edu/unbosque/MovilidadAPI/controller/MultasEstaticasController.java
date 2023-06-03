package co.edu.unbosque.MovilidadAPI.controller;

import java.util.List;

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

	@PostMapping(path = "")
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

	@GetMapping(path = "")
	public ResponseEntity<Iterable<MultasEstaticas>> getAll() {
		List<MultasEstaticas> all = (List<MultasEstaticas>) multas.findAll();
		if (all.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(all);
		}
		return ResponseEntity.status(HttpStatus.FOUND).body(all);
	}
}
