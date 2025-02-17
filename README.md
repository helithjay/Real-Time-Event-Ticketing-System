# Real-Time Event Ticketing System

## Introduction
The **Real-Time Event Ticketing System** is a multi-threaded Java application designed to simulate a real-time ticketing process. It ensures thread-safe operations through a shared ticket pool, allowing vendors to release tickets and customers to retrieve them. The system demonstrates Java concurrency and synchronization concepts using JavaFX for the graphical user interface.

## Features
- **Configuration Management**: Easily set parameters such as total tickets, release rate, retrieval rate, and maximum capacity.
- **Multi-threading**: Simultaneous operations by vendor and customer threads.
- **Thread-Safe Implementation**: Uses `Vector` for safe ticket pool management.
- **User-Friendly GUI**: Simple graphical interface for configuration and control.

## Technologies Used
- **Programming Language**: Java (JDK 21+)
- **GUI Framework**: JavaFX
- **Configuration Management**: Text file-based configuration
- **Build Tool**: Maven (for dependency management)

## System Requirements
- Java Development Kit (JDK) 21 or later
- Maven for dependency management
- IntelliJ IDEA (recommended) or any Java-supporting IDE

## Project Structure
```
src/
|-- com.ticketingsystem/
|   |-- TicketingGUI.java
|   |-- TicketPool.java
|   |-- Vendor.java
|   |-- Customer.java
|   |-- Configuration.java
|   |-- ConfigurationManager.java
pom.xml
README.md
```

## Setup Instructions
### 1. Clone the Repository
```sh
git clone https://github.com/yourusername/Real-Time-Ticketing-System.git
cd Real-Time-Ticketing-System
```

### 2. Open in IntelliJ IDEA
- Open IntelliJ IDEA and load the project.
- Ensure the correct JDK (21 or above) is configured.

### 3. Install Dependencies
Maven will automatically download the required dependencies such as JavaFX libraries.

### 4. Run the Application
- Locate `TicketingGUI.java`.
- Right-click and select **Run** to launch the application.

## Usage Instructions
### 1. Configure Parameters
- Enter values for total tickets, release rate (ms), retrieval rate (ms), and maximum capacity.
- Click **Save Configuration** to store these settings.

### 2. Start the Simulation
- Click **Start** to begin the ticketing process.
- Observe the real-time operations in the status area.

### 3. Stop the Simulation
- Click **Stop** to halt the threads safely.

## License
This project is licensed under the MIT License.

## Author
Helith Jayasuriya - helithjay

