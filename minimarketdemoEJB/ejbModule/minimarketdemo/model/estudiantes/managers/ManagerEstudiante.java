package minimarketdemo.model.estudiantes.managers;

import javax.ejb.Stateless;

import java.util.List;

import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import minimarketdemo.model.core.entities.EstEstudiante;
import minimarketdemo.model.core.entities.EstCiudad;

/**
 * Session Bean implementation class ManagerEstudiante
 */
@Stateless
@LocalBean
public class ManagerEstudiante {

    @PersistenceContext 
    private EntityManager em;

    /**
     * Default constructor. 
     */
    public ManagerEstudiante() {
    }

    // Método para crear un estudiante
    public void crearEstudiante(String nombre, String apellido, String email, java.util.Date fechaNacimiento, Integer ciudadId) {
        EstEstudiante estudiante = new EstEstudiante();
        EstCiudad ciudad = em.find(EstCiudad.class, ciudadId); 

        if (ciudad != null) {
            estudiante.setNombre(nombre);
            estudiante.setApellido(apellido);
            estudiante.setEmail(email);
            estudiante.setFechaNacimiento(fechaNacimiento);
            estudiante.setEstCiudad(ciudad);  

            em.persist(estudiante);  
        } else {
            throw new IllegalArgumentException("La ciudad con ID " + ciudadId + " no existe.");
        }
    }

    // Método para obtener todos los estudiantes
    public List<EstEstudiante> obtenerTodosEstudiantes() {
        return em.createQuery("SELECT e FROM EstEstudiante e", EstEstudiante.class).getResultList();
    }

    // Método para obtener un estudiante por su ID
    public EstEstudiante obtenerEstudiantePorId(Integer id) {
        return em.find(EstEstudiante.class, id);
    }

    // Método para actualizar un estudiante
    public void actualizarEstudiante(Integer id, String nombre, String apellido, String email, java.util.Date fechaNacimiento, Integer ciudadId) {
        EstEstudiante estudiante = em.find(EstEstudiante.class, id);
        if (estudiante != null) {
            EstCiudad ciudad = em.find(EstCiudad.class, ciudadId);
            if (ciudad != null) {
                estudiante.setNombre(nombre);
                estudiante.setApellido(apellido);
                estudiante.setEmail(email);
                estudiante.setFechaNacimiento(fechaNacimiento);
                estudiante.setEstCiudad(ciudad);
                em.merge(estudiante);  // Actualiza el estudiante
            } else {
                throw new IllegalArgumentException("La ciudad con ID " + ciudadId + " no existe.");
            }
        } else {
            throw new IllegalArgumentException("El estudiante con ID " + id + " no existe.");
        }
    }

    // Método para eliminar un estudiante
    public void eliminarEstudiante(Integer id) {
        EstEstudiante estudiante = em.find(EstEstudiante.class, id);
        if (estudiante != null) {
            em.remove(estudiante);  // Elimina el estudiante
        } else {
            throw new IllegalArgumentException("El estudiante con ID " + id + " no existe.");
        }
    }
}