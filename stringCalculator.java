import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {
    private static final String DEFAULT_DELIMITER = ",";
    private static final String DELIMITER_PREFIX = "//";
    private static final String DELIMITER_SUFFIX = "\n";
    private static final String NEGATIVE_EXCEPTION_MESSAGE = "Negatives not allowed: ";

    public int add(String numbers) {
        if (numbers.isEmpty()) {
            return 0;
        }

        String delimiter = determineDelimiter(numbers);
        String numbersWithoutDelimiter = removeDelimiterPrefix(numbers);

        String[] numberArray = splitNumbers(numbersWithoutDelimiter, delimiter);

        List<Integer> negatives = new ArrayList<>();
        int sum = 0;

        for (String number : numberArray) {
            int num = Integer.parseInt(number);
            if (num < 0) {
                negatives.add(num);
            }
            sum += num;
        }

        if (!negatives.isEmpty()) {
            throw new IllegalArgumentException(NEGATIVE_EXCEPTION_MESSAGE + negatives);
        }

        return sum;
    }

    private String determineDelimiter(String numbers) {
        if (numbers.startsWith(DELIMITER_PREFIX)) {
            int delimiterEndIndex = numbers.indexOf(DELIMITER_SUFFIX);
            return numbers.substring(DELIMITER_PREFIX.length(), delimiterEndIndex);
        }
        return DEFAULT_DELIMITER;
    }

    private String removeDelimiterPrefix(String numbers) {
        if (numbers.startsWith(DELIMITER_PREFIX)) {
            int numbersStartIndex = numbers.indexOf(DELIMITER_SUFFIX) + DELIMITER_SUFFIX.length();
            return numbers.substring(numbersStartIndex);
        }
        return numbers;
    }

    private String[] splitNumbers(String numbers, String delimiter) {
        String[] numberArray;
        if (delimiter.equals(DEFAULT_DELIMITER)) {
            numberArray = numbers.split(Pattern.quote(delimiter));
        } else {
            numberArray = numbers.split(Pattern.quote(delimiter) + "|" + Pattern.quote(DEFAULT_DELIMITER));
        }
        return numberArray;
    }
}
