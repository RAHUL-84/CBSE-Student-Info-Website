# 📚 CBSE Student Information Website

This is a Java Servlet-based web application that allows you to manage and view CBSE student academic details like name, roll number, and subject-wise marks.

---

## 🔧 Tech Stack

- Java Servlet.  
- JDBC (Java Database Connectivity).  
- MySQL.  
- HTML / CSS.  
- Apache Tomcat (for deployment).  

---

## ✨ Features

- ➕ Add new student data to the database.  
- 🔍 Search student by roll number.  
- 📃 View student name, roll number, and subject-wise marks.  
- 📦 Export `.war` file and deploy using Apache Tomcat.  
- 🧠 Real-time data fetching from MySQL using JDBC.  

---

## ⚙️ How It Works (Project Flow)

### 1. **Add Student**
- HTML form is used to take student details (roll number, name, marks).
- On form submission, `AddStudentServlet.java` is called.
- This servlet uses **JDBC** to insert data into MySQL.

---

### 🔍 Search Student

- User enters **roll number** in the HTML form  
- On submit, `SearchStudentServlet.java` is called  
- Servlet uses **JDBC** to fetch matching student record from **MySQL**  
- Data is displayed on browser using `PrintWriter` and basic HTML    

---

### 🗑️ Delete Student

- User enters **roll number** of student to delete  
- On submit, `DeleteStudentServlet.java` is triggered  
- Servlet uses **JDBC** to run `DELETE` query in **MySQL**  
- Displays confirmation message on browser using HTML  

### ✏️ Update Student

- User enters **roll number** to update student details  
- Existing data is fetched and shown in an editable HTML form  
- On submit, `UpdateStudentServlet.java` is triggered  
- Servlet updates the data in **MySQL** using **JDBC**  
- Displays success/failure message on browser  
