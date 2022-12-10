package game;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;



//게임 패널에서 왼쪽에 붙어서 게임이 진행되는 패널을 제공
public class GameRunningPanel extends JPanel {
	//풍선이 내려오는 스레드
	//풍선에 달린 단어가 올바른 단어인지 확인하는 메소드
	//배경 이미지
	private ImageIcon bgImageicon = new ImageIcon("gamePanelBackgroundImage.png");
	private Image gamePanelBackgroundImage = bgImageicon.getImage();
	private int ballonSpawnTime; //풍선 하나가 생성되는데 걸리는 시간
	private WordList wordList = new WordList("words.txt"); //단어 리스트 생성
	private ballonSpawnThread ballonSpawnThread;
	private boolean gameOn = false;
	private ControlPanel controlPanel;
	
	private StatusPanel statusPanel;
	
	
	private Vector<JLabel> wordVector = new Vector<JLabel>(); //임시로 String벡터 생성
	//풍선이 내려가는 스레드를 벡터로 관리(해쉬맵을 사용하는 방향도 고려해볼것)
	
	public GameRunningPanel(StatusPanel statusPanel) {
		this.statusPanel = statusPanel;
		setLayout(null);
		setSize(1000,900);
		
		
		ballonSpawnTime = 1000;
		ballonSpawnThread = new ballonSpawnThread(ballonSpawnTime);
		ballonSpawnThread.start(); //풍선 생성 시작 => 생성된 이후 풍선은 아래로 내려가기 시작
		gameOn = true; //게임 작동중으로 표시
		
		//사용자로부터 입력받을 공간 생성
		controlPanel = new ControlPanel(statusPanel);	
		add(controlPanel);
		
		setVisible(true);
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
						tf.setText(""); //텍스트 상자에 적힌 글자 지우기 => 단어가 없어지는 효과(임시로)
					}
					
//					if(match) {
//						gamePanel.printResult("성공");
//						gamePanel.stopGame(); //단어가 일치한다면 
//						gamePanel.startGame(); //
//					}
									
				}
			});
		}
		
		
		//벡터 안에 있는 단어인지 확인하는 메소드
		@SuppressWarnings("unlikely-arg-type")
		public boolean isMatch(String text) {
			for(int i=0;i<wordVector.size();i++) {
				//백터의 형식은 라벨이므로 라벨에서 단어를 뽑아야함
				JLabel label = wordVector.get(i);
				if(label.getText().equals(text)) { //벡터 안의 단어와 일치한다면
					System.out.println("단어 일치");
					return true;
				}
			}
			//반복후에도 단어가 없다면
			System.out.println("단어 불일치");
			return false;
		}
	}
	
	//스레드 작성
	//일정 시간마다 풍선을 생성하고 해당 풍선이 내려가도록 하는 스레드를 별도로 붙인다.
	private class ballonSpawnThread extends Thread{
		private int spawnSpeed; //풍선이 생성되는 속도 => 라운드마다 달라지도록
		private int percentage; //풍선생성 확률 => 라운드에 따라 달라지도록
		private String word;
		private JLabel label; //임시로 라벨로 설정 => 차후 풍선 객체로 수정
		
		private ballonFallingThread fallingThread; //풍선이 떨어지게 하는 스레드
		
		public ballonSpawnThread(int spawnSpeed) {
			this.spawnSpeed = spawnSpeed;
		}
		
		@Override
		public void run() {
			while(true) {
				word = wordList.getWord(); //랜덤 단어 추출
				//랜덤한 풍선에 대한 확률조정
				
				//풍선 생성
				//풍선 생성시 풍선 생성자에 매개변수로 랜덤한 숫자와 단어를 넣어서 보냄
				
				//풍선 객체에서는 해당 숫자를 보고 생성될 풍선의 타입(빨강,파랑,노랑,etc)을 결정
				
				//테스트용으로 JLabel에 작성
				label = new JLabel(word); //랜덤 단어를 붙여서 생성
				label.setSize(100,100);
				
				int x = (int)(Math.random()*950); //게임러닝 패널의 가로 길이는 1000
				int y = 10; //임시로 10위치에서 생성되도록
//				System.out.println(x+","+y);
				label.setLocation(x,y); //해당 위치에 생성
				add(label); //패널에 붙이기
				wordVector.add(label);//벡터에 단어 삽입
				
				//풍선에 대한 위치가 변경되도록 하는 스레드 작성
				//풍선이 떨어지게 하는 스레드에는 풍선이 떨어지는 속도와, 해당 라벨에 대한 참조를 넘겨줌
				fallingThread = new ballonFallingThread(label,700);
				fallingThread.start();
				
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
	
	//풍선이 내려가도록 하는 스레드 작성
	private class ballonFallingThread extends Thread{
		private int fallingSpeed;
		private JLabel label;
		
		//풍선이 떨어지는 속도와 라벨을 받음 => 해당 라벨에 독립적으로 동작하는 스레드를 작성하기 위함
		public ballonFallingThread(JLabel label, int fallingSpeed) {
			this.fallingSpeed = fallingSpeed;
			this.label = label; //참조를 가져옴
		}
		
		@Override
		public void run() {
			while(true) {
				int x = label.getX();
				int y = label.getY() + 10; //한번에 10픽셀씩 내려가도록
				label.setLocation(x,y);
				
				try {
					Thread.sleep(fallingSpeed); //해당 초 이후에 다시 위치 변경
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	
	@Override
    public void paintComponent(Graphics g) {
       super.paintComponent(g); //그래픽 컴포넌트 설정
       //배경 이미지
       g.drawImage(gamePanelBackgroundImage, 0, 0, this.getWidth(),this.getHeight(),null); //이미지가 그려지는 시점 알림받지 않기
    }
}
