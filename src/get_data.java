import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class get_data {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		try {

			System.out.println("get data");
			Class.forName("org.sqlite.JDBC");
			Connection connection = null;
			connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			Statement statement = connection.createStatement();
			ResultSet rs_api_usage = statement.executeQuery("select API_ID,Usage_Count from [API USAGE]");
			List result_api = new ArrayList();
			List result_count = new ArrayList();
			while (rs_api_usage.next()) {
				result_api.add(rs_api_usage.getString(1) + " ");
				result_count.add(rs_api_usage.getString(2) + " ");
			}
			int num = 0;
			String ret = "";
			while (num < result_api.size()) {
				ResultSet rs_api_search = statement.executeQuery(
						"select Package,Class,Method,Field from [API] WHERE API_ID = " + result_api.get(num));
				while (rs_api_search.next()) {
					for (int i = 1; i <= 4; i++) {
						String tmp = rs_api_search.getString(i);
						if (rs_api_search.wasNull()) {
							continue;
						} else {
							ret += tmp + " ";
						}
					}
				}
				result_api.set(num, ret);
				ret = "";
				num += 1;
			}
			System.out.println(result_api.toString());
			System.out.println(result_count.toString());

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}