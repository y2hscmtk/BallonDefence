package game;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EditPanel extends JPanel {

	private JTextField tf = new JTextField(20);
	private JButton saveBtn = new JButton("save");
   
	public EditPanel() {
      //�ؽ�Ʈ �Է�â�� ��������
		add(tf);
		add(saveBtn);
	}
}