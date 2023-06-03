package co.edu.unbosque.persistence;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class VehiculoDTO {

	private Integer id;
	private String placa;
	private String color;
	private PersonaDTO persona;
	private List<MultaDTO> multas;

	@JsonCreator
	public VehiculoDTO(@JsonProperty("id") Integer id, @JsonProperty("placa") String placa,
			@JsonProperty("color") String color, @JsonProperty("persona") PersonaDTO persona,
			@JsonProperty("multas") List<MultaDTO> multas) {
		super();
		this.id = id;
		this.placa = placa;
		this.color = color;
		this.persona = persona;
		this.multas = multas;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public PersonaDTO getPersona() {
		return persona;
	}

	public void setPersona(PersonaDTO persona) {
		this.persona = persona;
	}

	public List<MultaDTO> getMultas() {
		return multas;
	}

	public void setMultas(List<MultaDTO> multas) {
		this.multas = multas;
	}

	@Override
	public String toString() {
		return "VehiculoDTO [id=" + id + ", placa=" + placa + ", color=" + color + ", persona=" + persona + ", multas="
				+ Arrays.toString(multas.toArray()) + "]";
	}

}
