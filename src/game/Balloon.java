package game;

import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

//ǳ���� ��ĥ���� ����Ͽ� LayerdPane���� �ۼ�
public class Balloon extends JLayeredPane{
	
	private String text; //ǳ���� �Բ� �޷��� ������ �ܾ�
	private int ballonType; //� ������ ǳ������ ������ ����
	
	private int health = 1; //�⺻ ǳ���� ü��
	private int fallingSpeed; //ǳ���� �������� �ӵ�
	
	
	private StatusPanel statusPanel; //�������ͽ� �гο� ���� ���۷���
	
	//ǳ�� �̹��� ������
	//private ImageIcon blue = new ImageIcon("blue.png");
	private ImageIcon redBalloonIcon = new ImageIcon("redBalloon.png");//���� ǳ�� ������
	private ImageIcon blueBalloonIcon = new ImageIcon("blueBalloon.png");//�Ķ� ǳ�� ������
	private ImageIcon yellowBalloonIcon = new ImageIcon("yellowBalloon.png");//��� ǳ�� ������
	//Ư���� ǳ�� => ����Ȯ���� ���� �����Ұ� 2����?3����?
	private ImageIcon coinBalloonIcon = new ImageIcon("coinBalloon.png"); //���� ǳ�� => 1000���� ȹ��
	private ImageIcon starBalloonIcon = new ImageIcon("starBalloon.png"); //��Ÿ ǳ�� => �� ���尣 ����ġ 2�� 
	
	
	private Image image; 
	private BalloonFallingThread fallingThread = null; //ǳ���� �������� �ϴ� ������
	private JButton balloonImage; //ǳ�� �̹���
	private JLabel answerText; //ǳ�� �ؿ� ���� �ܾ�
	
	//ǳ�� ��ü �ܺο��� ǳ�� ��ü�� �������� �����带 �����Ҽ� �ֵ��� ������ ����
	public BalloonFallingThread getFallingThread() {
		return fallingThread; 
	}
	
	//�ܺο��� ǳ���� ����� �ֵ��� �޼ҵ� �ۼ�
	public void stopFallingThread() {
		if(fallingThread!=null) //null�� �ƴѰ�쿡 ���� ������ ����
			fallingThread.interrupt();
	}
	
//	public void waitFallingThread() {
//		fallingThread.stopBalloon(); //ǳ���� ��� ���߰���
//	}
//	
//	public void resumeFallingThread() {
//		fallingThread.resumeBalloon(); //�ٽ� �����̰� ��
//	}
//	
	//�Ű������� ���� �������� �̹����� ǳ���� �̹��� �����ϴ� �޼ҵ�
	public void setImage(ImageIcon icon) {
		this.image = icon.getImage();
	}
	
	
	//ǳ���� �޸� �ܾ ����
	public String getWord() {
		return answerText.getText();
	}
	
	//���� ǳ���̹����� �����Ѵ�.
	public JButton getBalloonImage() {
		return balloonImage;
	}
	
	//ǳ���� Ÿ���� �����Ѵ�.(ǳ���� �� 0: ����, 1:�Ķ� 2: ���, ü�µ� ����)
	public int getBalloonType() {
		return ballonType;
	}
	
