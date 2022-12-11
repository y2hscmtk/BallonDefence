package game;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatusPanel extends JPanel{
	//ĳ���Ϳ� ���� ���� ��Ȳ�� �����ϱ� ���� ������
	private int characterHealth;
	private JLabel healthLabel; 
	
	private int weaponType; //ĳ������ ����, ����ڰ� ������� ���Ⱑ �������� ���̵���	
	private int weaponPower = 1; // ������ ������, �⺻ ������ �ɷ�ġ�� 1
	
	//ĳ���͵��� �̹��� ����
	private ImageIcon sangsangBugi = new ImageIcon("character0.png");
    private ImageIcon hansungNyangI = new ImageIcon("character1.png");
    private ImageIcon kkukkuKkakka = new ImageIcon("character2.png");
	private ImageIcon selectedCharacater;
	
	//���ӿ� ���� ����
	private int score = 0; //������ ������ ����
	private JLabel scoreLabel = new JLabel(Integer.toString(score));
	private int coin = 0; //������ ������ ���� => ������ �̿�
	
	
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
	
	public int getCoin() {
		return coin;
	}
	
	
	
	public StatusPanel(int characterType) {

		setLayout(null);
		setSize(500,900);
		
		//���� �����ֱ�
		add(new JLabel("SCORE"));
		scoreLabel.setSize(300,300);
		scoreLabel.setLocation(0,0);
		//JLabel textLabel = new JLabel("����");
		//add(textLabel);
		add(scoreLabel);
		
		
		System.out.println("�������ͽ� ������ ȣ��");
		setBackground(Color.cyan);
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
		healthLabel = new JLabel(Integer.toString(characterHealth));
		healthLabel.setSize(200,200);
		healthLabel.setLocation(65,500);
		add(healthLabel);
//		JLabel  = new JLabel("ü�� : "+Integer.toString(characterHealth));
//		health.setSize(200,200);
//		health.setLocation(65,500);
//		add(health);
		
		
		JLabel character = new JLabel(selectedCharacater);
		character.setSize(selectedCharacater.getIconWidth(),selectedCharacater.getIconHeight());
		character.setLocation(65,140);
		add(character);
		
		setVisible(true);
	}
	
	//ĳ���Ϳ��� �������� ���ϴ� �޼ҵ� 
	//=> ǳ���� �������� 10�� �������� ���Ѵ�.
	public void getDamage() {
		characterHealth -= 10;
		//����� ü�� ǥ��
		healthLabel.setText(Integer.toString(characterHealth));
	}
	
	
	//������ �߰��ϴ� �޼ҵ�
	public void plusScore(int pScore) {
		score += pScore; //���� �߰�
		System.out.println("���� ���� : "+score);
		//����� ���� ǥ��
		scoreLabel.setText(Integer.toString(score));
	}	
	
	
}
