/*
 * To change this.producto.xettemplate, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.web.operation;

import com.pmarlen.model.beans.Linea;
import com.pmarlen.model.beans.Marca;
import com.pmarlen.model.beans.Multimedio;
import com.pmarlen.model.beans.Producto;
import java.util.Collection;

/**
 *
 * @author alfredo
 */
public class ProductoRendereable {
	private Producto producto;

	public ProductoRendereable(Producto producto) {
		this.producto = producto;
	}
	
	public Producto getProducto(){
		return this.producto;
	}
	
	/**
     * Getters and Setters
     */
	
	
    public Integer getId() {
        return this.producto.getId();
    }

    public void setId(Integer v) {
        this.producto.setId(v);
    }

    public String getCodigoBarras() {
        return this.producto.getCodigoBarras();
    }

    public void setCodigoBarras(String v) {
        this.producto.setCodigoBarras(v);
    }

    public Marca getMarca() {
        return this.producto.getMarca();
    }

    public void setMarca(Marca v) {
        this.producto.setMarca(v);
    }

    public String getNombre() {
        return this.producto.getNombre();
    }

    public void setNombre(String v) {
        this.producto.setNombre(v);
    }

    public String getPresentacion() {
        return this.producto.getPresentacion();
    }

    public void setPresentacion(String v) {
        this.producto.setPresentacion(v);
    }

    public int getUnidadesPorCaja() {
        return this.producto.getUnidadesPorCaja();
    }

    public void setUnidadesPorCaja(int v) {
        this.producto.setUnidadesPorCaja(v);
    }

    public String getUnidadMedida() {
        return this.producto.getUnidadMedida();
    }

    public void setUnidadMedida(String v) {
        this.producto.setUnidadMedida(v);
    }

    public double getCosto() {
        return this.producto.getCosto();
    }

    public void setCosto(double v) {
        this.producto.setCosto(v);
    }

    public double getCostoVenta() {
        return this.producto.getCostoVenta();
    }

    public void setCostoVenta(double v) {
        this.producto.setCostoVenta(v);
    }

    public String getContenido() {
        return this.producto.getContenido();
    }

    public void setContenido(String v) {
        this.producto.setContenido(v);
    }

    public String getAbrebiatura() {
        return this.producto.getAbrebiatura();
    }

    public void setAbrebiatura(String v) {
        this.producto.setAbrebiatura(v);
    }
	
	private String defaultImageName;
	
	public String getDefaultImageName(){
		if(defaultImageName==null){
			Collection<Multimedio> multimedioCollection = producto.getMultimedioCollection();
			for(Multimedio m: multimedioCollection){
				if(m.getMimeType().toLowerCase().startsWith("image/")){
					defaultImageName = producto.getId()+"_0.jpg";
					break;
				}
			}
			if(defaultImageName == null) {
				defaultImageName = "DEFAULT.jpg";
			}
		
		}
		return defaultImageName;
	}

 
}