	//ǳ���� ü���� �����Ѵ�.
	public int getHealth() {
		return health;
	}
	
	
	public Balloon(int ballonType,String text,int fallingSpeed, StatusPanel statusPanel){
//		System.out.println("�� ǳ�� ������");
		setLayout(null);
		this.ballonType = ballonType; //ǳ�� Ÿ���� ����
		this.statusPanel = statusPanel; //���۷��� ��������
		
		//ǳ���� Ÿ�Կ� ���� ǳ�� Ư�� ����
		switch(ballonType) {
		case 0: //����ǳ���� ���
			image = redBalloonIcon.getImage();
			break;
		case 1: //�Ķ�ǳ���� ���
			image = blueBalloonIcon.getImage();
			health = 2;
			break;
		case 2: //���ǳ���� ���
			image = yellowBalloonIcon.getImage();
			health = 3;
			break;
		case 3: //��Ʈ����� 1000��带 ȹ���ϴ� ǳ�� => ȿ������ ����غ���
			image = coinBalloonIcon.getImage();
			break;
		case 4: //��Ʈ����� �� ���嵿�� ������ 2��� �޴� ����
			image = starBalloonIcon.getImage();
			break;
		}
		
		
	    this.text = text;
	    
//	    //�ܾ���� �׽�Ʈ��
//	    this.text = "ABCDE"; //10������ ���
	    //9���ڰ� �� ������
	    
	    this.fallingSpeed = fallingSpeed; ///ǳ���� �������� �ӵ� ����
	    setSize(100,100);
	    setBackground(Color.cyan);
	    
	    
	    balloonImage = new JButton(blueBalloonIcon);
	    balloonImage.setSize(200,800);
	    balloonImage.setLocation(0,0); //ǳ�� ���� x,y��ǥ�� ���̰�
	    balloonImage.setOpaque(true);
	    balloonImage.setBackground(Color.black);
//	    balloonImage.setBounds(0,0,100,160);
		//button.setBounds(0,0,100,160);
		//button.setBackground(Color.RED);
		//add(balloonImage);
		
	    //�ܾ��� ���� Ȯ��
	    int textLength = text.length();
	   
	    
	    //ǳ�� �̹��� �ؿ� �ؽ�Ʈ�� ���̴� �۾�
		answerText = new JLabel(this.text);
		answerText.setOpaque(true); //������ ĥ�ϰ� �ϱ� ����
		answerText.setBackground(Color.white);
		answerText.setFont(new Font("SansSerif",Font.BOLD, 30));
		answerText.setSize(180,42);
		answerText.setLocation(62,getHeight()+136);
		add(answerText);
//		
//		//�ڱ��ڽſ� ���� ������ ǳ���� �������� �ӵ�(sleep)�� �Ѱܼ� ������ ����
		fallingThread = new BalloonFallingThread(this, fallingSpeed);
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
//		case 0: //ü���� 0�̵Ǹ� ǳ������
//			setVisible(false);
		case 1: //ü���� 1�̸� ����ǳ�� �̹���
			setImage(redBalloonIcon);
			break;
		case 2: //ü���� 2�̸� �Ķ�ǳ�� �̹�����
			setImage(blueBalloonIcon);
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
//			System.out.println("ǳ�� �ӵ� : "+fallingSpeed);
			
			this.fallingSpeed = fallingSpeed; //�������� �ӵ� ����(sleep�ð�)
			this.balloon = balloon; //ǳ�� ������������
		}
		
		
//		//���� stopFlag���¸� ����
//		public boolean getStopFlag() {
//			return stopFlag;
//		} 
//		
//		
//		//ǳ���� �������°��� ���ߵ��� stop���
//		public void stopBalloon() {
//	        stopFlag = true; 
//	    }
//	    
//		//ǳ���� �ٽ� �����̰� �ϴ� �޼ҵ�
//	    synchronized public void resumeBalloon() {
//	        stopFlag = false; //��ž �÷��׸� false�� ���� => ����ġ �ʵ���
//	        this.notify(); //�� ��ü�� ���Ѵ���ϴ� ������ �����
//	    }
//		
//		
//		//flag�� false�� �ɶ����� ��ٸ��� �Լ�
//        synchronized private void waitFlag() { //wait�Լ��� ���� ���ؼ� synchronized Ű���带 ����ؾ���
//           try {
//              this.wait(); //������ ���Ѵ�� ���·� ����=>notify()�� �ҷ�����������
//           } catch (InterruptedException e) {
//              // TODO Auto-generated catch block
//              e.printStackTrace();
//           } //��ٸ��鼭 �ߴܵ� ���·�
//        }
//		
		
		
		@Override
		public void run() {
			while(true) {
//				if(stopFlag) { //stop�÷��װ� �ö���ִٸ�
//					waitFlag(); //ǳ�� ���߰� �ϱ�
//				}
//				
				int x = balloon.getX();
				int y = balloon.getY()+10;//10�ȼ��� �Ʒ��� �̵�
//				//���� ���� ���Ϸ� �������� ǳ�� ��ü ����c
//				if(y>=500) {
//					setVisible(false);
//					//ü���� ��ƾ���
//					statusPanel.getDamage(10); //ĳ���Ϳ��� �������� ���Ѵ�.
//					break;//������ ����
//				}
				
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
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(image,0,0,getWidth(),getHeight(),null);
	}
	
}