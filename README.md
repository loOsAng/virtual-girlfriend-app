# virtual-girlfriend-app

---

# 糖心小伴 - 虚拟女友互动Web应用 (v0.1)
# Sweetheart Companion - Virtual Girlfriend Interactive Web Application (v0.1)

欢迎来到“糖心小伴”项目！这是一个基于Web的虚拟女友互动应用程序，允许用户创建和定制自己的虚拟伴侣，并与之进行多种互动，包括智能聊天。

Welcome to the "Sweetheart Companion" project! This is a web-based virtual girlfriend interactive application that allows users to create and customize their own virtual companions and engage in various interactions, including intelligent chat.

---

## ✨ 项目特性
## ✨ Features

*   **角色创建与定制：** 用户可以设定虚拟伴侣的昵称、年龄、家乡、职业、爱好、性格、生日以及来源作品。
    *   **Character Creation & Customization:** Users can set their virtual companion's nickname, age, hometown, occupation, hobbies, personality, birthday, and source material (e.g., from an anime or game).
*   **信息展示：** 清晰展示当前选中虚拟伴侣的详细档案。
    *   **Information Display:** Clearly displays the detailed profile of the currently selected virtual companion.
*   **多样互动：** 提供多种预设的甜蜜互动选项，如牵手、拥抱、看电影等。
    *   **Diverse Interactions:** Offers various preset sweet interaction options like holding hands, hugging, watching a movie, etc.
