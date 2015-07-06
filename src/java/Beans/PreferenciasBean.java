/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

/**
 *
 * @author Eduardo
 */
public class PreferenciasBean {

    private int idPreferencias;
    private String preferencia;
    private boolean tiene;

    public int getIdPreferencias() {
        return idPreferencias;
    }

    public void setIdPreferencias(int idPreferencias) {
        this.idPreferencias = idPreferencias;
    }

    public String getPreferencia() {
        return preferencia;
    }

    public void setPreferencia(String preferencia) {
        this.preferencia = preferencia;
    }

    public boolean isTiene() {
        return tiene;
    }

    public void setTiene(boolean tiene) {
        this.tiene = tiene;
    }

    
}
