package game;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;



//ĳ������ ���� ������ �����ֱ� ���� �г�
//���� ü��,����,����,���� �����ϰ� ȭ�鿡 ����Ѵ�.
public class StatusPanel extends JPanel{
	private GameFrame parent;
	
	//ĳ���Ϳ� ���� ���� ��Ȳ�� �����ϱ� ���� ������
	private int characterHealth;
	private JLabel healthLabel; 
	
	private int weaponType = 1; //ĳ������ ����, ����ڰ� ������� ���Ⱑ �������� ���̵���,�⺻����� 1������
	
	//���⿡ ���� ������ �ڵ��ȣ
	private final static int PENCIL = 1;
	private static final int SCISSORS = 2;
	private static final int CHAINSAW = 3;
	
	private int weaponPower = 1; // ������ ������, �⺻ ������ �ɷ�ġ�� 1
	
	
	//ĳ���͵��� �̹��� ����
	private ImageIcon sangsangBugi = new ImageIcon("character0.png");
    private ImageIcon hansungNyangI = new ImageIcon("character1.png");
    private ImageIcon kkukkuKkakka = new ImageIcon("character2.png");
	private ImageIcon selectedCharacater;
	
	//���� ������, ���� ������
	private ImageIcon coinIcon = new ImageIcon("coinImage.png");
	private ImageIcon xIcon = new ImageIcon("xImage.png");
	//ü�¹� ������(�ϳ��� ü��5)
	private ImageIcon healthBarIcon = new ImageIcon("healthBar.jpg");
	//���� ������
	private ImageIcon pencilIcon = new ImageIcon("pencil.png"); //���� �̹��� ������
	private ImageIcon chainsawIcon = new ImageIcon("chainsaw.png"); //�� �̹��� ������
	private ImageIcon scissorsIcon = new ImageIcon("scissors.png"); //���� �̹��� ������
	
	//���� ������
	private ImageIcon soundLabelIcon = new ImageIcon("music.png");
    private ImageIcon soundLabelMuteIcon = new ImageIcon("mute.png");
	
	
	
	//���� �̹����� ����
	private JLabel weaponLabel; 
	
	
	//���ӿ� ���� ����
	private int score = 0; //������ ������ ����
	private JLabel scoreLabel = new JLabel(Integer.toString(score));
	private int coin = 0; //������ ������ ���� => ������ �̿�
	private JLabel coinLabel = new JLabel(Integer.toString(coin));

	
	
	//ü�¹��� �̹����󺧵��� �����ϱ� ���� ���� => ĳ���͸��� ü���� �ٸ��Ƿ�
	private Vector<JLabel> healthBarVector = new Vector<JLabel>();
	private int healthBarIndex=0;
	
	
	
	
	//���������� ���߿� Ű�� �����ֵ��� ��ư�� �޾��� �̺�Ʈ
	//������ �����ϱ� ���� ������ �ۼ��� ��ưŬ���̺�Ʈ�� �������̵��Ͽ� ���ο� ��� �ۼ�
    private class SoundButtonClickedEvent extends ButtonClickedEvent{
    	private GameFrame parent;
    	
    	public SoundButtonClickedEvent(GameFrame parent,ImageIcon enteredIcon, ImageIcon presentIcon) {
    		super(parent,enteredIcon, presentIcon); //�θ� �����ڿ� �Ѱ��ش�.
    		// TODO Auto-generated constructor stub
    		this.parent = parent;
    	}


    	@Override //���콺�� �ö������� �̺�Ʈ�� �����ϱ� ���� �������̵�
    	public void mouseEntered(MouseEvent e) {
			
    	}
    	
    	
    	@Override //���콺�� Ŭ���Ǿ����� ���� ������ �۵������� Ȯ���Ͽ� ������ Ű�� ��
    	public void mouseClicked(MouseEvent e) {
    		JLabel label = (JLabel)(e.getComponent()); //�̺�Ʈ�� �߻��� ���� ������
    		if(parent.isMusicOn()) {//���� ������ ������̶��
    			System.out.println("���� ����� -> ����");
    			label.setIcon(getEnteredIcon()); //xǥ�÷� ����
    			parent.getMusic().musicStop();
    			parent.setMusicOnOff(false); //���� �������·� ǥ��
    		}
    			
    		else {//���� ������ �����ִ� ���¶��
    			System.out.println("���� ���� -> ���");
    			label.setIcon(getPresentIcon()); //��ǥ ǥ�÷� ����
    			parent.getMusic().resumeMusic();
//    			parent.getMusic().start();//���� ���ĺ��� �������
    			parent.setMusicOnOff(true); //���� �������·� ǥ��
    		}
    	}
    }
	
	
	
	//������ ĳ���Ͱ� ���������� ���� ����
	//ĳ������ ü�� ����
	//ĳ������ ���� ����, ����� �̹���
	//ĳ���� ���� ����
	//���� ����(�󸶸� ��������)
    
