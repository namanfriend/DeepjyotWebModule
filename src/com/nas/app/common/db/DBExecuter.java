package com.nas.app.common.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

@Component
public class DBExecuter {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Resource(name="sqlProperties")
    private Properties sqlProperties;
	
	public void executeUpdate(String queryName, Map<String, Object> inputParameters){
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		String SQL = sqlProperties.getProperty(queryName);
		//"UPDATE Employee SET age = :age WHERE empid = :empid"
		SqlParameterSource namedParameters = new MapSqlParameterSource(inputParameters);
		namedParameterJdbcTemplate.update(SQL, namedParameters);		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List executeQuery(String queryName, Map<String, Object> inputParameters, RowMapper rowMapper){
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		String SQL = sqlProperties.getProperty(queryName);
		//"UPDATE Employee SET age = :age WHERE empid = :empid"
		SqlParameterSource namedParameters = new MapSqlParameterSource(inputParameters);
		return namedParameterJdbcTemplate.query(SQL, namedParameters, rowMapper);		
	}
	
	public List executeQuery(String queryName, RowMapper rowMapper){
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		String SQL = sqlProperties.getProperty(queryName);
		//"UPDATE Employee SET age = :age WHERE empid = :empid"
		return namedParameterJdbcTemplate.query(SQL, rowMapper);		
	}
	
	public Object executeStoredProcedure(String storedProcedureName, Map<String, Object> inputParameters){
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate);
		simpleJdbcCall.withProcedureName(storedProcedureName).withoutProcedureColumnMetaDataAccess();
		if(inputParameters != null){
			for(String inputkey : inputParameters.keySet()){
				Object inputValue = inputParameters.get(inputkey);
				int dbType = Types.VARCHAR;
				if(inputValue instanceof String){
					dbType = Types.VARCHAR;
				} else if(inputValue instanceof Integer || inputValue instanceof Long || inputValue instanceof Double){
					dbType = Types.NUMERIC;
				} else if(inputValue instanceof Boolean){
					dbType = Types.BOOLEAN;
				} else if(inputValue instanceof Date){
					dbType = Types.DATE;
				}
				simpleJdbcCall.addDeclaredParameter(new SqlParameter(dbType, inputkey));
			}
		}
		return null;
	}
	
	
	public static void main(String[] args) {
		Connection c = null;
	   Statement stmt = null;

	      
	      try {
	         Class.forName("org.sqlite.JDBC");
	         c = DriverManager.getConnection("jdbc:sqlite:J://DipjyotWorkspace//DB//DJ.db");
	         
	         stmt = c.createStatement();
	         ResultSet rs = stmt.executeQuery( "SELECT * FROM SUBSCRIPTION;" );
	         
	         while ( rs.next() ) {
	            int id = rs.getInt("id");
	            String  name = rs.getString("name");
	            int age  = rs.getInt("age");
	            String  address = rs.getString("address");
	            float salary = rs.getFloat("salary");
	            // [ID, SUB_ID, NAME, ADDRESS_LINE1, ADDRESS_LINE2, ADDRESS_LINE3, CITY, STATE, PINCODE, SUB_DATE, TENURE, SUB_END_DATE]
	            System.out.println( "ID = " + id );
	            System.out.println( "NAME = " + name );
	            System.out.println( "AGE = " + age );
	            System.out.println( "ADDRESS = " + address );
	            System.out.println( "SALARY = " + salary );
	            System.out.println();
	         }
	         rs.close();
	         stmt.close();
	         c.close();
	         
	         
	      } catch ( Exception e ) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	      }
	      System.out.println("Opened database successfully");
	   }

}
