package game;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

//��Ģ�� �������ִ� �г�
public class RulePanel extends JPanel {
	private GameFrame parent;//�θ� ������ ����
	
	//Ȩ ��ư ������
	private ImageIcon homeButtonIcon = new ImageIcon("home.png");
    private ImageIcon homeButtonEnteredIcon = new ImageIcon("homeEntered.png");
    
    public RulePanel(GameFrame parent) {
        this.parent = parent;//���� �θ� ���������� �����Ѵ�
        setLayout(null); //��ġ ������ ����
        this.setBackground(Color.white);
        setSize(1500,900);
        
        //�ڷΰ��� ��ư => �ٽ� 4���� �޴� â���� �ǵ��ư���.
        //Ȩ ��ư
  		JLabel homeButton = new JLabel(homeButtonIcon);
  		homeButton.setSize(homeButtonIcon.getIconWidth(),homeButtonIcon.getIconHeight());
  		homeButton.setLocation(1360,20);
  		homeButton.addMouseListener(new ButtonClickedEvent(parent,parent.BEGINNING_PANEL,homeButtonEnteredIcon,homeButtonIcon));
  		add(homeButton);
        
        setVisible(true);
    }
    
    
  
}

