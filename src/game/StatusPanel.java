package game;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatusPanel extends JPanel{
	//ĳ���͵��� �̹��� ����
	private ImageIcon sangsangBugi = new ImageIcon("character0.png");
    private ImageIcon hansungNyangI = new ImageIcon("character1.png");
    private ImageIcon kkukkuKkakka = new ImageIcon("character2.png");
	private ImageIcon selectedCharacater;
	
	private int score = 0; //������ ������ ����
	
	//������ ĳ���Ͱ� ���������� ���� ����
	//ĳ������ ü�� ����
	//ĳ������ ���� ����, ����� �̹���
	//ĳ���� ���� ����
	//���� ����(�󸶸� ��������)
    
	
	public StatusPanel(int characterType) {

		setLayout(null);
		setSize(500,900);
		
		System.out.println("�������ͽ� ������ ȣ��");
		setBackground(Color.cyan);
		//ĳ���� ���������� ���� ��� �̹��� ����
		switch(characterType) {
		case 0:
			selectedCharacater = sangsangBugi;
			break;
		case 1:
			selectedCharacater = hansungNyangI;
			break;
		case 2:
			selectedCharacater = kkukkuKkakka;
			break;
		}
		
		JLabel character = new JLabel(selectedCharacater);
		character.setSize(selectedCharacater.getIconWidth(),selectedCharacater.getIconHeight());
		character.setLocation(65,140);
		add(character);
		
		setVisible(true);
	}
	
	//������ �߰��ϴ� �޼ҵ�
	public void plusScore(int pScore) {
		score += pScore; //���� �߰�
		System.out.println("���� ���� : "+score);
	}	
	
	
}
