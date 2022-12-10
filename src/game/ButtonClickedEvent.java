package game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class ButtonClickedEvent extends MouseAdapter{
	private GameFrame parent;//�θ� ����
	private int type;
	
	public ButtonClickedEvent(GameFrame parent,int type){
		this.parent = parent;
		this.type = type; //����ڷκ��� �̵��� �޴��� �����ڷ� �Է¹���
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		parent.swapPanel(type);
	}
}
