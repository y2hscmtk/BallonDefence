package game;

import javax.swing.JButton;
import javax.swing.JPanel;

//���� �г�, �����ӿ� ����
//���� Ÿ��Ʋ �޴����� ������ �г�
public class BeginningPanel extends JPanel{
	
	
	public BeginningPanel() {
		
		
		//4���� ��ư�� �ް� �ִ� panel�� ����
		//1. ���� ���� ��ư 
		//=> ��ư�� ������ �������� �г��� ���� �гη� �̵�, ���� �г��� �����ӿ��� ����
		JButton startButton = new JButton();
		startButton.setSize(100,10);
		startButton.setLocation(100,100);
		
		//2. ��Ģ ���� ��ư
		//=> ��ư�� ������ �������� �г��� ��Ģ�����гη� �̵�, ���� �г��� �����ӿ��� ����
		
		//3. �ܾ� ���� ��ư
		//=> ��ư�� ������ �������� �г��� �ܾ������гη� �̵�, ���� �г��� �����ӿ��� ����
		
		//���� ���� ��ư
		//=> ��ư�� ������ �������� �г��� ��ŷ�гη� �̵�, ���� �г��� �����ӿ��� ����
	}
	
}
