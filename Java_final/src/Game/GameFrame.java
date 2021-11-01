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
	//背景圖片
	public BackgroundImage bg;
	//容器裝敵人
	public ArrayList<Enery> eneryList = new ArrayList<Enery>();
	//子彈容器
	public ArrayList<Boom> boomList = new ArrayList<Boom>();
	//子彈的速度
	public int bspeed=0;
	
	//畫地圖，制定規則，是1畫磚頭，是2畫金幣，是3畫水管
	public int [][] map = null;
	
	public int score=0;
	public Label lab;
	
	public AePlayWave apw;
	
	//建構函式裡面初始化背景圖片和馬里奧物件
	public GameFrame() throws Exception {
		mario = new Mario(this);
		mario.start();
		Map mp= new Map();
		bg = new BackgroundImage();
		
		//窗體重繪執行緒
		new Thread()
		{
			public void run()
			{
				while(true)
				{
					//重繪窗體
					repaint();
					//檢查子彈是否出界
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
		//讀取地圖，並配置地圖
		for (int i = 0; i < map.length; i++)
		{
			for (int j = 0; j < map[0].length; j++) 
			{
				//讀取到的是1，畫磚頭
				if(map[i][j]==1)
				{
					brick = new Brick(j*30,i*30,30,30,new ImageIcon("image/brick.jpg").getImage());
					eneryList.add(brick);
				}
				//讀到2畫金幣
				if(map[i][j]==2)
				{
					coin = new Coin(j*30,i*30,30,30,new ImageIcon("image/coin.png").getImage());
					eneryList.add(coin);
				}
				//讀到3畫水管
				if(map[i][j]==3)
				{
					pipe = new Pipe(j*30,i*30,60,120,new ImageIcon("image/pipe.png").getImage());
					eneryList.add(pipe);
				}
				//讀到4畫終點
				if(map[i][j]==4)
				{
					end = new End(j*30,i*30,30,30,new ImageIcon("image/end.jpg").getImage());
					eneryList.add(end);
				}
			}
		}
	
		//設定背景音樂
		//com.huaxin.music.Util.startMusic("music/bg1.wav");
	}
	
	
	public void initFrame(){
		//設定窗體相關屬性
		
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
		//該窗體新增鍵盤監聽
		KeyListener kl = new KeyListener(this);
		this.addKeyListener(kl);
		
		apw=new AePlayWave("Super Mario Bros.wav"); 
		apw.start();
	}
	
	public void paint(Graphics g) 
	{
		//利用雙緩衝畫背景圖片和馬里奧
		BufferedImage bi = (BufferedImage)this.createImage(this.getSize().width,this.getSize().height);
		Graphics big = bi.getGraphics();
		big.drawImage(bg.img, bg.x, bg.y, null);
		for (int i = 0; i <eneryList.size(); i++) 
		{
			Enery e = eneryList.get(i);
			big.drawImage(e.img, e.x, e.y, e.width, e.height,null);
		}
		
		//畫子彈
		for (int i = 0; i < boomList.size(); i++) 
		{
			Boom b = boomList.get(i);
			Color c = big.getColor();
			big.setColor(Color.red);
			big.fillOval(b.x , b.y, b.width, b.width);
			big.setColor(c);
		}
		
		//畫人物
		big.drawImage(mario.img, mario.x, mario.y, mario.width, mario.height,null);
		g.drawImage(bi,0,0,null);
		
	}
	
	//檢查子彈是否出界，出界則從容器中移除，不移除的話，記憶體會洩漏
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