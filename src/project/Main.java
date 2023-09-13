package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static int order = 4;
	public static int currentKeys = 0;
	static LinkedList<String> evretirio = new LinkedList<>();
	static BTree<String,Integer> lexiko = new BTree<>();	//arxiko tree order = 4
	static int first = 0;

	public static void main(String[] args) throws FileNotFoundException {
		while(true) {
		//MENU
		System.out.println("\nA)Import file\nB)Search a word from file\nC)Search 100 words");
		Scanner read = new Scanner(System.in);
		String input = read.next();
		switch(input) {
			case "A": functionA();
					  break;
			
			case "B": functionB();
					  break;
			case "C": functionC();
					  break;
		}
		}
		
	  }
	
	public static void functionA() throws FileNotFoundException {
		System.out.println("Type file 1 or 2"); //user must type '1' or '2' !
		Scanner read = new Scanner(System.in);
		String input = read.next();
		openFile(input,first);
	}
	
	public static void functionB() {
		System.out.println("type a word");
		Scanner read = new Scanner(System.in);
		String input = read.next();
		if(lexiko.search(input) == null) {
			System.out.println("Can't found the word: " + input);
		}
		else {
			Integer n = lexiko.search(input);
			int[] array = lexiko.nodeKeys();
			System.out.println(evretirio.get(n) + "\nnodes: " + array[0] + " keys: " + array[1]);
		}
	}
	
	
	public static void openFile(String f, int point) throws FileNotFoundException {
		File file = new File(f+".txt");
	    Scanner input = new Scanner(file); 
	    int count = 0;
	    int pointer = point;
	    while (input.hasNext()) {
	      String word  = input.next();
	      //System.out.println(word);
	      if(lexiko.search(word) == null) { //an den yparxei sto btree
	    	  evretirio.add(data(f+".txt", count));	//insert at linked list
	    	  lexiko.insert(word, pointer);	//insert at btree
	    	  pointer ++;
	      }
	      else {	//an yparxei sto btree
	    	  Integer n = lexiko.search(word);
	    	  evretirio.set(n, evretirio.get(n).concat(data(f+".txt",count)));
	      }
	      count = count + word.length() + 1;
	    }
	    System.out.println("Word count: " + count);
	    first = pointer;
	}
	
	public static String data(String file, int number) {
		return file + " : " + number + "\n";
	}
	
	public static void functionC() throws FileNotFoundException {
		for(int i=0;i<2;i++) {
			if(i==0) {
				order = 10;
			}
			else {
				order = 20;
			}
			LinkedList<String> list = new LinkedList<>();
			BTree<String,Integer> tree = new BTree<>();
			List<String> file1 = new ArrayList<>();
			List<String> file2 = new ArrayList<>();
			int pointer = 0;
			for(int j=0;j<2;j++) {	//load words, create lexiko and evretirio
				String f = null;
				if(j==0) {
					f = "1";
				}
				else {
					f = "2";
				}
				ArrayList<String> words = new ArrayList<>();
				File file = new File(f+".txt");
			    Scanner input = new Scanner(file); 
			    int count = 0;
			    while (input.hasNext()) {
			      String word  = input.next();
			      //System.out.println(word);
			      if(tree.search(word) == null) { //an den yparxei sto btree
			    	  words.add(word);	//add to ArrayList
			    	  list.add(data(f+".txt", count));	//insert at linked list
			    	  tree.insert(word, pointer);	//insert at btree
			    	  pointer ++;
			      }
			      else {	//an yparxei sto btree
			    	  Integer n = tree.search(word);
			    	  list.set(n, list.get(n).concat(data(f+".txt",count)));
			      }
			      count = count + word.length() + 1;
			    }
			    Collections.shuffle(words);
			    if(j==0) {
					file1 = words.subList(0, 49);
				}
				else {
					file2 = words.subList(0, 49);
				}
			}
			
			List<String> join = new ArrayList<>();
			file1.addAll(file2);
			join = file1;
			int nodes = 0;
			int keys = 0;
			for(int s=0;s<join.size();s++) {
				String word = join.get(s);
				Main.currentKeys = 0;
				tree.search(word);
				int[] array = tree.nodeKeys();
				nodes += array[0];
				keys += array[1];
			}
			System.out.println("tree order: " + order + " nodes: " + nodes*1.0/100 + " keys: " + keys*1.0/100);
			
		}
	}
	
	
	}
