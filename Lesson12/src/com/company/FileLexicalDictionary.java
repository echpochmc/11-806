package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileLexicalDictionary {
    private final static String INPUT_FILE_NAME = "in.txt";
    private final static int LETTERS_NUMBER = 27;

    private boolean isSorted = false;
    private int wordsCount = 0;

    public FileLexicalDictionary(String ... words) throws IOException {
        this();
        for (String word : words) {
            add(word);
        }
    }

    public FileLexicalDictionary() throws IOException {
        clearInputFile();
    }

    /**
     * Returns true if the word is presented in the dictionary
     */
    public boolean exists(String word) {
        return false;
    }

    /**
     * Returns the word with dpecified index
     */
    public String getById(int id) {
        return null;
    }

    /**
     * Adds specified word to a dictionary
     */
    public void add(String word) throws IOException {
        File f = new File(INPUT_FILE_NAME);
        FileWriter fw = new FileWriter(f, true);
        PrintWriter pw = new PrintWriter(fw);
        pw.println(word);
        pw.close();
        wordsCount++;
        isSorted = false;
    }

    /**
     * Sorts all the added words in alphabet order
     */
    public void sort() throws IOException {
        int len = longestWordLength();
        for (int i = len - 1; i >= 0; i--) {
            distributeToFiles(i);
            merge();
        }
        for (char c = 'a'; c <= 'z'; c++) {//deleting all no longer needed files
            File f = new File("out" + c + ".txt");
            if (f.exists()) {
                f.delete();
            }
        }
        isSorted = true;
    }

    public String getDictionaryWordsAsString() throws IOException {
        //todo add check whether the words are sorted
        StringBuffer sb = new StringBuffer();
        Scanner sc = new Scanner(new File(INPUT_FILE_NAME));
        while (sc.hasNextLine()) {
            sb.append(sc.nextLine());
            sb.append("; ");
        }
        return sb.toString();
    }

    public String[] getDictionaryWordsAsArray() throws IOException {
        //todo add check whether the words are sorted
        String[] words = new String[wordsCount];
        Scanner sc = new Scanner(new File(INPUT_FILE_NAME));
        for (int i = 0; i < wordsCount; i++) {
            words[i] = sc.nextLine();
        }
        return words;
    }

    /**
     * Merges all words from outa.txt to outz.txt respectively
     * into source file {@link FileLexicalDictionary#INPUT_FILE_NAME}
     */
    private void merge() throws IOException {
        PrintWriter pw = new PrintWriter(new File(INPUT_FILE_NAME));
        Scanner[] scanners = new Scanner[LETTERS_NUMBER];
        scanners[0] = new Scanner(new File("out0.txt"));
        for (int i = 1; i < LETTERS_NUMBER; i++) {
            char letter = (char) (i + 'a' - 1);
            scanners[i] = new Scanner(new File("out" + letter + ".txt"));
        }
        for (int i = 0; i < LETTERS_NUMBER; i++) {
            while (scanners[i].hasNextLine()) {
                String s = scanners[i].nextLine();
                pw.println(s);
            }
        }
        for (Scanner scanner : scanners) {
            scanner.close();
        }
        pw.close();
    }

    /**
     * Places each word in the right file
     * according to k'th letter
     */
    private void distributeToFiles(int k) throws IOException {
        Scanner sc = new Scanner(new File(INPUT_FILE_NAME));
        PrintWriter[] pws = new PrintWriter[LETTERS_NUMBER];
        pws[0] = new PrintWriter(new File("out0.txt"));
        for (int i = 1; i < LETTERS_NUMBER; i++) {
            char letter = (char) (i + 'a' - 1);
            pws[i] = new PrintWriter(new File("out" + letter + ".txt"));
        }
        while (sc.hasNextLine()) {
            String word = sc.nextLine();
            if (k < word.length()) {
                char c = word.charAt(k);
                pws[(c - 'a') + 1].println(word);
            } else {
                pws[0].println(word);
            }
        }
        for (PrintWriter pw : pws) {
            pw.close();
        }
    }

    /**
     * Returns the longestWordLength of the longest word in a file
     */
    private int longestWordLength() throws IOException {
        Scanner sc = new Scanner(new File(INPUT_FILE_NAME));
        int max = 0;
        while (sc.hasNextLine()) {
            String s = sc.nextLine();
            int currentStringLength = s.length();
            if (currentStringLength > max) {
                max = currentStringLength;
            }
        }
        return max;
    }

    /**
     * Clears input file
     */
    private void clearInputFile() throws IOException {
        PrintWriter pw = new PrintWriter(new File(INPUT_FILE_NAME));
        pw.close();
    }
}
