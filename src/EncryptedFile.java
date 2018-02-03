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
import java.io.FileNotFoundException;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.KeySpec;
import java.security.AlgorithmParameters;
import java.security.SecureRandom;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.SecretKey;
import java.security.MessageDigest;

/* 
-Encrypting steps-
Insert Data
Set Encryption Method
Encrypt
Write file

-Decrypting steps-
Read file
Set Encryption Method
Decrypt
Return Data
*/

public class EncryptedFile
{
	private static final String ITERATIONS 			= "iterations";
	private static final String ITERATION_VECTOR 	= "iv";
	private static final String SALT				= "salt";
	private static final String CIPHERTEXT 			= "ciphertext";
	
	private static final String UTF_8				= "UTF-8";
	
	public static final String ENCRYPT_MODE	= "encrypt";
	public static final String DECRYPT_MODE	= "decrypt";
	
	private int iterations = 0;
	
	private String salt 			= "";
	private String cipherText 		= "";
	private String encryptMethod 	= "";
	
	private JSONObject jobj = null;
	
	public EncryptedFile(){}
	
	public void encrypt(char [] password, String secret, String filePath)
	{
		JSONObject jo = AES.encrypt(password, secret);
		Utils.writeJSON(filePath, jo);
	}
	
	public String decrypt(char [] password, String filePath)
		throws FileNotFoundException{
		
		JSONObject jo = Utils.readJSON(filePath);
		return AES.decrypt(password, jo);
	}
}