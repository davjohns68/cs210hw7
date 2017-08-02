// CS210 Assignment #7 "Personality Test"
// David Johnson
// This program analyzes test results from the The Keirsey Temperament Sorter
// and displays the results of where a person lies in the spectrum of personality types
import java.io.*;    // For file I/O
import java.util.*;  // For scanners

public class PersonalityTest {

    public static void main(String[] args) throws FileNotFoundException {
	System.out.println("** This program reports the results for Keirsey Personality Test  **\n");
        
        Scanner testData = new Scanner(new File("personality.txt"));
        
        while (testData.hasNext()) {
            String name = testData.nextLine();
            char[] data = testData.nextLine().toCharArray();
            // Set up arrays for different types of data
            char[] ei = new char[20];
            char[] sn = new char[20];
            char[] tf = new char[20];
            char[] jp = new char[20];
            // allAnswers holds the strings of all answer counts
            String[] allAnswers = new String[4];
            // percentB holds the percentage calculations for each type
            int[] percentB = new int[4];
            // personalityType holds the final determination for each of the four types
            char[] personalityType = new char[4];
            // Parse the data
            ei = parseData(data, "ei");
            sn = parseData(data, "sn");
            tf = parseData(data, "tf");
            jp = parseData(data, "jp");
            // Count the answers
            allAnswers[0] = countAnswers(ei);
            allAnswers[1] = countAnswers(sn);
            allAnswers[2] = countAnswers(tf);
            allAnswers[3] = countAnswers(jp);
            // Calculate the percentages
            percentB = calculatePercentages(allAnswers);
            // Determine the personality types
            personalityType = calculatePersonalityType(percentB);
            // Print the results
            printPersonResults(name, allAnswers, percentB, personalityType);
        }
    }
    
    public static void printPersonResults(String name, String[] ans, int[] perB, char[] type) {
        // This method prints all the user data to the console
        System.out.println(name + ":");
        System.out.println("answers: " + Arrays.toString(ans));
        System.out.println("percent B: " + Arrays.toString(perB));
        System.out.print("type: ");
        for (int i = 0; i < 4; i++) {
            System.out.print(type[i]);
        }
        System.out.println();
        System.out.println();
    }
    
    public static char[] parseData(char[] data, String type) {
        // This method will parse the string (passed as a char array) and return
        // a char array that is filled with the answers tor questions of that type
        // The type parameter is being used to inform the offset that marks the 
        // beginning of the appropriate data type
        int offset = 0;
        char[] parsedData = new char[20];
        if (type.equals("sn")) {
            offset = 1;
        } else if (type.equals("tf")) {
            offset = 3;
        } else if (type.equals("jp")) {
            offset = 5;
        }
        int count = 0;
        for (int i = 0; i < 10; i++) {
            parsedData[count] = data[i * 7 + offset];
            if (!type.equals("ei")) {
                parsedData[count + 1] = data[i * 7 + offset + 1];
            }
            count += 2;
        }
        return parsedData;
    }
    
    public static String countAnswers(char[] data) {
        // This method takes a char array containing answers for any type
        // It returns a string denoting the count of A vs B answers
        int aCount = 0;
        int bCount = 0;
        String answers;
        for (char answer : data) {
            if (Character.toLowerCase(answer) == 'b') {
                bCount++;
            } else if (Character.toLowerCase(answer) == 'a') {
                aCount++;
            }
        }
        answers = aCount + "A-" + bCount + "B";
        return answers;
    }
    
    public static int[] calculatePercentages(String[] answers) {
        // This method takes the answers array and uses the values contained
        // therein to calculate the percentage of B answers.
        //  It will return an int array of the percentages
        double aCount;
        double bCount;
        int[] results = new int[4];
        for (int i = 0; i < 4; i++) {
            // TODO: need to handle two digit count!
                aCount = Double.parseDouble(answers[i].split("-")[0].split("A")[0]);
                bCount = Double.parseDouble(answers[i].split("-")[1].split("B")[0]);
                results[i] = (int) Math.round(bCount / (aCount + bCount) * 100);
        }
        return results;
    }
    
    public static char[] calculatePersonalityType(int[] ratios) {
        // This method takes the percent B calculations array and uses it to
        // give the final outcome for each of the four personality types
        // It will return an array with the four types for the individual
        char[] type = new char[4];
        if (ratios[0] > 50) {
            type[0] = 'I';
        } else if (ratios[0] < 50) {
            type[0] = 'E';
        } else {
            type[0] = 'X';
        }
        if (ratios[1] > 50) {
            type[1] = 'N';
        } else if (ratios[1] < 50) {
            type[1] = 'S';
        } else {
            type[1] = 'X';
        }
        if (ratios[2] > 50) {
            type[2] = 'F';
        } else if (ratios[2] < 50) {
            type[2] = 'T';
        } else {
            type[2] = 'X';
        }
        if (ratios[3] > 50) {
            type[3] = 'P';
        } else if (ratios[3] < 50) {
            type[3] = 'J';
        } else {
            type[3] = 'X';
        }
        return type;
    }
}
