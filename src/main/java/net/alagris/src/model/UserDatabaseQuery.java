package net.alagris.src.model;

import java.security.spec.InvalidKeySpecException;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import net.alagris.src.crypto.HashPassword;
import net.alagris.src.model.object.UnporcessedUserObject;
import net.alagris.src.model.object.UserObject;

public class UserDatabaseQuery {

    @Autowired
    HashPassword hashPassword;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    AssertDatabase assertDatabase;

    public UserObject adduser(UnporcessedUserObject unporcessedUserObject) throws InvalidKeySpecException {
	byte[] salt = hashPassword.getSalt();
	byte[] pass = hashPassword.generateStrongPasswordHash(unporcessedUserObject.getPlaintextPassword(), salt);
	UserObject userObject = new UserObject(unporcessedUserObject.getUsername(), pass, salt);
	String mySqlString = "INSERT INTO `" + assertDatabase.getDatabaseName() + "`.`"
		+ assertDatabase.getUsersTableName() + "` (`" + assertDatabase.getUsernameFiledName() + "`, `"
		+ assertDatabase.getPasswordHashFiledName() + "`,`" + assertDatabase.getPasswordSaltFieldName()
		+ "`) VALUES (?,?,?);";
	jdbcTemplate.update(mySqlString, unporcessedUserObject.getUsername(), pass, salt);
	return userObject;
    }

    public UserObject getUserById(int id) {
	String mySqlString = "SELECT * FROM `" + assertDatabase.getDatabaseName() + "`.`"
		+ assertDatabase.getUsersTableName() + "` WHERE `id` = " + id + ";";
	return jdbcTemplate.queryForObject(mySqlString, new UserObjectRowMapper(assertDatabase));
    }

    public UserObject getUserByName(String name) {
	String mySqlString = "SELECT * FROM `" + assertDatabase.getDatabaseName() + "`.`"
		+ assertDatabase.getUsersTableName() + "` WHERE `" + assertDatabase.getUsernameFiledName() + "` = "
		+ name + ";";
	return jdbcTemplate.queryForObject(mySqlString, new UserObjectRowMapper(assertDatabase));
    }

    public boolean checkIfTextContainsBannedSqlSpecialCharacters(String text) {
	Pattern pattern = Pattern.compile("[\\s';`\"\'()]");
	return pattern.matcher(text).find();
    }
}
