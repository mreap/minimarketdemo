package minimarketdemo.model.seguridades.managers;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import minimarketdemo.model.auditoria.managers.ManagerAuditoria;
import minimarketdemo.model.core.entities.SegAsignacion;
import minimarketdemo.model.core.entities.SegModulo;
import minimarketdemo.model.core.entities.SegPerfil;
import minimarketdemo.model.core.entities.SegUsuario;
import minimarketdemo.model.core.managers.ManagerDAO;
import minimarketdemo.model.core.utils.ModelUtil;
import minimarketdemo.model.seguridades.dtos.LoginDTO;

/**
 * Implementa la logica de manejo de usuarios y autenticacion.
 * Funcionalidades principales:
 * <ul>
 * 	<li>Verificacion de credenciales de usuario.</li>
 *  <li>Asignacion de modulos a un usuario.</li>
 * </ul>
 * @author mrea
 */
@Stateless
@LocalBean
public class ManagerSeguridades {
	@EJB
	private ManagerDAO mDAO;
	@EJB
	private ManagerAuditoria mAuditoria;
    /**
     * Default constructor. 
     */
    public ManagerSeguridades() {
        
    }
    /**
     * Funcion de inicializacion de datos de usuarios.
     */
    public String inicializarDemo() throws Exception {
    	mAuditoria.mostrarLog(getClass(), "inicializarDemo", "Inicializacion de bdd demo.");
    	int idSegUsuarioAdmin=0;
    	String mensaje="";//mensaje que se enviara al metodo que invoca a esta funcion.
    	//buscar el usuario admin (id igual a 1)
    	SegUsuario admin=(SegUsuario) mDAO.findById(SegUsuario.class, 1);
    	if(admin==null) {
    		//creacion del usuario admin:
    		admin=new SegUsuario();
			admin.setActivo(true);
			admin.setApellidos("admin");
			admin.setClave("admin");
			admin.setCorreo("admin@minimarketdemo.com");
			admin.setNombres("admin");
			admin.setCodigo("admin");
			mDAO.insertar(admin);
			idSegUsuarioAdmin=admin.getIdSegUsuario();
			
			mAuditoria.mostrarLog(getClass(), "inicializarDemo", "Usuario administrador creado (id : "+idSegUsuarioAdmin+")");
    	}else {
    		idSegUsuarioAdmin=admin.getIdSegUsuario();
    		mAuditoria.mostrarLog(getClass(), "inicializarDemo", "Usuario administrador ya existe (id : "+idSegUsuarioAdmin+")");
    	}
    	mensaje="Id del usuario admin: "+idSegUsuarioAdmin;
    	
    	//verificar si ya existen los modulos iniciales:
    	int idSegModuloSeguridades=0;
    	int idSegModuloAuditoria=0;
    	int idSegPerfilAuditor=0;
    	int idSegPerfilSegAdministrador=0;
    	List<SegModulo> modulos=mDAO.findAll(SegModulo.class);
    	for(SegModulo m:modulos) {
    		if(m.getNombreModulo().equals("Seguridades"))
    			idSegModuloSeguridades=m.getIdSegModulo();
    		if(m.getNombreModulo().equals("Auditoría"))
    			idSegModuloAuditoria=m.getIdSegModulo();
    	}
    	
    	if(idSegModuloSeguridades==0) {
			//inicializacion de modulo:
			SegModulo modulo=new SegModulo();
			modulo.setNombreModulo("Seguridades");
			modulo.setIcono("pi pi-lock");
			mDAO.insertar(modulo);
			idSegModuloSeguridades=modulo.getIdSegModulo();
			mAuditoria.mostrarLog(getClass(), "inicializarDemo", "Creado módulo de seguridades (id : "+idSegModuloSeguridades+")");
			//ahora creamos el perfil para el acceso del usuario:
			SegPerfil perfil=new SegPerfil();
			perfil.setNombrePerfil("Administrador");
			perfil.setRutaAcceso("seguridades/administrador/menu");
			perfil.setSegModulo(modulo);
			mDAO.insertar(perfil);
			idSegPerfilSegAdministrador=perfil.getIdSegPerfil();
    	}else {
    		//si ya existe el modulo, buscamos el perfil de Administrador de seguridad:
    		SegModulo m=(SegModulo) mDAO.findById(SegModulo.class, idSegModuloSeguridades);
    		for(SegPerfil p:m.getSegPerfils()) {
    			if(p.getRutaAcceso().equals("seguridades/administrador/menu"))
    				idSegPerfilSegAdministrador=p.getIdSegPerfil();
    		}
    	}
    	//asignacion de accesos:
    	try {
			asignarPerfil(idSegUsuarioAdmin, idSegPerfilSegAdministrador);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    	
    	if(idSegModuloAuditoria==0) {
			SegModulo modulo=new SegModulo();
			modulo.setNombreModulo("Auditoría");
			modulo.setIcono("pi pi-eye");
			mDAO.insertar(modulo);
			idSegModuloAuditoria=modulo.getIdSegModulo();
			mAuditoria.mostrarLog(getClass(), "inicializarDemo", "Creado módulo de auditoría (id : "+idSegModuloAuditoria+")");
			//ahora creamos el perfil para el acceso del usuario:
			SegPerfil perfil=new SegPerfil();
			perfil.setNombrePerfil("Auditor");
			perfil.setRutaAcceso("auditoria/auditor/menu");
			perfil.setSegModulo(modulo);
			mDAO.insertar(perfil);
			idSegPerfilAuditor=perfil.getIdSegPerfil();
    	}else {
    		//si ya existe el modulo, buscamos el perfil de Auditor:
    		SegModulo m=(SegModulo) mDAO.findById(SegModulo.class, idSegModuloAuditoria);
    		for(SegPerfil p:m.getSegPerfils()) {
    			if(p.getRutaAcceso().equals("auditoria/auditor/menu"))
    				idSegPerfilAuditor=p.getIdSegPerfil();
    		}
    	}
    	//asignacion de accesos:
    	try {
			asignarPerfil(idSegUsuarioAdmin, idSegPerfilAuditor);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    	
		mAuditoria.mostrarLog(getClass(), "inicializarDemo", "Inicializacion de bdd demo terminada.");
		return mensaje+" Inicialización de bdd demo terminada.";
    }
    
    /**
     * Funcion de autenticacion mediante el uso de credenciales.
     * @param idSegUsuario El codigo del usuario que desea autenticarse.
     * @param clave La contrasenia.
     * @param direccionIP La dirección IP V4 del cliente web.
     * @return La ruta de acceso al sistema.
     * @throws Exception
     */
    public LoginDTO login(int idSegUsuario,String clave,String direccionIP) throws Exception{
    	//crear DTO:
		LoginDTO loginDTO=new LoginDTO();
		loginDTO.setIdSegUsuario(idSegUsuario);
		loginDTO.setDireccionIP(direccionIP);
		
    	if(ModelUtil.isEmpty(clave)) {
    		mAuditoria.mostrarLog(getClass(), "login", "Debe indicar una clave "+idSegUsuario);
    		throw new Exception("Debe indicar una clave");
    	}
    	SegUsuario usuario=(SegUsuario) mDAO.findById(SegUsuario.class, idSegUsuario);
    	if(usuario==null) {
    		mAuditoria.mostrarLog(getClass(), "login", "No existe usuario "+idSegUsuario);
    		throw new Exception("Error en credenciales.");
    	}
    		
    	if(usuario.getClave().equals(clave)) {
    		if(usuario.getActivo()==false) {
        		mAuditoria.mostrarLog(getClass(), "login", "Intento de login usuario desactivado "+idSegUsuario);
        		throw new Exception("El usuario esta desactivado.");
        	}
    		mAuditoria.mostrarLog(loginDTO, getClass(), "login", "Login exitoso "+idSegUsuario);
    		
    		loginDTO.setCorreo(usuario.getCorreo());
    		//aqui puede realizarse el envio de correo de notificacion.
    		
    		//obtener la lista de perfiles a los que tiene acceso:
    		List<SegAsignacion> listaAsignaciones=findAsignacionByUsuario(usuario.getIdSegUsuario());
    		for(SegAsignacion asig:listaAsignaciones) {
    			SegPerfil perfil=asig.getSegPerfil();
    			loginDTO.getListaPerfiles().add(perfil);
    		}
    		return loginDTO;
    	}
    	mAuditoria.mostrarLog(getClass(), "login", "No coincide la clave "+idSegUsuario);
    	throw new Exception("Error en credenciales");
    }
    
    public void cerrarSesion(int idSegUsuario) {
    	mAuditoria.mostrarLog(getClass(), "cerrarSesion", "Cerrar sesión usuario: "+idSegUsuario);
    }
    
    public void accesoNoPermitido(int idSegUsuario,String ruta) {
    	mAuditoria.mostrarLog(getClass(), "accesoNoPermitido", "Usuario "+idSegUsuario+" intento no autorizado a "+ruta);
    }
    
    public List<SegUsuario> findAllUsuarios(){
    	return mDAO.findAll(SegUsuario.class, "apellidos");
    }
    
    public SegUsuario findByIdSegUsuario(int idSegUsuario) throws Exception {
    	return (SegUsuario) mDAO.findById(SegUsuario.class, idSegUsuario);
    }
    
    public void insertarUsuario(SegUsuario nuevoUsuario) throws Exception {
    	nuevoUsuario.setCodigo("n/a");
    	mDAO.insertar(nuevoUsuario);
    }
    
    public void actualizarUsuario(LoginDTO loginDTO,SegUsuario edicionUsuario) throws Exception {
    	SegUsuario usuario=(SegUsuario) mDAO.findById(SegUsuario.class, edicionUsuario.getIdSegUsuario());
    	usuario.setApellidos(edicionUsuario.getApellidos());
    	usuario.setClave(edicionUsuario.getClave());
    	usuario.setCorreo(edicionUsuario.getCorreo());
    	usuario.setCodigo(edicionUsuario.getCodigo());
    	usuario.setNombres(edicionUsuario.getNombres());
    	mDAO.actualizar(usuario);
    	mAuditoria.mostrarLog(loginDTO, getClass(), "actualizarUsuario", "se actualizó al usuario "+usuario.getApellidos());
    }
    
    public void activarDesactivarUsuario(int idSegUsuario) throws Exception {
    	SegUsuario usuario=(SegUsuario) mDAO.findById(SegUsuario.class, idSegUsuario);
    	if(idSegUsuario==1)
    		throw new Exception("No se puede desactivar al usuario administrador.");
    	usuario.setActivo(!usuario.getActivo());
    	System.out.println("activar/desactivar "+usuario.getActivo());
    	mDAO.actualizar(usuario);
    }
    
    public void eliminarUsuario(int idSegUsuario) throws Exception {
    	SegUsuario usuario=(SegUsuario) mDAO.findById(SegUsuario.class, idSegUsuario);
    	if(usuario.getIdSegUsuario()==1)
    		throw new Exception("No se puede eliminar el usuario administrador.");
    	if(usuario.getSegAsignacions().size()>0)
    		throw new Exception("No se puede elimininar el usuario porque tiene asignaciones de módulos.");
    	mDAO.eliminar(SegUsuario.class, usuario.getIdSegUsuario());
    	//TODO agregar uso de LoginDTO para auditar metodo.
    }
    
    public List<SegModulo> findAllModulos(){
    	return mDAO.findAll(SegModulo.class, "nombreModulo");
    }
    
    public SegModulo insertarModulo(SegModulo nuevoModulo) throws Exception {
    	SegModulo modulo=new SegModulo();
    	modulo.setNombreModulo(nuevoModulo.getNombreModulo());
    	modulo.setIcono(nuevoModulo.getIcono());
    	mDAO.insertar(modulo);
    	return modulo;
    }
    
    public void eliminarModulo(int idSegModulo) throws Exception {
    	SegModulo modulo=(SegModulo) mDAO.findById(SegModulo.class, idSegModulo);
    	if(modulo.getSegPerfils().size()>0)
    		throw new Exception("No se puede eliminar un módulo que tiene perfiles asignados.");
    	mDAO.eliminar(SegModulo.class, idSegModulo);
    }
    
    public void actualizarModulo(SegModulo edicionModulo) throws Exception {
    	SegModulo modulo=(SegModulo) mDAO.findById(SegModulo.class, edicionModulo.getIdSegModulo());
    	modulo.setNombreModulo(edicionModulo.getNombreModulo());
    	modulo.setIcono(edicionModulo.getIcono());
    	mDAO.actualizar(modulo);
    }
    
    public List<SegAsignacion> findAsignacionByUsuario(int idSegUsuario){
    	String consulta="o.segUsuario.idSegUsuario="+idSegUsuario;
		List<SegAsignacion> listaAsignaciones=mDAO.findWhere(SegAsignacion.class, consulta, "o.segPerfil.segModulo.nombreModulo,o.segPerfil.nombrePerfil");
		return listaAsignaciones;
    }
    
    /**
     * Permite asignar el acceso a un perfil del inventario de sistemas.
     * @param idSegUsuario El codigo del usuario.
     * @param idSegPerfil El codigo del perfil que se va a asignar.
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public void asignarPerfil(int idSegUsuario,int idSegPerfil) throws Exception{
    	//Buscar los objetos primarios:
    	SegUsuario usuario=(SegUsuario)mDAO.findById(SegUsuario.class, idSegUsuario);
    	SegPerfil perfil=(SegPerfil)mDAO.findById(SegPerfil.class, idSegPerfil);
    	//verificar si ya existe:
    	String consulta="o.segPerfil.idSegPerfil="+idSegPerfil+" and o.segUsuario.idSegUsuario="+idSegUsuario;
    	List<SegAsignacion> asignaciones=mDAO.findWhere(SegAsignacion.class, consulta, null);
    	if(asignaciones==null || asignaciones.size()==0) {
	    	//crear la relacion:
	    	SegAsignacion asignacion=new SegAsignacion();
	    	asignacion.setSegPerfil(perfil);
	    	asignacion.setSegUsuario(usuario);
	    	//guardar la asignacion:
	    	mDAO.insertar(asignacion);
	    	mAuditoria.mostrarLog(getClass(), "asignarPerfil", "Perfil "+idSegPerfil+" asignado a usuario "+idSegUsuario);
    	}else {
    		throw new Exception("Ya existe la asignación de usuario/perfil ("+idSegUsuario+"/"+idSegPerfil+")");
    	}
    }
    
    public void eliminarAsignacion(int idSegAsignacion) throws Exception {
    	mDAO.eliminar(SegAsignacion.class, idSegAsignacion);
    }
    
    public List<SegPerfil> findPerfilesByModulo(int idSegModulo){
    	List<SegPerfil> listado=mDAO.findWhere(SegPerfil.class, "o.segModulo.idSegModulo="+idSegModulo, "o.nombrePerfil");
    	return listado;
    }
    
    public SegPerfil insertarPerfil(SegPerfil nuevoPerfil) throws Exception {
    	SegPerfil perfil=new SegPerfil();
    	perfil.setNombrePerfil(nuevoPerfil.getNombrePerfil());
    	perfil.setRutaAcceso(nuevoPerfil.getRutaAcceso());
    	perfil.setSegModulo(nuevoPerfil.getSegModulo());
    	mDAO.insertar(perfil);
    	return perfil;
    }
    
    public void eliminarPerfil(int idSegPerfil) throws Exception {
    	mDAO.eliminar(SegPerfil.class, idSegPerfil);
    }
    
    public void actualizarPerfil(SegPerfil edicionPerfil) throws Exception {
    	SegPerfil perfil=(SegPerfil) mDAO.findById(SegPerfil.class, edicionPerfil.getIdSegPerfil());
    	perfil.setNombrePerfil(edicionPerfil.getNombrePerfil());
    	perfil.setRutaAcceso(edicionPerfil.getRutaAcceso());
    	mDAO.actualizar(perfil);
    }

}
