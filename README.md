
```diff
 ____             _       _   ____              _
/ ___| _ __  _ __(_)_ __ | |_| __ )  ___   ___ | |_
\___ \| '_ \| '__| | '_ \| __|  _ \ / _ \ / _ \| __|
 ___) | |_) | |  | | | | | |_| |_) | (_) | (_) | |_
|____/| .__/|_|  |_|_| |_|\__|____/ \___/ \___/ \__|
 _____|_|                       _       _ _       _
|_   _| |__  _ __ ___  __ _  __| |_ __ (_) | ___ | |_
  | | | '_ \| '__/ _ \/ _` |/ _` | '_ \| | |/ _ \| __|
  | | | | | | | |  __/ (_| | (_| | |_) | | | (_) | |_
  |_| |_| |_|_|  \___|\__,_|\__,_| .__/|_|_|\___/ \__|
                                 |_|
```
# Threadpilot Microservices

## Overview
This project contains two microservices:
- **Vehicle Service** (`threadpilot-vehicle`)
- **Insurance Service** (`threadpilot-insurance`)

## Architecture Diagram

![Architecture Diagram](/images/architecture_all.png)

---

- **Vehicle Service** exposes endpoints to get vehicle details by registration number and by owner personal ID. [VEHICLE README](threadpilot-vehicle/Readme.md)
- **Insurance Service** exposes an endpoint to get insurance information by personal ID. [INSURANCE README](threadpilot-insurance/Readme.md)


## 🛠️ Tech Stack

