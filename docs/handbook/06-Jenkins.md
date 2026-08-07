# Jenkins Handbook

# Overview

Jenkins is an open-source automation server used to automate the Software Development Life Cycle (SDLC). It integrates with Version Control Systems, Build Tools, Testing Frameworks, and Deployment Platforms to implement Continuous Integration (CI) and Continuous Delivery (CI/CD).

Jenkins acts as the orchestration engine that continuously pulls source code, builds the application, executes automated tests, publishes reports, archives artifacts, and prepares applications for deployment.

---

# Lesson Progress

- [x] Purpose of Jenkins
- [x] Jenkins Installation
- [x] Jenkins Architecture
- [x] Jenkins Home Directory
- [x] First Freestyle Job
- [x] GitHub Integration (HTTPS)
- [x] GitHub Integration (SSH)
- [x] Jenkins Credentials
- [x] Maven Tool Configuration
- [x] Maven Build Step
- [x] Windows Batch Build Step
- [x] Git Checkout
- [x] Selenium + TestNG Integration
- [x] Surefire Reports
- [x] Publish JUnit Reports
- [x] Archive Artifacts
- [x] Parameterized Builds
- [x] Pipeline as Code
- [x] Declarative Pipeline
- [x] Jenkinsfile
- [x] Pipeline Parameters
- [x] Post Actions

---

# Sprint 1 – Introduction to Jenkins

## Topics Covered

- What is Jenkins?
- Why Jenkins is used
- Continuous Integration (CI)
- Continuous Delivery (CD)
- Continuous Deployment
- Jenkins Architecture
- Controller
- Agent
- Executor
- Workspace
- Build Lifecycle

---

# Sprint 2 – Jenkins Installation

## Topics Covered

- Windows Installation
- Windows Service
- Local System Account
- Port 8080
- Unlock Jenkins
- Install Suggested Plugins
- Administrator User Creation
- JENKINS_HOME
- Initial Architecture

---

# Sprint 3 – Freestyle Jobs

## Topics Covered

- Creating First Freestyle Job
- Workspace
- Source Code Management
- Build Steps
- Post Build Actions
- Build History
- Console Output
- Build Numbers

---

# Sprint 4 – Git Integration

## Topics Covered

### HTTPS Authentication

- Username
- Personal Access Token

### SSH Authentication

- SSH Key Generation
- Public Key
- Private Key
- GitHub SSH Configuration
- Jenkins SSH Credentials

### Git Checkout

- Repository URL
- Branch Selection
- Credentials
- Git Workspace

---

# Sprint 5 – Maven Integration

## Topics Covered

- Global Tool Configuration
- Maven Installation
- Maven Tool Name
- Invoke Top Level Maven Targets
- Windows Batch Command
- Maven Goals

Covered Commands

```
clean
compile
test
package
install
```

Comparison

- Maven Build Step
- Windows Batch Build

---

# Sprint 6 – Selenium Integration

## Topics Covered

- Maven Project
- Selenium Dependencies
- TestNG Integration
- testng.xml
- Surefire Plugin
- Surefire Reports
- Target Folder Analysis
- Workspace Structure

Target Folder

```
target/
│
├── classes
├── test-classes
├── surefire-reports
├── generated-test-sources
└── maven-status
```

---

# Sprint 7 – Reports

## Topics Covered

### Publish JUnit Test Results

```
target/surefire-reports/*.xml
```

### Archive Artifacts

```
target/**/*
```

Topics

- XML Reports
- HTML Reports
- Build Artifacts
- Fingerprints
- Build History

---

# Sprint 8 – Parameterized Builds

## Topics Covered

- Choice Parameter
- String Parameter
- Text Parameter
- Boolean Parameter
- Password Parameter
- Run Parameter

Passing Parameters

```
Jenkins

↓

Maven

↓

Java

↓

System.getProperty()
```

---

# Sprint 9 – Pipeline as Code

## Topics Covered

- Why Pipeline?
- Freestyle vs Pipeline
- Pipeline Job
- Jenkinsfile
- Declarative Pipeline

Pipeline Structure

```groovy
pipeline {

    agent any

    stages {

        stage('Example') {

            steps {

            }

        }

    }

}
```

---

# Sprint 10 – Jenkins Pipeline

## Topics Covered

### Agent

```groovy
agent any
```

### Tools

```groovy
tools {
    maven 'Name Maven-3.9.9'
}
```

### Git Checkout

```groovy
git
```

### Windows Batch

```groovy
bat
```

### Maven

```
mvn clean test
```

### Parameters

```groovy
parameters {

}
```

### params Object

```groovy
params.Environment
params.Browser
params.Suite
params.Headless
```

Parameter Flow

```
Pipeline UI

↓

params

↓

Maven

↓

-D Properties

↓

System.getProperty()

↓

Java Framework
```

---

# Sprint 11 – Publishing Results

## Topics Covered

### Publish JUnit Reports

```groovy
junit 'target/surefire-reports/*.xml'
```

### Archive Artifacts

```groovy
archiveArtifacts artifacts: 'target/**/*',
                  fingerprint: true
```

Topics

- Recursive Wildcards
- Artifact Storage
- Fingerprints
- Test Reports

---

# Sprint 12 – Post Actions

## Topics Covered

```groovy
post {

    always {

    }

    success {

    }

    failure {

    }

}
```

Actions

- always
- success
- failure

Enterprise Usage

- Notifications
- Email
- Slack
- Microsoft Teams
- Cleanup
- Artifact Publishing

---

# Final Enterprise Pipeline

Current Pipeline Features

- Git Checkout
- Maven Tool Configuration
- Maven Build
- TestNG Execution
- Dynamic Parameters
- Publish Test Results
- Archive Artifacts
- Post Actions

---

# Current Skill Checklist

✅ Jenkins Installation

✅ Jenkins Architecture

✅ Freestyle Jobs

✅ Git Integration

✅ SSH Authentication

✅ Maven Integration

✅ Selenium Integration

✅ TestNG Integration

✅ Surefire Reports

✅ Publish Test Results

✅ Archive Artifacts

✅ Parameterized Builds

✅ Declarative Pipeline

✅ Jenkinsfile

✅ Dynamic Pipeline Parameters

✅ Enterprise Post Actions

---

# Upcoming Sprints

## Sprint 15

- Environment Variables
- Credentials in Pipeline
- Pipeline Syntax Generator
- Clean Jenkinsfile Structure
- Enterprise Best Practices

## Sprint 16

- Multibranch Pipeline
- Webhooks
- GitHub Triggers
- Poll SCM
- Automatic Builds

## Sprint 17

- Shared Libraries
- Reusable Functions
- Global Pipelines

## Sprint 18

- Jenkins Agents
- Distributed Builds
- Labels
- Parallel Execution

## Sprint 19

- Docker Integration
- Docker Agents
- Docker Pipeline

## Sprint 20

- Production CI/CD Pipeline
- Best Practices
- Complete Enterprise Project