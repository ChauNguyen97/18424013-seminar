package seminar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
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

	public static void AddSlang() {
		List<String> val = new ArrayList<String>();
		System.out.println("Enter Slang word: ");
		String slang = sr.next();
		System.out.println("Enter Definition: ");
		String definition = sr.next();
		if (dictHashMap.containsKey(slang)) {
			System.out.println("This Slang word was existed, Choose what you want to: ");
			System.out.println("1. Overwrite");
			System.out.println("2. Dupicate");
			int chose = sr.nextInt();
			if (chose == 1) {
				val.add(definition);
			} else {
				val = dictHashMap.get(slang);
				val.add(definition);
			}
		} else {
			val.add(definition);
		}

		dictHashMap.put(slang, val);
		System.out.println("Add successfully!!!");
	}

	public static void SlangEditing(String slang) {
		List<String> val = new ArrayList<String>();
		val = dictHashMap.get(slang);
		dictHashMap.remove(slang);
		System.out.println("Enter new Slang word:");
		slang = sr.nextLine();
		dictHashMap.put(slang, val);
		System.out.println("Edit successfully!!");
	}

	public static void DefinitionEditing(String slang) {
		List<String> val = new ArrayList<String>();
		val = dictHashMap.get(slang);
		HashMap<String, List<String>> tmpMap = new HashMap<String, List<String>>();
		tmpMap.put(slang, val);
		PrintSlangWord(tmpMap);
		System.out.println("The definition you want to change:");
		String deChange = sr.nextLine();
		deChange = deChange.toLowerCase();
		for (String s : val) {
			if (deChange.contains(s.toLowerCase())) {
				System.out.println("1.Delete or 2.Edit definition?");
				System.out.print("Your choise:");
				int choice = sr.nextInt();
				val.remove(s);
				if (choice == 2) {
					System.out.println("Enter new definition:");
					s = sr.nextLine();
				}
				break;
			}
		}
		dictHashMap.put(slang, val);
		System.out.println("Edit successfully!!");
	}

	public static void EditSlang()
	{
		System.out.println("Enter Slang word you want to edit:");
		String slang = sr.nextLine();
		slang = slang.toUpperCase();
		if (!dictHashMap.containsKey(slang)) {
			System.out.println("This slang doesn't match!!!");
		} else {
			System.out.println("What do you want to change?");
			System.out.println("1. Slang word");
			System.out.println("2. Definition");
			System.out.print("Your choice:");
			int choice = sr.nextInt();
			while (choice != 1 || choice != 2) {
				System.out.print("Please, your choice(1 or 2):");
				choice = sr.nextInt();
			}
			if (choice == 1) {
				SlangEditing(slang);
			} else {
				DefinitionEditing(slang);
			}
		}
	}

	public static void DeleteSlang() {
		System.out.println("Enter Slang word you want to delete:");
		String slang = sr.nextLine();
		slang = slang.toUpperCase();
		if (!dictHashMap.containsKey(slang)) {
			System.out.println("This slang doesn't match!!!");
		} else {
			System.out.println("Do you want to delete?(y/n)");
			String as = sr.nextLine();
			as = as.toLowerCase();
			if (as == "y") {
				dictHashMap.remove(slang);
				System.out.println("Deleted!!!");
			}else {
				System.out.println("Not deleted!!");
			}
		}
	}
	
	public static void ResetDict() {
		dictHashMap.clear();
		if(dictHashMap.isEmpty()) {
			ReadFile("slang.txt");
			System.out.println("Reset successfully!!");
		}else {
			System.out.println("Reset fail!!!");
		}
	}
	
	public static String RandomSlang() {
		Object[] slang = dictHashMap.keySet().toArray();
		return slang[new Random().nextInt(slang.length)].toString();
	}

	public static void SlangOfTheDay() {
		String slang = RandomSlang();
		HashMap<String, List<String>> tmpMap = new HashMap<String, List<String>>();
		tmpMap.put(slang, dictHashMap.get(slang));
		System.out.println("Random slang word for today:");
		PrintSlangWord(tmpMap);
		
	}
	
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
		AddSlang();
		EditSlang();
		DeleteSlang();
		FindBySlang();
		RandomSlang();
		ResetDict();

	}
}
