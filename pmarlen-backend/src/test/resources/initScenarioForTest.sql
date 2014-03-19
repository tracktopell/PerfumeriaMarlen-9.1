
DELETE FROM MOVIMIENTO_HISTORICO_PRODUCTO;
DELETE FROM PEDIDO_VENTA_DETALLE; 
DELETE FROM PEDIDO_VENTA_ESTADO; 
DELETE FROM PEDIDO_VENTA;
DELETE FROM CLIENTE_CONTACTO;
DELETE FROM PROVEEDOR_CONTACTO;
DELETE FROM CONTACTO;
DELETE FROM CLIENTE;
DELETE FROM PROVEEDOR_PRODUCTO;
DELETE FROM PRODUCTO_MULTIMEDIO;
DELETE FROM PROVEEDOR;
DELETE FROM MULTIMEDIO;
DELETE FROM ALMACEN_PRODUCTO;
DELETE FROM PRODUCTO;
DELETE FROM MARCA;
DELETE FROM LINEA;
DELETE FROM INDUSTRIA;  
-- DELETE FROM USUARIO_PERFIL;
-- DELETE FROM USUARIO;
-- DELETE FROM PERFIL;

-- =================================================================================

-- INSERT INTO PERFIL VALUES ('root'        ,'Super Usuario');
-- INSERT INTO PERFIL VALUES ('pmarlenuser' ,'Usuario Comun');
-- INSERT INTO PERFIL VALUES ('admin' 	     ,'Administrador');
-- INSERT INTO PERFIL VALUES ('finances' 	 ,'Finanzas');
-- INSERT INTO PERFIL VALUES ('stock' 	     ,'Almacen');
-- INSERT INTO PERFIL VALUES ('sales' 	     ,'Vendedor');

-- DELETE FROM USUARIO_PERFIL;
-- DELETE FROM USUARIO;

-- INSERT INTO USUARIO VALUES ('root'			,1,'root'			,'977244cbc826a0f25d07a07f194835b1'	,'dleon@perfumeriamarlen.com.mx',NULL,1);

-- INSERT INTO USUARIO VALUES ('uleon'			,1,'Ulises León'			,'ef6299c9e7fdae6d775819ce1e2620b8'	,'uleon@perfumeriamarlen.com.mx',NULL,2);
-- INSERT INTO USUARIO VALUES ('ecastaneda'	,1,'Elizabath Castañeda'	,'ef6299c9e7fdae6d775819ce1e2620b8'	,'ecastaneda@perfumeriamarlen.com.mx',NULL,2);
-- INSERT INTO USUARIO VALUES ('dleon'			,1,'Daniel León'			,'ef6299c9e7fdae6d775819ce1e2620b8'	,'dleon@perfumeriamarlen.com.mx',NULL,2);
-- INSERT INTO USUARIO VALUES ('hmiranda'		,1,'Hilda Miranda'			,'ef6299c9e7fdae6d775819ce1e2620b8'	,'dleon@perfumeriamarlen.com.mx',NULL,2);
-- INSERT INTO USUARIO VALUES ('aleon'			,1,'Ana León'				,'ef6299c9e7fdae6d775819ce1e2620b8'	,'dleon@perfumeriamarlen.com.mx',NULL,2);
-- INSERT INTO USUARIO VALUES ('dolvera'		,1,'David Olvera'			,'ef6299c9e7fdae6d775819ce1e2620b8'	,'dleon@perfumeriamarlen.com.mx',NULL,2);

-- INSERT INTO USUARIO_PERFIL VALUES ('root'		,'root');
-- INSERT INTO USUARIO_PERFIL VALUES ('root'		,'pmarlenuser');

-- INSERT INTO USUARIO_PERFIL VALUES ('uleon'		,'pmarlenuser');
-- INSERT INTO USUARIO_PERFIL VALUES ('uleon'		,'admin');
-- INSERT INTO USUARIO_PERFIL VALUES ('uleon'		,'stock');
-- INSERT INTO USUARIO_PERFIL VALUES ('uleon'		,'finances');
-- INSERT INTO USUARIO_PERFIL VALUES ('uleon'		,'sales');

-- INSERT INTO USUARIO_PERFIL VALUES ('ecastaneda','pmarlenuser');
-- INSERT INTO USUARIO_PERFIL VALUES ('ecastaneda','admin');
-- INSERT INTO USUARIO_PERFIL VALUES ('ecastaneda','stock');
-- INSERT INTO USUARIO_PERFIL VALUES ('ecastaneda','finances');
-- INSERT INTO USUARIO_PERFIL VALUES ('ecastaneda','sales');

