package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
	private static final int ROUND1BALLONCOUNT = 1; //1���忡�� 10���� ǳ��
	private static final int ROUND2BALLONCOUNT = 1; //2���忡�� 20���� ǳ��
	private static final int ROUND3BALLONCOUNT = 1; //3���忡�� 30���� ǳ��
	//üũ����Ʈ4
	
	
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
	
	//�������ͽ�â�� ���� ������ ����
	public StatusPanel getStatusPanel() {
		return statusPanel;
	}
	
	//������ ���ݷ��� ����
	public void setWeaponPower(int selectedItem) {
//		weaponType = selectedItem; //�Է¹��� ���������� ���� ���� ����
//		//1�� ����, 2�� ����, 3�� ��
//		//���� Ÿ�Կ� �´� ���� �̹���, ���� ������ ����
		switch(selectedItem) {
		case 1:
			weaponPower = 1;
			break;
		case 2: 
			weaponPower = 2;
			break;
		case 3:
			weaponPower = 3;
			break;
		}
	}
	
	
	//����� �����ϴ� �г�
	private class DeadPanel extends JPanel{
		
		
		public DeadPanel() {
			
			setSize(500,500);
			setVisible(true);
		}
	}
	
	
	
	
	
	
	
	//������ �����ϴ� �г�
	//���ӿ� ���� ������ �޾Ƽ�, �� ���帶�� ������ ���� ����
	//�����̿� �����ϰ� ���� ����� �Ѿ�� �ϴ� ��ư �ۼ�
	private class StoreAndFianlPanel extends JPanel{
		private StatusPanel statusPanel;//������ ���Ž� ���ΰ� ü���� �����ϱ� ����
		
		private int gameLevel; //���� ������ ���� ���� ����
		private Clip clip; //�������� �������� ������ ȿ������ ����
		
		private GameMangeThread gameMangeThread;
		
		private ImageIcon backgroundImageIcon = new ImageIcon("StoreBoard.png");
		private Image backgroundImage = backgroundImageIcon.getImage();
		
		private ImageIcon rightArrowIcon = new ImageIcon("rightArrow.png");
		private ImageIcon rightArrowEnteredIcon = new ImageIcon("rightArrowEntered.png");
		
		//������ �ڵ��ȣ
		private static final int POSTION = 1;
		private static final int SCISSORS = 2;
		private static final int CHAINSAW = 3;
		
		
		private ImageIcon chainsawIcon = new ImageIcon("chainsaw.png"); //�� �̹��� ������
		private ImageIcon scissorsIcon = new ImageIcon("scissors.png"); //���� �̹��� ������
		private ImageIcon healthPostionIcon = new ImageIcon("HealthPositon.png"); //ü��ȸ�� ���� ������
		private ImageIcon noneIcon = new ImageIcon("None.png");
		
		
		private ItemLabelEvent itemSelectEvent;
//		//������ â�� �������� �� ������
//		private ImageIcon finalImageIcon = new ImageIcon("finalImage.png");
		
		private ImageIcon borderIcon = new ImageIcon("border.png");
		
		//�������� 2������ �����۸� �����ٰ�
		private ImageIcon item1;
		private JLabel item1Label;
		private ImageIcon item2;
		private JLabel item2Label;
		
		//�������� �������� �ʰ� �Ѿ�� �����Ƿ�=> �� ��� ���ʾ������� �������ڵ��� 1���� ����
		private int selectedItem = 1; //���õ� �������� ������ ���� => �̺�Ʈ �߻��� �Ѱ��ٰ�\
		private int weaponCode; //���� �ڵ带 ����
		
		//����&Ŭ���� â ������ ���̵� �Է¹��� ���� ����
		private JTextField input = new JTextField(15); //�ܾ��� �Է¹��� ���� ����
		//��ư�� ������ �Է��� �ؽ�Ʈ�� �����ϵ��� �ϴ� ��ư
		private JButton saveButton;
		
		
		private ImageIcon deadBoardIcon = new ImageIcon("deadBoard.png");
		//Ȩ��ư �̹���
		private ImageIcon homeButtonIcon = new ImageIcon("home.png");
		private ImageIcon homeButtonEnteredIcon = new ImageIcon("homeEntered.png");
		
		
		//��� �̹����� �ٲٱ� ���� �Լ�
		public void setBackgroundImage(ImageIcon icon) {
			this.backgroundImage = icon.getImage();
			repaint(); //�ٲ��̹����� ���̵��� paintComponent()ȣ��
		}
		
		
		
		
		//���� ���⸦ �Ǹ������� ����
		private boolean selling = true;
		
		//���� ������ �Ǹ������� Ȯ��
		public boolean isSelling() {
			return selling;
		}
		
		//�Ǹŵ� ���·� ����
		public void setSelling() {
			selling = false;
		}
		
		//�����۶󺧰� ���õ� �̺�Ʈ �ۼ�
		private class ItemLabelEvent extends MouseAdapter {
			private StatusPanel statusPanel; //������ ���ſ� ���� �������ͽ� â�� ������ �ҷ���������
			private int itemCode;
			
			//�������ڵ带 �Է¹޾� ����
			public ItemLabelEvent(int itemCode,StatusPanel statusPanel) {
				this.statusPanel = statusPanel; //�������ͽ�â�� ���� ���� ��������
				this.itemCode = itemCode;
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				int itemPrice = 0; //������ ������ ������ �κ�

				//�������ͽ��� ���� ��������� ���������� �ܿ� ��� Ȯ��
				
				switch(itemCode) {
				case POSTION: //1�� (����) ���ý�
					itemPrice = 300; //������ ���� ��������
					//�ش�������� ���ݺ��� ���� ���� ������ �ִ��� Ȯ��
					break;
				case SCISSORS: //������ õ��
					itemPrice = 1000; //������ ���� ��������
					selectedItem = SCISSORS; //������ �����Ͽ����� ����
					setSelling(); //�Ǹŵ� ���·� ��ȯ
					break;
				case CHAINSAW: //���� ��õ��
					itemPrice = 3000; //������ ���� ��������
					selectedItem = CHAINSAW; //���� �����Ͽ����� ����
					setSelling(); //�Ǹŵ� ���·� ��ȯ
					break;
				}
				
				//������ ���ݺ��� ���� ���� ���������� �̺�Ʈ �߻�
				if(statusPanel.getCoin()>=itemPrice) {
					switch(selectedItem) {
					case POSTION: //1�� (����) ���ý�
						statusPanel.healthRecovery(30); //30�� ü���� ȸ��
						statusPanel.minusCoin(300); //������ ���� ����
						break;
					case SCISSORS: //������ õ��
						System.out.println("���� ����");
						System.out.println("õ�� ����");
						statusPanel.minusCoin(1000); //�ݾ� ����
						statusPanel.setWeapon(selectedItem);
						break;
					case CHAINSAW: //���� ��õ��
						System.out.println("�� ����");
						statusPanel.minusCoin(3000); //�ݾ� ����
						System.out.println(selectedItem+","+CHAINSAW);
						statusPanel.setWeapon(selectedItem);
						break;
					}	
				}
				else {
					//���źҰ� �Ҹ� ����?
					System.out.println("���� �Ұ�");
				}
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				//��ư�� ������ ���� �Ҹ��� ������
				try {
					clip = AudioSystem.getClip();
					File audioFile = new File("ButtonClick.wav");
					AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
					clip.open(audioStream);
				}catch(Exception E) {
					System.out.println("����!");
				}
				clip.start(); // ��ư�� Ŭ�������� �Ҹ��� ������
			}
			
			
		}
		
		
		//�÷��̾� ����� �г��� �����гη� ���̰� �ϴ� �Լ� �ۼ�
		
		
		//���� ������ ���� ����ĭ���ε� �Ѿ�� �ֵ���
		public void setPanelElement(int gameLevel) {
			//���� ������ �������� ������ ����
			int nowCoin = statusPanel.getCoin();// ���� �ݾ��� ������ Ȯ��
			if(nowCoin<1000) {
				item1 = noneIcon; //�ƹ��͵� ������
				item2 = healthPostionIcon; //ü�¹����� 2��° ���������� ����
			}
			else if(1000<=nowCoin&&nowCoin<3000) {
				item1 = scissorsIcon; //���� ������ �����ֱ�
				item2 = healthPostionIcon; //ü�¹����� 2��° ���������� ����
			}
			else {
				item1 = chainsawIcon; //�� ������ �����ֱ�
				item2 = healthPostionIcon; //ü�¹����� 2��° ���������� ����
			}
			
			//������ 3�̸� �ƹ��͵� ������ �ʵ���
			if(gameLevel==3) {
				item1 = null; 
				item2 = null;
			}
				
		}
		
		
		//Ȩȭ������ ���ư���, ������ ������������ �����ϱ� ���� �̺�Ʈ �ۼ�(�������̵�����)
		class HomeButtonClickedEvent extends ButtonClickedEvent{

			public HomeButtonClickedEvent(GameFrame parent, int type, ImageIcon enteredIcon, ImageIcon presentIcon) {
				super(parent, type, enteredIcon, presentIcon); //���� ��ɵ��� ������ �����ϵ���
				// TODO Auto-generated constructor stub
			}


			@Override //���콺�� Ŭ���Ҷ� ������ ���θ޴� �������� �����ϴ� �κ� �߰�
			public void mouseClicked(MouseEvent e) {
				//���� ������ ����
				parent.getMusic().musicStop(); //���� �ߴ�
	    		//���� �ߴ� ����� ����
//	    		parent.
	    		
	    		//����ȭ�� �������� ����
	    		parent.getMusic().changeMusic("openningMusic.wav");
				
				
				//�г� ����
				parent.swapPanel(getType());
			}
			
			
		}
		
		
		class PlayerMakeEvent extends MouseAdapter{
			private boolean isMakePosible = true; //�÷��̾ ������ �������� ���θ� ����
			
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(isMakePosible) {
					String id = input.getText();
					//���� ������ �ܿ����ΰ� ������ ���Ѱ�����
					int finalScore = statusPanel.getScore() + statusPanel.getCoin();
					System.out.println("�׽�Ʈ");
					addPlayer(id,finalScore);
					input.setText("");
					isMakePosible = false;// ����� �Ұ��ϰ�
					input.setText("���̵� ����Ϸ�!");
					input.setEnabled(false); //�Է� �Ұ����ϰ� �����
				}
			}
		}
		
		
		
		
		
		
		//���� â�� ���� Ŭ���� â�� �����ϴ� Ŭ����
		//���� ���̵� �޾ƿ���,���� �Ŵ��� ������ ������������
		public StoreAndFianlPanel(int gameLevel,GameMangeThread gameMangeThread,StatusPanel statusPanel) {
			this.statusPanel = statusPanel; //�������ͽ� â�� ���� ������ ������ => ü��,���� ����
			this.gameLevel = gameLevel; //1���� �������� ����
			this.gameMangeThread = gameMangeThread;
			setLayout(null);
			
			this.selling = true; //������ �����Ǹ� ���⸦ �Ǹ����� ���·� ��ȯ
			
			
			//������ �ڵ带 �����ϱ� ���� �ڵ�
			int nowCoin = statusPanel.getCoin();
			//�������ͽ� â�� �ݾ� ��Ȳ�� ���� �ڵ� ����	
			if(nowCoin<=0) {
				this.weaponCode = 1;
			}
			else if(1000<=nowCoin&&nowCoin<3000) {
				this.weaponCode = 2;
			}
			else {
				this.weaponCode = 3;
			}
			
//			weaponCode = gameLevel+1; //1���� ������ �����ϴ� ���� �ڵ�� ����+1
	
			
			//�ǳڿ� �������� �� ���� ����
			//����â�� �ɼ��� �ְ�, Ŭ����â�� �ɼ����ְ�, ����â�� �ɼ��� �ִ�
			//����â�� ����� � �������� ���̰� ������ ���� ������ ���� ����
			setPanelElement(this.gameLevel);
			
			if(this.gameLevel==-1) {
				System.out.println(this.gameLevel+"����Ͽ����ϴ�!");
				
				
				repaint();
				

				System.out.println("���̵� �ؽ�Ʈ ������ ����");
				input.setFont(new Font("Gothic",Font.BOLD,20));
				input.setLocation(200,400);
				input.setSize(400,50);
				add(input);
				
				System.out.println("�Է� ��ư ����");
				saveButton = new JButton("����");
				saveButton.setFont(new Font("Gothic",Font.BOLD,20));
				saveButton.setLocation(600,400);
				saveButton.setSize(80,80);
				
				PlayerMakeEvent playerMakeEvent = new PlayerMakeEvent();
				
				//���̵� �����ϴ� �̺�Ʈ
				saveButton.addMouseListener(playerMakeEvent);
//						new MouseAdapter() {
//					
//					@Override
//					public void mouseClicked(MouseEvent e) {
//						String id = input.getText();
//						//���� ������ �ܿ����ΰ� ������ ���Ѱ�����
//						int finalScore = statusPanel.getScore() + statusPanel.getCoin();
//						System.out.println("���ο� �÷��̾� ����");
//						addPlayer(id,finalScore);
//						
//					}
//				});
				
				add(saveButton);
				
				//repaint();
				
			} //���� üũ����Ʈ
			else if(gameLevel==3) {
				System.out.println("3������� �Ϸ�!");
				//�̰��� ���� ����â�� �ʿ��� ��ҵ� �߰�
				
				//setBackgroundImage(deadBoardIcon);
				
				
				//GFrame�� ����� �޴� �гο����� ����
				JLabel homeButton = new JLabel(homeButtonIcon);
				homeButton.setSize(homeButtonIcon.getIconWidth(),homeButtonIcon.getIconHeight());
				homeButton.setLocation(0,0);
				homeButton.addMouseListener(new HomeButtonClickedEvent(parent,parent.BEGINNING_PANEL,homeButtonEnteredIcon,homeButtonIcon));
				add(homeButton);
				
						
				
				//üũ����Ʈ2
				//���̵� �Է¹��� �ؽ�Ʈ���� ����
				input.setFont(new Font("Gothic",Font.BOLD,20));
				input.setLocation(200,400);
				input.setSize(400,50);
				add(input);
				
				saveButton = new JButton("����");
				saveButton.setFont(new Font("Gothic",Font.BOLD,20));
				saveButton.setLocation(600,400);
				saveButton.setSize(80,80);
				
				//�ߺ������� ���ϱ� ����
				PlayerMakeEvent playerMakeEvent = new PlayerMakeEvent();
				//���̵� �����ϴ� �̺�Ʈ
				saveButton.addMouseListener(playerMakeEvent);
						
//						new MouseAdapter() {
//					
//					@Override
//					public void mouseClicked(MouseEvent e) {
//						String id = input.getText();
//						//���� ������ �ܿ����ΰ� ������ ���Ѱ�����
//						int finalScore = statusPanel.getScore() + statusPanel.getCoin();
//						System.out.println("���ο� �÷��̾� ����");
//						addPlayer(id,finalScore);
//						
//					}
//				});
				
				add(saveButton);
				
			}
			else { //1,2���� ����â ���� => 3���� ������ �ص��ǳ��� ������=>���̵� �Է¹޾� �����ϴ� ������, ó��ȭ������ ���ư��¹�ư
				item1Label = new JLabel(item1);
				
				item1Label.setSize(item1.getIconWidth(),item1.getIconHeight());
				item1Label.setLocation(140,150);
				
				//���� ���� �̺�Ʈ �ۼ� => �ѹ� ���ŵ� ���� �߰����Ű� ������
				item1Label.addMouseListener(new ItemLabelEvent(weaponCode,statusPanel));
				add(item1Label);
				
				JLabel border1 = new JLabel(borderIcon);
				border1.setSize(borderIcon.getIconWidth(),borderIcon.getIconHeight());
				border1.setLocation(60,100);
				add(border1);
				
				
				JLabel postionItem = new JLabel(item2);
				postionItem.setSize(item2.getIconWidth(),item2.getIconHeight());
				postionItem.setLocation(420,150); 
				//������ �ڵ带 �Ѱܼ� �̺�Ʈ �ۼ�, ������ ������ 300������
				postionItem.addMouseListener(new ItemLabelEvent(POSTION,statusPanel)); 
				//
				
				add(postionItem);
				
				JLabel border2 = new JLabel(borderIcon);
				border2.setSize(borderIcon.getIconWidth(),borderIcon.getIconHeight());
				border2.setLocation(400,100);
				add(border2);
				
				
				//���� ������ �Ѿ�� ��ư => ������ ���忡 ���� ���� �̹��� ��� ����غ���
				JLabel nextLevelButton = new JLabel(rightArrowIcon);
				nextLevelButton.setSize(rightArrowIcon.getIconWidth(),rightArrowIcon.getIconHeight());
				nextLevelButton.setLocation(500,20);
				nextLevelButton.addMouseListener(new MouseAdapter() {
					
					@Override //���콺�� ������Ʈ ���� �ö󰥶��� �̺�Ʈ
					public void mouseEntered(MouseEvent e) {
						JLabel label = (JLabel)(e.getComponent()); //�̺�Ʈ�� �߻��� ���� ������
						label.setIcon(rightArrowEnteredIcon); //���콺�� �ö󰥶��� �̹����� ����
					}
					
					@Override //���콺 ��ư�� ��������
					public void mouseReleased(MouseEvent e) {
						JLabel label = (JLabel)(e.getComponent()); //�̺�Ʈ�� �߻��� ���� ������
						label.setIcon(rightArrowIcon); //���� �̹����� ����
					}
					
					
					//���� Ŭ��
					@Override
					public void mouseClicked(MouseEvent e) {	
						//������ ���� ���������� �������ͽ�â���� �ѱ��
						//getStatusPanel().setWeapon(selectedItem);
						//���� ���ݷ� ����
						setWeaponPower(selectedItem);				
						setVisible(false);
						
						
//						//2�ʰ� ���
//						try {
//							setVisible(false); //����â �����
//							repaint();
//							System.out.println("1�ʰ� ���");
//							Thread.sleep(1000);
//						} catch (InterruptedException e1) {
//							// TODO Auto-generated catch block
//							e1.printStackTrace();
//						}
//						
						
						gameMangeThread.makeBalloonSpawnThreadAndStart(); //���� ������ ���ӻ���
						//gameMangeThread.setIsStoreOn(); //������ �ٽ� �Ⱥ��̴� ���·� ����
						gameMangeThread.setIsStoreOn(); //������ �ٽ� �Ⱥ��̴� ���·� ����
					}
					
					@Override
					public void mousePressed(MouseEvent e) {
						//��ư�� ������ ���� �Ҹ��� ������
						try {
							clip = AudioSystem.getClip();
							File audioFile = new File("ButtonClick.wav");
							AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
							clip.open(audioStream);
						}catch(Exception E) {
							System.out.println("����!");
						}
						clip.start(); // ��ư�� Ŭ�������� �Ҹ��� ������
					}
				});
				add(nextLevelButton);
			}
			
			
		}
		
		//���ο� �÷��̾ �����ϴ� �Լ�
		public void addPlayer(String id,int finalScore) {
			System.out.println(id +"���� ����"+finalScore +"�� ������");
			try{
	            File file = new File("Score.txt"); //���ھ� ���� �ҷ�����
	            if (!file.exists()) //������ ���ٸ� ����
	                file.createNewFile();
	            FileWriter fw = new FileWriter(file,true); //���� ���Ͽ� �̾��
	            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("Score.txt",true), "utf-8"));
	            //BufferedWriter writer = new BufferedWriter(fw);
	            writer.write("\n"+Integer.toString(finalScore)+"&"+id+"&"); //�����ٿ� �ܾ� ���̱�
	            writer.close();
	        }catch(IOException e){
	        	 e.printStackTrace();
	        }
		}
		
		
		
		
