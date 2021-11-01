package Util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

//�a�ϰt�m��
public class Map {
	
	//��Ʈe��
	public ArrayList<String> list = new ArrayList<String>();
	public int [][] map=null;
	
	public  int[][] readMap() throws Exception 
	{
		// �c�y�ɮ׿�J�y
		FileInputStream fis = new FileInputStream("map.txt");
		InputStreamReader isr = new InputStreamReader(fis);
		BufferedReader br = new BufferedReader(isr);
		
		//����Ū���@����
		String value = br.readLine();
		
		//�NŪ���쪺�@���ƥ[�J��e����
		while(value!=null)
		{
			list.add(value);
			value = br.readLine();
		}
		br.close();
		
		//�o��h�֦�h�֦C
		int row=list.size();
		int cloumn=0;
		for (int i = 0; i < 1; i++)
		{
			String str=list.get(i);
			String [] values = str.split(",");
			cloumn = values.length;
		}
		map = new int [row][cloumn];
		
		//�NŪ�쪺�r�����ഫ����ơA�ý�ȵ��G��}�Cmap
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
