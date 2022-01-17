package minimarketdemo.model.thumano.managers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import minimarketdemo.model.core.entities.ThmCargo;
import minimarketdemo.model.core.entities.ThmEmpleado;
import minimarketdemo.model.core.entities.ThmPeriodoRol;
import minimarketdemo.model.core.entities.ThmRolCabecera;
import minimarketdemo.model.core.entities.ThmRolDetalle;
import minimarketdemo.model.core.entities.VwThmConsultaRol;
import minimarketdemo.model.core.managers.ManagerDAO;
import minimarketdemo.model.seguridades.managers.ManagerSeguridades;
import minimarketdemo.model.thumano.dtos.DTOThmCargo;

/**
 * Session Bean implementation class ManagerTHumano
 */
@Stateless
@LocalBean
public class ManagerTHumano {
	public final static double PORCENTAJE_IESS=0.0935;
	public final static double PORCENTAJE_FONDOS_RESERVA=0.0833;
	public final static int INCREMENTO_HEXTRA=2;
	
	@EJB
	private ManagerDAO mDAO;
	@EJB
	private ManagerSeguridades mSeguridades;
    /**
     * Default constructor. 
     */
    public ManagerTHumano() {
        
    }
    
    //EMPLEADOS:
    @SuppressWarnings("unchecked")
	public List<ThmEmpleado> findAllThmEmpleado(){
    	return mDAO.findAll(ThmEmpleado.class);
    }
    
    public ThmEmpleado insertarThmEmpleado(ThmEmpleado nuevoEmpleado,int idThmCargo,int idSegUsuario) throws Exception {
    	ThmEmpleado nuevo=new ThmEmpleado();
    	Random rnd=new Random();
    	nuevo.setCuotaPrestamo(new BigDecimal(100*rnd.nextDouble()));//prestamo entre 0 y 100
    	nuevo.setHorasExtra(rnd.nextInt(20));//maximo 20 horas extra
    	nuevo.setHorasTrabajadas(160);//160 horas mensuales
    	nuevo.setSegUsuario(mSeguridades.findByIdSegUsuario(idSegUsuario));
    	nuevo.setThmCargo(findByIdThmCargo(idThmCargo));
    	mDAO.insertar(nuevo);
    	return nuevo;
    }
    
    //CARGOS:
    public ThmCargo findByIdThmCargo(int idThmCargo) throws Exception {
    	return (ThmCargo) mDAO.findById(ThmCargo.class, idThmCargo);
    }
    
    @SuppressWarnings("unchecked")
	public List<ThmCargo> findAllThmCargo(){
    	return mDAO.findAll(ThmCargo.class, "nombreCargo");
    }
    
    public List<DTOThmCargo> findAllDTOThmCargo(){
    	List<DTOThmCargo> listaDTO=new ArrayList<DTOThmCargo>();
    	for(ThmCargo cargo:findAllThmCargo()) {
    		DTOThmCargo nuevoDTO=new DTOThmCargo(cargo.getIdThmCargo(), cargo.getNombreCargo(), 
    				cargo.getRemuneracionMensual().doubleValue());
    		listaDTO.add(nuevoDTO);
    	}
    	return listaDTO;
    }
    public DTOThmCargo findDTOThmCargoById(int idThmCargo) throws Exception {
    	DTOThmCargo dtocargo=new DTOThmCargo();
    	ThmCargo cargo=findByIdThmCargo(idThmCargo);
    	dtocargo.setIdThmCargo(idThmCargo);
    	dtocargo.setNombreCargo(cargo.getNombreCargo());
    	dtocargo.setRemuneracionMensual(cargo.getRemuneracionMensual().doubleValue());
    	return dtocargo;
    }
    
    public void insertarCargo(ThmCargo cargo) throws Exception {
    	mDAO.insertar(cargo);
    }
    
    public void insertarCargoPasante() throws Exception {
    	ThmCargo cargo=new ThmCargo();
    	cargo.setNombreCargo("Pasante");
    	cargo.setRemuneracionMensual(new BigDecimal(440));
    	mDAO.insertar(cargo);
    }
    
    public void actualizarCargo(ThmCargo cargo) throws Exception {
    	ThmCargo edicionCargo=(ThmCargo) mDAO.findById(ThmCargo.class, cargo.getIdThmCargo());
    	edicionCargo.setNombreCargo(cargo.getNombreCargo());
    	edicionCargo.setRemuneracionMensual(cargo.getRemuneracionMensual());
    	mDAO.actualizar(edicionCargo);
    }
    
    public void eliminarCargo(int idThmCargo) throws Exception {
    	mDAO.eliminar(ThmCargo.class, idThmCargo);
    }
    
    //ROL DE PAGOS:
    
    @SuppressWarnings("unchecked")
	public List<ThmPeriodoRol> findAllThmPeriodoRol(){
    	return mDAO.findAll(ThmPeriodoRol.class, "nombrePeriodoRol");
    }
    
