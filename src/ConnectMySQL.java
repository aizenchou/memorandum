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

	// disconnect to MySQL
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
			System.out.println("�������ݿ�ʱ����");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("����ʱ����");
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
			System.out.println("ɾ�����ݿ�ʱ����");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("ɾ��ʱ����");
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
			System.out.println("�������ݿ�ʱ����");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("����ʱ����");
			e.printStackTrace();
		}
		return false;
	}

	void layoutStyle2(ResultSet rs) {
		System.out.println("-----------------");
		System.out.println("ִ�н��������ʾ:");
		System.out.println("-----------------");
		// System.out.println(" �û�ID" + "/t/t" + "�Ա�ID" + "/t/t" + "�û���"+ "/t/t"
		// + "����");
		System.out.println("-----------------");
		try {
			while (rs.next()) {
				System.out.println(rs.getString("date") + "/t/t"
						+ rs.getString("timestart") + "/t/t"
						+ rs.getString("timeend") + "/t/t"
						+ rs.getString("text"));
			}
		} catch (SQLException e) {
			System.out.println("��ʾʱ���ݿ����");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("��ʾ����");
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {

		ConnectMySQL h = new ConnectMySQL();
		h.connSQL();
		String s = "select * from datetable";

		// String insert =
		// "insert into datetable values('20130214','3333','3333','���õ��ط��ķ���')";
		String delete = "delete from datetable where date= '20130214'";

		if (h.insertSQL("20130214", "3333", "3333", "ˮˮˮˮ") == true) {
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
