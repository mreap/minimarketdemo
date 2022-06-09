package minimarketdemo.controller.thumano;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import minimarketdemo.model.core.entities.ThmPeriodoRol;
import minimarketdemo.model.thumano.managers.ManagerTHumano;

@Named
@SessionScoped
public class BeanTHumanoPeriodos implements Serializable {
	private static final long serialVersionUID = 1L;
	@EJB
	private ManagerTHumano mTHumano;
	private List<ThmPeriodoRol> listaPeriodos;
	@PostConstruct
	public void inicializar() {
	}

	public BeanTHumanoPeriodos() {
		
	}
	
	public String actionCargarMenuPeriodo() {
		listaPeriodos=mTHumano.findAllThmPeriodoRol();
		return "periodos?faces-redirect=true";
	}

	public List<ThmPeriodoRol> getListaPeriodos() {
		return listaPeriodos;
	}

	public void setListaPeriodos(List<ThmPeriodoRol> listaPeriodos) {
		this.listaPeriodos = listaPeriodos;
	}
	
}
