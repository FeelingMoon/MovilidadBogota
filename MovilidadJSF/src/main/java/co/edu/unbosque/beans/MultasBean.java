package co.edu.unbosque.beans;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import org.primefaces.PrimeFaces;

import co.edu.unbosque.daos.MultaDAO;
import co.edu.unbosque.daos.MultasEstaticasDAO;
import co.edu.unbosque.daos.VehiculoDAO;
import co.edu.unbosque.persistence.MultaDTO;
import co.edu.unbosque.persistence.MultasEstaticasDTO;
import co.edu.unbosque.persistence.VehiculoDTO;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;

@SuppressWarnings("serial")
@Named("multasBean")
@ViewScoped
public class MultasBean implements Serializable {

	private VehiculoDTO vehiculo;
	private MultaDTO seleccionado;
	private String marca, color;
	private VehiculoDAO v;
	private MultaDAO m;
	private MultasEstaticasDAO ms;

	@PostConstruct
	public void init() {
		try {
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			HttpSession session = (HttpSession) externalContext.getSession(false);
			vehiculo = (VehiculoDTO) session.getAttribute("vehiculo_seleccionado");
			v = new VehiculoDAO();
			m = new MultaDAO();
			ms = new MultasEstaticasDAO();
			vehiculo = v.getOneByPlaca(vehiculo.getPlaca());
		} catch (Exception e) {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
			} catch (IOException ae) {
				ae.printStackTrace();
			}
		}
	}

	public List<MultasEstaticasDTO> multasEstaticas() {
		return ms.getAllMultasEstaticas();
	}

	public void add(MultasEstaticasDTO multa) {
		try {
			String tmp = m.add(vehiculo.getPlaca(), multa.getCodigo(), multa.getDescripcion(), multa.getMulta(),
					LocalDate.now());
			vehiculo = v.getOneByPlaca(vehiculo.getPlaca());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Logro", tmp));
			PrimeFaces.current().ajax().update("mult:messages", "mult:dt-multas-v");
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ocurrio algo al seleccionar uno"));
			PrimeFaces.current().ajax().update("mult:messages");
		}
	}

	public void pagar() {
		try {
			String tmp = m.update(seleccionado.getId(), !seleccionado.isPagado());
			vehiculo = v.getOneByPlaca(vehiculo.getPlaca());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Logro", tmp));
			PrimeFaces.current().ajax().update("mult:messages", "mult:dt-multas-v");
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"No se pudo realizar el cambio exitosamente"));
			PrimeFaces.current().ajax().update("mult:messages");
		}
	}

	public void update() {
		if (marca != null && !marca.equals("") && color != null && !color.equals("")) {
			String tmp = v.update(vehiculo.getPlaca(), color, marca);
			vehiculo = v.getOneByPlaca(vehiculo.getPlaca());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Logro", tmp));
			PrimeFaces.current().ajax().update("mult:messages", "mult:dt-multa");
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Colocar todos los datos correctamente."));
			PrimeFaces.current().ajax().update("mult:messages");
		}
	}

	public void delete() {
		if (seleccionado != null) {
			String tmp = m.delete(seleccionado.getId());
			vehiculo = v.getOneByPlaca(vehiculo.getPlaca());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Logro", tmp));
			PrimeFaces.current().ajax().update("mult:messages", "mult:dt-multas-v");
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Multa no seleccionada."));
			PrimeFaces.current().ajax().update("mult:messages");
		}
	}

	public void guardarSelec(MultaDTO seleccionado) {
		this.seleccionado = seleccionado;
	}

	public VehiculoDTO getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(VehiculoDTO vehiculo) {
		this.vehiculo = vehiculo;
	}

	public MultaDTO getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(MultaDTO seleccionado) {
		this.seleccionado = seleccionado;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public VehiculoDAO getV() {
		return v;
	}

	public void setV(VehiculoDAO v) {
		this.v = v;
	}

	public MultaDAO getM() {
		return m;
	}

	public void setM(MultaDAO m) {
		this.m = m;
	}

	public MultasEstaticasDAO getMs() {
		return ms;
	}

	public void setMs(MultasEstaticasDAO ms) {
		this.ms = ms;
	}

}