-- INSERT INTO USUARIO_PERFIL VALUES ('dleon'		,'pmarlenuser');
-- INSERT INTO USUARIO_PERFIL VALUES ('dleon'		,'admin');
-- INSERT INTO USUARIO_PERFIL VALUES ('dleon'		,'stock');
-- INSERT INTO USUARIO_PERFIL VALUES ('dleon'		,'sales');

-- INSERT INTO USUARIO_PERFIL VALUES ('hmiranda'	,'pmarlenuser');
-- INSERT INTO USUARIO_PERFIL VALUES ('hmiranda'	,'sales');

-- INSERT INTO USUARIO_PERFIL VALUES ('aleon'		,'pmarlenuser');
-- INSERT INTO USUARIO_PERFIL VALUES ('aleon'		,'sales');
-- INSERT INTO USUARIO_PERFIL VALUES ('aleon'		,'stock');

-- INSERT INTO USUARIO_PERFIL VALUES ('dolvera'	,'pmarlenuser');
-- INSERT INTO USUARIO_PERFIL VALUES ('dolvera'	,'sales');

INSERT INTO PROVEEDOR(ID,RAZON_SOCIAL,RFC,TELEFONOS,TELEFONOS_MOVILES,FAXES,CALLE,NUM_EXTERIOR,NUM_INTERIOR,POBLACION_ID,EMAIL,URL) VALUES(1,'PROVEEDOR 1','PROV010101XXX','(0155)00000001','(0155)00000001','(0155)00000001','CALLE','0','1',100,'xxx1@mail.com','http://127.0.0.1');
INSERT INTO PROVEEDOR(ID,RAZON_SOCIAL,RFC,TELEFONOS,TELEFONOS_MOVILES,FAXES,CALLE,NUM_EXTERIOR,NUM_INTERIOR,POBLACION_ID,EMAIL,URL) VALUES(2,'PROVEEDOR 2','PROV010101XXX','(0155)00000001','(0155)00000001','(0155)00000001','CALLE','0','2',200,'xxx2@mail.com','http://127.0.0.1');
INSERT INTO PROVEEDOR(ID,RAZON_SOCIAL,RFC,TELEFONOS,TELEFONOS_MOVILES,FAXES,CALLE,NUM_EXTERIOR,NUM_INTERIOR,POBLACION_ID,EMAIL,URL) VALUES(3,'PROVEEDOR 3','PROV010101XXX','(0155)00000001','(0155)00000001','(0155)00000001','CALLE','0','3',300,'xxx3@mail.com','http://127.0.0.1');
INSERT INTO PROVEEDOR(ID,RAZON_SOCIAL,RFC,TELEFONOS,TELEFONOS_MOVILES,FAXES,CALLE,NUM_EXTERIOR,NUM_INTERIOR,POBLACION_ID,EMAIL,URL) VALUES(4,'SUPER'      ,'PROV010102XXX','(0155)00000002','(0155)00000003','(0155)00000004','CALLE','0','3',300,'xxx3@mail.com','http://127.0.0.1');
INSERT INTO PROVEEDOR(ID,RAZON_SOCIAL,RFC,TELEFONOS,TELEFONOS_MOVILES,FAXES,CALLE,NUM_EXTERIOR,NUM_INTERIOR,POBLACION_ID,EMAIL,URL) VALUES(5,'COTY'       ,'PROV010103XXX','(0155)00000003','(0155)00000005','(0155)00000005','CALLE','0','3',300,'xxx3@mail.com','http://127.0.0.1');

