package minimarketdemo.api.rest.thumano;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import minimarketdemo.model.core.entities.ThmCargo;
import minimarketdemo.model.thumano.dtos.DTOThmCargo;
import minimarketdemo.model.thumano.managers.ManagerTHumano;

@RequestScoped
@Path("thumano")
@Produces("application/json")
@Consumes("application/json")
public class ServiceRESTTHumano {
	@EJB
	private ManagerTHumano mTHumano;
	
	@GET
	@Path(value = "cargos")
	public List<DTOThmCargo> findAllDTOThmCargo(){
		return mTHumano.findAllDTOThmCargo();
	}
	
	@GET
	@Path(value = "cargos/{idThmCargo}")
	public DTOThmCargo findDTOThmCargoById(@PathParam("idThmCargo") int idThmCargo){
		try {
			System.out.println("GET /cargos/"+idThmCargo);
			return mTHumano.findDTOThmCargoById(idThmCargo);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@POST
	@Path(value = "cargos")
	public String insertarCargo(DTOThmCargo cargo) {
		ThmCargo nuevoCargo=new ThmCargo();
		nuevoCargo.setNombreCargo(cargo.getNombreCargo());
		nuevoCargo.setRemuneracionMensual(new BigDecimal(cargo.getRemuneracionMensual()));
		try {
			mTHumano.insertarCargo(nuevoCargo);
			return "{\"Resultado\":\"Cargo insertado.\"}"; 
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"Error\":\""+e.getMessage()+"\"}"; 
		}
		
	}	
	
	@POST
	@Path(value = "cargos/pasante")
	public Response insertarCargoPasante() {
		try {
			mTHumano.insertarCargoPasante();
			return Response.status(Response.Status.OK) //codigo HTTP 200
					.entity("Cargo pasante insertado.")
					.build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST) //codigo HTTP 400
					.entity("Error insertar cargo pasante : " + e.getMessage())
					.build();
		}
	}
	
	@PUT
	@Path(value = "cargos")
	public String actualizarCargo(DTOThmCargo cargo) {
		ThmCargo edicionCargo=new ThmCargo();
		edicionCargo.setIdThmCargo(cargo.getIdThmCargo());
		edicionCargo.setNombreCargo(cargo.getNombreCargo());
		edicionCargo.setRemuneracionMensual(new BigDecimal(cargo.getRemuneracionMensual()));
		try {
			mTHumano.actualizarCargo(edicionCargo);
			return "{\"Resultado\":\"Cargo actualizado.\"}";
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"Error\":\""+e.getMessage()+"\"}";
		}
	}
	
	@DELETE
	@Path(value = "cargos/{idThmCargo}")
	public String eliminarCargo(@PathParam("idThmCargo") int idThmCargo) {
		try {
			mTHumano.eliminarCargo(idThmCargo);
			return "{\"Resultado\":\"Cargo eliminado.\"}";
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"Error\":\""+e.getMessage()+"\"}";
		}
	}

}
