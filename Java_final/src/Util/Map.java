package Util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

//地圖配置類
public class Map {
	
	//資料容器
	public ArrayList<String> list = new ArrayList<String>();
	public int [][] map=null;
	
	public  int[][] readMap() throws Exception 
	{
		// 構造檔案輸入流
		FileInputStream fis = new FileInputStream("map.txt");
		InputStreamReader isr = new InputStreamReader(fis);
		BufferedReader br = new BufferedReader(isr);
		
		//直接讀取一行資料
		String value = br.readLine();
		
		//將讀取到的一行資料加入到容器中
		while(value!=null)
		{
			list.add(value);
			value = br.readLine();
		}
		br.close();
		
		//得到多少行多少列
		int row=list.size();
		int cloumn=0;
		for (int i = 0; i < 1; i++)
		{
			String str=list.get(i);
			String [] values = str.split(",");
			cloumn = values.length;
		}
		map = new int [row][cloumn];
		
		//將讀到的字元創轉換成整數，並賦值給二位陣列map
		for (int i = 0; i < list.size(); i++)
		{
			String str=list.get(i);
			String [] values=str.split(",");
			for (int j = 0; j < values.length; j++) 
			{
				map[i][j]=Integer.parseInt(values[j]);
			}
		}
		return map;
	}

}
