package co.edu.unbosque.MovilidadAPI.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class MultasEstaticas {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(unique = true)
	private String codigo;

	@Column(columnDefinition = "TEXT")
	private String descripcion;

	private String multa;

	private boolean inmovilizacion;

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

}
