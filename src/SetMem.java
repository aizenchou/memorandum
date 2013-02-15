import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class SetMem extends JFrame implements ActionListener
{

	private DateChooser dc;
	private JLabel staLabel, staHourLabel, staMinuteLabel, endLabel, endHourLabel, endMinuteLabel;
        private SpinnerNumberModel staHourNumberModel, staMinuteNumberModel, endHourNumberModel, endMinuteNumberModel;
	private JSpinner staHourSpinner, staMinuteSpinner, endHourSpinner, endMinuteSpinner;
	private JTextArea memTextArea;
	private JButton setButton, cancleButton;
        private JPanel jp1;
        private Font labelFont, buttonFont;
	
        public SetMem()
	{
		init();
	}
	public void init()
	{
		labelFont = new Font("微软雅黑", Font.PLAIN, 15);
		buttonFont = new Font("微软雅黑", Font.PLAIN, 13);
		jp1 = new JPanel(null);
		dc = new DateChooser();
		staLabel = new JLabel("开始时间");
		staHourLabel = new JLabel("时");
		staMinuteLabel = new JLabel("分");
		endLabel = new JLabel("结束时间");
		endHourLabel = new JLabel("时");
		endMinuteLabel = new JLabel("分");
		staHourNumberModel = new SpinnerNumberModel(0, 0, 23, 1);
		staMinuteNumberModel = new SpinnerNumberModel(0, 0, 59, 5);
		endHourNumberModel = new SpinnerNumberModel(0, 0, 23, 1);
		endMinuteNumberModel = new SpinnerNumberModel(0, 0, 59, 5);
		staHourSpinner = new JSpinner(staHourNumberModel);
		staMinuteSpinner = new JSpinner(staMinuteNumberModel);
		endHourSpinner = new JSpinner(endHourNumberModel);
		endMinuteSpinner = new JSpinner(endMinuteNumberModel);
		memTextArea = new JTextArea("在这里输入备忘录信心");
		memTextArea.setLineWrap(true);
		setButton = new JButton("设置");
		cancleButton = new JButton("取消");
		
		staLabel.setFont(labelFont);
		staHourLabel.setFont(labelFont);
		staMinuteLabel.setFont(labelFont);
		endLabel.setFont(labelFont);
		endHourLabel.setFont(labelFont);
		endMinuteLabel.setFont(labelFont);
		setButton.setFont(buttonFont);
		cancleButton.setFont(buttonFont);
		
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
		
		setButton.setBounds(100, 210, 75, 25);
		cancleButton.setBounds(200, 210, 75, 25);
		
		setButton.addActionListener(this);
		cancleButton.addActionListener(this);
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
		this.add(jp1);
		this.setTitle("备忘录设置");
		this.setSize(300, 280);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	
	}

        public void set()
	{
		ConnectMySQL.connSQL();
		String selectDate = dc.sdf2.format(dc.select.getTime());
		String selectStaTime = staHourSpinner.getValue().toString()+staMinuteSpinner.getValue().toString();
		String selectEndTime = endHourSpinner.getValue().toString()+endMinuteSpinner.getValue().toString();
		String selectMem = memTextArea.getText();
		ConnectMySQL.insertSQL(selectDate, selectStaTime, selectEndTime, selectMem);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		Object obj = e.getSource();
		if (obj == setButton) {
		    set();
		}
		else if (obj == cancleButton) {
			this.setVisible(false);
		} 
	}
	public static void main(String[] args)
	{
		new SetMem();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO 自动生成的方法存根
		if(e.getKeyCode()==10)
		{
			memTextArea.append("\r");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO 自动生成的方法存根
		
	}
}