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
	private JLabel wordLabel = new JLabel("이곳에 단어가 등장합니다.");
   
	//wordLabel은 groundPanel 보다 먼저 생성되어야 함
	private GroundPanel groundPanel = new GroundPanel();
	private WordList wordList = null;
	private ScorePanel scorePanel = null;
   
	public GamePanel(WordList wordList, ScorePanel scorePanel) {
		this.wordList = wordList;
		this.scorePanel = scorePanel;
      
      
		setLayout(new BorderLayout());
		add(new GroundPanel(),BorderLayout.CENTER); //그라운드 패널 중앙에 붙이기
      
		JPanel inputPanel = new JPanel();
		inputPanel.setBackground(Color.GRAY);
		inputPanel.add(inputField);
		inputField.addActionListener(new ActionListener() {

			@Override //엔터 쳤을때 이벤트 발생
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JTextField t = (JTextField)e.getSource();
				//워드 레이블의 단어와 사용자가 입력한 단어가 같다면
				if(t.getText().equals(wordLabel.getText())) {
					scorePanel.increase();
					t.setText(""); //텍스트 다시 공백으로 비우기
					String word = wordList.getWord(); //새로운 단어를 받아서
					setWord(word); // 기존의 단어가 입력된 이후 새로운 단어로 변경
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