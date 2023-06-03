package co.edu.unbosque.MovilidadAPI.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.MovilidadAPI.persistence.MultasEstaticas;

public interface MultasEstaticasRepository extends CrudRepository<MultasEstaticas, Integer> {
	public List<MultasEstaticas> findAll();

	public Optional<MultasEstaticas> findByCodigo(String codigo);
}
