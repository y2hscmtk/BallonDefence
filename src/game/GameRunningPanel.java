package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Vector;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


/*
 * 라운드 시스템으로 작성
 * 라운드마다 생성될 풍선의 수가 정해져있고, 해당 풍선을 모두 처리하면 라운드가 종료되는 방식으로
 * 라운드가 종료되면 기존의 스레드를 정지시키고, 벡터를 비우고, 게임 정보를 수정한다(풍선 생성 속도, 풍선 종류)
 * 라운드 종료 이후 상점이 등장하고, 게임에서 벌어들인 돈을 바탕으로 상점에서 무기를 변경할수 있도록함 
 * 1라운드에서 돈을 충분히 벌지 못하면 무기를 사지 못하도록 알맞은 금액으로 설정할것, 무기 이외의 다른 아이템 생각해볼것
*/

//게임 패널에서 왼쪽에 붙어서 게임이 진행되는 패널을 제공
public class GameRunningPanel extends JPanel {
	private GameFrame parent;
	//게임에 대한 특성
	//라운드마다 생성될 풍선의 최대 개수 => 해당 풍선을 모두 지우면 다음 라운드로 넘어가도록
	private static final int ROUND1BALLONCOUNT = 10; //1라운드에는 10개의 풍선
	private static final int ROUND2BALLONCOUNT = 20; //2라운드에는 20개의 풍선
	private static final int ROUND3BALLONCOUNT = 30; //3라운드에는 30개의 풍선
	
	private int weaponPower; //사용중인 무기의 공격력
	private int characterType; //사용자가 선택한 캐릭터가 무엇인지를 저장
	
	//풍선이 떨어지는 속도 => 캐릭터 특성에 따라 달라짐
	private int ballonSpeed;//풍선이 내려오는 시간 => 딜레이 되는 시간(밀리초단위)
	//꾸꾸까까가 선택될경우에 true로 변경
	private boolean luckyChance = false; //꼬꼬꾸꾸의 특성, true라면 일정확률로 풍선에 추가타를 가함
	
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
	
	public GameRunningPanel(StatusPanel statusPanel,int characterType,GameFrame parent) {
		this.parent = parent;
		this.characterType = characterType; //캐릭터 타입 지정
		this.statusPanel = statusPanel;
		setLayout(null);
		setSize(1000,900);
		
		
		//게임패널에서 홈 화면으로 돌아갈수 있도록 하기 위함 => 게임 종료후 다시하기에 사용
		//현재까지의 모든 벡터를 비우고,스레드를 중지시키고, 홈화면으로 돌아가면 다시 음악을 실행시켜야함
		
		 //홈 버튼
  		JLabel homeButton = new JLabel("버튼 테스트");
  		homeButton.setSize(100,100);
  		homeButton.setLocation(100,100);
  		homeButton.addMouseListener(new ButtonClickedEvent(parent,parent.BEGINNING_PANEL));
  		add(homeButton);
		
		
		
		//선택한 캐릭터에 따른 게임 생성
		switch(characterType) {
		case 0:
			ballonSpeed = 800;
			break;
		case 1: //한성냥이의 경우 => 동체시력
			ballonSpeed = 1500;//풍선을 느리게 본다.
			break;
		case 2: //꼬꼬꾸꾸의 경우 => 50프로 확률의 추가타
			ballonSpeed= 200;
			luckyChance = true; //럭키찬스 활성화
			break;
		}
		
		weaponPower = 1; //기본 무기의 파워는 1
		
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
		
		//1라운드로 풍선 생성 스레드 시작
		
//		ballonSpawnThread.start(); //풍선 생성 시작 => 생성된 이후 풍선은 아래로 내려가기 시작(풍선 객체 내부 스레드)
		gameOn = true; //게임 작동중으로 표시
		
		//사용자로부터 입력받을 공간 생성
		controlPanel = new ControlPanel(statusPanel);	
		add(controlPanel);
		
		//게임총괄스레드 생성
		GameMangeThread gameThread = new GameMangeThread();
		gameThread.start();
		
		setVisible(true);
	}
	
	
	//게임을 관리하는 스레드 작성 => 라운드 관리, 게임오버 관리, 다음라운드로의 진행,등등
	//게임러닝패널 클래스 안에 작성하여, 정보들을 관리할수 있도록
	private class GameMangeThread extends Thread{
//		private int ballonCount = 0;//현재 생성된 풍선의 개수를 저장할 변수
		private boolean isGameRun = true;
		private BallonSpawnThread spawnThread; //풍선이 생성되게 하는 스레드
		private int gameLevel = 1;
		private boolean isPlayerAlive = true; //플레이어의 생존상태 표시
		
