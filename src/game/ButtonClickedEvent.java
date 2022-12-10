package game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class ButtonClickedEvent extends MouseAdapter{
	private GameFrame parent;//부모 변수
	private int type;
	
	public ButtonClickedEvent(GameFrame parent,int type){
		this.parent = parent;
		this.type = type; //사용자로부터 이동할 메뉴를 생성자로 입력받음
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		parent.swapPanel(type);
	}
}
