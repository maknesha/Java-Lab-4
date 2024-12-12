import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Клас для представлення літери
class Letter {
    private char character;

    public Letter(char character) {
        this.character = character;
    }

    public char getCharacter() {
        return character;
    }
}

// Клас для представлення слова
class Word {
    private List<Letter> letters;

    public Word(String word) {
        letters = new ArrayList<>();
        for (char ch : word.toCharArray()) {
            letters.add(new Letter(ch));
        }
    }

    public String getWord() {
        StringBuilder sb = new StringBuilder();
        for (Letter letter : letters) {
            sb.append(letter.getCharacter());
        }
        return sb.toString();
    }
}

// Клас для представлення розділового знака
class Punctuation {
    private char symbol;

    public Punctuation(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }
}

// Клас для представлення речення
class Sentence {
    private List<Object> elements; // Може містити слова та розділові знаки

    public Sentence(String sentence) {
        elements = new ArrayList<>();
        String[] parts = sentence.split("(?=[.,!?])|(?<=[.,!?])");
        for (String part : parts) {
            if (part.matches("\\w+")) {
                elements.add(new Word(part));
            } else if (part.matches("\\p{Punct}")) {
                elements.add(new Punctuation(part.charAt(0)));
            }
        }
    }

    public String getSentence() {
        StringBuilder sb = new StringBuilder();
        for (Object element : elements) {
            if (element instanceof Word) {
                sb.append(((Word) element).getWord());
            } else if (element instanceof Punctuation) {
                sb.append(((Punctuation) element).getSymbol());
            }
        }
        return sb.toString();
    }
}

// Клас для представлення тексту
class Text {
    private List<Sentence> sentences;

    public Text(String text) {
        sentences = new ArrayList<>();
        String[] sentenceArray = text.split("(?<=\\.|\\?|!)\\s+");
        for (String sentence : sentenceArray) {
            sentences.add(new Sentence(sentence.trim()));
        }
    }

    public String getText() {
        StringBuilder sb = new StringBuilder();
        for (Sentence sentence : sentences) {
            sb.append(sentence.getSentence()).append(" ");
        }
        return sb.toString().trim();
    }
}

// Виконавчий клас
public class Lab4 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Введіть текст:");
            String input = scanner.nextLine();

            // Заміна табуляцій та пробілів на один пробіл
            String cleanedInput = input.replaceAll("\\s+", " ");
            Text text = new Text(cleanedInput);

            // Пошук найдовшого паліндрому
            String longestPalindrome = findLongestPalindromeWithSpaces(cleanedInput);
            if (!longestPalindrome.isEmpty()) {
                System.out.println("Найдовший паліндром: " + longestPalindrome);
            } else {
                System.out.println("Паліндромів не знайдено.");
            }
        } finally {
            scanner.close();
        }
    }

    public static String findLongestPalindromeWithSpaces(String text) {
        int maxLength = 0;
        String longestPalindrome = "";

        for (int i = 0; i < text.length(); i++) {
            for (int j = i + 1; j <= text.length(); j++) {
                String substring = text.substring(i, j).trim();
                if (isPalindromeIgnoreSpaces(substring) && substring.length() > maxLength) {
                    maxLength = substring.length();
                    longestPalindrome = substring;
                }
            }
        }

        return longestPalindrome;
    }

    public static boolean isPalindromeIgnoreSpaces(String str) {
        int left = 0;
        int right = str.length() - 1;

        while (left < right) {
            // Пропустити пробіли зліва
            while (left < right && str.charAt(left) == ' ') {
                left++;
            }
            // Пропустити пробіли справа
            while (left < right && str.charAt(right) == ' ') {
                right--;
            }
            // Порівняти символи
            if (Character.toLowerCase(str.charAt(left)) != Character.toLowerCase(str.charAt(right))) {
                return false;
            }
            left++;
            right--;
        }

        return true;
    }
}
