package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;



//게임 패널에서 왼쪽에 붙어서 게임이 진행되는 패널을 제공
public class GameRunningPanel extends JPanel {
	//게임에 대한 특성
	private int characterType; //사용자가 선택한 캐릭터가 무엇인지를 저장
	//풍선이 떨어지는 속도 => 캐릭터 특성에 따라 달라짐
	private int ballonSpeed;//딜레이 되는 시간(밀리초단위) => 200밀리초
	//꾸꾸까까가 선택될경우에 true로 변경
	private boolean luckyChance = false; //꾸꾸꼬꼬의 특성, true라면 일정확률로 풍선을 하나 더 터트림
	
	//풍선이 내려오는 스레드
	//풍선에 달린 단어가 올바른 단어인지 확인하는 메소드
	//배경 이미지
	private ImageIcon bgImageicon = new ImageIcon("gamePanelBackgroundImage.png");
	private Image gamePanelBackgroundImage = bgImageicon.getImage();
	
	
	private int ballonSpawnTime; //풍선 하나가 생성되는데 걸리는 시간
	private WordList wordList = new WordList("words.txt"); //단어 리스트 생성
	
	
	private BallonSpawnThread ballonSpawnThread; //풍선을 생성하는 스레드
	private boolean gameOn = false;
	
	//패널 관리
	private ControlPanel controlPanel;
	private StatusPanel statusPanel;
	
	//풍선을 관리하기 위한 벡터
	private Balloon balloon;
	private Vector<Balloon> balloonVector = new Vector<Balloon>();
	
	private Vector<JLabel> wordVector = new Vector<JLabel>(); //임시로 String벡터 생성
	//풍선이 내려가는 스레드를 벡터로 관리(해쉬맵을 사용하는 방향도 고려해볼것)
	
