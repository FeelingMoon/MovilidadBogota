package co.edu.unbosque.MovilidadAPI.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.MovilidadAPI.persistence.Multa;

public interface MultaRepository extends CrudRepository<Multa, Integer> {
	public List<Multa> findAll();
}
