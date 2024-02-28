## Web application written in java with the goal of meeting the guidelines for developing secure applications in java.

### Security Guidelines:
- Lifetime of sensitive data
- Cookies
- Cross-site scripting (XSS)
- Session http
- https: SSL/TLS with Apache Tomcat
- Accessing the DBMS via SSL/TLS
- File Upload with Apache Tika
  - Checking file format and content, and possibly removing malicious scripts
- Sql Injection
- Prepared Statement via JDBC
- Password Management
  - Passwords are concatenated to a salt and then hashed using SHA-256 algorithm 
- Keys & Encryption
  - Cookies are encrypted through AES encryption in CBC mode by applying eventual padding
### Software used
- Eclipse with Apache Tomcat
- Database: MySQL
