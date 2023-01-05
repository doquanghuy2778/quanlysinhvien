package quanlysinhvien.com.vn;

import java.util.Objects;


public class Student {


	private String MSV;
	private String lastName;
	private String firstName;
	private String gender;
	private String classStudent;
	private float mathsPoint;
	private float physicalPoint;
	private float chemistryPoint;
	private float AVG;

	public Student(String MSV, String lastName, String firstName, String gender, String classStudent, float mathsPoint,
			float physicalPoint, float chemistryPoint) {

		this.MSV = MSV;
		this.lastName = lastName;
		this.firstName = firstName;
		this.gender = gender;
		this.classStudent = classStudent;
		this.mathsPoint = mathsPoint;
		this.physicalPoint = physicalPoint;
		this.chemistryPoint = chemistryPoint;	
	}

	public Student(String mSV, String lastName, String firstName, String gender, String classStudent, float mathsPoint,
			float physicalPoint, float chemistryPoint, float aVG) {
		super();
		MSV = mSV;
		this.lastName = lastName;
		this.firstName = firstName;
		this.gender = gender;
		this.classStudent = classStudent;
		this.mathsPoint = mathsPoint;
		this.physicalPoint = physicalPoint;
		this.chemistryPoint = chemistryPoint;
		AVG = aVG;
	}

	public String getMSV() {
		return MSV;
	}

	public void setMSV(String MSV) {
		this.MSV = MSV;
	}

	public String getlastName() {
		return lastName;
	}

	public void setlastName(String lastName) {
		this.lastName = lastName;
	}

	public String getfirstName() {
		return firstName;
	}

	public void setfirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getgender() {
		return gender;
	}

	public void setgender(String gender) {
		this.gender = gender;
	}

	public String getclassStudent() {
		return classStudent;
	}

	public void setclassStudent(String classStudent) {
		this.classStudent = classStudent;
	}

	public float getmathsPoint() {
		return mathsPoint;
	}

	public void setmathsPoint(float mathsPoint) {
		this.mathsPoint = mathsPoint;
	}

	public float getphysicalPoint() {
		return physicalPoint;
	}

	public void setphysicalPoint(float physicalPoint) {
		this.physicalPoint = physicalPoint;
	}

	public float getchemistryPoint() {
		return chemistryPoint;
	}

	public void setchemistryPoint(float chemistryPoint) {
		this.chemistryPoint = chemistryPoint;
	}

	public float getAVG() {
		return (physicalPoint + mathsPoint + chemistryPoint) / 3;
	}

	public void setAVG() {
		this.AVG = (physicalPoint + mathsPoint + chemistryPoint) / 3;
	}

	@Override
	public String toString() {
		return  String.format("|%-7s|%-20s|%-20s|%-7s|%-7s|%-10s|%-10s|%-10s|%-6s|", getMSV(), getlastName(), getfirstName(),getclassStudent(), getgender(),getmathsPoint(),getphysicalPoint(), getchemistryPoint(),getAVG());
		}

	public Student(String MSV) {

		this.MSV = MSV;
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(MSV);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		return Objects.equals(MSV, other.MSV);
	}

	

	//


}
