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
	private GameFrame parent;//부모를 변수로 저장
	private ImageIcon homeButtonIcon = new ImageIcon("home.png");
	private ImageIcon homeButtonEnteredIcon = new ImageIcon("homeEntered.png");
	
	private ImageIcon icon = new ImageIcon("topTenBackgroundImage.png");
	private Image backgroundImage = icon.getImage();
	
	//파일에서 플레이어 정보를 읽어와서 점수와 이름으로 저장한다.
	//점수를 키값으로 하여 이름 저장 => 자동으로 정렬을 하기 위해 TreeMap으로 작성
	private TreeMap<String,Integer> playerMap = new TreeMap<String,Integer>();
	
	private static ScoreLabel[] topTenPlayerList = new ScoreLabel[10]; //탑10 플레이어를 저장할 레이블 배열
	
	public RankingPanel(GameFrame parent){
		this.parent = parent;//받은 부모를 전역변수로 저장한다
		setLayout(null); //배치 관리자 제거
		this.setBackground(Color.CYAN);
		setSize(1500,900);
		
		//뒤로가기 버튼 => 다시 4가지 메뉴 창으로 되돌아간다.
        //홈 버튼
  		JLabel homeButton = new JLabel(homeButtonIcon);
  		homeButton.setSize(homeButtonIcon.getIconWidth(),homeButtonIcon.getIconHeight());
  		homeButton.setLocation(1360,20);
  		homeButton.addMouseListener(new ButtonClickedEvent(parent,parent.BEGINNING_PANEL,homeButtonEnteredIcon,homeButtonIcon));
  		add(homeButton);
		
//		//점수 파일 읽어오기
//		try {
//			file2Map(); //지난 플레이어 정보를 읽어와서 맵에작성
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		//탑텐리스트 라벨 생성
//		makeLabelList();
//		
////		//생성한 라벨들을 붙이기
////		
//		showTopTen();
		
		setVisible(true);
	}
	
	
	//상위 10명에 대한 정보를 현재 패널의 적절한 위치에 부착한다.
	public void showTopTen() {
		
		for(int i=0;i<topTenPlayerList.length;i++) {
			
			//사이즈 지정
			
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
		parent.setFlag(); //재클릭여부를 알림
	}
	
	//화면에 보여지고 있는 랭킹순위들을 초기화한다.
	public void initShow() {
		for(int i=0;i<topTenPlayerList.length;i++) {
			
			//제거
			remove(topTenPlayerList[i]);
		}
	}
	

	//파일에서 정보를 읽어 맵에 저장하는 메소드 => 한글 잘 읽어옴
	public void file2Map() throws IOException {
		
		// TODO Auto-generated method stub
		 
        FileInputStream input=new FileInputStream("Score.txt");
        InputStreamReader reader=new InputStreamReader(input,"utf-8");
        BufferedReader in=new BufferedReader(reader);
        
        //BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file),"euc-kr"));
        // 한글 깨짐 현상 해결

        int ch;
        //String test = in.readLine();
        String token;
        while((token=in.readLine())!=null) { //한줄씩 읽어와서 맵에 삽입
        	StringTokenizer st= new StringTokenizer(token,"&");
        	int score = Integer.parseInt(st.nextToken());
        	String name = st.nextToken();
        	
        	//점수를 키값으로 하여 이름 저장
        	playerMap.put(name,score); //맵에 플레이어 정보 삽입
        
        }
        
        System.out.println(playerMap.size());
        in.close(); 
        //파일을 닫아줌 
	}
	
	//맵의 데이터를 추출하여 상위10명에 대한 라벨 리스트 생성
	public void makeLabelList() {
	
		//오름차순 정렬하는 부분
		List<String> keySet = new ArrayList<>(playerMap.keySet());
		int index = 0;

        //점수를 기준으로 내림차순 정렬(상위 10명에 사용)
        keySet.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return playerMap.get(o2).compareTo(playerMap.get(o1));
            }
        });

        for (String key : keySet) {
        	if(index>9)
        		break; //10개만 생성해야하므로
        	topTenPlayerList[index] = new ScoreLabel(key,playerMap.get(key));
        	System.out.print(index + "번 ");
            System.out.print("Key : " + key);
            System.out.println(", Val : " + playerMap.get(key));
            index++;
        }
	}
	
	
	public void uploadMap(TreeMap<String,Integer> playerMap) {
		System.out.println("데이터 복사중");
		this.playerMap = playerMap;
	}
	
	//점수를 보여주기 위한 라벨
	private class ScoreLabel extends JLabel{
		private int score;
		private String name;
		
		public String getName() {
			return this.name;
		}
		
		public int getScrore() {
			return this.score;
		}
		
		
		//생성자로 이름과 점수 입력받아 라벨 생성
		public ScoreLabel(String name,int score) {
			setLayout(null);
			this.name = name;
			this.score = score;
			
			//플레이어 라벨 생성
			JLabel playerLabel = new JLabel(name+" "+Integer.toString(score));
			playerLabel.setForeground(Color.WHITE);
			playerLabel.setFont(new Font("Gothic",Font.BOLD,40));
			playerLabel.setSize(400,100);
			playerLabel.setLocation(0,0); //생성된 라벨의 왼쪽 부분에서 생성
			add(playerLabel);
			
//			//이름 라벨 생성
//			JLabel nameLabel = new JLabel(name);
//			nameLabel.setSize(100,100);
//			nameLabel.setLocation(0,0); //생성된 라벨의 왼쪽 부분에서 생성
//			add(nameLabel);
//			
//			//점수 라벨 생성
//			JLabel scoreLabel = new JLabel(Integer.toString(score));
//			scoreLabel.setSize(100,100);
//			scoreLabel.setLocation(40,0); //생성된 라벨의 오른쪽 부분에서 생성
//			add(scoreLabel);
//			
		}
		
	}
	
	
	
	//배경 이미지 그리기
	@Override
    public void paintComponent(Graphics g) {
       super.paintComponent(g); //그래픽 컴포넌트 설정
       //배경 이미지
       g.drawImage(backgroundImage, 0, 0, icon.getIconWidth() ,icon.getIconHeight() ,null); //이미지가 그려지는 시점 알림받지 않기
    }
}