package me.k1llerk4se.libs;

import me.k1llerk4se.TheHiddenBot;
import me.k1llerk4se.command.CommandBase;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;

/**
 * Created by mikel on 12/5/2014.
 */

public abstract class SQLiteListener extends CommandBase {
public Connection c = null;
public SQLiteListener(){
	setupDB();
}
public abstract void setupDB();
public void openConnection(String db){
	if((boolean) TheHiddenBot.conf.get("useSQLite")){
		try{
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:" +db);
		}catch (Exception e){
			e.printStackTrace();
		}
	}else{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String sql = "jdbc:mysql://"+TheHiddenBot.conf.get("MySQLHost") + "/" + TheHiddenBot.conf.get("MySQLTable")+ "?user="+TheHiddenBot.conf.get("MySQLUserName")+"&password="+TheHiddenBot.conf.get("MySQLPassword");
			c = DriverManager.getConnection(sql);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
public boolean existsInDatabase(String SQLiteDB, String tableToLookIn, Object thingToBeChecked) throws SQLException {
	openConnection(SQLiteDB);
	String sql = "SELECT * FROM `"+tableToLookIn+"` WHERE `USER`=?";
	PreparedStatement preparedStatement;
	ResultSet resultSet;
	preparedStatement = c.prepareStatement(sql);
	preparedStatement.setObject(1, thingToBeChecked);
	resultSet = preparedStatement.executeQuery();
	if (!resultSet.next())
		return false;
	resultSet.close();
	preparedStatement.close();
	closeConnection();
	return true;
}
public void updateCache(String db, String table, HashMap list, String key, String value) throws SQLException {
	openConnection(db);
	try{
		ResultSet result;
		String sql = "SELECT * FROM `"+table+"`";
		PreparedStatement preparedStatement = c.prepareStatement(sql);
		result = preparedStatement.executeQuery();
		while(result.next()){
			list.put(result.getString(key), result.getString(value));
		}
		closeConnection();
		result.close();
		preparedStatement.close();
	}catch(Exception e){
		e.printStackTrace();
	}
}
public void closeConnection(){
	try {
		c.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}
public static String getCurrentTimeStamp() {
	return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
}
}