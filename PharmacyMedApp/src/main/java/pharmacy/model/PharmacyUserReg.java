package pharmacy.model;

public class PharmacyUserReg {
	int id;
String name;
long mobileNo;
String mail;
String password;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public long getMobileNo() {
	return mobileNo;
}
public void setMobileNo(long mobileNo) {
	this.mobileNo = mobileNo;
}
public String getMail() {
	return mail;
}
public void setMail(String mail) {
	this.mail = mail;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public PharmacyUserReg(int id, String name, long mobileNo, String mail, String password) {
	super();
	this.id = id;
	this.name = name;
	this.mobileNo = mobileNo;
	this.mail = mail;
	this.password = password;
}
public PharmacyUserReg() {
	
}
@Override
public String toString() {
	return "PharmacyUserReg [id=" + id + ", name=" + name + ", mobileNo=" + mobileNo + ", mail=" + mail + ", password="
			+ password + "]";
}

}
