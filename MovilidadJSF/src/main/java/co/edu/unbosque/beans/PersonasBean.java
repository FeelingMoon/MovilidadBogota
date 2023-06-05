package co.edu.unbosque.beans;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.primefaces.PrimeFaces;

import co.edu.unbosque.daos.PersonaDAO;
import co.edu.unbosque.persistence.PersonaDTO;
import co.edu.unbosque.persistence.VehiculoDTO;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;

@SuppressWarnings("serial")
@Named("personasBean")
@ViewScoped
public class PersonasBean implements Serializable {

	private PersonaDAO p;
	private List<PersonaDTO> personas;
	private PersonaDTO seleccionado;
	private String nombre;
	private Integer documento;
	private Date fechaMaxima, hoy, fechaNacimiento, fechaExpedicion;

	@PostConstruct
	public void init() {
		p = new PersonaDAO();
		personas = p.getAll();
		if (personas == null) {
			personas = new ArrayList<>();
		}
		fechaMaxima = Date.from((LocalDate.now().minusYears(16).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		hoy = new Date();
	}

	public void add() {
		if ((nombre != null && !nombre.equals("") && (documento != null && !documento.equals(0))
				&& (fechaNacimiento != null) && (fechaExpedicion != null))) {
			String tmp = p.add(nombre, documento,
					fechaExpedicion.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
					fechaNacimiento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			personas = p.getAll();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Logro", tmp));
			PrimeFaces.current().ajax().update("pers:messages", "pers:dt-personas-v");
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Colocar todos los datos correctamente."));
			PrimeFaces.current().ajax().update("pers:messages");
		}
	}

	public void guardarSelec(PersonaDTO seleccionado) {
		this.seleccionado = seleccionado;
	}

	public void delete() {
		if (seleccionado != null) {
			String tmp = p.delete(seleccionado.getDocumento());
			personas = p.getAll();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Logro", tmp));
			PrimeFaces.current().ajax().update("pers:messages", "pers:dt-personas-v");
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Persona no seleccionada."));
			PrimeFaces.current().ajax().update("pers:messages");
		}
	}

	public String selecAndPass(PersonaDTO seleccionado) {
		try {
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			HttpSession session = (HttpSession) ec.getSession(true);
			session.setAttribute("persona_seleccionada", seleccionado);
			return "persona.xhtml?faces-redirect=true";
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al redirigir."));
			PrimeFaces.current().ajax().update("pers:messages");
		}
		return "";
	}

	public String selecVAndPass(VehiculoDTO seleccionado) {
		try {
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			HttpSession session = (HttpSession) ec.getSession(true);
			session.setAttribute("persona_seleccionada", getPersonaWithPlaca(seleccionado.getPlaca()));
			return "persona.xhtml?faces-redirect=true";
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al redirigir."));
			PrimeFaces.current().ajax().update("pers:messages");
		}
		return "";
	}

	public PersonaDAO getP() {
		return p;
	}

	public void setP(PersonaDAO p) {
		this.p = p;
	}

	private PersonaDTO getPersonaWithPlaca(String placa) {
		for (PersonaDTO p : personas) {
			if (p.getVehiculos() != null && !p.getVehiculos().isEmpty()) {
				for (VehiculoDTO v : p.getVehiculos()) {
					if (v.getPlaca().equals(placa)) {
						return p;
					}
				}
			}
		}
		return null;
	}

	public List<PersonaDTO> getPersonas() {
		return personas;
	}

	public List<VehiculoDTO> vehiculos() {
		List<VehiculoDTO> tmp = new ArrayList<>();
		for (PersonaDTO p : personas) {
			if (p.getVehiculos() != null && !p.getVehiculos().isEmpty()) {
				for (VehiculoDTO v : p.getVehiculos()) {
					tmp.add(v);
				}
			}
		}
		return tmp;
	}

	public void setPersonas(List<PersonaDTO> personas) {
		this.personas = personas;
	}

	public PersonaDTO getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(PersonaDTO seleccionado) {
		this.seleccionado = seleccionado;
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

	public Date getFechaMaxima() {
		return fechaMaxima;
	}

	public void setFechaMaxima(Date fechaMaxima) {
		this.fechaMaxima = fechaMaxima;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Date getFechaExpedicion() {
		return fechaExpedicion;
	}

	public void setFechaExpedicion(Date fechaExpedicion) {
		this.fechaExpedicion = fechaExpedicion;
	}

	public Date getHoy() {
		return hoy;
	}

	public void setHoy(Date hoy) {
		this.hoy = hoy;
	}

}
