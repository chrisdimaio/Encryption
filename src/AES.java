package com;

import org.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import java.util.Arrays;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.lang.StringBuffer;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.KeySpec;
import java.security.AlgorithmParameters;
import java.security.SecureRandom;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.SecretKey;
import javax.crypto.BadPaddingException;
import java.security.MessageDigest;
import javax.swing.JOptionPane;
import java.io.FileNotFoundException;

public class AES
{
	private static final String ITERATION_VECTOR_K 	= "iv";
	private static final String SALT_K				= "salt";
	private static final String CIPHERTEXT_K 		= "ciphertext";
	private static final String KEY_ALGORITHM_K		= "keyalgorithm";
	private static final String KEY_ITERATIONS_K 	= "iterations";
	private static final String ENCRYPTION_METHOD_K	= "encryptionmethod";
	private static final String KEY_LENGTH_K		= "keylength";
	
	private static final String KEY_ALGORITHM_V		= "PBKDF2WithHmacSHA1";
	private static final String ENCRYPTION_METHOD_V	= "AES/CBC/PKCS5Padding";
	private static final int KEY_ITERATIONS_V		= 65536;
	private static final int KEY_LENGTH_V			= 128;
	
	private static final String UTF_8 = "UTF-8";
	
	@Deprecated
	public static String decrypt(String password, JSONObject obj)
	{
		return decrypt(password.toCharArray(), obj);
	}
	
	public static String decrypt(char[] password, JSONObject obj)
	{		
		try
		{
			Base64 base64 = new Base64();
			
			byte salt[] 		= base64.decodeBase64((String)obj.get(SALT_K));
			byte iv[] 			= base64.decodeBase64((String)obj.get(ITERATION_VECTOR_K));
			byte cipherText[] 	= base64.decodeBase64((String)obj.get(CIPHERTEXT_K));
		
			// Create key.
			SecretKeyFactory factory = SecretKeyFactory.getInstance(KEY_ALGORITHM_V);
			KeySpec spec = new PBEKeySpec(password, salt, KEY_ITERATIONS_V, KEY_LENGTH_V);
			SecretKey tmpKey = factory.generateSecret(spec);
			SecretKey key = new SecretKeySpec(tmpKey.getEncoded(), "AES");
		
			Cipher cipher = Cipher.getInstance(ENCRYPTION_METHOD_V);
			cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
			String plaintext = new String(cipher.doFinal(cipherText), UTF_8);
			
			return plaintext;
		}
		catch(BadPaddingException bpe)
		{
			JOptionPane.showMessageDialog(null, "Incorrect password or corrupt data", "Access Denied", JOptionPane.ERROR_MESSAGE);
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e.toString(), e.getMessage(), JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}
	
	@Deprecated
	public static JSONObject encrypt(String password, String secret)
	{
		return encrypt(password.toCharArray(), secret);
	}
	
	public static JSONObject encrypt(char[] password, String secret)
	{
		SecureRandom random = new SecureRandom();
		byte salt[] = new byte[16];
		random.nextBytes(salt);
		
		try
		{
			Base64 base64 = new Base64();

			// Create key.
			SecretKeyFactory factory = SecretKeyFactory.getInstance(KEY_ALGORITHM_V);
			KeySpec spec = new PBEKeySpec(password, salt, KEY_ITERATIONS_V, KEY_LENGTH_V);
			SecretKey tmpKey = factory.generateSecret(spec);
			SecretKey key = new SecretKeySpec(tmpKey.getEncoded(), "AES");
		
			// Create cipher and iv
			Cipher cipher = Cipher.getInstance(ENCRYPTION_METHOD_V);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			AlgorithmParameters params = cipher.getParameters();
			byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
			byte[] cipherText = cipher.doFinal(secret.getBytes(UTF_8));
			
			JSONObject encryptedOBJ = new JSONObject();
			
			encryptedOBJ.put(SALT_K, base64.encodeBase64String(salt));
			encryptedOBJ.put(ITERATION_VECTOR_K, base64.encodeBase64String(iv));
			encryptedOBJ.put(CIPHERTEXT_K, base64.encodeBase64String(cipherText));
			encryptedOBJ.put(KEY_ITERATIONS_K, KEY_ITERATIONS_V);
			encryptedOBJ.put(KEY_ALGORITHM_K, KEY_ALGORITHM_V);
			encryptedOBJ.put(ENCRYPTION_METHOD_K, ENCRYPTION_METHOD_V);
			encryptedOBJ.put(KEY_LENGTH_K, KEY_LENGTH_V);
			
			
			return encryptedOBJ;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}