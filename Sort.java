package com.qing.java8;

import java.util.Random;

public class Sort {

	public static void main(String[] args) {
		Sort main = new Sort();
		main.run();
	}
	
	void run() {
		// 构造待排序数组
		int length = 10;
		int sortArray[] = new int[length];
		Random r = new Random(System.currentTimeMillis());
		for(int i=0; i<length; i++) {
			sortArray[i] = r.nextInt(100);
		}
		printArray(sortArray);
		
//		insertionSort(sortArray);
		quickSort(sortArray, 0, sortArray.length-1);
		printArray(sortArray);
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
	
	/**
	 * 快速排序
	 * @param sortArray
	 * @param start
	 * @param end
	 */
	void quickSort(int sortArray[], int start, int end) {
		if(start >= end)
			return;
		
		int s = start;
		int e = end;
		
		int m = sortArray[end];
		
		while(start < end) {
			// 相等的条件也要加上，避免数组中有相等的两个值的时候产生死循环 
			// 32, 58, 31, 31, 2, 34, 23, 82, 69, 
			// 31, 31会一直交换，但是start和end的值不改变
			while(start < end && sortArray[start] <= m) {
				start ++;
			}
			if(start < end) {
				sortArray[end] = sortArray[start];
				sortArray[start] = m;
			}
			
			while(start < end && sortArray[end] >= m) {
				end --;
			}
			if(start < end) {
				sortArray[start] = sortArray[end];
				sortArray[end] = m;
			}
		}
		
		quickSort(sortArray, s, start-1);
		quickSort(sortArray, end+1, e);
	}
	
	void printArray(int array[]) {
		System.out.println();
		for(int i=0; i<array.length; i++) {
			System.out.print(array[i] + ", ");
		}
	}
}
