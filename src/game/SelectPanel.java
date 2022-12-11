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
    
    private ImageIcon sangsangBugiIcon = new ImageIcon("sangsangbugi.png");  //���α� ������
    private ImageIcon hansungNyanIicon = new ImageIcon("hansungNyang-i.png"); //�Ѽ����� ������
    private ImageIcon kkukkukkakkaIcon = new ImageIcon("kkukkukkakka.png"); //�ٲ�&��� ������
    
    private ImageIcon bgImageicon = new ImageIcon("selectBackgroundImage.png");
    private Image selectBackgroundImage = bgImageicon.getImage();
    
    private int selectType; //����ڰ� ������ ĳ���� ����
    private boolean flag = false; //����ڰ� ĳ���� ������ �ߴ��� Ȯ���ϴ� �뵵
    
    //ĳ���� ��ư�� Ŭ�������� �߻��� �̺�Ʈ
    //�����ڷ� ������ ĳ���� Ÿ���� �Է¹޾Ƽ� �����г��� ������ �г��� �����Ѵ�.
    private class CharacterSelectEvent extends MouseAdapter{
    	private int characterType;
    	
    	public CharacterSelectEvent(int characterType) {
    		this.characterType = characterType;
    	}
    	
    	@Override
    	public void mouseClicked(MouseEvent e) {
    		GamePanel game = new GamePanel(characterType); //���α�(�ڵ�0)�� ���� ����
    		parent.setContentPane(game);
    		//parent.swapPanel(GameFrame.BEGINNING_PANEL);//�θ��� ����Ʈ���� �����ϵ��� �Լ� ȣ��
    	}
    }
    
    
    public SelectPanel(GameFrame parent) {
        this.parent = parent;//���� �θ� ���������� �����Ѵ�
        setLayout(null); //��ġ ������ ����
        this.setBackground(Color.gray);
        setSize(1500,900);
        //setBounds(0, 0, 1500,900);

        //�ڷΰ��� ��ư => �ٽ� 4���� �޴� â���� �ǵ��ư���.
        JButton backButton = new JButton("�ڷΰ���");
        backButton.setSize(200,200);
        backButton.setLocation(10,25);
        backButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		parent.swapPanel(GameFrame.BEGINNING_PANEL);//�θ��� ����Ʈ���� �����ϱ� ���� �Լ� ȣ��
        	}
        });
        add(backButton);
        
        //����ڷκ��� �Է��� �޴� �κ�
        //�ؽ�Ʈ �ڽ��� ���̺� 3���� �ʿ�(���α�, �Ѽ�����, ����&�ٲ� ĳ���� ����â)
        
//        //���̵� �Է¹޴� â
//        JTextField id = new JTextField(20); 
//        id.setSize(200,10);
//        id.setLocation(500,500);//�ӽ� ��ġ
//        add(id);
        
        //ĳ���� ����â => �� ĳ���Ͱ� ���õǾ��ٸ� �ٽ� Ŭ���ϱ� ������, �ٸ� ĳ���ʹ� �����Ҽ� ������ �ؾ���
        //ĳ���� ���� ����, �ٽ� �ش� ĳ���͸� Ŭ���Ͽ��ٸ� ���������� ���µǰ�, �ٸ� ĳ���͸� �����Ҽ� �ֵ���
        
        
        //���α� �̹�����ư
        JLabel sangsangBugiLabel = new JLabel(sangsangBugiIcon);
        sangsangBugiLabel.setSize(sangsangBugiIcon.getIconWidth(),sangsangBugiIcon.getIconHeight());
        sangsangBugiLabel.setLocation(310, 270);
        sangsangBugiLabel.addMouseListener(new CharacterSelectEvent(0));
        add(sangsangBugiLabel);
        		
//        		MouseAdapter() {
//        	@Override
//        	public void mouseClicked(MouseEvent e) {
//        		GamePanel game = new GamePanel(0); //���α�(�ڵ�0)�� ���� ����
//        		parent.setContentPane(game);
//        		//parent.swapPanel(GameFrame.BEGINNING_PANEL);//�θ��� ����Ʈ���� �����ϵ��� �Լ� ȣ��
//        	}
//        });
        
        
        //�Ѽ����� �̹�����ư
        JLabel hansungNyanILabel = new JLabel(hansungNyanIicon);
        hansungNyanILabel.setSize(hansungNyanIicon.getIconWidth(),hansungNyanIicon.getIconHeight());
        hansungNyanILabel.setLocation(610, 270);
        hansungNyanILabel.addMouseListener(new CharacterSelectEvent(1));
        add(hansungNyanILabel);


        //�ٲٱ �̹�����ư
        JLabel kkukkuKkakkaLabel = new JLabel(kkukkukkakkaIcon);
        kkukkuKkakkaLabel.setSize(kkukkukkakkaIcon.getIconWidth(),kkukkukkakkaIcon.getIconHeight());
        kkukkuKkakkaLabel.setLocation(910, 270);
        kkukkuKkakkaLabel.addMouseListener(new CharacterSelectEvent(2)); 
        add(kkukkuKkakkaLabel);

        //ĳ���͸� �����ϸ� �ٷ� �Ѿ�� �Ұ��ΰ�, ��ư�� ���� ������ �����Ұ��ΰ� ����
//        JButton startButton = new JButton("���ӽ���"); 
//        startButton.setSize(100,100);
//        startButton.setLocation(800,800);
//        startButton.addMouseListener(new MouseAdapter() {
//        	//��ư�� Ŭ�������� ����ڰ� ĳ���Ϳ� ���̵� �Է��ߴ��� Ȯ���ϰ�,
//        	//�Է����� �ʾҴٸ� ���â�� ����
//        	//�Է��Ͽ��ٸ� �Է������� �������� ������ �����Ѵ�.
//        	@Override
//        	public void mouseClicked(MouseEvent e) {
//        		//���� �г� ������ �ش� �гη� ���Ƴ����
//        		
//        		
////        		parent.setContentPane(new );
//        	}
//        });
//        add(startButton);
        
        
        setVisible(true);
    }
    
    @Override
    public void paintComponent(Graphics g) {
       super.paintComponent(g); //�׷��� ������Ʈ ����
       
       //��� �̹��� ����
       g.drawImage(selectBackgroundImage, 0, 0, this.getWidth(),this.getHeight(),null);
    }
}