INSERT INTO CONTACTO(ID,NOMBRE,TELEFONOS,EMAIL) VALUES(1,'CONTACTO 1','(04455)00000001','contacto@mail.com'); 
INSERT INTO PROVEEDOR_CONTACTO(PROVEEDOR_ID,CONTACTO_ID) VALUES(1,1);
INSERT INTO CONTACTO(ID,NOMBRE,TELEFONOS,EMAIL) VALUES(2,'CONTACTO 2','(04455)00000001','contacto@mail.com'); 
INSERT INTO PROVEEDOR_CONTACTO(PROVEEDOR_ID,CONTACTO_ID) VALUES(2,2);
INSERT INTO CONTACTO(ID,NOMBRE,TELEFONOS,EMAIL) VALUES(3,'CONTACTO 3','(04455)00000001','contacto@mail.com'); 
INSERT INTO PROVEEDOR_CONTACTO(PROVEEDOR_ID,CONTACTO_ID) VALUES(3,3);
INSERT INTO CONTACTO(ID,NOMBRE,TELEFONOS,EMAIL) VALUES(104,'CONTACTO 4','(04455)00000004','contactox@mail.com'); 
INSERT INTO PROVEEDOR_CONTACTO(PROVEEDOR_ID,CONTACTO_ID) VALUES(4,104);
INSERT INTO CONTACTO(ID,NOMBRE,TELEFONOS,EMAIL) VALUES(105,'CONTACTO 5','(04455)00000004','contactox@mail.com'); 
INSERT INTO PROVEEDOR_CONTACTO(PROVEEDOR_ID,CONTACTO_ID) VALUES(5,105);

INSERT INTO INDUSTRIA(ID,NOMBRE) VALUES (1,'INDUSTRIA 1');
INSERT INTO INDUSTRIA(ID,NOMBRE) VALUES (2,'INDUSTRIA 2');
INSERT INTO INDUSTRIA(ID,NOMBRE) VALUES (3,'CENTRAL DE ABASTOS');
INSERT INTO INDUSTRIA(ID,NOMBRE) VALUES (4,'ADIDAS');

INSERT INTO LINEA(ID,NOMBRE) VALUES (1,'LINEA 1');
INSERT INTO LINEA(ID,NOMBRE) VALUES (2,'LINEA 2');
INSERT INTO LINEA(ID,NOMBRE) VALUES (3,'LINEA 3');
INSERT INTO LINEA(ID,NOMBRE) VALUES (4,'ABARROTES');
INSERT INTO LINEA(ID,NOMBRE) VALUES (5,'DESODORANTES');

INSERT INTO MARCA(ID,INDUSTRIA_ID,NOMBRE) VALUES (1,1,'MARCA 1');
INSERT INTO MARCA(ID,INDUSTRIA_ID,NOMBRE) VALUES (2,2,'MARCA 2');
INSERT INTO MARCA(ID,INDUSTRIA_ID,NOMBRE) VALUES (3,1,'MARCA 3');
INSERT INTO MARCA(ID,INDUSTRIA_ID,NOMBRE) VALUES (4,2,'MARCA 4');
INSERT INTO MARCA(ID,INDUSTRIA_ID,NOMBRE) VALUES (5,1,'MARCA 5');
INSERT INTO MARCA(ID,INDUSTRIA_ID,NOMBRE) VALUES (6,2,'MARCA 6');
INSERT INTO MARCA(ID,INDUSTRIA_ID,NOMBRE) VALUES (7,3,'VARIOS');
INSERT INTO MARCA(ID,INDUSTRIA_ID,NOMBRE) VALUES (8,4,'ADIDAS');

INSERT INTO PRODUCTO(ID,MARCA_ID,LINEA_ID,NOMBRE,PRESENTACION,CONTENIDO,UNIDADES_POR_CAJA,COSTO,COSTO_VENTA,PRECIO_BASE,UNIDAD_MEDIDA,CODIGO_BARRAS,ABREBIATURA) VALUES(1,1,1,'PRODUCTO 1','P1',95,72, 5.50 ,6.10,7.15,'PZA','7501039700952','PROD');
INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) VALUES(1,1  ,now(),30,60,5.50 ,7.15,'root');
INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,PRECIO_VENTA) VALUES (1,1,60,7.15);
INSERT INTO PROVEEDOR_PRODUCTO(PROVEEDOR_ID,PRODUCTO_ID,PRECIO_COMPRA) VALUES(1,1, 7.97);

INSERT INTO PRODUCTO(ID,MARCA_ID,LINEA_ID,NOMBRE,PRESENTACION,CONTENIDO,UNIDADES_POR_CAJA,COSTO,COSTO_VENTA,PRECIO_BASE,UNIDAD_MEDIDA,CODIGO_BARRAS,ABREBIATURA) VALUES(2,2,1,'PRODUCTO 2','P2',80,72, 4.00 ,4.90,5.20,'PZA','7501039700953','PROD');
INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) VALUES(1,2  ,now(),30,35,4.00 ,5.20,'root');
INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,PRECIO_VENTA) VALUES (1,2,35,5.20);
INSERT INTO PROVEEDOR_PRODUCTO(PROVEEDOR_ID,PRODUCTO_ID,PRECIO_COMPRA) VALUES(2,2, 7.97);

