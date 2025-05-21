# virtual-girlfriend-app

# 糖心小伴 - 虚拟女友互动Web应用 (v0.1)

欢迎来到“糖心小伴”项目！这是一个基于Web的虚拟女友互动应用程序，允许用户创建和定制自己的虚拟伴侣，并与之进行多种互动，包括智能聊天。

## ✨ 项目特性

*   **角色创建与定制：** 用户可以设定虚拟伴侣的昵称、年龄、家乡、职业、爱好、性格、生日以及来源作品。
*   **信息展示：** 清晰展示当前选中虚拟伴侣的详细档案。
*   **多样互动：** 提供多种预设的甜蜜互动选项，如牵手、拥抱、看电影等。
*   **智能聊天：** 集成 [DeepSeek API](https://platform.deepseek.com/)，实现与虚拟伴侣的自然语言对话。AI会根据角色的设定进行回应。
*   **多角色管理：** 支持创建和管理多个虚拟伴侣角色。
*   **聊天记录持久化：** 每个角色的聊天记录都会被保存，切换角色后可查看历史对话。
*   **响应式布局：** 界面设计考虑了不同屏幕尺寸的显示效果。

## 🚀 技术栈

本项目主要采用以下技术构建：

**后端 (Backend):**

*   **Java 17:**核心编程语言。
*   **Spring Boot 3.x:** 用于快速构建企业级Web应用的框架。
    *   **Spring MVC:** 处理Web请求和构建RESTful API。
    *   **Spring Data JPA (Hibernate):** 对象关系映射 (ORM) 和数据库交互。
    *   **Embedded Tomcat:** 内嵌Web服务器。
*   **MySQL:**关系型数据库，用于持久化存储女友数据和聊天记录。
*   **Maven:**项目构建和依赖管理工具。
*   **Java HTTP Client:**用于与 DeepSeek API 进行通信。
*   **RESTful API 设计原则。**

**前端 (Frontend):**

*   **HTML5:**页面结构。
*   **CSS3:**页面样式和布局 (包括 Flexbox 和 Grid)。
*   **JavaScript (ES6+):**页面交互逻辑和动态行为。
    *   **Fetch API (AJAX):** 实现前后端异步数据通信。
    *   **DOM Manipulation:** 动态更新页面内容。
*   **Font Awesome:**提供图标。
*   **Google Fonts:**提供自定义字体 (Pacifico, Inter)。

**数据交换格式:**

*   **JSON:**前后端数据交互以及与外部API通信的主要格式。

**API 集成:**

*   **DeepSeek API:**提供聊天机器人的AI能力。

**开发与版本控制:**

*   **Git:**版本控制系统。
*   **GitHub:**代码托管平台。
*   **IDE:** VS Code 
*   **MySQL Workbench:**数据库管理工具。

## 🔧 环境搭建与运行指南

在本地运行本项目，你需要以下环境：

1.  **Java Development Kit (JDK) 17 或更高版本。**
2.  **Apache Maven 3.6 或更高版本。**
3.  **MySQL 服务器 8.0 或更高版本。**
4.  **Git 客户端。**
5.  **一个 DeepSeek API Key。**

**步骤：**

1.  **克隆仓库：**
    ```bash
    git clone https://github.com/loOsAng/virtual-girlfriend-app.git
    cd virtual-girlfriend-app
    ```

2.  **配置 MySQL 数据库：**
    *   启动你的 MySQL 服务器。
    *   使用 MySQL 客户端 (如 MySQL Workbench) 创建一个新的数据库，例如 `virtual_girlfriend_db`。
        ```sql
        CREATE DATABASE virtual_girlfriend_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
        ```

3.  **配置应用属性：**
    *   在 `src/main/resources/` 目录下，复制 `application.properties.templates` 文件并将其重命名为 `application.properties`。
    *   打开 `application.properties` 文件，修改以下配置项为你自己的真实值：
        ```properties
        # DeepSeek API Configuration
        deepseek.api.key=YOUR_DEEPSEEK_API_KEY_HERE
        deepseek.api.endpoint=https://api.deepseek.com/v1/chat/completions

        # MySQL DataSource Configuration
        spring.datasource.url=jdbc:mysql://localhost:3306/virtual_girlfriend_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
        spring.datasource.username=YOUR_MYSQL_USERNAME_HERE # 例如 root
        spring.datasource.password=YOUR_MYSQL_PASSWORD_HERE
        ```

4.  **构建并运行后端应用：**
    在项目根目录下打开终端或 Git Bash，执行以下 Maven 命令：
    ```bash
    ./mvnw spring-boot:run
    ```
    或者 (如果 `mvnw` 不可用或你想用系统安装的 Maven)：
    ```bash
    mvn spring-boot:run
    ```
    应用成功启动后，后端服务将在 `http://localhost:8080` 上运行。

5.  **访问前端页面：**
    打开你的 Web 浏览器，访问 `http://localhost:8080/`。


## 💡 未来可能的改进方向 (v0.2 及以后)

*   **用户认证与授权：** 实现用户注册登录，让每个用户拥有自己的专属伴侣列表。
*   **更丰富的女友状态系统：** 引入心情值、好感度、体力值等，影响互动和聊天。
*   **高级互动反馈：** 为互动增加更生动的视觉和声音效果。
*   **女友形象定制：** 允许用户上传或选择女友的头像/形象。
*   **礼物系统和小游戏。**
*   **WebSocket 实现实时聊天更新 (如果多人使用或需要更即时反馈)。**
*   **更完善的错误处理和用户提示。**
*   **单元测试和集成测试。**

## 👋 贡献

欢迎提出 Issue 或 Pull Request 来改进本项目

## 📄 许可证

本项目采用 [MIT 许可证](LICENSE)。

---

希望这个 README 能帮助你更好地了解项目！
