import java.applet.AudioClip;
import java.io.*;
import java.applet.Applet;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Clip extends Frame {
	public Clip() {
		try {
			URL cb;
			File f = new File(
					"E:\\Java Workspace\\test130210-2 �����ļ�����(����·��)\\src\\sound\\msg.wav");
			cb = f.toURL();
			AudioClip aau;
			aau = Applet.newAudioClip(cb);
			aau.loop();
			ConnectMySQL.connSQL();
			ResultSet rs = ConnectMySQL.getNowResultSet();
			while (rs.next()) {
				String title1 = rs.getString("timestart");
				String title2 = rs.getString("timeend");
				JOptionPane.showConfirmDialog(
						null,
						rs.getString("text"),
						insertStr(title1, 2, "��") + "�� �� "
								+ insertStr(title2, 2, "��") + "�� �ı�����Ϣ",
						JOptionPane.YES_NO_OPTION);
			}
			aau.stop();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (HeadlessException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}

	public static String insertStr(String str1, int i, String insert) {
		StringBuilder tempStringBuilder = new StringBuilder(str1);
		tempStringBuilder.insert(i, insert);
		String tempString = tempStringBuilder.toString();
		return tempString;
	}

	public static void main(String[] args) {
		new Clip();
	}
}
