package minimarketdemo.controller.thumano;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import minimarketdemo.controller.JSFUtil;
import minimarketdemo.model.core.entities.SegUsuario;
import minimarketdemo.model.core.entities.ThmCargo;
import minimarketdemo.model.core.entities.ThmEmpleado;
import minimarketdemo.model.core.entities.ThmPeriodoRol;
import minimarketdemo.model.core.entities.ThmRolCabecera;
import minimarketdemo.model.seguridades.managers.ManagerSeguridades;
import minimarketdemo.model.thumano.managers.ManagerTHumano;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Named
@SessionScoped
public class BeanTHumanoRolPagos implements Serializable {
	private static final long serialVersionUID = 1L;
	@EJB
	private ManagerTHumano mTHumano;
	@EJB
	private ManagerSeguridades mSeguridades;
	private List<ThmPeriodoRol> listaPeriodosRol;
	private List<ThmRolCabecera> listaRolCabeceras;
	private int periodoRolSeleccionado;
	
	
	@PostConstruct
	public void inicializar() {

	}

	public BeanTHumanoRolPagos() {
		
	}
	
	public String actionCargarMenuRoles() {
		listaRolCabeceras=mTHumano.findAllThmRolCabecera();
		listaPeriodosRol=mTHumano.findAllThmPeriodoRol();
		return "roles?faces-redirect=true";
	}
	
	public void actionListenerGenerarRolPagos() {
		try {
			mTHumano.generarRolPagos(periodoRolSeleccionado);
			listaRolCabeceras=mTHumano.findAllThmRolCabecera();
			JSFUtil.crearMensajeINFO("Rol generado exitosamente.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public String actionReporte() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		/*
		 * parametros.put("p_titulo_principal",p_titulo_principal);
		 * parametros.put("p_titulo",p_titulo);
		 */
		FacesContext context = FacesContext.getCurrentInstance();
		ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
		String ruta = servletContext.getRealPath("thumano/analista/reporte.jasper");
		System.out.println(ruta);
		HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
		response.addHeader("Content-disposition", "attachment;filename=reporte.pdf");
		response.setContentType("application/pdf");
		try {
			Class.forName("org.postgresql.Driver");
			Connection connection = null;
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mmarketdemo", "postgres",
					"postgres");
			JasperPrint impresion = JasperFillManager.fillReport(ruta, parametros, connection);
			JasperExportManager.exportReportToPdfStream(impresion, response.getOutputStream());
			context.getApplication().getStateManager().saveView(context);
			System.out.println("reporte generado.");
			context.responseComplete();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
		return "";
	}

	public List<ThmPeriodoRol> getListaPeriodosRol() {
		return listaPeriodosRol;
	}

	public void setListaPeriodosRol(List<ThmPeriodoRol> listaPeriodosRol) {
		this.listaPeriodosRol = listaPeriodosRol;
	}

	public List<ThmRolCabecera> getListaRolCabeceras() {
		return listaRolCabeceras;
	}

	public void setListaRolCabeceras(List<ThmRolCabecera> listaRolCabeceras) {
		this.listaRolCabeceras = listaRolCabeceras;
	}

	public int getPeriodoRolSeleccionado() {
		return periodoRolSeleccionado;
	}

	public void setPeriodoRolSeleccionado(int periodoRolSeleccionado) {
		this.periodoRolSeleccionado = periodoRolSeleccionado;
	}
	
	

}
