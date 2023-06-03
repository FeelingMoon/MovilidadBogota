package co.edu.unbosque.MovilidadAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.MovilidadAPI.repository.MultaRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/Multa")
public class MultaController {

	@Autowired
	private MultaRepository multa;

}
