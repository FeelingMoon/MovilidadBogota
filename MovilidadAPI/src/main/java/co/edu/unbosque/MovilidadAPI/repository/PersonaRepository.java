package co.edu.unbosque.MovilidadAPI.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.MovilidadAPI.persistence.Persona;

public interface PersonaRepository extends CrudRepository<Persona, Integer> {
	public List<Persona> findAll();

	public Optional<Persona> findByDocumento(Integer documento);
}