		public void startGame() {
			this.isGameRun = true;
		}
		
		public void stopGame() {
			this.isGameRun = false;
		}
		
		public boolean getIsGameRun() {
			return this.isGameRun;
		}
		
		public GameMangeThread() {
			//라운드 1부터 시작
			isPlayerAlive = true; //플레이어의 생존상태 표시
			ballonSpawnThread = new BallonSpawnThread(ballonSpawnTime,ballonSpeed,statusPanel, gameLevel);
			ballonSpawnThread.start();
		}
		
		//플레이어의 체력을 확인하여 체력이 0 이하가 되었다면 true리턴
		//그렇지 않다면 false리턴
		public boolean isPlayerDie() {
			//캐릭터의 체력이 0이하가 되면 게임 종료
			if(statusPanel.getHealth()<=0) {
				//음악 스레드 종료 => 사망 음악 실행
				
				//풍선 생성 스레드 종료
				ballonSpawnThread.interrupt();
				
				//내려오는 풍선 스레드 종료 => 벡터를 돌면서
				for(int j=0;j<balloonVector.size();j++) {
					
					Balloon balloon = balloonVector.get(j);
					balloon.stopFallingThread(); //떨어지는 스레드 종료
				}
				
				return true; //플레이어 사망시 트루 리턴
//				System.exit(0);
			}
			else return false;
		}
		
		
		//게임의 진행상황에 맞춰 게임을 관리
		@Override
		public void run() {
			while(isGameRun) {
				//풍선이 일정높이 아래로 떨어지면 벡터에서 삭제 => 벡터의 모든 풍선 검사
				for(int i=0;i<balloonVector.size();i++) {
					try {
						Balloon fallingBalloon = balloonVector.get(i);
						
						int x = fallingBalloon.getX();
						int y = fallingBalloon.getY();
						
						
						//풍선 위치 검사
						//일정 높이 이하로 떨어지면 풍선 객체 삭제c
						if(y>=500) {
							remove(fallingBalloon);
							balloonVector.remove(fallingBalloon); //객체에서 삭제
							
//							setVisible(false);
							//체력을 깎아야함
							statusPanel.getDamage(10); //캐릭터에게 데미지를 가한다.
							
							//이후 체력이 0이 되었는지 확인해야함 => 풍선이 떨어져서 사망하는 경우 고려
							if(isPlayerDie()&&isPlayerAlive){ //플레이어가 아직 살아있는경우에 대해서만
								isGameRun = false; //현재 스레드 종료
								System.out.println("플레이어 사망");
								isPlayerAlive = false; //사망처리
								break;
								//최종 결과창 출력 => 아이디 입력받기 => 다시하기 버튼, 점수보기 버튼 보여주기
								
								
							}
							
						}
					}catch(Exception E) {
						System.out.println("deleted!");
					}
					
				}
				
				
				
				//풍선이 전부 생성이 되었고,생성된 풍선을 모두 지웠을때 => 다음 라운드로 넘어가거나, 게임 클리어를 알림
				if(!ballonSpawnThread.getIsRun()&&balloonVector.isEmpty()) {
					//게임이 3라운드까지 모두 끝났는지 확인필요
					if(gameLevel>3) {
						System.out.println("게임 성공! 아이디를 입력하세요!!");
						//작동중인 스레드는 GameMangeThread이외에는 없게 될것이므로 해당 스레드만 종료시키면됨
						isGameRun = false;
					}
					else { //3라운드 이하의 라운드에서만 상점과 새로운 풍선스레드를 동작
						System.out.println("상점창 띄우기");//상점창에서 버튼을 눌러 다음 스테이지로 넘어가게끔
						//벡터 비우기
						gameLevel+=1;
						ballonSpawnThread = new BallonSpawnThread(ballonSpawnTime,ballonSpeed,statusPanel,gameLevel);
						ballonSpawnThread.start();
					}
				}
				
				//현재 살아있는 경우에 대해서만 죽임
				if(isPlayerDie()&&isPlayerAlive){
					isGameRun = false; //현재 스레드 종료
					//최종 결과창 출력 => 아이디 입력받기 => 다시하기 버튼, 점수보기 버튼 보여주기
					isPlayerAlive = false; //사망처리 
					System.out.println("플레이어 사망");
				}
			}
		}
			
	}
	
	
	
