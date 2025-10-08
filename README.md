# 🎯 Resume Matcher

> An intelligent AI-powered system that analyzes resumes against job descriptions, providing compatibility scores and actionable improvement suggestions using Apache Lucene and Tika.

[![Under Development](https://img.shields.io/badge/status-under%20development-orange.svg)](https://github.com/blackwolf2902/resume-matcher)
[![Java](https://img.shields.io/badge/Java-21-blue.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-green.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

## 📋 Overview

Resume Matcher is a full-stack Spring Boot application that helps job seekers optimize their resumes for specific job descriptions. Using Apache Tika for document parsing and Apache Lucene for text analysis, it automatically extracts skills, calculates weighted compatibility scores, and provides data-driven insights to improve resume effectiveness.

## ✨ Features

### Currently Implemented
- 📄 **Resume Upload & Parsing**: Supports PDF and DOCX formats using Apache Tika
- 🔍 **Advanced Keyword Extraction**: Powered by Apache Lucene's EnglishAnalyzer with stop-word filtering
- 🎯 **Weighted Skill Matching**: Smart scoring algorithm with skill importance weighting
- 📊 **Compatibility Scoring**: Real-time matching between resume and job description
- 💾 **H2 Database Integration**: In-memory storage for development and testing
- 🔐 **RESTful API**: Clean endpoint design following REST principles

### Planned Features
- 💡 **AI-Powered Suggestions**: Actionable recommendations for resume improvement
- 📈 **Visual Analytics Dashboard**: Graphical representation of match scores and skill gaps
- 👤 **User Authentication**: JWT-based secure user management
- 📱 **Frontend Interface**: React-based responsive UI
- 🎨 **Skill Gap Analysis**: Identification of missing keywords with priority ranking
- 📧 **Export Reports**: PDF generation of analysis results

## 🏗️ Architecture

Clean layered architecture pattern with separation of concerns:

```
com.resume.jsb/
├── controller/         # REST API endpoints
│   └── ResumeController.java
├── service/           # Business logic layer
│   └── ResumeService.java
├── repository/        # Data access layer
│   └── ResumeRepository.java
├── model/            # Entity definitions
│   └── Resume.java
└── util/             # Helper utilities
    ├── KeywordExtractor.java
    └── ResumeScorer.java
```

**Tech Stack:**
- **Backend**: Java 21, Spring Boot 3.5.6, Spring Data JPA, Hibernate
- **Database**: H2 (development), MySQL (production-ready)
- **Document Parsing**: Apache Tika 2.9.0
- **Text Analysis**: Apache Lucene 9.9.0 (Core, Analysis, QueryParser)
- **Build Tool**: Maven
- **Utilities**: Lombok for boilerplate reduction

## 🚀 Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.6+
- IDE (VS Code, IntelliJ IDEA, or Eclipse)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/blackwolf2902/resume-matcher.git
   cd resume-matcher
   ```

2. **Build the project**
   ```bash
   ./mvnw clean install
   ```
   
   *For Windows:*
   ```bash
   mvnw.cmd clean install
   ```

3. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Access the application**
   - API Base URL: `http://localhost:8080`
   - H2 Console: `http://localhost:8080/h2-console`
     - JDBC URL: `jdbc:h2:mem:testdb`
     - Username: `sa`
     - Password: *(leave empty)*

## 📡 API Endpoints

### Resume Upload
```http
POST /resumes/upload
Content-Type: multipart/form-data

Parameters:
  - file: MultipartFile (PDF/DOCX)
  - candidateName: String

Response: Resume object with parsed content
```

**Example using cURL:**
```bash
curl -X POST http://localhost:8080/resumes/upload \
  -F "file=@resume.pdf" \
  -F "candidateName=John Doe"
```

### Resume Matching & Scoring
```http
POST /resumes/match
Content-Type: application/x-www-form-urlencoded

Parameters:
  - jdText: String (Job Description)
  - resumeText: String (Resume Content)

Response:
{
  "score": 75.5,
  "jdSkills": ["java", "spring boot", "mysql"],
  "resumeSkills": ["java", "python", "spring boot"],
  "matchedSkills": ["java", "spring boot"]
}
```

**Example using cURL:**
```bash
curl -X POST http://localhost:8080/resumes/match \
  -d "jdText=Looking for Java Spring Boot developer with MySQL experience" \
  -d "resumeText=Experienced Java developer with Spring Boot and Python skills"
```

### Get All Resumes
```http
GET /resumes

Response: List of all uploaded resumes
```

## 🎯 Scoring Algorithm

The weighted scoring system uses a comprehensive skill library:

| Skill Category | Examples | Weight Range |
|---------------|----------|--------------|
| **High Demand** | Spring Boot, Kubernetes, AWS, Machine Learning | 1.3 - 1.5 |
| **Standard** | Java, React, MySQL, Docker | 1.0 - 1.3 |
| **Foundational** | Git, HTML, CSS | 0.8 - 0.9 |

**Score Calculation:**
```
Score = (Σ Matched Skill Weights / Σ JD Skill Weights) × 100
```

## 🗂️ Database Schema

```sql
CREATE TABLE resume (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    candidate_name VARCHAR(255) NOT NULL,
    file_path VARCHAR(500),
    keywords VARCHAR(1000),
    text_content CLOB
);
```

## 📁 Project Structure

```
resume-matcher/
├── src/
│   ├── main/
│   │   ├── java/com/resume/jsb/
│   │   │   ├── JsbApplication.java
│   │   │   ├── controller/
│   │   │   ├── model/
│   │   │   ├── repository/
│   │   │   ├── service/
│   │   │   └── util/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── uploads/                    # Resume storage directory
├── .gitignore
├── pom.xml
└── README.md
```

## 🛠️ Configuration

### Switch to MySQL (Production)

Update `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/resume_matcher
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=update
```

Add MySQL dependency to `pom.xml`:
```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>
```

## 🛣️ Roadmap

- [x] Project setup with Spring Boot 3.5.6
- [x] Database schema and JPA entities
- [x] Resume upload functionality with Tika parsing
- [x] Apache Lucene keyword extraction
- [x] Weighted skill matching algorithm
- [x] REST API endpoints for upload and matching
- [ ] User authentication (JWT)
- [ ] Frontend React application
- [ ] Skill gap analysis with recommendations
- [ ] Visual analytics dashboard
- [ ] PDF report generation
- [ ] Unit and integration tests
- [ ] Docker containerization
- [ ] CI/CD pipeline setup

## 🧪 Testing

Run tests using Maven:
```bash
./mvnw test
```

## 🤝 Contributing

Contributions are welcome! This project is under active development.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Development Guidelines
- Follow Java naming conventions
- Use Lombok annotations to reduce boilerplate
- Write unit tests for new features
- Update documentation for API changes

## 🐛 Known Issues

- Large file uploads (>10MB) may timeout
- Lucene indexing not yet optimized for concurrent requests
- Limited support for non-English resumes

## 📝 Development Status

**Current Phase**: Core Backend Implementation (MVP)

**Last Updated**: January 2025

**Current Version**: 0.0.1-SNAPSHOT

## 👨‍💻 Author

**Arumugam N**
- GitHub: [@blackwolf2902](https://github.com/blackwolf2902)
- LinkedIn: [arumugam-nallasivan](https://linkedin.com/in/arumugam-nallasivan)
- Portfolio: [arumugam.qzz.io](https://arumugam.qzz.io)
- Email: nmaru2904@gmail.com

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- [Apache Tika](https://tika.apache.org/) - Document parsing
- [Apache Lucene](https://lucene.apache.org/) - Text analysis and search
- [Spring Boot](https://spring.io/projects/spring-boot) - Application framework
- [Project Lombok](https://projectlombok.org/) - Boilerplate reduction

## 📚 Resources

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Apache Tika Guide](https://tika.apache.org/2.9.0/gettingstarted.html)
- [Lucene Tutorial](https://lucene.apache.org/core/9_9_0/core/overview-summary.html)

---

⭐ **Star this repository** if you find it helpful!

📧 **Questions or Suggestions?** Open an issue or reach out directly!