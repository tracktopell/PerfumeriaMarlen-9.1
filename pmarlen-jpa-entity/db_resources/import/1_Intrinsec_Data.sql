DELETE FROM TIPO_MOVIMIENTO;
DELETE FROM PEDIDO_VENTA_DETALLE; 
DELETE FROM PEDIDO_VENTA_ESTADO; 
DELETE FROM PEDIDO_VENTA;
DELETE FROM CLIENTE;
DELETE FROM PROVEEDOR_PRODUCTO;
DELETE FROM PRODUCTO_MULTIMEDIO;
DELETE FROM PROVEEDOR;
DELETE FROM MULTIMEDIO;
DELETE FROM ALMACEN_PRODUCTO;
DELETE FROM PRODUCTO;
DELETE FROM ALMACEN;
DELETE FROM MARCA;
DELETE FROM LINEA;
DELETE FROM INDUSTRIA;  
DELETE FROM USUARIO_PERFIL;
DELETE FROM USUARIO;
DELETE FROM PERFIL;
DELETE FROM FORMA_DE_PAGO;
DELETE FROM ESTADO;

-- =================================================================================

INSERT INTO PERFIL VALUES ('root'        ,'Super Usuario');
INSERT INTO PERFIL VALUES ('pmarlenuser' ,'Usuario Sistema');
INSERT INTO PERFIL VALUES ('admin'        ,'Administrador');
INSERT INTO PERFIL VALUES ('finances'        ,'Finanzas');
INSERT INTO PERFIL VALUES ('stock'        ,'Almacen');
INSERT INTO PERFIL VALUES ('sales'        ,'Vendedor');

INSERT INTO USUARIO VALUES ('root'        ,1,'root'                            ,'977244cbc826a0f25d07a07f194835b1'    ,'root@perfumeriamarlen.com.mx',NULL);

INSERT INTO USUARIO_PERFIL VALUES ('root'        ,'root');
INSERT INTO USUARIO_PERFIL VALUES ('root'        ,'pmarlenuser');

INSERT INTO FORMA_DE_PAGO (ID,DESCRIPCION)  VALUES (1,'EFECTIVO');
INSERT INTO FORMA_DE_PAGO (ID,DESCRIPCION)  VALUES (2,'T.C.');
INSERT INTO FORMA_DE_PAGO (ID,DESCRIPCION)  VALUES (3,'VALES DESPENSA');
INSERT INTO FORMA_DE_PAGO (ID,DESCRIPCION)  VALUES (4,'OTRO');

INSERT INTO METODO_DE_PAGO(ID,DESCRIPCION)  VALUES (1,'UNA SOLA EXHIBICIÓN');
INSERT INTO METODO_DE_PAGO(ID,DESCRIPCION)  VALUES (2,'OTRO');

INSERT INTO ESTADO (ID,DESCRIPCION) VALUES (1    ,'CAPTURADO');
INSERT INTO ESTADO (ID,DESCRIPCION) VALUES (2    ,'SINCRONIZADO');
INSERT INTO ESTADO (ID,DESCRIPCION) VALUES (4    ,'VERIFICADO');
INSERT INTO ESTADO (ID,DESCRIPCION) VALUES (8    ,'SURTIDO');
INSERT INTO ESTADO (ID,DESCRIPCION) VALUES (16   ,'FACTURADO');
INSERT INTO ESTADO (ID,DESCRIPCION) VALUES (32   ,'REMISIONADO');
INSERT INTO ESTADO (ID,DESCRIPCION) VALUES (64   ,'ENVIADO');
INSERT INTO ESTADO (ID,DESCRIPCION) VALUES (128  ,'ENTREGADO');
INSERT INTO ESTADO (ID,DESCRIPCION) VALUES (256  ,'DEVUELTO');
INSERT INTO ESTADO (ID,DESCRIPCION) VALUES (512  ,'VENDIDO_SUCURSAL');
INSERT INTO ESTADO (ID,DESCRIPCION) VALUES (1024 ,'FACTURADO_SUCURSAL');
INSERT INTO ESTADO (ID,DESCRIPCION) VALUES (2048 ,'DEVUELTO_SUCURSAL');
INSERT INTO ESTADO (ID,DESCRIPCION) VALUES (4096 ,'PAGADO');

INSERT INTO ESTADO (ID,DESCRIPCION) VALUES (65536,'CANCELADO');

