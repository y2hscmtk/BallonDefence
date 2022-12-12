package game;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

//라벨을 버튼으로 취급하여 클릭했을때의 변화를 준다
//마우스 올라갈때 이미지 변화
//누르고 땠을때 이미지 변화,등
public class ButtonClickedEvent extends MouseAdapter{
	private GameFrame parent;//부모 변수
	private int type;
	private Clip clip; //효과음을 위하여
	private ImageIcon enteredIcon; //마우스가 올라갔을때 보여지도록 할 이미지
	private ImageIcon presentIcon; //컴포넌트의 현재 이미지 아이콘
	private boolean authority = false; //마우스 entered이벤트와 exited이벤트의 활성권한
	
	public ButtonClickedEvent(GameFrame parent,int type){
		this.parent = parent;
		this.type = type; //사용자로부터 이동할 메뉴를 생성자로 입력받음
		//버튼클릭 효과음 실행
	}
	
	//현재 이미지와 마우스가 올라갔을때 변경될 이미지를 배개변수로 받는 생성자
	public ButtonClickedEvent(GameFrame parent,int type,ImageIcon enteredIcon,ImageIcon presentIcon) {
		authority = true; //entered이벤트와 exited이벤트 발생 권한 부여
		this.parent = parent;
		this.type = type; //사용자로부터 이동할 메뉴를 생성자로 입력받음
		this.setEnteredIcon(enteredIcon);
		this.setPresentIcon(presentIcon);
	}
	
	
	public ButtonClickedEvent(ImageIcon enteredIcon,ImageIcon presentIcon) {
		this.setEnteredIcon(enteredIcon);
		this.setPresentIcon(presentIcon);
	}
	
	
	@Override //마우스가 컴포넌트 위에 올라갈때의 이벤트
	public void mouseEntered(MouseEvent e) {
		if(authority) { //권한이 있다면 이벤트 발생
			JLabel label = (JLabel)(e.getComponent()); //이벤트가 발생한 라벨을 가져옴
			label.setIcon(getEnteredIcon()); //마우스가 올라갈때의 이미지로 변경
		}
		
	}
	
	@Override //마우스가 컴포넌트 위를 벗어날때 이벤트
	public void mouseExited(MouseEvent e) {
		if(authority) { //권한이 있다면 이벤트 발생
			JLabel label = (JLabel)(e.getComponent()); //이벤트가 발생한 라벨을 가져옴
			label.setIcon(getPresentIcon()); //원래 이미지로 변경
		}
	}
	
	@Override //마우스 버튼이 떼어질때
	public void mouseReleased(MouseEvent e) {
		if(authority) { //권한이 있다면 이벤트 발생
			JLabel label = (JLabel)(e.getComponent()); //이벤트가 발생한 라벨을 가져옴
			label.setIcon(getPresentIcon()); //원래 이미지로 변경
		}
	}
	
	
	
	@Override
	public void mouseClicked(MouseEvent e) {	
//		if(isMusicButton)
//			System.out.println("뮤직버튼");
//		else //뮤직버튼이 아니면 패널을 변경하는 모드로
		parent.swapPanel(type);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
//		JLabel label = (JLabel)(e.getComponent()); //이벤트가 발생한 라벨을 가져옴
//		label.setIcon(presentIcon); //원래 이미지로 변경
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
