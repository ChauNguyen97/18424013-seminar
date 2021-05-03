package seminar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
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

	//https://stackoverflow.com/questions/2979383/java-clear-the-console
	public final static void clearScreen() {
		try {
			if (System.getProperty("os.name").contains("Windows"))
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			else
				Runtime.getRuntime().exec("clear");
		} catch (IOException | InterruptedException ex) {
		}
	}
	//https://stackoverflow.com/questions/6032118/make-the-console-wait-for-a-user-input-to-close
	public static void PauseTest() {
		System.out.println("Press Any Key To Continue...");
		String nextLine = new java.util.Scanner(System.in).nextLine();
	}
	//https://mkyong.com/java/how-to-read-utf-8-encoded-data-from-a-file-java/
	public static void ReadFile(String file_name) {

		try {
			File file = new File(file_name);
			InputStreamReader fileReader = new InputStreamReader(new FileInputStream(file), "utf8");

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

	public static void WriteFile(String file_name) {
		try {
			File f = new File(file_name);
			FileWriter fw = new FileWriter(f);
			for (String key : dictHashMap.keySet()) {
				fw.write(key + "`");
				List<String> tmp = dictHashMap.get(key);
				int i = 0;
				for (i = 0; i < tmp.size() - 1; i++) {
					fw.write(tmp.get(i) + "| ");
				}
				fw.write(tmp.get(i) + "\n");
			}

			fw.close();

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
		System.out.println("Enter Slang word:");
		String slang = sr.next();

		hisList.add(slang);
		HashMap<String, List<String>> tempMap = new HashMap<String, List<String>>();
		slang = slang.toLowerCase();
		for (String tmp : dictHashMap.keySet()) {
			if (tmp.toLowerCase().contains(slang)) {
				tempMap.put(tmp, dictHashMap.get(tmp));
			}
		}
		if (tempMap.isEmpty()) {
			System.out.println("Not Found!!!");
		} else {
			PrintSlangWord(tempMap);
		}
		PauseTest();
		MenuSearch();

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
		PauseTest();
		MenuSearch();
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
		PauseTest();
		MenuSearch();
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
		PauseTest();
		MenuDictionary();
	}

	public static void SlangEditing(String slang) {
		List<String> val = new ArrayList<String>();
		val = dictHashMap.get(slang);
		dictHashMap.remove(slang);
		System.out.println("Enter new Slang word:");
		slang = sr.next();
		dictHashMap.put(slang, val);
		System.out.println("Edit successfully!!");
		PauseTest();
		MenuDictionary();
	}

	public static void DefinitionEditing(String slang) {
		List<String> val = new ArrayList<String>();
		val = dictHashMap.get(slang);
		HashMap<String, List<String>> tmpMap = new HashMap<String, List<String>>();
		tmpMap.put(slang, val);
		PrintSlangWord(tmpMap);
		System.out.println("The definition you want to change:");
		String deChange = sr.next();
		deChange = deChange.toLowerCase();
		for (String s : val) {
			if (deChange.contains(s.toLowerCase())) {
				System.out.println("1.Delete or 2.Edit definition?");
				System.out.print("Your choise:");
				int choice = sr.nextInt();
				val.remove(s);
				if (choice == 2) {
					System.out.println("Enter new definition:");
					s = sr.next();
				}
				val.add(s);
				dictHashMap.put(slang, val);
				System.out.println("Edit successfully!!");
				return;
			}
		}
		dictHashMap.put(slang, val);
		System.out.println("Edit successfully!!");
	}

	public static void EditSlang() {
		System.out.println("Enter Slang word you want to edit:");
		String slang = sr.next();
		slang = slang.toUpperCase();
		if (!dictHashMap.containsKey(slang)) {
			System.out.println("This slang doesn't match!!!");
		} else {
			System.out.println("What do you want to change?");
			System.out.println("1. Slang word");
			System.out.println("2. Definition");
			System.out.print("Your choice:");
			int choice = sr.nextInt();
			if (choice == 1) {
				SlangEditing(slang);
			} else {
				DefinitionEditing(slang);
			}
		}
		PauseTest();
		MenuDictionary();
	}

	public static void DeleteSlang() {
		System.out.println("Enter Slang word you want to delete:");
		String slang = sr.next();
		slang = slang.toUpperCase();
		if (!dictHashMap.containsKey(slang)) {
			System.out.println("This slang doesn't match!!!");
		} else {
			System.out.println("Do you want to delete?(1. yes/0. no)");
			int as = sr.nextInt();
			if (as == 1) {
				dictHashMap.remove(slang);
				System.out.println("Deleted!!!");
			} else {
				System.out.println("Not deleted!!");
			}
		}
		PauseTest();
		MenuDictionary();
	}

	public static void ResetDict() {
		dictHashMap.clear();
		if (dictHashMap.isEmpty()) {
			ReadFile("slang.txt");
			System.out.println("Reset successfully!!");
		} else {
			System.out.println("Reset fail!!!");
		}
		PauseTest();
		MenuDictionary();
	}

	public static String RandomSlang(HashMap<String, List<String>> tmpMap) {
		Object[] slang = tmpMap.keySet().toArray();
		return slang[new Random().nextInt(slang.length)].toString();
	}

	public static void SlangOfTheDay() {
		String slang = RandomSlang(dictHashMap);
		HashMap<String, List<String>> tmpMap = new HashMap<String, List<String>>();
		tmpMap.put(slang, dictHashMap.get(slang));
		System.out.println("------------------------------");
		System.out.println("Random slang word for today:");
		PrintSlangWord(tmpMap);
		System.out.println("------------------------------");
	}

	public static void Game4Slang() {
		// clone hashmap
		HashMap<String, List<String>> temp = new HashMap<String, List<String>>(dictHashMap);

		Random random = new Random();
		List<String> choice = new ArrayList<String>();
		for (int i = 0; i < 4; i++) {
			String randomString = RandomSlang(temp);
			choice.add(randomString);
			temp.remove(randomString); // dam bao dap an khong trung
		}

		String question = choice.get(random.nextInt(choice.size()));

		System.out.println("Slang word: " + question);
		System.out.println("What is the definition of the above Slang word? Choose your answer (1-4)");
		int number = 1;
		for (String s : choice) {
			String answer = dictHashMap.get(s).toString();
			System.out.println(number + ". " + answer);
			number++;
		}
		System.out.print("Your answer: ");
		int right = sr.nextInt();
		if (choice.get(right - 1).equals(question)) {
			System.out.println(">>> Congratulation! You won the game!!!");
		} else {
			System.out.println(">>> You losed the game!!!");
		}
		PauseTest();
		MenuGame();
	}

	public static void Game4Definition() {
		// clone hashmap
		HashMap<String, List<String>> temp = new HashMap<String, List<String>>(dictHashMap);

		Random random = new Random();
		List<String> choice = new ArrayList<String>();
		for (int i = 0; i < 4; i++) {
			String randomString = RandomSlang(temp);
			choice.add(randomString);
			temp.remove(randomString); // dam bao dap an khong trung
		}

		String question = choice.get(random.nextInt(choice.size()));

		System.out.println("Definition: " + dictHashMap.get(question).toString());
		System.out.println("What is the Slang word of the above definition? Choose your answer (1-4)");
		int number = 1;
		for (String s : choice) {
			System.out.println(number + ". " + s);
			number++;
		}
		System.out.print("Your answer: ");
		int right = sr.nextInt();
		if (choice.get(right - 1).equals(question)) {
			System.out.println(">>> Congratulation! You won the game!!!");
		} else {
			System.out.println(">>> You losed the game!!!");
		}
		PauseTest();
		MenuGame();
	}

	public static void MainMenu() {
		System.out.println("---------MENU---------");
		int num;
		System.out.println("1. Search.");
		System.out.println("2. Change Dictionary.");
		System.out.println("3. Play fun game.");
		System.out.println("0. Exit.");
		System.out.print("Your choice: ");
		num = sr.nextInt();

		switch (num) {
		case 0: {
			WriteHistory("history.txt");
			WriteFile("dict_slangs.txt");
			System.exit(0);
			;
			break;
		}
		case 1: {
			MenuSearch();
			;
			break;
		}
		case 2: {
			MenuDictionary();
			;
			break;
		}
		case 3: {
			MenuGame();
			;
			break;
		}
		default:
			System.out.println("Please, choice 0 to 3!!!");
			MainMenu();
		}
	}

	public static void MenuSearch() {
		int num;
		clearScreen();
		System.out.println("--------MENU SEARCH--------");
		System.out.println("1. Search by Slang word.");
		System.out.println("2. Search by Definition.");
		System.out.println("3. History search.");
		System.out.println("0. Back to MAIN MENU.");
		System.out.print("Your choice: ");
		num = sr.nextInt();

		switch (num) {
		case 0: {
			MainMenu();
			break;
		}
		case 1: {
			FindBySlang();
			break;
		}
		case 2: {
			FindByDefinition();
			break;
		}
		case 3: {
			ShowHistory();
			break;
		}
		default:
			System.out.println("Please, choice 0 to 3!!!");
			MenuSearch();
		}

	}

	public static void MenuDictionary() {
		int num;
		clearScreen();
		System.out.println("--------MENU DICTIONARY--------");
		System.out.println("1. Add Slang word.");
		System.out.println("2. Edit Slang word.");
		System.out.println("3. Delete Slang word.");
		System.out.println("4. Reset dictionary.");
		System.out.println("0. Back to MAIN MENU.");
		System.out.print("Your choice: ");
		num = sr.nextInt();

		switch (num) {
		case 0: {
			MainMenu();
			break;
		}
		case 1: {
			AddSlang();
			break;
		}
		case 2: {
			EditSlang();
			break;
		}
		case 3: {
			DeleteSlang();
			break;
		}
		case 4: {
			ResetDict();
			break;
		}
		default:
			System.out.println("Please, choice 0 to 4!!!");
			MenuDictionary();
		}

	}

	public static void MenuGame() {
		int num;
		clearScreen();
		System.out.println("--------MENU GAME--------");
		System.out.println("1. Find Definition by Slang word.");
		System.out.println("2. Find Slang word by Definition.");
		System.out.println("0. Back to MAIN MENU.");
		System.out.print("Your choice: ");
		num = sr.nextInt();

		switch (num) {
		case 0: {
			MainMenu();
			break;
		}
		case 1: {
			Game4Slang();
			break;
		}
		case 2: {
			Game4Definition();
			break;
		}
		default:
			System.out.println("Please, choice 0 to 2!!!");
			MenuGame();
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ReadFile("dict_slangs.txt");
		if (dictHashMap.isEmpty()) {
			ReadFile("slang.txt");
		}
		ReadHistory("history.txt");
		SlangOfTheDay();

		MainMenu();

	}
}
