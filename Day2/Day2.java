package Day2;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day2 {
    public static void main(String[] args) throws FileNotFoundException {
        part1();
        part2();
    }

    public static void part2() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("Day2.txt"));
        int sum = 0;
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] split = line.split(": ");
            String[] split2 = split[1].split("; ");
            int minRed = 0;
            int minGreen = 0;
            int minBlue = 0;
            for (String s : split2) {
                String[] split3 = s.split(", ");
                for (String s1 : split3) {
                    String[] split4 = s1.split(" ");
                    int num = Integer.parseInt(split4[0]);
                    char color = split4[1].charAt(0);
                    switch(color) {
                        case 'r':
                            if (num > minRed) {
                                minRed = num;
                            }
                            break;
                        case 'g':
                            if (num > minGreen) {
                                minGreen = num;
                            }
                            break;
                        case 'b':
                            if (num > minBlue) {
                                minBlue = num;
                            }
                            break;
                    }
                }
            }
            sum += minRed * minGreen * minBlue;
        }
        System.out.println(sum);
    }

    public static void part1() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("Day2.txt"));
        int sum = 0;
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] split = line.split(": ");
            int ID = Integer.parseInt(split[0].substring(5));
            boolean valid = true;
            String[] split2 = split[1].split("; ");
            for (String s : split2) {
                String[] split3 = s.split(", ");
                for (String s1 : split3) {
                    String[] split4 = s1.split(" ");
                    int num = Integer.parseInt(split4[0]);
                    char color = split4[1].charAt(0);
                    switch(color) {
                        case 'r':
                            if (num > 12) {
                                valid = false;
                            }
                            break;
                        case 'g':
                            if (num > 13) {
                                valid = false;
                            }
                            break;
                        case 'b':
                            if (num > 14) {
                                valid = false;
                            }
                            break;
                    }
                }
            }
            if (valid) {
                sum += ID;
            }
        }
        System.out.println(sum);
    }
}