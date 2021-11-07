package io.whits.javadev.simple;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** <strong>A wrapper for <code>java.util.Scanner</code> designed to be more tolerant to user
 * buffoonery.</strong><p>
 * 
 * SmartScanner provides a series of methods to be used in the place of
 * the <code>java.util.Scanner</code> methods which are designed to be directly
 * shown to the user, specifically for when user values need to be cast
 * as types such as booleans or doubles. The methods safely check for 
 * bogus data to prevent potential crashes or unhandled exceptions.<p>
 * 
 * <i>This library is provided to everyone for use, modification, and
 * redistribution under the GNU AGPL license version 3.0 or later. A
 * LICENSE.txt file should be provided with this library, however it is
 * also available online at {@link https://www.gnu.org/licenses/agpl-3.0.en.html}.</i><p>
 * 
 * <i>For more info, please visit {@link https://whits.io/licensing}.</i>
 * @author Whit Huntley
 * @version 1.2.0
 * @since 2021-10-26
*/
public class SmartScanner {
    private Scanner input;

    /**
     * Construct a SmartScanner creating a new java.util.Scanner which reads
     * input from STDIN.
     */
    public SmartScanner() {
        this.input = new Scanner(System.in);
    }
    /**
     * Construct a new SmartScanner using an existing java.util.Scanner which
     * is already in use by the program utilizing this library.
     * @param s - The existing scanner to be used for the SmartScanner.
     */
    public SmartScanner(Scanner s) {
        this.input = s;
    }

    /**
     * Get the scanner currently used by SmartScanner
     * @return the scanner used by the instance of the SmartScanner
     */
    public Scanner getScanner() {
        return input;
    }
    
    /**
     * Set the scanner to be used by the SmartScanner
     * @param s the scanner to be used
     */
    public void setScanner(Scanner s) {
        input = s;
    }

    /** <strong>Get user input</strong>
     * <code>nextLine</code> is just a call to the default scanner nextline
     * with no changes.
     * @param prompt - The prompt to be provided to the user
     * @return The now cleaner input
     */
    public String nextLine(String prompt) {
        System.out.println(prompt);
        System.out.print("> ");
        String response = input.nextLine();
        response = response.toLowerCase();
        response = response.trim();
        return response;
    }

    /** <strong>Get user input and sanitize it</strong>
     * <code>smartNextStringSanitized</code> will prompt the user for input via STDIN and then clean
     * the resulting string so that it is more easily parsed by more simple code.
     * @param prompt - The prompt to be provided to the user
     * @return The now cleaner input
     */
    public String smartNextStringSanitized(String prompt) {
        System.out.println(prompt);
        System.out.print("> ");
        String response = input.nextLine();
        response = response.toLowerCase();
        response = response.trim();
        return response;
    }

    /** <strong>Get user input matching a regular expression</strong>
     * <code>smartForceNextStringMatching</code> will prompt the user for input via the input
     * Scanner and attempt to make it match a Java regex pattern. If the user fails to enter a
     * matching string, they will be prompted again.
     * @param prompt - the prompt to be shown to the user
     * @param e - the expression (from java's <code>util.regex.Pattern</code> class) to validate against
     * @return the user's validated input
     */
    public String smartForceNextStringMatching(String prompt, Pattern e) {
        String response = "";
        boolean validResponse = false;
        while (!validResponse) {
            System.out.println(prompt);
            System.out.print("> ");
            response = input.nextLine();
            Matcher m = e.matcher(prompt);
            if (m.find()) {
                validResponse = true;
            } else {
                System.out.println("That was not a valid response. Try again.");
                validResponse = false;
            }
        }
        return response;
    }

    /** <strong>Get the next valid integer</strong><p>
     * <code>nextInt</code> will continually loop until the user provides a 
     * valid integer value. Do not use this method if it can be avoided, it
     * is simply here for compatibility with the default scanner nextInt.
     * @return The user's provided value as an integer.
     */
    public int nextInt() {
        System.err.println("Bad programmer didn't update methods (nextInt rather than smartForceNextInt used).");
        return smartForceNextInt("");
    }

    /** <strong>Safely parse user input and cast as an int</strong><p>
     * <code>smartForceNextInt</code> will prompt the user for input via STDIN and attempt to
     * cast the resulting value as an integer. If the user fails to enter a valid
     * value to cast as an int, the function will alert them and provide
     * another prompt.
     * @param prompt - The text to be displayed to the user.
     * @return The user's provided value as an integer.
     */
    public int smartForceNextInt(String prompt) {
        int value = 0;
        boolean validResponse = false;
        while (!validResponse) {
            System.out.println(prompt);
            System.out.print("> ");
            try {
                value = Integer.parseInt(input.nextLine());
                validResponse = true;
            } catch (Exception e) {
                System.out.println("That is not a valid value. Try again.");
                System.out.println("Detailed error below:");
                System.out.println(e);
                System.out.println();
                validResponse = false;
            }
        }
        return value;
    }

    /** <strong>Safely parse user input and cast as an int within a range</strong><p>
     * <code>smartForceNextInt</code> will prompt the user for input via STDIN and attempt to
     * cast the resulting value as an int. If the user fails to enter a valid
     * value to cast as an int, the function will alert them and provide
     * another prompt.
     * @param prompt - The text to be displayed to the user.
     * @param rangeLower - The lower limit of allowed values (inclusive)
     * @return The user's provided value as an integer.
     */
    public int smartForceNextInt(String prompt, int rangeLower) {
        int value = 0;
        boolean validResponse = false;
        while (!validResponse) {
            // First get a healthy int value.
            value = smartForceNextInt(prompt);

            // Now check if within range
            if (value >= rangeLower) {
                validResponse = true;
            } else {
                System.out.printf("Please enter a value greater than %,d\n",
                    rangeLower);
                validResponse = false;
            }
        }
        return value;
    }

    /** <strong>Safely parse user input and cast as an int within a range</strong><p>
     * <code>smartForceNextInt</code> will prompt the user for input via STDIN and attempt to
     * cast the resulting value as an int. If the user fails to enter a valid
     * value to cast as an int, the function will alert them and provide
     * another prompt.
     * @param prompt - The text to be displayed to the user.
     * @param rangeLower - The lower limit of allowed values (inclusive)
     * @param rangeUpper - The upper limit of allowed values (inclusive)
     * @return The user's provided value as an int.
     */
    public int smartForceNextInt(String prompt, int rangeLower, int rangeUpper) {
        int value = 0;
        boolean validResponse = false;
        while (!validResponse) {
            value = smartForceNextInt(prompt);
            // Now check if within range
            if (value >= rangeLower && value <= rangeUpper) {
                validResponse = true;
            } else {
                System.out.printf("Please enter a value between %,d and %,d.\n",
                    rangeLower,
                    rangeUpper);
                validResponse = false;
            }
        }
        return value;
    }


    /** <strong>Get the next valid double</strong><p>
     * <code>nextInt</code> will continually loop until the user provides a 
     * valid double value. Do not use this method if it can be avoided, it
     * is simply here for compatibility with the default scanner nextInt.
     * @return The user's provided value as an integer.
     */
    public int nextDouble() {
        System.err.println("Bad programmer didn't update methods (nextInt rather than smartForceNextDouble used).");
        return smartForceNextInt("");
    }


    /** <strong>Safely parse user input and cast as a double</strong><p>
     * <code>smartForceNextDouble</code> will prompt the user for input via STDIN and attempt to
     * cast the resulting value as a double. If the user fails to enter a valid
     * value to cast as a double, the function will alert them and provide
     * another prompt.
     * @param prompt - The text to be displayed to the user.
     * @return The user's provided value as a double.
     */
    public double smartForceNextDouble(String prompt) {
        double value = 0;
        boolean validResponse = false;
        while (!validResponse) {
            System.out.println(prompt);
            System.out.print("> ");
            try {
                value = Double.parseDouble(input.nextLine());
                validResponse = true;
            } catch (Exception e) {
                System.out.println("That is not a valid value. Try again.");
                System.out.println("Detailed error below:");
                System.out.println(e);
                System.out.println();
                validResponse = false;
            }
        }
        return value;
    }

    /** <strong>Safely parse user input and cast as a double within a range</strong><p>
     * <code>smartForceNextDouble</code> will prompt the user for input via STDIN and attempt to
     * cast the resulting value as a double. If the user fails to enter a valid
     * value to cast as a double, the function will alert them and provide
     * another prompt.
     * @param prompt - The text to be displayed to the user.
     * @param rangeLower - The lower limit of allowed values (inclusive)
     * @return The user's provided value as a double.
     */
    public double smartForceNextDouble(String prompt, double rangeLower) {
        double value = 0;
        boolean validResponse = false;
        // Now check if within range
        while (!validResponse) {
            // First get a healthy double value.
            value = smartForceNextDouble(prompt);
            if (value >= rangeLower) {
                validResponse = true;
            } else {
                System.out.printf("Please enter a value greater than %,f.\n",
                    rangeLower);
                validResponse = false;
            }
        }
        return value;
    }

    /** <strong>Safely parse user input and cast as a double within a range</strong><p>
     * <code>smartForceNextDouble</code> will prompt the user for input via STDIN and attempt to
     * cast the resulting value as a double. If the user fails to enter a valid
     * value to cast as a double, the function will alert them and provide
     * another prompt.
     * @param prompt - The text to be displayed to the user.
     * @param rangeLower - The lower limit of allowed values (inclusive)
     * @param rangeUpper - The upper limit of allowed values (inclusive)
     * @return The user's provided value as a double.
     */
    public double smartForceNextDouble(String prompt, double rangeLower, double rangeUpper) {
        double value = 0;
        boolean validResponse = false;
        while (!validResponse) {
            value = smartForceNextDouble(prompt);
            // Now check if within range
            if (value >= rangeLower && value <= rangeUpper) {
                validResponse = true;
            } else {
                System.out.printf("Please enter a value between %,f and %,f.\n",
                    rangeLower,
                    rangeUpper);
                validResponse = false;
            }
        }
        return value;
    }


    /** <strong>Safely parse user input and cast as a boolean</strong><p>
     * <code>smartForceNextBoolean</code> will prompt the user for input via STDIN and attempt to
     * cast the resulting value as a boolean. If the user fails to enter a valid
     * value to represent a boolean, the function will alert them and provide
     * another prompt.
     * @param prompt - The text to be displayed to the user.
     * @return The user's provided value as a boolean.
     */
    public boolean smartForceNextBoolean(String prompt) {
        boolean value = true;
        boolean validResponse = false;
        while (!validResponse) {
            System.out.println(prompt);
            System.out.print("> ");
            String response = input.nextLine();
            // clean up 
            response = response.trim();
            response = response.toLowerCase();
            switch(response) {
                case "yes": value = true;
                    validResponse = true;
                    break;
                case "no": value = false;
                    validResponse = true;
                    break;
                default: System.out.printf("Sorry, %s is not a valid response. Try again.\n\n", response);
                    validResponse = false;
            }
        }
        return value;
    }

    /** <strong>Safely parse user input and attempt to cast as a boolean</strong><p>
     * <code>smartForceNextBoolean</code> will prompt the user for input via STDIN and attempt
     * to cast the resulting value as a boolean. However if the user fails to
     * enter a valid value to represent a boolean, the function will alert them
     * and simply return a default value passed to the method when called.
     * @param prompt - The text to be displayed to the user.
     * @param defaultValue - the default value to be returned in the event the user
     * doesn't pass a boolean cast-able value.
     * @return The user's provided value as a boolean.
     */
    public boolean smartForceNextBoolean(String prompt, boolean defaultValue) {
        String response = this.smartNextStringSanitized(prompt);
        // This is like this for java 11 compatibility.
        switch(response) {
            case "yes": 
            case "y":
            case "1":
            case "true":
            case "t": return true;
            case "no": 
            case "n":
            case "0":
            case "false":
            case "f": return false;
            default: System.out.printf("Sorry, %s is not a valid response. Assuming default value %b.\n\n", 
                response,
                defaultValue);
                return defaultValue;
        }
    }
}
