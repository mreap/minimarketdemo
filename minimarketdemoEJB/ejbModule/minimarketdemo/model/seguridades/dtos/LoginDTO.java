package minimarketdemo.model.seguridades.dtos;

import java.util.ArrayList;
import java.util.List;

import minimarketdemo.model.core.entities.SegPerfil;

public class LoginDTO {
	private int idSegUsuario;
	private String correo;
	private String direccionIP;
	private List<SegPerfil> listaPerfiles;
	
	public LoginDTO() {
		listaPerfiles=new ArrayList<SegPerfil>();
	}
	public int getIdSegUsuario() {
		return idSegUsuario;
	}
	public void setIdSegUsuario(int idSegUsuario) {
		this.idSegUsuario = idSegUsuario;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public List<SegPerfil> getListaPerfiles() {
		return listaPerfiles;
	}
	public void setListaPerfiles(List<SegPerfil> listaPerfiles) {
		this.listaPerfiles = listaPerfiles;
	}
	public String getDireccionIP() {
		return direccionIP;
	}
	public void setDireccionIP(String direccionIP) {
		this.direccionIP = direccionIP;
	}
	
}
