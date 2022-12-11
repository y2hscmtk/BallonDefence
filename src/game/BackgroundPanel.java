package game;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Graphics;
import java.awt.Image;

//배경 넣어주는 클래스
public class BackgroundPanel extends JLabel {
    private String path;
    private Image image;
    public BackgroundPanel(String path){
        setImage(path);
        setVisible(true);
    }
    public void setImage(String path){
        this.path = path;
        try{
        	//상대경로에서 이미지 불러옴
            image = new ImageIcon(this.getClass().getClassLoader().getResource(path)).getImage();
            repaint();
        }
        catch (Exception e){
            System.out.println("Failed to import image.");
            e.printStackTrace();
        }
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(image,0,0,getWidth(),getHeight(),null);
    }
}
