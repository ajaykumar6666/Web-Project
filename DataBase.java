package dao;

import java.sql.Connection;
import oracle.jdbc.pool.OracleDataSource;

public class DataBase {
	public static OracleDataSource ods;
	static
	{
		try {
			ods=new OracleDataSource();
			ods.setDriverType("thin");
			ods.setURL("jdbc:oracle:thin:@localhost:1521:xe");
			ods.setUser("diary");
			ods.setPassword("dd");
		}
		catch(Exception e)
		{
			System.out.println("Error in static init block:"+e.getMessage());
		}
	}
	public static Connection getConnection() throws Exception
	{
		return ods.getConnection();
	}

}
