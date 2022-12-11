package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RankingPanel extends JPanel {
	private GameFrame parent;//�θ� ������ ����
	private ImageIcon homeButtonIcon = new ImageIcon("back.png");
	private ImageIcon icon = new ImageIcon("rankingPanelImage.png");
	private Image backgroundImage = icon.getImage();
	
	//���Ͽ��� �÷��̾� ������ �о�ͼ� ������ �̸����� �����Ѵ�.
	//������ Ű������ �Ͽ� �̸� ���� => �ڵ����� ������ �ϱ� ���� TreeMap���� �ۼ�
	private TreeMap<String,Integer> playerMap = new TreeMap<String,Integer>();
	private Vector<String> playerVector = new Vector<String>();
	
	private static ScoreLabel[] topTenPlayerList = new ScoreLabel[10]; //ž10 �÷��̾ ������ ���̺� �迭
	
//	private int index = 0; //ž�ٸ���Ʈ�� �ε��� �ѹ�
	
//	private String[] playerList;
  
	public RankingPanel(GameFrame parent){
		this.parent = parent;//���� �θ� ���������� �����Ѵ�
		setLayout(null); //��ġ ������ ����
		this.setBackground(Color.CYAN);
		setSize(1500,900);
		
		//Ȩ ��ư
		JButton backButton = new JButton(homeButtonIcon);
		backButton.setSize(200,200);
		backButton.setLocation(1200,50);
		backButton.addMouseListener(new ButtonClickedEvent(parent,parent.BEGINNING_PANEL));
		add(backButton);
		
//		JButton show = new JButton(homeButtonIcon);
//		show.setSize(200,200);
//		show.setLocation(10,50);
//		show.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				getFile() //���� ��������
//			}
//		});
//		add(show);
		
		//���� ���� �о����
		try {
			file2Map(); //���� �÷��̾� ������ �о�ͼ� �ʿ��ۼ�
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//ž�ٸ���Ʈ �� ����
		makeLabelList();
		
//		//������ �󺧵��� ���̱�
//		
		showTopTen();
		
		setVisible(true);
	}
	
	
	public void showTopTen() {
		
		for(int i=0;i<topTenPlayerList.length;i++) {
			
			topTenPlayerList[i].setSize(80,80);
			topTenPlayerList[i].setOpaque(true);
			topTenPlayerList[i].setBackground(Color.white);
			if(i>=5) {
				topTenPlayerList[i].setLocation(900,-260 + (i*100));
			}
			else {
				topTenPlayerList[i].setLocation(400,240+(i*100));
			}
			add(topTenPlayerList[i]);
		}
	}
	

	//���Ͽ��� ������ �о� �ʿ� �����ϴ� �޼ҵ�
	public void file2Map() throws IOException {
		
		// TODO Auto-generated method stub
		 
        FileInputStream input=new FileInputStream("Score.txt");
        InputStreamReader reader=new InputStreamReader(input,"UTF-8");
        BufferedReader in=new BufferedReader(reader);
        // �ѱ� ���� ���� �ذ�

        int ch;
        //String test = in.readLine();
        String token;
        while((token=in.readLine())!=null) { //���پ� �о�ͼ� �ʿ� ����
        	StringTokenizer st= new StringTokenizer(token,"&");
        	int score = Integer.parseInt(st.nextToken());
        	String name = st.nextToken();
        	
        	//������ Ű������ �Ͽ� �̸� ����
        	playerMap.put(name,score); //�ʿ� �÷��̾� ���� ����
        
        }
        
        System.out.println(playerMap.size());
        in.close(); 
        //������ �ݾ��� 
	}
	
	//���� �����͸� �����Ͽ� ����10�� ���� �� ����Ʈ ����
	public void makeLabelList() {
	
		//�������� �����ϴ� �κ�
		List<String> keySet = new ArrayList<>(playerMap.keySet());
		int index = 0;

        //������ �������� �������� ����
        keySet.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return playerMap.get(o1).compareTo(playerMap.get(o2));
            }
        });

        for (String key : keySet) {
        	if(index>9)
        		break; //10���� �����ؾ��ϹǷ�
        	topTenPlayerList[index] = new ScoreLabel(key,playerMap.get(key));
        	
        	System.out.print(index + "�� ");
            System.out.print("Key : " + key);
            System.out.println(", Val : " + playerMap.get(key));
            index++;
        }
	}
	
	//������ �����ֱ� ���� ��
	private class ScoreLabel extends JLabel{
		private int score;
		private String name;
		
		public String getName() {
			return this.name;
		}
		
		public int getScrore() {
			return this.score;
		}
		
		
		//�����ڷ� �̸��� ���� �Է¹޾� �� ����
		public ScoreLabel(String name,int score) {
			this.name = name;
			this.score = score;
			
			//�̸� �� ����
			JLabel nameLabel = new JLabel(name);
			nameLabel.setSize(100,100);
			nameLabel.setLocation(0,0); //������ ���� ���� �κп��� ����
			add(nameLabel);
			
			//���� �� ����
			JLabel scoreLabel = new JLabel(Integer.toString(score));
			scoreLabel.setSize(100,100);
			scoreLabel.setLocation(40,0); //������ ���� ������ �κп��� ����
			add(scoreLabel);
			
		}
		
	}
	
	
	public void writeScoreFile() {
		
	}
	
	//��� �̹��� �׸���
	@Override
    public void paintComponent(Graphics g) {
       super.paintComponent(g); //�׷��� ������Ʈ ����
       //��� �̹���
       g.drawImage(backgroundImage, 0, 0, this.getWidth(),this.getHeight(),null); //�̹����� �׷����� ���� �˸����� �ʱ�
    }
}