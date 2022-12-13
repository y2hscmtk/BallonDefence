package game;

import java.awt.Color;
import java.awt.Font;
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
	private ImageIcon homeButtonIcon = new ImageIcon("home.png");
	private ImageIcon homeButtonEnteredIcon = new ImageIcon("homeEntered.png");
	
	private ImageIcon icon = new ImageIcon("topTenBackgroundImage.png");
	private Image backgroundImage = icon.getImage();
	
	//���Ͽ��� �÷��̾� ������ �о�ͼ� ������ �̸����� �����Ѵ�.
	//������ Ű������ �Ͽ� �̸� ���� => �ڵ����� ������ �ϱ� ���� TreeMap���� �ۼ�
	private TreeMap<String,Integer> playerMap = new TreeMap<String,Integer>();
	
	private static ScoreLabel[] topTenPlayerList = new ScoreLabel[10]; //ž10 �÷��̾ ������ ���̺� �迭
	
	public RankingPanel(GameFrame parent){
		this.parent = parent;//���� �θ� ���������� �����Ѵ�
		setLayout(null); //��ġ ������ ����
		this.setBackground(Color.CYAN);
		setSize(1500,900);
		
		//�ڷΰ��� ��ư => �ٽ� 4���� �޴� â���� �ǵ��ư���.
        //Ȩ ��ư
  		JLabel homeButton = new JLabel(homeButtonIcon);
  		homeButton.setSize(homeButtonIcon.getIconWidth(),homeButtonIcon.getIconHeight());
  		homeButton.setLocation(1360,20);
  		homeButton.addMouseListener(new ButtonClickedEvent(parent,parent.BEGINNING_PANEL,homeButtonEnteredIcon,homeButtonIcon));
  		add(homeButton);
		
//		//���� ���� �о����
//		try {
//			file2Map(); //���� �÷��̾� ������ �о�ͼ� �ʿ��ۼ�
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		//ž�ٸ���Ʈ �� ����
//		makeLabelList();
//		
////		//������ �󺧵��� ���̱�
////		
//		showTopTen();
		
		setVisible(true);
	}
	
	
	//���� 10�� ���� ������ ���� �г��� ������ ��ġ�� �����Ѵ�.
	public void showTopTen() {
		
		for(int i=0;i<topTenPlayerList.length;i++) {
			
			//������ ����
			
			topTenPlayerList[i].setSize(400,280);
//			topTenPlayerList[i].setOpaque(true);
			topTenPlayerList[i].setBackground(Color.white);
			if(i>=5) {
				topTenPlayerList[i].setLocation(865,-327 + (i*110));
			}
			else {
				topTenPlayerList[i].setLocation(400,220+(i*110));
			}
			add(topTenPlayerList[i]);
			
		}
		parent.setFlag(); //��Ŭ�����θ� �˸�
	}
	
	//ȭ�鿡 �������� �ִ� ��ŷ�������� �ʱ�ȭ�Ѵ�.
	public void initShow() {
		for(int i=0;i<topTenPlayerList.length;i++) {
			
			//����
			remove(topTenPlayerList[i]);
		}
	}
	

	//���Ͽ��� ������ �о� �ʿ� �����ϴ� �޼ҵ� => �ѱ� �� �о��
	public void file2Map() throws IOException {
		
		// TODO Auto-generated method stub
		 
        FileInputStream input=new FileInputStream("Score.txt");
        InputStreamReader reader=new InputStreamReader(input,"utf-8");
        BufferedReader in=new BufferedReader(reader);
        
        //BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file),"euc-kr"));
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

        //������ �������� �������� ����(���� 10�� ���)
        keySet.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return playerMap.get(o2).compareTo(playerMap.get(o1));
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
	
	
	public void uploadMap(TreeMap<String,Integer> playerMap) {
		System.out.println("������ ������");
		this.playerMap = playerMap;
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
			setLayout(null);
			this.name = name;
			this.score = score;
			
			//�÷��̾� �� ����
			JLabel playerLabel = new JLabel(name+" "+Integer.toString(score));
			playerLabel.setForeground(Color.WHITE);
			playerLabel.setFont(new Font("Gothic",Font.BOLD,40));
			playerLabel.setSize(400,100);
			playerLabel.setLocation(0,0); //������ ���� ���� �κп��� ����
			add(playerLabel);
			
//			//�̸� �� ����
//			JLabel nameLabel = new JLabel(name);
//			nameLabel.setSize(100,100);
//			nameLabel.setLocation(0,0); //������ ���� ���� �κп��� ����
//			add(nameLabel);
//			
//			//���� �� ����
//			JLabel scoreLabel = new JLabel(Integer.toString(score));
//			scoreLabel.setSize(100,100);
//			scoreLabel.setLocation(40,0); //������ ���� ������ �κп��� ����
//			add(scoreLabel);
//			
		}
		
	}
	
	
	
	//��� �̹��� �׸���
	@Override
    public void paintComponent(Graphics g) {
       super.paintComponent(g); //�׷��� ������Ʈ ����
       //��� �̹���
       g.drawImage(backgroundImage, 0, 0, icon.getIconWidth() ,icon.getIconHeight() ,null); //�̹����� �׷����� ���� �˸����� �ʱ�
    }
}