INSERT INTO PRODUCTO(ID,MARCA_ID,LINEA_ID,NOMBRE,PRESENTACION,CONTENIDO,UNIDADES_POR_CAJA,COSTO,COSTO_VENTA,PRECIO_BASE,UNIDAD_MEDIDA,CODIGO_BARRAS,ABREBIATURA) VALUES(3,3,2,'PRODUCTO 3','P3',85,72, 4.60 ,5.90,5.98,'PZA','7501039700954','PROD');
INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) VALUES(1,3  ,now(),30,18,4.60 ,5.98,'root');
INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,PRECIO_VENTA) VALUES (1,3,18,5.98);
INSERT INTO PROVEEDOR_PRODUCTO(PROVEEDOR_ID,PRODUCTO_ID,PRECIO_COMPRA) VALUES(3,3, 18.34);

INSERT INTO PRODUCTO(ID,MARCA_ID,LINEA_ID,NOMBRE,PRESENTACION,CONTENIDO,UNIDADES_POR_CAJA,COSTO,COSTO_VENTA,PRECIO_BASE,UNIDAD_MEDIDA,CODIGO_BARRAS,ABREBIATURA) VALUES(4,4,2,'PRODUCTO 44','P1',95,72, 5.50 ,6.10,7.15,'PZA','7501039700952','PROD');
INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) VALUES(1,4  ,now(),30,24,5.50 ,7.15,'root');
INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,PRECIO_VENTA) VALUES (1,4,24,7.15);
INSERT INTO PROVEEDOR_PRODUCTO(PROVEEDOR_ID,PRODUCTO_ID,PRECIO_COMPRA) VALUES(1,4, 7.97);

INSERT INTO PRODUCTO(ID,MARCA_ID,LINEA_ID,NOMBRE,PRESENTACION,CONTENIDO,UNIDADES_POR_CAJA,COSTO,COSTO_VENTA,PRECIO_BASE,UNIDAD_MEDIDA,CODIGO_BARRAS,ABREBIATURA) VALUES(5,5,3,'PRODUCTO 50','P55',80,72, 4.00 ,1.30,5.20,'PZA','7501039700947','PROD');
INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) VALUES(1,5  ,now(),30,42,4.00 ,5.20,'root');
INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,PRECIO_VENTA) VALUES (1,5,42,5.20);
INSERT INTO PROVEEDOR_PRODUCTO(PROVEEDOR_ID,PRODUCTO_ID,PRECIO_COMPRA) VALUES(2,5, 7.97);

INSERT INTO PRODUCTO(ID,MARCA_ID,LINEA_ID,NOMBRE,PRESENTACION,CONTENIDO,UNIDADES_POR_CAJA,COSTO,COSTO_VENTA,PRECIO_BASE,UNIDAD_MEDIDA,CODIGO_BARRAS,ABREBIATURA) VALUES(6,6,3,'PRODUCTO 606','P60',85,72,0.05, 4.60 ,5.30,'L','3501039700945','PROD');
INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) VALUES(1,6  ,now(),30,50,4.60 ,5.98,'root');
INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,PRECIO_VENTA) VALUES (1,6,50,5.30);
INSERT INTO PROVEEDOR_PRODUCTO(PROVEEDOR_ID,PRODUCTO_ID,PRECIO_COMPRA) VALUES(3,6, 18.34);

INSERT INTO PRODUCTO(ID,MARCA_ID,LINEA_ID,NOMBRE,PRESENTACION,CONTENIDO,UNIDADES_POR_CAJA,COSTO,COSTO_VENTA,PRECIO_BASE,UNIDAD_MEDIDA,CODIGO_BARRAS,ABREBIATURA) VALUES(7,8,5,'Fruity Rhythm','Aerosol Dama 150ml',150,24,19.99,21.80,23.98,'PZA','7795646631024','PROD');
INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) VALUES(1,7  ,now(),30,50,19.99,21.80,'root');
INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,PRECIO_VENTA) VALUES (1,7,50,21.80);
INSERT INTO PROVEEDOR_PRODUCTO(PROVEEDOR_ID,PRODUCTO_ID,PRECIO_COMPRA) VALUES(5,7, 19.99);

