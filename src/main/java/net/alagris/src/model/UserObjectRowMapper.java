package net.alagris.src.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import net.alagris.src.model.object.UserObject;

public class UserObjectRowMapper implements RowMapper<UserObject> {

	private AssertDatabase assertDatabase;

	public UserObjectRowMapper(AssertDatabase assertDatabase) {
		this.assertDatabase = assertDatabase;
	}

	@Override
	public UserObject mapRow(ResultSet rs, int rowNum) throws SQLException {
		if (rs == null)
			return null;
		return new UserObject(rs.getString(assertDatabase.getUsernameFiledName()),
				rs.getBytes(assertDatabase.getPasswordHashFiledName()),
				rs.getBytes(assertDatabase.getPasswordSaltFieldName()));
	}

}
