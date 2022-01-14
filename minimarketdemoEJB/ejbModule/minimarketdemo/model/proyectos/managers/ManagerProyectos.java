package minimarketdemo.model.proyectos.managers;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import minimarketdemo.model.auditoria.managers.ManagerAuditoria;
import minimarketdemo.model.core.entities.PryProyecto;
import minimarketdemo.model.core.entities.PryTarea;
import minimarketdemo.model.core.entities.SegUsuario;
import minimarketdemo.model.core.managers.ManagerDAO;
import minimarketdemo.model.core.utils.ModelUtil;
import minimarketdemo.model.seguridades.dtos.LoginDTO;

/**
 * Session Bean implementation class ManagerProyectos
 */
@Stateless
@LocalBean
public class ManagerProyectos {
	@EJB
	private ManagerDAO mDAO;
	@PersistenceContext
	private EntityManager em;
	@EJB
	private ManagerAuditoria mAuditoria;
    /**
     * Default constructor. 
     */
    public ManagerProyectos() {
    }
    
    //FUNCIONES DEL JEFE DE PROYECTOS:
    public List<PryProyecto> findAllProyectos(){
    	return mDAO.findAll(PryProyecto.class);
    }
    /**
     * Consulta de proyectos con filtro. Este metodo se usa como
     * ejemplo de uso directo de EntityManager (sin ManagerDAO)
     * @param avance El avance maximo para la busqueda.
     * @param nombre El nombre de busqueda.
     * @return
     */
    public List<PryProyecto> findProyectosFiltro(int avance,String nombre){
    	Query q=em.createQuery("select p from PryProyecto p where p.avance<:avance and p.nombre like :nombre", PryProyecto.class);
    	q.setParameter("avance", avance);
    	q.setParameter("nombre", nombre);
    	List<PryProyecto> lista= q.getResultList();
    	for(PryProyecto p:lista) {
    		if(p.getPryTareas()!=null)
    			p.getPryTareas().size();//precarga de las tareas en memoria
    	}
    	return lista;
    }
    public PryProyecto inicializarProyecto() {
    	PryProyecto proyecto=new PryProyecto();
    	proyecto.setAvance(0);
    	proyecto.setEstado("I");
    	proyecto.setFechaInicio(new Date());
    	proyecto.setFechaFin(ModelUtil.addDays(new Date(), 30));
    	return proyecto;
    }
    public void insertarProyecto(LoginDTO loginDTO,PryProyecto nuevoProyecto) throws Exception{
    	if(nuevoProyecto.getFechaFin().before(nuevoProyecto.getFechaInicio()))
    		throw new Exception("La fecha de fin debe ser posterior a la fecha de inicio.");
    	mDAO.insertar(nuevoProyecto);
    	mAuditoria.mostrarLog(loginDTO,getClass(), "insertarProyecto", "Proyecto "+nuevoProyecto.getNombre()+" insertado exitosamente.");
    }
    public List<PryTarea> findTareasByProyecto(int idPryProyecto){
    	return mDAO.findWhere(PryTarea.class, "o.pryProyecto.idPryProyecto="+idPryProyecto, "o.fechaInicio");
    }
    public PryTarea inicializarTarea(PryProyecto proyecto) {
    	PryTarea tarea=new PryTarea();
    	tarea.setAvance(0);
    	tarea.setFechaInicio(proyecto.getFechaInicio());
    	tarea.setFechaFin(proyecto.getFechaFin());
    	tarea.setPryProyecto(proyecto);
    	return tarea;
    }
    public void insertarTarea(PryTarea nuevaTarea,int idSegUsuario) throws Exception {
    	SegUsuario usuario=(SegUsuario) mDAO.findById(SegUsuario.class, idSegUsuario);
    	nuevaTarea.setSegUsuario(usuario);
    	nuevaTarea.setAvance(0);
    	mDAO.insertar(nuevaTarea);
    }
    
    //FUNCIONES DEL ANALISTA:
    public List<PryTarea> findTareasByUsuario(int idSegUsuario){
    	return mDAO.findWhere(PryTarea.class, "o.segUsuario.idSegUsuario="+idSegUsuario, "o.fechaInicio");
    }
    public void actualizarAvance(PryTarea tarea) throws Exception {
    	if(tarea.getAvance()<0 || tarea.getAvance()>100)
    		throw new Exception("Avance incorrecto.");
    	mDAO.actualizar(tarea);
    	//cada vez que se actualiza el avance, se calcula el avance a nivel de proyecto:
    	actualizarAvanceProyecto(tarea.getPryProyecto().getIdPryProyecto());
    }
    public void actualizarAvanceProyecto(int idPryProyecto) throws Exception {
    	PryProyecto proyecto=(PryProyecto) mDAO.findById(PryProyecto.class, idPryProyecto);
    	long diasProyecto=ModelUtil.getDifferenceDays(proyecto.getFechaInicio(), proyecto.getFechaFin());
    	System.out.println(diasProyecto);
    	List<PryTarea> tareas=findTareasByProyecto(idPryProyecto);
    	float avance=0;
    	for(PryTarea t:tareas) {
    		avance+=t.getAvance()/100.0;
    		System.out.println("avance: "+t.getAvance()+" suma:"+avance);
    	}
    	proyecto.setAvance((int) (avance/tareas.size()*100));
    	System.out.println("avance total:"+avance/tareas.size()*100);
    	mDAO.actualizar(proyecto);
    }

}
