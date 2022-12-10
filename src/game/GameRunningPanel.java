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



//���� �гο��� ���ʿ� �پ ������ ����Ǵ� �г��� ����
public class GameRunningPanel extends JPanel {
	//ǳ���� �������� ������
	//ǳ���� �޸� �ܾ �ùٸ� �ܾ����� Ȯ���ϴ� �޼ҵ�
	//��� �̹���
	private ImageIcon bgImageicon = new ImageIcon("gamePanelBackgroundImage.png");
	private Image gamePanelBackgroundImage = bgImageicon.getImage();
	private int ballonSpawnTime; //ǳ�� �ϳ��� �����Ǵµ� �ɸ��� �ð�
	private WordList wordList = new WordList("words.txt"); //�ܾ� ����Ʈ ����
	private ballonSpawnThread ballonSpawnThread;
	private boolean gameOn = false;
	private ControlPanel controlPanel;
	
	private StatusPanel statusPanel;
	
	
	private Vector<JLabel> wordVector = new Vector<JLabel>(); //�ӽ÷� String���� ����
	//ǳ���� �������� �����带 ���ͷ� ����(�ؽ����� ����ϴ� ���⵵ ����غ���)
	
	public GameRunningPanel(StatusPanel statusPanel) {
		this.statusPanel = statusPanel;
		setLayout(null);
		setSize(1000,900);
		
		
		ballonSpawnTime = 1000;
		ballonSpawnThread = new ballonSpawnThread(ballonSpawnTime);
		ballonSpawnThread.start(); //ǳ�� ���� ���� => ������ ���� ǳ���� �Ʒ��� �������� ����
		gameOn = true; //���� �۵������� ǥ��
		
		//����ڷκ��� �Է¹��� ���� ����
		controlPanel = new ControlPanel(statusPanel);	
		add(controlPanel);
		
		setVisible(true);
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
						tf.setText(""); //�ؽ�Ʈ ���ڿ� ���� ���� ����� => �ܾ �������� ȿ��(�ӽ÷�)
					}
					
//					if(match) {
//						gamePanel.printResult("����");
//						gamePanel.stopGame(); //�ܾ ��ġ�Ѵٸ� 
//						gamePanel.startGame(); //
//					}
									
				}
			});
		}
		
		
		//���� �ȿ� �ִ� �ܾ����� Ȯ���ϴ� �޼ҵ�
		@SuppressWarnings("unlikely-arg-type")
		public boolean isMatch(String text) {
			for(int i=0;i<wordVector.size();i++) {
				//������ ������ ���̹Ƿ� �󺧿��� �ܾ �̾ƾ���
				JLabel label = wordVector.get(i);
				if(label.getText().equals(text)) { //���� ���� �ܾ�� ��ġ�Ѵٸ�
					System.out.println("�ܾ� ��ġ");
					return true;
				}
			}
			//�ݺ��Ŀ��� �ܾ ���ٸ�
			System.out.println("�ܾ� ����ġ");
			return false;
		}
	}
	
	//������ �ۼ�
	//���� �ð����� ǳ���� �����ϰ� �ش� ǳ���� ���������� �ϴ� �����带 ������ ���δ�.
	private class ballonSpawnThread extends Thread{
		private int spawnSpeed; //ǳ���� �����Ǵ� �ӵ� => ���帶�� �޶�������
		private int percentage; //ǳ������ Ȯ�� => ���忡 ���� �޶�������
		private String word;
		private JLabel label; //�ӽ÷� �󺧷� ���� => ���� ǳ�� ��ü�� ����
		
		private ballonFallingThread fallingThread; //ǳ���� �������� �ϴ� ������
		
		public ballonSpawnThread(int spawnSpeed) {
			this.spawnSpeed = spawnSpeed;
		}
		
		@Override
		public void run() {
			while(true) {
				word = wordList.getWord(); //���� �ܾ� ����
				//������ ǳ���� ���� Ȯ������
				
				//ǳ�� ����
				//ǳ�� ������ ǳ�� �����ڿ� �Ű������� ������ ���ڿ� �ܾ �־ ����
				
				//ǳ�� ��ü������ �ش� ���ڸ� ���� ������ ǳ���� Ÿ��(����,�Ķ�,���,etc)�� ����
				
				//�׽�Ʈ������ JLabel�� �ۼ�
				label = new JLabel(word); //���� �ܾ �ٿ��� ����
				label.setSize(100,100);
				
				int x = (int)(Math.random()*950); //���ӷ��� �г��� ���� ���̴� 1000
				int y = 10; //�ӽ÷� 10��ġ���� �����ǵ���
//				System.out.println(x+","+y);
				label.setLocation(x,y); //�ش� ��ġ�� ����
				add(label); //�гο� ���̱�
				wordVector.add(label);//���Ϳ� �ܾ� ����
				
				//ǳ���� ���� ��ġ�� ����ǵ��� �ϴ� ������ �ۼ�
				//ǳ���� �������� �ϴ� �����忡�� ǳ���� �������� �ӵ���, �ش� �󺧿� ���� ������ �Ѱ���
				fallingThread = new ballonFallingThread(label,700);
				fallingThread.start();
				
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
	
	//ǳ���� ���������� �ϴ� ������ �ۼ�
	private class ballonFallingThread extends Thread{
		private int fallingSpeed;
		private JLabel label;
		
		//ǳ���� �������� �ӵ��� ���� ���� => �ش� �󺧿� ���������� �����ϴ� �����带 �ۼ��ϱ� ����
		public ballonFallingThread(JLabel label, int fallingSpeed) {
			this.fallingSpeed = fallingSpeed;
			this.label = label; //������ ������
		}
		
		@Override
		public void run() {
			while(true) {
				int x = label.getX();
				int y = label.getY() + 10; //�ѹ��� 10�ȼ��� ����������
				label.setLocation(x,y);
				
				try {
					Thread.sleep(fallingSpeed); //�ش� �� ���Ŀ� �ٽ� ��ġ ����
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	
	@Override
    public void paintComponent(Graphics g) {
       super.paintComponent(g); //�׷��� ������Ʈ ����
       //��� �̹���
       g.drawImage(gamePanelBackgroundImage, 0, 0, this.getWidth(),this.getHeight(),null); //�̹����� �׷����� ���� �˸����� �ʱ�
    }
}