INSERT INTO PRODUCTO(ID,MARCA_ID,LINEA_ID,NOMBRE,PRESENTACION,CONTENIDO,UNIDADES_POR_CAJA,COSTO,COSTO_VENTA,PRECIO_BASE,UNIDAD_MEDIDA,CODIGO_BARRAS,ABREBIATURA) VALUES(8,8,5,'Dama Pure Powder','Aerosol Dama 150ml',150,24,19.99,21.80,23.98,'PZA','7795646571023','PROD');
INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) VALUES(1,8  ,now(),30,50,19.99,23.98,'root');
INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,PRECIO_VENTA) VALUES (1,8,50,23.98);
INSERT INTO PROVEEDOR_PRODUCTO(PROVEEDOR_ID,PRODUCTO_ID,PRECIO_COMPRA) VALUES(5,8, 19.99);

INSERT INTO PRODUCTO(ID,MARCA_ID,LINEA_ID,NOMBRE,PRESENTACION,CONTENIDO,UNIDADES_POR_CAJA,COSTO,COSTO_VENTA,PRECIO_BASE,UNIDAD_MEDIDA,CODIGO_BARRAS,ABREBIATURA) VALUES(9,8,5,'24Hrs Ice Dive','Rollon Caballero 50ml',50,24,12.88,14.06,15.46,'PZA','7501737431257','PROD');
INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) VALUES(1,9  ,now(),30,50,12.88,15.46,'root');
INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,PRECIO_VENTA) VALUES (1,9,50,15.46);
INSERT INTO PROVEEDOR_PRODUCTO(PROVEEDOR_ID,PRODUCTO_ID,PRECIO_COMPRA) VALUES(5,9, 12.88);

INSERT INTO PRODUCTO(ID,MARCA_ID,LINEA_ID,NOMBRE,PRESENTACION,CONTENIDO,UNIDADES_POR_CAJA,COSTO,COSTO_VENTA,PRECIO_BASE,UNIDAD_MEDIDA,CODIGO_BARRAS,ABREBIATURA) VALUES(10,8,5,'48 Hrs Ice Dive','Rollon Caballero 50ml',50,24,12.88,14.06,15.46,'PZA','7501737460509','PROD');
INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) VALUES(1,10 ,now(),30,50,12.88,15.46,'root');
INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,PRECIO_VENTA) VALUES (1,10,50,15.46);
INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) VALUES(2,10 ,now(),30,10,12.88,12.20,'root');
INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,PRECIO_VENTA) VALUES (2,10,10,12.20);

INSERT INTO PROVEEDOR_PRODUCTO(PROVEEDOR_ID,PRODUCTO_ID,PRECIO_COMPRA) VALUES(5,10, 12.88);

INSERT INTO PRODUCTO(ID,MARCA_ID,LINEA_ID,NOMBRE,PRESENTACION,CONTENIDO,UNIDADES_POR_CAJA,COSTO,COSTO_VENTA,PRECIO_BASE,UNIDAD_MEDIDA,CODIGO_BARRAS,ABREBIATURA) VALUES(11,8,5,'Action 3 Control','Rollon Caballero 50ml',50,24,12.88,14.06,15.46,'PZA','75030032','PROD');
INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) VALUES(1,11 ,now(),30,50,12.88,15.46,'root');
INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,PRECIO_VENTA) VALUES (1,11,50,15.46);
INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) VALUES(2,11 ,now(),30,10,12.88,13.50,'root');
INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,PRECIO_VENTA) VALUES (2,11,10,13.50);
INSERT INTO PROVEEDOR_PRODUCTO(PROVEEDOR_ID,PRODUCTO_ID,PRECIO_COMPRA) VALUES(5,11, 12.88);

INSERT INTO PRODUCTO(ID,MARCA_ID,LINEA_ID,NOMBRE,PRESENTACION,CONTENIDO,UNIDADES_POR_CAJA,COSTO,COSTO_VENTA,PRECIO_BASE,UNIDAD_MEDIDA,CODIGO_BARRAS,ABREBIATURA) VALUES(12,8,5,'Action 3 Pro Level','Rollon Caballero 50ml',50,24,12.88,14.06,15.46,'PZA','75029999','PROD');
INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) VALUES(1,12 ,now(),30,50,12.88,15.46,'root');
INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,PRECIO_VENTA) VALUES (1,12,50,15.46);
INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) VALUES(2,12 ,now(),30,10,12.88,13.00,'root');
INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,PRECIO_VENTA) VALUES (2,12,10,13.00);

