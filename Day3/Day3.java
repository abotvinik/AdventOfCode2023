package Day3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day3 {
    public static final int SIZE = 140;

    static String[] lines = new String[SIZE];
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("Day3/Day3.txt"));
        for (int i = 0; i < SIZE; i++) {
            lines[i] = scanner.nextLine();
        }
        
        part1();
        part2();
    }

    private static void part1() {
        int sum = 0;
        for(int i = 0; i < SIZE; i++) {
            System.out.println(lines[i]);
            int start = 0;
            int end = 0;
            boolean prevDigit = false;
            boolean isPart = false;
            for(int j = 0; j < lines[i].length(); j++) {
                if(prevDigit && Character.isDigit(lines[i].charAt(j))){
                    if(isPart(i, j))
                        isPart = true;
                }
                else if(!prevDigit && Character.isDigit(lines[i].charAt(j))) {
                    start = j;
                    if(isPart(i, j))
                        isPart = true;
                    prevDigit = true;
                }
                else if(prevDigit && !Character.isDigit(lines[i].charAt(j))) {
                    end = j;
                    prevDigit = false;
                    if(isPart)
                        sum += Integer.parseInt(lines[i].substring(start, end));
                    isPart = false;
                }
            }
            if(prevDigit) {
                end = SIZE;
                if(isPart)
                    sum += Integer.parseInt(lines[i].substring(start, end));
            }
        }
        System.out.println(sum);
    }

    private static boolean isPart(int row, int col) {
        // Top left corner
        if(row != 0 && col != 0 && isSymbol(row - 1, col - 1))
            return true;

        // Top middle
        if(row != 0 && isSymbol(row - 1, col))
            return true;

        // Top right corner
        if(row != 0 && col != (SIZE - 1) && isSymbol(row - 1, col + 1))
            return true;

        // Middle left
        if(col != 0 && isSymbol(row, col - 1))
            return true;

        // Middle right
        if(col != (SIZE - 1) && isSymbol(row, col + 1))
            return true;
        
        // Bottom left corner
        if(row != (SIZE - 1) && col != 0 && isSymbol(row + 1, col - 1))
            return true;
        
        // Bottom middle
        if(row != (SIZE - 1) && isSymbol(row + 1, col))
            return true;
        
        // Bottom right corner
        if(row != (SIZE - 1) && col != (SIZE - 1) && isSymbol(row + 1, col + 1))
            return true;

        return false;
    }

    private static boolean isSymbol(int row, int col) {
        char c = lines[row].charAt(col);
        if(c == '.' || (c >= '0' && c <= '9'))
            return false;
        return true;
    }

    private static void part2() {
        int sum = 0;
        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                if(lines[i].charAt(j) == '*') {
                    sum += findRatio(i, j);
                }
            }
        }
        System.out.println(sum);
    }

    private static int findRatio(int row, int col) {
        int numFound = 0;
        int product = 1;
        // Top
        if(row != 0) {
            if(Character.isDigit(getVal(row - 1, col))) {
                int left = col;
                int right = col;
                while(left != -1 && Character.isDigit(getVal(row - 1, left))) {
                    left--;
                }
                while(right != SIZE && Character.isDigit(getVal(row - 1, right))) {
                    right++;
                }
                numFound++;
                product *= Integer.parseInt(lines[row - 1].substring(left + 1, right));
            }
            else {
                if(col != 0 && Character.isDigit(getVal(row - 1, col - 1))) {
                    int left = col - 1;
                    int right = col;
                    while(left != -1 && Character.isDigit(getVal(row - 1, left))) {
                        left--;
                    }
                    numFound++;
                    product *= Integer.parseInt(lines[row - 1].substring(left + 1, right));
                }
                if(col != SIZE && Character.isDigit(getVal(row - 1, col + 1))) {
                    int left = col;
                    int right = col + 1;
                    while(right != SIZE && Character.isDigit(getVal(row - 1, right))) {
                        right++;
                    }
                    numFound++;
                    product *= Integer.parseInt(lines[row - 1].substring(left + 1, right));
                }
            }
        }
        // Middle
        if(col != 0 && Character.isDigit(getVal(row, col - 1))) {
            int left = col - 1;
            int right = col;
            while(left != -1 && Character.isDigit(getVal(row, left))) {
                left--;
            }
            numFound++;
            product *= Integer.parseInt(lines[row].substring(left + 1, right));
        }
        if(col != SIZE && Character.isDigit(getVal(row, col + 1))) {
            int left = col;
            int right = col + 1;
            while(right != SIZE && Character.isDigit(getVal(row, right))) {
                right++;
            }
            numFound++;
            product *= Integer.parseInt(lines[row].substring(left + 1, right));
        }

        // Bottom
        if(row != SIZE - 1) {
            if(Character.isDigit(getVal(row + 1, col))) {
                int left = col;
                int right = col;
                while(left != -1 && Character.isDigit(getVal(row + 1, left))) {
                    left--;
                }
                while(right != SIZE && Character.isDigit(getVal(row + 1, right))) {
                    right++;
                }
                numFound++;
                product *= Integer.parseInt(lines[row + 1].substring(left + 1, right));
            }
            else {
                if(col != 0 && Character.isDigit(getVal(row + 1, col - 1))) {
                    int left = col - 1;
                    int right = col;
                    while(left != -1 && Character.isDigit(getVal(row + 1, left))) {
                        left--;
                    }
                    numFound++;
                    product *= Integer.parseInt(lines[row + 1].substring(left + 1, right));
                }
                if(col != SIZE && Character.isDigit(getVal(row + 1, col + 1))) {
                    int left = col;
                    int right = col + 1;
                    while(right != SIZE && Character.isDigit(getVal(row + 1, right))) {
                        right++;
                    }
                    numFound++;
                    product *= Integer.parseInt(lines[row + 1].substring(left + 1, right));
                }
            }
        }

        return numFound >= 2 ? product : 0;
    }

    private static char getVal(int row, int col) {
        return lines[row].charAt(col);
    }
}
