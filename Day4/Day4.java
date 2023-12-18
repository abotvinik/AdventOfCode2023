package Day4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

class Card {
    private int[] winners;
    private int[] myNumbers;

    public Card(int[] winners, int[] myNumbers) {
        this.winners = winners;
        this.myNumbers = myNumbers;
    }

    public Card(String winners, String myNumbers) {
        int[] winnersArr = new int[(winners.length() + 1) / 3];
        int[] myNumbersArr = new int[(myNumbers.length() + 1) / 3];

        for(int i = 0; i < winnersArr.length; i++) {
            winnersArr[i] = Integer.parseInt(winners.substring(i * 3, i * 3 + 2).trim());
        }
        for(int i = 0; i < myNumbersArr.length; i++) {
            myNumbersArr[i] = Integer.parseInt(myNumbers.substring(i * 3, i * 3 + 2).trim());
        }

        this.winners = winnersArr;
        this.myNumbers = myNumbersArr;
    }

    public int[] getWinners() {
        return winners;
    }

    public int[] getMyNumbers() {
        return myNumbers;
    }

    public void sort(){
        Arrays.sort(this.winners);
        Arrays.sort(this.myNumbers);
    }
}

public class Day4 {

    public static final int SIZE = 199;
    public static Card[] cards = new Card[SIZE];

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("Day4/Day4.txt"));
        for(int i = 0; i < SIZE; i++) {
            String line = scanner.nextLine();
            int colonIndex = line.indexOf(':');
            int barIndex = line.indexOf('|');
            String winners = line.substring(colonIndex + 2, barIndex - 1);
            String myNumbers = line.substring(barIndex + 2);
            cards[i] = new Card(winners, myNumbers);
            cards[i].sort();
        }
        part1();
        part2();
    }

    private static void part1() {
        int total = 0;
        for(int i = 0; i < SIZE; i++) {
            Card card = cards[i];
            int[] winners = card.getWinners();
            int[] myNumbers = card.getMyNumbers();
            int winIdx = 0;
            int myIdx = 0;
            int win = 0;
            while(winIdx < winners.length && myIdx < myNumbers.length) {
                if(winners[winIdx] == myNumbers[myIdx]) {
                    if(win == 0) 
                        win++;
                    else
                        win *= 2;
                    myIdx++;
                } else if(winners[winIdx] < myNumbers[myIdx]) {
                    winIdx++;
                } else {
                    myIdx++;
                }
            }
            total += win; 
        }
        System.out.println(total);
    }

    private static void part2() {
        int dp[] = new int[SIZE];
        for(int i = 0; i < SIZE; i++) {
            dp[i] = 1;
        }

        for(int i = 0; i < SIZE; i++) {
            Card card = cards[i];
            int[] winners = card.getWinners();
            int[] myNumbers = card.getMyNumbers();
            int winIdx = 0;
            int myIdx = 0;
            int win = 0;
            while(winIdx < winners.length && myIdx < myNumbers.length) {
                if(winners[winIdx] == myNumbers[myIdx]) {
                    win++;
                    myIdx++;
                } else if(winners[winIdx] < myNumbers[myIdx]) {
                    winIdx++;
                } else {
                    myIdx++;
                }
            }
            for(int j = 1; j <= win; j++) {
                dp[i + j] += dp[i];
            }
        }
        System.out.println(IntStream.of(dp).sum());
    }
}
