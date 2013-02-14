import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private JSpinner staHourSpinner, staMinuteSpinner, endHourSpinner, endMinuteSpinner;
	private JTextArea memTextArea;
	private JButton setButton, cancleButton;
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		
	}
	
}