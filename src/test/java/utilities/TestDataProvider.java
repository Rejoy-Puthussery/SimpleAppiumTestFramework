package utilities;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.testng.annotations.DataProvider;

public class TestDataProvider {
	@DataProvider(name = "data-provider")
	 public Object[][] dataProvider(Method m){
		List<HashMap<String, Object>> testDataArray;
		Object[][] testDataObjects = null;
		try {
			testDataArray =  readData(m.getName());
			testDataObjects = new Object[testDataArray.size()][1];
			for (HashMap<String, Object> data : testDataArray){
				testDataObjects[testDataArray.indexOf(data)][0] = data;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			return testDataObjects;
		}
	 }
	
	private List<HashMap<String, Object>> readData(String testMethodName) throws Exception {
		String testDataLocSql = "SELECT testDataTableName, testDataId FROM " +  Tools.getProperty("db_testCaseDataColumns_table_name") + " WHERE testMethodName = \"" + testMethodName +"\"";
		ResultSet testDataLoc_rs = runSqlQuery(testDataLocSql);
		List<HashMap<String, Object>> testDataObjects = new ArrayList<>();
		while(testDataLoc_rs.next()) {
			ResultSet rs = runSqlQuery("SELECT * FROM " +  testDataLoc_rs.getObject(1)  +  " WHERE id = " + testDataLoc_rs.getObject(2));
			ResultSetMetaData rs_md = rs.getMetaData();
			int rs_columnCount = rs_md.getColumnCount();
			while(rs.next()) {
				HashMap<String, Object> row = new HashMap<>(rs_columnCount);
				for(int i=1; i<=rs_columnCount; ++i){
					row.put(rs_md.getColumnName(i),rs.getObject(i));
				}
				testDataObjects.add(row);
			}
		}
		return testDataObjects;
	}
	
	private ResultSet runSqlQuery(String sql) throws Exception {
		Connection db_connection;
		Statement stmt;
		ResultSet rs;
		db_connection = DataBaseConnector.connectDB(Tools.getProperty("db_url"));
		stmt = db_connection.createStatement();
		rs = stmt.executeQuery(sql);
		return rs;
	}

}
