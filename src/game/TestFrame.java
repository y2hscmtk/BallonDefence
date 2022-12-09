package game;

import javax.swing.JFrame;

public class TestFrame extends JFrame{
	public void TestFrame() {
		System.out.println("생성자 호출됨");
		setSize(200,200);
		setLocation(200,200);
		setVisible(true); //생성된 순간 보이도록 하기
	}
}
