package co.edu.unbosque.persistence;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MultaDTO {

	private Integer id;
	private String codigo;
	private String descripcion;
	private String valor;
	private LocalDate fechaIngreso;
	private boolean pagado;
	private VehiculoDTO vehiculo;

	@JsonCreator
	public MultaDTO(@JsonProperty("id") Integer id, @JsonProperty("codigo") String codigo,
			@JsonProperty("descripcion") String descripcion, @JsonProperty("valor") String valor,
			@JsonProperty("fechaIngreso") String fechaIngreso, @JsonProperty("pagado") boolean pagado,
			@JsonProperty("vehiculo") VehiculoDTO vehiculo) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.valor = valor;
		String[] tmp = fechaIngreso.split("-");
		this.fechaIngreso = LocalDate.of(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), Integer.parseInt(tmp[2]));
		this.pagado = pagado;
		this.vehiculo = vehiculo;
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

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public LocalDate getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(LocalDate fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public boolean isPagado() {
		return pagado;
	}

	public void setPagado(boolean pagado) {
		this.pagado = pagado;
	}

	public VehiculoDTO getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(VehiculoDTO vehiculo) {
		this.vehiculo = vehiculo;
	}

	@Override
	public String toString() {
		return "MultaDTO [id=" + id + ", codigo=" + codigo + ", descripcion=" + descripcion + ", valor=" + valor
				+ ", fechaIngreso=" + fechaIngreso + ", pagado=" + pagado + ", vehiculo=" + vehiculo + "]";
	}

}
