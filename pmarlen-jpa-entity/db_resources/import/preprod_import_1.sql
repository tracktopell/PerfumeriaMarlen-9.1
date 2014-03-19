-- ========================== SCRIPT DE IMPORTACION =============================
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

-- =================================================================================

-- ==================== PROVEEDOR: 2 ITEMS
INSERT INTO PROVEEDOR(ID,RAZON_SOCIAL,RFC,TELEFONOS,TELEFONOS_MOVILES,FAXES,CALLE,NUM_EXTERIOR,NUM_INTERIOR,POBLACION_ID,EMAIL,URL) VALUES(1,'PROVEEDORA DE PRODUCTOS Y SERVICIOS GRUPO KAJESKA, S. DE R.L. DE C.V.','KAJE010101XXX','55-1234-5678,55-1234-5678','55-1234-5678,55-1234-5678','55-1234-5678,55-1234-5678','CALLE XX','IN','EX',60180,'nomail@mail.com','http://pagina.com');
INSERT INTO PROVEEDOR(ID,RAZON_SOCIAL,RFC,TELEFONOS,TELEFONOS_MOVILES,FAXES,CALLE,NUM_EXTERIOR,NUM_INTERIOR,POBLACION_ID,EMAIL,URL) VALUES(2,'GP POWERCELL DE MEXICO, S.A. DE C.V.','POWE010101XX1','55-1234-5678,55-1234-5678','55-1234-5678,55-1234-5678','55-1234-5678,55-1234-5678','CALLE XX','IN','EX',60181,'nomail@mail.com','http://pagina.com');
-- ==================== CLIENTE: 2 ITEMS
INSERT INTO CLIENTE(ID,RAZON_SOCIAL,RFC,TELEFONOS,TELEFONOS_MOVILES,FAXES,CALLE,NUM_EXTERIOR,NUM_INTERIOR,POBLACION_ID,EMAIL,URL) VALUES(1,'PROVEEDORA DE PRODUCTOS Y SERVICIOS GRUPO KAJESKA, S. DE R.L. DE C.V.','KAJE010101XXX','55-1234-5678,55-1234-5678','55-1234-5678,55-1234-5678','55-1234-5678,55-1234-5678','CALLE XX','IN','EX',60181,'nomail@mail.com','http://pagina.com');
INSERT INTO CLIENTE(ID,RAZON_SOCIAL,RFC,TELEFONOS,TELEFONOS_MOVILES,FAXES,CALLE,NUM_EXTERIOR,NUM_INTERIOR,POBLACION_ID,EMAIL,URL) VALUES(2,'GP POWERCELL DE MEXICO, S.A. DE C.V.','POWE010101XX1','55-1234-5678,55-1234-5678','55-1234-5678,55-1234-5678','55-1234-5678,55-1234-5678','CALLE XX','IN','EX',60182,'nomail@mail.com','http://pagina.com');
-- ==================== INDUSTRIA: 2 ITEMS
INSERT INTO INDUSTRIA(ID,NOMBRE) VALUES (1,'UNILEVER DE MEXICO, S DE R.L. DE C.V.');
INSERT INTO INDUSTRIA(ID,NOMBRE) VALUES (2,'GP POWERCELL DE MEXICO, S.A. DE C.V.');
-- ==================== LINEA: 3 ITEMS
INSERT INTO LINEA(ID,NOMBRE) VALUES (1, 'DESODORANTES');
INSERT INTO LINEA(ID,NOMBRE) VALUES (2, 'CREMAS');
INSERT INTO LINEA(ID,NOMBRE) VALUES (3, 'PILAS');
-- ==================== MARCA: 5 ITEMS
INSERT INTO MARCA(ID,LINEA_ID,INDUSTRIA_ID,NOMBRE) VALUES (1,1,1, 'DOVE');
INSERT INTO MARCA(ID,LINEA_ID,INDUSTRIA_ID,NOMBRE) VALUES (2,1,1, 'REXONA');
INSERT INTO MARCA(ID,LINEA_ID,INDUSTRIA_ID,NOMBRE) VALUES (3,1,1, 'AXE');
INSERT INTO MARCA(ID,LINEA_ID,INDUSTRIA_ID,NOMBRE) VALUES (4,2,1, 'VASENOL');
INSERT INTO MARCA(ID,LINEA_ID,INDUSTRIA_ID,NOMBRE) VALUES (5,3,2, 'GP POWERCELL');
-- ==================== PRODUCTO: 18 ITEMS
-- ===========PRODUCTO [644]
INSERT INTO PRODUCTO(ID,MARCA_ID,NOMBRE,PRESENTACION,CONTENIDO,UNIDADES_POR_CAJA,COSTO,COSTO_VENTA,PRECIO_BASE,UNIDAD_MEDIDA,CODIGO_BARRAS) VALUES(644,1,'Dove Rollon Dama','Invisible Dry',50,12.0,10.500000,13.715090,19.444950,'ML',7.8924529E7);

INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) VALUES(1,644,now(),30,828,13.715090,19.444950,'root');
INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (1,644,828,0,19.444950);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (2,644,0,0,15.000000);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (3,644,12,0,18.900000);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (4,644,828,0,15.000000);

INSERT INTO PROVEEDOR_PRODUCTO(PROVEEDOR_ID,PRODUCTO_ID,PRECIO_COMPRA) VALUES(1,644, 13.715090);

-- ===========PRODUCTO [645]
INSERT INTO PRODUCTO(ID,MARCA_ID,NOMBRE,PRESENTACION,CONTENIDO,UNIDADES_POR_CAJA,COSTO,COSTO_VENTA,PRECIO_BASE,UNIDAD_MEDIDA,CODIGO_BARRAS) VALUES(645,2,'Rexona Rollon Dama','Active Emotion',50,12.0,10.500000,13.715090,19.444950,'ML',7.8926523E7);

INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) VALUES(1,645,now(),30,2400,13.715090,19.444950,'root');
INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (1,645,2400,0,19.444950);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (2,645,0,0,15.000000);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (3,645,12,0,18.900000);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (4,645,2400,0,15.000000);

INSERT INTO PROVEEDOR_PRODUCTO(PROVEEDOR_ID,PRODUCTO_ID,PRECIO_COMPRA) VALUES(1,645, 13.715090);

-- ===========PRODUCTO [646]
INSERT INTO PRODUCTO(ID,MARCA_ID,NOMBRE,PRESENTACION,CONTENIDO,UNIDADES_POR_CAJA,COSTO,COSTO_VENTA,PRECIO_BASE,UNIDAD_MEDIDA,CODIGO_BARRAS) VALUES(646,2,'Rexona Rollon Dama','Calming',50,12.0,10.500000,13.715090,19.444950,'ML',7.8923348E7);

INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) VALUES(1,646,now(),30,2400,13.715090,19.444950,'root');
INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (1,646,2400,0,19.444950);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (2,646,0,0,15.000000);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (3,646,12,0,18.900000);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (4,646,2400,0,15.000000);

INSERT INTO PROVEEDOR_PRODUCTO(PROVEEDOR_ID,PRODUCTO_ID,PRECIO_COMPRA) VALUES(1,646, 13.715090);

-- ===========PRODUCTO [647]
INSERT INTO PRODUCTO(ID,MARCA_ID,NOMBRE,PRESENTACION,CONTENIDO,UNIDADES_POR_CAJA,COSTO,COSTO_VENTA,PRECIO_BASE,UNIDAD_MEDIDA,CODIGO_BARRAS) VALUES(647,2,'Rexona Rollon Dama','Powder',50,12.0,10.500000,13.715090,19.444950,'ML',7.8924338E7);

INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) VALUES(1,647,now(),30,828,13.715090,19.444950,'root');
INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (1,647,828,0,19.444950);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (2,647,0,0,15.000000);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (3,647,12,0,18.900000);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (4,647,828,0,15.000000);

INSERT INTO PROVEEDOR_PRODUCTO(PROVEEDOR_ID,PRODUCTO_ID,PRECIO_COMPRA) VALUES(1,647, 13.715090);

-- ===========PRODUCTO [648]
INSERT INTO PRODUCTO(ID,MARCA_ID,NOMBRE,PRESENTACION,CONTENIDO,UNIDADES_POR_CAJA,COSTO,COSTO_VENTA,PRECIO_BASE,UNIDAD_MEDIDA,CODIGO_BARRAS) VALUES(648,3,'Axe Rollon Caballero','Sensitive Seco',50,12.0,10.500000,13.715090,19.444950,'ML',7.5030537E7);

INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) VALUES(1,648,now(),30,1200,13.715090,19.444950,'root');
INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (1,648,1200,0,19.444950);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (2,648,0,0,15.000000);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (3,648,12,0,18.900000);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (4,648,1200,0,15.000000);

INSERT INTO PROVEEDOR_PRODUCTO(PROVEEDOR_ID,PRODUCTO_ID,PRECIO_COMPRA) VALUES(1,648, 13.715090);

-- ===========PRODUCTO [649]
INSERT INTO PRODUCTO(ID,MARCA_ID,NOMBRE,PRESENTACION,CONTENIDO,UNIDADES_POR_CAJA,COSTO,COSTO_VENTA,PRECIO_BASE,UNIDAD_MEDIDA,CODIGO_BARRAS) VALUES(649,3,'Axe Rollon Caballero','Dark Temptation',50,12.0,10.500000,13.715090,19.444950,'ML',7.8924574E7);

INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) VALUES(1,649,now(),30,1200,13.715090,19.444950,'root');
INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (1,649,1200,0,19.444950);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (2,649,0,0,15.000000);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (3,649,12,0,18.900000);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (4,649,1200,0,15.000000);

INSERT INTO PROVEEDOR_PRODUCTO(PROVEEDOR_ID,PRODUCTO_ID,PRECIO_COMPRA) VALUES(1,649, 13.715090);

-- ===========PRODUCTO [650]
INSERT INTO PRODUCTO(ID,MARCA_ID,NOMBRE,PRESENTACION,CONTENIDO,UNIDADES_POR_CAJA,COSTO,COSTO_VENTA,PRECIO_BASE,UNIDAD_MEDIDA,CODIGO_BARRAS) VALUES(650,3,'Axe Rollon Caballero','Instinct',50,12.0,10.500000,13.715090,19.444950,'ML',7.8925526E7);

INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) VALUES(1,650,now(),30,1200,13.715090,19.444950,'root');
INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (1,650,1200,0,19.444950);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (2,650,0,0,15.000000);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (3,650,12,0,18.900000);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (4,650,1200,0,15.000000);

INSERT INTO PROVEEDOR_PRODUCTO(PROVEEDOR_ID,PRODUCTO_ID,PRECIO_COMPRA) VALUES(1,650, 13.715090);

-- ===========PRODUCTO [651]
INSERT INTO PRODUCTO(ID,MARCA_ID,NOMBRE,PRESENTACION,CONTENIDO,UNIDADES_POR_CAJA,COSTO,COSTO_VENTA,PRECIO_BASE,UNIDAD_MEDIDA,CODIGO_BARRAS) VALUES(651,3,'Axe Rollon Caballero','Twist',50,12.0,10.500000,13.715090,19.444950,'ML',7.5038618E7);

INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) VALUES(1,651,now(),30,1200,13.715090,19.444950,'root');
INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (1,651,1200,0,19.444950);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (2,651,0,0,15.000000);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (3,651,12,0,18.900000);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (4,651,1200,0,15.000000);

INSERT INTO PROVEEDOR_PRODUCTO(PROVEEDOR_ID,PRODUCTO_ID,PRECIO_COMPRA) VALUES(1,651, 13.715090);

-- ===========PRODUCTO [652]
INSERT INTO PRODUCTO(ID,MARCA_ID,NOMBRE,PRESENTACION,CONTENIDO,UNIDADES_POR_CAJA,COSTO,COSTO_VENTA,PRECIO_BASE,UNIDAD_MEDIDA,CODIGO_BARRAS) VALUES(652,1,'Dove Stick Dama','Go Fresh Limon',50,12.0,15.500000,18.980090,26.909550,'G',7.5027957E7);

INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) VALUES(1,652,now(),30,1200,18.980090,26.909550,'root');
INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (1,652,1200,0,26.909550);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (2,652,0,0,20.000000);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (3,652,12,0,26.910000);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (4,652,1200,0,20.000000);

INSERT INTO PROVEEDOR_PRODUCTO(PROVEEDOR_ID,PRODUCTO_ID,PRECIO_COMPRA) VALUES(1,652, 18.980090);

