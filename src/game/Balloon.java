package game;

import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

//풍선이 겹칠때를 대비하여 LayerdPane으로 작성
public class Balloon extends JLayeredPane{
	
	private String text; //풍선에 함께 달려서 내려올 단어
	private int ballonType; //어떤 종류의 풍선인지 정보를 저장
	
	private int health = 1; //기본 풍선의 체력
	private int fallingSpeed; //풍선이 떨어지는 속도
	
	
	private StatusPanel statusPanel; //스테이터스 패널에 대한 래퍼랜스
	
	//풍선 이미지 아이콘
	//private ImageIcon blue = new ImageIcon("blue.png");
	private ImageIcon redBalloonIcon = new ImageIcon("redBalloon.png");//빨강 풍선 아이콘
	private ImageIcon blueBalloonIcon = new ImageIcon("blueBalloon.png");//파랑 풍선 아이콘
	private ImageIcon yellowBalloonIcon = new ImageIcon("yellowBalloon.png");//노랑 풍선 아이콘
	//특별한 풍선 => 등장확률을 낮게 설정할것 2프로?3프로?
	private ImageIcon coinBalloonIcon = new ImageIcon("coinBalloon.png"); //코인 풍선 => 1000코인 획득
	private ImageIcon starBalloonIcon = new ImageIcon("starBalloon.png"); //스타 풍선 => 한 라운드간 경험치 2배 
	
	
	private Image image; 
	private BalloonFallingThread fallingThread = null; //풍선이 떨어지게 하는 스레드
	private JButton balloonImage; //풍선 이미지
	private JLabel answerText; //풍선 밑에 붙일 단어
	
	//풍선 객체 외부에서 풍선 객체가 떨어지는 스레드를 조작할수 있도록 스레드 리턴
	public BalloonFallingThread getFallingThread() {
		return fallingThread; 
	}
	
	//외부에서 풍선을 멈출수 있도록 메소드 작성
	public void stopFallingThread() {
		if(fallingThread!=null) //null이 아닌경우에 대해 스레드 종료
			fallingThread.interrupt();
	}
	
//	public void waitFallingThread() {
//		fallingThread.stopBalloon(); //풍선을 잠깐 멈추게함
//	}
//	
//	public void resumeFallingThread() {
//		fallingThread.resumeBalloon(); //다시 움직이게 함
//	}
//	
	//매개변수로 받은 아이콘의 이미지로 풍선의 이미지 변경하는 메소드
	public void setImage(ImageIcon icon) {
		this.image = icon.getImage();
	}
	
	
	//풍선에 달린 단어를 리턴
	public String getWord() {
		return answerText.getText();
	}
	
	//현재 풍선이미지를 리턴한다.
	public JButton getBalloonImage() {
		return balloonImage;
	}
	
	//풍선의 타입을 리턴한다.(풍선의 색 0: 빨강, 1:파랑 2: 노랑, 체력도 동일)
	public int getBalloonType() {
		return ballonType;
	}
	
