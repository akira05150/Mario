package Game;

public class Boom extends Thread{
	//子彈的座標，大小，速度
	int x,y;
	int width;
	int hight;
	int speed=5;
	public GameFrame gf;
	public Mario ma;
	public Boom(int x, int y, int width ,int hight)
	{
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.hight = hight;
		//if(ma.left)	speed=-2;
		//else speed=2;
		new Thread(this) {
			
		}.start();
	}
	
	public void run() {
        while (gf.checkBoom()) {
            try 
            {
				Thread.sleep(10);
			} 
            catch (InterruptedException e) 
            {
				e.printStackTrace();
			}
            x += speed;
        }
    }
}

    

    