-- ===========PRODUCTO [653]
INSERT INTO PRODUCTO(ID,MARCA_ID,NOMBRE,PRESENTACION,CONTENIDO,UNIDADES_POR_CAJA,COSTO,COSTO_VENTA,PRECIO_BASE,UNIDAD_MEDIDA,CODIGO_BARRAS) VALUES(653,3,'Axe Stick Caballero','Instinct',50,12.0,15.500000,18.347092,26.012100,'G',7.5028718E7);

INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) VALUES(1,653,now(),30,1440,18.347092,26.012100,'root');
INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (1,653,1440,0,26.012100);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (2,653,0,0,20.000000);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (3,653,12,0,23.900000);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (4,653,1440,0,20.000000);

INSERT INTO PROVEEDOR_PRODUCTO(PROVEEDOR_ID,PRODUCTO_ID,PRECIO_COMPRA) VALUES(1,653, 18.347092);

-- ===========PRODUCTO [654]
INSERT INTO PRODUCTO(ID,MARCA_ID,NOMBRE,PRESENTACION,CONTENIDO,UNIDADES_POR_CAJA,COSTO,COSTO_VENTA,PRECIO_BASE,UNIDAD_MEDIDA,CODIGO_BARRAS) VALUES(654,1,'Dove Stick Caballero','Clean Comfort',50,12.0,15.500000,18.980090,26.909550,'G',7.503106E7);

INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) VALUES(1,654,now(),30,1200,18.980090,26.909550,'root');
INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (1,654,1200,0,26.909550);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (2,654,0,0,20.000000);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (3,654,12,0,26.910000);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (4,654,1200,0,20.000000);

INSERT INTO PROVEEDOR_PRODUCTO(PROVEEDOR_ID,PRODUCTO_ID,PRECIO_COMPRA) VALUES(1,654, 18.980090);

-- ===========PRODUCTO [655]
INSERT INTO PRODUCTO(ID,MARCA_ID,NOMBRE,PRESENTACION,CONTENIDO,UNIDADES_POR_CAJA,COSTO,COSTO_VENTA,PRECIO_BASE,UNIDAD_MEDIDA,CODIGO_BARRAS) VALUES(655,4,'Vasenol Liquida','Humectacion Total',200,12.0,11.500000,13.143527,18.634600,'ML',7.501056339227E12);

INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) VALUES(1,655,now(),30,1200,13.143527,18.634600,'root');
INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (1,655,1200,0,18.634600);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (2,655,0,0,14.500000);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (3,655,12,0,16.900000);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (4,655,1200,0,14.500000);

INSERT INTO PROVEEDOR_PRODUCTO(PROVEEDOR_ID,PRODUCTO_ID,PRECIO_COMPRA) VALUES(1,655, 13.143527);

-- ===========PRODUCTO [656]
INSERT INTO PRODUCTO(ID,MARCA_ID,NOMBRE,PRESENTACION,CONTENIDO,UNIDADES_POR_CAJA,COSTO,COSTO_VENTA,PRECIO_BASE,UNIDAD_MEDIDA,CODIGO_BARRAS) VALUES(656,4,'Vasenol Liquida','Cocoa Butter',200,12.0,11.500000,13.143527,18.634600,'ML',7.5010563388E12);

INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) VALUES(1,656,now(),30,1200,13.143527,18.634600,'root');
INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (1,656,1200,0,18.634600);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (2,656,0,0,14.500000);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (3,656,12,0,16.900000);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (4,656,1200,0,14.500000);

INSERT INTO PROVEEDOR_PRODUCTO(PROVEEDOR_ID,PRODUCTO_ID,PRECIO_COMPRA) VALUES(1,656, 13.143527);

-- ===========PRODUCTO [657]
INSERT INTO PRODUCTO(ID,MARCA_ID,NOMBRE,PRESENTACION,CONTENIDO,UNIDADES_POR_CAJA,COSTO,COSTO_VENTA,PRECIO_BASE,UNIDAD_MEDIDA,CODIGO_BARRAS) VALUES(657,5,'GP PowerCell Battery','Heavy Duty "AA"',5,100.0,4.000000,4.898088,6.944400,'Pz',4.891199103391E12);

INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) VALUES(1,657,now(),30,100,4.898088,6.944400,'root');
INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (1,657,100,0,6.944400);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (2,657,0,0,1.490000);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (3,657,12,0,1.900000);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (4,657,100,0,1.490000);

INSERT INTO PROVEEDOR_PRODUCTO(PROVEEDOR_ID,PRODUCTO_ID,PRECIO_COMPRA) VALUES(2,657, 4.898088);

