package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Main {

	static final int seed = -1;
	static final boolean solveMode = false;
	static long solveCount = 0;
	static long solveFalse = 0;
	static double solvabillity = 1;
	static Random random = new Random();

	public static void main(String[] args) {
		int water[][] = { { 1, 2, 3, 4 }, { 5, 6, 1, 4 }, { 7, 8, 1, 9 }, { 8, 9, 4, 2 },
				{ 3, 7, 5, -1 }, { 9, 1, 8, 4 }, { 7, 6, 9, 10 }, { 10, 3, 2, -1 },
				{ 6, 10, 7, -1 }, { 6, 3, 8, 5 }, //{ 12, 6, 11, 8 }, { 12, 1, 3, 11 },
				{ 0, 0, 0, 0 }, { 0, 0, 0, 0 } };
		if (!solveMode) {
			
			LinkedList<Action> result = new LinkedList<>();/*
			moveAndAdd(water, result, 9, 10);
			moveAndAdd(water, result, 8, 10);
			moveAndAdd(water, result, 7, 8);
			moveAndAdd(water, result, 4, 7);
			moveAndAdd(water, result, 6, 4);
			moveAndAdd(water, result, 6, 10);
			moveAndAdd(water, result, 5, 6);
			moveAndAdd(water, result, 0, 5);
			moveAndAdd(water, result, 0, 11);
			moveAndAdd(water, result, 7, 0);
			moveAndAdd(water, result, 11, 7);
			moveAndAdd(water, result, 0, 11);
			moveAndAdd(water, result, 9, 11);
			moveAndAdd(water, result, 3, 9);
			moveAndAdd(water, result, 3, 6);
			moveAndAdd(water, result, 0, 3);
			moveAndAdd(water, result, 1, 0);
			moveAndAdd(water, result, 1, 10);
			moveAndAdd(water, result, 5, 1);
			moveAndAdd(water, result, 5, 9);
			moveAndAdd(water, result, 5, 3);
			moveAndAdd(water, result, 2, 5);
			moveAndAdd(water, result, 4, 5);
			moveAndAdd(water, result, 0, 4);
			moveAndAdd(water, result, 2, 0);
			moveAndAdd(water, result, 9, 0);
			moveAndAdd(water, result, 9, 4);
			moveAndAdd(water, result, 1, 9);
			moveAndAdd(water, result, 2, 9);*/
			printWater(water);
			predict(water, 11);
			System.out.println("Solve: " + solveCount + ", Failure: " + solveFalse + ", Solvabillity: "
					+ ((double) solveFalse / solveCount));
			
			/*
			int table[][] = new int[water.length-2][water.length-2];
			for (int i = 0; i < 12; i++) {
				for (int j = i; j < 12; j++) {
					move(water[i], water[12], 1);
					move(water[j], water[13], 1);
					solveCount = 0;
					solveFalse = 0;
					loop = 0;
					predict(water, 13);
					System.out.print(i + ", " + j + " : ");
					System.out.println("Solve: " + solveCount + ", Failure: " + solveFalse + ", Solvabillity: "
							+ ((double) solveFalse / solveCount));
					table[i][j] = (int) (solveFalse * 100 / solveCount);
					table[j][i] = table[i][j];
					move(water[13], water[j], 1);
					move(water[12], water[i], 1);
				}
			}
			File ff = new File("resulTable.txt");
			try {
				ff.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(ff))) {
				for (int i = 0; i < table.length; i++) {
					bw.write("" + i);
					for (int tmo : table[i]) {
						bw.write(" " + tmo);
					}
					bw.newLine();
				}
				bw.newLine();
			} catch (Exception e) {
				e.printStackTrace();
			}
			*/
		}
		//move(water[1], water[13], 1);

		/*int water[][] = { { 1, 2, 3, 4 }, { 5, 6, 7, 1 }, { 8, 2, 9, 5 }, { 1, 4, 9, 5 }, { 10, 6, 9, 7 },
				{ 10, 8, 9, 8 }, { 3, 11, 3, 7 }, { 6, 4, 7, 10 }, { 6, 10, 2, 8 }, { 11, 3, 2, 5 }, { 11, 1, 4, 11 },
				{ 0, 0, 0, 0 }, { 0, 0, 0, 0 } };*/

		if (solveMode) {
			predict(water, 9);
			File f = new File("new_resultv4_" + seed + ".txt");
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			LinkedList<Action> result = new LinkedList<>();
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
				for (int tmp[] : water) {
					for (int tmo : tmp) {
						bw.write(" " + tmo);
					}
					bw.newLine();
				}
				bw.newLine();
				for (Action a : result) {
					bw.write(a.toString());
					bw.newLine();
				}
				result.clear();
				search(water, result);
				for (Action a : result)
					System.out.println(a);
				printWater(water);
				for (Action a : result) {
					bw.write(a.toString());
					bw.newLine();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public static void moveAndAdd(int water[][], LinkedList<Action> result, int from, int to) {
		move(water[from], water[to], getTopSize(water[from]));
		result.add(new Action(from, to));
	}

	public static void predict(int water[][], int max) {
		int remain[] = new int[max];
		ArrayList<Integer[]> empty = new ArrayList<>();
		for (int i = 0; i < remain.length; i++)
			remain[i] = 4;
		for (int i = 0; i < water.length; i++) {
			for (int j = 0; j < water[i].length; j++) {
				if (water[i][j] != -1)
					remain[water[i][j]]--;
				else
					empty.add(new Integer[] { i, j });
			}
		}
		for (int r : remain)
			System.out.println(r);
		Integer tempty[][] = new Integer[empty.size()][2];
		empty.toArray(tempty);
		makeWater(water, remain, tempty, 0);
		if (solveMode) {
			printWater(water);
			System.out.println(loop);
		}
	}

	static long loop = 0;

	public static void makeWater(int water[][], int remain[], Integer index[][], int now) {
		if (now >= index.length) {
			loop++;
			if (!solveMode && solvabillity >= random.nextDouble()) {
				solveCount++;
				int tmpWater[][] = new int[water.length][water[0].length];
				for (int i = 0; i < tmpWater.length; i++) {
					for (int j = 0; j < tmpWater[i].length; j++) {
						tmpWater[i][j] = water[i][j];
					}
				}
				try {
					if (!search(tmpWater, new LinkedList<>())) {
						solveFalse++;
						//printWater(water);
						//System.out.println(loop);
					}
				} catch (Exception e) {
					solveCount--;
				}
			}
			return;
		}
		if (loop > 10000000)
			return;
		int x = index[now][0];
		int y = index[now][1];
		for (int i = 1; i < remain.length; i++) {
			if (remain[i] <= 0)
				continue;
			if ((y > 0 && water[x][y - 1] == i) || (y < water[i].length - 1 && water[x][y + 1] == i))
				continue;
			remain[i]--;
			water[x][y] = i;
			makeWater(water, remain, index, now + 1);
			if (loop == seed)
				return;
			remain[i]++;
		}
		water[x][y] = -1;
	}

	public static void printWater(int water[][]) {
		for (int tmp[] : water) {
			for (int tmo : tmp) {
				System.out.print(" " + tmo);
			}
			System.out.println();
		}
		System.out.println();
	}

	static boolean isDebug = false;
	static long time = 0;
	static final long maxCount = 1000000;

	public static boolean search(int water[][], LinkedList<Action> result) throws Exception {
		time = 0;
		for (int i = 0; i < water.length; i++) {
			if (isFinishedWater(water[i]))
				continue;
			int topWater = getTopWater(water[i]);
			int topSize = getTopSize(water[i]);
			boolean isOne = isOneWater(water[i]);
			boolean flag = false;
			for (int j = 0; j < water.length; j++) {
				if (i == j)
					continue;
				int top = getTopWater(water[j]);
				if (top == 0 || top == topWater) {
					if (top == 0) {
						if (flag)
							continue;
						flag = true;
						if (isOne)
							continue;
					}
					int remainSize = getRemainSize(water[j]);
					if (topSize > remainSize)
						continue;
					move(water[i], water[j], topSize);
					time++;
					if (time % 10000 == 0 && isDebug)
						printWater(water);
					if (!solveMode && time >= maxCount)
						throw new Exception();
					if (isCompleted(water) || subSearch(water, result, i, j)) {
						result.addFirst(new Action(i, j));
						return true;
					}
					move(water[j], water[i], topSize);
				}
			}
		}
		return false;
	}

	public static boolean subSearch(int water[][], LinkedList<Action> result, int from, int to) throws Exception {
		int tmp[] = { from, to };
		for (int i = 0; i < tmp.length; i++) {
			if (isFinishedWater(water[tmp[i]]) && water[tmp[i]][0] != 0)
				continue;
			int topWater = getTopWater(water[tmp[i]]);
			int topSize = getTopSize(water[tmp[i]]);
			boolean isOne = isOneWater(water[tmp[i]]);
			int remainSize = getRemainSize(water[tmp[i]]);
			for (int j = 0; j < water.length; j++) {
				if (tmp[i] == j)
					continue;
				int top = getTopWater(water[j]);
				if (top != 0 && topWater != 0 && top != topWater)
					continue;
				int toSize = getTopSize(water[j]);
				int remSize = getRemainSize(water[j]);
				for (int k = 0; k < 2; k++) {
					if (k == 0 && (topWater == 0 || (top == 0 && isOne)))
						continue;
					if (k == 1 && (top == 0 || (topWater == 0 && isOneWater(water[j]))))
						continue;
					int f = tmp[i], t = j, fSize = topSize, tSize = remSize;
					if (k == 1) {
						f = j;
						t = tmp[i];
						fSize = toSize;
						tSize = remainSize;
					}
					if (fSize > tSize)
						continue;
					move(water[f], water[t], fSize);
					time++;
					if (time % 10000 == 0 && isDebug)
						printWater(water);
					if (!solveMode && time >= maxCount)
						throw new Exception();
					if (isCompleted(water) || subSearch(water, result, f, t)) {
						result.addFirst(new Action(f, t));
						return true;
					}
					move(water[t], water[f], fSize);
				}
			}
		}
		return false;
	}

	public static void printCounts(int water[][]) {
		for (int i = 0; i <= 11; i++) {
			int count = 0;
			for (int tmp[] : water) {
				for (int tmo : tmp) {
					if (i == tmo)
						count++;
				}
			}
			System.out.println(i + ": " + count);
		}
	}

	public static boolean isCompleted(int water[][]) {
		for (int i[] : water) {
			for (int j : i) {
				if (j != i[0])
					return false;
			}
		}
		return true;
	}

	public static boolean isFinishedWater(int water[]) {
		for (int i : water)
			if (i != water[0])
				return false;
		return true;
	}

	public static int getTopWater(int water[]) {
		for (int i : water)
			if (i != 0)
				return i;
		return 0;
	}

	public static int getRemainSize(int water[]) {
		for (int i = 0; i < water.length; i++)
			if (water[i] != 0)
				return i;
		return water.length;
	}

	public static int getTopSize(int water[]) {
		int empty = getRemainSize(water);
		for (int i = empty; i < water.length; i++)
			if (water[i] != water[empty])
				return i - empty;
		return water.length - empty;
	}

	public static boolean isOneWater(int water[]) {
		return water.length == getRemainSize(water) + getTopSize(water);
	}

	public static void move(int from[], int to[], int size) {
		int top = getTopWater(from);
		int tmpSize = size;
		for (int i = 0; tmpSize > 0; i++) {
			if (from[i] == 0)
				continue;
			if (from[i] != top)
				break;
			from[i] = 0;
			tmpSize--;
		}
		for (int i = to.length - 1; size > 0; i--) {
			if (to[i] != 0)
				continue;
			to[i] = top;
			size--;
		}
	}

}

record Action(int pre, int after) {
	@Override
	public String toString() {
		return pre + " -> " + after;
	}
}