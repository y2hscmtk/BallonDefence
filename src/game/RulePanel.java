package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

//��Ģ�� �������ִ� �г�
public class RulePanel extends JPanel {
	private GameFrame parent;//�θ� ������ ����
	
    private ImageIcon icon = new ImageIcon("back.png");
    
    public RulePanel(GameFrame parent) {
        this.parent = parent;//���� �θ� ���������� �����Ѵ�
        setLayout(null); //��ġ ������ ����
        this.setBackground(Color.white);
        setSize(1500,900);
        //setBounds(0, 0, 1500,900);
        //Ȩ��ư
        //Ȩ ȭ������ �̵�
        JButton backButton = new JButton(icon);
        backButton.setSize(200,200);
		backButton.setLocation(1200,50);
        backButton.addMouseListener(new ButtonClickedEvent(parent,parent.BEGINNING_PANEL));
        
        add(backButton);
        setVisible(true);
    }
    
    
  
}