	//스레드 작성
	//일정 시간마다 풍선을 생성하고 해당 풍선이 내려가도록 하는 스레드를 별도로 붙인다.
	//라운드에 따라서 풍선의 색깔별 확률을 부여한다.
	private class BallonSpawnThread extends Thread{
		private int spawnSpeed; //풍선이 생성되는 속도 => 라운드마다 달라지도록
		private int percentage; //풍선생성 확률 => 라운드에 따라 달라지도록
		private String word;
		private JLabel label; //임시로 라벨로 설정 => 차후 풍선 객체로 수정
		private int fallingSpeed; //떨어지는 시간
		private int x; //풍선이 생성될 가로 위치를 지정
		
		private boolean isRun = true;
		
		
		public void startSpawn() {
			this.isRun = true;
		}
		
		public void stopGame() {
			this.isRun = false;
		}
		
		public boolean getIsRun() {
			return this.isRun;
		}
		
		
		private int gameLevel = 3; //게임의 현재 라운드를 기록
		//아이템풍선의 생성확률
		private int starBalloonPercentage=5; //스타풍선 생성확률 5퍼센트
		private int coinBalloonPercentage=3; //코인풍선 생성확률 3퍼센트
		
		
		//각 색깔풍선의 생성확률
		private int redBalloonPercentage;
		private int blueBalloonPercentage;
		//노랑풍선은 그 이외의 확률이므로 별도 지정x
		private int BallonCount;
		
		//풍선이 어느정도 균등한 위치에서 생성되도록 하기위함
		private int lastPosition = 0; //첫번째로 생성된 위치를 저장 임의로 x로 지정
		private int lastPosition2 = 5; //두번째로 생성된 위치를 저장
		
		private StatusPanel statusPanel; //스테이터스 패널에 대한 래퍼랜스
		
		//풍선 생성시 스테이터스창에 대한 래퍼랜스를 넘겨줘야함
		//=>풍선이 떨어지는 스레드에서 풍선이 땅으로 떨어지면 스테이터스창에 영향을 가할수 있도록
		public BallonSpawnThread(int spawnSpeed,int fallingSpeed,StatusPanel statusPanel,int gameLevel) {
			this.gameLevel = gameLevel; //게임 레벨
			System.out.println("라운드 : "+ gameLevel);
//			gameLevel = 3;
			//레벨에 맞춰 색깔풍선 확률과, 생성될 풍선의 수 지정
			switch(gameLevel) {
			case 1:
				BallonCount = ROUND1BALLONCOUNT; //10개
				//아이템 풍선 기능 추가
				//빨강풍선 : 파랑풍선 = 약 8 : 2 비율설정
				redBalloonPercentage = 74;
				blueBalloonPercentage = 18;
				//노랑풍선은 그 이외의 확률
				break;
			case 2:
				BallonCount = ROUND2BALLONCOUNT; //20개
				//빨강풍선 : 파랑풍선 = 약 5 : 3 : 2 비율설정
				redBalloonPercentage = 46;
				blueBalloonPercentage = 27;
				//노랑풍선은 19;
				//노랑풍선은 그 이외의 확률
				break;
			case 3:
				BallonCount = ROUND3BALLONCOUNT; //30개
				//빨강풍선 : 파랑풍선 : 노랑풍선 = 3 : 4 : 3 비율 설정
				redBalloonPercentage = 28;
				blueBalloonPercentage = 37;
				//노랑풍선은 그 이외의 확률
				break;
			}
			
			
			this.fallingSpeed = fallingSpeed;
			this.spawnSpeed = spawnSpeed;
			this.statusPanel = statusPanel;
		}
		
