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

//텍스트를 입력받아 올바른 단어인지 확인하는 메소드
//텍스트창을 관리한다.
class ControlPanel extends JPanel {
	private GamePanel gamePanel;
	private JTextField input = new JTextField(15); //단어을 입력받을 공간 설정
	
	public ControlPanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		this.setLayout(new FlowLayout());
		this.setBackground(Color.LIGHT_GRAY);
		add(input);
		
		input.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JTextField tf = (JTextField)e.getSource();
				String text = tf.getText(); //텍스트에 입력된 단어가 무엇인지 가져옴
				if(text.equals("exit")) //exit입력시 종료
					System.exit(0); //프로그램 종료
				
				if(!gamePanel.isGameOn()) //엑션 무시
					return;
					
				boolean match = gamePanel.matchWord(text); //입력한 단어가 올바른 단어인지 확인
				
				if(match) {
					gamePanel.stopGame(); //단어가 일치한다면 
					gamePanel.startGame(); //
				}
				tf.setText(""); //텍스트 상자에 적힌 글자 지우기				
			}
		});
	}
}


//게임이 진행되는 공간
public class GamePanel extends JPanel{
	//Container contentPane; //컨텐트펜을 다루기 위해
	
	private GameRunningPanel gameRunningPanel = new GameRunningPanel();
    
    //우측 상단에 표시될 캐릭터의 이미지
    private ImageIcon sangsangBugi = new ImageIcon("character0.png");
    private ImageIcon hansungNyangI = new ImageIcon("character1.png");
    private ImageIcon kkukkuKkakka = new ImageIcon("character2.png");
    private ImageIcon selectedCharacter = null; //선택된 이미지를 가리키도록
    
	//캐릭터 특성, 무기 특성, 풍선 속도
	private int characterType; //사용자가 선택한 캐릭터가 무엇인지를 저장
	private int characterHealth;//캐릭터의 체력
	private int weaponType; //캐릭터의 무기, 사용자가 사용중인 무기가 무엇인지 보이도록	
	private int weaponPower = 1; // 무기의 데미지, 기본 무기의 능력치는 1
	//풍선이 떨어지는 속도 => 캐릭터 특성에 따라 달라짐
	private int ballonSpeed = 100;//딜레이 되는 시간(밀리초단위) => 200밀리초
	//꾸꾸까까가 선택될경우에 true로 변경
	private boolean luckyChance = false; //꾸꾸꼬꼬의 특성, true라면 일정확률로 풍선을 하나 더 터트림
	
	
	private JLabel label = new JLabel(); //
	private String fallingWord = null;
	private FallingThread thread = null; 
	private boolean gameOn = false;
	private WordList wordList; //단어를 관리하기 위한 용도
	
	private ControlPanel controlPanel;
	
	
	//풍선이 떨어지는 스레드
	
	//한 라운드가 끝나면 기존의 풍선들은 모두 제거되어야함
	
	public GamePanel(int characterType) {
		setLayout(null);
		//this.contentPane = contentPane;
		this.setBackground(Color.gray);
        setSize(1500,900);
		
        
		add(label);
		wordList = new WordList("words.txt");
        
		//게임을 생성할때는 캐릭터 정보를 입력받아서 생성
		this.characterType = characterType;
		
		//선택된 캐릭터에 따라 게임난이도 조절
		//0번은 상상부기, 1번은 한성냥이, 3번은 꾸꾸와까까
		switch(characterType) {
		case 0:
			selectedCharacter = sangsangBugi; //상상부기 이미지 선택
			characterHealth = 150; //상상부기의 캐릭터 특성; 체력이 많다(단단하다)
			break;
		case 1:
			selectedCharacter = hansungNyangI; //한성냥이 이미지 선택
			characterHealth = 90;
			ballonSpeed = 350; //한성냥이의 캐릭터 특성; 풍선을 느리게떨어진다(동체시력)
			break;
		case 2:
			selectedCharacter =  kkukkuKkakka; //꾸꾸까까 이미지 선택
			characterHealth = 80;
			luckyChance = true; //꾸꾸까까의 캐릭터 특성; 일정확률~
			break;
		}
		
		//설정된 정보들을 바탕으로 이미지와 정보가 우측에 보이도록
		JLabel character = new JLabel(selectedCharacter);
		character.setSize(selectedCharacter.getIconWidth(),selectedCharacter.getIconHeight());
		character.setLocation(1110,140);
		add(character);
		
		//테스트 확인용 잘 입력되었나 확인용
		System.out.println("선택된 캐릭터 : " + characterType + "캐릭터 체력 : " + characterHealth + "풍선 속도: " + ballonSpeed);
		
		//풍선을 랜덤하게 생성하는 스레드 실행
		
		//게임 시작
		startGame(); 
		makeSplitPane();
		
		
		//풍선이 떨어지게 하는 스레드 실행
		setVisible(true);
	}
	
	
	class testPanel extends JPanel{
		
