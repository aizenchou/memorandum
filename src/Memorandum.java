import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.*;

public class Memorandum extends JFrame implements ActionListener {
	private JLabel dateLabel;
	private JPanel timePanel;
	private JLabel displayArea;
	private String DEFAULT_TIME_FORMAT = "HH:mm:ss";
	public SimpleDateFormat sdfTime = new SimpleDateFormat("HHmm");
	public SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
	public SimpleDateFormat sdfSecond = new SimpleDateFormat("ss");
	private String time;
	private int ONE_SECOND = 1000;
	private JButton setMemButton;
	private JButton minimizeButton;
	private Font buttonFont = new Font("΢���ź�", Font.PLAIN, 13);
	private Font dateFont = new Font("΢���ź�", Font.PLAIN, 16);
	private Font timeFont = new Font("TimesRoman", Font.PLAIN, 40);
	private Font borderFont = new Font("΢���ź�", Font.PLAIN, 15);
	private TitledBorder todayMemBorder;
	private JPanel memPanel;
	public ScrollPane tableScrollPanel;
	public JTable memTable;
	String[] Names = { "��ʼʱ��", "��ֹʱ��", "������Ϣ" };
	Object[][] playerInfo = null;

	public ResultSet todayResultSet, nowResultSet;

	public Memorandum() {
		init();
	}

	public void init() {
		timePanel = new JPanel(null);
		memPanel = new JPanel();
		displayArea = new JLabel();
		dateLabel = new JLabel();
		setMemButton = new JButton("�༭����¼");
		minimizeButton = new JButton("��С��������");
		todayMemBorder = new TitledBorder("���ձ���");
		DefaultTableModel model = new DefaultTableModel(playerInfo, Names) {

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		memTable = new JTable(model);
		memTable.setPreferredScrollableViewportSize(new Dimension(270, 245));
		JScrollPane scrollPane = new JScrollPane(memTable);
		memTable.setDefaultRenderer(Object.class,
				new TableCellTextAreaRenderer()); // �����Զ���������
		/*
		 * DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		 * r.setHorizontalAlignment(JLabel.CENTER);
		 * memTable.setDefaultRenderer(Object.class, r); //ʹ�����У����Ḳ�ǻ�������
		 */
		configTimeArea();
		configTable();

		dateLabel.setText(getDate());
		dateLabel.setFont(dateFont);
		displayArea.setFont(timeFont);
		todayMemBorder.setTitleFont(borderFont);
		minimizeButton.setFont(buttonFont);
		setMemButton.setFont(buttonFont);
		minimizeButton.setBounds(150, 100, 130, 30);
		setMemButton.setBounds(5, 100, 130, 30);
		dateLabel.setBounds(0, 0, 300, 50);
		displayArea.setBounds(0, 15, 300, 100);
		memPanel.setBounds(0, 140, 285, 300);
		memTable.setBounds(5, 20, 275, 275);
		dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		displayArea.setHorizontalAlignment(SwingConstants.CENTER);
		setMemButton.addActionListener(this);
		minimizeButton.addActionListener(this);
		timePanel.add(dateLabel);
		timePanel.add(displayArea);
		timePanel.add(setMemButton);
		timePanel.add(minimizeButton);
		memPanel.setBorder(todayMemBorder);
		memPanel.add(scrollPane, BorderLayout.CENTER);
		timePanel.add(memPanel);
		this.add(timePanel);
		this.setTitle("��������¼");
		this.setSize(300, 480);
		this.setLocationRelativeTo(null);
		this.setTray();
		Image image1 = Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("/trayIconImage/image.png"));
		setIconImage(image1);
		this.setVisible(true);
		// this.setResizable(false); //ʹ���ڲ��ܸı��С
	}

	private void configTimeArea() {
		Timer tmr = new Timer();
		tmr.scheduleAtFixedRate(new JLabelTimerTask(), new Date(), ONE_SECOND);
	}

	protected class JLabelTimerTask extends TimerTask {
		SimpleDateFormat dateFormatter = new SimpleDateFormat(
				DEFAULT_TIME_FORMAT);

		@Override
		public void run() {
			time = dateFormatter.format(Calendar.getInstance().getTime());
			displayArea.setText(time);
			remind();
			updateTable();
		}
	}

