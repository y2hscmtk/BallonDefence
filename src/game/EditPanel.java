package game;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EditPanel extends JPanel {

	private JTextField tf = new JTextField(20);
	private JButton saveBtn = new JButton("save");
   
	public EditPanel() {
      //텍스트 입력창을 만들어야함
		add(tf);
		add(saveBtn);
	}
}