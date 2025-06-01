
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

## Feature Toggles
![Promotion Feature Manager](/images/feature_flags.png)
In this projects, for the feature toggles I make use of Togglz which is a Java-based library implementing feature flags enabling us to dynamically enable/disable features without redeploying the codebase. In this project, I took promotion based approach as feature flags. This is will integrated with SpringBoot.


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
  - **Insurance Service:** [http://localhost:8080/swagger-ui.html](http://localhost:8082/swagger-ui.html)

---
## Personal Reflection

I have previously worked extensivily on testing when I worked a backend engineering at Schibsted from writing unittests, integration and end to end tests as well as during my consulting time I have worked with tests across different industries.

What I have found challenging about not having much time to focus on a proper implementation by looking for example on the contract testing which I found extremely interesting.

If I had more time I would look into the following:
- Feature toggles: Togglz integrate well with SpringBoot but I would love to look into other tools like unleash given it is language-agnostic but with the timeframe in this project it would have taken more time to look into unleash.
- Contract testing, I would like to use an external storage e.g Github for contracts for both the products threadpilot-vehicle and consumer threadpilot-insurance.
- For enpowering developers or onboarding new developers, I would have looked in the tools such backstage or Harness developer portal API.

