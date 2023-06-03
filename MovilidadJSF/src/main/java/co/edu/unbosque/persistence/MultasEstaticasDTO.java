package co.edu.unbosque.persistence;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MultasEstaticasDTO {

	private Integer id;
	private String codigo;
	private String descripcion;
	private String multa;
	private boolean inmovilizacion;

	@JsonCreator // anotación para indicar a Jackson que use este constructor
	public MultasEstaticasDTO(@JsonProperty("id") int id, @JsonProperty("codigo") String codigo,
			@JsonProperty("descripcion") String descripcion, @JsonProperty("multa") String multa,
			@JsonProperty("inmovilizacion") boolean inmovilizacion) {
		this.id = id;
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.multa = multa;
		this.inmovilizacion = inmovilizacion;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getMulta() {
		return multa;
	}

	public void setMulta(String multa) {
		this.multa = multa;
	}

	public boolean isInmovilizacion() {
		return inmovilizacion;
	}

	public void setInmovilizacion(boolean inmovilizacion) {
		this.inmovilizacion = inmovilizacion;
	}

	@Override
	public String toString() {
		return "MultasEstaticasDTO [id=" + id + ", codigo=" + codigo + ", descripcion=" + descripcion + ", multa="
				+ multa + ", inmovilizacion=" + inmovilizacion + "]";
	}

}
