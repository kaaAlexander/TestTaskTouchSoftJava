package by.tut.alexander.kaa;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tests {

    @Test
    public void testNotExistFile() {
        String filePath = "this is not real file path";
        Assert.assertEquals(null, Main.readFile(filePath));
    }

    @Test
    public void testEmptyData() {
        List<String> testData = new ArrayList<>();
        Assert.assertEquals(0, Main.parseData(testData).size());
    }

    @Test
    public void testNotValidData() {
        List<String> testData = new ArrayList<>();
        testData.add("08:00");
        testData.add("09:20    10:35");
        testData.add("11:00 16:00error");
        testData.add("10:00 10:30 error");
        testData.add("10:20 11:30");
        testData.add("10:30 17:15");
        Assert.assertEquals(2, Main.parseData(testData).size());
    }

    @Test
    public void testValidData() {
        List<String> testData = new ArrayList<>();
        testData.add("08:00 9:07");
        testData.add("09:20 10:35");
        testData.add("11:00 16:00");
        testData.add("10:00 10:30");
        testData.add("10:20 11:30");
        testData.add("10:30 17:15");
        Assert.assertEquals(6, Main.parseData(testData).size());
    }

    @Test
    public void testDataCount() {
        Map<Integer, List<Double>> data = new HashMap<>();
        List<Double> first = new ArrayList<>();
        first.add(10.00);
        first.add(10.30);
        data.put(1, first);
        first.clear();
        first.add(10.20);
        first.add(11.30);
        data.put(2, first);
        first.clear();
        first.add(11.00);
        first.add(16.00);
        data.put(2, first);
        Assert.assertEquals("2", Integer.toString(Main.employeesCounting(data)));
    }

}
