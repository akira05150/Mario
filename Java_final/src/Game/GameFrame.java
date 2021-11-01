package Game;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.util.ArrayList;

import Util.AePlayWave;
import Util.Map;

public class GameFrame extends JFrame{
	
	public Mario mario;
	public Enery pipe,coin,brick,end;
	//�I���Ϥ�
	public BackgroundImage bg;
	//�e���˼ĤH
	public ArrayList<Enery> eneryList = new ArrayList<Enery>();
	//�l�u�e��
	public ArrayList<Boom> boomList = new ArrayList<Boom>();
	//�l�u���t��
	public int bspeed=0;
	
	//�e�a�ϡA��w�W�h�A�O1�e�j�Y�A�O2�e�����A�O3�e����
	public int [][] map = null;
	
	public int score=0;
	public Label lab;
	
	public AePlayWave apw;
	
	//�غc�禡�̭���l�ƭI���Ϥ��M����������
	public GameFrame() throws Exception {
		mario = new Mario(this);
		mario.start();
		Map mp= new Map();
		bg = new BackgroundImage();
		
		//���魫ø�����
		new Thread()
		{
			public void run()
			{
				while(true)
				{
					//��ø����
					repaint();
					//�ˬd�l�u�O�_�X��
					checkBoom();
					try 
					{
						Thread.sleep(10);
					} 
					catch (InterruptedException e) 
					{
						e.printStackTrace();
					}
				}
			}
			
		}.start();
		
		map=mp.readMap();
		//Ū���a�ϡA�ðt�m�a��
		for (int i = 0; i < map.length; i++)
		{
			for (int j = 0; j < map[0].length; j++) 
			{
				//Ū���쪺�O1�A�e�j�Y
				if(map[i][j]==1)
				{
					brick = new Brick(j*30,i*30,30,30,new ImageIcon("image/brick.jpg").getImage());
					eneryList.add(brick);
				}
				//Ū��2�e����
				if(map[i][j]==2)
				{
					coin = new Coin(j*30,i*30,30,30,new ImageIcon("image/coin.png").getImage());
					eneryList.add(coin);
				}
				//Ū��3�e����
				if(map[i][j]==3)
				{
					pipe = new Pipe(j*30,i*30,60,120,new ImageIcon("image/pipe.png").getImage());
					eneryList.add(pipe);
				}
				//Ū��4�e���I
				if(map[i][j]==4)
				{
					end = new End(j*30,i*30,30,30,new ImageIcon("image/end.jpg").getImage());
					eneryList.add(end);
				}
			}
		}
	
		//�]�w�I������
		//com.huaxin.music.Util.startMusic("music/bg1.wav");
	}
	
	
	public void initFrame(){
		//�]�w��������ݩ�
		
		lab = new Label();
		lab.setText("score:"+score+" ");
		lab.setBackground(Color.black);
		lab.setForeground(Color.white);
		lab.setAlignment(Label.RIGHT);
		lab.setFont(new Font("Arial",Font.BOLD,25));
		lab.setLocation(0,0);
		lab.setSize(800,50);
		this.setLayout(null);
		this.add(lab);
		this.setSize(800,450);
		this.setTitle("Mario");
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(3);
		this.setVisible(true);
		//�ӵ���s�W��L��ť
		KeyListener kl = new KeyListener(this);
		this.addKeyListener(kl);
		
		apw=new AePlayWave("Super Mario Bros.wav"); 
		apw.start();
	}
	
	public void paint(Graphics g) 
	{
		//�Q�����w�ĵe�I���Ϥ��M������
		BufferedImage bi = (BufferedImage)this.createImage(this.getSize().width,this.getSize().height);
		Graphics big = bi.getGraphics();
		big.drawImage(bg.img, bg.x, bg.y, null);
		for (int i = 0; i <eneryList.size(); i++) 
		{
			Enery e = eneryList.get(i);
			big.drawImage(e.img, e.x, e.y, e.width, e.height,null);
		}
		
		//�e�l�u
		for (int i = 0; i < boomList.size(); i++) 
		{
			Boom b = boomList.get(i);
			Color c = big.getColor();
			big.setColor(Color.red);
			big.fillOval(b.x , b.y, b.width, b.width);
			big.setColor(c);
		}
		
		//�e�H��
		big.drawImage(mario.img, mario.x, mario.y, mario.width, mario.height,null);
		g.drawImage(bi,0,0,null);
		
	}
	
	//�ˬd�l�u�O�_�X�ɡA�X�ɫh�q�e���������A���������ܡA�O����|���|
	public boolean checkBoom()
	{
		boolean flag=true;
		for (int i = 0; i < boomList.size(); i++)
		{
			Boom b = boomList.get(i);
			if(b.x<0 || b.x>800)
			{
				boomList.remove(i);
				flag=false;
			}
		}
		return flag;
	}
	
}