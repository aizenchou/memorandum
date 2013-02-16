import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ConnectMySQL {
	private static Connection conn = null;
	public static PreparedStatement statement = null;

	// �������ݿ�
	public static void connSQL() {
		String url = "jdbc:mysql://localhost:3306/men?characterEncoding=UTF-8";
		String username = "date";
		String password = "date"; // ���������������������ݿ�
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, username, password);
			System.out.println("���ݿ����ӳɹ�");
		}
		// ����������������쳣
		catch (ClassNotFoundException cnfex) {
			System.err.println("װ�� JDBC/ODBC ��������ʧ�ܡ�");
			cnfex.printStackTrace();
		}
		// �����������ݿ��쳣
		catch (SQLException sqlex) {
			System.err.println("�޷��������ݿ�");
			sqlex.printStackTrace();
		}
	}

	// �Ͽ�����
	public static void deconnSQL() {
		try {
			if (conn != null)
				conn.close();
			System.out.println("���ݿ�رճɹ�");
		} catch (Exception e) {
			System.out.println("�ر����ݿ����� ��");
			e.printStackTrace();
		}
	}

	// ���ؽ����
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

	// �������ݣ�����Ϊ�ֶ���
	public static boolean insertSQL(String date, String timestart,
			String timeend, String text) {
		String sql = "insert into datetable values('" + date + "','"
				+ timestart + "','" + timeend + "','" + text + "')";
		try {
			statement = conn.prepareStatement(sql);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println("�������ݿ�ʱ����");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("����ʱ����");
			e.printStackTrace();
		}
		return false;
	}

	// ɾ������
	public static boolean deleteSQL(String sql) {
		try {
			statement = conn.prepareStatement(sql);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println("ɾ�����ݿ�ʱ����");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("ɾ��ʱ����");
			e.printStackTrace();
		}
		return false;
	}

	// ��������
	public static boolean updateSQL(String sql) {
		try {
			statement = conn.prepareStatement(sql);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println("�������ݿ�ʱ����");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("����ʱ����");
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

}