//		//ĥ�� ���ֱ�
//		public void removeBoard() {
//			backgroundImage = 
//		}
		
		
		//��� �̹��� �׸��� =>ĥ���� �����ͼ� �׸�
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			System.out.println("ȣ���");
			g.drawImage(backgroundImage,0,0,backgroundImageIcon.getIconWidth(),backgroundImageIcon.getIconHeight(),null);
		}
		
		
	}
	
	
	private StoreAndFianlPanel storeAndFinalPanel;
	
	
	public GameRunningPanel(StatusPanel statusPanel,int characterType,GameFrame parent) {
		this.parent = parent;
		this.characterType = characterType; //ĳ���� Ÿ�� ����
		this.statusPanel = statusPanel;
		setLayout(null);
		setSize(1000,900);
		
		weaponPower = 1; //���� �г� ������ �⺻����� ����
		
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
		//private BallonSpawnThread spawnThread; //ǳ���� �����ǰ� �ϴ� ������
		private int gameLevel = 1;
		private boolean isPlayerAlive = true; //�÷��̾��� �������� ǥ��
		private int roundCount = 3;
		private boolean isStoreOn = false; //����â�� ������ִ��� Ȯ��
		private boolean testFlag = false;
		
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
		
		
		//ǳ�� ���� �����带 �����ϴ� �޼ҵ� => ����â���� ���� ���带 �����ϵ��� �ϱ� ����
		public void makeBalloonSpawnThreadAndStart() {//�����Ǹ� ���� ������ ������ ������
			ballonSpawnThread = new BallonSpawnThread(ballonSpawnTime,ballonSpeed,statusPanel,gameLevel);
			ballonSpawnThread.start();
		}
		
		
		//������ �ٽ� �Ⱥ��̴� ���¶�� ����
		public void setIsStoreOn() {
			isStoreOn = false;
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
								//���� ���â ��� => ���̵� �Է¹ޱ� => �ٽ��ϱ� ��ư, �������� ��ư �����ֱ�
								storeAndFinalPanel = new StoreAndFianlPanel(-1,this,statusPanel);
								storeAndFinalPanel.setBounds(100,200,800,500);
								break;	
							}
							
						}
					}catch(Exception E) {
						System.out.println("deleted!");
					}
					
				}
			
				
				
