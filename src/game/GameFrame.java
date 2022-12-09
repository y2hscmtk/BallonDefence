package game;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

//여러 패널을 돌려가며 사용자에게 게임을 조작할수 있는 프레임을 제공
public class GameFrame extends JFrame{

	public GameFrame() {
		setSize(1500,900);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //x버튼을 눌러 프로그램을 종료하도록
		//게임이 생성될 위치를 지정
		Dimension frameSize = this.getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// (모니터화면 가로 - 프레임화면 가로) / 2, (모니터화면 세로 - 프레임화면 세로) / 2
		this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		
		this.setResizable(false);
		
		setVisible(true);
	}
	
	
}
