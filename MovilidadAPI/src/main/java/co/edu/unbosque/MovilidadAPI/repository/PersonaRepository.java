package co.edu.unbosque.MovilidadAPI.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.MovilidadAPI.persistence.Persona;

public interface PersonaRepository extends CrudRepository<Persona, Integer> {
	public List<Persona> findAll();
}
