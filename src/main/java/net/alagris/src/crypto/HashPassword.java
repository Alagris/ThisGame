package net.alagris.src.crypto;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HashPassword {

	private int iterations;
	/** Length of salt in bytes */
	private int saltLength;
	/** Length of hash in bytes */
	private int hashLength;
	private static final Logger log = LoggerFactory.getLogger(HashPassword.class);
	private static final SecretKeyFactory SECRET_KEY_FACTORY;
	private static final SecureRandom SECURE_RANDOM;

	static {
		final String KEY_FACTORY_TYPE = "PBKDF2WithHmacSHA1";
		SecretKeyFactory skf = null;
		try {
			skf = SecretKeyFactory.getInstance(KEY_FACTORY_TYPE);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			log.error("==============!!SECURITY ERROR!!==============");
			log.error("Key factory instance invalid: " + KEY_FACTORY_TYPE);
			log.error("==============!!SECURITY ERROR!!==============");
		}
		SECRET_KEY_FACTORY = skf;
	}

	static {
		final String SECURE_RANDOM_TYPE = "SHA1PRNG";
		SecureRandom sr = null;
		try {
			sr = SecureRandom.getInstance(SECURE_RANDOM_TYPE);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			log.error("==============!!SECURITY ERROR!!==============");
			log.error("SecureRandom instance invalid: " + SECURE_RANDOM_TYPE);
			log.error("==============!!SECURITY ERROR!!==============");
		}
		SECURE_RANDOM = sr;
	}

	public int getIterations() {
		return iterations;
	}

	public void setIterations(int iterations) {
		this.iterations = iterations;
	}

	/** Length of salt in bytes */
	public int getSaltLength() {
		return saltLength;
	}

	/** Length of salt in bytes */
	public void setSaltLength(int saltLength) {
		this.saltLength = saltLength;
	}

	/** Length of hash in bytes */
	public int getHashLength() {
		return hashLength;
	}

	/** Length of hash in bytes */
	public void setHashLength(int keyLength) {
		this.hashLength = keyLength;
	}

	public byte[] generateStrongPasswordHash(String password, byte[] salt) throws InvalidKeySpecException {

		PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations,
				hashLength * 8/**
								 * it seems the parameter is passed in bits so
								 * that's why i have to multiply by 8
								 */
		);
		return SECRET_KEY_FACTORY.generateSecret(spec).getEncoded();
	}

	public byte[] getSalt() {
		byte[] salt = new byte[saltLength];
		SECURE_RANDOM.nextBytes(salt);
		return salt;
	}

}
