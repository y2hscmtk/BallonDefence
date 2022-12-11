package game;

import java.awt.Color;
import java.awt.Image;
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
	//���ӿ� ���� Ư��
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
	
	public GameRunningPanel(StatusPanel statusPanel,int characterType) {
		this.characterType = characterType; //ĳ���� Ÿ�� ����
		this.statusPanel = statusPanel;
		setLayout(null);
		setSize(1000,900);
		
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
		ballonSpawnThread = new BallonSpawnThread(ballonSpawnTime,ballonSpeed,statusPanel);
		ballonSpawnThread.start(); //ǳ�� ���� ���� => ������ ���� ǳ���� �Ʒ��� �������� ����(ǳ�� ��ü ���� ������)
		gameOn = true; //���� �۵������� ǥ��
		
		//����ڷκ��� �Է¹��� ���� ����
		controlPanel = new ControlPanel(statusPanel);	
		add(controlPanel);
		GameMangeThread gameThread = new GameMangeThread();
		gameThread.start();
		
		setVisible(true);
	}
	
	
	
	
	
	//������ �����ϴ� ������ �ۼ� => ���� ����, ���ӿ��� ����
	//���ӷ����г� Ŭ���� �ȿ� �ۼ��Ͽ�, �������� �����Ҽ� �ֵ���
	private class GameMangeThread extends Thread{
		
		//������ �����Ȳ�� ���� ������ ����
		@Override
		public void run() {
			while(true) {
				//ĳ������ ü���� 0���ϰ� �Ǹ� ���� ����
				if(statusPanel.getHealth()<=0) {
					//���� ������ ���� => ��� ���� ����
					
					//ǳ�� ������ ���� => ���͸� ���鼭
					
					//���� ����
					
					//ǳ�� ���� ������ ����
					
					//���� ���â ��� => ���̵� �Է¹ޱ� => �ٽ��ϱ� ��ư, �������� ��ư �����ֱ�
					
					System.out.println("���α׷� ����");
					System.exit(0);
				}
			}
		}
			
	}
	
	
	
	//������ �ۼ�
	//���� �ð����� ǳ���� �����ϰ� �ش� ǳ���� ���������� �ϴ� �����带 ������ ���δ�.
	private class BallonSpawnThread extends Thread{
		private int spawnSpeed; //ǳ���� �����Ǵ� �ӵ� => ���帶�� �޶�������
		private int percentage; //ǳ������ Ȯ�� => ���忡 ���� �޶�������
		private String word;
		private JLabel label; //�ӽ÷� �󺧷� ���� => ���� ǳ�� ��ü�� ����
		private int fallingSpeed; //�������� �ð�
		private int x; //ǳ���� ������ ���� ��ġ�� ����
		
		private int BallonCount;
		
		//ǳ���� ������� �յ��� ��ġ���� �����ǵ��� �ϱ�����
		private int lastPosition = 0; //ù��°�� ������ ��ġ�� ���� ���Ƿ� x�� ����
		private int lastPosition2 = 5; //�ι�°�� ������ ��ġ�� ����
		
		private StatusPanel statusPanel; //�������ͽ� �гο� ���� ���۷���
		
		//ǳ�� ������ �������ͽ�â�� ���� ���۷����� �Ѱ������
		//=>ǳ���� �������� �����忡�� ǳ���� ������ �������� �������ͽ�â�� ������ ���Ҽ� �ֵ���
		public BallonSpawnThread(int spawnSpeed,int fallingSpeed,StatusPanel statusPanel) {
			this.fallingSpeed = fallingSpeed;
			this.spawnSpeed = spawnSpeed;
			this.statusPanel = statusPanel;
		}
		
		@Override
		public void run() {
			while(true) {
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
				x = (int)(Math.random()*100) + (70*position);

				//ǳ���� ������ ��ġ ����
				
				int y = -100; //�ӽ÷� 0��ġ���� �����ǵ���
				
				//ǳ�� ���� => (ǳ�� Ÿ��, �ܾ�)
				//ǳ�� ������ �������ͽ� �гο� ���� ������ �ѱ� => �������ͽ� �гο� ������ ���Ҽ� �ֵ���
				balloon = new Balloon(2,word,fallingSpeed,statusPanel);
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
		}
	}
	
	
	class ControlPanel extends JPanel {
		private JTextField input = new JTextField(15); //�ܾ��� �Է¹��� ���� ����
		private StatusPanel statusPanel;
		private Clip clip;
		
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
							System.out.println("����!");
						}
						clip.start(); // ��ư�� Ŭ�������� �Ҹ��� ������
						statusPanel.plusScore(10); //���� �߰�
						
						System.out.println("�ܾ� ��ġ��");
						
					}
					else { //������ �ƴҽ� => ü���� 5����
						try {
							clip = AudioSystem.getClip();
							File audioFile = new File("Wrong.wav");
							AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
							clip.open(audioStream);
						}catch(Exception E) {
							System.out.println("����!");
						}
						clip.start(); // ��ư�� Ŭ�������� �Ҹ��� ������
						statusPanel.getDamage(5);
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
					System.out.println("�ܾ� ��ġ");
					
					if(luckyChance) { //��Ű���� ȿ���� Ȱ��ȭ �Ǿ��ִٸ�
						//50�ۼ�Ʈ�� Ȯ���� �߰� ������ ���Ѵ�.
						
						
						int random = (int)(Math.random()*100);
						System.out.println(random);
						if(random%2!=0) {
							//ǳ���� �߰� ������ ���Ѵ�.(������ �����ϸ� �ٲٵ� ����)
							System.out.println("�߰�����!");
							balloon.getDamage(weaponPower*2);
						}
						else
							balloon.getDamage(weaponPower); 
//						
					}
					else //�ٲٱ� �ƴѰ�쿡��(luckyChance ��Ȱ��ȭ
						balloon.getDamage(weaponPower); //������ ������ ��ŭ ǳ���� ���ظ� ���Ѵ�.
					
					//���� ǳ���� ü���� �����ͼ� Ȯ��
					int ballonHealth = balloon.getHealth();
					
					//ǳ���� ü���� 0���ϰ� �Ǹ� ǳ���� �����Ѵ�.
					if(ballonHealth<=0) {
						balloon.setVisible(false); //�Ⱥ��̵��� ó��
						balloon.stopFallingThread(); //ǳ�� ���߱�
						balloonVector.remove(balloon); //���Ϳ��� ǳ������
						remove(balloon); //�гο� �޸� ǳ����ü�� �����.
					}
					
					//ǳ���� ���� ���������� ����
					//ǳ���� Ÿ�Կ� ���� �ٸ� ó��
//					int balloonType = balloon.getBalloonType();
//					switch(balloonType) {
//					case 0: //����ǳ���� ���
//						
//						
//					}
					return true;
				}
			}
			//�ݺ��Ŀ��� �ܾ ���ٸ�
			System.out.println("�ܾ� ����ġ");
			return false;
		}
	}
	
	//���ӷ����г��� ��׶��� �̹��� �׸���
//	@Override
//    public void paintComponent(Graphics g) {
//       super.paintComponent(g); //�׷��� ������Ʈ ����
//       //��� �̹���
//       g.drawImage(gamePanelBackgroundImage, 0, 0, this.getWidth(),this.getHeight(),null); //�̹����� �׷����� ���� �˸����� �ʱ�
//    }
}
