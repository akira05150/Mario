package Game;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
//import com.huaxin.enery.Enery;

import Util.AePlayWave;

//自己的角色類
public class Mario extends Thread{
	
	public GameFrame gf;
	public boolean jumpFlag=true;
	//馬里奧的座標
	public int x=50,y=385;
	//馬里奧的速度
	public int xspeed=5,yspeed=1;
	//馬里奧的寬高
	public int width=30,height=30;
	//馬里奧的圖片
	public Image img = new ImageIcon("image/mario.png").getImage();
	public boolean left=false,right=false,down=false,up=false;
	public String Dir_Up="Up",Dir_Left="Left",Dir_Right="Right",Dir_Down="Down";
	
	//建構元
	public Mario (GameFrame gf) 
	{
		this.gf=gf;
		this.Gravity();
	}
	
	public void run()
	{
		while(true)
		{
			//向左走
			if(left)
			{
				//碰撞到了
				if(hit(Dir_Left))	this.xspeed=0;
				if(this.x>=0)
				{
					this.x -= this.xspeed;
					this.img=new ImageIcon("image/mario.png").getImage();
				}
				this.xspeed=5;
			}
			
			//向右走
			if(right)
			{
				if(hit(Dir_Right))	this.xspeed=0;
				//任人物向右移動
				if(this.x<400)
				{
					this.x += this.xspeed;
					this.img=new ImageIcon("image/mario.png").getImage();
				}
				if(this.x>=400)
				{
					//背景向左移動
					gf.bg.x -= this.xspeed;
					//障礙物項左移動
					for (int i = 0; i <gf.eneryList.size(); i++)
					{
						Enery enery = gf.eneryList.get(i);
						enery.x -= this.xspeed;
					}
					this.img=new ImageIcon("image/mario.png").getImage();
				}
				this.xspeed=5;
			}
			
			//向上跳
			if(up)
			{
				if(jumpFlag && !isGravity)
				{
					jumpFlag=false;//190
					new Thread()
					{
						public void run()
						{
							jump();
							jumpFlag=true;
						}
					}.start();
				}
			}
			try 
			{
				Thread.sleep(20);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			
			if(this.y>450)
			{
				gf.apw.stop();
				AePlayWave gg=new AePlayWave("Game over.wav"); 
				gg.start();
				try
				{
				    Thread.sleep(3000);
				}
				catch (InterruptedException e)
				{
				    e.printStackTrace();
				}
				JOptionPane.showMessageDialog(null,"GAME OVER",
				"Game Over",JOptionPane.INFORMATION_MESSAGE);
				break;
			}
		}
	}
	
	//向上跳的函式
	public void jump()
	{
		int jumpHeight=0;
		for (int i = 0; i < 100 ; i++ ) 
		{
			gf.mario.y -= this.yspeed;
			jumpHeight += this.yspeed;
			if(hit(Dir_Up))
			{
				break;
			}
			try 
			{
				Thread.sleep(5);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
		
		
		for (int i = 0; i <jumpHeight; i++) 
		{
			gf.mario.y += this.yspeed;
			if(hit(Dir_Down))
			{
				this.yspeed=0;
				break;
			}
			try 
			{
				Thread.sleep(5);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		this.yspeed=1;//還原速度
	}
	
	//檢測碰撞
	public boolean hit(String dir)
	{
		Rectangle myrect = new Rectangle(this.x,this.y,this.width,this.height);
		Rectangle rect = null;
		/*try 
		{
			
		}
		catch(java.lang.NullPointerException e) {};*/
		for (int i = 0; i < gf.eneryList.size(); i++) 
		{
			Enery enery = gf.eneryList.get(i);
			if(dir.equals("Left"))
			{
				rect = new Rectangle(enery.x+2,enery.y,enery.width,enery.height);
				//+2+1用來解決碰到磚塊無法走動的問題(避免卡在障礙物中)
			}
			else if(dir.equals("Right"))
			{
				rect = new Rectangle(enery.x-2,enery.y,enery.width,enery.height);
			}
			else if(dir.equals("Up"))
			{
				rect = new Rectangle(enery.x,enery.y+1,enery.width,enery.height);
				//解決左右上跳時，卡在障礙物的問題
			}
			else if(dir.equals("Down"))
			{
				rect = new Rectangle(enery.x,enery.y-2,enery.width,enery.height);
			}
			//碰撞檢測
			if(myrect.intersects(rect))
			{
				if (enery instanceof End) 
				{
					gf.eneryList.remove(enery);
					
					gf.apw.stop();
					AePlayWave win=new AePlayWave("win.wav"); 
					win.start();
					try
					{
					    Thread.sleep(5000);
					}
					catch (InterruptedException e)
					{
					    e.printStackTrace();
					}
					
					JOptionPane.showMessageDialog(null,"YOU WIN!",
					"Congratulation",JOptionPane.INFORMATION_MESSAGE);
					break;
                }
				if (enery instanceof Coin)
				{
					gf.eneryList.remove(enery);
					gf.score += 200;
					gf.lab.setText("score:"+gf.score+" ");
					
					AePlayWave gc=new AePlayWave("get coin.wav"); 
					gc.start();
                }
				return true;
			}
			
		}
		return false;
	}
	
	//檢查是否貼地
	public boolean isGravity=false;
	public void Gravity()
	{
		new Thread()
		{
			public void run()
			{
				while(true)
				{
					try 
					{
						sleep(5);//統一下落速度
					} 
					catch (InterruptedException e) 
					{
						e.printStackTrace();
					}
					//if(!jumpFlag){}//跳起時不檢查貼地
					while(true)
					{
						if(!jumpFlag)
						{
							isGravity=false;
							break;
						}
						if(hit(Dir_Down))
						{
							isGravity=false;
							break;
							//解決跳至方塊上，再跳下至另一方塊無法判別貼地而無法跳躍之問題
						}
						//if(y>=390)	isGravity=false; //讓馬力歐可以掉落至視窗外
						else
						{
							isGravity=true;
							y += yspeed;	//解決跳至方塊上，跳下時人物卻消失的問題
						}
						try 
						{
							sleep(5);
						} 
						catch (InterruptedException e) 
						{
							e.printStackTrace();
						}
					}
				}
			}
		}.start();
	}
}