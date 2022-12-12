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
 * ���� �ý������� �ۼ�
 * ���帶�� ������ ǳ���� ���� �������ְ�, �ش� ǳ���� ��� ó���ϸ� ���尡 ����Ǵ� �������
 * ���尡 ����Ǹ� ������ �����带 ������Ű��, ���͸� ����, ���� ������ �����Ѵ�(ǳ�� ���� �ӵ�, ǳ�� ����)
 * ���� ���� ���� ������ �����ϰ�, ���ӿ��� ������� ���� �������� �������� ���⸦ �����Ҽ� �ֵ����� 
 * 1���忡�� ���� ����� ���� ���ϸ� ���⸦ ���� ���ϵ��� �˸��� �ݾ����� �����Ұ�, ���� �̿��� �ٸ� ������ �����غ���
*/

//���� �гο��� ���ʿ� �پ ������ ����Ǵ� �г��� ����
public class GameRunningPanel extends JPanel {
	private GameFrame parent;
	//���ӿ� ���� Ư��
	//���帶�� ������ ǳ���� �ִ� ���� => �ش� ǳ���� ��� ����� ���� ����� �Ѿ����
	private static final int ROUND1BALLONCOUNT = 10; //1���忡�� 10���� ǳ��
	private static final int ROUND2BALLONCOUNT = 20; //2���忡�� 20���� ǳ��
	private static final int ROUND3BALLONCOUNT = 30; //3���忡�� 30���� ǳ��
	
	private int weaponPower; //������� ������ ���ݷ�
	private int characterType; //����ڰ� ������ ĳ���Ͱ� ���������� ����
	
	//ǳ���� �������� �ӵ� => ĳ���� Ư���� ���� �޶���
	private int ballonSpeed;//ǳ���� �������� �ð� => ������ �Ǵ� �ð�(�и��ʴ���)
	//�ٲٱ� ���õɰ�쿡 true�� ����
	private boolean luckyChance = false; //�����ٲ��� Ư��, true��� ����Ȯ���� ǳ���� �߰�Ÿ�� ����
	
	//ǳ���� �������� ������
	//ǳ���� �޸� �ܾ �ùٸ� �ܾ����� Ȯ���ϴ� �޼ҵ�
	//��� �̹���
	private ImageIcon bgImageicon = new ImageIcon("gamePanelBackgroundImage.png");
	private Image gamePanelBackgroundImage = bgImageicon.getImage();
	
	
	
	private int ballonSpawnTime; //ǳ�� �ϳ��� �����Ǵµ� �ɸ��� �ð�
	private WordList wordList = new WordList("words.txt"); //�ܾ� ����Ʈ ����
	
	
	private BallonSpawnThread ballonSpawnThread; //ǳ���� �����ϴ� ������
	private boolean gameOn = false;
	
	//�г� ����
	private ControlPanel controlPanel;
	private StatusPanel statusPanel;
	
	//ǳ���� �����ϱ� ���� ����
	private Balloon balloon;
	private Vector<Balloon> balloonVector = new Vector<Balloon>();
	
	private Vector<JLabel> wordVector = new Vector<JLabel>(); //�ӽ÷� String���� ����
	//ǳ���� �������� �����带 ���ͷ� ����(�ؽ����� ����ϴ� ���⵵ ����غ���)
	
