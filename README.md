<div align="center">
   
   ![GitHub Repo stars](https://img.shields.io/github/stars/Pantelijaa/Gym-App?style=flat&logo=github&color=%23d05613)
   ![GitHub forks](https://img.shields.io/github/forks/Pantelijaa/Gym-App?style=flat&logo=github&color=%234b9607)
   ![GitHub watchers](https://img.shields.io/github/watchers/Pantelijaa/Gym-App?style=flat&logo=github&color=%23143ee8)
   
</div>

<a id="readme-top"></a>

# Gym-App

## About The Project

This project contains simple app for tracking members and memberships. The test database with generated QR codes is located in [Gym-App/tree/master/src/main/resources/com/gymapp/db](https://github.com/Pantelijaa/Gym-App/tree/master/src/main/resources/com/gymapp/db)

### Built With
* [![Java][Java-badge]][Java-url]
* [![CSS][CSS-badge]][CSS-url]
* [![SQLite][SQLite-badge]][SQLite-url]
* [![Maven][Maven-badge]][Maven-url]
* [![Hibernate][Hibernate-badge]][Hibernate-url]

### Architecture Layers
Project is split into multiple layers:
|     Layer     	| Responsibility                                                                                                                         	|
|:-------------:	|----------------------------------------------------------------------------------------------------------------------------------------	|
|  **Database** 	| Stores any amount of data, can be created/opened independently from application itself. Forced to have specific `Tables` and `Columns` 	|
|   **Entity**  	| Java Object representation of `Tables` from `Database`. **Hibernate** implementation of **Jakarta Persistence (JPA)** is used for Mapping.   	|
|    **DAO**    	| Performs basic `CRUD` operations on **Entity** for accesing data                                                                       	|
|  **Service**  	| Performs more complex algorithms on data                                                                                               	|
| **Contoller** 	| Handles end user UI/UX                                                                                                         	|

### Structure Tree
Below is a overview of the project structure:

```
├── src/main
│   ├── java
│   │   ├── com/gymapp
│   │   │   ├── components                      -> reusable components logic
│   │   │   │   ├── BottomBar.java
│   │   │   │   ├── SidePanel.java
│   │   │   ├── controllers                     -> scenes logic
│   │   │   │   ├── AddController.java
│   │   │   │   ├── DashboardController.java
│   │   │   │   ├── DBSelectorController.java
│   │   │   │   ├── ListController.java
│   │   │   │   ├── MembershipController.java
│   │   │   │   ├── ScanController.java
│   │   │   │   ├── ScanViewerController.java
│   │   │   ├── converters                      -> data converters to satisfy SQLite limitations
│   │   │   │   ├── LocalDateConverter.java
│   │   │   │   ├── PeriodConverter.java
│   │   │   │   ├── YearMonthCovnerter.java
│   │   │   ├── dao                             -> DAO layer
│   │   │   │   ├── Dao.java
│   │   │   │   ├── GymMemberDaoImpl.java
│   │   │   │   ├── HistoryDaoImpl.java
│   │   │   │   ├── MembershipDaoImpl.java
│   │   │   ├── entity                          -> entity layer
│   │   │   │   ├── GymMember.java
│   │   │   │   ├── History.java
│   │   │   │   ├── Membership.java
│   │   │   ├── enums                     
│   │   │   │   ├── FxmlViewEnum.java           -> abstaction to more intuitive scene changes
│   │   │   │   ├── MembershipEnum.java         -> assures proper membership format
│   │   │   ├── helpers
│   │   │   │   ├── PropertiesHelper.java       -> handles app.config state
│   │   │   ├── service                         -> service layer
│   │   │   │   ├── GymMemberService.java
│   │   │   │   ├── HistoryService.java
│   │   │   │   ├── MembershipService.java
│   │   │   │   ├── QRService.java
│   │   │   ├── App.java                        -> main app initialization file
│   │   ├── module-info.java
│   ├── resources
│   │   ├── com/gymapp
│   │   │   ├── components                      -> reusable components fxml files
│   │   │   │   ├── bottomBar.fxml
│   │   │   │   ├── sidePanel.fxml
│   │   │   ├── css
│   │   │   ├── db
│   │   │   │   ├── test.db                     -> database for testing puropose
│   │   │   ├── images
│   │   │   ├── QR                              -> QR codes associated with test database
│   │   │   ├── views                           -> scenes fxml files
│   │   │   │  ├── add.fxml
│   │   │   │  ├── dashboard.fxml
│   │   │   │  ├── dbSelector.fxml
│   │   │   │  ├── list.fxml
│   │   │   │  ├── membership.fxml
│   │   │   │  ├── scan.fxml
│   │   │   │  ├── scanViewer.fxml
│   │   ├── META-INF
│   │   │   ├── app.config                      -> configuration file for saving app state
│   │   │   ├── persistence.xml                 -> presistence settings
├── .gitignore
├── README.md
├── pom.xml
```

## Getting Started

### Prerequisites

Make sure you have **Git**, **JDK** and **Maven** installed on your machine.

### Installation

1. **Clone the repository**
   
   ```bash
   git clone https://github.com/Pantelijaa/Gym-App.git
   ```

2. **Test the project**
   ```bash
   mvn test
   ```

3. **Resolve dependencies before running the application**
   
   ```bash
   mvn dependency:resolve
   ```
   
4. **Run the application**
   
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




