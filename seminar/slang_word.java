package seminar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
        	File file =new File(file_name);
        	FileReader fileReader = new FileReader(file);
        	
        	BufferedReader bReader = new BufferedReader(fileReader);
        	String line;
        	while((line = bReader.readLine()) != null) {
        		if(line.contains("`")) {
        			List<String> tmpList = new ArrayList<String>();
        			String[] s = line.split("`");
        			if (s[1].contains("|")) {
						String[] tmp = s[1].split("\\|");// "Welcome |to| java" -> [Welcome, to, java]
						for(int i = 0; i < tmp.length; i++) {
							tmp[i] = tmp[i].trim();
						}
						tmpList = Arrays.asList(tmp);
					}
        			else {
						tmpList.add(s[1]);
					}
        			dictHashMap.put(s[0], tmpList);
        		}
        		
            }
        	fileReader.close();
        	bReader.close();
        }
        catch (Exception ex) {
            System.out.println("Error: "+ex);
        }
    }

	public static void FindBySlang() {
		System.out.println("Enter a slang word: ");
		String key = sr.next();
		
		hisList.add(key);
		key = key.toUpperCase();
		if(!dictHashMap.containsKey(key)) {
			System.out.println("Not Found!!!");
		}
		else {
			List<String> list = dictHashMap.get(key);
			System.out.println("Definition:");
			for(String s: list) {
				System.out.println("-"+s);
			}
		}
		
	}
	
	public slang_word() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ReadFile("F:\\Java\\seminar\\src\\seminar\\slang.txt");
		//for (String name: dictHashMap.keySet()) {
		//String key = name.toString();
		//String value = dictHashMap.get(name).toString();
		//System.out.println(key + " " + value);}
		FindBySlang();
		
	}
}


