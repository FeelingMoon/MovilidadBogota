package co.edu.unbosque.daos;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import co.edu.unbosque.persistence.VehiculoDTO;

public class VehiculoDAO {

	private RestTemplate restTemplate;

	public VehiculoDAO() {
		restTemplate = new RestTemplate();
	}

	public String add(Integer documento, String placa, String color) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/Persona/AddVehicle");
		builder.queryParam("documento", documento);
		builder.queryParam("placa", placa);
		builder.queryParam("color", color);
		String url = builder.toUriString();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, null, String.class);
		return response.getBody();
	}

	public String transfer(Integer documento, Integer docOrigen, Integer docDestino) {
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl("http://localhost:8080/Persona/TransferVehicle");
		builder.queryParam("documento", documento);
		builder.queryParam("docOrigen", docOrigen);
		builder.queryParam("docDestino", docDestino);
		String url = builder.toUriString();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, null, String.class);
		return response.getBody();
	}

	public String update(String placa, String color) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/Vehiculo/Update");
		builder.queryParam("placa", placa);
		builder.queryParam("color", color);
		String url = builder.toUriString();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, null, String.class);
		return response.getBody();
	}

	public List<VehiculoDTO> getAll() {
		String url = "http://localhost:8080/Vehiculo/GetAll";
		ResponseEntity<VehiculoDTO[]> response = restTemplate.getForEntity(url, VehiculoDTO[].class);
		if (response.getStatusCode().equals(HttpStatus.FOUND)) {
			return Arrays.asList(response.getBody());
		}
		return null;
	}

	public VehiculoDTO getOneByPlaca(String placa) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/Vehiculo/GetOneByPlaca");
		builder.queryParam("placa", placa);
		String url = builder.toUriString();
		ResponseEntity<VehiculoDTO> response = restTemplate.getForEntity(url, VehiculoDTO.class);
		if (response.getStatusCode().equals(HttpStatus.FOUND)) {
			return response.getBody();
		}
		return null;
	}

	public String delete(String placa) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/Vehiculo/Delete");
		builder.queryParam("placa", placa);
		String url = builder.toUriString();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
		return response.getBody();
	}
}
