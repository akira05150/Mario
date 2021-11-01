package Game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

//��L���U��ť��
public class KeyListener extends KeyAdapter{
	public GameFrame gf;
	public boolean jumpFlag=true;
	public KeyListener(GameFrame gf) 
	{
		this.gf=gf;
	}
	
//��L��ť
public void keyPressed(KeyEvent e) 
{
	int code = e.getKeyCode();
	switch(code)
	{
		//�V�k���A�k�b
		case 39:
			gf.mario.right=true;
			break;
		//�V�����A���b
		case 37:
			gf.mario.left=true;
			break;
		//�g�l�u�Ashift
		case 16:
			addBoom();
			break;
		//�V�W���A�W�b
		case 38:
			gf.mario.up=true;
			break;
	}
}

//�s�W�l�u
public void addBoom() 
{	
	Boom b = new Boom(gf.mario.x+30,gf.mario.y+10,10,10);
	if(gf.mario.left)	b.speed=-2;
	if(gf.mario.right)	b.speed=2;
	gf.boomList.add(b);
}

//��L�����ť
public void keyReleased(KeyEvent e) 
{
	int code=e.getKeyCode();
	if(code==39)
	{
		gf.mario.right=false;
		gf.mario.img=new ImageIcon("image/mario.png").getImage();
	}
	if(code==37)
	{
		gf.mario.left=false;
		gf.mario.img=new ImageIcon("image/mario.png").getImage();
	}
	if(code==38)
	{
		gf.mario.up=false;
	}
}


}