package by.tut.alexander.kaa;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static List<String> readFile(String filePath) {
        File inputFile = new File(filePath + File.separator + "input.txt");
        List<String> fileContent = null;
        try {
            Scanner fileScanner = new Scanner(inputFile);
            fileContent = new ArrayList<>();
            while (fileScanner.hasNextLine()) {
                fileContent.add(fileScanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("We don't find file by path : " + inputFile.getAbsolutePath() + ". Please check file path and try again!");
        }
        return fileContent;
    }

    public static Map<Integer, List<Double>> parseData(List<String> fileContent) {
        Map<Integer, List<Double>> timeMap = new HashMap<>();
        for (int i = 0; i < fileContent.size(); i++) {
            Integer lineNumber = i + 1;
            List<Double> timeList = new ArrayList<>();
            String line = fileContent.get(i);
            String[] data = line.split(" ");
            if (data.length != 2) {
                System.out.println("Data error! In line : " + lineNumber + " " + line);
            } else {
                for (String dataElement : data) {
                    String[] time = dataElement.split(":");
                    if (time.length != 2) {
                        System.out.println("Data error! In time : " + time + " in line " + lineNumber);
                    } else {
                        try {
                            Double hours = Double.parseDouble(time[0]);
                            if (hours < 0 || hours > 23) {
                                throw new NumberFormatException();
                            }
                            Double minutes = Double.parseDouble(time[1]);
                            if (minutes < 0 || minutes > 59) {
                                throw new NumberFormatException();
                            }
                            hours = hours + (minutes / 100);
                            timeList.add(hours);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid Data in line: " + lineNumber + " " + line);
                        }

                    }
                    if (timeList.size() == 2) {
                        timeMap.put(i, timeList);
                    }
                }
            }
        }
        return timeMap;
    }

    public static Integer employeesCounting(Map<Integer, List<Double>> timeMap) {
        Set<Integer> keySet = timeMap.keySet();
        Integer employeesCount = 0;
        for (Integer entryFirst : keySet) {
            Integer count = 0;
            List<Double> endTimeList = new ArrayList<>();
            List<Double> startTimeList = new ArrayList<>();
            if (!endTimeList.isEmpty()) {
                endTimeList.clear();
            }
            if (!startTimeList.isEmpty()) {
                startTimeList.clear();
            }
            for (Integer entrySecond : keySet) {
                List<Double> firstTimes = timeMap.get(entryFirst);
                List<Double> secondTimes = timeMap.get(entrySecond);
                Double firstStartTime = firstTimes.get(0);
                Double firstEndTime = firstTimes.get(1);
                Double secondStartTime = secondTimes.get(0);
                Double secondEndTime = secondTimes.get(1);
                if (firstStartTime >= secondStartTime && firstStartTime <= secondEndTime) {
                    count = ++count;
                    endTimeList.add(secondEndTime);
                    startTimeList.add(secondStartTime);
                    continue;
                }
                if (firstEndTime >= secondStartTime && firstEndTime <= secondEndTime) {
                    count = ++count;
                    endTimeList.add(secondEndTime);
                    startTimeList.add(secondStartTime);
                    continue;
                }
            }
            if (count > 2) {
                Collections.sort(endTimeList);
                Collections.sort(startTimeList);
                for (Double endTime : endTimeList) {
                    for (Double startTime : startTimeList) {
                        if (endTime < startTime) {
                            count = --count;
                            break;
                        }
                    }
                }
            }
            if (count > employeesCount) {
                employeesCount = count;
            }
        }
        return employeesCount;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Please enter path to input file, or 'exit' for exit from program : ");
            String filePath = scanner.nextLine().trim();
            if (filePath.equals("")) {
                continue;
            }
            if (filePath.equals("exit")) {
                System.exit(0);
            }
            List<String> fileContent = readFile(filePath);
            if (fileContent == null) {
                continue;
            }
            if (fileContent.isEmpty()) {
                System.out.println("File is empty!");
                continue;
            }
            Map<Integer, List<Double>> timeMap = parseData(fileContent);
            if (timeMap.isEmpty()) {
                System.out.println("Data is empty!");
                continue;
            }
            System.out.println("Max employees at work : " + employeesCounting(timeMap));
        }
    }
}


