package game;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatusPanel extends JPanel{
	//캐릭터들의 이미지 정보
	private ImageIcon sangsangBugi = new ImageIcon("character0.png");
    private ImageIcon hansungNyangI = new ImageIcon("character1.png");
    private ImageIcon kkukkuKkakka = new ImageIcon("character2.png");
	private ImageIcon selectedCharacater;
	
	private int score = 0; //점수를 저장할 변수
	
	//선택한 캐릭터가 무엇인지에 대한 정보
	//캐릭터의 체력 정보
	//캐릭터의 무기 정보, 무기들 이미지
	//캐릭터 점수 정보
	//코인 정보(얼마를 벌었는지)
    
	
	public StatusPanel(int characterType) {

		setLayout(null);
		setSize(500,900);
		
		System.out.println("스테이터스 생성자 호출");
		setBackground(Color.cyan);
		//캐릭터 선택정보를 토대로 띄울 이미지 결정
		switch(characterType) {
		case 0:
			selectedCharacater = sangsangBugi;
			break;
		case 1:
			selectedCharacater = hansungNyangI;
			break;
		case 2:
			selectedCharacater = kkukkuKkakka;
			break;
		}
		
		JLabel character = new JLabel(selectedCharacater);
		character.setSize(selectedCharacater.getIconWidth(),selectedCharacater.getIconHeight());
		character.setLocation(65,140);
		add(character);
		
		setVisible(true);
	}
	
	//점수를 추가하는 메소드
	public void plusScore(int pScore) {
		score += pScore; //점수 추가
		System.out.println("현재 점수 : "+score);
	}	
	
	
}
