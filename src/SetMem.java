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
        private JPanel jp1;
	private JFrame jf;
	
	public SetMem()
	{
		jf = new JFrame();
		jp1 = new JPanel(null);
		dc = new DateChooser();
		staLabel = new JLabel("��ʼʱ��");
		staHourLabel = new JLabel("ʱ");
		staMinuteLabel = new JLabel("��");
		endLabel = new JLabel("����ʱ��");
		endHourLabel = new JLabel("ʱ");
		endMinuteLabel = new JLabel("��");
		staHourSpinner = new JSpinner();
		
		
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO �Զ����ɵķ������
		
	}
	
}