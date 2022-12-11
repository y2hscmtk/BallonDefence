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
        backButton.setSize(icon.getIconWidth(),icon.getIconHeight());
        backButton.setLocation(500,325);
        backButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		parent.swapPanel(GameFrame.BEGINNING_PANEL);//부모의 컨텐트팬을 변경하기 위해 함수 호출
        	}
        });
        
        add(backButton);
        setVisible(true);
    }
    
    
  
}

