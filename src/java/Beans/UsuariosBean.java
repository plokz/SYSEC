/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.util.List;

/**
 *
 * @author Eduardo
 */
public class UsuariosBean {

    private int idUsuario;
    private String nombre, rfc, colonia, CP, email, usuario, password,fechaMiembro;
    private EstadoBean estadoBean;
    private TipoBean tipoBean;
   
    List<MensajesBean> listaMensajes;
    List<PostBean> listaPost;
    List<PreferenciasBean> preferenciasBean;

    public String getFechaMiembro() {
        return fechaMiembro;
    }

    public void setFechaMiembro(String fechaMiembro) {
        this.fechaMiembro = fechaMiembro;
    }

    
    
    
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCP() {
        return CP;
    }

    public void setCP(String CP) {
        this.CP = CP;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EstadoBean getEstadoBean() {
        return estadoBean;
    }

    public void setEstadoBean(EstadoBean estadoBean) {
        this.estadoBean = estadoBean;
    }

    public TipoBean getTipoBean() {
        return tipoBean;
    }

    public void setTipoBean(TipoBean tipoBean) {
        this.tipoBean = tipoBean;
    }

    public List<MensajesBean> getListaMensajes() {
        return listaMensajes;
    }

    public void setListaMensajes(List<MensajesBean> listaMensajes) {
        this.listaMensajes = listaMensajes;
    }

    public List<PostBean> getListaPost() {
        return listaPost;
    }

    public void setListaPost(List<PostBean> listaPost) {
        this.listaPost = listaPost;
    }

    public List<PreferenciasBean> getPreferenciasBean() {
        return preferenciasBean;
    }

    public void setPreferenciasBean(List<PreferenciasBean> preferenciasBean) {
        this.preferenciasBean = preferenciasBean;
    }
}
