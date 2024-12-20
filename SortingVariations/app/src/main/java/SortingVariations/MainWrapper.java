package SortingVariations;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class MainWrapper {
    public static void main(String[] originalArgs) {
        String resourceFileName = "PresortedRandomInput.csv";

        try (InputStream inputStream = MainWrapper.class.getClassLoader().getResourceAsStream(resourceFileName)) {
            if (inputStream == null) {
                System.err.println("Resource not found: " + resourceFileName);
                return;
            }

            try (Scanner fileScanner = new Scanner(inputStream)) {
                // Skip the header line
                if (fileScanner.hasNextLine()) {
                    fileScanner.nextLine();
                }

                // For each row in the CSV
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    String[] parts = line.split(",", 3); // n, presortedness, values
                    if (parts.length < 3) {
                        System.err.println("Invalid CSV format: " + line);
                        continue;
                    }

                    int n = Integer.parseInt(parts[0].trim());
                    int presortedness = Integer.parseInt(parts[1].trim());
                    String values = parts[2].trim();

                    // Prepare the lines that the other main expects from System.in
                    // If the other main expects:
                    //   line1: n
                    //   line2: presortedness
                    //   line3: space-separated values
                    String inputData = n + "\n" + presortedness + "\n" + values + "\n";

                    // Set System.in to read from this inputData
                    ByteArrayInputStream bais = new ByteArrayInputStream(inputData.getBytes(StandardCharsets.UTF_8));
                    System.setIn(bais);

                    // args[] for the main: (example based on your previous format)
                    String[] simulatedArgs = {
                            "binomialSort",
                            "PRESORTED",
                            "",
                            "Adaptive",
                            "20"
                    };

                    // Call the actual main method which reads from System.in
                    SortingVariations.Main.main(simulatedArgs);

                    // After one iteration, the next line from CSV is processed similarly
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}