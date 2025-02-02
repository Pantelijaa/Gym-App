<div align="center">
   
   ![GitHub Repo stars](https://img.shields.io/github/stars/Pantelijaa/Gym-App?style=flat&logo=github&color=%23d05613)
   ![GitHub forks](https://img.shields.io/github/forks/Pantelijaa/Gym-App?style=flat&logo=github&color=%234b9607)
   ![GitHub watchers](https://img.shields.io/github/watchers/Pantelijaa/Gym-App?style=flat&logo=github&color=%23143ee8)
   
</div>

<a id="readme-top"></a>

# Gym-App

> [!IMPORTANT]
> Project is still in early develpoment.

## About The Project

Application for tracking subscriptions. Codebase is separated in multiple layers to ensure proper data flow: **`Database` <-> `Entity` <-> `Dao` <-> `Service` <-> `Controller`**.

### Built With
* [![Java][Java-badge]][Java-url]
* [![CSS][CSS-badge]][CSS-url]
* [![SQLite][SQLite-badge]][SQLite-url]
* [![Maven][Maven-badge]][Maven-url]
* [![Hibernate][Hibernate-badge]][Hibernate-url]

### Project Architecture layers

|     Layer     	| Responsibility                                                                                                                         	|
|:-------------:	|----------------------------------------------------------------------------------------------------------------------------------------	|
|  **Database** 	| Stores any amount of data, can be created/opened independently from application itself. Forced to have specific `Tables` and `Columns` 	|
|   **Entity**  	| Java Object representation of `Tables` from `Database`. **Hibernate** implementation of **Jakarta Persistence** (JPA)** is used for Mapping.   	|
|    **Dao**    	| Performs basic `CRUD` operations on **Entity** for accesing data                                                                       	|
|  **Service**  	| Performs more complex algorithms on data                                                                                               	|
| **Contoller** 	| Handles end user UI/UX                                                                                                         	|

## Getting Started

### Prerequisites

Make sure you have **Git**, **JDK** and **Maven** installed on your machine.

### Installation

1. **Clone the repository**
   
   ```bash
   git clone https://github.com/Pantelijaa/Gym-App.git
   ```

2. **Test the Project**
   ```bash
   mvn test
   ```

3. **Resolve dependencies before running the application**
   
   ```bash
   mvn dependency:resolve
   ```
   
4. **Run the Application**
   
   ```bash
   mvn clean javafx:run
   ```

## Usage
* Managing members
* Managing memberships
* Tracking statistics
* QR Code Generation
* QR Code Scanning

## License

Distributed under the MIT license. See `LICENSE` for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

[Java-badge]: https://img.shields.io/badge/Java-%23db8437?style=for-the-badge&logo=openjdk&link=https%3A%2F%2Fwww.java.com%2Fen%2F
[Java-url]: https://www.java.com/en/
[CSS-badge]: https://img.shields.io/badge/CSS-%23663399?style=for-the-badge&logo=css
[CSS-url]: /
[SQLite-badge]: https://img.shields.io/badge/Maven-%23C71A36?style=for-the-badge&logo=apachemaven&link=https%3A%2F%2Fmaven.apache.org%2F
[SQLite-url]: https://maven.apache.org/
[Maven-badge]: https://img.shields.io/badge/SQLite-%23003B57?style=for-the-badge&logo=sqlite&link=https%3A%2F%2Fwww.sqlite.org%2F
[Maven-url]: https://www.sqlite.org
[Hibernate-badge]: https://img.shields.io/badge/-Hibernate-%2359666C?style=for-the-badge&logo=hibernate
[Hibernate-url]: https://hibernate.org/
[GitHub-badge]: https://img.shields.io/badge/GitHub-%23181717?style=for-the-badge&logo=github&link=https%3A%2F%2Fwww.github.com/%2F
[GitHub-url]: https://github.com/




