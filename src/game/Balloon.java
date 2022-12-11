package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import b.Balloon;

//ǳ���� ��ĥ���� ����Ͽ� LayerdPane���� �ۼ�
public class Balloon extends JLayeredPane{
	
	private String text; //ǳ���� �Բ� �޷��� ������ �ܾ�
	private int health = 3;
	//ǳ�� �̹��� ������
	private ImageIcon blue = new ImageIcon("blue.png");
	private ImageIcon blueBalloonIcon = new ImageIcon("blueBalloon.png");//�Ķ� ǳ�� ������
	private BalloonFallingThread fallingThread = null; //ǳ���� �������� �ϴ� ������
	private JLabel balloonImage; //ǳ�� �̹���
	private JLabel answerText; //ǳ�� �ؿ� ���� �ܾ�
	
	//ǳ�� ��ü �ܺο��� ǳ�� ��ü�� �������� �����带 �����Ҽ� �ֵ��� ������ ����
	public BalloonFallingThread getFallingThread() {
		return fallingThread; 
	}
	
	//ǳ���� �޸� �ܾ ����
	public String getWord() {
		return answerText.getText();
	}
	
	//���� ǳ���̹����� �����Ѵ�.
	public JLabel getBalloonImage() {
		return balloonImage;
	}
	
	  
	public Balloon(int health,String text){
		setLayout(null);
		this.health = health;
	    this.text = text;
	      
	    setSize(100,200);
	    
	    //ǳ�� �̹��� �ؿ� �ؽ�Ʈ�� ���̴� �۾�
	    balloonImage = new JLabel(blueBalloonIcon);
	    balloonImage.setSize(blueBalloonIcon.getIconWidth(),blueBalloonIcon.getIconHeight());
		//button.setBounds(0,0,100,160);
		//button.setBackground(Color.RED);
		add(balloonImage);
		
		answerText = new JLabel(text);
		answerText.setOpaque(true); //������ ĥ�ϰ� �ϱ� ����
		answerText.setBackground(Color.white);
		answerText.setFont(new Font("SansSerif",Font.BOLD, 30));
		answerText.setSize(80,40);
		answerText.setLocation(balloonImage.getX()+130,balloonImage.getY()+320);
		add(answerText);
		
		//�ڱ��ڽſ� ���� ������ ǳ���� �������� �ӵ�(sleep)�� �Ѱܼ� ������ ����
		fallingThread = new BalloonFallingThread(this, 200);
		fallingThread.start(); //ǳ�� �������� �ϴ� ������ �۵�
		
		setVisible(true);  
	}
	  	
//	public void abilityInvoke() {
//		actionListener.notify();
//	}
//  
	//ǳ���� ���ظ� ������ ǳ���� �̹����� �����ϰ� �ϱ� ����(���ش� �ùٸ� �ܾ �Է�������)
	public void getDamage(int damage){
		health-=damage;
		switch (health){
		case 1:
			break;
		}
	}
	  
	//ǳ���� ���������� �ϴ� ������ �ۼ�
	private class BalloonFallingThread extends Thread{
		private Balloon balloon = null;
		private int fallingSpeed; //ǳ���� �������� �ӵ�
		private boolean stopFlag = false; //���� �����°��� ���ߵ���
		
		//ǳ���� �������� �ӵ� ����
		public BalloonFallingThread(Balloon balloon,int fallingSpeed) {
			this.fallingSpeed = fallingSpeed; //�������� �ӵ� ����(sleep�ð�)
			this.balloon = balloon; //ǳ�� ������������
		}
		
		
		//���� stopFlag���¸� ����
		public boolean getStopFlag() {
			return stopFlag;
		} 
		
		
		//ǳ���� �������°��� ���ߵ��� stop���
		public void stopBalloon() {
	        stopFlag = true; 
	    }
	    
		//ǳ���� �ٽ� �����̰� �ϴ� �޼ҵ�
	    synchronized public void resumeBalloon() {
	        stopFlag = false; //��ž �÷��׸� false�� ���� => ����ġ �ʵ���
	        this.notify(); //�� ��ü�� ���Ѵ���ϴ� ������ �����
	    }
		
		
		//flag�� false�� �ɶ����� ��ٸ��� �Լ�
        synchronized private void waitFlag() { //wait�Լ��� ���� ���ؼ� synchronized Ű���带 ����ؾ���
           try {
              this.wait(); //������ ���Ѵ�� ���·� ����=>notify()�� �ҷ�����������
           } catch (InterruptedException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
           } //��ٸ��鼭 �ߴܵ� ���·�
        }
		
		
		
		@Override
		public void run() {
			while(true) {
				if(stopFlag) { //stop�÷��װ� �ö���ִٸ�
					waitFlag(); //ǳ�� ���߰� �ϱ�
				}
				int x = balloon.getX();
				int y = balloon.getY()+10;//5�ȼ� �̵��ϰ� �ϱ�����
				
				balloon.setLocation(x,y); //ǳ�� ��ġ �̵�
				try {
					
					Thread.sleep(fallingSpeed);
				}catch(InterruptedException e) {
					// TODO Auto-generated catch block
					break;
				}
			}
		}
		
	}
	
}