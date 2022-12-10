package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

//게임을 진행하는 공간
//풍선에 달린 단어들이 랜덤하게 하늘에서 떨어지고
//알맞은 텍스트를 입력하면 해당 풍선이 터진다.

//오른쪽에는 사용자가 선택한 캐릭터의 이미지와
//점수, 캐릭터의 체력, 사용중인 무기를 보여주는 패널을 띄우고
//왼쪽에는 게임을 진행하는 패널을 띄운다.
public class GamePanel extends JPanel{
	//이미지 정보
	private ImageIcon bgImageicon = new ImageIcon("gamePanelBackgroundImage.png");
    private Image gamePanelBackgroundImage = bgImageicon.getImage();
	//캐릭터 특성, 무기 특성, 풍선 속도
	private int characterType; //사용자가 선택한 캐릭터가 무엇인지를 저장
	private int characterHealth;//캐릭터의 체력
	private int weaponType; //캐릭터의 무기, 사용자가 사용중인 무기가 무엇인지 보이도록	
	private int weaponPower = 1; // 무기의 데미지, 기본 무기의 능력치는 1
	//풍선이 떨어지는 속도 => 캐릭터 특성에 따라 달라짐
	private int ballonSpeed = 10;//시간당 10px씩 내려가는것이 기본속도
	//꾸꾸까까가 선택될경우에 true로 변경
	private boolean luckyChance = false; //꾸꾸꼬꼬의 특성, true라면 일정확률로 풍선을 하나 더 터트림
	
	//풍선이 떨어지는 스레드
	
	//한 라운드가 끝나면 기존의 풍선들은 모두 제거되어야함
	
	public GamePanel(int characterType) {
		setLayout(null); //풍선이 떨어지는 위치를 랜덤으로 지정하기 위해
		this.setBackground(Color.gray);
        setSize(1500,900);
		
		//게임을 생성할때는 캐릭터 정보를 입력받아서 생성
		this.characterType = characterType;
		
		//선택된 캐릭터에 따라 게임난이도 조절
		//0번은 상상부기, 1번은 한성냥이, 3번은 꾸꾸와까까
		switch(characterType) {
		case 0:
			characterHealth = 150; //상상부기의 캐릭터 특성; 체력이 많다(단단하다)
			break;
		case 1:
			characterHealth = 90;
			ballonSpeed = 7; //한성냥이의 캐릭터 특성; 풍선을 느리게떨어진다(동체시력)
			break;
		case 2:
			characterHealth = 80;
			luckyChance = true; //꾸꾸까까의 캐릭터 특성; 일정확률~
			break;
		}
		
		//테스트 확인용 잘 입력되었나 확인용
		System.out.println("선택된 캐릭터 : " + characterType + "캐릭터 체력 : " + characterHealth + "풍선 속도: " + ballonSpeed);
		//풍선을 랜덤하게 생성하는 스레드 실행
		
		//풍선이 떨어지게 하는 스레드 실행
		setVisible(true);
	}
	
	@Override
    public void paintComponent(Graphics g) {
       super.paintComponent(g); //그래픽 컴포넌트 설정
       //배경 이미지
       g.drawImage(gamePanelBackgroundImage, 0, 0, this.getWidth(),this.getHeight(),null); //이미지가 그려지는 시점 알림받지 않기
    }
	
}
