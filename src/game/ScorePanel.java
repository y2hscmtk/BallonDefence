package game;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScorePanel extends JPanel{
	private int score = 0; //�ʱ� ���� 0��
	private JLabel scoreLabel = new JLabel(Integer.toString(score));
   
	public ScorePanel() {
		setBackground(Color.yellow);
		//���� JPanel�� �÷ο찡 ����Ʈ
		add(new JLabel("����"));
		//JLabel textLabel = new JLabel("����");
		//add(textLabel);
		add(scoreLabel);
	}
   
	// ���ھ��� ������ �ø��� �޼ҵ�
	public void increase() {
		score += 10;
		scoreLabel.setText(Integer.toString(score));
	}

}