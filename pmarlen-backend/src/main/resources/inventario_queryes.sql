SELECT S.ID SUCURSAL_ID,A.ID ALMACEN_ID,A.TIPO_ALMACEN,P.ID,P.CODIGO_BARRAS,P.NOMBRE,P.PRESENTACION,M.NOMBRE MARCA_NOMBRE,I.NOMBRE INDUSTRIA_NOMBRE,L.NOMBRE LINEA_NOMBRE,AP.CANTIDAD_ACTUAL,AP.PRECIO_VENTA,AP.COSTO,AP.PRECIO_MAYOREO
FROM   PRODUCTO P,MARCA M, INDUSTRIA I,LINEA L, ALMACEN_PRODUCTO AP,ALMACEN A,SUCURSAL S
WHERE  1=1
AND    P.MARCA_ID = M.ID
AND    M.INDUSTRIA_ID = I.ID
AND    M.LINEA_ID = L.ID
AND    P.ID       = AP.PRODUCTO_ID
AND    AP.ALMACEN_ID = A.ID
AND    A.SUCURSAL_ID = S.ID
AND    S.ID = 1
AND    A.TIPO_ALMACEN=1
ORDER  BY MARCA_NOMBRE;