| Technology      | Description                                 |
|-----------------|---------------------------------------------|
| ![Spring Boot](https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white) | Backend framework for building REST APIs |
| ![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white) | Containerization for easy deployment |
| ![Liquibase](https://img.shields.io/badge/Liquibase-2962FF?style=for-the-badge&logo=liquibase&logoColor=white) | Database version control tool |
| ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white) | Relational database |
| ![Togglz](https://img.shields.io/badge/Togglz-FF6F00?style=for-the-badge&logo=featureflag&logoColor=white) | Feature toggle system |
| ![Sonar](https://img.shields.io/badge/SonarQube-4E9BCD?style=for-the-badge&logo=sonarqube&logoColor=white) | Code quality and static analysis |
| ![GitHub Actions](https://img.shields.io/badge/GitHub%20Actions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white) | CI/CD pipelines |
| ![IntelliJ](https://img.shields.io/badge/IntelliJ_IDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white) | Main development IDE |
| ![VS Code](https://img.shields.io/badge/VSCode-007ACC?style=for-the-badge&logo=visual-studio-code&logoColor=white) | Alternative lightweight editor |
| ![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white) | Build automation tool |

---

## Running locally
### Prerequisite
- docker installed [Docker Installation](https://docs.docker.com/engine/install/)
- docker compose installation [Docker Compose Installation](https://docs.docker.com/compose/install/)


### Running docker compose
Clone the project repository
```bash
git clone https://github.com/kalilou/threadpilot-poc.git
```
Build threadpilot-vehicle
```bash
cd threadpilot-vehicle && ./gradlew clean build
```

Build threadpilot-insurance
```bash
cd threadpilot-insurance && ./gradlew clean build
```

Run docker compose on the root of the project
```bash
docker-compose up --build
```
#### Example Threadpilot Endpoints
- **Insurance Endpoint:** [`http://localhost:8080/api/insurances/199001011001`](http://localhost:8080/api/insurances/199001011001)
- **Feature Toggle Endpoint:** [`http://localhost:8080/togglz-console`](http://localhost:8080/togglz-console)
- **Vehicle Endpoint:** [`http://localhost:8081/api/vehicles/ABC130`](http://localhost:8081/api/vehicles/ABC130)

## Feature Toggles
![Promotion Feature Manager](/images/feature_flags.png)
In this projects, for the feature toggles I make use of Togglz which is a Java-based library implementing feature flags enabling us to dynamically enable/disable features without redeploying the codebase. In this project, I took promotion based approach as feature flags. This is will integrated with SpringBoot.
Endpoint: http://localhost:8080/togglz-console


## 🧪 Testing Strategy: The Test Pyramid
![Testing approach](/images/test.png)
This project follows the **Test Pyramid** approach to ensure a balanced, maintainable, and efficient testing strategy.

The test pyramid encourages writing **many fast and focused unit tests**, fewer integration and contract tests, and minimal (but valuable) E2E/API tests. This ensures faster feedback loops, high reliability, and confidence in code changes.

---

### 🔺 Test Pyramid Summary

| Layer        | Type            | Tools                             | Quantity | Speed    | Stability  |
|--------------|------------------|------------------------------------|----------|----------|------------|
| 🔝 Top        | **E2E Tests**     | Selenium, Cypress - ❌ Not used   | Few      | ❌ Slow   | ⚠️ Fragile  |
| 🔗 Middle     | **Integration**   | Spring Boot Test - ✅ Used   | Medium   | ⚡ Medium | ⚖️ Moderate |
| 📜 Middle     | **Contract Tests**| Spring Cloud Contract -  ✅ Used    | Medium   | ⚡ Fast   | ✅ Stable   |
| 🔬 Base       | **Unit Tests**    | JUnit 5 -  ✅ Used                | Many     | 🚀 Fastest | 🛡️ Stable   |

---

- Unittests coverage all insurance Service components (Using JUnit 5)
- Integration tests covering the controllers  (Using SprintBoot Test)
- Contract tests to ensure API compatibility between threadpilot-insurance and threadpilot-vehicle REST API (Using SprintBoot Cloud Contract)

### 🧭 Guiding Principles in this projects based on the Test Pyramid approach.
- ✅ Favor **more unit tests** for fast feedback.
- ⚖️ Use **contract and integration tests** to ensure compatibility across threadpilot-insurance and threadpilot-vehicle.
- 🧪 Only this **E2E tests** only for critical user flows (Not used in these projects)

## 🚀 Continuous Delivery & DevOps
![Testing approach](/images/deploy.png)

This GitHub Actions workflow automates the build and deployment process for the **Test Insurance Service**. It is triggered by pushes and pull requests to the `main`, and also on other branches based on a given engineering adopted workflow such as `staging`, and `testing` branches for example.

### 📋 Workflow Overview
`./.github/workflows/deploy.yml`
- **Trigger Events**
  - Pull requests targeting: `main`, `staging`, `testing`
  - Pushes to: `main`, `staging`, `testing`

- **Concurrency**
  - Ensures only the latest workflow run executes per branch by canceling any in-progress runs.

### 🛠️ Jobs Breakdown

1. **🔔 Start Notification**
   - Sends a start message to indicate the pipeline has begun. This notification can be send to any communication tools used by the engineering team

2. **🧪 Sonar Analysis**
   - Executes static code analysis using Sonar to ensure code quality, this one is not working on the pipeline yet since it requires setting up Sonar to work with GitHub, but it was tested locally as depicted below.
   ![Testing approach](/images/sonar.png)

3. **🔧 Build Jobs**
   - **Insurance Build**
     - Builds the `threadpilot-insurance` project using Java 21.
   - **Vehicle Build**
     - Builds the `threadpilot-vehicle` project using Java 21.

4. **🚚 Deployment** *(only on `main` branch)*
   - **Insurance Deploy**
     - Deploys the built Insurance service.
   - **Vehicle Deploy**
     - Deploys the built Vehicle service.

5. **🔔 End Notification**
   - Sends a message indicating the pipeline has completed.

### 📁 Reusable Workflow
- Since I am a fun of reusable workflow so I have setup a very simple one for both builds to rely on a shared Gradle workflow defined in:
`./.github/workflows/gradle.yml`
---

## 📖 API Documentation with Swagger

Both the Vehicle and Insurance microservices are equipped with Swagger (OpenAPI) for interactive API documentation and testing.

### 🔧 Setup & Access
- After starting each service, access the Swagger UI in your browser:
  - **Vehicle Service:** [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)
  - **Insurance Service:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---
## 💭 Personal Reflection

In my previous role as a Backend Engineer at Schibsted, I worked extensively on testing—ranging from unit tests to integration and end-to-end tests. During my time as a consultant, I also had the opportunity to work on testing strategies across various industries.

One challenge I faced in this project was the limited time available to dive deep into some areas I find particularly interesting, such as contract testing. I believe there’s a lot of value in implementing it properly, and I would have loved more time to explore it further.

If I had more time, I would have explored:

- 🔀 **Feature toggles**: While [Togglz](https://www.togglz.org/) integrates well with Spring Boot and was the obvious choice, I’m also interested in tools like [Unleash](https://www.getunleash.io/) due to its language-agnostic nature 🌐. However, evaluating and implementing it properly would have required more time.

- 🤝 **Contract testing**: I would have liked to externalize and version control contracts, ideally storing them in GitHub 📂. This would have been especially useful for managing contracts between the `threadpilot-vehicle` and `threadpilot-insurance` products.

- 🚀 **Developer experience**: For onboarding and empowering developers, I was keen on exploring tools like [Backstage](https://backstage.io/) or the [Harness Developer Portal API](https://developer.harness.io/). These tools could significantly improve discoverability and developer productivity ⚙️.

- 🔢 **API Versioning** I would have taken some more time to dive deeper into **API versioning**

- 🔍📈 **Monitoring & Logs** I’d also spend more time exploring additional **monitoring metrics** and refining a **meaningful logging approach**. Good Monitoring and logging helps in understanding system health and debugging issues faster.

