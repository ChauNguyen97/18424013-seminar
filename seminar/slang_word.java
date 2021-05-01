package seminar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class slang_word {

	public static HashMap<String, List<String>> dictHashMap = new HashMap<String, List<String>>();
	public static ArrayList<String> hisList = new ArrayList<String>();
	public static Scanner sr = new java.util.Scanner(System.in);

	public static void ReadFile(String file_name) {

		try {
			File file = new File(file_name);
			FileReader fileReader = new FileReader(file);

			BufferedReader bReader = new BufferedReader(fileReader);
			String line;
			while ((line = bReader.readLine()) != null) {
				if (line.contains("`")) {
					List<String> tmpList = new ArrayList<String>();
					String[] s = line.split("`");
					if (s[1].contains("|")) {
						String[] tmp = s[1].split("\\|");// "Welcome |to| java" -> [Welcome, to, java]
						for (int i = 0; i < tmp.length; i++) {
							tmp[i] = tmp[i].trim();
						}
						tmpList = Arrays.asList(tmp);
					} else {
						tmpList.add(s[1]);
					}
					dictHashMap.put(s[0], tmpList);
				}

			}
			fileReader.close();
			bReader.close();
		} catch (Exception ex) {
			System.out.println("Error: " + ex);
		}
	}

	public static void PrintSlangWord(HashMap<String, List<String>> tempMap) {
		for (String name : tempMap.keySet()) {
			String key = name.toString();
			List<String> value = tempMap.get(name);
			System.out.println("-------------------------------");
			System.out.println("Slang word: " + key);
			System.out.println("Definition: ");
			for (String s : value) {
				System.out.println("-" + s);
			}
		}
	}

	public static void FindBySlang() {
		System.out.println("Enter a slang word: ");
		String key = sr.next();

		hisList.add(key);
		HashMap<String, List<String>> tempMap = new HashMap<String, List<String>>();
		key = key.toUpperCase();

		for (String tmp : dictHashMap.keySet()) {
			if (tmp.contains(key)) {
				tempMap.put(tmp, dictHashMap.get(tmp));
			}
		}
		if (tempMap.isEmpty()) {
			System.out.println("Not Found!!!");
		} else {
			PrintSlangWord(tempMap);
		}

		FindByDefinition();

	}

	public static void FindByDefinition() {
		System.out.println("Enter a definition: ");
		String value = sr.next();

		hisList.add(value);
		HashMap<String, List<String>> tempMap = new HashMap<String, List<String>>();
		value = value.toLowerCase();

		for (String tmp : dictHashMap.keySet()) {
			for (String s : dictHashMap.get(tmp)) {
				if (s.toLowerCase().contains(value)) {
					tempMap.put(tmp, dictHashMap.get(tmp));
				}
			}
		}
		if (tempMap.isEmpty()) {
			System.out.println("Not Found!!!");
		} else {
			PrintSlangWord(tempMap);
		}

		ShowHistory();

	}

	public static void ReadHistory(String file_name) {
		try {
			File file = new File(file_name);
			FileReader fileReader = new FileReader(file);
			BufferedReader br = new BufferedReader(fileReader);
			String line;

			while ((line = br.readLine()) != null) {
				hisList.add(line);
			}

			fileReader.close();
			br.close();
		} catch (Exception ex) {
			System.out.println("Error: " + ex);
		}
	}

	public static void WriteHistory(String file_name) {
		try {
			File file = new File(file_name);
			FileWriter fileWriter = new FileWriter(file);

			for (String s : hisList) {
				fileWriter.write(s + "\n");
			}
			fileWriter.close();
		} catch (Exception ex) {
			System.out.println("Error: " + ex);
		}
	}

	public static void ShowHistory() {
		System.out.println("-------------------------------");
		System.out.println("History Search : ");
		for (String s : hisList) {
			System.out.println("-" + s);
		}
	}

	/*
	 * public static void Overwrite(String sl, String de) { List<String> val = new
	 * ArrayList<String>(); val.add(de); dictHashMap.put(sl.toUpperCase(), val);
	 * System.out.println("Add successfully!!!"); }
	 * 
	 * public static void Duplicate(String sl, String de) { List<String> val = new
	 * ArrayList<String>(); val = dictHashMap.get(sl); val.add(de);
	 * dictHashMap.put(sl.toUpperCase(), val);
	 * System.out.println("Add successfully!!!"); }
	 * 
	 * public static void AddSlang() { System.out.println("Enter Slang word: ");
	 * String slang = sr.next(); System.out.println("Enter Definition: "); String
	 * definition = sr.next(); if (dictHashMap.containsKey(slang)) {
	 * System.out.println("This Slang word was existed, Choose what you want to: ");
	 * System.out.println("1. Overwrite"); System.out.println("2. Dupicate"); int
	 * chose = sr.nextInt(); if(chose == 1) { Overwrite(slang, definition); }else {
	 * Duplicate(slang, definition); } }else { Overwrite(slang, definition); } }
	 */

	public slang_word() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ReadFile("slang.txt");
		/*
		 * for (String name : dictHashMap.keySet()) { String key = name.toString();
		 * String value = dictHashMap.get(name).toString(); System.out.println(key + " "
		 * + value); }
		 */
		FindBySlang();

	}
}
