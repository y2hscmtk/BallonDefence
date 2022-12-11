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



//���� �гο��� ���ʿ� �پ ������ ����Ǵ� �г��� ����
public class GameRunningPanel extends JPanel {
	//���ӿ� ���� Ư��
	private int characterType; //����ڰ� ������ ĳ���Ͱ� ���������� ����
	//ǳ���� �������� �ӵ� => ĳ���� Ư���� ���� �޶���
	private int ballonSpeed;//������ �Ǵ� �ð�(�и��ʴ���) => 200�и���
	//�ٲٱ� ���õɰ�쿡 true�� ����
	private boolean luckyChance = false; //�ٲٲ����� Ư��, true��� ����Ȯ���� ǳ���� �ϳ� �� ��Ʈ��
	
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
		case 2: //�ٲٱ���� ��� => ��Ű���� ȿ��
			ballonSpeed= 200;
			luckyChance = true; //��Ű���� Ȱ��ȭ
			break;
		}
		
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
		ballonSpawnThread = new BallonSpawnThread(ballonSpawnTime,ballonSpeed);
		ballonSpawnThread.start(); //ǳ�� ���� ���� => ������ ���� ǳ���� �Ʒ��� �������� ����(ǳ�� ��ü ���� ������)
		gameOn = true; //���� �۵������� ǥ��
		
		//����ڷκ��� �Է¹��� ���� ����
		controlPanel = new ControlPanel(statusPanel);	
		add(controlPanel);
		
		setVisible(true);
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
		private int lastPosition = 9; //������ ������ ��ġ�� ���� ���Ƿ� x�� ����
		
//		private ballonFallingThread fallingThread; //ǳ���� �������� �ϴ� ������
		
		public BallonSpawnThread(int spawnSpeed,int fallingSpeed) {
			this.fallingSpeed = fallingSpeed;
			this.spawnSpeed = spawnSpeed;
		}
		
		@Override
		public void run() {
			while(true) {
				int position;
				System.out.println("ǳ�������� ������");
				word = wordList.getWord(); //���� �ܾ� ����
				//������ ǳ���� ���� Ȯ������
				
				
				//ǳ���� ���� ��ġ ���� => ǳ������ �� ��ġ�� �ʵ���
				//�̹����� ���α��̸� �����ͼ� ���ӷ����г��� �������� ������ ������ ����
				while(true) {
					position = (int)(Math.random()*10);//0~9������ ���� ���� => ǳ���� ������ ��ġ�� ���Ƿ� �����ϱ� ����
					if(position!=lastPosition) {//������ ��ġ�� �ƴ϶��
						lastPosition = position; //���� ��ġ�� �����ϰ�
						break; //Ż��
					}
						
				}	
				
				//�������� ���� ǳ���� x��ǥ ����
				x = (int)(Math.random()*100) + (100*position);

				
				
				//ǳ���� ������ ��ġ ����
				
				int y = 0; //�ӽ÷� 0��ġ���� �����ǵ���
				
				//ǳ�� ���� => (ǳ�� Ÿ��, �ܾ�)
				balloon = new Balloon(1,word,fallingSpeed);
				balloon.setVisible(true);
				balloon.setSize(300,300);
				balloon.setLocation(x,y);
				add(balloon); //�гο� ���̱�
				balloonVector.add(balloon);//���Ϳ� ǳ�� ����
				System.out.println(x+","+y);
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
		
		//������ 
		public ControlPanel(StatusPanel statusPanel) {
			this.statusPanel = statusPanel;
			setLayout(null);
			//�г��� ������ ��ġ ����
			setBounds(0,800,1000,100); //0,800�� ��ġ���� 800x100ũ���� ��Ʈ�� �г� ����
			setBackground(Color.cyan); //�� �����Ǿ����� Ȯ���ϱ� ���� �ӽû��� ����
			System.out.println("��Ʈ���г�ȣ���");
			
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

					System.out.println(text);
					//�ܾ ��ġ�Ѵٸ� => �ش� �ܾ ���� ������ ����,����	
					if(isMatch(text)) {
						statusPanel.plusScore(10); //���� �߰�
						
						System.out.println("�ܾ� ��ġ��");
						
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
					
					balloon.setVisible(false); //�Ⱥ��̵��� ó��
					balloon.stopFallingThread(); //ǳ�� ���߱�
					balloonVector.remove(balloon);
					remove(balloon); //�гο� �޸� ǳ����ü�� �����.
					System.out.println("�ܾ� ��ġ");
					return true;
				}
			}
			//�ݺ��Ŀ��� �ܾ ���ٸ�
			System.out.println("�ܾ� ����ġ");
			return false;
		}
	}
	
	@Override
    public void paintComponent(Graphics g) {
       super.paintComponent(g); //�׷��� ������Ʈ ����
       //��� �̹���
       g.drawImage(gamePanelBackgroundImage, 0, 0, this.getWidth(),this.getHeight(),null); //�̹����� �׷����� ���� �˸����� �ʱ�
    }
}