		@Override
		public void run() {
			
			
			//게임 오프닝 메시지
			
			
//			while(true) {
//				
//			}
			//원하는 순자만큼 풍선을 생성하도록
			for(int i=0;i<BallonCount;i++) {
				int position;
				word = wordList.getWord(); //랜덤 단어 추출
				//랜덤한 풍선에 대한 확률조정
				
				
				//풍선의 생성 위치 조정 => 풍선끼리 잘 겹치지 않도록
				//이미지의 가로길이를 가져와서 게임러닝패널의 영역으로 나누어 영역을 지정
				while(true) {
					position = (int)(Math.random()*10);//0~9까지의 난수 생성 => 풍선이 생성될 위치를 임의로 지정하기 위함
					if(position!=lastPosition&&position!=lastPosition2) {//이전 2개의 위치가 아닌곳에 생성
						lastPosition2 = lastPosition; //첫번째 생성위치를 두번째 생성위치로 옮기고
						lastPosition = position; //현재 생성된 위치를 저장 => 이후 2개의 위치가 아닌곳에 생성되게함
						//System.out.println(position);
						break; //탈출
					}
						
				}	
				
				//포지션을 통해 풍선의 x좌표 지정
				x = (int)(Math.random()*100) + (70*position); //이전에 생성되었던 2곳의 위치에 생성되지않도록
				//풍선이 생성될 위치 결정
				int y = -100; //임시로 0위치에서 생성되도록
				
				//풍선 색 확률조정
				int random = (int)(Math.random()*100)+1; //0~100까지의 난수 생성
				int ballonType; //풍선이 생성될 타입 지정
				System.out.println("난수 : "+random);
				//0~3사이의 난수 => 3퍼센트
				if(0<=random&&random<coinBalloonPercentage) //3퍼센트 확률로 코인풍선 등장
					ballonType = 3; //코인풍선
				else if(coinBalloonPercentage<=random&&random<coinBalloonPercentage+starBalloonPercentage) //5퍼센트 확률로 스타풍선(경험치2배)등장
					ballonType = 4; //스타코인
				else if(coinBalloonPercentage+starBalloonPercentage<=random&&random<coinBalloonPercentage+starBalloonPercentage+redBalloonPercentage)
					ballonType = 0; //빨강
				else if(coinBalloonPercentage+starBalloonPercentage+redBalloonPercentage<=random&&random<=coinBalloonPercentage+starBalloonPercentage+redBalloonPercentage+blueBalloonPercentage)
					ballonType = 1; //파랑풍선
				else //나머지는 노랑색
					ballonType = 2; 
				
				
				//풍선 생성 => (풍선 타입, 단어)
				//풍선 생성시 스테이터스 패널에 대한 참조를 넘김 => 스테이터스 패널에 영향을 가할수 있도록
				balloon = new Balloon(ballonType,word,fallingSpeed,statusPanel);
				balloon.setVisible(true);
				balloon.setSize(300,300);
				balloon.setLocation(x,y);
				add(balloon); //패널에 붙이기
				balloonVector.add(balloon);//벡터에 풍선 삽입
				//System.out.println(x+","+y);
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
			
			
			System.out.println("스레드 종료");
			isRun = false;
			//라운드 종료 이후
			controlPanel.initDoublePoint(); //경험치 2배=>1배로 초기화
//			while(true) {
//				
//			}
		}
	}
	
	
	class ControlPanel extends JPanel {
		private JTextField input = new JTextField(15); //단어을 입력받을 공간 설정
		private StatusPanel statusPanel;
		private int doublePoint = 1; //스타풍선을 터트렸을때 한 라운드 동안 경험치를 2배로 받도록 하기위함
		private Clip clip;
		
		//경험치2배 => 1배로 초기화
		public void initDoublePoint() {
			doublePoint = 1; 
		}
		
