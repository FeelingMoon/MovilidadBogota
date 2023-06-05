package co.edu.unbosque.persistence;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PersonaDTO {

	private Integer id;
	private String nombre;
	private Integer documento;
	private LocalDate fechaExpedicion;
	private LocalDate fechaNacimiento;
	private List<VehiculoDTO> vehiculos;
	private String listaV;

	@JsonCreator
	public PersonaDTO(@JsonProperty("id") Integer id, @JsonProperty("nombre") String nombre,
			@JsonProperty("documento") Integer documento, @JsonProperty("fechaExpedicion") String fechaExpedicion,
			@JsonProperty("vehiculo") List<VehiculoDTO> vehiculos,
			@JsonProperty("fechaNacimiento") String fechaNacimiento) {
		super();
		this.id = id;
		this.nombre = nombre.replace("%20", " ");
		this.documento = documento;
		String[] tmp = fechaExpedicion.split("-");
		this.fechaExpedicion = LocalDate.of(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]),
				Integer.parseInt(tmp[2]));
		tmp = fechaNacimiento.split("-");
		this.fechaNacimiento = LocalDate.of(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]),
				Integer.parseInt(tmp[2]));
		this.vehiculos = vehiculos;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getDocumento() {
		return documento;
	}

	public void setDocumento(Integer documento) {
		this.documento = documento;
	}

	public LocalDate getFechaExpedicion() {
		return fechaExpedicion;
	}

	public void setFechaExpedicion(LocalDate fechaExpedicion) {
		this.fechaExpedicion = fechaExpedicion;
	}

	public List<VehiculoDTO> getVehiculos() {
		return vehiculos;
	}

	public void setVehiculos(List<VehiculoDTO> vehiculos) {
		this.vehiculos = vehiculos;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getListaV() {
		listaV = "";
		if (vehiculos.isEmpty()) {
			listaV = "No hay vehiculos relacionados";
		} else {
			for (VehiculoDTO vh : vehiculos) {
				listaV += vh.getPlaca() + " - " + vh.getMarca() + " - " + vh.getColor() + ";\n";
			}
			listaV = listaV.substring(0, listaV.length() - 2);
		}
		return listaV;
	}

	public void setListaV(String listaV) {
		this.listaV = listaV;
	}

	@Override
	public String toString() {
		return "PersonaDTO [id=" + id + ", nombre=" + nombre + ", documento=" + documento + ", fechaExpedicion="
				+ fechaExpedicion + ", vehiculos=" + Arrays.toString(vehiculos.toArray()) + "]";
	}

}
