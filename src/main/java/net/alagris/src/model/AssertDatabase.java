package net.alagris.src.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import net.alagris.src.crypto.HashPassword;

public class AssertDatabase {
	@Autowired
	HashPassword hashPassword;
	@Autowired
	JdbcTemplate jdbcTemplate;

	private String databaseName;
	private String usersTableName;
	private String usernameFiledName;
	private String passwordHashFiledName;
	private String passwordSaltFieldName;
	private int maxInclusiveUsernameLength;
	private int minInclusiveUserPasswordLength;

	public int getMaxInclusiveUsernameLength() {
		return maxInclusiveUsernameLength;
	}

	public void setMaxInclusiveUsernameLength(int maxInclusiveUsernameLength) {
		this.maxInclusiveUsernameLength = maxInclusiveUsernameLength;
	}

	public int getMinInclusiveUserPasswordLength() {
		return minInclusiveUserPasswordLength;
	}

	public void setMinInclusiveUserPasswordLength(int minInclusiveUserPasswordLength) {
		this.minInclusiveUserPasswordLength = minInclusiveUserPasswordLength;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getUsersTableName() {
		return usersTableName;
	}

	public void setUsersTableName(String usersTableName) {
		this.usersTableName = usersTableName;
	}

	public String getUsernameFiledName() {
		return usernameFiledName;
	}

	public void setUsernameFiledName(String usernameFiledName) {
		this.usernameFiledName = usernameFiledName;
	}

	public String getPasswordHashFiledName() {
		return passwordHashFiledName;
	}

	public void setPasswordHashFiledName(String passwordHashFiledName) {
		this.passwordHashFiledName = passwordHashFiledName;
	}

	public String getPasswordSaltFieldName() {
		return passwordSaltFieldName;
	}

	public void setPasswordSaltFieldName(String passwordSaltFieldName) {
		this.passwordSaltFieldName = passwordSaltFieldName;
	}

	public void assertDatabase() {
		String mySqlString = "CREATE TABLE IF NOT EXISTS `" + getDatabaseName() + "`.`" + getUsersTableName()
				+ "` (`id` int(11) NOT NULL UNIQUE AUTO_INCREMENT,`" + getUsernameFiledName()
				+ "` NOT NULL UNIQUE varchar(" + getMaxInclusiveUsernameLength() + ") NOT NULL,`"
				+ getPasswordHashFiledName() + "` varbinary(" + hashPassword.getHashLength() + ") NOT NULL,  `"
				+ getPasswordSaltFieldName() + "` varbinary(" + hashPassword.getSaltLength()
				+ ") NOT NULL,PRIMARY KEY (`id`),UNIQUE KEY `id` (`id`));";
		jdbcTemplate.update(mySqlString);
	}

}
