package co.edu.unbosque.MovilidadAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.MovilidadAPI.repository.VehiculoRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/Vehiculo")
public class VehiculoController {

	@Autowired
	private VehiculoRepository vehiculo;

}
