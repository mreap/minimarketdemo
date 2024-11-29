package minimarketdemo.controller.estudiante;

import minimarketdemo.model.core.entities.EstEstudiante;
import minimarketdemo.model.core.entities.EstCiudad;
import minimarketdemo.model.estudiantes.managers.ManagerEstudiante;
import minimarketdemo.model.ciudad.managers.ManagerCiudad;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import java.io.Console;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class BeanEstudiante implements Serializable {

    @Inject
    private ManagerEstudiante managerEstudiante; // Para las operaciones CRUD de estudiantes

    @Inject
    private ManagerCiudad managerCiudad; // Para obtener las ciudades

    private EstEstudiante estudiante;
    private Integer estudianteSeleccionado;
    private List<EstEstudiante> listaEstudiantes;
    private List<EstCiudad> ciudades; // Lista de ciudades para el selectBox

    public BeanEstudiante() {
        estudiante = new EstEstudiante();
    }

    // Getter y Setter
    public EstEstudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(EstEstudiante estudiante) {
        this.estudiante = estudiante;
    }

    public List<EstEstudiante> getListaEstudiantes() {

            listaEstudiantes = managerEstudiante.obtenerTodosEstudiantes();

        return listaEstudiantes;
    }

    public List<EstCiudad> getCiudades() {
        if (ciudades == null) {
            ciudades = managerCiudad.obtenerTodasCiudades();
        }
        return ciudades;
    }
    
    public Integer formCiudadData(Integer id) {
    	if(id == null)
    		return 0;
    	return id;
    }

    // Métodos CRUD
    public String crearEstudiante() {
    	System.out.println("aaaa");
        if (estudiante != null && estudiante.getEstCiudad() != null) {
            managerEstudiante.crearEstudiante(estudiante.getNombre(), estudiante.getApellido(), estudiante.getEmail(), estudiante.getFechaNacimiento(), estudiante.getEstCiudad().getId());
            estudiante = new EstEstudiante(); // Reseteamos el objeto para un nuevo registro
        }
        return "listaEstudiantes?faces-redirect=true";
    }

    public String actualizarEstudiante() {
        if (estudiante != null && estudiante.getEstCiudad() != null) {
            managerEstudiante.actualizarEstudiante(estudiante.getId(), estudiante.getNombre(), estudiante.getApellido(), estudiante.getEmail(), estudiante.getFechaNacimiento(), estudiante.getEstCiudad().getId());
        }
        return "listaEstudiantes?faces-redirect=true";
    }

    public String eliminarEstudiante(Integer id) {
        if (id != null) {
            managerEstudiante.eliminarEstudiante(id);
        }
        return "listaEstudiantes?faces-redirect=true";
    }
    public String eliminarEstudiante() {
        return eliminarEstudiante(estudianteSeleccionado);
    }
    public String capturarEliminarEstudiante(Integer id) {
    	try {
			//capturamos el valor enviado desde el DataTable:
			estudianteSeleccionado = id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
    }

    // Método para pre-cargar los datos de un estudiante (por si se está actualizando)
    public void cargarEstudiante(Integer id) {
        estudiante = managerEstudiante.obtenerEstudiantePorId(id);
    }
}