//				//���尡 4�� �Ѿ�� Ŭ����!
//				if(gameLevel>3&&isStoreOn==false&&testFlag == false) {
//					System.out.println("���� ����! ���̵� �Է��ϼ���!!");
//					//�۵����� ������� GameMangeThread�̿ܿ��� ���� �ɰ��̹Ƿ� �ش� �����常 �����Ű���
//					isGameRun = false;
//					break; //while�� break;
//				}
				
				//ǳ���� ���� ������ �Ǿ���,������ ǳ���� ��� �������� => ���� ����� �Ѿ�ų�, ���� Ŭ��� �˸�
				if(!ballonSpawnThread.getIsRun()&&balloonVector.isEmpty()&&isStoreOn==false) {
					//������ 3������� ��� �������� Ȯ���ʿ�
					if(gameLevel==3) {
						
						//Ŭ���� ����?
						
						System.out.println(gameLevel + "���� Ŭ����! => ����â ����");
						
						//����â �����Ұ�
						storeAndFinalPanel = new StoreAndFianlPanel(gameLevel,this,statusPanel); //���� �����忡 ���� ���� ���� �ο�
						storeAndFinalPanel.setBounds(100,200,800,500);
						add(storeAndFinalPanel);
						
						repaint(); //����â�� ���̵���
						isStoreOn = true;
					}
					else {
						
						gameLevel++;
						System.out.println(gameLevel-1 + "���� ����â ����");//����â���� ��ư�� ���� ���� ���������� �Ѿ�Բ�
						storeAndFinalPanel = new StoreAndFianlPanel(gameLevel-1,this,statusPanel); //���� �����忡 ���� ���� ���� �ο�
						storeAndFinalPanel.setBounds(100,200,800,500);
						add(storeAndFinalPanel);
						
						repaint(); //����â�� ���̵���
						isStoreOn = true;
						testFlag = true;
						
//						if(gameLevel==3&&balloonVector.isEmpty()) {
//							System.out.println("���� Ŭ����!");
//							break;
//						}
					}
//					if(gameLevel>3) {
//						//���̵� �Է¹޴� �г� ���� => ����ȭ������ ���ư��� ��ư�� �����Ұ�
//						System.out.println("���� ����! ���̵� �Է��ϼ���!!");
//						//�۵����� ������� GameMangeThread�̿ܿ��� ���� �ɰ��̹Ƿ� �ش� �����常 �����Ű���
//						isGameRun = false;
//					}
//					else { //3���� ������ ���忡���� ������ ���ο� ǳ�������带 ����
//						if(isStoreOn!=true&&gameLevel<=2) {
//							System.out.println(roundCount);
//							gameLevel++;
//							System.out.println(gameLevel-1 + "���� ����â ����");//����â���� ��ư�� ���� ���� ���������� �Ѿ�Բ�
//							storePanel = new StorePanel(gameLevel-1,this); //���� �����忡 ���� ���� ���� �ο�
//							storePanel.setBounds(100,200,800,500);
//							
//							add(storePanel);
//							
//							repaint(); //����â�� ���̵���
//							isStoreOn = true;
//							testFlag = true;
//							
//							if(gameLevel==3&&balloonVector.isEmpty()) {
//								System.out.println("���� Ŭ����!");
//								break;
//							}
							
							
//						}
						
						//���� ����

//						//�Ʒ� ������ ����â�� ��ư���� �ű��
//						gameLevel+=1;
//						ballonSpawnThread = new BallonSpawnThread(ballonSpawnTime,ballonSpeed,statusPanel,gameLevel);
//						ballonSpawnThread.start();
					
				}
				
				//���� ����ִ� ��쿡 ���ؼ��� ����
				if(isPlayerDie()&&isPlayerAlive){
					isGameRun = false; //���� ������ ����
					//���� ���â ��� => ���̵� �Է¹ޱ� => �ٽ��ϱ� ��ư, �������� ��ư �����ֱ�
					isPlayerAlive = false; //���ó�� 
					System.out.println("�÷��̾� ���");
					
					//���� �Ű����� ��� -1�� �Է� => ���ĭ ����
					storeAndFinalPanel = new StoreAndFianlPanel(-1,this,statusPanel);
					storeAndFinalPanel.setBounds(100,200,800,500);
					add(storeAndFinalPanel);
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
				
				//ǳ�� �ڽ��ȿ� ������ �� ������ �ܾ�� ���� �ܾ� ����
				while(true) {
					word = wordList.getWord(); //���� �ܾ� ����
					if(word.length()<10) //9���� ������ �ܾ��� ��� �ݺ��� Ż��
						break;
				}
				
				
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
//				System.out.println("���� : "+random); Ȯ�ο�
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
	
	

	
	
	//üũ����Ʈ3
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
		
		
		//üũ����Ʈ
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
					
					//ǳ���� ü���� 0���ϰ� �Ǹ� ǳ���� �����Ѵ�. => ǳ���� ������ ���� �ٸ� ������ �ο��Ѵ�.
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
							coin = 200;
							break;
						case 2: //���ǳ�� 600��, 300��
							score = 600;
							coin = 300;
							break;
						case 3: //����ǳ���� ���
							score = 77; //77�� ȹ��
							coin = 1000; //1000���� ȹ��
						case 4: //��Ÿǳ���� ���
							score = 77; //777�� ȹ��
							coin = 0; //���� �߰� x
							//����ġ 2�� Ȱ��ȭ
							doublePoint = 2; //����ġ �ο��� 2��� ����
							
						}
						
						if(doublePoint==2) {
							System.out.println("����ġ 2��ȿ�� ������");
						}
						else
							System.out.println("�⺻ ����ġ�� ������");
						
						//���ΰ� ������ ���Ѵ�.
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
