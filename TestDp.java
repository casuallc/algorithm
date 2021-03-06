package com.qing.java8;

import java.util.ArrayList;
import java.util.List;

/**
 * DP -- 动态规划
 * 使用场景：
 * 	问题的求解可分解为多个子问题的求解
 * 	子问题的会被多次求解
 * 
 * @author liuchangqing
 * @function
 */
public class TestDp {

	
	public static void main(String[] args) {
		try {
			TestDp main = new TestDp();
			main.run();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	void run() throws Exception {
//		for(int k=1; k<15; k++) {
//			pipeLength = k;
//			splitPipeFromBottom();
//
//			int n = pipeLength;
//			while(true) {
//				if(n <= 0)
//					break;
//				System.out.println(pipeSplitLength[n]);
//				n = n - pipeSplitLength[n];
//			}
//		}
		
	}
	
	
	// 有效的分割铁管
	// 铁管总长度
	int pipeLength = 16;
	// 长度和价格的对照
	int pipePriceWithLength[] = {0, 1, 5, 8, 9, 10, 17, 17, 20, 24, 30};
	// 某个长度下的切割后最大收益
	int pipePrice[] = new int[pipeLength+1];
	// 某个长度下的切割最大收益时的第一段切割长度
	int pipeSplitLength[] = new int[pipeLength+1];
	
	/**
	 * 自底向上思考
	 * 依次计算长度为1,2,3...时的最大收益，即大问题的最优解依赖于子问题的最优解（反证法可证明）
	 */
	void splitPipeFromBottom() {
		
		for(int i=1; i<=pipeLength; i++) {
			int p = -1;
			for(int k=1; k<=i; k++) {
				int temp = getPipePriceWithLength(k) + pipePrice[i-k];
				if(p < temp) {
					p = temp;
					pipeSplitLength[i] = k;
				}
			}
			pipePrice[i] = p;
		}
	}
	
	/**
	 * 自顶向下计算
	 * 第一次切割长度为1，然后计算剩余长度的最佳切割方案，剩余长度同样先切割长度为1的一段
	 * 递归即可求解，每次递归可以算出当前len下的最大收益
	 * @param len
	 * @return
	 */
	int splitPipeFromTop(int len) {
		if(pipePrice[len] > 0) {
			return pipePrice[len];
		}
		
		if(len == 0)
			return 0;
		
		int r = -1;
		for(int i=1; i<=len; i++) {
			int temp = getPipePriceWithLength(i) + splitPipeFromTop(len-i);
			if(r < temp) {
				r = temp;
				pipeSplitLength[len] = i;
			}
		}
		return r;
	}
	
	int getPipePriceWithLength(int l) {
		return l / 10 * 30 + pipePriceWithLength[l % 10];
	}
	
	
	// 拦截导弹问题
	void intercept() {
		int array[] = {4, 19, 12, 7, 2, 4, 10, 3, 2};
		// 拦截第n个位置时所能拦截的最大个数
		int cacheCnt[] = new int[array.length];
		// 拦截第n个位置时应该拦截的下一个位置
		int cacheFa[] = new int[array.length];
		
		int len = array.length-1;
		
		for(int i=len; i>=0; i--) {
			// 拦截最后一个
			if(i == len) {
				cacheCnt[len] = 1;
				continue;
			}
			
			int cnt = 1;
			for(int k=len; k>i && array[i] > array[k]; k--) {
				int temp = 1 + cacheCnt[k];
				if(cnt < temp) {
					cnt = temp;
					cacheFa[i] = k;
				}
			}
			cacheCnt[i] = cnt;
		}
		
		int position = 0;
		// 
		for(int i=0; i<cacheCnt.length; i++) {
			if(cacheCnt[position] < cacheCnt[i])
				position = i;
			System.out.print(cacheCnt[i] + ", ");
		}
		
		System.out.println();
		for(int i=0; i<cacheFa.length; i++) {
			System.out.print(cacheFa[i] + ", ");
		}
		
		// 输出结果
		System.out.println();
		while(cacheFa[position] != 0) {
			System.out.print(array[position] + ", ");
			position = cacheFa[position];
		}
		
	}
}
