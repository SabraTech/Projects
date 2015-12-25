package eg.edu.alexu.csd.oop.game;


import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import eg.edu.alexu.csd.oop.game.listeners.EasyGame;
import eg.edu.alexu.csd.oop.game.listeners.HardGame;
import eg.edu.alexu.csd.oop.game.listeners.MediumGame;


import javax.swing.JLabel;
import java.awt.Font;


public class GameWindow {

  private JFrame frame;
  public static final Logger log = Logger.getLogger(GameWindow.class.getName());
  
  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          PropertyConfigurator.configure("log4j.properties");
          GameWindow window = new GameWindow();
          window.frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the application.
   */
  public GameWindow() {
    initialize();
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {
    frame = new JFrame();
    try {
      frame.setContentPane(new ImagePanel("res/background.jpg"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    frame.setSize(780, 580);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(null);
    frame.setLocationRelativeTo(null);
    frame.setResizable(false);
    frame.setTitle("Circus Of Plates");
    
    JButton easyGame = new JButton(new ImageIcon("res/ice.jpg"));
    easyGame.addActionListener(new EasyGame());
    easyGame.setBounds(295, 89, 191, 84);
    frame.getContentPane().add(easyGame);
    
    JButton mediumGame = new JButton(new ImageIcon("res/background.jpg"));
    mediumGame.addActionListener(new MediumGame());
    mediumGame.setBounds(295, 260, 191, 84);
    frame.getContentPane().add(mediumGame);
    
    JButton hardGame = new JButton(new ImageIcon("res/circus1.jpg"));
    hardGame.addActionListener(new HardGame());
    hardGame.setBounds(295, 425, 191, 84);
    frame.getContentPane().add(hardGame);
    
    JLabel lblEasy = new JLabel("Easy");
    lblEasy.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
    lblEasy.setBounds(355, 29, 61, 26);
    frame.getContentPane().add(lblEasy);
    
    JLabel lblMedium = new JLabel("Medium");
    lblMedium.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
    lblMedium.setBounds(338, 208, 78, 36);
    frame.getContentPane().add(lblMedium);
    
    JLabel lblHard = new JLabel("Hard");
    lblHard.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
    lblHard.setBounds(348, 388, 68, 26);
    frame.getContentPane().add(lblHard);
    
    
    
  }
  
  public class ImagePanel extends JPanel {
    
    /**
     * 
     */
    private static final long serialVersionUID = 7420939268401129196L;
    private Image bgImage;
    
    public ImagePanel(String fileName) throws IOException{
      bgImage = ImageIO.read(new File(fileName));
    }
    
    public void paintComponent(Graphics g){
      super.paintComponent(g);
      g.drawImage(bgImage,0,0,getWidth(), getHeight(),this);
    }
    
  }
}