		//생성자 
		public ControlPanel(StatusPanel statusPanel) {
			this.statusPanel = statusPanel;
			setLayout(null);
			//패널이 생성될 위치 조정
			setBounds(0,800,1000,100); //0,800의 위치에서 800x100크기의 컨트롤 패널 부착
			setBackground(Color.cyan); //잘 부착되었는지 확인하기 위한 임시색상 지정
			
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

					//System.out.println(text);
					//단어가 일치한다면 => 해당 단어에 대한 스레드 종료,삭제	
					if(isMatch(text)) {
						//정답시 효과음 나도록
						try {
							clip = AudioSystem.getClip();
							File audioFile = new File("Correct.wav");
							AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
							clip.open(audioStream);
						}catch(Exception E) {
							System.out.println("정답 음원 추출 오류!");
						}
						clip.start(); // 버튼을 클릭했을때 소리가 나도록
						
						//풍선의 체력이 0이되었을때만 점수추가
//						statusPanel.plusScore(10); //점수 추가
						
//						System.out.println("단어 일치함");
						
					}
					else { //정답이 아닐시 => 체력을 5깎음
						try {
							clip = AudioSystem.getClip();
							File audioFile = new File("Wrong.wav");
							AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
							clip.open(audioStream);
						}catch(Exception E) {
							System.out.println("오답 음원 추출 오류!");
						}
						clip.start(); // 버튼을 클릭했을때 소리가 나도록
						statusPanel.getDamage(5); //오답시 5의 데미지
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
//					System.out.println("단어 일치");
					
					if(luckyChance) { //럭키찬스 효과가 활성화 되어있다면
						//50퍼센트의 확률로 추가 공격을 가한다.

						int random = (int)(Math.random()*100);
						System.out.println(random);
						if(random%2!=0) {
							//풍선에 추가 공격을 가한다.(꼬꼬가 공격하면 꾸꾸도 공격)
							System.out.println("추가공격!");
							balloon.getDamage(weaponPower+1);
						}
						else
							balloon.getDamage(weaponPower); 
//						
					}
					else //꼬꼬꾸꾸가 아닌경우에는 luckyChance 비활성화, 무기만큼 데미지를 가한다.
						balloon.getDamage(weaponPower); //무기의 데미지 만큼 풍선에 피해를 가한다.
					
					//이후 풍선의 체력을 가져와서 확인
					int ballonHealth = balloon.getHealth();
					
					//풍선의 체력이 0이하가 되면 풍선을 삭제한다.
					if(ballonHealth<=0) {
						//풍선의 체력이 0이하가되면 점수 추가
						//풍선 종류별로 차등한 점수를 부여한다.
						//빨강풍선(0+100점), 파랑풍선(100+100점), 노랑풍선(200+100점)
						int balloonType = balloon.getBalloonType(); //현재 풍선이 어떤타입인지체크
						//풍선의 종류에 따른 차등 점수와 코인을 부여
						int score = 0; 
						int coin = 0;
						switch(balloonType) {
						case 0: //빨강풍선 150점, 100원
							score = 150;
							coin = 100;
							break;
						case 1: //파랑풍선 300점, 200원
							score = 300;
							break;
						case 2: //노랑풍선 600점, 300원
							score = 600;
							break;
						case 3: //코인풍선의 경우
							score = 777; //777원 획득
							coin = 1000; //1000원을 획득
						case 4: //스타풍선의 경우
							score = 777; //777원 획득
							coin = 77;
							//경험치 2배 활성화
							doublePoint = 2; //경험치 부여를 2배로 변경
							
						}
						statusPanel.plusScore(score*doublePoint);//스타풍선 터트리기전에는 doublePoint가 1
						statusPanel.plusCoin(coin);
						
						balloon.setVisible(false); //안보이도록 처리
						balloon.stopFallingThread(); //풍선 멈추기
						balloonVector.remove(balloon); //벡터에서 풍선제거
						
						remove(balloon); //패널에 달린 풍선객체를 지운다.
					}
					return true; //정답이 일치할경우 true를 리턴한다
				}
			}
			//반복후에도 단어가 없다면
//			System.out.println("단어 불일치");
			return false; //틀린 답을 입력할경우 fasle를 리턴한다.
		}
	}
	
	//게임러닝패널의 백그라운드 이미지 그리기
	@Override
    public void paintComponent(Graphics g) {
       super.paintComponent(g); //그래픽 컴포넌트 설정
       //배경 이미지
       g.drawImage(gamePanelBackgroundImage, 0, 0, this.getWidth(),this.getHeight(),null); //이미지가 그려지는 시점 알림받지 않기
    }
}
