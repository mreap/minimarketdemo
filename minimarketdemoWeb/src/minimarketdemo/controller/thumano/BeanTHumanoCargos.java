package minimarketdemo.controller.thumano;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import minimarketdemo.controller.JSFUtil;
import minimarketdemo.model.core.entities.ThmCargo;
import minimarketdemo.model.thumano.managers.ManagerTHumano;

@Named
@SessionScoped
public class BeanTHumanoCargos implements Serializable {
	private static final long serialVersionUID = 1L;
	@EJB
	private ManagerTHumano mTHumano;
	private List<ThmCargo> listaCargos;
	@PostConstruct
	public void inicializar() {
	}

	public BeanTHumanoCargos() {
		
	}
	
	public String actionCargarMenuCargos() {
		listaCargos=mTHumano.findAllThmCargo();
		return "cargos?faces-redirect=true";
	}
	
	public void actionListenerConsultarCargos() {
		listaCargos=mTHumano.findAllThmCargo();
		JSFUtil.crearMensajeINFO("Cargos consultados: "+listaCargos.size());
	}

	public List<ThmCargo> getListaCargos() {
		return listaCargos;
	}

	public void setListaCargos(List<ThmCargo> listaCargos) {
		this.listaCargos = listaCargos;
	}
	
	

	

}
