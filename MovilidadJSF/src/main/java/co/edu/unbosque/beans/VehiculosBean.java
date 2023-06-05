package co.edu.unbosque.beans;

import java.io.IOException;
import java.io.Serializable;

import org.primefaces.PrimeFaces;

import co.edu.unbosque.daos.PersonaDAO;
import co.edu.unbosque.daos.VehiculoDAO;
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
@Named("vehiculosBean")
@ViewScoped
public class VehiculosBean implements Serializable {

	private PersonaDTO persona;
	private VehiculoDTO seleccionado;
	private String placa, marca, color, nombre;
	private Integer documento;
	private VehiculoDAO v;
	private PersonaDAO p;

	@PostConstruct
	public void init() {
		try {
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			HttpSession session = (HttpSession) externalContext.getSession(false);
			persona = (PersonaDTO) session.getAttribute("persona_seleccionada");
			v = new VehiculoDAO();
			p = new PersonaDAO();
			persona = p.getOneByDocument(persona.getDocumento());
		} catch (Exception e) {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
			} catch (IOException ae) {
				ae.printStackTrace();
			}
		}
	}

	public void add() {
		if ((placa != null && !placa.equals("") && (marca != null && !marca.equals(""))
				&& (color != null && !color.equals("")))) {
			String tmp = v.add(persona.getDocumento(), placa, color, marca);
			persona = p.getOneByDocument(persona.getDocumento());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Logro", tmp));
			PrimeFaces.current().ajax().update("vehi:messages", "vehi:dt-vehiculos-v");
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Colocar todos los datos correctamente."));
			PrimeFaces.current().ajax().update("vehi:messages");
		}
	}

	public void transfer() {
		if ((documento != null && !documento.equals(0)) && (seleccionado != null)) {
			String tmp = v.transfer(seleccionado.getPlaca(), persona.getDocumento(), documento);
			persona = p.getOneByDocument(persona.getDocumento());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Logro", tmp));
			PrimeFaces.current().ajax().update("vehi:messages", "vehi:dt-vehiculos-v");
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Colocar todos los datos correctamente."));
			PrimeFaces.current().ajax().update("vehi:messages");
		}
	}

	public void update() {
		if (nombre != null && !nombre.equals("")) {
			String tmp = p.update(persona.getDocumento(), nombre);
			persona = p.getOneByDocument(persona.getDocumento());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Logro", tmp));
			PrimeFaces.current().ajax().update("vehi:messages", "vehi:dt-persona");
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Colocar todos los datos correctamente."));
			PrimeFaces.current().ajax().update("vehi:messages");
		}
	}

	public void delete() {
		if (seleccionado != null) {
			String tmp = v.delete(seleccionado.getPlaca());
			persona = p.getOneByDocument(persona.getDocumento());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Logro", tmp));
			PrimeFaces.current().ajax().update("vehi:messages", "vehi:dt-vehiculos-v");
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Vehiculo no seleccionado."));
			PrimeFaces.current().ajax().update("vehi:messages");
		}
	}

	public String selecAndPass(VehiculoDTO seleccionado) {
		try {
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			HttpSession session = (HttpSession) ec.getSession(true);
			session.setAttribute("vehiculo_seleccionado", seleccionado);
			return "multa.xhtml?faces-redirect=true";
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al redirigir."));
			PrimeFaces.current().ajax().update("vehi:messages");
		}
		return "";
	}

	public void guardarSelec(VehiculoDTO seleccionado) {
		this.seleccionado = seleccionado;
	}

	public PersonaDTO getPersona() {
		return persona;
	}

	public void setPersona(PersonaDTO persona) {
		this.persona = persona;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
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

	public VehiculoDTO getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(VehiculoDTO seleccionado) {
		this.seleccionado = seleccionado;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public VehiculoDAO getV() {
		return v;
	}

	public void setV(VehiculoDAO v) {
		this.v = v;
	}

	public PersonaDAO getP() {
		return p;
	}

	public void setP(PersonaDAO p) {
		this.p = p;
	}

	public Integer getDocumento() {
		return documento;
	}

	public void setDocumento(Integer documento) {
		this.documento = documento;
	}

}