	//getter�Լ� �ۼ� => ���� ������ ����
	public int getHealth() {
		return characterHealth;
	}
	
	public int getScore() {
		return score;
	}
	
	//�������� ���� ���������� �Ѱ���
	public int getCoin() {
		return coin;
	}
	
	//������ ���ݷ��� ����
	public int getWeaponPower() {return weaponPower;}
	
	//���õ� ����� ���� �̹��� ���� => ���� ���� ����������
	public void setWeapon(int selectedItem) {
		weaponType = selectedItem; //�Է¹��� ���������� ���� ���� ����
		//1�� ����, 2�� ����, 3�� ��
		//���� Ÿ�Կ� �´� ���� �̹���, ���� ������ ����
		switch(weaponType) {
		case PENCIL:
			System.out.println("���ʷ� �̹��� ����");
			weaponLabel.setIcon(pencilIcon);
			weaponPower = 1;
			break;
		case SCISSORS: 
			System.out.println("������ �̹��� ����");
			weaponLabel.setIcon(scissorsIcon);
			repaint();
			weaponPower = 2;
			break;
		case CHAINSAW:
			System.out.println("������ �̹��� ����");
			weaponLabel.setIcon(chainsawIcon);
			weaponPower = 3;
			break;
		}
	}
	
	public StatusPanel(int characterType, GameFrame parent) {
		this.parent = parent;
		setLayout(null);
		setSize(500,900);
		
		//���� �����ֱ�
		add(new JLabel("SCORE"));
		scoreLabel.setSize(300,300);
		scoreLabel.setLocation(0,0);
		//JLabel textLabel = new JLabel("����");
		//add(textLabel);
		add(scoreLabel);
		
		
		//�Ҹ� �ѱ�&���� ������
		JLabel soundButtonLabel = new JLabel(soundLabelIcon);
        soundButtonLabel.setSize(soundLabelIcon.getIconWidth(),soundLabelIcon.getIconHeight());
        soundButtonLabel.setLocation(20, 20);
        soundButtonLabel.addMouseListener(new SoundButtonClickedEvent(parent,soundLabelMuteIcon,soundLabelIcon));
        		
//        		new ButtonClickedEvent(parent, GameFrame.SELECT_PANEL,soundLabelMuteIcon,soundLabelIcon));
        add(soundButtonLabel);
		

		//���� ������& x������
		JLabel coinImageLabel = new JLabel(coinIcon);
		coinImageLabel.setSize(coinIcon.getIconWidth(),coinIcon.getIconHeight());
		coinImageLabel.setLocation(140,480);
		coinImageLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				healthRecovery(30); //���������� 30�� ü���� ȸ��
			}
		});
		coinLabel.setSize(300,300);
		coinLabel.setLocation(280,350);
		add(coinLabel);
		
		
		JLabel xImageLabel = new JLabel(xIcon);
		xImageLabel.setSize(xIcon.getIconWidth(),xIcon.getIconHeight());
		xImageLabel.setLocation(200,500);
		add(coinImageLabel);
		add(xImageLabel);
		
		
		
		
		
		
		
		System.out.println("�������ͽ� ������ ȣ��");
		setBackground(Color.GREEN);
		//ĳ���� ���������� ���� ��� �̹��� ����
		switch(characterType) {
		case 0: //���α��� ���
			characterHealth = 150; //150�� ü������ ����
			selectedCharacater = sangsangBugi;
			break;
		case 1: //�Ѽ������� ���
			characterHealth = 90; //90�� ü������ ����
			selectedCharacater = hansungNyangI;
			break;
		case 2: //�ٲٿ� ����� ���
			characterHealth = 80; //80�� ü������ ����
			selectedCharacater = kkukkuKkakka;
			break;
		}
		//healthBarIndex = characterHealth/10*2; //ü�¹��� ���� ����=>���� ���۽� ���
		int i=0;
//		//ü�¹� ���� => ü���� 10������ 2�� ���Ѹ�ŭ ����=> ü���� 150�̸� 30�� ����(ü�¹� 1���� ü��5�� �ǹ�)
		for(i=0;i<(characterHealth/10*2);i++) {
//			System.out.println("ü�¹� �߰�"); Ȯ�ο�
			JLabel healthBar = new JLabel(healthBarIcon);
			healthBar.setSize(healthBarIcon.getIconWidth(),healthBarIcon.getIconHeight());
			healthBar.setLocation(64+(i*10),420);
			add(healthBar);
			healthBarVector.add(healthBar);
		}
		
		healthBarIndex = 64+(i*10); //���� ������ ��ġ�� ����Ű����
