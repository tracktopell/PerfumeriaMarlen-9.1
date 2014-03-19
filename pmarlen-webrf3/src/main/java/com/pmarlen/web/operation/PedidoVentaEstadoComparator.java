package com.pmarlen.web.operation;

import com.pmarlen.model.beans.PedidoVentaEstado;
import java.util.Comparator;

class PedidoVentaEstadoComparator implements Comparator<PedidoVentaEstado> {

    @Override
    public int compare(PedidoVentaEstado o1, PedidoVentaEstado o2) {

        int dateComparation = o1.getFecha().compareTo(o2.getFecha());

        dateComparation =   (dateComparation < 0 )? -1:
                            (dateComparation > 0 )?  1:0;

        int estadoComparation = (o1.getEstado().getId() - o2.getEstado().getId()) * 100;

        return estadoComparation + dateComparation;
    }
}
