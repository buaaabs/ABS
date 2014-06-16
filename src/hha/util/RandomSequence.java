package hha.util;

import java.util.Random;

public class RandomSequence {

	/**
	 * 用0~n生成m个数的随机序列
	 * 
	 * @param limit
	 *            - n-1
	 * @param need
	 *            - m
	 * @return 生成的随机序列
	 */
	public static int[] random_serial(int limit, int need) {
		int[] temp = new int[limit];
		int[] result = new int[need];
		for (int i = 0; i < limit; i++)
			temp[i] = i;
		int w;
		Random rand = new Random();
		for (int i = 0; i < need; i++) {
			w = rand.nextInt(limit - i) + i;
			int t = temp[i];
			temp[i] = temp[w];
			temp[w] = t;
			result[i] = temp[i];
		}
		return result;
	}

	/**
	 * 对0～n进行随机乱序排列，比如用于歌曲随机播放。 1、按顺序用0到n填满整个数组；
	 * 2、随机产生从0到n-2个数组下标，把这个下标的元素值跟n-1下标的元素值交换， 一直进行到下标为1的元素。
	 * 因此它只需要遍历一次就能产生全部的随机数。
	 * 
	 * @param limit
	 *            - n-1
	 * @return 生成的随机序列
	 */
	public static int[] random_serial(int limit) {
		int[] result = new int[limit];
		for (int i = 0; i < limit; i++)
			result[i] = i;
		int w;
		Random rand = new Random();
		for (int i = limit - 1; i > 0; i--) {
			w = rand.nextInt(i);
			int t = result[i];
			result[i] = result[w];
			result[w] = t;
		}
		return result;
	}
}