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
	private GameFrame parent;//부모 변수
	private int type;
	private Clip clip;
	
	public ButtonClickedEvent(GameFrame parent,int type){
		this.parent = parent;
		this.type = type; //사용자로부터 이동할 메뉴를 생성자로 입력받음
		//버튼클릭 효과음 실행
		
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {	
		parent.swapPanel(type);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		//버튼이 눌려진 순간 소리가 나도록
		try {
			clip = AudioSystem.getClip();
			File audioFile = new File("ButtonClick.wav");
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
			clip.open(audioStream);
		}catch(Exception E) {
			System.out.println("오류!");
		}
		clip.start(); // 버튼을 클릭했을때 소리가 나도록
	}
}
