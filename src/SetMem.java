import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SetMem extends JFrame implements ActionListener, KeyListener {

	private DateChooser dc;
	private JLabel staLabel, staHourLabel, staMinuteLabel, endLabel,
			endHourLabel, endMinuteLabel;
	private SpinnerNumberModel staHourNumberModel, staMinuteNumberModel,
			endHourNumberModel, endMinuteNumberModel;
	private JSpinner staHourSpinner, staMinuteSpinner, endHourSpinner,
			endMinuteSpinner;
	private JTextArea memTextArea;
	private JButton setButton, cancleButton, deleteButton;
	private JPanel jp1;
	private Font labelFont, buttonFont;

	public SetMem() {
		init();
	}

	public void init() {
		labelFont = new Font("΢���ź�", Font.PLAIN, 15);
		buttonFont = new Font("΢���ź�", Font.PLAIN, 13);
		jp1 = new JPanel(null);
		dc = new DateChooser();
		staLabel = new JLabel("��ʼʱ��");
		staHourLabel = new JLabel("ʱ");
		staMinuteLabel = new JLabel("��");
		endLabel = new JLabel("����ʱ��");
		endHourLabel = new JLabel("ʱ");
		endMinuteLabel = new JLabel("��");
		staHourNumberModel = new SpinnerNumberModel(0, 0, 23, 1);
		staMinuteNumberModel = new SpinnerNumberModel(0, 0, 59, 5);
		endHourNumberModel = new SpinnerNumberModel(0, 0, 23, 1);
		endMinuteNumberModel = new SpinnerNumberModel(0, 0, 59, 5);
		staHourSpinner = new JSpinner(staHourNumberModel);
		staMinuteSpinner = new JSpinner(staMinuteNumberModel);
		endHourSpinner = new JSpinner(endHourNumberModel);
		endMinuteSpinner = new JSpinner(endMinuteNumberModel);
		memTextArea = new JTextArea("�����������뱸��¼��Ϣ");
		memTextArea.setLineWrap(true);
		setButton = new JButton("����");
		cancleButton = new JButton("ȡ��");
		deleteButton = new JButton("ɾ��");
		ChangeListener listener = new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				ResultSet selectedResultSet = ConnectMySQL
						.getSelectedResultSet(dc, staHourSpinner,
								staMinuteSpinner, endHourSpinner,
								endMinuteSpinner);
				try {
					if (selectedResultSet.next()) {
						memTextArea
								.setText(selectedResultSet.getString("text"));
					}
				} catch (SQLException e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				}
			}
		};

		staLabel.setFont(labelFont);
		staHourLabel.setFont(labelFont);
		staMinuteLabel.setFont(labelFont);
		endLabel.setFont(labelFont);
		endHourLabel.setFont(labelFont);
		endMinuteLabel.setFont(labelFont);
		setButton.setFont(buttonFont);
		cancleButton.setFont(buttonFont);
		deleteButton.setFont(buttonFont);

		dc.setBounds(0, 0, 285, 50);

		staLabel.setBounds(0, 60, 75, 25);
		staHourSpinner.setBounds(100, 60, 50, 25);
		staHourLabel.setBounds(165, 60, 25, 25);
		staMinuteSpinner.setBounds(190, 60, 50, 25);
		staMinuteLabel.setBounds(255, 60, 25, 25);

		endLabel.setBounds(0, 90, 75, 25);
		endHourSpinner.setBounds(100, 90, 50, 25);
		endHourLabel.setBounds(165, 90, 25, 25);
		endMinuteSpinner.setBounds(190, 90, 50, 25);
		endMinuteLabel.setBounds(255, 90, 25, 25);

		memTextArea.setBounds(0, 125, 285, 75);

		deleteButton.setBounds(5, 210, 75, 25);
		setButton.setBounds(105, 210, 75, 25);
		cancleButton.setBounds(205, 210, 75, 25);

		setButton.addActionListener(this);
		cancleButton.addActionListener(this);
		deleteButton.addActionListener(this);
		memTextArea.addKeyListener(this);

		jp1.add(dc);
		jp1.add(staLabel);
		jp1.add(staHourSpinner);
		jp1.add(staHourLabel);
		jp1.add(staMinuteSpinner);
		jp1.add(staMinuteLabel);
		jp1.add(endLabel);
		jp1.add(endHourSpinner);
		jp1.add(endHourLabel);
		jp1.add(endMinuteSpinner);
		jp1.add(endMinuteLabel);
		jp1.add(memTextArea);
		jp1.add(setButton);
		jp1.add(cancleButton);
		jp1.add(deleteButton);
		staHourNumberModel.addChangeListener(listener);
		staMinuteNumberModel.addChangeListener(listener);
		endHourNumberModel.addChangeListener(listener);
		endMinuteNumberModel.addChangeListener(listener);
		this.add(jp1);
		this.setTitle("����¼����");
		this.setSize(300, 280);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void set() {
		ConnectMySQL.connSQL();
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
		String selectMem = memTextArea.getText();
		try {
			if (ConnectMySQL.getSelectedResultSet(dc, staHourSpinner,
					staMinuteSpinner, endHourSpinner, endMinuteSpinner).next()) {
				if (ConnectMySQL.updateSQL(selectDate, selectStaTime,
						selectEndTime, selectMem)) {
					JOptionPane.showInternalMessageDialog(
							this.getContentPane(), "�޸����ݳɹ���", "Information:",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "�����޸�ʧ�ܣ�", "Error:",
							JOptionPane.ERROR_MESSAGE);
				}
			}

			else if (ConnectMySQL.insertSQL(selectDate, selectStaTime,
					selectEndTime, selectMem)) {
				JOptionPane.showInternalMessageDialog(this.getContentPane(),
						"�������ݳɹ���", "Information:",
						JOptionPane.INFORMATION_MESSAGE);
				this.setVisible(false);
			} else {
				JOptionPane.showMessageDialog(null, "���ݲ���ʧ�ܣ�", "Error:",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}

	public void delete() {
		ConnectMySQL.connSQL();
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
		if (ConnectMySQL.deleteSQL(selectDate, selectStaTime, selectEndTime)) {
			JOptionPane.showInternalMessageDialog(this.getContentPane(),
					"ɾ�����ݳɹ���", "Information:", JOptionPane.INFORMATION_MESSAGE);
			memTextArea.setText("�����������뱸��¼��Ϣ");
		} else {
			JOptionPane.showMessageDialog(null, "����ɾ��ʧ�ܣ�", "Error:",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO �Զ����ɵķ������
		Object obj = e.getSource();
		if (obj == setButton) {
			set();
		} else if (obj == cancleButton) {
			this.setVisible(false);
		} else if (obj == deleteButton) {
			delete();
		}
	}

	public static void main(String[] args) {
		ConnectMySQL.connSQL();
		new SetMem();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO �Զ����ɵķ������
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO �Զ����ɵķ������
		if (e.getKeyCode() == 10) {
			memTextArea.append("\r");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO �Զ����ɵķ������
	}
}