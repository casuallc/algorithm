package com.qing.java8;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author liuchangqing
 * @time 2016年4月9日下午2:18:02
 * @function 4个生产者线程，1个消费者线程
 * A生产者 1
 * B 2
 * C 3
 * D 4
 * 且只能顺序生产，12341234，消费者顺序消费
 * 数据存放到数组products[13]中
 * 每个生产200次
 */
public class CTest {

	private ReentrantLock lock = new ReentrantLock(true);
	private Condition condition = lock.newCondition();
	private int products[] = new int[13];
	
	private int serverCount = 4;
	private int eachProSum = 200;
	
	public static void main(String[] args) throws Exception {
		CTest main = new CTest();
		main.run();
	}
	
	void run() throws Exception {
		Executor executor = Executors.newFixedThreadPool(5);
		executor.execute(new Client());
		executor.execute(new Server(1));
		executor.execute(new Server(2));
		executor.execute(new Server(3));
		executor.execute(new Server(4));
	}
	
	private int getPos = 0;
	
	class Server implements Runnable {
		private int count = 0;
		private int putPos;

		private int n;
		public Server(int n) {
			this.n = n;
			this.putPos = n-1;
		}
		
		@Override
		public void run() {
			while(true) {
				try {
					lock.lock();
					while(products[putPos % products.length] != 0 || putPos - getPos >= products.length) {
						condition.await();
					}
					products[putPos % products.length] = n;
					putPos +=serverCount;
					count ++;
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				} finally {
					lock.unlock();
					if(count >= eachProSum) {
						System.out.println("server "+n+" is done");						
						return;
					}
				}
			}
		}

		
	}
	
	class Client implements Runnable {

		@Override
		public void run() {
			while(true) {
				if(products[getPos % products.length] == 0)
					continue;
				try {
					lock.lock();
					System.out.print(products[getPos % products.length]+", ");
					products[getPos % products.length] =0;
					getPos ++;
					condition.signalAll();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				} finally {
					lock.unlock();
					if(getPos >= eachProSum*serverCount) {
						System.out.println("client is done");
						return;
					}
				}
			}
		}
		
	}
}