*   **智能聊天：** 集成 [DeepSeek API](https://platform.deepseek.com/)，实现与虚拟伴侣的自然语言对话。AI会根据角色的设定进行回应。
    *   **Intelligent Chat:** Integrates with the [DeepSeek API](https://platform.deepseek.com/) to enable natural language conversations with the virtual companion. The AI responds based on the character's defined personality and traits.
*   **多角色管理：** 支持创建和管理多个虚拟伴侣角色。
    *   **Multi-Character Management:** Supports creating and managing multiple virtual companion characters.
*   **聊天记录持久化：** 每个角色的聊天记录都会被保存，切换角色后可查看历史对话。
    *   **Chat History Persistence:** Chat logs for each character are saved, allowing users to view past conversations when switching between characters.
*   **响应式布局：** 界面设计考虑了不同屏幕尺寸的显示效果。
    *   **Responsive Layout:** The interface is designed to adapt to different screen sizes.

---

## 🚀 技术栈
## 🚀 Tech Stack

本项目主要采用以下技术构建：
This project is primarily built using the following technologies:

**后端 (Backend):**

*   **Java 17:** 核心编程语言。
    *   Core programming language.
*   **Spring Boot 3.x:** 用于快速构建企业级Web应用的框架。
    *   Framework for rapidly building enterprise-grade web applications.
    *   **Spring MVC:** 处理Web请求和构建RESTful API。
        *   Handles web requests and builds RESTful APIs.
    *   **Spring Data JPA (Hibernate):** 对象关系映射 (ORM) 和数据库交互。
        *   Object-Relational Mapping (ORM) and database interaction.
    *   **Embedded Tomcat:** 内嵌Web服务器。
        *   Embedded web server.
*   **MySQL:** 关系型数据库，用于持久化存储女友数据和聊天记录。
    *   Relational database for persisting companion data and chat logs.
*   **Maven:** 项目构建和依赖管理工具。
    *   Project build and dependency management tool.
*   **Java HTTP Client:** 用于与 DeepSeek API 进行通信。
    *   Used for communicating with the DeepSeek API.
*   **RESTful API 设计原则。**
    *   RESTful API design principles.

**前端 (Frontend):**

*   **HTML5:** 页面结构。
    *   Page structure.
*   **CSS3:** 页面样式和布局 (包括 Flexbox 和 Grid)。
    *   Page styling and layout (including Flexbox and Grid).
*   **JavaScript (ES6+):** 页面交互逻辑和动态行为。
    *   Page interaction logic and dynamic behavior.
    *   **Fetch API (AJAX):** 实现前后端异步数据通信。
        *   Implements asynchronous data communication between frontend and backend.
    *   **DOM Manipulation:** 动态更新页面内容。
        *   Dynamically updates page content.
*   **Font Awesome:** 提供图标。
    *   Provides icons.
*   **Google Fonts:** 提供自定义字体 (Pacifico, Inter)。
    *   Provides custom fonts (Pacifico, Inter).

**数据交换格式 (Data Exchange Format):**

*   **JSON:** 前后端数据交互以及与外部API通信的主要格式。
    *   Primary format for frontend-backend data exchange and communication with external APIs.

**API 集成 (API Integration):**

*   **DeepSeek API:** 提供聊天机器人的AI能力。
    *   Provides AI capabilities for the chatbot.

**开发与版本控制 (Development & Version Control):**

*   **Git:** 版本控制系统。
    *   Version control system.
*   **GitHub:** 代码托管平台。
    *   Code hosting platform.
*   **IDE:** VS Code
    *   IDE: VS Code
*   **MySQL Workbench:** 数据库管理工具。
    *   Database management tool.

---

## 🔧 环境搭建与运行指南
## 🔧 Environment Setup and Running Guide

在本地运行本项目，你需要以下环境：
To run this project locally, you will need the following environment:

1.  **Java Development Kit (JDK) 17 或更高版本。**
    *   Java Development Kit (JDK) 17 or higher.
2.  **Apache Maven 3.6 或更高版本。**
    *   Apache Maven 3.6 or higher.
3.  **MySQL 服务器 8.0 或更高版本。**
    *   MySQL Server 8.0 or higher.
4.  **Git 客户端。**
    *   Git client.
5.  **一个 DeepSeek API Key。**
    *   A DeepSeek API Key.

**步骤 (Steps)：**

1.  **克隆仓库 (Clone the repository)：**
    ```bash
    git clone https://github.com/loOsAng/virtual-girlfriend-app.git
    cd virtual-girlfriend-app
    ```

2.  **配置 MySQL 数据库 (Configure MySQL Database)：**
    *   启动你的 MySQL 服务器。
        *   Start your MySQL server.
    *   使用 MySQL 客户端 (如 MySQL Workbench) 创建一个新的数据库，例如 `virtual_girlfriend_db`。
        *   Use a MySQL client (e.g., MySQL Workbench) to create a new database, for example `virtual_girlfriend_db`.
        ```sql
        CREATE DATABASE virtual_girlfriend_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
        ```

3.  **配置应用属性 (Configure Application Properties)：**
    *   在 `src/main/resources/` 目录下，复制 `application.properties.templates` 文件并将其重命名为 `application.properties`。
        *   In the `src/main/resources/` directory, copy the `application.properties.templates` file and rename it to `application.properties`.
    *   打开 `application.properties` 文件，修改以下配置项为你自己的真实值：
        *   Open the `application.properties` file and modify the following configuration items with your actual values:
        ```properties
        # DeepSeek API Configuration
        deepseek.api.key=YOUR_DEEPSEEK_API_KEY_HERE
        deepseek.api.endpoint=https://api.deepseek.com/v1/chat/completions

        # MySQL DataSource Configuration
        spring.datasource.url=jdbc:mysql://localhost:3306/virtual_girlfriend_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
        spring.datasource.username=YOUR_MYSQL_USERNAME_HERE # 例如 root (e.g., root)
        spring.datasource.password=YOUR_MYSQL_PASSWORD_HERE
        ```

4.  **构建并运行后端应用 (Build and Run the Backend Application)：**
    在项目根目录下打开终端或 Git Bash，执行以下 Maven 命令：
    Open a terminal or Git Bash in the project root directory and execute the following Maven command:
    ```bash
    ./mvnw spring-boot:run
    ```
    或者 (如果 `mvnw` 不可用或你想用系统安装的 Maven)：
    Or (if `mvnw` is unavailable or you want to use your system-installed Maven):
    ```bash
    mvn spring-boot:run
    ```
    应用成功启动后，后端服务将在 `http://localhost:8080` 上运行。
    After the application starts successfully, the backend service will be running on `http://localhost:8080`.

5.  **访问前端页面 (Access the Frontend Page)：**
    打开你的 Web 浏览器，访问 `http://localhost:8080/`。
    Open your web browser and navigate to `http://localhost:8080/`.

---

## 💡 未来可能的改进方向 (v0.2 及以后)
## 💡 Future Possible Improvements (v0.2 and beyond)

*   **用户认证与授权：** 实现用户注册登录，让每个用户拥有自己的专属伴侣列表。
    *   **User Authentication & Authorization:** Implement user registration and login, allowing each user to have their own exclusive list of companions.
*   **更丰富的女友状态系统：** 引入心情值、好感度、体力值等，影响互动和聊天。
    *   **Richer Companion Status System:** Introduce mood, affection, stamina levels, etc., to influence interactions and chat.
*   **高级互动反馈：** 为互动增加更生动的视觉和声音效果。
    *   **Advanced Interaction Feedback:** Add more vivid visual and sound effects to interactions.
*   **女友形象定制：** 允许用户上传或选择女友的头像/形象。
    *   **Companion Avatar Customization:** Allow users to upload or select avatars/images for their companions.
*   **礼物系统和小游戏。**
    *   **Gift System and Mini-games.**
*   **WebSocket 实现实时聊天更新 (如果多人使用或需要更即时反馈)。**
    *   **WebSocket for Real-time Chat Updates:** (If multi-user or more immediate feedback is needed).
*   **更完善的错误处理和用户提示。**
    *   **More Comprehensive Error Handling and User Prompts.**
*   **单元测试和集成测试。**
    *   **Unit Tests and Integration Tests.**

---

## 👋 贡献
## 👋 Contributing

欢迎提出 Issue 或 Pull Request 来改进本项目。
Feel free to submit Issues or Pull Requests to improve this project.

---

## 📄 许可证
## 📄 License

本项目采用 [MIT 许可证](LICENSE)。
This project is licensed under the [MIT License](LICENSE).

---

希望这个 README 能帮助你更好地了解项目！
Hope this README helps you better understand the project!
