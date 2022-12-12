package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import game.GameFrame.Music;


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
    private ImageIcon selectLabelEnteredicon = new ImageIcon("goGameEntered.png");
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
    
    private ImageIcon soundLabelIcon = new ImageIcon("music.png");
    private ImageIcon soundLabelMuteIcon = new ImageIcon("mute.png");
    
    private Music music; //���� �гο��� ������ �����ֵ���
    
    private boolean MusicOn = true;//���� ������ �÷��������� ���θ� ����
    
    
    //���� ���� ������¸� �����Ѵ�.
    public boolean isMusicOn() {
    	return MusicOn;
    }
//    TestFrame tf;
//    
    //���� ���ǻ��¸� ����
    public void setMusicOn(boolean onOff) {
    	MusicOn = onOff;
    }
    
    
    //������ �����ϱ� ���� ������ �ۼ��� ��ưŬ���̺�Ʈ�� �������̵��Ͽ� ���ο� ��� �ۼ�
    private class SoundButtonClickedEvent extends ButtonClickedEvent{
    	private GameFrame parent;
    	
    	public SoundButtonClickedEvent(GameFrame parent,ImageIcon enteredIcon, ImageIcon presentIcon) {
    		super(parent,enteredIcon, presentIcon); //�θ� �����ڿ� �Ѱ��ش�.
    		// TODO Auto-generated constructor stub
    		this.parent = parent;
    	}


    	@Override //���콺�� �ö������� �̺�Ʈ�� �����ϱ� ���� �������̵�
    	public void mouseEntered(MouseEvent e) {
			
    	}
    	
    	
    	@Override //���콺�� Ŭ���Ǿ����� ���� ������ �۵������� Ȯ���Ͽ� ������ Ű�� ��
    	public void mouseClicked(MouseEvent e) {
    		JLabel label = (JLabel)(e.getComponent()); //�̺�Ʈ�� �߻��� ���� ������
    		if(isMusicOn()) {//���� ������ ������̶��
    			System.out.println("���� ����� -> ����");
    			label.setIcon(getEnteredIcon()); //xǥ�÷� ����
    			parent.getMusic().musicStop();
    			setMusicOn(false); //���� �������·� ǥ��
    		}
    			
    		else {//���� ������ �����ִ� ���¶��
    			System.out.println("���� ���� -> ���");
    			label.setIcon(getPresentIcon()); //��ǥ ǥ�÷� ����
    			parent.getMusic().resumeMusic();
//    			parent.getMusic().start();//���� ���ĺ��� �������
    			setMusicOn(true); //���� �������·� ǥ��
    		}
    	}
    }
    	
    
    
    
    
    public BeginningPanel(GameFrame parent) {
        this.parent = parent;//�θ� �Է¹޾� ������ ����
//        this.music = music;// ������ �����ϵ��� �ϱ� ���Ͽ�
        
        setLayout(null); //��ġ ������ ����
        
        //�Ҹ� ���� ��ư
        JLabel soundButtonLabel = new JLabel(soundLabelIcon);
        soundButtonLabel.setSize(soundLabelIcon.getIconWidth(),soundLabelIcon.getIconHeight());
        soundButtonLabel.setLocation(20, 20);
        soundButtonLabel.addMouseListener(new SoundButtonClickedEvent(parent,soundLabelMuteIcon,soundLabelIcon));
        		
//        		new ButtonClickedEvent(parent, GameFrame.SELECT_PANEL,soundLabelMuteIcon,soundLabelIcon));
        add(soundButtonLabel);
        
        //4���� ��ư�� �ް� �ִ� panel�� ����
        //1. ���� ���� ��ư
        //=> ��ư�� ������ �������� �г��� ���� �гη� �̵�, ���� �г��� �����ӿ��� ����
        JLabel startButtonLabel = new JLabel(selectLabelicon);
        startButtonLabel.setSize(selectLabelicon.getIconWidth(),selectLabelicon.getIconHeight());
        startButtonLabel.setLocation(398, 200);
        startButtonLabel.addMouseListener(new ButtonClickedEvent(parent, GameFrame.SELECT_PANEL,selectLabelEnteredicon,selectLabelicon));
        add(startButtonLabel);		
        
        //2. ��Ģ ���� ��ư
        //=> ��ư�� ������ �������� �г��� ��Ģ�����гη� �̵�, ���� �г��� �����ӿ��� ����
        JLabel ruleButtonLabel = new JLabel(ruleLabelIcon);
        ruleButtonLabel.setSize(ruleLabelIcon.getIconWidth(),ruleLabelIcon.getIconHeight());
        ruleButtonLabel.setLocation(398, 328);
        ruleButtonLabel.addMouseListener(new ButtonClickedEvent(parent, GameFrame.RULE_PANEL));
        add(ruleButtonLabel);

        //3. �ܾ� ���� ��ư
        //=> ��ư�� ������ �������� �г��� �ܾ������гη� �̵�, ���� �г��� �����ӿ��� ����
        JLabel wordEditButtonLabel = new JLabel(editLabelIcon);
        wordEditButtonLabel.setSize(editLabelIcon.getIconWidth(),editLabelIcon.getIconHeight());
        wordEditButtonLabel.setLocation(398, 458);
        wordEditButtonLabel.addMouseListener(new ButtonClickedEvent(parent, GameFrame.EDIT_PANEL));
        add(wordEditButtonLabel);

        //4. ���� ���� ��ư
        //=> ��ư�� ������ �������� �г��� ��ŷ�гη� �̵�, ���� �г��� �����ӿ��� ����
        JLabel showLankingButtonLabel = new JLabel(langkingLabelIcon);
        showLankingButtonLabel.setSize(editLabelIcon.getIconWidth(),editLabelIcon.getIconHeight());
        showLankingButtonLabel.setLocation(398, 593);
        showLankingButtonLabel.addMouseListener(new ButtonClickedEvent(parent, GameFrame.RANKING_PANEL));
        add(showLankingButtonLabel);

        
//        //���ο� ������ ������ �׽�Ʈ ��ư
//        JButton testButton = new JButton("������ ����");
//        testButton.setSize(500, 100);
//        testButton.setLocation(500, 100);
//        testButton.addMouseListener(new MouseAdapter() {
//        	@Override
//        	public void mouseClicked(MouseEvent e) {
//        		System.out.println("��ư ������");
//        		if(tf==null) {
//        			tf = new TestFrame();
//        			tf.setVisible(true);
//        			tf.setSize(250,100);
//        			tf.setLocation(200,200);
//        		}
//        		else {
//        			System.out.println("�̹� �����Ǿ�����");
//        		}
//        		//new TestFrame(); // ��ư�� ���� ���� ���ο� �������� ������
//        	}
//        });
//        //add(testButton);
    }
    
    @Override
    public void paintComponent(Graphics g) {
       super.paintComponent(g); //�׷��� ������Ʈ ����
       //��� �̹���
       g.drawImage(backgroundPanelImage, 0, 0, this.getWidth(),this.getHeight(),null); //�̹����� �׷����� ���� �˸����� �ʱ�
    }
    
    
}