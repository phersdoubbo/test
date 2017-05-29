//
// SPSBlowfishCodec created by alexpod on  May 21, 2004
//

package com.scholastic.sysbo;

import java.security.Provider;
import java.security.Security;
import java.util.Hashtable;
import java.util.Map;
import java.util.ResourceBundle;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;





public class SchBlowfishCodec {
	private static final String DEFAULT_CRYPTO_PADDING = "Blowfish/ECB/PKCS5Padding";
	private static final String CODEC_CONFIG = "SchBlowfishCodecConfig";
	private static final String SECURE_CONFIG_PROVIDER = "SchBlowfishSec";
	//private static final Logger myLogger = Logger.getLogger("crypto");
	
	private static final Map instances = new Hashtable();

	// following are specific to each instance
	private SecretKey key = null;

	private Cipher encryptCipher = null;

	private Cipher decryptCipher = null;

	// above are specific to each instance

	public static SchBlowfishCodec singleton() {
		return instance(null);
	}
	public static SchBlowfishCodec singleton(boolean isSecure) {
		return isSecure?instance(SECURE_CONFIG_PROVIDER):instance(null);
	}

	public static SchBlowfishCodec instance(String configProvider) {
		if (SchStringUtils.isEmpty(configProvider))
			configProvider = "SchBlowfish";
		
		Object instance = instances.get(configProvider);
		if (instance == null) {
			instance = createInstance(configProvider);
			instances.put(configProvider, instance);
		}
		if (instance instanceof SchBlowfishCodec)
			return (SchBlowfishCodec) instance;
		return null;
	}

	public String decodeText(String in) {
		try {
			byte[] bytes = decode(Base64.decode(in));
			return bytes != null ? new String(bytes) : null;
		} catch (Exception ex) {
			// myLogger.error("Exception decoding..., ERROR: " + ex);
			return null;
		}
	}

	public String encodeText(String in) {
		try {
			byte[] bytes = encode(in.getBytes());
			return bytes != null ? Base64.encodeBytes(bytes) : null;
		} catch (Exception ex) {
			// myLogger.error("Exception encoding..., ERROR: " + ex);
			return null;
		}
	}

	private SchBlowfishCodec() {
		super();
		Provider sunJce = new com.sun.crypto.provider.SunJCE();
		Security.addProvider(sunJce);
	}

	private static Object createInstance(String configProvider) {
		try {
			ProviderProps providerProps = loadProviderProps(configProvider);

			SchBlowfishCodec newCodec = new SchBlowfishCodec();

			javax.crypto.spec.IvParameterSpec iv = null;
			if(!SchStringUtils.isEmpty(providerProps.ivVal))
				iv = new javax.crypto.spec.IvParameterSpec(providerProps.ivVal.getBytes());
			
			newCodec.key = new SecretKeySpec(providerProps.key.getBytes(), "Blowfish");
			
			newCodec.encryptCipher = Cipher.getInstance(providerProps.paddingType);
			if(iv != null)
				newCodec.encryptCipher.init(Cipher.ENCRYPT_MODE, newCodec.key, iv);
			else
				newCodec.encryptCipher.init(Cipher.ENCRYPT_MODE, newCodec.key);
				

			newCodec.decryptCipher = Cipher.getInstance(providerProps.paddingType);
			if(iv != null)
				newCodec.decryptCipher.init(Cipher.DECRYPT_MODE, newCodec.key, iv);
			else
				newCodec.decryptCipher.init(Cipher.DECRYPT_MODE, newCodec.key);
				
			return newCodec;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Object();
	}

	// loads (possibly encrypted) key
	private static ProviderProps loadProviderProps(String configProvider) {
		ProviderProps providerProps = new ProviderProps();
		try {
			ResourceBundle bundle = ResourceBundle.getBundle(CODEC_CONFIG);
			providerProps.key = bundle.getString(configProvider + ".CodecKey");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!SchStringUtils.isEmpty(providerProps.key)) {
			try {
				Cipher cipher = Cipher.getInstance(DEFAULT_CRYPTO_PADDING);
				cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(
						"INTERNAL_key88".getBytes(), "Blowfish"));
				providerProps.key = new String(cipher.doFinal(Base64.decode(providerProps.key.trim() + '\n')));
			} catch (Exception e) {
				// e.printStackTrace();
			}
		}
		try {
			ResourceBundle bundle = ResourceBundle.getBundle(CODEC_CONFIG);
			providerProps.ivVal = bundle.getString(configProvider + ".ivVal");
		} catch (Exception e) {}
		try {
			ResourceBundle bundle = ResourceBundle.getBundle(CODEC_CONFIG);
			providerProps.paddingType = bundle.getString(configProvider + ".paddingType");
		} catch (Exception e) {
			providerProps.paddingType = DEFAULT_CRYPTO_PADDING;
		}
		return providerProps;
	}

	private static class ProviderProps{
		String key = null;
		String paddingType = DEFAULT_CRYPTO_PADDING;
		String ivVal = null; 
	}
	
	private byte[] decode(byte[] ciphertext) throws Exception {
		byte[] cleartext = decryptCipher.doFinal(ciphertext);
		return cleartext;
	}

	private byte[] encode(byte[] ciphertext) throws Exception {
		return encryptCipher.doFinal(ciphertext);
	}

	

	private static String encodeKey(String keyString) throws Exception {
		Cipher cipher = Cipher.getInstance(DEFAULT_CRYPTO_PADDING);
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec("INTERNAL_key88"
				.getBytes(), "Blowfish"));

		return Base64.encodeBytes(cipher.doFinal(keyString.getBytes()));
	}

}