		public testPanel() {
			setSize(900,900);
			setBackground(Color.cyan);
			setVisible(true);
		}
	}
	
	
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
		JSplitPane vPane = new JSplitPane(); //가로로 나누는 splitPane
		vPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		vPane.setDividerLocation(300);
		//hPane에 붙여야함
		hPane.setRightComponent(vPane); //hPane의 오른쪽에 붙이기

//		hPane.setLeftComponent(gamePanel);
//	  
//		//점수 패널 붙이기
//	    vPane.setTopComponent(scorePanel);
//	    vPane.setBottomComponent(editPanel);
	}
	
	
	//풍선이 떨어지는 스레드 작성
	
	//단어가 내려오는 공간
	
	//게임이 진행중인지 여부를 리턴하는 메소드
	public boolean isGameOn() {
		return gameOn;
	}
	
	//게임을 중단하는 메소드
	public void stopGame() {
		if(thread == null)
			return; //스레드가 생성되지않았다면 무시
		thread.interrupt(); //그렇지 않다면 풍선 스레드 중지
		thread = null; //스레드를 다시 null상태로 돌림
		gameOn = false; //게임진행상태 false로
	}
			
	//새로운 단어가 떨어지도록
	public void stopSelfAndNewGame() { //게임을 정지하고 새로운 게임 시작
		startGame();	
	}
			
	//단어를 뽑아서 라벨에 붙이고		
	public void startGame() { 
		fallingWord = wordList.getWord(); //떨어지도록 할 단어 선정
		label.setText(fallingWord); //단어를 라벨에 붙인다.
		label.setSize(200, 30); //라벨 크기 지정
		label.setLocation((getWidth()-200)/2, 0); //단어가 떨어지기 시작할 위치 지정
		label.setOpaque(true); //배경색을 지정하기 위함
		label.setForeground(Color.MAGENTA); //라벨의 배경색설정		
		label.setFont(new Font("Tahoma", Font.ITALIC, 20)); //단어의 글씨체 지정

		//단어가 내려가기 시작하면 해당 단어마다 각각의 스레드를 실행시켜야함 => 생성된 이후 내려간 속도가 다 달라야 하므로
		thread = new FallingThread(this, label,ballonSpeed); //단어별로 별도의 스레드 동작
		thread.start(); //스레드 작동
		gameOn = true; //게임 진행상태 true로 변경
	}
	
	//입력한 단어와 현재 내려오고있는 단어와 비교하는 메소드
	public boolean matchWord(String text) {
		//단어가 내려가는중이고, 해당 단어가 입력한 단어와 같다면 true리턴
		//=> 여러개의 단어에 대해 확인해야하므로 반복문을 사용하여 관리?
		if(fallingWord != null && fallingWord.equals(text))
			return true; 
		else
			return false;
	}
	
	
	//단어가 내려가는 스레드
	class FallingThread extends Thread {
		private GamePanel panel; 
		private JLabel label; //단어를 관리하기 위한 라벨
		private int ballonSpeed;
		private long delay = 200; //단어가 내려가는 속도?
		private boolean falling = false; //떨어지고 있는지 여부
		
		//단어가 내려가는것을 보여주는 패널과, 단어에 대한 정보를 받는다.
		public FallingThread(GamePanel panel, JLabel label,int ballonSpeed) {
			this.panel = panel;
			this.label = label;
			this.ballonSpeed = ballonSpeed;
		}
		
		//현재 단어가 내려가고 있는지여부를 리턴
		public boolean isFalling() {
			return falling; 
		}	
				
		@Override
		public void run() {
			falling = true; //떨어지고 있는중으로 표시
			while(true) {
				try {
					Thread.sleep(ballonSpeed);
					//sleep(delay);
					int y = label.getY() + 5; //5픽셀씩 아래로 떨어짐
					//단어의 높이가 완전히 땅으로 떨어지면
					if(y >= panel.getHeight()-label.getHeight()) {
						falling = false; //상태를 false로 변경하고
						label.setText("");//내용을 지운다.
						panel.stopSelfAndNewGame();
						break; 
					}
					
					label.setLocation(label.getX(), y);
					GamePanel.this.repaint();
				} catch (InterruptedException e) {
					falling = false;
					return; //스레드 종료
				}
			}
		}	
	}

	
	
	
	
}
