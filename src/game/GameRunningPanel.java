package game;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


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
	
	
	private Vector<String> wordVector = new Vector<String>(); //�ӽ÷� String���� ����
	//ǳ���� �������� �����带 ���ͷ� ����(�ؽ����� ����ϴ� ���⵵ ����غ���)
	
	public GameRunningPanel() {
		setLayout(null);
		setSize(1000,900);
		ballonSpawnTime = 1000;
		ballonSpawnThread = new ballonSpawnThread(ballonSpawnTime);
		ballonSpawnThread.start(); //ǳ�� ���� ���� => ������ ���� ǳ���� �Ʒ��� �������� ����
		setVisible(true);
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
				
				int x = (int)(Math.random()*900); //���ӷ��� �г��� ���� ���̴� 1000
				int y = 10; //�ӽ÷� 10��ġ���� �����ǵ���
				System.out.println(x+","+y);
				label.setLocation(x,y); //�ش� ��ġ�� ����
				add(label); //�гο� ���̱�
				
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
