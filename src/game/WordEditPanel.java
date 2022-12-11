package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

//�ܾ� ���� �г�
public class WordEditPanel extends JPanel{
	private GameFrame parent;//�θ� ����
	private ImageIcon backgroundIcon = new ImageIcon("wordEditPanelImage.png");
	private Image backgroundImage = backgroundIcon.getImage();
	
	
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
	
	
	//��� �̹��� �׸���
  	@Override
  	public void paintComponent(Graphics g) {
  		super.paintComponent(g); //�׷��� ������Ʈ ����
  		//��� �̹���
  		g.drawImage(backgroundImage, 0, 0, this.getWidth(),this.getHeight(),null); //�̹����� �׷����� ���� �˸����� �ʱ�
  	}
}