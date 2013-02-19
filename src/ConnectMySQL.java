import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.*;

public class ConnectMySQL {
	private static Connection conn = null;
	public static PreparedStatement statement = null;

	// 连接数据库
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

	// 断开连接
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

	// 返回结果集
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

	// 插入数据，参数为字段名
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

	// 删除数据
	public static boolean deleteSQL(String date, String timestart,
			String timeend) {
		String sql = "delete from datetable where date='" + date
				+ "' and timestart='" + timestart + "' and timeend='" + timeend
				+ "'";
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

	// 更新数据,参数为字段名
	public static boolean updateSQL(String date, String timestart,
			String timeend, String text) {
		String sql = "update datetable set text='" + text + "' where date='"
				+ date + "' and timestart='" + timestart + "' and timeend='"
				+ timeend + "'";
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

	public static ResultSet getTodayResultSet() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
		String currentDate = null;
		currentDate = sdfDate.format(Calendar.getInstance().getTime());
		// System.out.println(currentDate);
		ResultSet rs = selectSQL("select * from datetable where date='"
				+ currentDate + "'");
		return rs;
	}

	public static ResultSet getNowResultSet() {
		SimpleDateFormat sdfTime = new SimpleDateFormat("HHmm");
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
		String currentDate = null, currentTime = null;
		currentDate = sdfDate.format(Calendar.getInstance().getTime());
		currentTime = sdfTime.format(Calendar.getInstance().getTime());
		ResultSet rs = selectSQL("select * from datetable where date='"
				+ currentDate + "' and timestart='" + currentTime + "'");
		return rs;
	}

	public static ResultSet getSelectedResultSet(DateChooser dc,
			JSpinner staHourSpinner, JSpinner staMinuteSpinner,
			JSpinner endHourSpinner, JSpinner endMinuteSpinner) {
		String selectDate = dc.sdf2.format(dc.select.getTime());
		String str1 = null, str2 = null, str3 = null, str4 = null;
		if ((Integer) staHourSpinner.getValue() < 10) {
			str1 = '0' + staHourSpinner.getValue().toString();
		} else {
			str1 = staHourSpinner.getValue().toString();
		}
		if ((Integer) staMinuteSpinner.getValue() < 10) {
			str2 = '0' + staMinuteSpinner.getValue().toString();
		} else {
			str2 = staMinuteSpinner.getValue().toString();
		}
		if ((Integer) endHourSpinner.getValue() < 10) {
			str3 = '0' + endHourSpinner.getValue().toString();
		} else {
			str3 = endHourSpinner.getValue().toString();
		}
		if ((Integer) endMinuteSpinner.getValue() < 10) {
			str4 = '0' + endMinuteSpinner.getValue().toString();
		} else {
			str4 = endMinuteSpinner.getValue().toString();
		}
		String selectStaTime = str1 + str2;
		String selectEndTime = str3 + str4;
		ResultSet rs = selectSQL("select * from datetable where date='"
				+ selectDate + "' and timestart='" + selectStaTime
				+ "' and timeend='" + selectEndTime + "'");
		return rs;
	}

}
