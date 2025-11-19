# Mountain Race Registration - Web App Demo

A full-stack web application built with Java and the Spring Framework that allows users to browse and register for mountain racing events. This project was developed to practice and demonstrate backend development skills, database modeling, and building a dynamic web interface.

## ✨ Features

*   **User Registration & Login:** Secure user authentication and session management.
*   **Event Browser:** View a list of all available mountain competitions and races with key details.
*   **Event Registration:** Users can sign up for specific events.
*   **User Dashboard:** Registered users can see a list of events they are signed up for.
*   **Admin Panel:** A separate view for administrators to add, edit, or delete events/runners/control points...

---

## 🛠️ Tech Stack

*   **Backend:** Java, Spring Boot, Spring MVC, Spring Security
*   **Database:** MySQL / PostgreSQL (with Spring Data JPA & Hibernate)
*   **Frontend:** Thymeleaf, HTML5, CSS3
*   **Build Tool:** Apache Maven
*   **Server:** Embedded Apache Tomcat

---

## 🚀 How to Run

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/marcos-blancorodri/java-spring-webapp-demo.git
    ```

2.  **Configure the database:**
    *   Open the `src/main/resources/application.properties` file.
    *   Update the `spring.datasource.url`, `spring.datasource.username`, and `spring.datasource.password` properties with your local database credentials.

3.  **Build and run the application:**
    ```bash
    mvn spring-boot:run
    ```

4.  Open your web browser and navigate to `http://localhost:8080`.

---

## 📝 Lessons Learned

This was one of my foundational projects for learning the Spring ecosystem. I gained significant experience in RESTful principles, ORM with Hibernate, and managing dependencies with Maven. If I were to rebuild this project today, I would focus on implementing a more robust testing suite with JUnit/Mockito and containerizing the application with Docker for easier deployment.