INSERT INTO TIPO_MOVIMIENTO (ID,DESCRIPCION) VALUES (10,'CREACION');
INSERT INTO TIPO_MOVIMIENTO (ID,DESCRIPCION) VALUES (20,'ENTRADA_ALMACEN');
INSERT INTO TIPO_MOVIMIENTO (ID,DESCRIPCION) VALUES (30,'SALIDA_ALMACEN');
INSERT INTO TIPO_MOVIMIENTO (ID,DESCRIPCION) VALUES (50,'TIPO_MOV_MODIFICACION_COSTO_O_PRECIO');
INSERT INTO TIPO_MOVIMIENTO (ID,DESCRIPCION) VALUES (51,'TIPO_MOV_MODIFICACION_CODIGOBARRAS');
INSERT INTO TIPO_MOVIMIENTO (ID,DESCRIPCION) VALUES (52,'TIPO_MOV_MODIFICACION_NOMBRE_DESCRIPCION');

INSERT INTO SUCURSAL (ID,ID_PADRE,NOMBRE,CALLE,NUM_INTERIOR,NUM_EXTERIOR,POBLACION_ID,TELEFONOS,COMENTARIOS,USUARIO_SICOFI,PASSWORD_SICOFI,SERIE_SICOFI) VALUES(1,NULL,'PERFUMERIA MARLEN S.A. DE C.V. CENTRO DE DISTRIBUCIÓN','C. ORQUIDEAS','MZ5','LT6 S/N',60816,'(55)5936-2597','CENTRO DE DISTRIBUCION','perfumeriamarlendistribuciones@hotmail.com','Os15!es20','PMM');
INSERT INTO SUCURSAL (ID,ID_PADRE,NOMBRE,CALLE,NUM_INTERIOR,NUM_EXTERIOR,POBLACION_ID,TELEFONOS,COMENTARIOS,USUARIO_SICOFI,PASSWORD_SICOFI,SERIE_SICOFI) VALUES(2,1   ,'PERFUMERIA MARLEN S.A. DE C.V. SUCURSAL SN. MARTIN'   ,'C. FRANCISCO VILLA','MZ98','LT3 No.121',60818,'(55)5936-2597','SUCURSAL SN. MARTIN','cfdsuc2@perfumeriamarlen.com.mx','Pm@rl3n02','PMS');

INSERT INTO ALMACEN (ID,SUCURSAL_ID,TIPO_ALMACEN) VALUES(1,1,1);
INSERT INTO ALMACEN (ID,SUCURSAL_ID,TIPO_ALMACEN) VALUES(2,1,2);
INSERT INTO ALMACEN (ID,SUCURSAL_ID,TIPO_ALMACEN) VALUES(3,1,3);

INSERT INTO ALMACEN (ID,SUCURSAL_ID,TIPO_ALMACEN) VALUES(4,2,1);
INSERT INTO ALMACEN (ID,SUCURSAL_ID,TIPO_ALMACEN) VALUES(5,2,2);
INSERT INTO ALMACEN (ID,SUCURSAL_ID,TIPO_ALMACEN) VALUES(6,2,3);
    
UPDATE USUARIO SET SUCURSAL_ID=1 WHERE USUARIO_ID='root';

INSERT INTO CONFIGURACION_SISTEMA VALUES ('MULTIMEDIO_PATH','/home/pmarlenmultimedio/files/');

INSERT INTO CLIENTE(ID,RAZON_SOCIAL,RFC,CONTACTO,TELEFONOS,CALLE,NUM_EXTERIOR,NUM_INTERIOR,POBLACION_ID,EMAIL,URL) VALUES(1,'PUBLICO GENERAL','XAXX010101000','CAJERO','55-5936-7894','FRANCISCO VILLA','Mz 98','Lt 3',60818,'nomail@mail.com','http://pagina.com');
INSERT INTO CLIENTE(ID,RAZON_SOCIAL,RFC,CONTACTO,TELEFONOS,CALLE,NUM_EXTERIOR,NUM_INTERIOR,POBLACION_ID,EMAIL,URL) VALUES(2,'SERVICIOS DE LOGISTICA EMPRESARIAL KENIA, S.A. DE C.V.','SLE120202M92','GERENTE','55-5936-2597','CALLE XX','IN','EX',60816,'nomail@mail.com','http://pagina.com');
