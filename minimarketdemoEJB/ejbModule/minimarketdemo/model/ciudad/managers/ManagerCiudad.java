package minimarketdemo.model.ciudad.managers;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import minimarketdemo.model.core.entities.EstCiudad;

import java.util.List;

/**
 * Session Bean implementation class ManagerCiudad
 */
@Stateless
@LocalBean
public class ManagerCiudad {

    @PersistenceContext
    private EntityManager em;

    /**
     * Default constructor.
     */
    public ManagerCiudad() {
        // Constructor vacío
    }

    // Método para crear una ciudad
    public void crearCiudad(String nombre, String codigoPostal) {
        EstCiudad ciudad = new EstCiudad();
        ciudad.setNombre(nombre);
        ciudad.setCodigoPostal(codigoPostal);

        em.persist(ciudad);  // Guardar la ciudad en la base de datos
    }

    // Método para obtener todas las ciudades
    public List<EstCiudad> obtenerTodasCiudades() {
        return em.createQuery("SELECT c FROM EstCiudad c", EstCiudad.class).getResultList();
    }

    // Método para obtener una ciudad por su ID
    public EstCiudad obtenerCiudadPorId(Integer id) {
        return em.find(EstCiudad.class, id);
    }

    // Método para actualizar una ciudad
    public void actualizarCiudad(Integer id, String nombre, String codigoPostal) {
        EstCiudad ciudad = em.find(EstCiudad.class, id);
        if (ciudad != null) {
            ciudad.setNombre(nombre);
            ciudad.setCodigoPostal(codigoPostal);
            em.merge(ciudad);  // Actualiza la ciudad
        } else {
            throw new IllegalArgumentException("La ciudad con ID " + id + " no existe.");
        }
    }

    // Método para eliminar una ciudad
    public void eliminarCiudad(Integer id) {
        EstCiudad ciudad = em.find(EstCiudad.class, id);
        if (ciudad != null) {
            em.remove(ciudad);  // Elimina la ciudad
        } else {
            throw new IllegalArgumentException("La ciudad con ID " + id + " no existe.");
        }
    }
}
