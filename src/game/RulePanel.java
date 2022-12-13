package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

//��Ģ�� �������ִ� �г�
public class RulePanel extends JPanel {
	private GameFrame parent;//�θ� ������ ����
	
    private ImageIcon bgImageicon = new ImageIcon("background.png");
    private Image backgroundPanelImage = bgImageicon.getImage();
	
	//Ȩ ��ư ������
	private ImageIcon homeButtonIcon = new ImageIcon("home.png");
    private ImageIcon homeButtonEnteredIcon = new ImageIcon("homeEntered.png");
    
    //������ ȭ��ǥ ��ư
    private ImageIcon rightArrowIcon = new ImageIcon("rightArrow.png");
    private ImageIcon rightArrowEnteredIcon = new ImageIcon("rightArrowEntered.png");
    
    private ImageIcon[] rules = new ImageIcon[5]; //�Ʒ� �̹������� �̰����� �ű�°� ���
    
    private ImageIcon rule1 = new ImageIcon("rule1.png");
//    private ImageIcon rule2 = new ImageIcon("rule2.png");
//    private ImageIcon rule3 = new ImageIcon("rule3.png");
//    private ImageIcon rule4 = new ImageIcon("rule4.png");
//    private ImageIcon rule5 = new ImageIcon("rule5.png");
    
    //�� �̹����� ȭ�鿡 �����ֱ� ����
    private JLabel ruleLabel;
    
    private JLabel lastRuleLabel; //���� ���� �����ϵ���
    private JLabel nextRuleLabel; //���� ��ȣ�� ���� �����ϵ���
    
    private int index = 0;
    
    //�̹�����ȣ�� �ε����� ����
    public int getIndex() {
    	return index;
    }
    
    
    //���� �̹����� �Ѿ�� �ϴ� Ŭ���� ���� �̺�Ʈ �������̵��ؼ� �ۼ�
    private class NextImageButtonClickedEvent extends ButtonClickedEvent{

    	//������ ȣ��
		public NextImageButtonClickedEvent(GameFrame parent, ImageIcon enteredIcon, ImageIcon presentIcon) {
			super(parent, enteredIcon, presentIcon);
			// TODO Auto-generated constructor stub
		}
		
		//���콺Ŭ�� �̺�Ʈ�� �������̵�
		@Override
		public void mouseClicked(MouseEvent e) {
			ruleLabel.setIcon(rules[index]);
			
			index++; //Ŭ���ϸ� ���� �ε����� �Ѿ����
			if(index>=5) {
				index%=5; //��ⷯ���� =>ó������ ���ư���
			}
		}
    	
    }
    
    
    
//    public void initRuleImage() {
//    	 ruleLabel = new JLabel(rules[0]);
//         //�̹������� ũ��� ����
//         ruleLabel.setSize(rule1.getIconWidth(),rule1.getIconHeight());
//         ruleLabel.setLocation(330,250);
//         add(ruleLabel);
//         
//         
//         //���� �̹��� ��ư
//         
//         JLabel nextImageButton = new JLabel(rightArrowIcon);
//         nextImageButton.setSize(rightArrowIcon.getIconWidth(),rightArrowIcon.getIconHeight());
//         nextImageButton.setLocation(700,180);
//         nextImageButton.addMouseListener(new  NextImageButtonClickedEvent(parent,rightArrowEnteredIcon,rightArrowIcon));
//         add(nextImageButton);
//    }
    
    public RulePanel(GameFrame parent) {
        this.parent = parent;//���� �θ� ���������� �����Ѵ�
        setLayout(null); //��ġ ������ ����
        this.setBackground(Color.white);
        setSize(1500,900);
        
        //�� ���� �̹��� ��������
        //initRuleImage(); //�ʱ�ȭ
        
        //"rule1.png"
        //�� ���� �̹��� �迭 ����
        for(int i=0;i<rules.length;i++) {
        	rules[i] = new ImageIcon("rule"+(i+1)+".png");
        }
        
        
        ruleLabel = new JLabel(rules[0]);
        //�̹������� ũ��� ����
        ruleLabel.setSize(rule1.getIconWidth(),rule1.getIconHeight());
        ruleLabel.setLocation(330,250);
        add(ruleLabel);
        
        
        //���� �̹��� ��ư
        
        JLabel nextImageButton = new JLabel(rightArrowIcon);
        nextImageButton.setSize(rightArrowIcon.getIconWidth(),rightArrowIcon.getIconHeight());
        nextImageButton.setLocation(700,180);
        nextImageButton.addMouseListener(new  NextImageButtonClickedEvent(parent,rightArrowEnteredIcon,rightArrowIcon));
        add(nextImageButton);
        //        nextLevelButton.addMouseListener(new MouseAdapter() {
//			
//			@Override //���콺�� ������Ʈ ���� �ö󰥶��� �̺�Ʈ
//			public void mouseEntered(MouseEvent e) {
//				JLabel label = (JLabel)(e.getComponent()); //�̺�Ʈ�� �߻��� ���� ������
//				label.setIcon(rightArrowEnteredIcon); //���콺�� �ö󰥶��� �̹����� ����
//			}
//			
//			@Override //���콺 ��ư�� ��������
//			public void mouseReleased(MouseEvent e) {
//				JLabel label = (JLabel)(e.getComponent()); //�̺�Ʈ�� �߻��� ���� ������
//				label.setIcon(rightArrowIcon); //���� �̹����� ����
//			}
//			
//			
//			//���� Ŭ��
//			@Override
//			public void mouseClicked(MouseEvent e) {	
//				//������ ���� ���������� �������ͽ�â���� �ѱ��
//				//getStatusPanel().setWeapon(selectedItem);
//				//���� ���ݷ� ����
//				setWeaponPower(selectedItem);				
//				setVisible(false);
//				
//				
////				//2�ʰ� ���
////				try {
////					setVisible(false); //����â �����
////					repaint();
////					System.out.println("1�ʰ� ���");
////					Thread.sleep(1000);
////				} catch (InterruptedException e1) {
////					// TODO Auto-generated catch block
////					e1.printStackTrace();
////				}
////				
//				
//				gameMangeThread.makeBalloonSpawnThreadAndStart(); //���� ������ ���ӻ���
//				//gameMangeThread.setIsStoreOn(); //������ �ٽ� �Ⱥ��̴� ���·� ����
//				gameMangeThread.setIsStoreOn(); //������ �ٽ� �Ⱥ��̴� ���·� ����
//			}
//        
//        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        //�ڷΰ��� ��ư => �ٽ� 4���� �޴� â���� �ǵ��ư���.
        //Ȩ ��ư
  		JLabel homeButton = new JLabel(homeButtonIcon);
  		homeButton.setSize(homeButtonIcon.getIconWidth(),homeButtonIcon.getIconHeight());
  		homeButton.setLocation(1360,20);
  		homeButton.addMouseListener(new ButtonClickedEvent(parent,parent.BEGINNING_PANEL,homeButtonEnteredIcon,homeButtonIcon));
  		add(homeButton);
        
        setVisible(true);
    }
    
    
    @Override
    public void paintComponent(Graphics g) {
       super.paintComponent(g); //�׷��� ������Ʈ ����
       //��� �̹���
       g.drawImage(backgroundPanelImage, 0, 0, this.getWidth(),this.getHeight(),null); //�̹����� �׷����� ���� �˸����� �ʱ�
    }
  
}