-- ===========PRODUCTO [658]
INSERT INTO PRODUCTO(ID,MARCA_ID,NOMBRE,PRESENTACION,CONTENIDO,UNIDADES_POR_CAJA,COSTO,COSTO_VENTA,PRECIO_BASE,UNIDAD_MEDIDA,CODIGO_BARRAS) VALUES(658,5,'GP PowerCell Battery','Extra Heavy Duty "AA"',5,100.0,6.000000,6.857492,9.722400,'Pz',4.891199061943E12);

INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) VALUES(1,658,now(),30,100,6.857492,9.722400,'root');
INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (1,658,100,0,9.722400);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (2,658,0,0,1.990000);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (3,658,12,0,2.900000);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (4,658,100,0,1.990000);

INSERT INTO PROVEEDOR_PRODUCTO(PROVEEDOR_ID,PRODUCTO_ID,PRECIO_COMPRA) VALUES(2,658, 6.857492);

-- ===========PRODUCTO [659]
INSERT INTO PRODUCTO(ID,MARCA_ID,NOMBRE,PRESENTACION,CONTENIDO,UNIDADES_POR_CAJA,COSTO,COSTO_VENTA,PRECIO_BASE,UNIDAD_MEDIDA,CODIGO_BARRAS) VALUES(659,5,'GP PowerCell Battery','Super Alkaline "AA"',10,100.0,38.500000,39.288103,55.701800,'Pz',4.891199095436E12);

INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) VALUES(1,659,now(),30,100,39.288103,55.701800,'root');
INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (1,659,100,0,55.701800);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (2,659,0,0,4.990000);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (3,659,12,0,5.900000);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (4,659,100,0,4.990000);

INSERT INTO PROVEEDOR_PRODUCTO(PROVEEDOR_ID,PRODUCTO_ID,PRECIO_COMPRA) VALUES(2,659, 39.288103);

-- ===========PRODUCTO [660]
INSERT INTO PRODUCTO(ID,MARCA_ID,NOMBRE,PRESENTACION,CONTENIDO,UNIDADES_POR_CAJA,COSTO,COSTO_VENTA,PRECIO_BASE,UNIDAD_MEDIDA,CODIGO_BARRAS) VALUES(660,5,'GP PowerCell Battery','Super Alkaline "AAA"',10,100.0,38.500000,40.857672,57.927100,'Pz',4.891199095443E12);

INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) VALUES(1,660,now(),30,100,40.857672,57.927100,'root');
INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (1,660,100,0,57.927100);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (2,660,0,0,5.990000);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (3,660,12,0,6.500000);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (4,660,100,0,5.990000);

INSERT INTO PROVEEDOR_PRODUCTO(PROVEEDOR_ID,PRODUCTO_ID,PRECIO_COMPRA) VALUES(2,660, 40.857672);

-- ===========PRODUCTO [662]
INSERT INTO PRODUCTO(ID,MARCA_ID,NOMBRE,PRESENTACION,CONTENIDO,UNIDADES_POR_CAJA,COSTO,COSTO_VENTA,PRECIO_BASE,UNIDAD_MEDIDA,CODIGO_BARRAS) VALUES(662,5,'GP PowerCell Battery','Super Alkaline "D"',2,100.0,22.500000,22.960580,32.553000,'Pz',4.891199000003E12);

INSERT INTO MOVIMIENTO_HISTORICO_PRODUCTO (ALMACEN_ID,PRODUCTO_ID,FECHA,TIPO_MOVIMIENTO_ID,CANTIDAD,COSTO,PRECIO,USUARIO_ID) VALUES(1,662,now(),30,100,22.960580,32.553000,'root');
INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (1,662,100,0,32.553000);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (2,662,0,0,28.900000);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (3,662,12,0,32.900000);

INSERT INTO ALMACEN_PRODUCTO(ALMACEN_ID,PRODUCTO_ID,CANTIDAD_ACTUAL,CANTIDAD_MINIMA,PRECIO_VENTA) VALUES (4,662,100,0,28.900000);

INSERT INTO PROVEEDOR_PRODUCTO(PROVEEDOR_ID,PRODUCTO_ID,PRECIO_COMPRA) VALUES(2,662, 22.960580);

-- ===========
-- ============================= END EXPORT
