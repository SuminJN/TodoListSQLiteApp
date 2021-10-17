package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import com.google.gson.Gson;
import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {

	public static void createItem(TodoList l) {
		
		String title, desc, category, due_date, location, priority;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 추가]\n"
				+ "제목 > ");
		
		title = sc.next();
		if (l.isDuplicate(title)) {
			System.out.printf("제목이 중복됩니다!");
			return;
		}
		sc.nextLine();
		
		System.out.print("카테고리 > ");
		category = sc.nextLine().trim();
		
		System.out.print("내용 > ");
		desc = sc.nextLine().trim();
		
		System.out.print("마감일자 > ");
		due_date = sc.nextLine().trim();
		
		System.out.print("장소 > ");
		location = sc.nextLine().trim();
		
		System.out.print("우선순위(A > B > C) > ");
		priority = sc.nextLine().trim();
		
		
		TodoItem t = new TodoItem(title, desc, category, due_date, location, priority);
		if(l.addItem(t)>0)
			System.out.println("추가되었습니다.");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 삭제]\n"
				+ "삭제할 항목의 번호를 입력하시오 > ");
		
		String str = sc.nextLine();
		String[] sp = str.split(" ");
		
		for(String index : sp) {
			if(l.deleteItem(Integer.parseInt(index)) > 0)
				System.out.println(Integer.parseInt(index) + "번 항목이 삭제되었습니다.");
			
		}
	}


	public static void updateItem(TodoList l) {
		
		String new_title, new_desc, new_category, new_due_date, new_location, new_priority;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 수정]\n"
				+ "수정할 항목의 번호를 입력하시오 > ");
		int index = sc.nextInt();
		
		System.out.print("새 제목 > ");
		new_title = sc.next().trim();
		
		System.out.print("새 카테고리 > ");
		new_category = sc.next().trim();
		sc.nextLine();
		
		System.out.print("새 내용 > ");
		new_desc = sc.nextLine().trim();
		
		System.out.print("새 마감일자 > ");
		new_due_date = sc.nextLine().trim();
		
		System.out.print("새 장소 > ");
		new_location = sc.nextLine().trim();
		
		System.out.print("우선순위(A > B > C) > ");
		new_priority = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(new_title, new_desc, new_category, 
				new_due_date, new_location, new_priority);
		t.setId(index);
		if(l.updateItem(t) > 0)
			System.out.println("수정되었습니다.");
		
	}

	public static void listAll(TodoList l) {
		System.out.printf("[전체 목록, 총 %d개]\n", l.getCount());
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}
	
	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.printf("[전체 목록, 총 %d개]\n", l.getCount());
		for (TodoItem item : l.getOrderedList(orderby, ordering)) {
			System.out.println(item.toString());
		}
	}
	
	public static void listCompletedAll(TodoList l) {
		int count=0;
		for (TodoItem item : l.getCompletedList()) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("\n총 %d개의 항목이 완료되었습니다.\n", count);
	}
	
	public static void listCateAll(TodoList l) {
		int count=0;
		for (String item : l.getCategories()) {
			System.out.print(item + " ");
			count++;
		}
		System.out.printf("\n총 %d개의 카테고리가 등록되어 있습니다.\n", count);
			
	}
	
	public static void findList(TodoList l, String keyword) {
		int count=0;
		for (TodoItem item : l.getList(keyword)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("총 %d개의 항목을 찾았습니다.\n", count);
	}
	
	public static void findCateList(TodoList l, String cate) {
		int count=0;
		for(TodoItem item : l.getListCategory(cate)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("\n총 %d개의 항목을 찾았습니다.\n", count);
		
	}

	public static void completeItem(TodoList l) {
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[완료체크]\n"
				+ "체크할 항목의 번호들을 입력하시오 > ");
		
		String str = sc.nextLine();
		String[] sp = str.split(" ");
		
		for(String index : sp) {
			if(l.completeItem(Integer.parseInt(index)) > 0);
				System.out.println(Integer.parseInt(index) + "번 항목을 완료체크하였습니다.");
		}
	}
	
	public static void writeJson(TodoList l) {
		
		Gson gson = new Gson();
		
		try {
			FileWriter writer = new FileWriter("todolist.txt");
			
			for (TodoItem item : l.getList()) {
				String jsonStr = gson.toJson(item);
				writer.write(jsonStr);
				writer.write("\n");
			}
			writer.close();
			System.out.println("파일에 저장되었습니다!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void readJson(TodoList l) {
		Gson gson = new Gson();
		String jsonStr = null;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("todolist.txt"));
			while( (jsonStr = br.readLine()) != null ) {
				TodoItem item = gson.fromJson(jsonStr, TodoItem.class);
				System.out.println(item);
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
