package net.alagris.src.model.object;

import net.alagris.src.model.AssertDatabase;
import net.alagris.src.model.UserDatabaseQuery;

public class UnporcessedUserObject {

    private String username;
    private String plaintextPassword;

    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }

    public String getPlaintextPassword() {
	return plaintextPassword;
    }

    public void setPlaintextPassword(String plaintextPassword) {
	this.plaintextPassword = plaintextPassword;
    }

    public boolean validate(AssertDatabase assertDatabase, UserDatabaseQuery userDatabaseQuery) {
	if (getUsername() == null || getPlaintextPassword() == null
		|| assertDatabase.getMaxInclusiveUsernameLength() < getUsername().length()
		|| assertDatabase.getMinInclusiveUserPasswordLength() >= getPlaintextPassword().length()
		|| userDatabaseQuery.checkIfTextContainsBannedSqlSpecialCharacters(getUsername())) {
	    return false;
	}
	return false;
    }
}
