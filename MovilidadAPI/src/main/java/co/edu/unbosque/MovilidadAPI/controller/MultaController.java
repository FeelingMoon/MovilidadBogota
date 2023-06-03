package co.edu.unbosque.MovilidadAPI.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.MovilidadAPI.persistence.Multa;
import co.edu.unbosque.MovilidadAPI.repository.MultaRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/Multa")
public class MultaController {

	@Autowired
	private MultaRepository multa;

	@PutMapping("/Update")
	public ResponseEntity<String> update(@RequestParam Integer id, @RequestParam boolean pagado) {
		Optional<Multa> tmp = multa.findById(id);
		if (!tmp.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND (CODE 206)");
		} else {
			Multa pr = tmp.get();
			pr.setPagado(pagado);
			multa.save(pr);
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("UPDATED (CODE 202)");
	}

	@DeleteMapping(path = "/Delete")
	public ResponseEntity<String> delete(@RequestParam Integer id) {
		Optional<Multa> pr = multa.findById(id);
		if (!pr.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND (CODE 404)");
		}
		multa.delete(pr.get());
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("DELETED (CODE 202)");
	}

}
