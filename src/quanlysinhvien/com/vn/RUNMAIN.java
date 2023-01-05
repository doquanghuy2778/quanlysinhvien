package quanlysinhvien.com.vn;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class RUNMAIN {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		StudentManager chucNang = new StudentManager();
		String choice = "";
		String input = "";
		boolean exit = false;

		List<Student> thongTinSV = new ArrayList<>();
		List<String> listMSV = new ArrayList<>();

		boolean checkMenu = false;
		StudentManager studentManager = new StudentManager();
		Scanner src = new Scanner(System.in);
		String choiseYN = null;
		
		do {
		
			chucNang.hienThiMenu();
			System.out.print("Mời bạn nhập chức năng: ");
			choice = scanner.nextLine();

			if (!choice.equals("1") && !choice.equals("2") && !choice.equals("3") && !choice.equals("4")
					&& !choice.equals("5") && !choice.equals("6") && !choice.equals("7") && !choice.equals("8")
					&& !choice.equals("9") && !choice.equals("10") && !choice.equals("11") && !choice.equals("12")) {
				do {
					System.out.println("Bạn đã nhập sai fomat, vui lòng nhập lại từ 1 - 12");
					chucNang.hienThiMenu();
					System.out.print("Mời bạn nhập chức năng: ");
					choice = scanner.nextLine();

				} while (!choice.equals("1") && !choice.equals("2") && !choice.equals("3") && !choice.equals("4")
						&& !choice.equals("5") && !choice.equals("6") && !choice.equals("7") && !choice.equals("8")
						&& !choice.equals("9") && !choice.equals("10") && !choice.equals("11") && !choice.equals("12"));

			}

			switch (choice) {
			case "2":

				do {
					System.out.println("-------------------------------");

					chucNang.nhapThongTin();
					System.out.print("Bạn có muốn quay trở lại menu ? Nhấn Y để quay về hoặc N để ở lại chức năng: ");
					input = scanner.nextLine();

				} while (input.equalsIgnoreCase("n"));

				break;

			case "1":

				do {
					System.out.println("-------------------------------");

					chucNang.hienThiThongTin();
					System.out.print("Bạn có muốn quay trở lại menu ? Nhấn Y để quay về hoặc N để ở lại chức năng: ");
					input = scanner.nextLine();

				} while (input.equalsIgnoreCase("n"));
				break;
			case "3":

				do {
					System.out.println("-------------------------------");
					chucNang.chucNang3();
					System.out.print("Bạn có muốn quay trở lại menu ? Nhấn Y để quay về hoặc N để ở lại chức năng: ");
					input = scanner.nextLine();

				} while (input.equalsIgnoreCase("n"));
				break;
			case "4":

				do {
					// Copy code runmain vÃ o Ä‘Ã¢y
					System.out.println("-------------------------------");
					chucNang.chucNang4();
					System.out.print("Bạn có muốn quay trở lại menu ? Nhấn Y để quay về hoặc N để ở lại chức năng: ");
					input = scanner.nextLine();

				} while (input.equalsIgnoreCase("n"));
				break;
			case "5": 
				do {
					System.out.println("Chuc nang 5");
//					Chức năng 5 : Tìm và hiển thị thông tin Sinh Viên theo Tên Sinh VIên
					studentManager.chucNang5();
					System.out.print("Bạn có muốn quay trở lại menu ? Nhấn Y để quay về hoặc N để ở lại chức năng: ");
					input = scanner.nextLine();
				} while (input.equalsIgnoreCase("n"));				
				break;
			

			case "6": {
//				Chức năng 6 : Hiển thị thông tin các sinh viên Giởi theo thứ tự giảm dần điểm trung bình.
				do {
					System.out.println("Chuc nang 6");
					studentManager.chucNang6();
					System.out.print("Bạn có muốn quay trở lại menu ? Nhấn Y để quay về hoặc N để ở lại chức năng: ");
					input = scanner.nextLine();
				} while (input.equalsIgnoreCase("n"));
				break;
			}
			case "7":

				do {
					// 
					System.out.println("-------------------------------");
					chucNang.sapxepkha();
					System.out.print("Bạn có muốn quay trở lại menu ? Nhấn Y để quay về hoặc N để ở lại chức năng: ");
					input = scanner.nextLine();
				} while (input.equalsIgnoreCase("n"));
				break;
			case "8":
				do {
					// Copy code runmain vÃ o Ä‘Ã¢y
					System.out.println("-------------------------------");
					chucNang.sapxepTB();
					System.out.print("Bạn có muốn quay trở lại menu ? Nhấn Y để quay về hoặc N để ở lại chức năng: ");
					input = scanner.nextLine();
				} while (input.equalsIgnoreCase("n"));
				break;
				
			case "9":
				do {
					// Copy code runmain vÃ o Ä‘Ã¢y
					System.out.println("-------------------------------");
					chucNang.chucnang9();
					System.out.print("Bạn có muốn quay trở lại menu ? Nhấn Y để quay về hoặc N để ở lại chức năng: ");
					input = scanner.nextLine();

				} while (input.equalsIgnoreCase("n"));
				break;
			case "10":
				do {
					System.out.println("-------------------------------");
					chucNang.chucNang10();
					System.out.print("Bạn có muốn quay trở lại menu ? Nhấn Y để quay về hoặc N để ở lại chức năng: ");
					input = scanner.nextLine();

				} while (input.equalsIgnoreCase("n"));
				break;
			case "11":
				do {
					System.out.println("-------------------------------");
					chucNang.sapXepTheoTen();
					System.out.print("Bạn có muốn quay trở lại menu ? Nhấn Y để quay về hoặc N để ở lại chức năng: ");
					input = scanner.nextLine();
				} while (input.equalsIgnoreCase("n"));
				break;
			case "12":
				System.out.println("-------------------------------");
				System.out.println("Chương trình kết thúc!");
				System.exit(0);
				break;
			}
		} while (choice.equals("1") || choice.equals("2") || choice.equals("3") || choice.equals("4")
				|| choice.equals("5") || choice.equals("6") || choice.equals("7") || choice.equals("8")
				|| choice.equals("9") || choice.equals("10") || choice.equals("11") || choice.equals("12"));

	
	
	}

}
