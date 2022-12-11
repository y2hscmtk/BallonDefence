package game;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class ButtonClickedEvent extends MouseAdapter{
	private GameFrame parent;//�θ� ����
	private int type;
	private Clip clip;
	
	public ButtonClickedEvent(GameFrame parent,int type){
		this.parent = parent;
		this.type = type; //����ڷκ��� �̵��� �޴��� �����ڷ� �Է¹���
		//��ưŬ�� ȿ���� ����
		
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {	
		parent.swapPanel(type);
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