	public String getDate() {
		int y, m, d;
		Calendar cal = Calendar.getInstance();
		y = cal.get(Calendar.YEAR);
		m = cal.get(Calendar.MONTH);
		d = cal.get(Calendar.DATE);
		return y + "��" + m + "��" + d + "��";
	}

	public void remind() {
		String currentSecond = sdfSecond.format(
				Calendar.getInstance().getTime()).toString();
		nowResultSet = ConnectMySQL.getNowResultSet();
		// System.out.println(currentSecond);
		if (currentSecond.equals("00")) {
			try {
				if (nowResultSet.next()) {
					new Clip();
				} else {
				}
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
	}

	private void configTable() {
		TableColumn firsetColumn = memTable.getColumnModel().getColumn(0);
		TableColumn secondColumn = memTable.getColumnModel().getColumn(1);
		firsetColumn.setPreferredWidth(55);
		firsetColumn.setMaxWidth(55);
		firsetColumn.setMinWidth(55);
		secondColumn.setPreferredWidth(55);
		secondColumn.setMaxWidth(55);
		secondColumn.setMinWidth(55);
	}

	public void updateTable() {
		String currentSecond = sdfSecond.format(
				Calendar.getInstance().getTime()).toString();

		// System.out.println(currentSecond);
		if (currentSecond.equals("00")) {
			updateTableRightNow();
		}
	}

	public void updateTableRightNow() {
		todayResultSet = ConnectMySQL.getTodayResultSet();
		try {
			while (todayResultSet.next()) {
				DefaultTableModel tableModel = (DefaultTableModel) memTable
						.getModel();
				String timesta = todayResultSet.getString("timestart");
				String timeend = todayResultSet.getString("timeend");
				String memtext = todayResultSet.getString("text");
				tableModel.addRow(new Object[] {
						Clip.insertStr(timesta, 2, ":"),
						Clip.insertStr(timeend, 2, ":"), memtext });
			}
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}

	// ���������ʾ��1.���жϵ�ǰƽ̨�Ƿ�֧��������ʾ
	public void setTray() {

		if (SystemTray.isSupported()) {// �жϵ�ǰƽ̨�Ƿ�֧�����̹���
			// ��������ʵ��
			SystemTray tray = SystemTray.getSystemTray();
			// ��������ͼ�꣺1.��ʾͼ��Image 2.ͣ����ʾtext 3.�����˵�popupMenu 4.��������ͼ��ʵ��
			// 1.����Imageͼ��
			Image image = Toolkit.getDefaultToolkit().getImage(
					getClass().getResource("/trayIconImage/clientIcon.gif"));
			// 2.ͣ����ʾtext
			String text = "��������¼";
			// 3.�����˵�popupMenu
			PopupMenu popMenu = new PopupMenu();
			MenuItem itmOpen = new MenuItem("��");
			itmOpen.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Show();
				}
			});
			MenuItem itmHide = new MenuItem("����");
			itmHide.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					UnVisible();
				}
			});
			MenuItem itmExit = new MenuItem("�˳�");
			itmExit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Exit();
				}
			});
			MenuItem itmEdit = new MenuItem("�༭");
			itmEdit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Edit();
				}
			});
			popMenu.add(itmOpen);
			popMenu.add(itmHide);
			popMenu.add(itmEdit);
			popMenu.add(itmExit);

			// ��������ͼ��
			TrayIcon trayIcon = new TrayIcon(image, text, popMenu);
			// ������ͼ��ӵ�������
			try {
				tray.add(trayIcon);
			} catch (AWTException e1) {
				e1.printStackTrace();
			}
		}
	}

	// �ڲ����в���ֱ�ӵ����ⲿ�ࣨthis����ָ��
	public void UnVisible() {
		this.setVisible(false);
	}

	public void Show() {
		this.setVisible(true);
	}

	public void Edit() {
		new SetMem();
	}

	public void Exit() {
		ConnectMySQL.deconnSQL();
		System.exit(0);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO �Զ����ɵķ������
		Object obj = e.getSource();
		if (obj == setMemButton) {
			Edit();
		} else if (obj == minimizeButton) {
			UnVisible();
		}
	}

	public static void main(String[] args) {
		ConnectMySQL.connSQL();
		Memorandum memorandum = new Memorandum();
	}

}