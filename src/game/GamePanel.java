package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;


//게임을 진행하는 공간
//풍선에 달린 단어들이 랜덤하게 하늘에서 떨어지고
//알맞은 텍스트를 입력하면 해당 풍선이 터진다.

//오른쪽에는 사용자가 선택한 캐릭터의 이미지와
//점수, 캐릭터의 체력, 사용중인 무기를 보여주는 패널을 띄우고
//왼쪽에는 게임을 진행하는 패널을 띄운다.

public class GamePanel extends JPanel{
	private GameFrame parent;//부모를 변수로 저장
	
	
	//캐릭터 특성, 무기 특성, 풍선 속도
	private int characterType; //사용자가 선택한 캐릭터가 무엇인지를 저장
	private int characterHealth;//캐릭터의 체력
	private int weaponType; //캐릭터의 무기, 사용자가 사용중인 무기가 무엇인지 보이도록	
	private int weaponPower = 1; // 무기의 데미지, 기본 무기의 능력치는 1
	//풍선이 떨어지는 속도 => 캐릭터 특성에 따라 달라짐
	private int ballonSpeed = 100;//딜레이 되는 시간(밀리초단위) => 200밀리초
	//꾸꾸까까가 선택될경우에 true로 변경
	private boolean luckyChance = false; //꾸꾸꼬꼬의 특성, true라면 일정확률로 풍선을 하나 더 터트림
	
	
	private StatusPanel statusPanel; //오른쪽 창에 띄울 스테이터스창
	private GameRunningPanel gameRunningPanel;
	
//	private JLabel label = new JLabel(); 
//	private String fallingWord = null;
//	private FallingThread thread = null; 
	private boolean gameOn = false;
	
	
//	private WordList wordList; //단어를 관리하기 위한 용도
//	private ControlPanel controlPanel;
	
	
	
	
	//풍선이 떨어지는 스레드
	
	//한 라운드가 끝나면 기존의 풍선들은 모두 제거되어야함
	
	public GamePanel(int characterType,GameFrame parent) {
		this.parent = parent; //프레임의 컨텐트팬을 조작하기위해
		setLayout(null);
//		//this.contentPane = contentPane;
//		this.setBackground(Color.gray);
        setSize(1500,900);
        
		//게임을 생성할때는 캐릭터 정보를 입력받아서 생성
		this.characterType = characterType;
		
		//선택된 캐릭터에 따라 게임난이도 조절
		//0번은 상상부기, 1번은 한성냥이, 3번은 꾸꾸와까까
		
		switch(characterType) {
		case 0:
			characterHealth = 150;
			break;
		case 1: //한성냥이의 경우 => 90의 체력으로 시작한다.
			characterHealth = 90;
			break;
		case 2: //꾸꾸까까의 경우 체력 80으로 시작한다.
			characterHealth = 80;
			break;
		}
		
		
		//위에서 정의된 정보를 바탕으로 스테이터스 창으로 넘김
		
		
//		
//		//왼쪽에 띄울 스테이터스 창 이미지
	
		statusPanel = new StatusPanel(characterType,parent);
		gameRunningPanel = new GameRunningPanel(statusPanel,characterType,parent);
		
		
		//설정된 정보들을 바탕으로 이미지와 정보가 우측에 보이도록
//		
		//테스트 확인용 잘 입력되었나 확인용
		//System.out.println("선택된 캐릭터 : " + characterType + "캐릭터 체력 : " + characterHealth + "풍선 속도: " + ballonSpeed);
		
		//풍선을 랜덤하게 생성하는 스레드 실행
		
		//게임 시작
		makeSplitPane();
		
		
		//풍선이 떨어지게 하는 스레드 실행
		setVisible(true);
	}
	
	//현재 게임의 진행여부
	public boolean isGameOn() {
		return gameOn;
	}
	
	
//	class testPanel extends JPanel{
//		
//		public testPanel() {
//			setSize(900,900);
//			setBackground(Color.cyan);
//			setVisible(true);
//		}
//	}
//	
	
	private void makeSplitPane() {
		setLayout(null); //지정자 없앤다.
		JSplitPane hPane = new JSplitPane();
		hPane.setEnabled(false);
		hPane.setBounds(0,0,1500,900);
		hPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);//방향을 설정 => 어떻게 영역을 분할할지(어떤 방향)
		hPane.setDividerLocation(1000); // 화면을 어디서부터 나눌지 정함
		add(hPane); //가운데에 splitPane 붙이기
		//=> 화면을 두개로 나누어 사용할수 있게 함 => 디바이더
		hPane.setLeftComponent(gameRunningPanel);
		hPane.setRightComponent(statusPanel);
		//오른쪽 부분을 가로로 나눈다?
//		hPane.setLeftComponent(gamePanel);
//	  
//		//점수 패널 붙이기
//	    vPane.setTopComponent(scorePanel);
//	    vPane.setBottomComponent(editPanel);
	}
}
