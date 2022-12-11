package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

//규칙을 설명해주는 패널
public class RulePanel extends JPanel {
	private GameFrame parent;//부모를 변수로 저장
	
    private ImageIcon icon = new ImageIcon("back.png");
    
    public RulePanel(GameFrame parent) {
        this.parent = parent;//받은 부모를 전역변수로 저장한다
        setLayout(null); //배치 관리자 제거
        this.setBackground(Color.white);
        setSize(1500,900);
        //setBounds(0, 0, 1500,900);
        //홈버튼
        //홈 화면으로 이동
        JButton backButton = new JButton(icon);
        backButton.setSize(200,200);
		backButton.setLocation(1200,50);
        backButton.addMouseListener(new ButtonClickedEvent(parent,parent.BEGINNING_PANEL));
        
        add(backButton);
        setVisible(true);
    }
    
    
  
}

