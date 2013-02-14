import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;


public class Memorandum extends JFrame implements ActionListener {
	private JLabel dateLabel;
	private JPanel timePanel;
	private JLabel displayArea;
	private String DEFAULT_TIME_FORMAT = "HH:mm:ss";
	private String time;
	private int ONE_SECOND = 1000;
	private JButton setMemButton;
	private JButton minimizeButton;

	public Memorandum() {
		timePanel = new JPanel(null);
		displayArea = new JLabel();
		dateLabel = new JLabel();
		setMemButton = new JButton("�༭����¼");
		minimizeButton = new JButton("��С��������");

		configTimeArea();

		dateLabel.setText(getDate());
		dateLabel.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		displayArea.setFont(new Font("TimesRoman", Font.PLAIN, 40));
		minimizeButton.setBounds(150, 100, 130, 30);
		setMemButton.setBounds(5, 100, 130, 30);
		dateLabel.setBounds(0, 0, 300, 50);
		displayArea.setBounds(0, 15, 300, 100);
		dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		displayArea.setHorizontalAlignment(SwingConstants.CENTER);
		setMemButton.addActionListener(this);
		minimizeButton.addActionListener(this);
		timePanel.add(dateLabel);
		timePanel.add(displayArea);
		timePanel.add(setMemButton);
		timePanel.add(minimizeButton);
		this.add(timePanel);
		this.setTitle("��������¼");
		this.setSize(300, 250);
		this.setLocationRelativeTo(null);
		this.setTray();
		Image image1 = Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("/trayIconImage/image.png"));
		setIconImage(image1);
		this.setVisible(true);
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

	// ����������ʾ��1.���жϵ�ǰƽ̨�Ƿ�֧��������ʾ
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
		DateChooser.setmem();
	}
	public void Exit() {
		System.exit(0);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO �Զ����ɵķ������
		Object obj = e.getSource();
		if(obj == setMemButton)
		{
			DateChooser.setmem();
		}
		else if(obj == minimizeButton)
		{
			this.setVisible(false);
		}
	}

	public static void main(String[] args) {
		new Memorandum();
	}

}