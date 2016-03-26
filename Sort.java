package com.qing.java8;

import java.util.Random;

public class Sort {

	public static void main(String[] args) {
		Sort main = new Sort();
		main.run();
	}
	
	void run() {
		// 构造待排序数组
		int length = 100;
		int sortArray[] = new int[length];
		Random r = new Random(System.currentTimeMillis());
		for(int i=0; i<length; i++) {
			sortArray[i] = r.nextInt(100);
		}
		printArray(sortArray);
		
		insertionSort(sortArray);
	}
	
	/**
	 * 插入排序
	 * @param sortArray
	 */
	void insertionSort(int sortArray[]) {
		int array[] = new int[sortArray.length];
		
		for(int i=0; i<sortArray.length; i++) {
			
			int pos = i;
			for(int k=i-1; k>=0; k--) {
				if(array[k] > sortArray[i]) {
					array[k+1] = array[k];
					pos = k;
				}
			}
			array[pos] = sortArray[i];
		}
		printArray(array);
	}
	
	
	
	void printArray(int array[]) {
		System.out.println();
		for(int i=0; i<array.length; i++) {
			System.out.print(array[i] + ", ");
		}
	}
}