    @SuppressWarnings("unchecked")
	public List<ThmRolCabecera> findAllThmRolCabecera(){
    	return mDAO.findAll(ThmRolCabecera.class);
    }
    
    public void generarRolPagos(int idThmPeriodoRol) throws Exception{
    	//Obtenemos la referencia al periodo a generar:
    	ThmPeriodoRol periodoRol=(ThmPeriodoRol) mDAO.findById(ThmPeriodoRol.class, idThmPeriodoRol);
    	if(periodoRol.getGenerado()==true)
    		throw new Exception("El período indicado ya fue generado.");
    	//Iteramos la lista de empleados:
    	List<ThmEmpleado> listaEmpleados=findAllThmEmpleado();
    	if(listaEmpleados.size()==0)
    		throw new Exception("No existen empleados registrados.");
    	for(ThmEmpleado empleado:listaEmpleados) {
    		//por cada empleado se genera la cabecera del rol:
    		ThmRolCabecera cab=new ThmRolCabecera();
    		cab.setHorasExtras(empleado.getHorasExtra());
    		cab.setHorasTrabajadas(empleado.getHorasTrabajadas());
    		cab.setNombreCargo(empleado.getThmCargo().getNombreCargo());
    		cab.setThmPeriodoRol(periodoRol);
    		cab.setThmEmpleado(empleado);
    		//generar el detalle de cada rol cabecera:
    		generarDetalleRolPagos(cab, empleado);
    		mDAO.insertar(cab);
    	}
    	periodoRol.setGenerado(true);
    	mDAO.actualizar(periodoRol);
    }
    
    private void generarDetalleRolPagos(ThmRolCabecera cab,ThmEmpleado emp) {
    	List<ThmRolDetalle> detalles= new ArrayList<ThmRolDetalle>();
    	double subtotal=0;
    	double total=0;
		
    	ThmRolDetalle det=new ThmRolDetalle();
		det.setDescripcion("Sueldo");
		det.setOrden(1);
		det.setThmRolCabecera(cab);
		det.setTipoDetalle("IN");
		det.setValor(emp.getThmCargo().getRemuneracionMensual());
		detalles.add(det);
		subtotal+=det.getValor().doubleValue();
		
		det=new ThmRolDetalle();
		det.setDescripcion("Horas extras");
		det.setOrden(2);
		det.setThmRolCabecera(cab);
		det.setTipoDetalle("IN");
		det.setValor(new BigDecimal(emp.getHorasExtra()*emp.getThmCargo().getRemuneracionMensual().doubleValue()/emp.getHorasTrabajadas()*INCREMENTO_HEXTRA));
		detalles.add(det);
		subtotal+=det.getValor().doubleValue();
		
		cab.setSubtotalIngresos(new BigDecimal(subtotal));
		subtotal=0;
		
		det=new ThmRolDetalle();
		det.setDescripcion("Décimo 3er sueldo");
		det.setOrden(3);
		det.setThmRolCabecera(cab);
		det.setTipoDetalle("IA");
		det.setValor(new BigDecimal(emp.getThmCargo().getRemuneracionMensual().doubleValue()/12));
		detalles.add(det);
		subtotal+=det.getValor().doubleValue();
		
		det=new ThmRolDetalle();
		det.setDescripcion("Fondos de reserva");
		det.setOrden(4);
		det.setThmRolCabecera(cab);
		det.setTipoDetalle("IA");
		det.setValor(new BigDecimal(emp.getThmCargo().getRemuneracionMensual().doubleValue()*PORCENTAJE_FONDOS_RESERVA));
		detalles.add(det);
		subtotal+=det.getValor().doubleValue();
		
		cab.setSubtotalIngresosAdicionales(new BigDecimal(subtotal));
		subtotal=0;
		
		det=new ThmRolDetalle();
		det.setDescripcion("Aporte IESS");
		det.setOrden(5);
		det.setThmRolCabecera(cab);
		det.setTipoDetalle("EG");
		det.setValor(new BigDecimal(cab.getSubtotalIngresos().doubleValue()*PORCENTAJE_IESS));
		detalles.add(det);
		subtotal+=det.getValor().doubleValue();
		
		det=new ThmRolDetalle();
		det.setDescripcion("Préstamos");
		det.setOrden(6);
		det.setThmRolCabecera(cab);
		det.setTipoDetalle("EG");
		det.setValor(emp.getCuotaPrestamo());
		detalles.add(det);
		subtotal+=det.getValor().doubleValue();
		
		cab.setSubtotalEgresos(new BigDecimal(subtotal));
		subtotal=0;
		
		total=cab.getSubtotalIngresos().doubleValue()+cab.getSubtotalIngresosAdicionales().doubleValue()-cab.getSubtotalEgresos().doubleValue();
		cab.setTotal(new BigDecimal(total));
		
		cab.setThmRolDetalles(detalles);
		
    }
    
    @SuppressWarnings("unchecked")
	public List<VwThmConsultaRol> findVwThmConsultaRol(){
    	return mDAO.findAll(VwThmConsultaRol.class);
    }
    

}
