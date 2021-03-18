package minimarketdemo.api.rest.thumano;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

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
	
	@POST
	@Path(value = "cargos")
	public void insertarCargo() {
		try {
			mTHumano.insertarCargo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
