package co.edu.unbosque.daos;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import co.edu.unbosque.persistence.PersonaDTO;

public class PersonaDAO {

	private RestTemplate restTemplate;

	public PersonaDAO() {
		restTemplate = new RestTemplate();
	}

	public String delete(Integer documento) {
		try {
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8081/Persona/Delete");
			builder.queryParam("documento", documento);
			String url = builder.toUriString();
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
			return response.getBody();
		} catch (Exception e) {
			return "NOT FOUND (CODE 404)";
		}
	}

	public String update(Integer documento, String nombre) {
		try {
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8081/Persona/Update");
			builder.queryParam("documento", documento);
			builder.queryParam("nombre", nombre);
			String url = builder.toUriString();
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, null, String.class);
			return response.getBody();
		} catch (Exception e) {
			return "NOT FOUND (CODE 404)";
		}
	}

	public List<PersonaDTO> getAll() {
		try {
			String url = "http://localhost:8081/Persona/GetAll";
			ResponseEntity<PersonaDTO[]> response = restTemplate.getForEntity(url, PersonaDTO[].class);
			if (response.getStatusCode().equals(HttpStatus.FOUND)) {
				return Arrays.asList(response.getBody());
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	public PersonaDTO getOneByDocument(Integer documento) {
		try {
			UriComponentsBuilder builder = UriComponentsBuilder
					.fromHttpUrl("http://localhost:8081/Persona/GetOneByDocument");
			builder.queryParam("documento", documento);
			String url = builder.toUriString();
			ResponseEntity<PersonaDTO> response = restTemplate.getForEntity(url, PersonaDTO.class);
			if (response.getStatusCode().equals(HttpStatus.FOUND)) {
				return response.getBody();
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	public String add(String nombre, Integer documento, LocalDate fechaExpedicion, LocalDate fechaNacimiento) {
		try {
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8081/Persona/Add");
			builder.queryParam("nombre", nombre);
			builder.queryParam("documento", documento);
			builder.queryParam("fechaExpedicion", fechaExpedicion);
			builder.queryParam("fechaNacimiento", fechaNacimiento);
			String url = builder.toUriString();
			ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);
			return response.getBody();
		} catch (Exception e) {
			return "CONFLICT (CODE 409)";
		}
	}
}
