# LIBRARY MANAGEMENT SYSTEM
## About :
Hey there! This is my second semester console based java project. It develops using advanced java concepts it briefly demostrates the use of java concepts such as **OOP**, **access modifiers**, **Exception Handling**, **Package Management**, **Database using JDBC**, to effectively store the data of objects and display it on console user i have built **custom data structure** which works just like **linked list**. Below is the function of each package:
1) data_structure : contains logic of custom data structure
2) models : contains classes of each object to get and set values through getter and setter methods
3) services : contains logic of each operation to be performed on objects like **CRUD operation** and **add into data structure** etc.
4) utils : establish connection with Mysql using JDBC
5) main file : contains main class to run the program
You can use the project from both ends i.e. **user end** and **admin end**. Each has its own set of operations.

## Installation
1. Clone the repo:
   ```sh
   git clone https://github.com/jainamb12/library-management-system.git
   ```
2. Navigate into the project directory:
   ```sh
   cd yourproject
   ```
3. Install Requirements:
   ```sh
   Java Development Kit (JDK) 11+  
Download from: [Oracle JDK](https://www.oracle.com/java/technologies/javase-downloads.html) or [OpenJDK](https://openjdk.org/)
   ```sh
   8.0.30 / PHP 8.0.30 or higher
   Download from: [PHP Official Website](https://www.php.net/downloads)
   ```

## Usage :
Before compiling the project, make sure you have **xampp control panel** install on your system, and **mysql** database is running. Then follow the below steps:
1) Run Mysql and Apache in your xampp control panel check if the port is 3306 and 80 respectively. If not, change them to 3306 and 80 respectively by editing the **httpd.conf** and **my.ini** files. Then restart the server. If still port is occupied , then change the port in **httpd.conf** and **my.ini** files to any other and update in the code where ever required. Then restart the server.

2) open Mysql php :
   ```sh
   http://localhost/phpmyadmin
   ```
3) create a new database **library_db** and import the **library_db.sql** file from the project directory.

4) open the project and add **mysql-connector-jarFile** to **refrence libraries** section.
5) compile the project using 
    ```sh
    javac Main.java
    ```
6) run the project using
    ```sh
    java Main
    ```

## Contributing
Contributions are welcome! Follow these steps:
1. Fork the project.
2. Create a new branch (`git checkout -b feature-branch`).
3. Commit your changes (`git commit -m 'Add feature'`).
4. Push to the branch (`git push origin feature-branch`).
5. Open a pull request.
