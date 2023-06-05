package co.edu.unbosque.daos;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import co.edu.unbosque.persistence.MultasEstaticasDTO;

public class MultasEstaticasDAO {
	private RestTemplate restTemplate;

	public MultasEstaticasDAO() {
		restTemplate = new RestTemplate();
	}

	public List<MultasEstaticasDTO> getAllMultasEstaticas() {
		String url = "http://localhost:8081/MultasEstaticas/GetAll";
		ResponseEntity<MultasEstaticasDTO[]> response = restTemplate.getForEntity(url, MultasEstaticasDTO[].class);
		if (response.getStatusCode().equals(HttpStatus.FOUND)) {
			return Arrays.asList(response.getBody());
		}
		return null;
	}

	public MultasEstaticasDTO getMultasEstaticasByCode(String codigo) {
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl("http://localhost:8081/MultasEstaticas/GetOneByCode");
		builder.queryParam("codigo", codigo);
		String url = builder.toUriString();
		ResponseEntity<MultasEstaticasDTO> response = restTemplate.getForEntity(url, MultasEstaticasDTO.class);
		if (response.getStatusCode().equals(HttpStatus.FOUND)) {
			return response.getBody();
		}
		return null;
	}
}
