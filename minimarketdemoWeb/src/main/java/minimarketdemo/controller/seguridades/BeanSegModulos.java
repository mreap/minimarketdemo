package minimarketdemo.controller.seguridades;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import minimarketdemo.controller.JSFUtil;
import minimarketdemo.model.core.entities.SegModulo;
import minimarketdemo.model.core.entities.SegPerfil;
import minimarketdemo.model.seguridades.managers.ManagerSeguridades;

@Named
@SessionScoped
public class BeanSegModulos implements Serializable {
	private static final long serialVersionUID = 1L;
	@EJB
	private ManagerSeguridades mSeguridades;
	private List<SegModulo> listaModulos;
	private List<SegPerfil> listaPerfiles;
	private SegModulo nuevoModulo;
	private SegModulo edicionModulo;
	private SegModulo moduloSeleccionado;
	private SegPerfil nuevoPerfil;
	private SegPerfil edicionPerfil;

	public BeanSegModulos() {
		
	}
	@PostConstruct
	public void inicializacion() {
		System.out.println("BeanSegModulos inicializado...");
		nuevoModulo=new SegModulo();
		nuevoPerfil=new SegPerfil();
	}
	
	public String actionCargarMenuModulos() {
		listaModulos=mSeguridades.findAllModulos();
		return "modulos?faces-redirect=true";
	}
	
	public void actionListenerInsertarModulo() {
		try {
			mSeguridades.insertarModulo(nuevoModulo);
			listaModulos=mSeguridades.findAllModulos();
			JSFUtil.crearMensajeINFO("Módulo creado.");
			nuevoModulo=new SegModulo();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void actionListenerCargarModulo(SegModulo modulo) {
		edicionModulo=modulo;
	}
	
	public void actionListenerGuardarEdicionModulo() {
		try {
			mSeguridades.actualizarModulo(edicionModulo);
			JSFUtil.crearMensajeINFO("Módulo editado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	public void actionListenerEliminarModulo(int idSegModulo) {
		try {
			mSeguridades.eliminarModulo(idSegModulo);
			listaModulos=mSeguridades.findAllModulos();
			JSFUtil.crearMensajeINFO("Módulo eliminado");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public String actionCargarPerfiles(SegModulo modulo) {
		listaPerfiles=mSeguridades.findPerfilesByModulo(modulo.getIdSegModulo());
		moduloSeleccionado=modulo;
		nuevoPerfil=new SegPerfil();
		return "perfiles?faces-redirect=true";
	}
	
	public void actionListenerInsertarPerfil() {
		try {
			nuevoPerfil.setSegModulo(moduloSeleccionado);
			mSeguridades.insertarPerfil(nuevoPerfil);
			listaPerfiles=mSeguridades.findPerfilesByModulo(moduloSeleccionado.getIdSegModulo());
			JSFUtil.crearMensajeINFO("Perfil creado.");
			nuevoPerfil=new SegPerfil();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void actionListenerCargarPerfil(SegPerfil perfil) {
		edicionPerfil=perfil;
	}
	
	public void actionListenerEliminarPerfil(int idSegPerfil) {
		try {
			mSeguridades.eliminarPerfil(idSegPerfil);
			listaPerfiles=mSeguridades.findPerfilesByModulo(moduloSeleccionado.getIdSegModulo());
			JSFUtil.crearMensajeINFO("Perfil eliminado");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void actionListenerGuardarEdicionPerfil() {
		try {
			mSeguridades.actualizarPerfil(edicionPerfil);
			JSFUtil.crearMensajeINFO("Perfil editado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
		
	}

	public List<SegModulo> getListaModulos() {
		return listaModulos;
	}

	public void setListaModulos(List<SegModulo> listaModulos) {
		this.listaModulos = listaModulos;
	}
	
	public SegModulo getNuevoModulo() {
		return nuevoModulo;
	}
	public void setNuevoModulo(SegModulo nuevoModulo) {
		this.nuevoModulo = nuevoModulo;
	}
	public SegModulo getEdicionModulo() {
		return edicionModulo;
	}
	public void setEdicionModulo(SegModulo edicionModulo) {
		this.edicionModulo = edicionModulo;
	}
	public List<SegPerfil> getListaPerfiles() {
		return listaPerfiles;
	}
	public void setListaPerfiles(List<SegPerfil> listaPerfiles) {
		this.listaPerfiles = listaPerfiles;
	}
	public SegModulo getModuloSeleccionado() {
		return moduloSeleccionado;
	}
	public void setModuloSeleccionado(SegModulo moduloSeleccionado) {
		this.moduloSeleccionado = moduloSeleccionado;
	}
	public SegPerfil getNuevoPerfil() {
		return nuevoPerfil;
	}
	public void setNuevoPerfil(SegPerfil nuevoPerfil) {
		this.nuevoPerfil = nuevoPerfil;
	}
	public SegPerfil getEdicionPerfil() {
		return edicionPerfil;
	}
	public void setEdicionPerfil(SegPerfil edicionPerfil) {
		this.edicionPerfil = edicionPerfil;
	}
	

}
