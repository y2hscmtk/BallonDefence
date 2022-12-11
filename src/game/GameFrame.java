package game;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;

//���� �г��� �������� ����ڿ��� ������ �����Ҽ� �ִ� �������� ����
//�������� ������ �гο��� ������
public class GameFrame extends JFrame {
	//������� ��ư Ŭ���� ���� �г��� ���Ƴ��� �پ��� �޴��� �� �� �ֵ���
    public static final int BEGINNING_PANEL = 0; //���� �г�
    public static final int SELECT_PANEL = 1; //���� �г�
    public static final int RULE_PANEL = 2; //�� ���� �г�
    public static final int EDIT_PANEL = 3; //�� ���� �г�
    public static final int RANKING_PANEL = 4; //��ŷ ���� �г�
    
    //swapPanel�Լ��� ���� �г��� ���Ƴ��� �ֵ��� �ϱ� ����
    //������ GameFrame�� �����ڰ� �ҷ��� ����
    private SelectPanel selectPanel;
    private BeginningPanel beginningPanel;
    private WordEditPanel wordEditPanel;
    private RankingPanel rankingPanel;
    private RulePanel rulePanel;
    private GamePanel gamePanel;
    
    private MusicThread musicThread;
    
    public MusicThread getMusicThread() {
    	return musicThread;
    }
    
    
    public GameFrame() {
    	setTitle("BallonDefense");
    	
    	//�г� ���� => �ڱ� �ڽ��� �����ڷ� �Ѱ��༭ �θ� ��������
    	selectPanel = new SelectPanel(this);
        beginningPanel = new BeginningPanel(this);
        wordEditPanel = new WordEditPanel(this);
        rankingPanel = new RankingPanel(this);
        rulePanel = new RulePanel(this);
        
        setSize(1500, 900);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //x��ư�� ���� ���α׷��� �����ϵ���
        //�������� ������ ��ġ�� ����(���� ȭ�� ��ġ)
        Dimension frameSize = this.getSize();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // (�����ȭ�� ���� - ������ȭ�� ����) / 2, (�����ȭ�� ���� - ������ȭ�� ����) / 2
        this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

        this.setResizable(false); //ũ�� ���� �Ұ����ϰ�
        setContentPane(beginningPanel); //����Ʈ���� �����гη� ����
        
        musicThread = new MusicThread("openningMusic.wav"); //����ȭ�� ���� ����
        musicThread.start();
        
        setVisible(true);
    }
    
    //���۹������ �÷��� ������
    public class MusicThread extends Thread{
    	private String path; //���������� ��θ� �������
    	private Clip clip; //Ŭ���� �ʵ�� ����
    	
    	public MusicThread(String path) {
    		this.path = path;
    	}
    	
    	//���� ����
    	public void musicStop() {
    		clip.stop(); //����� ��� �ߴ�
    	}
  
  	
    	@Override
    	public void run() {
    		try{
        		AudioInputStream ais = AudioSystem.getAudioInputStream(new File(path));
        		clip = AudioSystem.getClip();
	            clip.open(ais);
	            clip.start();
	            clip.loop(Clip.LOOP_CONTINUOUSLY);
	        }catch (Exception ex){
	        	System.out.println("�ҷ����� ����");
    	    }
    	}
    	
    }
    
   
    
    //������� ��ư Ŭ���� ���� ����Ʈ���� �ٸ��� �ٿ����� ȭ���� ��ȭ��Ű�� �޼ҵ�
    public void swapPanel(int type){
        //�Է¹��� ������ ���� �г��� �����Ѵ�.
        switch (type){
            case BEGINNING_PANEL:
                setContentPane(beginningPanel);
                break;
            case RULE_PANEL:
                setContentPane(rulePanel);
                break;
            case SELECT_PANEL:
                setContentPane(selectPanel);
                break;
            case EDIT_PANEL:
                setContentPane(wordEditPanel);
                break;
            case RANKING_PANEL:
            	setContentPane(rankingPanel);
                break;
        }
    }
}