
import java.util.Scanner;

public class Client {

	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		char[][] board = new char[10][10];
		for (int i = 0; i < 10; i++) {
			board[i] = scn.nextLine().toCharArray();
		}
		String[] words = scn.nextLine().split(";");
		crossword(board, words, 0);
		crossword2(board, words, 1, new boolean[words.length]);
	}

	public static void crossword(char[][] board, String[] words, int wsf) {
		if (wsf == words.length) {
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[0].length; j++) {
					System.out.print(board[i][j] + " ");
				}
				System.out.println();
			}

			return;
		}

		// who will be the sp of words[wsf]
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j] == '-' || board[i][j] == words[wsf].charAt(0)) {
					if (canplacewordvertically(board, i, j, words[wsf]) == true) {
						boolean[] reset = placewordvertically(board, i, j, words[wsf]);
						crossword(board, words, wsf + 1);
						unplacewordvertically(board, i, j, reset);
					}

					if (canplacewordhorizontally(board, i, j, words[wsf]) == true) {
						boolean[] reset = placewordhorizontally(board, i, j, words[wsf]);
						crossword(board, words, wsf + 1);
						unplacewordhorizontally(board, i, j, reset);
					}
				}
			}
		}
	}

	public static void crossword2(char[][] board, String[] words, int bno, boolean[] wusf) {
		if (bno == board.length * board.length + 1) {
			for (int i = 0; i < wusf.length; i++) {
				if (wusf[i] == false) {
					return;
				}
			}
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[0].length; j++) {
					System.out.print(board[i][j] + " ");
				}
				System.out.println();
			}

			return;
		}

		int row = (bno - 1) / board.length;
		int col = (bno - 1) % board.length;

		boolean flag = true;
		for (int i = 0; i < words.length; i++) {
			if (wusf[i] == false) {
				if (canplacewordhorizontally(board, row, col, words[i])) {
					flag = false;
					boolean[] reset = placewordhorizontally(board, row, col, words[i]);
					wusf[i] = true;
					crossword2(board, words, bno + 1, wusf);
					wusf[i] = false;
					unplacewordhorizontally(board, row, col, reset);
				}

				if (canplacewordvertically(board, row, col, words[i])) {
					flag = false;
					boolean[] reset = placewordvertically(board, row, col, words[i]);
					wusf[i] = true;
					crossword2(board, words, bno + 1, wusf);
					wusf[i] = false;
					unplacewordvertically(board, row, col, reset);
				}
			}
		}
		if(flag){
				crossword2(board, words, bno + 1, wusf);
		}
	}

	private static boolean canplacewordvertically(char[][] board, int i, int j, String word) {
		if (i > 0 && board[i - 1][j] != '+') {
			return false;
		} else if (i + word.length() < board.length && 
				   board[i + word.length()][j] != '+') {
			return false;
		}

		for (int r = 0; r < word.length(); r++) {
			if (i + r == board.length || (board[i + r][j] != '-' && board[i + r][j] != word.charAt(r))) {
				return false;
			}
		}

		return true;
	}

	public static boolean[] placewordvertically(char[][] board, int i, int j, String word) {
		boolean[] ret = new boolean[word.length()];

		for (int r = 0; r < word.length(); r++) {
			ret[r] = board[i + r][j] == '-';
			board[i + r][j] = word.charAt(r);
		}

		return ret;
	}

	public static void unplacewordvertically(char[][] board, int i, int j, boolean[] reset) {
		for (int r = 0; r < reset.length; r++) {
			if (reset[r]) {
				board[i + r][j] = '-';
			}
		}
	}

	private static boolean canplacewordhorizontally(char[][] board, int i, int j, String word) {
		if (j > 0 && board[i][j - 1] != '+') {
			return false;
		} else if (j + word.length() < board[0].length && board[i][j + word.length()] != '+') {
			return false;
		}

		for (int c = 0; c < word.length(); c++) {
			if (j + c == board.length || (board[i][j + c] != '-' && board[i][j + c] != word.charAt(c))) {
				return false;
			}
		}

		return true;
	}

	public static boolean[] placewordhorizontally(char[][] board, int i, int j, String word) {
		boolean[] ret = new boolean[word.length()];

		for (int c = 0; c < word.length(); c++) {
			ret[c] = board[i][j + c] == '-';
			board[i][j + c] = word.charAt(c);
		}

		return ret;
	}

	public static void unplacewordhorizontally(char[][] board, int i, int j, boolean[] reset) {
		for (int c = 0; c < reset.length; c++) {
			if (reset[c]) {
				board[i][j + c] = '-';
			}
		}
	}

}
