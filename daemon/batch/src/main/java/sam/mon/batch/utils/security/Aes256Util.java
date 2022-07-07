package sam.mon.batch.utils.security;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import sam.mon.batch.utils.property.PropertyUtil;

public class Aes256Util {

	private static volatile Aes256Util INSTANCE;	
	
	private static String secretKey; //32bit
	private static String IV; //16bit	
	
	public static Aes256Util getInstance() {
        if (INSTANCE == null) {
            synchronized (Aes256Util.class) {
                if (INSTANCE == null)
                    INSTANCE = new Aes256Util();
            }
        }
        return INSTANCE;
    }	
	
    private Aes256Util() {
    	Properties prod = new Properties();
    	FileInputStream fi = null;
    	
    	try {
    		fi = new FileInputStream(FileUtils.getFile(PropertyUtil.getProperty("ats.encryp.file")));
    		prod.load(fi);    		

        	secretKey = prod.getProperty("ats.encryp.value");
            IV = secretKey.substring(0, 16);
    	}catch(IOException e) {
    		e.printStackTrace();
    	}
    }
	
	// enc
    public String AES_Encode(String str) {
        byte[] keyData = secretKey.getBytes();

        SecretKey secureKey = new SecretKeySpec(keyData, "AES");

        Cipher c;
		try {
			c = Cipher.getInstance("AES/CBC/PKCS5Padding");
	        c.init(Cipher.ENCRYPT_MODE, secureKey, new IvParameterSpec(IV.getBytes()));

	        byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
	        String enStr = new String(Base64.encodeBase64(encrypted));

	        return enStr;
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | 
				InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
    }
    
    // dec
    public String AES_Decode(String str) {
        byte[] keyData = secretKey.getBytes();
        SecretKey secureKey = new SecretKeySpec(keyData, "AES");
        Cipher c;
		try {
			c = Cipher.getInstance("AES/CBC/PKCS5Padding");
	        c.init(Cipher.DECRYPT_MODE, secureKey, new IvParameterSpec(IV.getBytes("UTF-8")));

	        byte[] byteStr = Base64.decodeBase64(str.getBytes());

	        return new String(c.doFinal(byteStr), "UTF-8");
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException 
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
			return null;
		}
    }

    
}
