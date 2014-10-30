import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// createRandomFile("E:/fileToSort.txt");

		externalSort("E:/fileToSort.txt");

	}

	private static void externalSort(String path) {
		int maxFile = 340000;
		int tempFile = 10000;

		int slices = (int) Math.ceil(maxFile / tempFile);

		int[] buffer = new int[tempFile];

		String perLine = null;

		try {

			FileWriter fw = null;
			PrintWriter pw = null;

			BufferedReader br = new BufferedReader(new FileReader(path));

			int i, j;

			i = j = 0;

			for (i = 0; i < slices; i++) {

				for (j = 0; j < tempFile; j++) {
					perLine = br.readLine();
					if (perLine != null)
						buffer[j] = Integer.parseInt(perLine);
					else
						break;
				}

				quickSort(buffer, 0, buffer.length - 1);

				// Write the sorted numbers to temp file
				fw = new FileWriter("E:/zTestCase/" + "sorted"
						+ Integer.toString(i) + ".txt");
				pw = new PrintWriter(fw);

				for (int k = 0; k < j; k++)
					pw.println(buffer[k]);

				pw.close();
				fw.close();
			}

			br.close();

			BufferedReader[] brs = new BufferedReader[slices];

			int[] topNumbers = new int[slices];

			for (int k = 0; k < slices; k++) {

				brs[k] = new BufferedReader(new FileReader("E:/zTestCase/"
						+ "sorted" + Integer.toString(k) + ".txt"));

				perLine = brs[k].readLine();

				if (perLine != null) {
					topNumbers[k] = Integer.parseInt(perLine);
				}

				else {
					topNumbers[k] = Integer.MAX_VALUE;
				}
			}

			fw = new FileWriter("E:/zTestCase/FinalSorted.txt");

			pw = new PrintWriter(fw);

			int min = 0, minFile = 0;

			for (i = 0; i < maxFile; i++) {
				
				min = topNumbers[0];
				minFile = 0;
				
				for (j = 0; j < slices; j++) {

					if (min > topNumbers[j]) {
						min = topNumbers[j];
						minFile = j;
					}

				}

				pw.println(min);

				perLine = brs[minFile].readLine();

				if (perLine != null) {
					topNumbers[minFile] = Integer.parseInt(perLine);
				}

				else {
					topNumbers[minFile] = Integer.MAX_VALUE;
				}
			}

			for (i = 0; i < 2; i++)
				brs[i].close();

			pw.close();
			fw.close();

		} catch (FileNotFoundException exception) {
			exception.printStackTrace();
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	private static void createRandomFile(String path) {

		try {

			File file = new File(path);

			if (file.createNewFile()) {
				System.out.println("File is created!");

				BufferedWriter bw = new BufferedWriter(new FileWriter(file));
				Random r = new Random();
				for (int i = 0; i < 50000000; i++) {
					bw.write(String.valueOf(r.nextInt(100000)));
					bw.newLine();
				}

				bw.close();

			} else {
				System.out.println("File already exists.");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void quickSort(int[] arr, int left, int right) {
		int index = quickSortPartition(arr, left, right);

		if (index - 1 > left) {
			quickSort(arr, left, index - 1);
		}

		if (right > index) {
			quickSort(arr, index, right);
		}
	}

	private static int quickSortPartition(int[] arr, int left, int right) {
		int i = left, j = right, temp;

		int pivot = arr[(left + right) / 2];

		while (i <= j) {

			while (arr[i] < pivot) {
				i++;
			}

			while (arr[j] > pivot) {
				j--;
			}

			if (i <= j) {
				temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;

				i++;
				j--;
			}
		}

		return i;
	}

}
