package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

//�ܾ� ���� �г�
public class WordEditPanel extends JPanel{
	private GameFrame parent;//�θ� ����
	private ImageIcon homeButtonIcon = new ImageIcon("home.png");
	private ImageIcon homeButtonEnteredIcon = new ImageIcon("homeEntered.png");
	
	private ImageIcon backgroundIcon = new ImageIcon("wordEditPanelImage.png");
	private Image backgroundImage = backgroundIcon.getImage();
	
	private Vector<String> wordVector = new Vector<String>();
	
	private JList<String> scrollList; 
	private JScrollPane scrollPane;
	
	//��ư ������

	private	ImageIcon inputButtonIcon = new ImageIcon("inputButton.png");
	private	ImageIcon inputButtonEnterdIcon = new ImageIcon("inputButtonEntered.png");
	
	
	
//	 //��� �̹���
//    private ImageIcon bgImageicon = new ImageIcon("background.png");
//    private Image backgroundPanelImage = bgImageicon.getImage();
//	
    
    //�Է¹�ư �������� �̺�Ʈ �������̵�
    private class InputButtonClickedEvent extends ButtonClickedEvent{
    	private JTextField tf;
    	
    	
		public InputButtonClickedEvent(JTextField tf,GameFrame parent, ImageIcon enteredIcon, ImageIcon presentIcon) {
			super(parent, enteredIcon, presentIcon);
			this.tf = tf; //�ؽ�Ʈ ������ ���� ���� ��������
			// TODO Auto-generated constructor stub
		}
		
		
		@Override //���콺 Ŭ���̺�Ʈ
		public void mouseClicked(MouseEvent e) {
			//�ܾ� ����
			addWord(tf.getText()); //�ش� �ܾ �����Ѵ�
			tf.setText(""); //������ �����.
		}
    	
    	
    }
    
    
	
	public WordEditPanel (GameFrame parent) {
        this.parent = parent;//�θ� �Է¹޾� ������ ����
        setLayout(null); //��ġ ������ ����
        setBackground(Color.green);
        setSize(1500,900);

      //�ڷΰ��� ��ư => �ٽ� 4���� �޴� â���� �ǵ��ư���.
        //Ȩ ��ư
  		JLabel homeButton = new JLabel(homeButtonIcon);
  		homeButton.setSize(homeButtonIcon.getIconWidth(),homeButtonIcon.getIconHeight());
  		homeButton.setLocation(1360,20);
  		homeButton.addMouseListener(new ButtonClickedEvent(parent,parent.BEGINNING_PANEL,homeButtonEnteredIcon,homeButtonIcon));
  		add(homeButton);
        
        //words.txt���� �о�ͼ� �迭 ����
  		Scanner scanner;
  		try {
  			scanner = new Scanner(new FileReader("words.txt"));
  			while(scanner.hasNext()) {
  				//�� ���ξ� �о���� => words.txt�� �� ���ο� �� �ܾ� �� ������
  				wordVector.add(scanner.nextLine()); 
  			}
  			scanner.close(); //��ĳ�� �ʿ������
  		} catch (FileNotFoundException e) {
  			// TODO Auto-generated catch block
  			//������ ������쿡 ���� ó�� ����
  			e.printStackTrace();
  		}
  		
  		//�ܾ �Է¹ޱ� ���� �ؽ�Ʈ �ڽ��� �ܾ �Է��ϱ� ���� ��ư ����
  		JTextField tf = new JTextField(10);
  		tf.setFont(new Font("Gothic",Font.BOLD,30));
  		tf.setSize(550,50);
  		tf.setLocation(380,230);
  		add(tf);
  		
  		
  		//�Է¹�ư ĭ
  		InputButtonClickedEvent inputButtonClickedEvent = new InputButtonClickedEvent(tf,parent,inputButtonEnterdIcon,inputButtonIcon);
  		
  		
  		JLabel inputButton = new JLabel(inputButtonIcon);
  		inputButton.setSize(inputButtonIcon.getIconWidth(),inputButtonIcon.getIconHeight());
  		inputButton.setLocation(950,210);
  		inputButton.addMouseListener(inputButtonClickedEvent); //�̺�Ʈ �ޱ�
  		add(inputButton);
  		
  		
  		//���� ����� �ܾ���� ��ũ���� ���� Ȯ���Ҽ� �ֵ����ϴ� â ����
  		scrollList = new JList<String>(wordVector);
  		scrollPane = new JScrollPane(scrollList);
  		
  		scrollPane.setSize(700,400);
  		scrollPane.setLocation(380,315);
  		add(scrollPane);
  		
        
        setVisible(true);

    }
	
	

	//�ܾ �Ű������� �޾Ƽ� words.txt�� ����
	public void addWord(String word) {
		System.out.println(word +"������");
		try{
            File file = new File("words.txt");
            if (!file.exists()) //������ ���ٸ� ����
                file.createNewFile();
            FileWriter fw = new FileWriter(file,true); //���� ���Ͽ� �̾��
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write("\n"+word); //�����ٿ� �ܾ� ���̱�
            writer.close();
        }catch(IOException e){
        	 e.printStackTrace();
        }
	}
	
	
	 @Override
	    public void paintComponent(Graphics g) {
	       super.paintComponent(g); //�׷��� ������Ʈ ����
	       //��� �̹���
	       g.drawImage(backgroundImage, 0, 0,  backgroundIcon.getIconWidth(), backgroundIcon.getIconHeight(),null); //�̹����� �׷����� ���� �˸����� �ʱ�
	    }
	    
}