package quanlysinhvien.com.vn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;


public class StudentManager {


	Scanner scanner = new Scanner(System.in);

	public static ArrayList<Student> danhSach;

	public StudentManager() {
		this.danhSach = new ArrayList<Student>();
	}

	String MSV = "";
	String lastName = "";
	String firstName = "";
	String gender = "";
	String classStudent = "";
	float mathsPoint = 0;
	float physicalPoint = 0;
	float chemistryPoint = 0;
	float AVG = 0;
	boolean checkThongTinNhap = false;
	boolean checkYN = false;
	boolean Check = false;
	String choiseYN = "";

	String Host = "localhost";
	String Port = "3306";
	String DB_Name = "practice";

	String DB_URL = "jdbc:mysql://" + Host + ":" + Port + "/" + DB_Name;
	String User_name = "root";
	String Password = "thitlonbachi";
	Connection conn = null;
	Connection conn1 = null;
	Connection conn2 = null;

	List<Student> hocsinhY = new ArrayList<>();
	List<Student> thongTinSV = new ArrayList<>();

	// Hiển thị menu
	public void hienThiMenu() {
		System.out.println("---------MENU---------");
		System.out.println("Chức năng 1 : Hiển Thị Thông Tin Toàn Bộ Sinh Viên.");
		System.out.println("Chức năng 2 : Nhập thông tin Sinh Viên.");
		System.out.println("Chức năng 3 : Chỉnh sửa thông tin 1 Sinh Viên theo MSV.");
		System.out.println("Chức năng 4 : Tìm và hiển thị thông tin Sinh Viên theo MSV.");
		System.out.println("Chức năng 5 : Tìm và hiển thị thông tin Sinh Viên theo Tên Sinh Viên");
		System.out.println(
				"Chức năng 6 : Hiển thị thông tin các sinh viên Giỏi theo thứ tự giảm dần điểm trung bình. ( DTB >= 8.0)");
		System.out.println(
				"Chức năng 7 : Hiển thị thông tin các sinh viên Khá theo thứ tự giảm dần điểm trung bình.( 8.0 > DTB >= 6.5)");
		System.out.println(
				"Chức năng 8 : Hiển thị thông tin các sinh viên Trung Bình theo thứ tự giảm dần điểm trung bình.(6.5 > DTB >= 5.0)");
		System.out.println(
				"Chức năng 9 : Hiển thị thông tin các sinh viên Yếu theo thứ tự giảm dần điểm trung bình.( DTB < 5.0)");
		System.out.println("Chức năng 10 : Xóa thông tin SV theo MSV.");
		System.out.println("Chức năng 11 : Hiển Thị Thông Tin Toàn Bộ Sinh Viên theo thứ tự Anpha-B của FristName.");
		System.out.println("Chức năng 12 : Kết thúc chương trình");
	}

