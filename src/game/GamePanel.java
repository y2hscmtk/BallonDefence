package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

//������ �����ϴ� ����
//ǳ���� �޸� �ܾ���� �����ϰ� �ϴÿ��� ��������
//�˸��� �ؽ�Ʈ�� �Է��ϸ� �ش� ǳ���� ������.

//�����ʿ��� ����ڰ� ������ ĳ������ �̹�����
//����, ĳ������ ü��, ������� ���⸦ �����ִ� �г��� ����
//���ʿ��� ������ �����ϴ� �г��� ����.
public class GamePanel extends JPanel{
	//��� �̹���
	private ImageIcon bgImageicon = new ImageIcon("gamePanelBackgroundImage.png");
    private Image gamePanelBackgroundImage = bgImageicon.getImage();
    //ĳ���� �̹���(���� ��ܿ� ����)
    private ImageIcon sangsangBugi = new ImageIcon("character0.png");
    private ImageIcon hansungNyangI = new ImageIcon("character1.png");
    private ImageIcon kkukkuKkakka = new ImageIcon("character2.png");
    private ImageIcon selectedCharacter = null; //���õ� �̹����� ����Ű����
    
	//ĳ���� Ư��, ���� Ư��, ǳ�� �ӵ�
	private int characterType; //����ڰ� ������ ĳ���Ͱ� ���������� ����
	private int characterHealth;//ĳ������ ü��
	private int weaponType; //ĳ������ ����, ����ڰ� ������� ���Ⱑ �������� ���̵���	
	private int weaponPower = 1; // ������ ������, �⺻ ������ �ɷ�ġ�� 1
	//ǳ���� �������� �ӵ� => ĳ���� Ư���� ���� �޶���
	private int ballonSpeed = 10;//�ð��� 10px�� �������°��� �⺻�ӵ�
	//�ٲٱ� ���õɰ�쿡 true�� ����
	private boolean luckyChance = false; //�ٲٲ����� Ư��, true��� ����Ȯ���� ǳ���� �ϳ� �� ��Ʈ��
	
	//ǳ���� �������� ������
	
	//�� ���尡 ������ ������ ǳ������ ��� ���ŵǾ����
	
	public GamePanel(int characterType) {
		setLayout(null); //ǳ���� �������� ��ġ�� �������� �����ϱ� ����
		this.setBackground(Color.gray);
        setSize(1500,900);
		
		//������ �����Ҷ��� ĳ���� ������ �Է¹޾Ƽ� ����
		this.characterType = characterType;
		
		//���õ� ĳ���Ϳ� ���� ���ӳ��̵� ����
		//0���� ���α�, 1���� �Ѽ�����, 3���� �ٲٿͱ��
		switch(characterType) {
		case 0:
			selectedCharacter = sangsangBugi; //���α� �̹��� ����
			characterHealth = 150; //���α��� ĳ���� Ư��; ü���� ����(�ܴ��ϴ�)
			break;
		case 1:
			selectedCharacter = hansungNyangI; //�Ѽ����� �̹��� ����
			characterHealth = 90;
			ballonSpeed = 7; //�Ѽ������� ĳ���� Ư��; ǳ���� �����Զ�������(��ü�÷�)
			break;
		case 2:
			selectedCharacter =  kkukkuKkakka; //�ٲٱ�� �̹��� ����
			characterHealth = 80;
			luckyChance = true; //�ٲٱ���� ĳ���� Ư��; ����Ȯ��~
			break;
		}
		
		//������ �������� �������� �̹����� ������ ������ ���̵���
		JLabel character = new JLabel(selectedCharacter);
		character.setSize(selectedCharacter.getIconWidth(),selectedCharacter.getIconHeight());
		character.setLocation(1110,140);
		add(character);
		
		//�׽�Ʈ Ȯ�ο� �� �ԷµǾ��� Ȯ�ο�
		System.out.println("���õ� ĳ���� : " + characterType + "ĳ���� ü�� : " + characterHealth + "ǳ�� �ӵ�: " + ballonSpeed);
		//ǳ���� �����ϰ� �����ϴ� ������ ����
		
		//ǳ���� �������� �ϴ� ������ ����
		setVisible(true);
	}
	
	
	//ǳ���� �������� ������ �ۼ�
	
	
	
	
	
	@Override
    public void paintComponent(Graphics g) {
       super.paintComponent(g); //�׷��� ������Ʈ ����
       //��� �̹���
       g.drawImage(gamePanelBackgroundImage, 0, 0, this.getWidth(),this.getHeight(),null); //�̹����� �׷����� ���� �˸����� �ʱ�
    }
	
}
