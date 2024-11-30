package minimarketdemo.controller.estudiante;

import minimarketdemo.model.core.entities.EstEstudiante;
import minimarketdemo.model.core.entities.EstCiudad;
import minimarketdemo.model.estudiantes.managers.ManagerEstudiante;
import minimarketdemo.model.ciudad.managers.ManagerCiudad;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;


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
        try {
            if (estudiante.getEstCiudad() == null || estudiante.getEstCiudad().getId() == null) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, 
                                                    "Advertencia", 
                                                    "Debe seleccionar una ciudad.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return null; // No redirigir, permanece en la misma página
            }

            managerEstudiante.crearEstudiante(
                estudiante.getNombre(),
                estudiante.getApellido(),
                estudiante.getEmail(),
                estudiante.getFechaNacimiento(),
                estudiante.getEstCiudad().getId()
            );

            // Agregar mensaje de éxito
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                                                "Éxito", 
                                                "¡El estudiante fue creado correctamente!");
            FacesContext.getCurrentInstance().addMessage(null, msg);

            // Reiniciar el objeto estudiante
            estudiante = new EstEstudiante();
            estudiante.setEstCiudad(new EstCiudad());

        } catch (Exception e) {
            // Agregar mensaje de error
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                                                "Error", 
                                                "Ocurrió un error al crear el estudiante: " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            e.printStackTrace();
        }
        return null; // Permanece en la misma página después de ejecutar
    }
    
    public void actualizarEstudiante() {
        try {
            managerEstudiante.actualizarEstudiante(estudiante.getId(),
                                                   estudiante.getNombre(),
                                                   estudiante.getApellido(),
                                                   estudiante.getEmail(),
                                                   estudiante.getFechaNacimiento(),
                                                   estudiante.getEstCiudad().getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Éxito", "Estudiante actualizado correctamente."));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error", "No se pudo actualizar el estudiante: " + e.getMessage()));
        }
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
        if (estudiante == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error", "No se pudo cargar el estudiante."));
        }
    }

}
