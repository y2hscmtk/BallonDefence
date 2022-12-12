package game;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

//���� ��ư���� ����Ͽ� Ŭ���������� ��ȭ�� �ش�
//���콺 �ö󰥶� �̹��� ��ȭ
//������ ������ �̹��� ��ȭ,��
public class ButtonClickedEvent extends MouseAdapter{
	private GameFrame parent;//�θ� ����
	private int type;
	private Clip clip; //ȿ������ ���Ͽ�
	private ImageIcon enteredIcon; //���콺�� �ö����� ���������� �� �̹���
	private ImageIcon presentIcon; //������Ʈ�� ���� �̹��� ������
	private boolean authority = false; //���콺 entered�̺�Ʈ�� exited�̺�Ʈ�� Ȱ������
	
	public ButtonClickedEvent(GameFrame parent,int type){
		this.parent = parent;
		this.type = type; //����ڷκ��� �̵��� �޴��� �����ڷ� �Է¹���
		//��ưŬ�� ȿ���� ����
	}
	
	//���� �̹����� ���콺�� �ö����� ����� �̹����� �谳������ �޴� ������
	public ButtonClickedEvent(GameFrame parent,int type,ImageIcon enteredIcon,ImageIcon presentIcon) {
		authority = true; //entered�̺�Ʈ�� exited�̺�Ʈ �߻� ���� �ο�
		this.parent = parent;
		this.type = type; //����ڷκ��� �̵��� �޴��� �����ڷ� �Է¹���
		this.setEnteredIcon(enteredIcon);
		this.setPresentIcon(presentIcon);
	}
	
	
	public ButtonClickedEvent(ImageIcon enteredIcon,ImageIcon presentIcon) {
		this.setEnteredIcon(enteredIcon);
		this.setPresentIcon(presentIcon);
	}
	
	
	@Override //���콺�� ������Ʈ ���� �ö󰥶��� �̺�Ʈ
	public void mouseEntered(MouseEvent e) {
		if(authority) { //������ �ִٸ� �̺�Ʈ �߻�
			JLabel label = (JLabel)(e.getComponent()); //�̺�Ʈ�� �߻��� ���� ������
			label.setIcon(getEnteredIcon()); //���콺�� �ö󰥶��� �̹����� ����
		}
		
	}
	
	@Override //���콺�� ������Ʈ ���� ����� �̺�Ʈ
	public void mouseExited(MouseEvent e) {
		if(authority) { //������ �ִٸ� �̺�Ʈ �߻�
			JLabel label = (JLabel)(e.getComponent()); //�̺�Ʈ�� �߻��� ���� ������
			label.setIcon(getPresentIcon()); //���� �̹����� ����
		}
	}
	
	@Override //���콺 ��ư�� ��������
	public void mouseReleased(MouseEvent e) {
		if(authority) { //������ �ִٸ� �̺�Ʈ �߻�
			JLabel label = (JLabel)(e.getComponent()); //�̺�Ʈ�� �߻��� ���� ������
			label.setIcon(getPresentIcon()); //���� �̹����� ����
		}
	}
	
	
	
	@Override
	public void mouseClicked(MouseEvent e) {	
//		if(isMusicButton)
//			System.out.println("������ư");
//		else //������ư�� �ƴϸ� �г��� �����ϴ� ����
		parent.swapPanel(type);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
//		JLabel label = (JLabel)(e.getComponent()); //�̺�Ʈ�� �߻��� ���� ������
//		label.setIcon(presentIcon); //���� �̹����� ����
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

	public ImageIcon getEnteredIcon() {
		return enteredIcon;
	}

	public void setEnteredIcon(ImageIcon enteredIcon) {
		this.enteredIcon = enteredIcon;
	}

	public ImageIcon getPresentIcon() {
		return presentIcon;
	}

	public void setPresentIcon(ImageIcon presentIcon) {
		this.presentIcon = presentIcon;
	}
}
