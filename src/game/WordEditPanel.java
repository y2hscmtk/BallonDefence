package game;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class WordEditPanel extends JPanel{
	private GameFrame parent;//�θ� ����
	
	public WordEditPanel (GameFrame parent) {
        this.parent = parent;//�θ� �Է¹޾� ������ ����
        setLayout(null); //��ġ ������ ����
        setBackground(Color.green);
        setSize(1500,900);
        //setBounds(0, 0, 1500,900);
        //���� ���� ��ư
        //=> ��ư�� ������ �������� �г��� ��ŷ�гη� �̵�, ���� �г��� �����ӿ��� ����
        JButton showLankingButton = new JButton("��ŷ ����");
        showLankingButton.setSize(500, 100);
        showLankingButton.setLocation(500, 575);
        showLankingButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) { //��ŷ�гη� �̵�
        		parent.swapPanel(GameFrame.RANKING_PANEL);//�θ��� ����Ʈ���� �����ϵ��� �Լ� ȣ��
        	}
        });
        add(showLankingButton);
        

    }
}