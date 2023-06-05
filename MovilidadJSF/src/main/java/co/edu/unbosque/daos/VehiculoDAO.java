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

	public String add(Integer documento, String placa, String color, String marca) {
		try {
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8081/Persona/AddVehicle");
			builder.queryParam("documento", documento);
			builder.queryParam("placa", placa);
			builder.queryParam("color", color);
			builder.queryParam("marca", marca);
			String url = builder.toUriString();
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, null, String.class);
			return response.getBody();
		} catch (Exception e) {
			return "CONFLICT (CODE 409)";
		}
	}

	public String transfer(String placa, Integer docOrigen, Integer docDestino) {
		try {
			UriComponentsBuilder builder = UriComponentsBuilder
					.fromHttpUrl("http://localhost:8081/Persona/TransferVehicle");
			builder.queryParam("placa", placa);
			builder.queryParam("docOrigen", docOrigen);
			builder.queryParam("docDestino", docDestino);
			String url = builder.toUriString();
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, null, String.class);
			return response.getBody();
		} catch (Exception e) {
			return "NOT FOUND (CODE 404)";
		}
	}

	public String update(String placa, String color, String marca) {
		try {
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8081/Vehiculo/Update");
			builder.queryParam("placa", placa);
			builder.queryParam("color", color);
			builder.queryParam("marca", marca);
			String url = builder.toUriString();
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, null, String.class);
			return response.getBody();
		} catch (Exception e) {
			return "NOT FOUND (CODE 404)";
		}
	}

	public List<VehiculoDTO> getAll() {
		try {
			String url = "http://localhost:8081/Vehiculo/GetAll";
			ResponseEntity<VehiculoDTO[]> response = restTemplate.getForEntity(url, VehiculoDTO[].class);
			if (response.getStatusCode().equals(HttpStatus.FOUND)) {
				return Arrays.asList(response.getBody());
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	public VehiculoDTO getOneByPlaca(String placa) {
		try {
			UriComponentsBuilder builder = UriComponentsBuilder
					.fromHttpUrl("http://localhost:8081/Vehiculo/GetOneByPlaca");
			builder.queryParam("placa", placa);
			String url = builder.toUriString();
			ResponseEntity<VehiculoDTO> response = restTemplate.getForEntity(url, VehiculoDTO.class);
			if (response.getStatusCode().equals(HttpStatus.FOUND)) {
				return response.getBody();
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	public String delete(String placa) {
		try {
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8081/Vehiculo/Delete");
			builder.queryParam("placa", placa);
			String url = builder.toUriString();
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
			return response.getBody();
		} catch (Exception e) {
			return "NOT FOUND (CODE 404)";
		}
	}
}