//		int x = healthBarVector.get(healthBarVector.size()-1).getX();
//		int y = healthBarVector.get(healthBarVector.size()-1).getY();
//		lastHealthBarPosition.x = x;
//		lastHealthBarPosition.y = y;
		//������ ���� ü�¹��� ��ġ ���� => ü���߰� � ����ϱ� ����
		
		
		//ü�� �ؽ�Ʈ ǥ��
		healthLabel = new JLabel(Integer.toString(characterHealth));
		healthLabel.setSize(200,200);
		healthLabel.setLocation(65,500);
		add(healthLabel);
//		JLabel  = new JLabel("ü�� : "+Integer.toString(characterHealth));
//		health.setSize(200,200);
//		health.setLocation(65,500);
//		add(health);

		//ĳ���� ������
		JLabel character = new JLabel(selectedCharacater);
		character.setSize(selectedCharacater.getIconWidth(),selectedCharacater.getIconHeight());
		character.setLocation(65,140);
		add(character);
		
		
		//���� ������ ���̰�
		//�ʱ� ���� ���� => �ʱ� ����� ����
		weaponLabel = weaponLabel = new JLabel(pencilIcon);
		weaponLabel.setSize(pencilIcon.getIconWidth(),pencilIcon.getIconHeight());
		weaponLabel.setLocation(65,560);
		add(weaponLabel);
		
		
		
		
		
		
		
		
		setVisible(true);
	}
	
	//ĳ���Ϳ��� �������� ���ϴ� �޼ҵ� 
	//=> ǳ���� �������� 10�� �������� ���Ѵ�.
	public void getDamage(int damage) {
		//������ % 5��ŭ�� ü�¹ٸ� ���־��� 
		//=> �����ִ� ü�¹ٰ� 5�ε�,10�� ������쿡 ���ѿ���ó�� �ʿ�
		System.out.println("���� ü��"+characterHealth);
		System.out.println("ü�¹� ü��"+healthBarVector.size()*5);
		//���� ���� �ִ� ü�¹� �󺧿� ���� ������ �����ͼ� visible�� fasle�� ����
		//������ ���� ü�¹� �󺧿� ���� ������ ������
		//��������ŭ ������ �ϹǷ� �ݺ��� �̿�
		for(int i=0;i<damage/5;i++) {
			if(characterHealth<=5)
				break; //ü���� 5�϶� 10�� �������� �ִ� ��쿡 ���ؼ�
			//���� ü�¹��� ���� ������ ü�¹ٶ��� ��ġ�� �����صд�.
			JLabel finalHealthBar = healthBarVector.get(healthBarVector.size()-1); 
			finalHealthBar.setVisible(false); //�Ⱥ��̰� �����
			healthBarVector.remove(healthBarVector.size()-1); //���Ϳ����� ����
			//�������� ����, ���� ������ ���� ü�¹ٶ��� ��ġ�� �����´�.
			healthBarIndex = healthBarVector.get(healthBarVector.size()-1).getX()+10;
		}
//		//characterHealth/10*2 => ü�¹��� ����
//		healthBarVector.remove((characterHealth/10*2));
//		
		characterHealth -= damage;
		//����� ü�� ǥ��
		healthLabel.setText(Integer.toString(characterHealth));
	}
	
	//ĳ������ ü���� ���Ѵ�.
	public void healthRecovery(int pHealth) {
		//�÷����� ü�¼���ŭ ü�¹ٸ� �����ش�.
		int i=0;
		for(i=0;i<(pHealth/10*2);i++) {
//			System.out.println("ü�¹� �߰�");
			JLabel healthBar = new JLabel(healthBarIcon);
			healthBar.setSize(healthBarIcon.getIconWidth(),healthBarIcon.getIconHeight());
			//���� ü�¹��� x��ǥ�� ������� ���
			healthBar.setLocation(healthBarIndex+i*10,420);
			add(healthBar);
			repaint(); //�׷������� ����������
			healthBarVector.add(healthBar);
		}
		//x��ǥ �� ����
		healthBarIndex = healthBarIndex+(i-1)*10;
		
		characterHealth += pHealth;
		//����� ü�� ǥ��
		healthLabel.setText(Integer.toString(characterHealth));
	}
	
	
	//������ �߰��ϴ� �޼ҵ�
	public void plusScore(int pScore) {
		score += pScore; //���� �߰�
		//����� ���� ǥ��
		System.out.println(pScore+"�� �߰�");
		scoreLabel.setText(Integer.toString(score));
	}	
	
	
	//������ �߰��ϴ� �޼ҵ�
	public void plusCoin(int pCoin) {
		coin += pCoin; //���� �߰�
		//����� ���� ǥ��
		System.out.println(pCoin+"�� �߰�");
		coinLabel.setText(Integer.toString(coin));
	}
	
	
	//������ ���� �޼ҵ� => ���Ǳ���
	public void minusCoin(int mCoin) {
		System.out.println(mCoin +"����");
		coin -= mCoin; //���� �߰�
		//����� ���� ǥ��
		coinLabel.setText(Integer.toString(coin));
	}
	
	
	
}