	//풍선의 체력을 리턴한다.
	public int getHealth() {
		return health;
	}
	
	
	public Balloon(int ballonType,String text,int fallingSpeed, StatusPanel statusPanel){
//		System.out.println("새 풍선 생성됨");
		setLayout(null);
		this.ballonType = ballonType; //풍선 타입을 저장
		this.statusPanel = statusPanel; //래퍼랜스 가져오기
		
		//풍선의 타입에 따른 풍선 특성 결정
		switch(ballonType) {
		case 0: //빨강풍선의 경우
			image = redBalloonIcon.getImage();
			break;
		case 1: //파랑풍선의 경우
			image = blueBalloonIcon.getImage();
			health = 2;
			break;
		case 2: //노랑풍선의 경우
			image = yellowBalloonIcon.getImage();
			health = 3;
			break;
		case 3: //터트릴경우 1000골드를 획득하는 풍선 => 효과음도 고려해볼것
			image = coinBalloonIcon.getImage();
			break;
		case 4: //터트릴경우 한 라운드동안 점수를 2배로 받는 코인
			image = starBalloonIcon.getImage();
			break;
		}
		
		
	    this.text = text;
	    
//	    //단어길이 테스트용
//	    this.text = "ABCDE"; //10글자의 경우
	    //9글자가 딱 적당함
	    
	    this.fallingSpeed = fallingSpeed; ///풍선이 떨어지는 속도 지정
	    setSize(100,100);
	    setBackground(Color.cyan);
	    
	    
	    balloonImage = new JButton(blueBalloonIcon);
	    balloonImage.setSize(200,800);
	    balloonImage.setLocation(0,0); //풍선 라벨의 x,y좌표에 보이게
	    balloonImage.setOpaque(true);
	    balloonImage.setBackground(Color.black);
//	    balloonImage.setBounds(0,0,100,160);
		//button.setBounds(0,0,100,160);
		//button.setBackground(Color.RED);
		//add(balloonImage);
		
	    //단어의 길이 확인
	    int textLength = text.length();
	   
	    
	    //풍선 이미지 밑에 텍스트를 붙이는 작업
		answerText = new JLabel(this.text);
		answerText.setOpaque(true); //배경색을 칠하게 하기 위함
		answerText.setBackground(Color.white);
		answerText.setFont(new Font("SansSerif",Font.BOLD, 30));
		answerText.setSize(180,42);
		answerText.setLocation(62,getHeight()+136);
		add(answerText);
//		
//		//자기자신에 대한 참조와 풍선이 떨어지는 속도(sleep)를 넘겨서 스레드 생성
		fallingThread = new BalloonFallingThread(this, fallingSpeed);
		fallingThread.start(); //풍선 떨이지게 하는 스레드 작동
		
		setVisible(true);  
	}
	  	
//	public void abilityInvoke() {
//		actionListener.notify();
//	}
//  
	//풍선이 피해를 입으면 풍선의 이미지를 수정하게 하기 위해(피해는 올바른 단어를 입력했을때)
	public void getDamage(int damage){
		health-=damage;
		switch (health){
//		case 0: //체력이 0이되면 풍선제거
//			setVisible(false);
		case 1: //체력이 1이면 빨강풍선 이미지
			setImage(redBalloonIcon);
			break;
		case 2: //체력이 2이면 파랑풍선 이미지로
			setImage(blueBalloonIcon);
			break;
		}
	}

	  
	//풍선이 내려가도록 하는 스레드 작성
	private class BalloonFallingThread extends Thread{
		private Balloon balloon = null;
		private int fallingSpeed; //풍선이 떨어지는 속도
		private boolean stopFlag = false; //눈이 내리는것을 멈추도록
		
		//풍선이 떨어지는 속도 지정
		public BalloonFallingThread(Balloon balloon,int fallingSpeed) {
//			System.out.println("풍선 속도 : "+fallingSpeed);
			
			this.fallingSpeed = fallingSpeed; //떨어지는 속도 지정(sleep시간)
			this.balloon = balloon; //풍선 참조가져오기
		}
		
		
//		//현재 stopFlag상태를 리턴
//		public boolean getStopFlag() {
//			return stopFlag;
//		} 
//		
//		
//		//풍선이 내려가는것을 멈추도록 stop명령
//		public void stopBalloon() {
//	        stopFlag = true; 
//	    }
//	    
//		//풍선을 다시 움직이게 하는 메소드
//	    synchronized public void resumeBalloon() {
//	        stopFlag = false; //스탑 플래그를 false로 변경 => 멈추치 않도록
//	        this.notify(); //이 객체를 무한대기하는 쓰레드 깨우기
//	    }
//		
//		
//		//flag가 false가 될때까지 기다리는 함수
//        synchronized private void waitFlag() { //wait함수를 쓰기 위해선 synchronized 키워드를 사용해야함
//           try {
//              this.wait(); //쓰레드 무한대기 상태로 변경=>notify()가 불려지기전까지
//           } catch (InterruptedException e) {
//              // TODO Auto-generated catch block
//              e.printStackTrace();
//           } //기다리면서 중단된 상태로
//        }
//		
		
		
		@Override
		public void run() {
			while(true) {
//				if(stopFlag) { //stop플래그가 올라와있다면
//					waitFlag(); //풍선 멈추게 하기
//				}
//				
				int x = balloon.getX();
				int y = balloon.getY()+10;//10픽셀씩 아래로 이동
//				//일정 높이 이하로 떨어지면 풍선 객체 삭제c
//				if(y>=500) {
//					setVisible(false);
//					//체력을 깎아야함
//					statusPanel.getDamage(10); //캐릭터에게 데미지를 가한다.
//					break;//스레드 종료
//				}
				
				balloon.setLocation(x,y); //풍선 위치 이동
				try {
					
					Thread.sleep(fallingSpeed);
				}catch(InterruptedException e) {
					// TODO Auto-generated catch block
					break;
				}
			}
		}
		
		
		
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(image,0,0,getWidth(),getHeight(),null);
	}
	
}