package minimarketdemo.controller.proyectos;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import minimarketdemo.controller.JSFUtil;
import minimarketdemo.controller.seguridades.BeanSegLogin;
import minimarketdemo.model.core.entities.PryProyecto;
import minimarketdemo.model.core.entities.PryTarea;
import minimarketdemo.model.core.entities.SegUsuario;
import minimarketdemo.model.proyectos.managers.ManagerProyectos;
import minimarketdemo.model.seguridades.managers.ManagerSeguridades;

@Named
@SessionScoped
public class BeanPryLider implements Serializable {
	@EJB
	private ManagerProyectos mProyectos;
	@EJB
	private ManagerSeguridades mSeguridades;
	private PryProyecto nuevoProyecto;
	private List<PryProyecto> listaProyectos;
	private List<PryTarea> listaTareas;
	private PryProyecto proyectoSeleccionado;
	private PryTarea nuevaTarea;
	private List<SegUsuario> listaUsuarios;
	private int idSegUsuarioSeleccionado;
	@Inject
	private BeanSegLogin beanSeagLogin;

	public BeanPryLider() {
	}
	@PostConstruct
	public void inicializar() {
		listaProyectos=mProyectos.findAllProyectos();
		nuevoProyecto=mProyectos.inicializarProyecto();
	}
	public void actionListenerInsertarProyecto() {
		try {
			mProyectos.insertarProyecto(beanSeagLogin.getLoginDTO(), nuevoProyecto);
			JSFUtil.crearMensajeINFO("Proyecto creado");
			nuevoProyecto=mProyectos.inicializarProyecto();
			listaProyectos=mProyectos.findAllProyectos();
		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.crearMensajeERROR(e.getMessage());
		}
	}
	public String actionSeleccionarProyecto(PryProyecto proyecto) {
		listaUsuarios=mSeguridades.findAllUsuarios();
		proyectoSeleccionado=proyecto;
		listaTareas=mProyectos.findTareasByProyecto(proyectoSeleccionado.getIdPryProyecto());
		nuevaTarea=mProyectos.inicializarTarea(proyectoSeleccionado);
		return "tareas?faces-redirect=true";
	}
	public void actionListenerInsertarTarea() {
		try {
			mProyectos.insertarTarea(nuevaTarea,idSegUsuarioSeleccionado);
			JSFUtil.crearMensajeINFO("Tarea creada");
			nuevaTarea=mProyectos.inicializarTarea(proyectoSeleccionado);
			listaTareas=mProyectos.findTareasByProyecto(proyectoSeleccionado.getIdPryProyecto());
		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.crearMensajeERROR(e.getMessage());
		}
	}
	public PryProyecto getNuevoProyecto() {
		return nuevoProyecto;
	}
	public void setNuevoProyecto(PryProyecto nuevoProyecto) {
		this.nuevoProyecto = nuevoProyecto;
	}
	public List<PryProyecto> getListaProyectos() {
		return listaProyectos;
	}
	public void setListaProyectos(List<PryProyecto> listaProyectos) {
		this.listaProyectos = listaProyectos;
	}
	public PryProyecto getProyectoSeleccionado() {
		return proyectoSeleccionado;
	}
	public void setProyectoSeleccionado(PryProyecto proyectoSeleccionado) {
		this.proyectoSeleccionado = proyectoSeleccionado;
	}
	public PryTarea getNuevaTarea() {
		return nuevaTarea;
	}
	public void setNuevaTarea(PryTarea nuevaTarea) {
		this.nuevaTarea = nuevaTarea;
	}
	public List<SegUsuario> getListaUsuarios() {
		return listaUsuarios;
	}
	public void setListaUsuarios(List<SegUsuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}
	public int getIdSegUsuarioSeleccionado() {
		return idSegUsuarioSeleccionado;
	}
	public void setIdSegUsuarioSeleccionado(int idSegUsuarioSeleccionado) {
		this.idSegUsuarioSeleccionado = idSegUsuarioSeleccionado;
	}
	public List<PryTarea> getListaTareas() {
		return listaTareas;
	}
	public void setListaTareas(List<PryTarea> listaTareas) {
		this.listaTareas = listaTareas;
	}


}
