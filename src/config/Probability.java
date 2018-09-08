package config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Random;

/**
 * 权重概率
 * 
 * @author King, Rain
 * 
 */
public final class Probability {
	
	private Probability() {
	}

	/**
	 * 获取随机数
	 * 
	 * @param min
	 * @param max
	 * @return Integer, null: when max < min
	 */
	public static Integer rand(int min, int max) {
		int tmp = max - min;
		if (tmp < 0) {
			return null;
		} else if (tmp == 0) {
			return min;
		} else {
			return ((new Random()).nextInt(tmp + 1)) + min;
		}
	}

	/**
	 * 获取随机数(double)
	 * 
	 * @param min
	 *            最小值
	 * @param max
	 *            最大值
	 * @param round
	 *            保留多少位小数（不能太大）
	 * @return Double, null: when max < min
	 */
	public static Double rand(double min, double max, int round) {
		double dRound = Math.pow(10, round);
		int iMin = (int) (min * dRound);
		int iMax = (int) (max * dRound);
		Integer r = rand(iMin, iMax);
		if (r == null) {
			return null;
		}
		return r / dRound;
	}

	/**
	 * 获取概率事件，几率最多支持3位小数
	 * 
	 * @param map
	 *            参数举例: HashMap(1=>20.1, 2=>29.9, 3=>50), 则20.1%几率返回1, 29.9%返回2,
	 *            50%返回3
	 * @return 返回键值
	 */
	public static <T> T getRand(HashMap<T, Double> map) {
		int multiple = 1000; // 放大位数

		// 求和
		int sum = 0;
		Iterator<Entry<T, Double>> iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<T, Double> entry = iter.next();
			Double v = entry.getValue();
			sum += v * multiple;
		}

		// 产生0-sum的整数随机
		int luckNum = new Random().nextInt(sum) + 1;
		int tmp = 0;
		iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<T, Double> entry = iter.next();
			Double v = entry.getValue();
			tmp += v * multiple;
			if (luckNum <= tmp) {
				return entry.getKey();
			}
		}
		return null;
	}
	
	/**
	 * 获取概率事件，几率最多支持3位小数
	 * 
	 * @param map
	 *            参数举例: HashMap(1=>20.1, 2=>29.9, 3=>50), 则20.1%几率返回1, 29.9%返回2,
	 *            50%返回3
	 * @return 返回键值
	 */
	public static <T> T getRand(HashSet<T> map) {
		int multiple = 1000; // 放大位数
		// 求和
		int sum = map.size() * multiple;
		
		// 产生0-sum的整数随机
		int luckNum = new Random().nextInt(sum) + 1;
		int tmp = 0;
		for(T one : map){
			tmp += multiple;
			if (luckNum <= tmp) {
				return one;
			}
		}
		return null;
	}

	/**
	 * 获取概率事件
	 * 
	 * @param wildsArr
	 *            字符数组
	 * @param num
	 *            返回结果个数
	 * @return 选中结果（会有重复的）
	 */
	public static int[] getRandByStrArray(int[] wildsArr, int num) {
		int[] ret = new int[num];
		int len = wildsArr.length;
		for (int i = 0; i < num; i++) {
			int luckNum = new Random().nextInt(len);
			System.arraycopy(wildsArr, luckNum, ret, i, 1);
		}
		return ret;
	}

	/**
	 * 由概率随机是否触发
	 * 
	 * @param chance
	 *            0.00 - 100.00
	 * @return boolean
	 */
	public static boolean isLuck(double chance) {
		// chance = 50.0;// 测试：30%成功
		int chanceInt = (int) chance * 100;
		return Probability.rand(1, 10000) <= chanceInt;
	}
	/**
	 * 获取两个数之间的随机数，得出的值符合正态分布
	 * 
	 * @param min
	 *            最小值
	 * @param max
	 *            最大值
	 * @param factor
	 *            调整曲线参数
	 * @return
	 */
	public static int gaussianRand(int min, int max, int factor) {
		if (min > max) {
			return 0;
		}
		int middle = (int) Math.ceil((min + max) / 2);
		int in = max - middle;
		factor = factor < 0 ? 0 : factor;
		LinkedHashMap<Integer, Double> map = new LinkedHashMap<Integer, Double>();
		for (int i = min; i <= max; i++) {
			if (i == middle) {
				map.put(i, (double) in);
			} else {
				double tmp = Math.abs(in - Math.abs(middle - i));
				tmp = tmp <= 0 ? 1 : tmp;
				tmp = factor > 0 ? tmp * (1 + factor / 100.0) : tmp;
				tmp = tmp > in ? in : tmp;
				map.put(i, tmp);
			}
		}
		return Probability.getRand(map);
	}
	
	/**
	 * 骰子: 权重概率计算, 每次选出六个格子
	 * 
	 * @param rands
	 * @param start
	 * @return
	 * @date 2013-5-20 下午3:16:29
	 * @author shen
	 */
	public static int getRandGrids(double[] rands, int start) {

		int multiple = 1000; // 放大位数

		// 求和
		int sum = 0;
		int loop1 = start + 6;
		int s1 = start;
		for (int i = s1; i < loop1; i++) {

			if (i == rands.length) {
				loop1 = 6 - i + start;
				i = 0;
				s1 = 0;
			}
			sum += rands[i] * multiple;
		}

		// 产生0-sum的整数随机
		Random random = new Random();
		int luckNum = random.nextInt(sum) + 1;
		int tmp = 0;
		int s2 = start;
		int loop2 = start + 6;
		for (int i = s2; i < loop2; i++) {
			if (i == rands.length) {
				loop2 = 6 - i + start;
				i = 0;
				s2 = 0;
			}
			tmp += rands[i] * multiple;
			if (luckNum <= tmp) {
				return i;
			}
		}
		return -1;
	}
}
