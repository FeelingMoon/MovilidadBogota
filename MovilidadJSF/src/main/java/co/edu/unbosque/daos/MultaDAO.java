package co.edu.unbosque.daos;

import java.time.LocalDate;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class MultaDAO {

	private RestTemplate restTemplate;

	public MultaDAO() {
		restTemplate = new RestTemplate();
	}

	public String add(String placa, String codigo, String descripcion, String valor, LocalDate fecha) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/Vehiculo/AddMulta");
		builder.queryParam("placa", placa);
		builder.queryParam("codigo", codigo);
		builder.queryParam("descripcion", descripcion);
		builder.queryParam("valor", valor);
		builder.queryParam("fecha", fecha);
		String url = builder.toUriString();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, null, String.class);
		return response.getBody();
	}

	public String delete(Integer id) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/Multa/Delete");
		builder.queryParam("id", id);
		String url = builder.toUriString();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
		return response.getBody();
	}

	public String update(Integer id, boolean pagado) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/Multa/Update");
		builder.queryParam("id", id);
		builder.queryParam("pagado", pagado);
		String url = builder.toUriString();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, null, String.class);
		return response.getBody();
	}
}
