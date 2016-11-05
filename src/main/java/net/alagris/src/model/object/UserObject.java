package net.alagris.src.model.object;

import java.security.spec.InvalidKeySpecException;

import net.alagris.src.crypto.HashPassword;

public class UserObject {
    private final String username;
    private final byte[] hash;
    private final byte[] salt;

    public UserObject(String username, byte[] hash, byte[] salt) {
	this.salt = salt;
	this.hash = hash;
	this.username = username;
    }

    public String getUsername() {
	return username;
    }

    public byte[] getHash() {
	return hash;
    }

    public byte[] getSalt() {
	return salt;
    }

    public boolean comparePasswords(String anotherPassword, HashPassword hashPassword) throws InvalidKeySpecException {
	byte[] pass = hashPassword.generateStrongPasswordHash(anotherPassword, getSalt());
	for (int i = 1; i < pass.length; i++) {
	    if (pass[i] != getHash()[i])
		return false;
	}
	return true;
    }
}
