import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectMySQL {
	private static Connection conn = null;
	public static PreparedStatement statement = null;

	// connect to MySQL
	public static void connSQL() {
		String url = "jdbc:mysql://localhost:3306/men?characterEncoding=UTF-8";
		String username = "date";
		String password = "date"; // 加载驱动程序以连接数据库
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, username, password);
			System.out.println("数据库连接成功");
		}
		// 捕获加载驱动程序异常
		catch (ClassNotFoundException cnfex) {
			System.err.println("装载 JDBC/ODBC 驱动程序失败。");
			cnfex.printStackTrace();
		}
		// 捕获连接数据库异常
		catch (SQLException sqlex) {
			System.err.println("无法连接数据库");
			sqlex.printStackTrace();
		}
	}

	// disconnect to MySQL
	public static void deconnSQL() {
		try {
			if (conn != null)
				conn.close();
			System.out.println("数据库关闭成功");
		} catch (Exception e) {
			System.out.println("关闭数据库问题 ：");
			e.printStackTrace();
		}
	}

	// execute selection language
	public static ResultSet selectSQL(String sql) {
		ResultSet rs = null;
		try {
			statement = conn.prepareStatement(sql);
			rs = statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	// execute insertion language
	public static boolean insertSQL(String date, String timestart,
			String timeend, String text) {
		String sql = "insert into datetable values('" + date + "','"
				+ timestart + "','" + timeend + "','" + text + "')";
		try {
			statement = conn.prepareStatement(sql);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println("插入数据库时出错：");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("插入时出错：");
			e.printStackTrace();
		}
		return false;
	}

	// execute delete language
	public static boolean deleteSQL(String sql) {
		try {
			statement = conn.prepareStatement(sql);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println("删除数据库时出错：");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("删除时出错：");
			e.printStackTrace();
		}
		return false;
	}

	// execute update language
	public static boolean updateSQL(String sql) {
		try {
			statement = conn.prepareStatement(sql);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println("更新数据库时出错：");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("更新时出错：");
			e.printStackTrace();
		}
		return false;
	}

	void layoutStyle2(ResultSet rs) {
		System.out.println("-----------------");
		System.out.println("执行结果如下所示:");
		System.out.println("-----------------");
		// System.out.println(" 用户ID" + "/t/t" + "淘宝ID" + "/t/t" + "用户名"+ "/t/t"
		// + "密码");
		System.out.println("-----------------");
		try {
			while (rs.next()) {
				System.out.println(rs.getString("date") + "/t/t"
						+ rs.getString("timestart") + "/t/t"
						+ rs.getString("timeend") + "/t/t"
						+ rs.getString("text"));
			}
		} catch (SQLException e) {
			System.out.println("显示时数据库出错。");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("显示出错。");
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {

		ConnectMySQL h = new ConnectMySQL();
		h.connSQL();
		String s = "select * from datetable";

		// String insert =
		// "insert into datetable values('20130214','3333','3333','顶得到地方的发的')";
		String delete = "delete from datetable where date= '20130214'";

		if (h.insertSQL("20130214", "3333", "3333", "水水水水") == true) {
			System.out.println("insert successfully");
			ResultSet resultSet = h.selectSQL(s);
			h.layoutStyle2(resultSet);
		}

		if (h.deleteSQL(delete) == true) {
			System.out.println("delete successfully");
			ResultSet resultSet = h.selectSQL(s);
			h.layoutStyle2(resultSet);
		}

		h.deconnSQL();
	}
}
