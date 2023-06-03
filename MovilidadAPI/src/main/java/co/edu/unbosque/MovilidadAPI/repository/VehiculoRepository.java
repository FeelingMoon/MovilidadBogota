package co.edu.unbosque.MovilidadAPI.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.MovilidadAPI.persistence.Vehiculo;

public interface VehiculoRepository extends CrudRepository<Vehiculo, Integer> {
	public List<Vehiculo> findAll();

	public Optional<Vehiculo> findByPlaca(String placa);
}