	public GameRunningPanel(StatusPanel statusPanel,int characterType,GameFrame parent) {
		this.parent = parent;
		this.characterType = characterType; //ĳ���� Ÿ�� ����
		this.statusPanel = statusPanel;
		setLayout(null);
		setSize(1000,900);
		
		
		//�����гο��� Ȩ ȭ������ ���ư��� �ֵ��� �ϱ� ���� => ���� ������ �ٽ��ϱ⿡ ���
		//��������� ��� ���͸� ����,�����带 ������Ű��, Ȩȭ������ ���ư��� �ٽ� ������ ������Ѿ���
		
		 //Ȩ ��ư
  		JLabel homeButton = new JLabel("��ư �׽�Ʈ");
  		homeButton.setSize(100,100);
  		homeButton.setLocation(100,100);
  		homeButton.addMouseListener(new ButtonClickedEvent(parent,parent.BEGINNING_PANEL));
  		add(homeButton);
		
		
		
		//������ ĳ���Ϳ� ���� ���� ����
		switch(characterType) {
		case 0:
			ballonSpeed = 800;
			break;
		case 1: //�Ѽ������� ��� => ��ü�÷�
			ballonSpeed = 1500;//ǳ���� ������ ����.
			break;
		case 2: //�����ٲ��� ��� => 50���� Ȯ���� �߰�Ÿ
			ballonSpeed= 200;
			luckyChance = true; //��Ű���� Ȱ��ȭ
			break;
		}
		
		weaponPower = 1; //�⺻ ������ �Ŀ��� 1
		
		JButton stopBalloon = new JButton("ǳ�� ���߱�");
		stopBalloon.setSize(100,100);
		stopBalloon.setLocation(0,800);
		stopBalloon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				for(int i=0;i<balloonVector.size();i++) {
					Balloon balloon = balloonVector.get(i);
					balloonVector.remove(i);// ���Ϳ��� �����
					balloon.stopFallingThread();
				}
			}
		});
		add(stopBalloon);
		
		ballonSpawnTime = 2000; //ǳ���� �����Ǵµ� �ɸ��� �ð��� 1�ʷ� ����
		
		//1����� ǳ�� ���� ������ ����
		
