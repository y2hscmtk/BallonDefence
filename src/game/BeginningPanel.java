package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


class TestFrame extends JFrame{
	
	public void TestFrame() {
		System.out.println("�� ������ ����");
		setSize(200,200);
		setLocation(200,200);
		setVisible(true); //������ ���� ���̵��� �ϱ�
	}
}


public class BeginningPanel extends JPanel {
    private GameFrame parent;//�θ� ����
    //��� �̹���
    private ImageIcon bgImageicon = new ImageIcon("background.png");
    private Image backgroundPanelImage = bgImageicon.getImage();
    //���� ���� ��ư(���̺�) �̹���
    private ImageIcon selectLabelicon = new ImageIcon("goGame.png");
    //private Image gameStartLabelImage = selectLabelicon.getImage();
    //���� ���� ��ư(���̺�) �̹���
    private ImageIcon ruleLabelIcon = new ImageIcon("goRule.png");
    //private Image ruleLabelImage = ruleLabelIcon.getImage();
    //��ŷ ���� ��ư(���̺�) �̹���
    private ImageIcon langkingLabelIcon = new ImageIcon("goRangking.png");
    //private Image showRangkingLabelImage = langkingLabelIcon.getImage();
    //�ܾ� ���� ��ư(���̺�) �̹���
    private ImageIcon editLabelIcon = new ImageIcon("goEdit.png");
    //private Image EditButtonImage = editLabelIcon.getImage();
    
    
    TestFrame tf;
    
    
    
    public BeginningPanel(GameFrame parent) {
        this.parent = parent;//�θ� �Է¹޾� ������ ����
        
        setLayout(null); //��ġ ������ ����
        
        //4���� ��ư�� �ް� �ִ� panel�� ����
        //1. ���� ���� ��ư
        //=> ��ư�� ������ �������� �г��� ���� �гη� �̵�, ���� �г��� �����ӿ��� ����
        JLabel startButtonLabel = new JLabel(selectLabelicon);
        startButtonLabel.setSize(selectLabelicon.getIconWidth(),selectLabelicon.getIconHeight());
        startButtonLabel.setLocation(398, 211);
        startButtonLabel.addMouseListener(new ButtonClickedEvent(parent, GameFrame.SELECT_PANEL));
        add(startButtonLabel);		
        
        //2. ��Ģ ���� ��ư
        //=> ��ư�� ������ �������� �г��� ��Ģ�����гη� �̵�, ���� �г��� �����ӿ��� ����
        JLabel ruleButtonLabel = new JLabel(ruleLabelIcon);
        ruleButtonLabel.setSize(ruleLabelIcon.getIconWidth(),ruleLabelIcon.getIconHeight());
        ruleButtonLabel.setLocation(398, 358);
        ruleButtonLabel.addMouseListener(new ButtonClickedEvent(parent, GameFrame.RULE_PANEL));
        add(ruleButtonLabel);

        //3. �ܾ� ���� ��ư
        //=> ��ư�� ������ �������� �г��� �ܾ������гη� �̵�, ���� �г��� �����ӿ��� ����
        JLabel wordEditButtonLabel = new JLabel(editLabelIcon);
        wordEditButtonLabel.setSize(editLabelIcon.getIconWidth(),editLabelIcon.getIconHeight());
        wordEditButtonLabel.setLocation(398, 498);
        wordEditButtonLabel.addMouseListener(new ButtonClickedEvent(parent, GameFrame.EDIT_PANEL));
        add(wordEditButtonLabel);

        //4. ���� ���� ��ư
        //=> ��ư�� ������ �������� �г��� ��ŷ�гη� �̵�, ���� �г��� �����ӿ��� ����
        JLabel showLankingButtonLabel = new JLabel(langkingLabelIcon);
        showLankingButtonLabel.setSize(editLabelIcon.getIconWidth(),editLabelIcon.getIconHeight());
        showLankingButtonLabel.setLocation(398, 643);
        showLankingButtonLabel.addMouseListener(new ButtonClickedEvent(parent, GameFrame.RANKING_PANEL));
        add(showLankingButtonLabel);

        
        //���ο� ������ ������ �׽�Ʈ ��ư
        JButton testButton = new JButton("������ ����");
        testButton.setSize(500, 100);
        testButton.setLocation(500, 100);
        testButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		System.out.println("��ư ������");
        		if(tf==null) {
        			tf = new TestFrame();
        			tf.setVisible(true);
        			tf.setSize(250,100);
        			tf.setLocation(200,200);
        		}
        		else {
        			System.out.println("�̹� �����Ǿ�����");
        		}
        		//new TestFrame(); // ��ư�� ���� ���� ���ο� �������� ������
        	}
        });
        //add(testButton);
    }
    
    @Override
    public void paintComponent(Graphics g) {
       super.paintComponent(g); //�׷��� ������Ʈ ����
       //��� �̹���
       g.drawImage(backgroundPanelImage, 0, 0, this.getWidth(),this.getHeight(),null); //�̹����� �׷����� ���� �˸����� �ʱ�
    }
    
    
}