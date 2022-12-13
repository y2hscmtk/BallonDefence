package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
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
		
		@Override //�̺�Ʈ ����
		public void mouseClicked(MouseEvent e) {
			
		}
		
		//���콺Ŭ�� �̺�Ʈ�� �������̵�
		@Override
		public void mousePressed(MouseEvent e) {
			ruleLabel.setIcon(rules[index]);
			
			index++; //Ŭ���ϸ� ���� �ε����� �Ѿ����
			if(index>=5) {
				index%=5; //��ⷯ���� =>ó������ ���ư���
			}
			
			JLabel label = (JLabel)(e.getComponent()); //�̺�Ʈ�� �߻��� ���� ������
//			label.setIcon(presentIcon); //���� �̹����� ����
			//��ư�� ������ ���� �Ҹ��� ������
			try {
				setClip(AudioSystem.getClip());
				File audioFile = new File("ButtonClick.wav");
				AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
				getClip().open(audioStream);
			}catch(Exception E) {
				System.out.println("����!");
			}
			getClip().start(); // ��ư�� Ŭ�������� �Ҹ��� ������
			
		}
    	
    }
    

    
    public RulePanel(GameFrame parent) {
        this.parent = parent;//���� �θ� ���������� �����Ѵ�
        setLayout(null); //��ġ ������ ����
        this.setBackground(Color.white);
        setSize(1500,900);
        
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

