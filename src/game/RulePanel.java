package game;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

//규칙을 설명해주는 패널
public class RulePanel extends JPanel {
	private GameFrame parent;//부모를 변수로 저장
	
	//홈 버튼 아이콘
	private ImageIcon homeButtonIcon = new ImageIcon("home.png");
    private ImageIcon homeButtonEnteredIcon = new ImageIcon("homeEntered.png");
    
    public RulePanel(GameFrame parent) {
        this.parent = parent;//받은 부모를 전역변수로 저장한다
        setLayout(null); //배치 관리자 제거
        this.setBackground(Color.white);
        setSize(1500,900);
        
        //뒤로가기 버튼 => 다시 4가지 메뉴 창으로 되돌아간다.
        //홈 버튼
  		JLabel homeButton = new JLabel(homeButtonIcon);
  		homeButton.setSize(homeButtonIcon.getIconWidth(),homeButtonIcon.getIconHeight());
  		homeButton.setLocation(1360,20);
  		homeButton.addMouseListener(new ButtonClickedEvent(parent,parent.BEGINNING_PANEL,homeButtonEnteredIcon,homeButtonIcon));
  		add(homeButton);
        
        setVisible(true);
    }
    
    
  
}

