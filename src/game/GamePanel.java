package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GamePanel extends JPanel {
	private JTextField inputField = new JTextField(20);
	private JLabel wordLabel = new JLabel("�̰��� �ܾ �����մϴ�.");
   
	//wordLabel�� groundPanel ���� ���� �����Ǿ�� ��
	private GroundPanel groundPanel = new GroundPanel();
	private WordList wordList = null;
	private ScorePanel scorePanel = null;
   
	public GamePanel(WordList wordList, ScorePanel scorePanel) {
		this.wordList = wordList;
		this.scorePanel = scorePanel;
      
      
		setLayout(new BorderLayout());
		add(new GroundPanel(),BorderLayout.CENTER); //�׶��� �г� �߾ӿ� ���̱�
      
		JPanel inputPanel = new JPanel();
		inputPanel.setBackground(Color.GRAY);
		inputPanel.add(inputField);
		inputField.addActionListener(new ActionListener() {

			@Override //���� ������ �̺�Ʈ �߻�
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JTextField t = (JTextField)e.getSource();
				//���� ���̺��� �ܾ�� ����ڰ� �Է��� �ܾ ���ٸ�
				if(t.getText().equals(wordLabel.getText())) {
					scorePanel.increase();
					t.setText(""); //�ؽ�Ʈ �ٽ� �������� ����
					String word = wordList.getWord(); //���ο� �ܾ �޾Ƽ�
					setWord(word); // ������ �ܾ �Էµ� ���� ���ο� �ܾ�� ����
				}
			}
         
		});
		inputPanel.add(inputField,BorderLayout.SOUTH);
		add(inputPanel,BorderLayout.SOUTH);
	}
   
   
	public void setWord(String word) {
		wordLabel.setText(word);
	}
	
	class GroundPanel extends JPanel{
		public GroundPanel() {
			add(wordLabel);
		}
	}
}