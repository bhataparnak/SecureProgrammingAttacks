package com.aa.lookahead.one.common;

import java.util.ArrayList;
import java.util.List;

public class Sample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ArrayList<Integer> list = null;		
		for(int i = 0 ; i <12 ;i++)
		{
			list.add(i);
		}

		int[] strs = new int[10];
		for (int i = 0 ; i < strs.length+1 ; i++)
		{
			strs[i] = i+1;
		}
		
		System.out.println(list.size());
		System.out.println(strs[10]);

	}

}