//		ballonSpawnThread.start(); //ǳ�� ���� ���� => ������ ���� ǳ���� �Ʒ��� �������� ����(ǳ�� ��ü ���� ������)
		gameOn = true; //���� �۵������� ǥ��
		
		//����ڷκ��� �Է¹��� ���� ����
		controlPanel = new ControlPanel(statusPanel);	
		add(controlPanel);
		
		//�����Ѱ������� ����
		GameMangeThread gameThread = new GameMangeThread();
		gameThread.start();
		
		setVisible(true);
	}
	
	
	//������ �����ϴ� ������ �ۼ� => ���� ����, ���ӿ��� ����, ����������� ����,���
	//���ӷ����г� Ŭ���� �ȿ� �ۼ��Ͽ�, �������� �����Ҽ� �ֵ���
	private class GameMangeThread extends Thread{
//		private int ballonCount = 0;//���� ������ ǳ���� ������ ������ ����
		private boolean isGameRun = true;
		private BallonSpawnThread spawnThread; //ǳ���� �����ǰ� �ϴ� ������
		private int gameLevel = 1;
		private boolean isPlayerAlive = true; //�÷��̾��� �������� ǥ��
		
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
			//���� 1���� ����
			isPlayerAlive = true; //�÷��̾��� �������� ǥ��
			ballonSpawnThread = new BallonSpawnThread(ballonSpawnTime,ballonSpeed,statusPanel, gameLevel);
			ballonSpawnThread.start();
		}
		
		//�÷��̾��� ü���� Ȯ���Ͽ� ü���� 0 ���ϰ� �Ǿ��ٸ� true����
		//�׷��� �ʴٸ� false����
		public boolean isPlayerDie() {
			//ĳ������ ü���� 0���ϰ� �Ǹ� ���� ����
			if(statusPanel.getHealth()<=0) {
				//���� ������ ���� => ��� ���� ����
				
				//ǳ�� ���� ������ ����
				ballonSpawnThread.interrupt();
				
				//�������� ǳ�� ������ ���� => ���͸� ���鼭
				for(int j=0;j<balloonVector.size();j++) {
					
					Balloon balloon = balloonVector.get(j);
					balloon.stopFallingThread(); //�������� ������ ����
				}
				
				return true; //�÷��̾� ����� Ʈ�� ����
//				System.exit(0);
			}
			else return false;
		}
		
		
		//������ �����Ȳ�� ���� ������ ����
		@Override
		public void run() {
			while(isGameRun) {
				//ǳ���� �������� �Ʒ��� �������� ���Ϳ��� ���� => ������ ��� ǳ�� �˻�
				for(int i=0;i<balloonVector.size();i++) {
					try {
						Balloon fallingBalloon = balloonVector.get(i);
						
						int x = fallingBalloon.getX();
						int y = fallingBalloon.getY();
						
						
						//ǳ�� ��ġ �˻�
						//���� ���� ���Ϸ� �������� ǳ�� ��ü ����c
						if(y>=500) {
							remove(fallingBalloon);
							balloonVector.remove(fallingBalloon); //��ü���� ����
							
//							setVisible(false);
							//ü���� ��ƾ���
							statusPanel.getDamage(10); //ĳ���Ϳ��� �������� ���Ѵ�.
							
							//���� ü���� 0�� �Ǿ����� Ȯ���ؾ��� => ǳ���� �������� ����ϴ� ��� ���
							if(isPlayerDie()&&isPlayerAlive){ //�÷��̾ ���� ����ִ°�쿡 ���ؼ���
								isGameRun = false; //���� ������ ����
								System.out.println("�÷��̾� ���");
								isPlayerAlive = false; //���ó��
								break;
								//���� ���â ��� => ���̵� �Է¹ޱ� => �ٽ��ϱ� ��ư, �������� ��ư �����ֱ�
								
								
							}
							
						}
					}catch(Exception E) {
						System.out.println("deleted!");
					}
					
				}
				
				
				
				//ǳ���� ���� ������ �Ǿ���,������ ǳ���� ��� �������� => ���� ����� �Ѿ�ų�, ���� Ŭ��� �˸�
				if(!ballonSpawnThread.getIsRun()&&balloonVector.isEmpty()) {
					//������ 3������� ��� �������� Ȯ���ʿ�
					if(gameLevel>3) {
						System.out.println("���� ����! ���̵� �Է��ϼ���!!");
						//�۵����� ������� GameMangeThread�̿ܿ��� ���� �ɰ��̹Ƿ� �ش� �����常 �����Ű���
						isGameRun = false;
					}
					else { //3���� ������ ���忡���� ������ ���ο� ǳ�������带 ����
						System.out.println("����â ����");//����â���� ��ư�� ���� ���� ���������� �Ѿ�Բ�
						//���� ����
						gameLevel+=1;
						ballonSpawnThread = new BallonSpawnThread(ballonSpawnTime,ballonSpeed,statusPanel,gameLevel);
						ballonSpawnThread.start();
					}
				}
				
				//���� ����ִ� ��쿡 ���ؼ��� ����
				if(isPlayerDie()&&isPlayerAlive){
					isGameRun = false; //���� ������ ����
					//���� ���â ��� => ���̵� �Է¹ޱ� => �ٽ��ϱ� ��ư, �������� ��ư �����ֱ�
					isPlayerAlive = false; //���ó�� 
					System.out.println("�÷��̾� ���");
				}
			}
		}
			
	}
	
	
	
	//������ �ۼ�
	//���� �ð����� ǳ���� �����ϰ� �ش� ǳ���� ���������� �ϴ� �����带 ������ ���δ�.
	//���忡 ���� ǳ���� ���� Ȯ���� �ο��Ѵ�.
	private class BallonSpawnThread extends Thread{
		private int spawnSpeed; //ǳ���� �����Ǵ� �ӵ� => ���帶�� �޶�������
		private int percentage; //ǳ������ Ȯ�� => ���忡 ���� �޶�������
		private String word;
		private JLabel label; //�ӽ÷� �󺧷� ���� => ���� ǳ�� ��ü�� ����
		private int fallingSpeed; //�������� �ð�
		private int x; //ǳ���� ������ ���� ��ġ�� ����
		
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
		
		
		private int gameLevel = 3; //������ ���� ���带 ���
		//������ǳ���� ����Ȯ��
		private int starBalloonPercentage=5; //��Ÿǳ�� ����Ȯ�� 5�ۼ�Ʈ
		private int coinBalloonPercentage=3; //����ǳ�� ����Ȯ�� 3�ۼ�Ʈ
		
		
		//�� ����ǳ���� ����Ȯ��
		private int redBalloonPercentage;
		private int blueBalloonPercentage;
		//���ǳ���� �� �̿��� Ȯ���̹Ƿ� ���� ����x
		private int BallonCount;
		
		//ǳ���� ������� �յ��� ��ġ���� �����ǵ��� �ϱ�����
		private int lastPosition = 0; //ù��°�� ������ ��ġ�� ���� ���Ƿ� x�� ����
		private int lastPosition2 = 5; //�ι�°�� ������ ��ġ�� ����
		
		private StatusPanel statusPanel; //�������ͽ� �гο� ���� ���۷���
		
		//ǳ�� ������ �������ͽ�â�� ���� ���۷����� �Ѱ������
		//=>ǳ���� �������� �����忡�� ǳ���� ������ �������� �������ͽ�â�� ������ ���Ҽ� �ֵ���
		public BallonSpawnThread(int spawnSpeed,int fallingSpeed,StatusPanel statusPanel,int gameLevel) {
			this.gameLevel = gameLevel; //���� ����
			System.out.println("���� : "+ gameLevel);
//			gameLevel = 3;
			//������ ���� ����ǳ�� Ȯ����, ������ ǳ���� �� ����
			switch(gameLevel) {
			case 1:
				BallonCount = ROUND1BALLONCOUNT; //10��
				//������ ǳ�� ��� �߰�
				//����ǳ�� : �Ķ�ǳ�� = �� 8 : 2 ��������
				redBalloonPercentage = 74;
				blueBalloonPercentage = 18;
				//���ǳ���� �� �̿��� Ȯ��
				break;
			case 2:
				BallonCount = ROUND2BALLONCOUNT; //20��
				//����ǳ�� : �Ķ�ǳ�� = �� 5 : 3 : 2 ��������
				redBalloonPercentage = 46;
				blueBalloonPercentage = 27;
				//���ǳ���� 19;
				//���ǳ���� �� �̿��� Ȯ��
				break;
			case 3:
				BallonCount = ROUND3BALLONCOUNT; //30��
				//����ǳ�� : �Ķ�ǳ�� : ���ǳ�� = 3 : 4 : 3 ���� ����
				redBalloonPercentage = 28;
				blueBalloonPercentage = 37;
				//���ǳ���� �� �̿��� Ȯ��
				break;
			}
			
			
			this.fallingSpeed = fallingSpeed;
			this.spawnSpeed = spawnSpeed;
			this.statusPanel = statusPanel;
		}
		
		@Override
		public void run() {
			
			
			//���� ������ �޽���
			
			
//			while(true) {
//				
//			}
			//���ϴ� ���ڸ�ŭ ǳ���� �����ϵ���
			for(int i=0;i<BallonCount;i++) {
				int position;
				word = wordList.getWord(); //���� �ܾ� ����
				//������ ǳ���� ���� Ȯ������
				
				
				//ǳ���� ���� ��ġ ���� => ǳ������ �� ��ġ�� �ʵ���
				//�̹����� ���α��̸� �����ͼ� ���ӷ����г��� �������� ������ ������ ����
				while(true) {
					position = (int)(Math.random()*10);//0~9������ ���� ���� => ǳ���� ������ ��ġ�� ���Ƿ� �����ϱ� ����
					if(position!=lastPosition&&position!=lastPosition2) {//���� 2���� ��ġ�� �ƴѰ��� ����
						lastPosition2 = lastPosition; //ù��° ������ġ�� �ι�° ������ġ�� �ű��
						lastPosition = position; //���� ������ ��ġ�� ���� => ���� 2���� ��ġ�� �ƴѰ��� �����ǰ���
						//System.out.println(position);
						break; //Ż��
					}
						
				}	
				
				//�������� ���� ǳ���� x��ǥ ����
				x = (int)(Math.random()*100) + (70*position); //������ �����Ǿ��� 2���� ��ġ�� ���������ʵ���
				//ǳ���� ������ ��ġ ����
				int y = -100; //�ӽ÷� 0��ġ���� �����ǵ���
				
				//ǳ�� �� Ȯ������
				int random = (int)(Math.random()*100)+1; //0~100������ ���� ����
				int ballonType; //ǳ���� ������ Ÿ�� ����
				System.out.println("���� : "+random);
				//0~3������ ���� => 3�ۼ�Ʈ
				if(0<=random&&random<coinBalloonPercentage) //3�ۼ�Ʈ Ȯ���� ����ǳ�� ����
					ballonType = 3; //����ǳ��
				else if(coinBalloonPercentage<=random&&random<coinBalloonPercentage+starBalloonPercentage) //5�ۼ�Ʈ Ȯ���� ��Ÿǳ��(����ġ2��)����
					ballonType = 4; //��Ÿ����
				else if(coinBalloonPercentage+starBalloonPercentage<=random&&random<coinBalloonPercentage+starBalloonPercentage+redBalloonPercentage)
					ballonType = 0; //����
				else if(coinBalloonPercentage+starBalloonPercentage+redBalloonPercentage<=random&&random<=coinBalloonPercentage+starBalloonPercentage+redBalloonPercentage+blueBalloonPercentage)
					ballonType = 1; //�Ķ�ǳ��
				else //�������� �����
					ballonType = 2; 
				
				
				//ǳ�� ���� => (ǳ�� Ÿ��, �ܾ�)
				//ǳ�� ������ �������ͽ� �гο� ���� ������ �ѱ� => �������ͽ� �гο� ������ ���Ҽ� �ֵ���
				balloon = new Balloon(ballonType,word,fallingSpeed,statusPanel);
				balloon.setVisible(true);
				balloon.setSize(300,300);
				balloon.setLocation(x,y);
				add(balloon); //�гο� ���̱�
				balloonVector.add(balloon);//���Ϳ� ǳ�� ����
				//System.out.println(x+","+y);
				//ǳ�� ������ ǳ�� �����ڿ� �Ű������� ������ ���ڿ� �ܾ �־ ����
				
				//ǳ�� ��ü������ �ش� ���ڸ� ���� ������ ǳ���� Ÿ��(����,�Ķ�,���,etc)�� ����
				
//				//�׽�Ʈ������ JLabel�� �ۼ�
//				label = new JLabel(word); //���� �ܾ �ٿ��� ����
//				label.setSize(100,100);
//						
//				label.setLocation(x,y); //�ش� ��ġ�� ����
//				add(label); //�гο� ���̱�
				//wordVector.add(label);//���Ϳ� �ܾ� ����
				
				//ǳ���� ���� ��ġ�� ����ǵ��� �ϴ� ������ �ۼ�
				//ǳ���� �������� �ϴ� �����忡�� ǳ���� �������� �ӵ���, �ش� �󺧿� ���� ������ �Ѱ���
//				fallingThread = new ballonFallingThread(label,700);
//				fallingThread.start();
				
				try {
					Thread.sleep(spawnSpeed);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					//���ͷ� �߻���
					break; //������ ����
				}
			}
			
			
			System.out.println("������ ����");
			isRun = false;
			//���� ���� ����
			controlPanel.initDoublePoint(); //����ġ 2��=>1��� �ʱ�ȭ
//			while(true) {
//				
//			}
		}
	}
	
	
	class ControlPanel extends JPanel {
		private JTextField input = new JTextField(15); //�ܾ��� �Է¹��� ���� ����
		private StatusPanel statusPanel;
		private int doublePoint = 1; //��Ÿǳ���� ��Ʈ������ �� ���� ���� ����ġ�� 2��� �޵��� �ϱ�����
		private Clip clip;
		
		//����ġ2�� => 1��� �ʱ�ȭ
		public void initDoublePoint() {
			doublePoint = 1; 
		}
		
		//������ 
		public ControlPanel(StatusPanel statusPanel) {
			this.statusPanel = statusPanel;
			setLayout(null);
			//�г��� ������ ��ġ ����
			setBounds(0,800,1000,100); //0,800�� ��ġ���� 800x100ũ���� ��Ʈ�� �г� ����
			setBackground(Color.cyan); //�� �����Ǿ����� Ȯ���ϱ� ���� �ӽû��� ����
			
//			this.setLayout(new FlowLayout());
//			this.setBackground(Color.LIGHT_GRAY);
			input.setLocation(10,10);
			input.setSize(800,50);
			add(input);
			
			//����Ű�� ������ �̺�Ʈ �߻�
			input.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					JTextField tf = (JTextField)e.getSource();
					String text = tf.getText(); //�ؽ�Ʈ�� �Էµ� �ܾ �������� ������

					//System.out.println(text);
					//�ܾ ��ġ�Ѵٸ� => �ش� �ܾ ���� ������ ����,����	
					if(isMatch(text)) {
						//����� ȿ���� ������
						try {
							clip = AudioSystem.getClip();
							File audioFile = new File("Correct.wav");
							AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
							clip.open(audioStream);
						}catch(Exception E) {
							System.out.println("���� ���� ���� ����!");
						}
						clip.start(); // ��ư�� Ŭ�������� �Ҹ��� ������
						
						//ǳ���� ü���� 0�̵Ǿ������� �����߰�
//						statusPanel.plusScore(10); //���� �߰�
						
//						System.out.println("�ܾ� ��ġ��");
						
					}
					else { //������ �ƴҽ� => ü���� 5����
						try {
							clip = AudioSystem.getClip();
							File audioFile = new File("Wrong.wav");
							AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
							clip.open(audioStream);
						}catch(Exception E) {
							System.out.println("���� ���� ���� ����!");
						}
						clip.start(); // ��ư�� Ŭ�������� �Ҹ��� ������
						statusPanel.getDamage(5); //����� 5�� ������
					}
					tf.setText(""); //�ؽ�Ʈ ���ڿ� ���� ���� ����� => �ܾ �������� ȿ��(�ӽ÷�)				
				}
			});
		}
		
		//ǳ������ �ȿ� �ִ� �ܾ����� Ȯ���ϴ� �޼ҵ�
		@SuppressWarnings("unlikely-arg-type")
		public boolean isMatch(String text) {
			for(int i=0;i<balloonVector.size();i++) {
				
				Balloon balloon = balloonVector.get(i);
				if(balloon.getWord().equals(text)) { //���� ���� �ܾ�� ��ġ�Ѵٸ�
//					System.out.println("�ܾ� ��ġ");
					
					if(luckyChance) { //��Ű���� ȿ���� Ȱ��ȭ �Ǿ��ִٸ�
						//50�ۼ�Ʈ�� Ȯ���� �߰� ������ ���Ѵ�.

						int random = (int)(Math.random()*100);
						System.out.println(random);
						if(random%2!=0) {
							//ǳ���� �߰� ������ ���Ѵ�.(������ �����ϸ� �ٲٵ� ����)
							System.out.println("�߰�����!");
							balloon.getDamage(weaponPower+1);
						}
						else
							balloon.getDamage(weaponPower); 
//						
					}
					else //�����ٲٰ� �ƴѰ�쿡�� luckyChance ��Ȱ��ȭ, ���⸸ŭ �������� ���Ѵ�.
						balloon.getDamage(weaponPower); //������ ������ ��ŭ ǳ���� ���ظ� ���Ѵ�.
					
					//���� ǳ���� ü���� �����ͼ� Ȯ��
					int ballonHealth = balloon.getHealth();
					
					//ǳ���� ü���� 0���ϰ� �Ǹ� ǳ���� �����Ѵ�.
					if(ballonHealth<=0) {
						//ǳ���� ü���� 0���ϰ��Ǹ� ���� �߰�
						//ǳ�� �������� ������ ������ �ο��Ѵ�.
						//����ǳ��(0+100��), �Ķ�ǳ��(100+100��), ���ǳ��(200+100��)
						int balloonType = balloon.getBalloonType(); //���� ǳ���� �Ÿ������üũ
						//ǳ���� ������ ���� ���� ������ ������ �ο�
						int score = 0; 
						int coin = 0;
						switch(balloonType) {
						case 0: //����ǳ�� 150��, 100��
							score = 150;
							coin = 100;
							break;
						case 1: //�Ķ�ǳ�� 300��, 200��
							score = 300;
							break;
						case 2: //���ǳ�� 600��, 300��
							score = 600;
							break;
						case 3: //����ǳ���� ���
							score = 777; //777�� ȹ��
							coin = 1000; //1000���� ȹ��
						case 4: //��Ÿǳ���� ���
							score = 777; //777�� ȹ��
							coin = 77;
							//����ġ 2�� Ȱ��ȭ
							doublePoint = 2; //����ġ �ο��� 2��� ����
							
						}
						statusPanel.plusScore(score*doublePoint);//��Ÿǳ�� ��Ʈ���������� doublePoint�� 1
						statusPanel.plusCoin(coin);
						
						balloon.setVisible(false); //�Ⱥ��̵��� ó��
						balloon.stopFallingThread(); //ǳ�� ���߱�
						balloonVector.remove(balloon); //���Ϳ��� ǳ������
						
						remove(balloon); //�гο� �޸� ǳ����ü�� �����.
					}
					return true; //������ ��ġ�Ұ�� true�� �����Ѵ�
				}
			}
			//�ݺ��Ŀ��� �ܾ ���ٸ�
//			System.out.println("�ܾ� ����ġ");
			return false; //Ʋ�� ���� �Է��Ұ�� fasle�� �����Ѵ�.
		}
	}
	
	//���ӷ����г��� ��׶��� �̹��� �׸���
	@Override
    public void paintComponent(Graphics g) {
       super.paintComponent(g); //�׷��� ������Ʈ ����
       //��� �̹���
       g.drawImage(gamePanelBackgroundImage, 0, 0, this.getWidth(),this.getHeight(),null); //�̹����� �׷����� ���� �˸����� �ʱ�
    }
}