INSERT INTO PROVEEDOR_PRODUCTO(PROVEEDOR_ID,PRODUCTO_ID,PRECIO_COMPRA) VALUES(5,12, 12.88);

INSERT INTO CLIENTE (ID,RFC,FECHA_CREACION,RAZON_SOCIAL,NOMBRE_ESTABLECIMIENTO,CALLE,NUM_INTERIOR,NUM_EXTERIOR,POBLACION_ID,TELEFONOS,FAXES,TELEFONOS_MOVILES,EMAIL,PLAZO_DE_PAGO,URL,OBSERVACIONES,DESCRIPCION_RUTA)VALUES 	(1,'XXXX010101ZZZ',now(),'CLIENTE DE MOSTRADOR',NULL,'CALLE','Ext','Int',100,'58370253,58371099',NULL,NULL,'xxxx@hotmail.com',NULL,NULL,'Precio Real $255.96','X');
INSERT INTO CLIENTE (ID,RFC,FECHA_CREACION,RAZON_SOCIAL,NOMBRE_ESTABLECIMIENTO,CALLE,NUM_INTERIOR,NUM_EXTERIOR,POBLACION_ID,TELEFONOS,FAXES,TELEFONOS_MOVILES,EMAIL,PLAZO_DE_PAGO,URL,OBSERVACIONES,DESCRIPCION_RUTA)VALUES 	(2,'XXXX010102ZZZ',now(),'CLIENTE 2',NULL,'CALLE','Ext','Int',100,'58370253,58371099',NULL,NULL,'xxxx@hotmail.com',NULL,NULL,'xx','X');	
INSERT INTO CLIENTE (ID,RFC,FECHA_CREACION,RAZON_SOCIAL,NOMBRE_ESTABLECIMIENTO,CALLE,NUM_INTERIOR,NUM_EXTERIOR,POBLACION_ID,TELEFONOS,FAXES,TELEFONOS_MOVILES,EMAIL,PLAZO_DE_PAGO,URL,OBSERVACIONES,DESCRIPCION_RUTA)VALUES 	(3,'XXXX010103ZZZ',now(),'CLIENTE 3',NULL,'CALLE','Ext','Int',100,'58370253,58371099',NULL,NULL,'xxxx@hotmail.com',NULL,NULL,'yy','X');	
INSERT INTO CLIENTE (ID,RFC,FECHA_CREACION,RAZON_SOCIAL,NOMBRE_ESTABLECIMIENTO,CALLE,NUM_INTERIOR,NUM_EXTERIOR,POBLACION_ID,TELEFONOS,FAXES,TELEFONOS_MOVILES,EMAIL,PLAZO_DE_PAGO,URL,OBSERVACIONES,DESCRIPCION_RUTA)VALUES 	(4,'SLE120202M92' ,now(),'SERVICIOS DE LOGISTICA INDUSTRIARIAL KENIA','SERVICIOS DE LOGISTICA INDUSTRIARIAL KENIA','20 de Noviembre','MZ 15','LT 4',60816,'59362597',NULL,NULL,'uleon@perfumeriamarlen.com.mx',NULL,NULL,'ULISES LEÓN RESENDIZ','X');	

INSERT INTO CONTACTO(ID,NOMBRE,TELEFONOS,EMAIL) VALUES(4,'CONTACTO 4','(04455)00000001','contacto@mail.com'); 
INSERT INTO CLIENTE_CONTACTO VALUES 	(1,4);
INSERT INTO CONTACTO(ID,NOMBRE,TELEFONOS,EMAIL) VALUES(5,'CONTACTO 5','(04455)00000001','contacto@mail.com'); 
INSERT INTO CLIENTE_CONTACTO VALUES 	(2,5);
INSERT INTO CONTACTO(ID,NOMBRE,TELEFONOS,EMAIL) VALUES(6,'CONTACTO 6','(04455)00000001','contacto@mail.com'); 
INSERT INTO CLIENTE_CONTACTO VALUES 	(3,6);
INSERT INTO CONTACTO(ID,NOMBRE,TELEFONOS,EMAIL) VALUES(7,'ULISES LEÓN RESENDIZ','5936-2597','uleon@perfumeriamarlen.com.mx'); 
INSERT INTO CLIENTE_CONTACTO VALUES 	(4,7);
