# Forest Simulation Engine – Logic & Dual Persistence

This project is a Java-based simulation engine built to demonstrate advanced Object-Oriented Programming (OOP) and sophisticated data management. It focuses on creating a modular 10x10 grid world where diverse entities interact through a unified interface system.

### 🛠 Technical Blueprint
* **Object-Oriented Architecture**: Leverages **Interfaces** and **Polymorphism** to decouple the core engine logic from specific entity behaviors (like Robots or Trees), ensuring the system is easily scalable.
* **Modular Design**: Developed with a strict **Separation of Concerns**, following standard Java project conventions to keep business logic independent from the user interface.
* **Complex State Management**: Utilizes `HashMap` coordinate tracking to manage spatial data and entity interactions efficiently across the grid.

### 💾 Why Dual Persistence?
To explore different architectural needs and data handling strategies, I implemented two distinct ways to save the world state:

1. **JSON Implementation**: Used for transparency and easy debugging. It allows for human-readable configuration of the grid coordinates and entity attributes.
2. **Binary Serialization**: Chosen for its "snapshot" capability. This allows the JVM to rebuild complex object graphs which includes Interfaces and Abstract classes, instantly without the need for manual parsing.



### 🚀 How to Run
To run the application from your terminal, navigate to the `app` directory and use:

```bash
./gradlew run