	public GameRunningPanel(StatusPanel statusPanel,int characterType) {
		this.characterType = characterType; //캐릭터 타입 지정
		this.statusPanel = statusPanel;
		setLayout(null);
		setSize(1000,900);
		
		//선택한 캐릭터에 따른 게임 생성
		switch(characterType) {
		case 0:
			ballonSpeed = 800;
			break;
		case 1: //한성냥이의 경우 => 동체시력
			ballonSpeed = 1500;//풍선을 느리게 본다.
			break;
		case 2: //꾸꾸까까의 경우 => 럭키찬스 효과
			ballonSpeed= 200;
			luckyChance = true; //럭키찬스 활성화
			break;
		}
		
		JButton stopBalloon = new JButton("풍선 멈추기");
		stopBalloon.setSize(100,100);
		stopBalloon.setLocation(0,800);
		stopBalloon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				for(int i=0;i<balloonVector.size();i++) {
					Balloon balloon = balloonVector.get(i);
					balloonVector.remove(i);// 벡터에서 지우기
					balloon.stopFallingThread();
				}
			}
		});
		add(stopBalloon);
		
		ballonSpawnTime = 2000; //풍선이 생성되는데 걸리는 시간을 1초로 지정
		ballonSpawnThread = new BallonSpawnThread(ballonSpawnTime,ballonSpeed);
		ballonSpawnThread.start(); //풍선 생성 시작 => 생성된 이후 풍선은 아래로 내려가기 시작(풍선 객체 내부 스레드)
		gameOn = true; //게임 작동중으로 표시
		
		//사용자로부터 입력받을 공간 생성
		controlPanel = new ControlPanel(statusPanel);	
		add(controlPanel);
		
		setVisible(true);
	}
	
	
	
	
	//스레드 작성
	//일정 시간마다 풍선을 생성하고 해당 풍선이 내려가도록 하는 스레드를 별도로 붙인다.
	private class BallonSpawnThread extends Thread{
		private int spawnSpeed; //풍선이 생성되는 속도 => 라운드마다 달라지도록
		private int percentage; //풍선생성 확률 => 라운드에 따라 달라지도록
		private String word;
		private JLabel label; //임시로 라벨로 설정 => 차후 풍선 객체로 수정
		private int fallingSpeed; //떨어지는 시간
		private int x; //풍선이 생성될 가로 위치를 지정
		private int lastPosition = 9; //이전에 생성된 위치를 저장 임의로 x로 지정
		
//		private ballonFallingThread fallingThread; //풍선이 떨어지게 하는 스레드
		
		public BallonSpawnThread(int spawnSpeed,int fallingSpeed) {
			this.fallingSpeed = fallingSpeed;
			this.spawnSpeed = spawnSpeed;
		}
		
		@Override
		public void run() {
			while(true) {
				int position;
				System.out.println("풍선스레드 생성됨");
				word = wordList.getWord(); //랜덤 단어 추출
				//랜덤한 풍선에 대한 확률조정
				
				
				//풍선의 생성 위치 조정 => 풍선끼리 잘 겹치지 않도록
				//이미지의 가로길이를 가져와서 게임러닝패널의 영역으로 나누어 영역을 지정
				while(true) {
					position = (int)(Math.random()*10);//0~9까지의 난수 생성 => 풍선이 생성될 위치를 임의로 지정하기 위함
					if(position!=lastPosition) {//이전의 위치가 아니라면
						lastPosition = position; //현재 위치를 저장하고
						break; //탈출
					}
						
				}	
				
				//포지션을 통해 풍선의 x좌표 지정
				x = (int)(Math.random()*100) + (100*position);

				
				
				//풍선이 생성될 위치 결정
				
				int y = 0; //임시로 0위치에서 생성되도록
				
				//풍선 생성 => (풍선 타입, 단어)
				balloon = new Balloon(1,word,fallingSpeed);
				balloon.setVisible(true);
				balloon.setSize(300,300);
				balloon.setLocation(x,y);
				add(balloon); //패널에 붙이기
				balloonVector.add(balloon);//벡터에 풍선 삽입
				System.out.println(x+","+y);
				//풍선 생성시 풍선 생성자에 매개변수로 랜덤한 숫자와 단어를 넣어서 보냄
				
				//풍선 객체에서는 해당 숫자를 보고 생성될 풍선의 타입(빨강,파랑,노랑,etc)을 결정
				
//				//테스트용으로 JLabel에 작성
//				label = new JLabel(word); //랜덤 단어를 붙여서 생성
//				label.setSize(100,100);
//						
//				label.setLocation(x,y); //해당 위치에 생성
//				add(label); //패널에 붙이기
				//wordVector.add(label);//벡터에 단어 삽입
				
				//풍선에 대한 위치가 변경되도록 하는 스레드 작성
				//풍선이 떨어지게 하는 스레드에는 풍선이 떨어지는 속도와, 해당 라벨에 대한 참조를 넘겨줌
//				fallingThread = new ballonFallingThread(label,700);
//				fallingThread.start();
				
				try {
					Thread.sleep(spawnSpeed);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					//인터룹 발생시
					break; //스레드 종료
				}
			}
		}
	}
	
	
	class ControlPanel extends JPanel {
		private JTextField input = new JTextField(15); //단어을 입력받을 공간 설정
		private StatusPanel statusPanel;
		
		//생성자 
		public ControlPanel(StatusPanel statusPanel) {
			this.statusPanel = statusPanel;
			setLayout(null);
			//패널이 생성될 위치 조정
			setBounds(0,800,1000,100); //0,800의 위치에서 800x100크기의 컨트롤 패널 부착
			setBackground(Color.cyan); //잘 부착되었는지 확인하기 위한 임시색상 지정
			System.out.println("컨트롤패널호출됨");
			
//			this.setLayout(new FlowLayout());
//			this.setBackground(Color.LIGHT_GRAY);
			input.setLocation(10,10);
			input.setSize(800,50);
			add(input);
			
			//엔터키를 쳤을때 이벤트 발생
			input.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					JTextField tf = (JTextField)e.getSource();
					String text = tf.getText(); //텍스트에 입력된 단어가 무엇인지 가져옴

					System.out.println(text);
					//단어가 일치한다면 => 해당 단어에 대한 스레드 종료,삭제	
					if(isMatch(text)) {
						statusPanel.plusScore(10); //점수 추가
						
						System.out.println("단어 일치함");
						
					}
					tf.setText(""); //텍스트 상자에 적힌 글자 지우기 => 단어가 없어지는 효과(임시로)				
				}
			});
		}
		
		//풍선벡터 안에 있는 단어인지 확인하는 메소드
		@SuppressWarnings("unlikely-arg-type")
		public boolean isMatch(String text) {
			for(int i=0;i<balloonVector.size();i++) {
				
				Balloon balloon = balloonVector.get(i);
				if(balloon.getWord().equals(text)) { //벡터 안의 단어와 일치한다면
					
					balloon.setVisible(false); //안보이도록 처리
					balloon.stopFallingThread(); //풍선 멈추기
					balloonVector.remove(balloon);
					remove(balloon); //패널에 달린 풍선객체를 지운다.
					System.out.println("단어 일치");
					return true;
				}
			}
			//반복후에도 단어가 없다면
			System.out.println("단어 불일치");
			return false;
		}
	}
	
	@Override
    public void paintComponent(Graphics g) {
       super.paintComponent(g); //그래픽 컴포넌트 설정
       //배경 이미지
       g.drawImage(gamePanelBackgroundImage, 0, 0, this.getWidth(),this.getHeight(),null); //이미지가 그려지는 시점 알림받지 않기
    }
}
