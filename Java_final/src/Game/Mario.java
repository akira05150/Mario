package Game;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
//import com.huaxin.enery.Enery;

import Util.AePlayWave;

//�ۤv��������
public class Mario extends Thread{
	
	public GameFrame gf;
	public boolean jumpFlag=true;
	//���������y��
	public int x=50,y=385;
	//���������t��
	public int xspeed=5,yspeed=1;
	//���������e��
	public int width=30,height=30;
	//���������Ϥ�
	public Image img = new ImageIcon("image/mario.png").getImage();
	public boolean left=false,right=false,down=false,up=false;
	public String Dir_Up="Up",Dir_Left="Left",Dir_Right="Right",Dir_Down="Down";
	
	//�غc��
	public Mario (GameFrame gf) 
	{
		this.gf=gf;
		this.Gravity();
	}
	
	public void run()
	{
		while(true)
		{
			//�V����
			if(left)
			{
				//�I����F
				if(hit(Dir_Left))	this.xspeed=0;
				if(this.x>=0)
				{
					this.x -= this.xspeed;
					this.img=new ImageIcon("image/mario.png").getImage();
				}
				this.xspeed=5;
			}
			
			//�V�k��
			if(right)
			{
				if(hit(Dir_Right))	this.xspeed=0;
				//���H���V�k����
				if(this.x<400)
				{
					this.x += this.xspeed;
					this.img=new ImageIcon("image/mario.png").getImage();
				}
				if(this.x>=400)
				{
					//�I���V������
					gf.bg.x -= this.xspeed;
					//��ê����������
					for (int i = 0; i <gf.eneryList.size(); i++)
					{
						Enery enery = gf.eneryList.get(i);
						enery.x -= this.xspeed;
					}
					this.img=new ImageIcon("image/mario.png").getImage();
				}
				this.xspeed=5;
			}
			
			//�V�W��
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
	
	//�V�W�����禡
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
		this.yspeed=1;//�٭�t��
	}
	
	//�˴��I��
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
				//+2+1�ΨӸѨM�I��j���L�k���ʪ����D(�קK�d�b��ê����)
			}
			else if(dir.equals("Right"))
			{
				rect = new Rectangle(enery.x-2,enery.y,enery.width,enery.height);
			}
			else if(dir.equals("Up"))
			{
				rect = new Rectangle(enery.x,enery.y+1,enery.width,enery.height);
				//�ѨM���k�W���ɡA�d�b��ê�������D
			}
			else if(dir.equals("Down"))
			{
				rect = new Rectangle(enery.x,enery.y-2,enery.width,enery.height);
			}
			//�I���˴�
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
	
	//�ˬd�O�_�K�a
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
						sleep(5);//�Τ@�U���t��
					} 
					catch (InterruptedException e) 
					{
						e.printStackTrace();
					}
					//if(!jumpFlag){}//���_�ɤ��ˬd�K�a
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
							//�ѨM���ܤ���W�A�A���U�ܥt�@����L�k�P�O�K�a�ӵL�k���D�����D
						}
						//if(y>=390)	isGravity=false; //�����O�ڥi�H�����ܵ����~
						else
						{
							isGravity=true;
							y += yspeed;	//�ѨM���ܤ���W�A���U�ɤH���o���������D
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