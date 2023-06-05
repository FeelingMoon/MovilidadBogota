package co.edu.unbosque.daos;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import co.edu.unbosque.persistence.MultaDTO;

public class MultaDAO {

	private RestTemplate restTemplate;

	public MultaDAO() {
		restTemplate = new RestTemplate();
	}

	public String add(String placa, String codigo, String descripcion, String valor, LocalDate fecha) {
		try {
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8081/Vehiculo/AddMulta");
			builder.queryParam("placa", placa);
			builder.queryParam("codigo", codigo);
			builder.queryParam("descripcion", descripcion);
			builder.queryParam("valor", valor);
			builder.queryParam("fecha", fecha);
			String url = builder.toUriString();
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, null, String.class);
			return response.getBody();
		} catch (Exception e) {
			return "CONFLICT (CODE 409)";
		}
	}

	public String delete(Integer id) {
		try {
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8081/Multa/Delete");
			builder.queryParam("id", id);
			String url = builder.toUriString();
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
			return response.getBody();
		} catch (Exception e) {
			return "NOT FOUND (CODE 404)";
		}
	}

	public String update(Integer id, boolean pagado) {
		try {
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8081/Multa/Update");
			builder.queryParam("id", id);
			builder.queryParam("pagado", pagado);
			String url = builder.toUriString();
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, null, String.class);
			return response.getBody();
		} catch (Exception e) {
			return "NOT FOUND (CODE 404)";
		}
	}

	public List<MultaDTO> getAll() {
		try {
			String url = "http://localhost:8081/Multa/GetAll";
			ResponseEntity<MultaDTO[]> response = restTemplate.getForEntity(url, MultaDTO[].class);
			if (response.getStatusCode().equals(HttpStatus.FOUND)) {
				return Arrays.asList(response.getBody());
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}
}
