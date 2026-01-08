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
import minimarketdemo.model.core.entities.PryTarea;
import minimarketdemo.model.proyectos.managers.ManagerProyectos;

@Named
@SessionScoped
public class BeanPryAnalista implements Serializable {
	@EJB
	private ManagerProyectos mProyectos;
	private List<PryTarea> listaTareas;
	@Inject
	private BeanSegLogin beanSegLogin;
	public BeanPryAnalista() {
	}

	@PostConstruct
	public void inicializar() {
		listaTareas=mProyectos.findTareasByUsuario(beanSegLogin.getIdSegUsuario());
	}
	
	public void actionListenerActualizarAvance(PryTarea tarea) {
		try {
			mProyectos.actualizarAvance(tarea);
			JSFUtil.crearMensajeINFO("Avance actualizado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	public List<PryTarea> getListaTareas() {
		return listaTareas;
	}

	public void setListaTareas(List<PryTarea> listaTareas) {
		this.listaTareas = listaTareas;
	}

	public BeanSegLogin getBeanSegLogin() {
		return beanSegLogin;
	}

	public void setBeanSegLogin(BeanSegLogin beanSegLogin) {
		this.beanSegLogin = beanSegLogin;
	}
	
	
}
