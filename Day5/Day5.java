package Day5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Day5 {
    public static void main(String[] args) throws FileNotFoundException {
        part1();
        part2();
    }

    private static void part1() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("Day5/Day5.txt"));
        String seedStr = scanner.nextLine().substring(7);
        String[] seedStrArr= seedStr.split(" ");
        long[] seedArr = Arrays.stream(seedStrArr).mapToLong(Long::parseLong).toArray();

        scanner.nextLine();
        scanner.nextLine();

        // Seed to Soil
        ArrayList<long[]> soilConv = conversionList(scanner);
        scanner.nextLine();

        // Soil to Fertilizer
        ArrayList<long[]> fertConv = conversionList(scanner);
        scanner.nextLine();

        // Fertilizer to Water
        ArrayList<long[]> waterConv = conversionList(scanner);
        scanner.nextLine();

        // Water to Light
        ArrayList<long[]> lightConv = conversionList(scanner);
        scanner.nextLine();

        // Light to Temperature
        ArrayList<long[]> tempConv = conversionList(scanner);
        scanner.nextLine();

        // Temperature to Humidity
        ArrayList<long[]> humidConv = conversionList(scanner);
        scanner.nextLine();
        
        // Humidity to Location
        ArrayList<long[]> locConv = conversionList(scanner);

        // Conversion
        long[] soilArr = forwardConv(seedArr, soilConv);
        long[] fertArr = forwardConv(soilArr, fertConv);
        long[] waterArr = forwardConv(fertArr, waterConv);
        long[] lightArr = forwardConv(waterArr, lightConv);
        long[] tempArr = forwardConv(lightArr, tempConv);
        long[] humidArr = forwardConv(tempArr, humidConv);
        Long[] locArr = Arrays.stream(forwardConv(humidArr, locConv)).boxed().toArray(Long[]::new);
        System.out.println(Collections.min(Arrays.asList(locArr)));

    }

    private static ArrayList<long[]> conversionList(Scanner scanner) {
        ArrayList<long[]> conv = new ArrayList<>();
        String str = scanner.nextLine();
        while(str.length() > 1) {
            String[] convArr = str.split(" ");
            long[] convLongArr = Arrays.stream(convArr).mapToLong(Long::parseLong).toArray();
            conv.add(convLongArr);
            str = scanner.nextLine();
        }

        return conv;
    }

    private static long[] forwardConv(long[] inArr, ArrayList<long[]> convList) {
        long[] outArr = new long[inArr.length];
        for(int i = 0; i < inArr.length; i++) {
            outArr[i] = inArr[i];
            for(long[] conv : convList) {
                if(inArr[i] >= conv[1] && inArr[i] < conv[1] + conv[2]) {
                    outArr[i] = inArr[i] + (conv[0] - conv[1]);
                    break;
                }
            }
        }

        return outArr;
    }

    private static long backwardConv(long in, ArrayList<long[]> convList) {
        long out = in;
        for(long[] conv : convList) {
            if(in >= conv[0] && in < conv[0] + conv[2]) {
                out = in - (conv[0] - conv[1]);
                break;
            }
        }
        return out;
    }

    private static void part2() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("Day5/Day5.txt"));
        String seedStr = scanner.nextLine().substring(7);
        String[] seedStrArr= seedStr.split(" ");
        ArrayList<Long[]> seedArrList = new ArrayList<>();
        for(int i = 0; i < seedStrArr.length; i+=2) {
            seedArrList.add(new Long[]{Long.parseLong(seedStrArr[i]), Long.parseLong(seedStrArr[i + 1])});
        }
        Long[][] seedArr = new Long[seedArrList.size()][2];
        seedArr = seedArrList.toArray(seedArr);

        scanner.nextLine();
        scanner.nextLine();

        // Seed to Soil
        ArrayList<long[]> soilConv = conversionList(scanner);
        scanner.nextLine();

        // Soil to Fertilizer
        ArrayList<long[]> fertConv = conversionList(scanner);
        scanner.nextLine();

        // Fertilizer to Water
        ArrayList<long[]> waterConv = conversionList(scanner);
        scanner.nextLine();

        // Water to Light
        ArrayList<long[]> lightConv = conversionList(scanner);
        scanner.nextLine();

        // Light to Temperature
        ArrayList<long[]> tempConv = conversionList(scanner);
        scanner.nextLine();

        // Temperature to Humidity
        ArrayList<long[]> humidConv = conversionList(scanner);
        scanner.nextLine();
        
        // Humidity to Location
        ArrayList<long[]> locConv = conversionList(scanner);

        ArrayList<Long> checkList = new ArrayList<>();
        // Seeds to Check
        for(int i = 0; i < seedArr.length; i++) {
            checkList.add(seedArr[i][0]);
        }
        for(long[] conv : soilConv) {
            checkList.add(conv[1]);
        }
        for(long[] conv : fertConv) {
            checkList.add(backwardConv(conv[1], soilConv));
        }
        for(long[] conv : waterConv) {
            checkList.add(backwardConv(backwardConv(conv[1], fertConv), soilConv));
        }
        for(long[] conv : lightConv) {
            checkList.add(backwardConv(backwardConv(backwardConv(conv[1], waterConv), fertConv), soilConv));
        }
        for(long[] conv : tempConv) {
            checkList.add(backwardConv(backwardConv(backwardConv(backwardConv(conv[1], lightConv), waterConv), fertConv), soilConv));
        }
        for(long[] conv : humidConv) {
            checkList.add(backwardConv(backwardConv(backwardConv(backwardConv(backwardConv(conv[1], tempConv), lightConv), waterConv), fertConv), soilConv));
        }
        for(long[] conv : locConv) {
            checkList.add(backwardConv(backwardConv(backwardConv(backwardConv(backwardConv(backwardConv(conv[1], humidConv), tempConv), lightConv), waterConv), fertConv), soilConv));
        }

        for(int i = 0; i < checkList.size(); i++) {
            long l = checkList.get(i);
            boolean check = false;
            for(int j = 0; j < seedArr.length; j++) {
                if(l >= seedArr[j][0] && l < seedArr[j][0] + seedArr[j][1]) {
                    check = true;
                    break;
                }
            }
            if(!check) {
                checkList.remove(i);
                i--;
            }
        }

        long[] checkArr = Arrays.stream(checkList.toArray(new Long[checkList.size()])).mapToLong(Long::longValue).toArray();

        // Conversion
        long[] soilArr = forwardConv(checkArr, soilConv);
        long[] fertArr = forwardConv(soilArr, fertConv);
        long[] waterArr = forwardConv(fertArr, waterConv);
        long[] lightArr = forwardConv(waterArr, lightConv);
        long[] tempArr = forwardConv(lightArr, tempConv);
        long[] humidArr = forwardConv(tempArr, humidConv);
        Long[] locArr = Arrays.stream(forwardConv(humidArr, locConv)).boxed().toArray(Long[]::new);
        System.out.println(Arrays.toString(locArr));
        System.out.println(Collections.min(Arrays.asList(locArr)));

    }
}
