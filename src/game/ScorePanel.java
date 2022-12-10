package game;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScorePanel extends JPanel{
	private int score = 0; //초기 점수 0점
	private JLabel scoreLabel = new JLabel(Integer.toString(score));
   
	public ScorePanel() {
		setBackground(Color.yellow);
		//원래 JPanel은 플로우가 디폴트
		add(new JLabel("점수"));
		//JLabel textLabel = new JLabel("점수");
		//add(textLabel);
		add(scoreLabel);
	}
   
	// 스코어의 점수를 올리는 메소드
	public void increase() {
		score += 10;
		scoreLabel.setText(Integer.toString(score));
	}

}