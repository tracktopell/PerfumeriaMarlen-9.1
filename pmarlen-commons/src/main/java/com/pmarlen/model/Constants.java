
package com.pmarlen.model;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Constants
 */
public class Constants {

    public static final String PERFIL_ROOT = "root";
    public static final String PERFIL_PMARLENUSER = "pmarlenuser";
    public static final String PERFIL_ADMIN = "admin";
    public static final String PERFIL_FINANCES = "finances";
    public static final String PERFIL_STOCK = "stock";
    public static final String PERFIL_SALES = "sales";
	
    public static final int CREATE_ACTION = 1;
    public static final int UPDATE_ACTION = 2;
    
    public static final int ESTADO_CAPTURADO			= 1;
    public static final int ESTADO_SINCRONIZADO			= 2;
    public static final int ESTADO_VERIFICADO			= 4;  
    public static final int ESTADO_SURTIDO				= 8;
    public static final int ESTADO_FACTURADO			= 16;
	public static final int ESTADO_REMISIONADO			= 32;
    public static final int ESTADO_ENVIADO				= 64;
    public static final int ESTADO_ENTREGADO			= 128;
    public static final int ESTADO_DEVUELTO				= 256;	
	public static final int ESTADO_VENDIDO_SUCURSAL		= 512;
	public static final int ESTADO_FACTURADO_SUCURSAL	= 1024;
	public static final int ESTADO_DEVUELTO_SUCURSAL		= 2048;
	public static final int ESTADO_PAGADO				= 4096;
	
    public static final int ESTADO_CANCELADO				= 65536;
    //--------------------------------------------------------------------------
	
	
    public static final int TIPO_MOV_CREACION			= 10;
    public static final int TIPO_MOV_ENTRADA_ALMACEN	= 20;
	public static final int TIPO_MOV_ENTRADA_ALMACEN_DEV= 21;
    public static final int TIPO_MOV_SALIDA_ALMACEN		= 30;
	public static final int TIPO_MOV_SALIDA_DEV			= 31;
    public static final int TIPO_MOV_MODIFICACION_COSTO_O_PRECIO	 = 50;
	public static final int TIPO_MOV_MODIFICACION_CODIGOBARRAS		 = 51;
	public static final int TIPO_MOV_MODIFICACION_NOMBRE_DESCRIPCION = 52;
	
	public static final int ALMACEN_LINEA       = 1;
    public static final int ALMACEN_OPORTUNIDAD = 2;
	public static final int ALMACEN_REGALIAS    = 3;
	
    private static final String VERSION_FILE_RESOURCE = "/com/tracktopell/util/version/file/Version.properties";

    private static Logger logger = LoggerFactory.getLogger(Constants.class);

    public static String getServerVersion() {
        Properties pro = new Properties();
        String version = null;
        try {
            InputStream resourceAsStream = Constants.class.getResourceAsStream(VERSION_FILE_RESOURCE);
            if(resourceAsStream == null){
                throw new IOException("The resource:"+VERSION_FILE_RESOURCE+" doesn't exist!");
            }
            pro.load(resourceAsStream);
            logger.info("->version=" + pro);
//            version = pro.getProperty("version.major") + "."
//                    + pro.getProperty("version.minor") + "@"
//                    + pro.getProperty("version.revision");
            version = pro.getProperty("version.major") + "."
                    + pro.getProperty("version.minor") + "."
                    + pro.getProperty("version.timestamp");

        } catch (IOException ex) {
            logger.error("Can't load Version properties:", ex);
            version = "-.-.-";
        }
        return version;
    }
	
	public static String getMD5Encrypted(String e) {

        MessageDigest mdEnc = null; // Encryption algorithm
        try {
            mdEnc = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            return null;
        }
        mdEnc.update(e.getBytes(), 0, e.length());
        return (new BigInteger(1, mdEnc.digest())).toString(16);
    }
	
	public static void main(String[] args) {
		String str[]={
			"l3onuli5es",
			"ca5taneda3li",
			"l3ondan1el",
			"m1randah1lda",
			"l3onan4",
			"0lveradav1d",
			"temporal",
			"wallhalla",
			"mgpaulin13"
		};
		for(String s:str){
			//System.out.println("md5("+s+")="+Constants.getMD5Encrypted(s));
			System.out.println(Constants.getMD5Encrypted(s));
		}
		
	}

	public static Object getDescripcionTipoAlmacen(int tipoAlmacen) {
		if(tipoAlmacen == ALMACEN_LINEA){
			return "LINEA";
		} else if(tipoAlmacen == ALMACEN_OPORTUNIDAD){
			return "OPORTUNIDAD";
		} else if(tipoAlmacen == ALMACEN_REGALIAS){
			return "REGALIAS";
		} else {
			return null;
		}
	}
}
