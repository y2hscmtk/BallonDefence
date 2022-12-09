package game;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

//���� �г��� �������� ����ڿ��� ������ �����Ҽ� �ִ� �������� ����
public class GameFrame extends JFrame{

	public GameFrame() {
		setSize(1500,900);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //x��ư�� ���� ���α׷��� �����ϵ���
		//������ ������ ��ġ�� ����
		Dimension frameSize = this.getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// (�����ȭ�� ���� - ������ȭ�� ����) / 2, (�����ȭ�� ���� - ������ȭ�� ����) / 2
		this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		
		this.setResizable(false);
		
		setVisible(true);
	}
	
	
}
