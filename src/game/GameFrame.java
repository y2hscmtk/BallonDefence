package game;

import java.awt.Dimension;

import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;

//여러 패널을 돌려가며 사용자에게 게임을 조작할수 있는 프레임을 제공
//실질적인 조작은 패널에서 시행함
public class GameFrame extends JFrame {
	//사용자의 버튼 클릭에 따라 패널을 갈아끼며 다양한 메뉴를 볼 수 있도록
    public static final int BEGINNING_PANEL = 0; //시작 패널
    public static final int SELECT_PANEL = 1; //게임 패널
    public static final int RULE_PANEL = 2; //룰 설명 패널
    public static final int EDIT_PANEL = 3; //룰 설명 패널
    public static final int RANKING_PANEL = 4; //랭킹 보기 패널
    
    //swapPanel함수를 통해 패널을 갈아낄수 있도록 하기 위함
    //생성은 GameFrame의 생성자가 불려진 이후
    private SelectPanel selectPanel;
    private BeginningPanel beginningPanel;
    private WordEditPanel wordEditPanel;
    private RankingPanel rankingPanel;
    private RulePanel rulePanel;
    private GamePanel gamePanel;
    
    private TreeMap<String,Integer> playerMap = new TreeMap<String,Integer>();
    
    private boolean flag = false;
    
    
    public GamePanel getGamePanel() {
    	return gamePanel;
    }
    
    
    //재클릭 활성화
    public void setFlag() {
    	flag = true;
    }
    
    private Music music;
    
    private boolean musicOn = true; //현재 음악이 재생중인지 여부
    
    //현재 음악이 나오고 있는지 확인
    public boolean isMusicOn() {
    	return musicOn;
    }
    
    //현재 음악 재생상태를 설정
    public void setMusicOnOff(boolean onOff) {
    	musicOn = onOff;
    }
    
    
    public Music getMusic() {
    	return music;
    }
    
    
    public GameFrame() {
    	setTitle("BallonDefense");
    	
    	
    	try {
			file2Map();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	//패널 생성 => 자기 자신을 생성자로 넘겨줘서 부모를 설정해줌
    	selectPanel = new SelectPanel(this);
        beginningPanel = new BeginningPanel(this);
        wordEditPanel = new WordEditPanel(this);
        rankingPanel = new RankingPanel(this);
        rulePanel = new RulePanel(this);
        
        setSize(1500, 900);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //x버튼을 눌러 프로그램을 종료하도록
        //프레임이 생성될 위치를 지정(게임 화면 위치)
        Dimension frameSize = this.getSize();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // (모니터화면 가로 - 프레임화면 가로) / 2, (모니터화면 세로 - 프레임화면 세로) / 2
        this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

        this.setResizable(false); //크기 조절 불가능하게
        setContentPane(beginningPanel); //컨텐트펜을 시작패널로 설정
        
        music = new Music("openningMusic.wav"); //시작화면 음악 삽입
        music.loadAndStartMusic();
//        musicThread.start();
        
        setVisible(true);
    }
    
   
    //음악은 프레임에서 관리
    //음악을 관리하는 클래스 작성
    //음악 설정, 음악 변경,등
    public class Music {
    	private String path; //음악파일의 경로를 저장받음
    	private Clip clip; //클립을 필드로 생성
    	
    	public Music(String path) {
    		this.path = path;
    	}
    	
    	//음악 실행
    	public void loadAndStartMusic() {
    		loadAudio(path);
    		startMusic();
    	}
    	
    	//음악 변경
    	public void changeMusic(String path) {
    		this.path = path; //음악 경로 변
    		loadAudio(path);
    		startMusic();
    	}
    	
    	//로드되어있는 오디오를 실행시킨다.
    	public void startMusic() {
    		clip.start();
    	}
    	
    	
    	public void loadAudio(String pathName) {
    		try{
        		clip = AudioSystem.getClip();
        		File audioFile = new File(pathName);
        		AudioInputStream ais = AudioSystem.getAudioInputStream(audioFile);
	            clip.open(ais);
	        }catch (Exception ex){
	        	System.out.println("불러오기 오류");
    	    }
    	}
    	
    	
    	//중지된 이후부터 음악을 시작
    	public void resumeMusic() {
    		clip.start();
    	}
    	
    	public void musicReStart() {
    		clip.setFramePosition(0); //재생 위치를 처음으로 옮김
    		clip.start(); //처음부터 다시 시작
    	}
    	
    	//음악 종료
    	public void musicStop() {
    		clip.stop(); //오디오 재생 중단
    	}
  
  	
//    	@Override
//    	public void run() {
//    		try{
//        		AudioInputStream ais = AudioSystem.getAudioInputStream(new File(path));
//        		clip = AudioSystem.getClip();
//	            clip.open(ais);
//	            clip.start();
//	            clip.loop(Clip.LOOP_CONTINUOUSLY);
//	        }catch (Exception ex){
//	        	System.out.println("불러오기 오류");
//    	    }
//    	}
    	
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
    
    
    
    
    
    //사용자의 버튼 클릭에 따라 컨텐트펜을 다르게 붙여가며 화면을 변화시키는 메소드
    public void swapPanel(int type){
        //입력받은 변수에 맞춰 패널을 변경한다.
        switch (type){
            case BEGINNING_PANEL:
                setContentPane(beginningPanel);
                break;
            case RULE_PANEL:
//            	rulePanel.initRuleImage();
                setContentPane(rulePanel);
                break;
            case SELECT_PANEL:
                setContentPane(selectPanel);
                break;
            case EDIT_PANEL:
                setContentPane(wordEditPanel);
                break;
            case RANKING_PANEL:
            	if(flag) //다시 업데이트 하는경우
            		rankingPanel.initShow(); //화면에 현재 보여지고 있던 내용을 초기화
            	//파일 내용 업데이트
            	try {
        			file2Map();
        		} catch (IOException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
            	
            	rankingPanel.uploadMap(playerMap); //데이터 복사
            	//탑텐리스트 라벨 생성
        		rankingPanel.makeLabelList();
            	rankingPanel.showTopTen();
            	
            	setContentPane(rankingPanel);
                break;
        }
    }

	
}