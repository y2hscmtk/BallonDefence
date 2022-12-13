package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


//���̵� �Է��ϰ� ĳ���͸� �����ϴ� â => ���̵� �Է��� ���� ������ �޴°ɷ� ����
//������� �Է¿� ���缭 ������ �����Ѵ�.
public class SelectPanel extends JPanel {
    private GameFrame parent;//�θ� ������ ����
   
    
    //Ȩ��ư�� ���� ������
    private ImageIcon homeButtonIcon = new ImageIcon("home.png");
    private ImageIcon homeButtonEnteredIcon = new ImageIcon("homeEntered.png");
    
    private ImageIcon sangsangBugiIcon = new ImageIcon("sangsangbugi.png");  //���α� ������
    private ImageIcon hansungNyanIicon = new ImageIcon("hansungNyang-i.png"); //�Ѽ����� ������
    private ImageIcon kkokkokkukkuIcon = new ImageIcon("kkokkokkukku.png"); //����&�ٲ� ������
    
    //���콺�� �ö����� ĳ���� ��ư�� ��ȭ�� �ֱ� ����
    private ImageIcon enteredBugi = new ImageIcon("sangsangbugiEntered.png");
    private ImageIcon enteredHansungNyangi = new ImageIcon("hansungNyang-iEntered.png");
    private ImageIcon enteredKKKK = new ImageIcon("kkokkokkukkuEntered.png");
    
    //��� �̹���
    private ImageIcon selectBackgroundImageicon = new ImageIcon("selectBackgroundImage.png");
    private Image selectBackgroundImage = selectBackgroundImageicon.getImage();
    
//    private ImageIcon bgImageicon = new ImageIcon("background.png");
//    private Image backgroundPanelImage = bgImageicon.getImage();
//    
    
    private int selectType; //����ڰ� ������ ĳ���� ����
//    private boolean flag = false; //����ڰ� ĳ���� ������ �ߴ��� Ȯ���ϴ� �뵵
    
    //ĳ���� ��ư�� Ŭ�������� �߻��� �̺�Ʈ
    //�����ڷ� ������ ĳ���� Ÿ���� �Է¹޾Ƽ� �����г��� ������ �г��� �����Ѵ�.
    //���� ��ư Ŭ�� �̺�Ʈ�� �������̵��ؼ� �ۼ�
    private class CharacterSelectButtonClickedEvent extends ButtonClickedEvent{

//    	
    	public CharacterSelectButtonClickedEvent(GameFrame parent,int characterType,ImageIcon enteredIcon,ImageIcon presentIcon) {
    		super(parent,characterType,enteredIcon,presentIcon); //�θ�����ڿ� �Ѱ���

    	}
    	
    	@Override
    	public void mouseClicked(MouseEvent e) {
    		GamePanel game = new GamePanel(getType(),parent); //���α�(�ڵ�0)�� ���� ����
    		
    		//���� �������� ���� ����
    		//parent.getMusicThread()
    		parent.getMusic().musicStop(); //���� �ߴ�
    		//���� �ߴ� ����� ����
//    		parent.
    		
    		//���� �������� ���� ���� => ���� ���� �÷��� ���� true�� �����ؾ���(���� �г�?)
    		parent.getMusic().changeMusic("gameMusic.wav");
    		
    		parent.setContentPane(game);
    		//parent.swapPanel(GameFrame.BEGINNING_PANEL);//�θ��� ����Ʈ���� �����ϵ��� �Լ� ȣ��
    	}
    	
    	
//    	@Override //���콺�� ������Ʈ ���� �ö󰥶��� �̺�Ʈ
//    	public void mouseEntered(MouseEvent e) {
//    		JLabel label = (JLabel)(e.getComponent()); //�̺�Ʈ�� �߻��� ���� ������
//    		label.setIcon(enteredIcon); //���콺�� �ö󰥶��� �̹����� ����
//    	}
//    	
//    	@Override //���콺�� ������Ʈ ���� ����� �̺�Ʈ
//    	public void mouseExited(MouseEvent e) {
//			JLabel label = (JLabel)(e.getComponent()); //�̺�Ʈ�� �߻��� ���� ������
//			label.setIcon(presentIcon); //���콺�� �ö󰥶��� �̹����� ����
//    	}
    	
    }
    
    
    public SelectPanel(GameFrame parent) {
        this.parent = parent;//���� �θ� ���������� �����Ѵ�
        setLayout(null); //��ġ ������ ����
        this.setBackground(Color.gray);
        setSize(1500,900);

        //�ڷΰ��� ��ư => �ٽ� 4���� �޴� â���� �ǵ��ư���.
        //Ȩ ��ư
  		JLabel homeButton = new JLabel(homeButtonIcon);
  		homeButton.setSize(homeButtonIcon.getIconWidth(),homeButtonIcon.getIconHeight());
  		homeButton.setLocation(1360,20);
  		homeButton.addMouseListener(new ButtonClickedEvent(parent,parent.BEGINNING_PANEL,homeButtonEnteredIcon,homeButtonIcon));
  		add(homeButton);
        

  		//ĳ���� ��ư �̺�Ʈ => ��ư Ŭ�� �̺�Ʈ�� �������̵��ؼ� ĳ���� �����ϴ� �κи� �߰��� ����?
        
        //���α� �̹�����ư
        JLabel sangsangBugiLabel = new JLabel(sangsangBugiIcon);
        sangsangBugiLabel.setSize(sangsangBugiIcon.getIconWidth(),sangsangBugiIcon.getIconHeight());
        sangsangBugiLabel.setLocation(310, 230);
        sangsangBugiLabel.addMouseListener(new CharacterSelectButtonClickedEvent(parent,0,enteredBugi,sangsangBugiIcon));
        add(sangsangBugiLabel);
        		
        //�Ѽ����� �̹�����ư
        JLabel hansungNyanILabel = new JLabel(hansungNyanIicon);
        hansungNyanILabel.setSize(hansungNyanIicon.getIconWidth(),hansungNyanIicon.getIconHeight());
        hansungNyanILabel.setLocation(610, 230);
        hansungNyanILabel.addMouseListener(new CharacterSelectButtonClickedEvent(parent,1,enteredHansungNyangi,hansungNyanIicon));
        add(hansungNyanILabel);


        //�����ٲ� �̹�����ư
        JLabel kkokkokkukkuLabel = new JLabel(kkokkokkukkuIcon);
        kkokkokkukkuLabel.setSize(kkokkokkukkuIcon.getIconWidth(),kkokkokkukkuIcon.getIconHeight());
        kkokkokkukkuLabel.setLocation(910, 230);
        kkokkokkukkuLabel.addMouseListener(new CharacterSelectButtonClickedEvent(parent,2, enteredKKKK, kkokkokkukkuIcon)); 
        add(kkokkokkukkuLabel);


        
        setVisible(true);
    }
    
    @Override
    public void paintComponent(Graphics g) {
       super.paintComponent(g); //�׷��� ������Ʈ ����
       //��� �̹���
       g.drawImage(selectBackgroundImage, 0, 0,selectBackgroundImageicon.getIconWidth(),selectBackgroundImageicon.getIconHeight(),null); //�̹����� �׷����� ���� �˸����� �ʱ�
    }
}
