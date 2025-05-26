# Real-Time Ticket Management System

A comprehensive full-stack application that simulates a real-time ticket booking system with concurrent vendor and customer operations. Built with Spring Boot backend and React frontend, featuring multi-threading, real-time updates, and complete CRUD operations.

## 🎯 Project Overview

This system simulates a real-world ticket booking scenario where:
- **Vendors** automatically release tickets at configurable intervals
- **Customers** purchase tickets concurrently 
- **System administrators** monitor and control the entire process in real-time
- **Database persistence** maintains all configurations and ticket records

## 🚀 Features

### Backend Features
- **Multi-threaded Architecture**: Separate threads for vendor ticket release and customer purchases
- **Configurable System Parameters**: Customizable ticket counts, release rates, and capacity limits
- **Real-time Monitoring**: Live system status and ticket pool tracking
- **Database Integration**: JPA/Hibernate with MySQL for data persistence
- **RESTful API**: Complete CRUD operations with proper HTTP status codes
- **Thread-Safe Operations**: Synchronized methods for concurrent access
- **Transaction Management**: Atomic operations for data consistency

### Frontend Features
- **Real-time Dashboard**: Live updates of system metrics and ticket counts
- **Configuration Management**: Create, activate, and manage multiple system configurations
- **Manual Controls**: Direct ticket addition and purchase capabilities
- **Responsive Design**: Modern UI with Tailwind CSS
- **Real-time Status Updates**: Automatic polling for live system monitoring
- **Interactive Ticket Management**: View and manage individual tickets

## 🏗️ Architecture

### Backend Architecture (Spring Boot)
```
├── Entity Layer (JPA Entities)
│   ├── Configuration.java
│   ├── Ticket.java
│   └── TicketStatus.java (Enum)
├── DTO Layer (Data Transfer Objects)
│   ├── ConfigurationDto.java
│   ├── TicketDto.java
│   ├── TicketPoolStatusDto.java
│   └── SystemControlDto.java
├── Repository Layer (JPA Repositories)
│   ├── ConfigurationRepository.java
│   └── TicketRepository.java
├── Service Layer (Business Logic)
│   ├── ConfigurationService.java
│   ├── TicketService.java
│   └── TicketSystemService.java
└── Controller Layer (REST APIs)
    ├── ConfigurationController.java
    ├── TicketController.java
    └── TicketSystemController.java
```

### Frontend Architecture (React)
```
├── Components
│   ├── Dashboard (System monitoring)
│   ├── ConfigurationForm (Create configurations)
│   ├── ConfigurationList (Manage configurations)
│   └── TicketManager (Ticket operations)
├── State Management
│   ├── System status tracking
│   ├── Configuration management
│   └── Real-time updates
└── API Integration
    ├── RESTful API calls
    ├── Error handling
    └── Loading states
```

## 🛠️ Technology Stack

### Backend
- **Java 11+**
- **Spring Boot 2.7+**
- **Spring Data JPA**
- **MySQL Database**
- **Lombok** (Boilerplate reduction)
- **Maven** (Dependency management)

### Frontend
- **React 18**
- **Tailwind CSS**
- **Lucide React** (Icons)
- **Modern JavaScript (ES6+)**

## 📋 Prerequisites

- Java 11 or higher
- Node.js 16+ and npm
- MySQL 8.0+
- Maven 3.6+

## 🚀 Getting Started

### Database Setup
```sql
CREATE DATABASE ticket_system;
CREATE USER 'ticket_user'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON ticket_system.* TO 'ticket_user'@'localhost';
FLUSH PRIVILEGES;
```

### Backend Setup
1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/ticket-system.git
   cd ticket-system
   ```

2. **Configure database connection**
   ```properties
   # src/main/resources/application.properties
   spring.datasource.url=jdbc:mysql://localhost:3306/ticket_system
   spring.datasource.username=ticket_user
   spring.datasource.password=password
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```

3. **Run the Spring Boot application**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
   Backend will start on `http://localhost:8080`

### Frontend Setup
1. **Navigate to frontend directory and install dependencies**
   ```bash
   cd frontend
   npm install
   ```

2. **Start the React development server**
   ```bash
   npm start
   ```
   Frontend will start on `http://localhost:3000`

## 📡 API Endpoints

### Configuration Management
- `GET /api/configurations` - List all configurations
- `POST /api/configurations` - Create new configuration
- `GET /api/configurations/{id}` - Get specific configuration
- `GET /api/configurations/active` - Get active configuration
- `PUT /api/configurations/{id}/activate` - Activate configuration
- `DELETE /api/configurations/{id}` - Delete configuration

### Ticket Operations
- `GET /api/tickets` - List all tickets
- `POST /api/tickets/add/{count}` - Add tickets to pool
- `POST /api/tickets/purchase` - Purchase ticket
- `GET /api/tickets/available` - Get available tickets
- `GET /api/tickets/status` - Get ticket pool status
- `DELETE /api/tickets/clear` - Clear all tickets

### System Control
- `POST /api/system/start` - Start the ticket system
- `POST /api/system/stop` - Stop the ticket system
- `GET /api/system/status` - Get system status
- `GET /api/system/running` - Check if system is running

## 🔧 Configuration Parameters

| Parameter | Description | Default |
|-----------|-------------|---------|
| `totalTickets` | Initial number of tickets | 100 |
| `ticketReleaseRateMs` | Vendor release interval (ms) | 2000 |
| `customerRetrievalRateMs` | Customer purchase interval (ms) | 1500 |
| `maxTicketCapacity` | Maximum tickets in pool | 50 |
| `configName` | Configuration identifier | - |

## 🎮 Usage

1. **Create Configuration**: Set up system parameters including ticket counts and timing intervals
2. **Activate Configuration**: Make a configuration active for system operation
3. **Start System**: Begin automated vendor and customer operations
4. **Monitor Dashboard**: View real-time statistics and system status
5. **Manual Operations**: Manually add tickets or simulate purchases
6. **Stop System**: Halt all automated operations

## 🧵 Concurrency Features

- **Thread-Safe Operations**: Synchronized methods for ticket pool access
- **Concurrent Execution**: Separate threads for vendors and customers
- **Race Condition Prevention**: Proper synchronization mechanisms
- **Atomic Transactions**: Database operations with transaction management

## 📊 Monitoring & Analytics

- Real-time ticket pool status
- Purchase and availability metrics
- System performance indicators
- Configuration effectiveness tracking

## 🔒 Error Handling

- Comprehensive exception handling
- User-friendly error messages
- Graceful system degradation
- Transaction rollback on failures

## 🚀 Future Enhancements

- **WebSocket Integration**: Real-time push notifications
- **User Authentication**: Role-based access control
- **Advanced Analytics**: Historical data analysis
- **Load Balancing**: Multi-instance deployment support
- **Notification System**: Email/SMS alerts
- **Performance Metrics**: Detailed system analytics

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

## 👨‍💻 Author

**Your Name**
- GitHub: [@yourusername](https://github.com/helithjay)
- LinkedIn: [Your LinkedIn](https://www.linkedin.com/in/helith-jayasuriya-3720252b7/)
- Email: helithjayasuriya77@gmail.com

## 🙏 Acknowledgments

- Spring Boot community for excellent documentation
- React team for the powerful frontend framework
- Contributors and testers who helped improve this project
