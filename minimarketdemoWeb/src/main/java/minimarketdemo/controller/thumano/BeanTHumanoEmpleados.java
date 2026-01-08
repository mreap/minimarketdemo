package minimarketdemo.controller.thumano;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import minimarketdemo.controller.JSFUtil;
import minimarketdemo.model.core.entities.SegUsuario;
import minimarketdemo.model.core.entities.ThmCargo;
import minimarketdemo.model.core.entities.ThmEmpleado;
import minimarketdemo.model.core.entities.VwThmConsultaRol;
import minimarketdemo.model.seguridades.managers.ManagerSeguridades;
import minimarketdemo.model.thumano.managers.ManagerTHumano;

@Named
@SessionScoped
public class BeanTHumanoEmpleados implements Serializable {
	private static final long serialVersionUID = 1L;
	@EJB
	private ManagerTHumano mTHumano;
	@EJB
	private ManagerSeguridades mSeguridades;
	private List<SegUsuario> listaUsuarios;
	private List<ThmCargo> listaCargos;
	private List<ThmEmpleado> listaEmpleados;
	private ThmEmpleado nuevoEmpleado;
	private int idSegUsuario;
	private int idThmCargo;
	private List<VwThmConsultaRol> listaConsulta;
	
	@PostConstruct
	public void inicializar() {
		nuevoEmpleado=new ThmEmpleado();
	}

	public BeanTHumanoEmpleados() {
		
	}
	
	public String actionCargarMenuEmpleados() {
		listaUsuarios=mSeguridades.findAllUsuarios();
		listaCargos=mTHumano.findAllThmCargo();
		listaEmpleados=mTHumano.findAllThmEmpleado();
		return "empleados?faces-redirect=true";
	}
	
	public String actionCargarMenuConsulta() {
		listaConsulta=mTHumano.findVwThmConsultaRol();
		return "consulta?faces-redirect=true";
	}
	
	public void actionListenerInsertarEmpleado() {
		try {
			mTHumano.insertarThmEmpleado(nuevoEmpleado, idThmCargo, idSegUsuario);
			listaEmpleados=mTHumano.findAllThmEmpleado();
			JSFUtil.crearMensajeINFO("Empleado registrado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	public List<SegUsuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<SegUsuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public List<ThmCargo> getListaCargos() {
		return listaCargos;
	}

	public void setListaCargos(List<ThmCargo> listaCargos) {
		this.listaCargos = listaCargos;
	}

	public List<ThmEmpleado> getListaEmpleados() {
		return listaEmpleados;
	}

	public void setListaEmpleados(List<ThmEmpleado> listaEmpleados) {
		this.listaEmpleados = listaEmpleados;
	}

	public ThmEmpleado getNuevoEmpleado() {
		return nuevoEmpleado;
	}

	public void setNuevoEmpleado(ThmEmpleado nuevoEmpleado) {
		this.nuevoEmpleado = nuevoEmpleado;
	}

	public int getIdSegUsuario() {
		return idSegUsuario;
	}

	public void setIdSegUsuario(int idSegUsuario) {
		this.idSegUsuario = idSegUsuario;
	}

	public int getIdThmCargo() {
		return idThmCargo;
	}

	public void setIdThmCargo(int idThmCargo) {
		this.idThmCargo = idThmCargo;
	}

	public List<VwThmConsultaRol> getListaConsulta() {
		return listaConsulta;
	}

	public void setListaConsulta(List<VwThmConsultaRol> listaConsulta) {
		this.listaConsulta = listaConsulta;
	}

}
