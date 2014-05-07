/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.businesslogic;

import com.pmarlen.model.beans.PedidoVenta;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author alfredo
 */
public class BusinessLogicCommander {

	public static void main(String[] args) {
		System.out.println("=======================PMArlen BusinessLogicCommander ==========================");
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("/application-context.xml");
			System.out.println("=>context:" + context);
			System.out.print("PRESS :");
			System.in.read();
			PedidoVentaBusinessLogic pedidoVentaBusinessLogic = context.getBean("pedidoVentaBusinessLogic", PedidoVentaBusinessLogic.class);
			List<PedidoVenta> pvList = pedidoVentaBusinessLogic.findPedidoVentaEntities();
			for (PedidoVenta pv : pvList) {
				System.out.println("=>PV:" + pv.getId());
			}
			System.out.print("PRESS :");
			System.in.read();
			
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}
}
