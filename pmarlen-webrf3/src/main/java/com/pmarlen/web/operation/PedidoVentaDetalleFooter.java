package com.pmarlen.web.operation;

/**
 * PedidoVentaDetalleFooter
 */
public class PedidoVentaDetalleFooter {
private Integer  cantTotal;
    private Integer numItems;
    private Double subtotal;
    private Double impuesto;
    private Double descuento;
    private Double total;

    /**
     * @return the cantTotal
     */
    public Integer getCantTotal() {
        return cantTotal;
    }

    /**
     * @param cantTotal the cantTotal to set
     */
    public void setCantTotal(Integer cantTotal) {
        this.cantTotal = cantTotal;
    }

    /**
     * @return the numItems
     */
    public Integer getNumItems() {
        return numItems;
    }

    /**
     * @param numItems the numItems to set
     */
    public void setNumItems(Integer numItems) {
        this.numItems = numItems;
    }

    /**
     * @return the subtotal
     */
    public Double getSubtotal() {
        return subtotal;
    }

    /**
     * @param subtotal the subtotal to set
     */
    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    /**
     * @return the impuesto
     */
    public Double getImpuesto() {
        return impuesto;
    }

    /**
     * @param impuesto the impuesto to set
     */
    public void setImpuesto(Double impuesto) {
        this.impuesto = impuesto;
    }

    /**
     * @return the descuento
     */
    public Double getDescuento() {
        return descuento;
    }

    /**
     * @param descuento the descuento to set
     */
    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    /**
     * @return the total
     */
    public Double getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(Double total) {
        this.total = total;
    }
}
