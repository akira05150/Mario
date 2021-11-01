package Game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

//鍵盤按下監聽類
public class KeyListener extends KeyAdapter{
	public GameFrame gf;
	public boolean jumpFlag=true;
	public KeyListener(GameFrame gf) 
	{
		this.gf=gf;
	}
	
//鍵盤監聽
public void keyPressed(KeyEvent e) 
{
	int code = e.getKeyCode();
	switch(code)
	{
		//向右走，右箭
		case 39:
			gf.mario.right=true;
			break;
		//向左走，左箭
		case 37:
			gf.mario.left=true;
			break;
		//射子彈，shift
		case 16:
			addBoom();
			break;
		//向上跳，上箭
		case 38:
			gf.mario.up=true;
			break;
	}
}

//新增子彈
public void addBoom() 
{	
	Boom b = new Boom(gf.mario.x+30,gf.mario.y+10,10,10);
	if(gf.mario.left)	b.speed=-2;
	if(gf.mario.right)	b.speed=2;
	gf.boomList.add(b);
}

//鍵盤釋放監聽
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