	// chức năng 2: Nhập thông tin sinh viên
	public void nhapThongTin() {
		try {
			conn = DriverManager.getConnection(DB_URL, User_name, Password);
			if (conn != null) {

				Statement stmt = null;
				stmt = conn.createStatement();

				Check = false;
				// Nhập mã sinh viên
				do {

					System.out.print("Nhập MSV theo cú pháp MSVabc trong đó abc là 3 số của mã sinh viên: ");
					MSV = scanner.nextLine();

					// kiểm tra
					Pattern pattern = Pattern.compile("^MSV[0-9]{3}$");
					if (pattern.matcher(MSV).find()) {
						if (MSV.equalsIgnoreCase("MSV000")) {
							System.out.println("Bạn đã nhập sai định dạng, vui lòng nhập lại: ");
						} else {
							Check = false;
						}

					} else {
						System.out.println("Bạn đã nhập sai định dạng, vui lòng nhập lại");
						Check = true;
					}
					// kiểm tra trùng
					String select_pre = "select count(MSV) from practice.student where MSV = ?";
					PreparedStatement preStatement = conn.prepareStatement(select_pre);
					preStatement.setString(1, MSV);
					ResultSet resultSet = preStatement.executeQuery();
					while (resultSet.next()) {
						if (resultSet.getInt(1) != 0) {
							System.out.println("Mã sinh viên đã tồn tại, vui lòng nhập lại");
							Check = true;
						}
					}

				} while (Check);

				// Nhập họ

				do {
					Check = false;
					System.out.print("Nhập họ sinh viên (viết hoa chữ cái đầu): ");
					lastName = scanner.nextLine().trim();
					Pattern pattern = Pattern.compile("^[A-Z]\\w{0,}([ ][A-Z]\\w{0,}){0,}$");
					if (!pattern.matcher(lastName).find()) {
						System.out.println("Bạn đã nhập sai định dạng, vui lòng nhập lại");
						Check = true;
					}
					if (lastName.length() > 20) {
						System.out.println("Họ của bạn quá dài, vui lòng rút gọn lại");
						Check = true;
					}
				} while (Check);
				// xử lý nhập tên

				do {
					Check = false;
					System.out.print("Nhập tên sinh viên (viết hoa chữ cái đầu): ");
					firstName = scanner.nextLine().trim();
					Pattern pattern = Pattern.compile("^[A-Z]\\w{0,}([ ][A-Z]\\w{0,}){0,}$");
					if (!pattern.matcher(firstName).find()) {
						System.out.println("Bạn đã nhập sai định dạng, vui lòng nhập lại");
						Check = true;
					}
					if (firstName.length() > 20) {
						System.out.println("Tên của bạn quá dài, vui lòng rút gọn lại");
						Check = true;
					}
				} while (Check);

				// xử lý nhập giới tính
				do {
					Check = false;
					System.out.print("Chọn giới tính của sinh viên: 1. Nữ hoặc 2. Nam: ");
					String genderCheck = scanner.nextLine().trim();
					Pattern pattern = Pattern.compile("^[12]{1}$");
					if (pattern.matcher(genderCheck).find()) {
						if (genderCheck.equals("1")) {
							gender = "Nữ";
						} else {
							gender = "Nam";
						}
					} else {
						System.out.println("Bạn chọn chưa đúng, vui lòng nhập lại: 1. Nữ hoặc 2. Nam");
						Check = true;
					}

				} while (Check);

				// Xử lý nhập lớp
				do {
					Check = false;
					System.out.print("Nhập lớp sinh viên: ");
					classStudent = scanner.nextLine().trim();
					Pattern pattern = Pattern.compile("^$");
					if (pattern.matcher(classStudent).find()) {
						System.out.println("Bạn chưa nhập thông tin, vui lòng nhập lại");
						Check = true;
					}
				} while (Check);

				// xử lý nhập điểm
				do {
					Check = false;
					System.out.print("Nhập điểm toán (0-10): ");
					try {
						mathsPoint = Float.valueOf(scanner.nextLine());
					} catch (Exception e) {
						System.out.println("Bạn đã nhập sai định dạng, vui lòng nhập lại");
						Check = true;
					}
					if ((mathsPoint > 10) || (mathsPoint < 0)) {
						System.out.println("Bạn chưa nhập thông tin hoặc nhập sai định dạng, vui lòng nhập lại");
						Check = true;
					}
				} while (Check);

				do {
					Check = false;
					System.out.print("Nhập điểm lý (0-10): ");
					try {
						physicalPoint = Float.valueOf(scanner.nextLine());
					} catch (Exception e) {
						System.out.println("Bạn đã nhập sai định dạng, vui lòng nhập lại");
						Check = true;
					}
					if ((physicalPoint > 10) || (physicalPoint < 0)) {
						System.out.println("Bạn chưa nhập thông tin hoặc nhập sai định dạng, vui lòng nhập lại");
						Check = true;
					}
				} while (Check);
				do {
					Check = false;
					System.out.print("Nhập điểm hóa (0-10): ");
					try {
						chemistryPoint = Float.valueOf(scanner.nextLine());
					} catch (Exception e) {
						System.out.println("Bạn đã nhập sai định dạng, vui lòng nhập lại");
						Check = true;
					}
					if ((chemistryPoint > 10) || (chemistryPoint < 0)) {
						System.out.println("Bạn chưa nhập thông tin hoặc nhập sai định dạng, vui lòng nhập lại");
						Check = true;
					}
				} while (Check);
//				AVG = Math.round((mathsPoint + physicalPoint + chemistryPoint) / 3 * 10);
//				AVG = AVG / 10;

				// insert vào database
				String insert_pre = "insert into practice.student values (?,?,?,?,?,?,?,?,?)";
				PreparedStatement preStatement = conn.prepareStatement(insert_pre);
				preStatement.setString(1, MSV);
				preStatement.setString(2, firstName);
				preStatement.setString(3, lastName);
				preStatement.setString(4, classStudent);
				preStatement.setString(5, gender);
				preStatement.setFloat(6, mathsPoint);
				preStatement.setFloat(7, physicalPoint);
				preStatement.setFloat(8, chemistryPoint);
				preStatement.setFloat(9, AVG);
				preStatement.executeUpdate();

			} else {
				System.out.println("kết nối thất bại !!!");
			}
			conn.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	// chức năng 1: hiển thị thông tin
	public void formattable() {
		System.out.println("Danh sách sinh viên: ");
		System.out.format("|%-7s|%-20s|%-20s|%-7s|%-7s|%-10s|%-10s|%-10s|%-6s|", "MSV", "LastName", "FirstName",
				"Class", "Gender", "MathsPoint", "PhysPoint", "ChePoint", "AVG");
		System.out.println();
		System.out.println(
				"-----------------------------------------------------------------------------------------------------------");
	}

	public void hienThiThongTin() {
		try {
			conn = DriverManager.getConnection(DB_URL, User_name, Password);
			if (conn != null) {

				Statement stmt = null;
				stmt = conn.createStatement();
				String select = "select * from practice.student";

				ResultSet resultSet = stmt.executeQuery(select);

				if (resultSet.next() == false) {
					System.out.println("Danh sách hiện tại chưa có thông tin");

				} else {
					formattable();
					do {

						System.out.format("|%-7s|%-20s|%-20s|%-7s|%-7s|%-10s|%-10s|%-10s|%-6.2f|",
								resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
								resultSet.getString(4), resultSet.getString(5), resultSet.getFloat(6),
								resultSet.getFloat(7), resultSet.getFloat(8), resultSet.getFloat(9));
						System.out.println();
					} while (resultSet.next());

				}

			} else {
				System.out.println("Kết nối thất bại");
			}
			conn.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	// Chức năng 3
	public void chucNang3() {
		boolean Check = false;
		String choice4;
		String choice5 = null;

		do {
			System.out.println("Nhập mã sinh viên cần chỉnh sửa:");
			String MSV = scanner.nextLine().toUpperCase();
			try {
				Connection conn = null;
				conn = DriverManager.getConnection(DB_URL, User_name, Password);
				Statement stmt = null;
				stmt = conn.createStatement();
				String COUNT = "SELECT * FROM practice.student where MSV = '" + MSV + "';";
				ResultSet resultSet = stmt.executeQuery(COUNT);
				if (resultSet.next()) {
					Check = false;

					System.out.printf(
							"----------------------------------------------------------------------------------------------------------%n");
					System.out.printf(
							"                           THÔNG TIN SINH VIÊN CÓ MÃ SINH VIÊN LÀ: " + MSV + " %n");
					System.out.printf(
							"----------------------------------------------------------------------------------------------------------%n");
					System.out.printf("| %-10S | %-10S | %-6S | %-6S | %-9S | %8S | %8S | %8S | %6S |%n",
							"Mã sinh viên", "Họ và tên đệm", "Tên", "Lớp", "Giới tính", "Điểm toán", "Điểm lý",
							"Điểm hóa", "Điểm TB");
					System.out.printf(
							"----------------------------------------------------------------------------------------------------------%n");
					System.out.printf("| %-12S | %-13S | %-6S | %-6S | %-9S | %9S | %8S | %8S | %7S |%n",
							resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
							resultSet.getString(4), resultSet.getString(5), resultSet.getString(6),
							resultSet.getString(7), resultSet.getString(8), resultSet.getString(9));

					boolean Check2 = false;
					do {
						System.out.println("Bạn muốn chỉnh sửa gì: (Mời nhập chức năng từ 1 đến 8) ");

						System.out.printf("| %-10s | %-6s | %-6s | %-9s | %8s | %8s | %8s | %6s |%n", "1.Họ và tên đệm",
								"2.Tên", "3Lớp", "4.Giới tính", "5.Điểm toán", "6.Điểm lý", "7.Điểm hóa",
								"8.Không chỉnh sửa");

						choice4 = scanner.nextLine();

						if (!choice4.equals("1") && !choice4.equals("2") && !choice4.equals("3") && !choice4.equals("4")
								&& !choice4.equals("5") && !choice4.equals("6") && !choice4.equals("7")
								&& !choice4.equals("8")) {
							System.out.println("Bạn đã nhập sai. Mời bạn nhập lại!");
							Check2 = true;
						} else {
							Check2 = false;
						}

					} while (Check2);

					boolean check1 = false;
					if (choice4.equalsIgnoreCase("1")) {
						do {
							System.out.println("Mời sửa họ và tên đệm sinh viên:");
							String lastName1 = scanner.nextLine().trim();
							Pattern pattern = Pattern.compile("^{1,}$");
							if (pattern.matcher(lastName1).find()) {
								System.out.println("Bạn nhập chưa đúng. Mời nhập lại:");
								check1 = true;
							} else {
								String UPDATE = "update practice.student" + " set LastName = + '" + lastName1 + "'"
										+ " where MSV = + '" + MSV + "';";
								stmt.executeUpdate(UPDATE);
								check1 = false;
							}
						} while (check1);

					} else if (choice4.equalsIgnoreCase("2")) {
						do {
							System.out.println("Mời sửa tên sinh viên:");
							String firstName1 = scanner.nextLine().trim();
							Pattern pattern = Pattern.compile("^{1,}$");
							if (pattern.matcher(firstName1).find()) {
								System.out.println("Bạn nhập chưa đúng. Mời nhập lại:");
								check1 = true;
							} else {
								String UPDATE = "update practice.student" + " set FirstName = + '" + firstName1 + "'"
										+ " where MSV = + '" + MSV + "';";
								stmt.executeUpdate(UPDATE);
								check1 = false;
							}
						} while (check1);

					} else if (choice4.equalsIgnoreCase("3")) {
						do {
							System.out.println("Mời sửa giới tính sinh viên: Nhập M là Male, Nhập F là Female.");
							String gender1 = scanner.nextLine().trim();
							Pattern pattern = Pattern.compile("^{1,}$");
							if (pattern.matcher(gender1).find()) {
								System.out.println("Bạn nhập chưa đúng. Mời nhập lại:");
								check1 = true;
							}
							if (gender1.equalsIgnoreCase("F")) {
								gender1 = "Female";
								String UPDATE = "update practice.student" + " set Gender = + '" + gender1 + "'"
										+ " where MSV = + '" + MSV + "';";
								stmt.executeUpdate(UPDATE);
								check1 = false;
							} else if (gender1.equalsIgnoreCase("M")) {
								gender1 = "Male";
								String UPDATE = "update practice.student" + " set Gender = + '" + gender1 + "'"
										+ " where MSV = + '" + MSV + "';";
								stmt.executeUpdate(UPDATE);
								check1 = false;
							} else {
								System.out.println("Bạn nhập chưa đúng. Mời nhập lại:");
								check1 = true;
							}
						} while (check1);

					} else if (choice4.equalsIgnoreCase("4")) {
						do {
							System.out.println("Mời sửa lớp sinh viên:");
							String classStudent1 = scanner.nextLine().trim();
							Pattern pattern = Pattern.compile("^{1,}$");
							if (pattern.matcher(classStudent1).find()) {
								System.out.println("Bạn nhập chưa đúng. Mời nhập lại:");
								check1 = true;
							} else {
								String UPDATE = "update practice.student" + " set Class = + '" + classStudent1 + "'"
										+ " where MSV = + '" + MSV + "';";
								stmt.executeUpdate(UPDATE);
								check1 = false;
							}
						} while (check1);

					} else if (choice4.equalsIgnoreCase("5")) {
						do {
							System.out.println("Mời sửa điểm toán sinh viên:");
							try {
								mathsPoint = Float.valueOf(scanner.nextLine());
								if ((mathsPoint > 10) || (mathsPoint < 0)) {
									System.out.println("Bạn nhập chưa đúng. Mời nhập lại:");
									Check = true;
								} else {
									Check = false;
								}
							} catch (Exception e) {
								System.out.println("Bạn nhập chưa đúng. Mời nhập lại:");
								Check = true;
							}
						} while (Check);
						String UPDATE = "update practice.student" + " set MathsPoint = + '" + mathsPoint + "'"
								+ " where MSV = + '" + MSV + "';";
						stmt.executeUpdate(UPDATE);

					} else if (choice4.equalsIgnoreCase("6")) {
						do {
							System.out.println("Mời sửa điểm lý sinh viên:");
							try {
								physicalPoint = Float.valueOf(scanner.nextLine());
								if ((physicalPoint > 10) || (physicalPoint < 0)) {
									System.out.println("Bạn nhập chưa đúng. Mời nhập lại:");
									Check = true;
								} else {
									Check = false;
								}
							} catch (Exception e) {
								System.out.println("Bạn nhập chưa đúng. Mời nhập lại:");
								Check = true;
							}
						} while (Check);
						String UPDATE = "update practice.student" + " set PhysicalPoint = + '" + physicalPoint + "'"
								+ " where MSV = + '" + MSV + "';";
						stmt.executeUpdate(UPDATE);

					} else if (choice4.equalsIgnoreCase("7")) {
						do {
							System.out.println("Mời sửa điểm hóa sinh viên:");
							try {
								chemistryPoint = Float.valueOf(scanner.nextLine());
								if ((chemistryPoint > 10) || (chemistryPoint < 0)) {
									System.out.println("Bạn nhập chưa đúng. Mời nhập lại:");
									Check = true;
								} else {
									Check = false;
								}
							} catch (Exception e) {
								System.out.println("Bạn nhập chưa đúng. Mời nhập lại:");
								Check = true;
							}
						} while (Check);
						String UPDATE = "update practice.student" + " set ChemistryPoint = + '" + chemistryPoint + "'"
								+ " where MSV = + '" + MSV + "';";
						stmt.executeUpdate(UPDATE);

					} else if (choice4.equalsIgnoreCase("8")) {
						System.out.println("Bạn đã chọn không chỉnh sửa.");
						break;

					}

					String SELECT1 = "SELECT * FROM practice.student where MSV = '" + MSV + "';";
					ResultSet resultSet1 = stmt.executeQuery(SELECT1);

					if (resultSet1.next()) {

						AVG = ((resultSet1.getFloat(6) + resultSet1.getFloat(7) + resultSet1.getFloat(8)) / 3);
						AVG = Math.round(AVG * 10);
						AVG = AVG / 10;

					}

					String UPDATE = "update practice.student" + " set AveragePoint = + '" + AVG + "'"
							+ " where MSV = + '" + MSV + "';";
					stmt.executeUpdate(UPDATE);

					do {
						System.out.println("Bạn muốn tiếp tục chỉnh sửa thông tin sinh viên không: Y/N");
						choice5 = scanner.nextLine();

						if (!choice5.equalsIgnoreCase("Y") && !choice5.equalsIgnoreCase("N")) {
							System.out.println("Bạn đã chọn sai. Mời chọn lại.");
						} else if (choice5.equalsIgnoreCase("Y")) {
							System.out.println("Bạn đã chọn tiếp tục chỉnh sửa.");
							Check = true;
						} else if (choice5.equalsIgnoreCase("N")) {
							System.out.println("Bạn đã chọn dừng chỉnh sửa.");
						}
					} while (!choice5.equalsIgnoreCase("Y") && !choice5.equalsIgnoreCase("N"));

				} else {
					Check = true;
					System.out.println("Mã sinh viên bạn nhập không tồn tại. Mời nhập lại:");

				}

				conn.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}

		} while (Check);
	}

	// chức năng 4
	public void chucNang4() {
		String choice6;
		boolean Check = false;
		boolean Check3 = false;

		do {
			System.out.println("Nhập mã sinh viên cần tìm:");
			String MSV = scanner.nextLine().toUpperCase();

			try {
				Connection conn = null;
				conn = DriverManager.getConnection(DB_URL, User_name, Password);

				Statement stmt = null;
				stmt = conn.createStatement();
				String SELECT = "SELECT * FROM practice.student where MSV = '" + MSV + "';";
				ResultSet resultSet = stmt.executeQuery(SELECT);

				if (resultSet.next()) {
					Check = false;

					System.out.printf(
							"----------------------------------------------------------------------------------------------------------%n");
					System.out.printf(
							"                           THÔNG TIN SINH VIÊN CÓ MÃ SINH VIÊN LÀ: " + MSV + " %n");
					System.out.printf(
							"----------------------------------------------------------------------------------------------------------%n");
					System.out.printf("| %-10S | %-10S | %-6S | %-6S | %-9S | %8S | %8S | %8S | %6S |%n",
							"Mã sinh viên", "Họ và tên đệm", "Tên", "Lớp", "Giới tính", "Điểm toán", "Điểm lý",
							"Điểm hóa", "Điểm TB");
					System.out.printf(
							"----------------------------------------------------------------------------------------------------------%n");
					System.out.printf("| %-12S | %-13S | %-6S | %-6S | %-9S | %9S | %8S | %8S | %7S |%n",
							resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
							resultSet.getString(4), resultSet.getString(5), resultSet.getString(6),
							resultSet.getString(7), resultSet.getString(8), resultSet.getString(9));

					Check = false;
				} else {
					System.out.println("Mã sinh viên bạn nhập không tồn tại. Bạn muốn nhập lại không: Y/N");
					choice6 = scanner.nextLine();
					if (choice6.equalsIgnoreCase("Y")) {
						Check = true;

					} else if (choice6.equalsIgnoreCase("N")) {
						Check = false;
					}

				}
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} while (Check);
	}

// chức năng 7
	public void sapxepkha() {
		try {
			conn = DriverManager.getConnection(DB_URL, User_name, Password);
			if (conn != null) { // kết nối thành công
				Statement stmt = conn.createStatement();
				String SXkha = "select * from practice.student where AveragePoint < 8 and  AveragePoint >= 6.5 order by  AveragePoint DESC";
				ResultSet sResultSet = stmt.executeQuery(SXkha);
				if (sResultSet.next()) {
					System.out.println("Danh sách học sinh khá giảm dần ");
					formattable();
					do {
						System.out.format("|%-7s|%-20s|%-20s|%-7s|%-7s|%-10s|%-10s|%-10s|%-6s|",
								sResultSet.getString(1), sResultSet.getString(2), sResultSet.getString(3),
								sResultSet.getString(4), sResultSet.getString(5), sResultSet.getFloat(6),
								sResultSet.getFloat(7), sResultSet.getFloat(8), sResultSet.getFloat(9));
						System.out.println();
					} while (sResultSet.next());
				} else {
					System.out.println("Danh sách chưa có thông tin");
				}
			} else {
				System.out.println("Kết nối thất bại");
			}
			// đóng kết nối
			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

//chức năng 8
	public void sapxepTB() {
		try {
			conn = DriverManager.getConnection(DB_URL, User_name, Password);
			if (conn != null) {
				Statement statement = conn.createStatement();
				String SXTB = "select * from practice.student where AveragePoint < 6.5 and  AveragePoint >= 5 order by  AveragePoint DESC";
				ResultSet sResultSet2 = statement.executeQuery(SXTB);
				if (sResultSet2.next()) {
					System.out.println("Danh sách sinh viên có điểm trung bình giảm dần");
					formattable();
					do {
						System.out.format("|%-7s|%-20s|%-20s|%-7s|%-7s|%-10s|%-10s|%-10s|%-6s|",
								sResultSet2.getString(1), sResultSet2.getString(2), sResultSet2.getString(3),
								sResultSet2.getString(4), sResultSet2.getString(5), sResultSet2.getFloat(6),
								sResultSet2.getFloat(7), sResultSet2.getFloat(8), sResultSet2.getFloat(9));
						System.out.println();
					} while (sResultSet2.next());
				} else {
					System.out.println("Không có dữ liệu");
				}
			} else {
				System.out.println("Kết nối thất bại");
			}
			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

//chức năng 9
	public void chucnang9() {

		// Dùng MySQL
		try {

			// Tạo Kết nối
			conn = DriverManager.getConnection(DB_URL, User_name, Password);
			if (conn != null) {
				System.out.println("Kết nối DataBase thành công ");
				Statement st = conn.createStatement();
				String SAPXEPSVY = "select*from JAVA24_DB.danhsach where aVG <5  and aVG >=0 order by aVG desc";
				ResultSet rs = st.executeQuery(SAPXEPSVY);
				System.out.println("Danh sách sinh viên đạt điểm TB Yếu theo thứ tự giảm dần diểm trung bình là : ");
				// Hiển thị bảng Table
				formattable();
				if (rs.next()) {
					do {
						System.out.format("|%-7s|%-20s|%-20s|%-7s|%-7s|%-10s|%-10s|%-10s|%-6s|\n", rs.getString(1),
								rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getFloat(6),
								rs.getFloat(7), rs.getFloat(8), rs.getFloat(9));
					} while (rs.next());
				} else {
					System.out.printf(
							"                                  Không có sinh viên đạt điểm TB Yếu trong danh sách %n");
				}
				System.out.printf(
						"-----------------------------------------------------------------------------------------------------------%n");
			} else {
				System.out.println("Kết nối thất bại");
			}

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// Chức năng 10
//	Chức năng 10 : Xóa thông tin SV theo MSV.

	public void chucNang10() {

		// Dùng MySQL
		try {
			// Tạo Kết nối
			conn = DriverManager.getConnection(DB_URL, User_name, Password);
			if (conn != null) {
				System.out.println("Kết nối DataBase thành công ");
				System.out.println("Nhập MSV của sinh viên cần xóa: ");
				String inPutMSV = scanner.nextLine().trim().toUpperCase();
				// Tạo Statement
				Statement st = conn.createStatement();
				String CheckSV = "select*from JAVA24_DB.danhsach where MSV = '" + inPutMSV + "'";
				ResultSet rs = st.executeQuery(CheckSV);
				System.out.println("Thông tin của sinh viên cần xóa : ");
				// Hiển thị bảng Table
				formattable();
				if (rs.next()) {
					System.out.format("|%-7s|%-20s|%-20s|%-7s|%-7s|%-10s|%-10s|%-10s|%-6s|\n", rs.getString(1),
							rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getFloat(6),
							rs.getFloat(7), rs.getFloat(8), rs.getFloat(9));
					System.out.printf(
							"-----------------------------------------------------------------------------------------------------------%n");
					System.out.println("Chọn Y :để xóa sinh viên ");
					System.out.println("Chọn N :để hủy xóa và tiếp tục chức năng 10 .");
					choiseYN = scanner.nextLine();
					if (!choiseYN.equalsIgnoreCase("Y") && !choiseYN.equalsIgnoreCase("N")) {
						System.out.println("Bạn chọn k đúng ");
						checkThongTinNhap = true;
					} else if (choiseYN.equalsIgnoreCase("Y")) {
						checkThongTinNhap = false;
						String DELETESV = "DELETE from JAVA24_DB.danhsach where MSV = '" + inPutMSV + "' ";
						int xoaSV = 0;
						xoaSV = st.executeUpdate(DELETESV);
						if (xoaSV != 0) {
							System.out.println("Đã xóa thành công ");
						} else {
							System.err.println("Xóa không thành công ");
						}
					} else {
						System.out.println("Hủy xóa.");
					}
				} else {
					System.err.printf("                               MSV không tồn tại trong Danh sách %n");
					System.out.printf(
							"-----------------------------------------------------------------------------------------------------------%n");
				}
			} else {
				System.out.println("Kết nối thất bại");
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// chức năng 11
	public void sapXepTheoTen() {

		try {
			conn = DriverManager.getConnection(DB_URL, User_name, Password);
			if (conn != null) {

				Statement stmt = null;
				stmt = conn.createStatement();
				String select = "select * from practice.student order by firstName asc";
				ResultSet resultSet = stmt.executeQuery(select);

				if (resultSet.next() == false) {
					System.out.println("Danh sách hiện tại chưa có thông tin");

				} else {
					formattable();
					do {

						System.out.format("|%-7s|%-20s|%-20s|%-7s|%-7s|%-10s|%-10s|%-10s|%-6s|", resultSet.getString(1),
								resultSet.getString(2), resultSet.getString(3), resultSet.getString(4),
								resultSet.getString(5), resultSet.getFloat(6), resultSet.getFloat(7),
								resultSet.getFloat(8), resultSet.getFloat(9));
						System.out.println();

					} while (resultSet.next());

				}

			} else {
				System.out.println("Káº¿t ná»‘i tháº¥t báº¡i");
			}
			conn.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	// chức năng 5
	public void chucNang5() {

		// Dùng MySQL
		try {
			// Tạo Kết nối
			conn = DriverManager.getConnection(DB_URL, User_name, Password);
			if (conn != null) {
				System.out.println("Kết nối DataBase thành công ");
				// Tạo Statement
				Statement st = conn.createStatement();
				System.out.println("Mời bạn nhập tên sinh viên cần tìm kiếm");
				String tenSVNhap = scanner.nextLine().trim();
				String SELECT = "select*from JAVA24_DB.danhsach where lastName like '%" + tenSVNhap
						+ "%' or firtName like '%" + tenSVNhap + "%'";
				ResultSet rs = st.executeQuery(SELECT);
				// Hiển thị bảng Table
				formattable();
				int count = 0;
				if (rs.next()) {
					do {
						count++;
						System.out.format("|%-7s|%-20s|%-20s|%-7s|%-7s|%-10s|%-10s|%-10s|%-6s|\n", rs.getString(1),
								rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getFloat(6),
								rs.getFloat(7), rs.getFloat(8), rs.getFloat(9));
					} while (rs.next());
				}
				if (count == 0) {
					System.out
							.printf("                                       Không có tên sinh viên trong danh sách %n");
				}
				System.out.printf(
						"-----------------------------------------------------------------------------------------------------------%n");
			} else {
				System.out.println("Kết nối thất bại");
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// chức năng 6
	public void chucNang6() {

		// Dùng MySQL
		try {

			// Tạo Kết nối
			conn = DriverManager.getConnection(DB_URL, User_name, Password);
			if (conn != null) {
				System.out.println("Kết nối DataBase thành công ");
				// Tạo Statement
				Statement st = conn.createStatement();
				String SAPXEPSVGIOI = "select*from JAVA24_DB.danhsach where aVG >=8 order by aVG desc";
				ResultSet rs = st.executeQuery(SAPXEPSVGIOI);
				System.out.println(
						"Danh sách sinh viên đạt điểm Trung Bình Giỏi theo thứ tự giảm dần diểm trung bình là : ");
				// Hiển thị bảng Table
				formattable();
				if (rs.next()) {
					do {
						System.out.format("|%-7s|%-20s|%-20s|%-7s|%-7s|%-10s|%-10s|%-10s|%-6s|\n", rs.getString(1),
								rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getFloat(6),
								rs.getFloat(7), rs.getFloat(8), rs.getFloat(9));
					} while (rs.next());
				} else {
					System.err
							.printf("                                     Không có sinh viên giỏi trong danh sách %n");
				}
				System.out.printf(
						"-----------------------------------------------------------------------------------------------------------%n");
			} else {
				System.out.println("Kết nối thất bại ");
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// Check MSV Nhập vào
	private static boolean checkMSVNhap(String MSV, List<Student> thongTinSV) {
		for (int i = 0; i < thongTinSV.size(); i++) {
			if (thongTinSV.get(i).getMSV().equals(MSV)) {
				return true;
			}
		}
		return false;
	}

	// Check Tên sinh viên trong list
	private static boolean checkTenSVNhap(String tenSVNhap, List<Student> thongTinSV) {
		for (int i = 0; i < thongTinSV.size(); i++) {
			if (thongTinSV.get(i).getfirstName().equalsIgnoreCase(tenSVNhap)) {
				System.out.println("Sinh viên :" + thongTinSV.get(i));
				return true;
			}
		}
		return false;
	}


}
