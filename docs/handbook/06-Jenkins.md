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

---

# Sprint 13 – Parameterized Builds

## Learning Objectives

Learn how to make Jenkins jobs dynamic by allowing users to provide input at build time instead of hardcoding values inside the pipeline.

---

## Topics Covered

### Why Parameterized Builds?

Without parameters

```
Jenkins

↓

Always Executes

QA
Chrome
Smoke
```

Every execution is identical.

---

With Parameters

```
User

↓

Select Values

↓

Jenkins

↓

Pipeline

↓

Maven

↓

Automation Framework
```

One pipeline can execute multiple combinations.

---

## Supported Parameter Types

### Choice Parameter

```groovy
choice(

    name: 'Environment',

    choices: ['QA','UAT','PROD'],

    description: 'Select Environment'

)
```

Purpose

Allows users to choose a predefined value.

---

### Boolean Parameter

```groovy
booleanParam(

    name: 'Headless',

    defaultValue: true,

    description: 'Run Browser Headless'

)
```

Purpose

Returns

```
true

or

false
```

---

## Pipeline Parameters

Complete Parameter Block

```groovy
parameters {

    choice(

        name:'Environment',

        choices:['QA','UAT','PROD']

    )

    choice(

        name:'Browser',

        choices:['Chrome','Edge']

    )

    choice(

        name:'Suite',

        choices:['Smoke','Regression']

    )

    booleanParam(

        name:'Headless',

        defaultValue:true

    )

}
```

---

## Accessing Parameters

Syntax

```groovy
params.Environment

params.Browser

params.Suite

params.Headless
```

Example

```groovy
echo "${params.Environment}"
```

---

## Passing Parameters to Maven

```groovy
bat """

mvn clean test ^

-Denvironment=${params.Environment} ^

-Dbrowser=${params.Browser} ^

-Dsuite=${params.Suite} ^

-Dheadless=${params.Headless}

"""
```

---

## Maven to Java Flow

```
Jenkins UI

↓

Pipeline Parameters

↓

Maven Command

↓

-D System Properties

↓

Java Framework

↓

System.getProperty()
```

---

## Java Code

```java
String environment =
System.getProperty("environment");

String browser =
System.getProperty("browser");

String suite =
System.getProperty("suite");

String headless =
System.getProperty("headless");
```

---

## Validation

Console Output

```
Environment : QA

Browser : Chrome

Suite : Smoke

Headless : true
```

Changing Jenkins parameters changes the values passed into the automation framework without modifying the source code.

---

## Enterprise Benefits

- Single reusable pipeline
- No hardcoded environments
- Supports multiple browsers
- Supports multiple test suites
- Easy production deployment

---

## Best Practices

✔ Never hardcode environments.

✔ Use parameters for all configurable values.

✔ Pass parameters using Maven properties.

✔ Read values inside Java using `System.getProperty()`.

---

## Sprint Summary

Completed

- Choice Parameters
- Boolean Parameters
- params Object
- Maven Parameter Passing
- Java System Properties
- Dynamic Test Execution

---

# Sprint 14 – Environment Variables

## Learning Objectives

Understand how Jenkins Environment Variables simplify pipeline configuration by centralizing reusable values.

---

## Topics Covered

### Why Environment Variables?

Without Environment Variables

```groovy
git branch:'main',

url:'git@github.com:pmplak/selenium-cicd-demo.git'
```

Values are repeated throughout the pipeline.

---

With Environment Variables

```groovy
environment {

    GIT_BRANCH='main'

    REPOSITORY='git@github.com:pmplak/selenium-cicd-demo.git'

}
```

Pipeline becomes cleaner and easier to maintain.

---

## Global Environment Block

```groovy
environment {

    BUILD_OWNER='Petchimuthu Pandiyan'

    GIT_BRANCH='main'

    REPOSITORY='git@github.com:pmplak/selenium-cicd-demo.git'

    MAVEN_GOAL='clean test'

}
```

---

## Accessing Variables

Syntax

```groovy
env.BUILD_OWNER

env.GIT_BRANCH

env.REPOSITORY

env.MAVEN_GOAL
```

Example

```groovy
echo "${env.BUILD_OWNER}"
```

---

## Environment Information Stage

```groovy
stage('Environment Information') {

    steps {

        echo "================================="

        echo "Build Owner : ${env.BUILD_OWNER}"

        echo "Repository : ${env.REPOSITORY}"

        echo "Branch : ${env.GIT_BRANCH}"

        echo "Maven Goal : ${env.MAVEN_GOAL}"

        echo "================================="

    }

}
```

Purpose

Displays build configuration before executing the pipeline.

---

## Using Environment Variables

Git Checkout

```groovy
git(

    branch: env.GIT_BRANCH,

    credentialsId:'github-ssh',

    url: env.REPOSITORY

)
```

---

Maven Execution

```groovy
bat """

mvn ${env.MAVEN_GOAL} ^

-Denvironment=${params.Environment} ^

-Dbrowser=${params.Browser} ^

-Dsuite=${params.Suite} ^

-Dheadless=${params.Headless}

"""
```

---

## Variable Scope

Global Environment

```
Available

↓

Entire Pipeline
```

Stage Environment

```groovy
environment {

    TEST_OWNER='Automation Team'

}
```

Available only inside that stage.

---

## Benefits

- Centralized configuration
- No repeated values
- Easier maintenance
- Easy environment migration
- Cleaner Jenkinsfile

---

## Validation

Console

```
=================================

Build Owner : Petchimuthu Pandiyan

Repository : git@github.com:pmplak/selenium-cicd-demo.git

Branch : main

Maven Goal : clean test

=================================
```

---

## Enterprise Best Practices

✔ Store reusable values in the Environment block.

✔ Avoid hardcoding repository URLs.

✔ Store branch names as variables.

✔ Keep Maven goals configurable.

✔ Use stage-level environment variables only when required.

---

## Sprint Summary

Completed

- Global Environment Variables
- Stage Environment Variables
- env Object
- Environment Information Stage
- Git Using Variables
- Maven Using Variables
- Variable Scope
- Enterprise Configuration

---

# Sprint 15 – Build Triggers & Pipeline from SCM

## Learning Objectives

Understand how Jenkins automatically starts builds and how to maintain the pipeline as code inside GitHub.

---

## Topics Covered

### Build Triggers

- Build Now
- Build Periodically
- Poll SCM
- GitHub Webhooks (Introduction)
- Trigger Builds Remotely (Overview)

---

### Pipeline from SCM

Instead of storing the Groovy Pipeline script inside Jenkins, store the Jenkinsfile in the Git repository.

Benefits:

- Pipeline version controlled
- Team collaboration
- Code reviews
- Rollback support
- Single source of truth

Pipeline Flow

```
GitHub Repository
        │
        │
 Jenkinsfile
        │
        ▼
 Jenkins Pipeline Job
        │
        ▼
 Execute Pipeline
```

---

## Configuring Pipeline from SCM

Job Type

```
Pipeline
```

Definition

```
Pipeline script from SCM
```

SCM

```
Git
```

Repository URL

```
git@github.com:pmplak/selenium-cicd-demo.git
```

Branch

```
main
```

Script Path

```
Jenkinsfile
```

---

## Pipeline Structure

```groovy
pipeline {

    agent any

    tools {

        maven 'Name Maven-3.9.9'

    }

    environment {

        BUILD_OWNER = 'Petchimuthu Pandiyan'
        GIT_BRANCH  = 'main'
        REPOSITORY  = 'git@github.com:pmplak/selenium-cicd-demo.git'
        MAVEN_GOAL  = 'clean test'

    }

    parameters {

        choice(...)
        booleanParam(...)

    }

    stages {

    }

    post {

    }

}
```

---

## Environment Variables

Global Environment

```groovy
environment {

    BUILD_OWNER='Petchimuthu Pandiyan'
    GIT_BRANCH='main'
    REPOSITORY='git@github.com:pmplak/selenium-cicd-demo.git'
    MAVEN_GOAL='clean test'

}
```

Usage

```groovy
echo "${env.BUILD_OWNER}"
```

Purpose

- Avoid hardcoding
- Centralized configuration
- Easy maintenance

---

## Pipeline Parameters

Covered Parameters

- Choice Parameter
- Boolean Parameter

Usage

```groovy
params.Environment
params.Browser
params.Suite
params.Headless
```

Flow

```
Pipeline UI

↓

params

↓

Maven

↓

-D Properties

↓

Java Framework
```

---

## Browser Validation

Using Script Block

```groovy
script {

    if(params.Browser=="Chrome"){

        echo "Executing Chrome Tests"

    }else{

        echo "Executing Edge Tests"

    }

}
```

Purpose

- Dynamic execution
- Conditional logic
- Runtime decision making

---

## Stage Level Environment

```groovy
environment {

    TEST_OWNER="Automation Team"

}
```

Usage

```groovy
echo "${TEST_OWNER}"
```

Scope

Only available within the Build Stage.

---

## Stage Level Tools

```groovy
tools {

    maven 'Name Maven-3.9.9'

}
```

Purpose

Override global tools for a specific stage.

---

## Retry Block

```groovy
retry(2){

    bat "mvn clean test"

}
```

Purpose

Automatically retries transient failures.

Enterprise Usage

- Network glitches
- Browser launch failures
- Temporary Git failures

---

## Pipeline from SCM Advantages

- Version Controlled
- Traceable
- Reusable
- Team Collaboration
- Enterprise Standard

---

## Sprint Summary

Completed

- Pipeline from SCM
- Environment Variables
- Stage Environment
- Stage Tools
- Retry
- Browser Validation
- Jenkinsfile stored in GitHub

---

# Sprint 16 – Credentials Management & Workspace Management

## Learning Objectives

Understand how Jenkins securely stores sensitive information and how workspace management improves pipeline stability.

---

## Topics Covered

### Jenkins Credentials

Credential Types

- Username with Password
- SSH Username with Private Key
- Secret Text
- Secret File
- Certificate

---

## SSH Credentials

Credential Type

```
SSH Username with private key
```

Used For

- Git Checkout
- Git Push
- Git Clone

Pipeline

```groovy
withCredentials([

    sshUserPrivateKey(

        credentialsId:'github-ssh',

        keyFileVariable:'SSH_KEY',

        usernameVariable:'SSH_USER'

    )

]){

    echo "Git User : ${SSH_USER}"

}
```

Purpose

Secure Git Authentication.

---

## Secret Text Credential

Credential Type

```
Secret Text
```

Example

```
API Key

Bearer Token

OAuth Token
```

Pipeline

```groovy
withCredentials([

    string(

        credentialsId:'dummy-api-key',

        variable:'API_KEY'

    )

]){

    echo "Credential Loaded Successfully"

}
```

Security

Credential value is masked in Console Output.

---

## Workspace Cleanup

Plugin

```
Workspace Cleanup Plugin
```

Pipeline

```groovy
cleanWs()
```

Purpose

Deletes old files before starting a new build.

Benefits

- Fresh Workspace
- No stale files
- Reliable builds

---

## Stash

Purpose

Temporarily store files during pipeline execution.

```groovy
stash(

    name:'project-files',

    includes:'target/**/*'

)
```

---

## Unstash

Restore files.

```groovy
unstash 'project-files'
```

Flow

```
Workspace

↓

Stash

↓

Temporary Storage

↓

Unstash

↓

Workspace
```

---

## Pipeline Options

### timestamps()

```groovy
timestamps()
```

Purpose

Display timestamps in console logs.

---

### timeout()

```groovy
timeout(

    time:30,

    unit:'MINUTES'

)
```

Purpose

Terminate hung builds.

---

### buildDiscarder()

```groovy
buildDiscarder(

    logRotator(

        numToKeepStr:'20',

        artifactNumToKeepStr:'10'

    )

)
```

Purpose

Automatically delete old builds and artifacts.

---

### disableConcurrentBuilds()

```groovy
disableConcurrentBuilds()
```

Purpose

Prevent multiple builds from using the same workspace simultaneously.

---

### skipDefaultCheckout()

```groovy
skipDefaultCheckout()
```

Purpose

Prevent Jenkins from performing the automatic SCM checkout.

Only the explicit Git checkout stage executes.

---

## Console Validation

Successfully Verified

- SSH Credential Loaded
- Secret Credential Loaded
- Workspace Cleaned
- Files Stashed
- Files Restored
- Timeout Enabled
- Retry Executed
- Build Success
- JUnit Published
- Artifacts Archived

---

## Enterprise Best Practices

- Never hardcode credentials.
- Always use Jenkins Credentials.
- Always clean workspace before checkout.
- Use stash/unstash to transfer artifacts.
- Keep Jenkinsfile inside GitHub.
- Enable timestamps.
- Configure build retention.
- Use retry for unstable operations.
- Disable concurrent builds.

---

## Sprint Summary

Completed

- Credentials Management
- SSH Credentials
- Secret Text Credentials
- Workspace Cleanup
- Stash
- Unstash
- Pipeline Options
- Enterprise Pipeline Configuration

---

---

# Sprint 17 – Workspace Management & Pipeline Optimization

## Learning Objectives

Learn how Jenkins manages the build workspace, why workspace cleanup is important, and how enterprise pipelines optimize workspace usage.

---

## Topics Covered

### Jenkins Workspace

Every Jenkins Job has its own Workspace.

Example

```
C:\ProgramData\Jenkins.jenkins\workspace\First-Jenkins-Pipeline
```

Workspace contains

- Source Code
- Maven Dependencies
- Target Folder
- Reports
- Build Artifacts
- Temporary Files

---

## Workspace Lifecycle

```
Pipeline Starts

↓

Workspace Created

↓

Git Checkout

↓

Build

↓

Reports

↓

Artifacts

↓

Pipeline Ends
```

---

## Why Workspace Cleanup?

Problem

```
Build #1

↓

target/

↓

Build #2

↓

Old Files Still Exist
```

Old files can cause

- Wrong reports
- Stale artifacts
- Compilation issues
- Unstable builds

---

## cleanWs()

Plugin

```
Workspace Cleanup Plugin
```

Pipeline

```groovy
cleanWs()
```

Purpose

Deletes the complete workspace before starting a fresh build.

---

## Validation

Console

```
cleanWs

Deleting project workspace...

done
```

---

## skipDefaultCheckout()

Normally Jenkins automatically performs

```
Checkout SCM
```

before entering the pipeline.

We disabled it using

```groovy
options {

    skipDefaultCheckout()

}
```

Reason

Our pipeline performs an explicit Git checkout.

This avoids duplicate checkouts.

---

## Explicit Checkout

```groovy
git(

    branch: env.GIT_BRANCH,

    credentialsId: 'github-ssh',

    url: env.REPOSITORY

)
```

Benefits

- Complete control
- Custom branches
- Custom credentials
- Better pipeline readability

---

## Workspace Structure

```
workspace/

│

├── src

├── target

├── pom.xml

├── testng.xml

├── Jenkinsfile

└── .git
```

---

## Stash

Purpose

Temporarily store files during the same pipeline execution.

Pipeline

```groovy
stash(

    name: 'project-files',

    includes: 'target/**/*'

)
```

Console

```
Stashed 25 file(s)
```

---

## Unstash

Purpose

Restore previously stashed files.

Pipeline

```groovy
unstash 'project-files'
```

Console

```
Files Restored Successfully
```

---

## Stash Flow

```
Workspace

↓

Stash

↓

Temporary Jenkins Storage

↓

Delete Workspace

↓

Unstash

↓

Workspace Restored
```

---

## Enterprise Usage

Used for

- Parallel Builds
- Multi-Agent Pipelines
- Docker Agents
- Kubernetes Agents
- Distributed Builds

---

## Best Practices

✔ Clean workspace before checkout.

✔ Never depend on old build files.

✔ Use stash/unstash only inside the same pipeline.

✔ Archive artifacts for long-term storage.

✔ Keep workspaces lightweight.

---

## Sprint Summary

Completed

- Workspace Management
- Workspace Cleanup Plugin
- cleanWs()
- skipDefaultCheckout()
- Explicit Git Checkout
- Workspace Lifecycle
- Stash
- Unstash
- Enterprise Workspace Best Practices

---

# Sprint 18 – Enterprise Pipeline Options

## Learning Objectives

Learn the most commonly used Jenkins Pipeline options that improve build stability, readability, and maintainability.

---

## Topics Covered

### Pipeline Options

```groovy
options {

    timestamps()

    buildDiscarder()

    disableConcurrentBuilds()

    timeout()

    skipDefaultCheckout()

}
```

---

## timestamps()

Pipeline

```groovy
timestamps()
```

Purpose

Adds timestamps to every console log.

Example

```
21:54:14

21:54:15

21:54:16
```

Benefits

- Easier debugging
- Performance analysis
- Build timing

---

## timeout()

Pipeline

```groovy
timeout(

    time:30,

    unit:'MINUTES'

)
```

Purpose

Automatically stops hanging builds.

Console

```
Timeout set to expire in 30 min
```

Enterprise Usage

- Prevents blocked executors.
- Prevents infinite waits.
- Saves build resources.

---

## buildDiscarder()

Pipeline

```groovy
buildDiscarder(

    logRotator(

        numToKeepStr:'20',

        artifactNumToKeepStr:'10'

    )

)
```

Purpose

Automatically deletes old builds and artifacts.

Benefits

- Saves disk space.
- Improves Jenkins performance.
- Prevents storage growth.

---

## disableConcurrentBuilds()

Pipeline

```groovy
disableConcurrentBuilds()
```

Purpose

Only one build can execute at a time.

Validation

Start one build.

Immediately click

```
Build Now
```

again.

Second build waits until the first completes.

---

## retry()

Pipeline

```groovy
retry(2){

    bat "mvn clean test"

}
```

Purpose

Automatically retries failed operations.

Enterprise Usage

- Temporary network failures.
- Browser launch failures.
- Git checkout failures.
- Package download failures.

---

## Stage Level Tools

```groovy
tools {

    maven 'Name Maven-3.9.9'

}
```

Purpose

Override global tools inside a specific stage.

---

## Stage Level Environment

```groovy
environment {

    TEST_OWNER="Automation Team"

}
```

Usage

```groovy
echo "${TEST_OWNER}"
```

Scope

Available only inside that stage.

---

## Browser Validation

Using Script Block

```groovy
script {

    if(params.Browser=="Chrome"){

        echo "Executing Chrome Tests"

    }

    else{

        echo "Executing Edge Tests"

    }

}
```

Purpose

Runtime decision making.

---

## Console Validation

Successfully Verified

- timestamps()
- timeout()
- buildDiscarder()
- disableConcurrentBuilds()
- retry()
- Stage Environment
- Stage Tools
- Browser Validation

---

## Enterprise Best Practices

✔ Always enable timestamps.

✔ Configure build retention.

✔ Set timeout for every pipeline.

✔ Disable concurrent builds when using the same workspace.

✔ Retry only unstable operations.

✔ Keep stage environments isolated.

---

## Sprint Summary

Completed

- timestamps()
- timeout()
- buildDiscarder()
- disableConcurrentBuilds()
- retry()
- Stage Environment
- Stage Tools
- Browser Validation
- Enterprise Pipeline Options

---

---

# Sprint 19 – Advanced Declarative Pipeline

## Learning Objectives

Learn advanced Declarative Pipeline features that improve maintainability, readability, and enterprise readiness.

---

## Topics Covered

### Complete Pipeline Structure

```groovy
pipeline {

    agent any

    options { }

    tools { }

    environment { }

    parameters { }

    stages {

        stage(){

            steps{

            }

        }

    }

    post{

    }

}
```

---

## Pipeline Execution Flow

```
Pipeline Starts

↓

Agent Allocation

↓

Pipeline Options

↓

Tool Configuration

↓

Environment Variables

↓

Build Parameters

↓

Stages

↓

Post Actions

↓

Pipeline Ends
```

---

## Pipeline Options

Implemented

```groovy
options {

    timestamps()

    buildDiscarder(

        logRotator(

            numToKeepStr:'20',

            artifactNumToKeepStr:'10'

        )

    )

    disableConcurrentBuilds()

    timeout(

        time:30,

        unit:'MINUTES'

    )

    skipDefaultCheckout()

}
```

---

## Environment Variables

Global Variables

```groovy
environment {

    BUILD_OWNER='Petchimuthu Pandiyan'

    GIT_BRANCH='main'

    REPOSITORY='git@github.com:pmplak/selenium-cicd-demo.git'

    MAVEN_GOAL='clean test'

}
```

Usage

```groovy
env.BUILD_OWNER

env.GIT_BRANCH

env.REPOSITORY

env.MAVEN_GOAL
```

---

## Parameters

Implemented

```groovy
choice()

booleanParam()
```

Pipeline Usage

```groovy
params.Environment

params.Browser

params.Suite

params.Headless
```

---

## Browser Validation

Using Script Block

```groovy
script {

    if(params.Browser=="Chrome"){

        echo "Executing Chrome Tests"

    }

    else{

        echo "Executing Edge Tests"

    }

}
```

Purpose

Runtime decision making.

---

## Stage Environment

```groovy
environment {

    TEST_OWNER="Automation Team"

}
```

Usage

```groovy
echo "${TEST_OWNER}"
```

---

## Stage Tool

```groovy
tools{

    maven 'Name Maven-3.9.9'

}
```

Purpose

Override global tool configuration.

---

## Retry

```groovy
retry(2){

    bat "mvn clean test"

}
```

Purpose

Automatically retries unstable operations.

---

## JUnit Publishing

```groovy
junit 'target/surefire-reports/*.xml'
```

Purpose

Publish TestNG XML results into Jenkins.

---

## Archive Artifacts

```groovy
archiveArtifacts(

    artifacts:'target/**/*',

    fingerprint:true

)
```

Purpose

Store build outputs after pipeline completion.

---

## Secret Credentials

Secret Text

```groovy
withCredentials([

    string(

        credentialsId:'dummy-api-key',

        variable:'API_KEY'

    )

]){

    echo "Credential Loaded Successfully"

}
```

---

## SSH Credentials

```groovy
withCredentials([

    sshUserPrivateKey(

        credentialsId:'github-ssh',

        keyFileVariable:'SSH_KEY',

        usernameVariable:'SSH_USER'

    )

]){

    echo "${SSH_USER}"

}
```

---

## Manual Approval

```groovy
input{

    message "Continue Pipeline?"

    ok "Proceed"

}
```

Purpose

Pause pipeline until user approval.

Enterprise Usage

- Production Deployment
- CAB Approval
- Release Management

---

## Parallel Stage

```groovy
parallel{

    stage("Chrome"){

    }

    stage("Edge"){

    }

}
```

Purpose

Execute multiple stages simultaneously.

---

## Matrix Stage

```groovy
matrix{

    axes{

        axis{

            name 'BrowserName'

            values 'Chrome','Edge'

        }

    }

}
```

Purpose

Run the same stage using multiple combinations.

---

## Validation

Successfully Verified

- Environment Variables
- Parameters
- Retry
- JUnit
- Artifacts
- Credentials
- Manual Approval
- Parallel
- Matrix

---

## Enterprise Best Practices

✔ Keep Jenkinsfile inside GitHub.

✔ Use Pipeline from SCM.

✔ Avoid hardcoded values.

✔ Use parameters.

✔ Secure credentials.

✔ Publish reports.

✔ Archive artifacts.

---

## Sprint Summary

Completed

- Advanced Declarative Pipeline
- Retry
- Manual Approval
- Parallel
- Matrix
- Credentials
- Browser Validation
- JUnit
- Archive Artifacts

---

# Sprint 20 – Scripted Pipeline

## Learning Objectives

Understand how Groovy scripting extends Declarative Pipelines and enables dynamic pipeline behavior.

---

## Topics Covered

### Declarative vs Scripted

Declarative

```groovy
stage("Build"){

    steps{

    }

}
```

Scripted

```groovy
script{

}
```

---

## Why Script Block?

Used whenever normal Declarative syntax cannot solve a problem.

Examples

- Loops
- Conditions
- Variables
- Lists
- Exception Handling

---

## Variables

```groovy
def projectName="Selenium CI/CD"

def version="1.0"
```

Console

```
Project : Selenium CI/CD

Version : 1.0
```

---

## Lists

```groovy
def browsers=[

    "Chrome",

    "Edge"

]
```

---

## Loop

```groovy
for(browser in browsers){

    echo browser

}
```

Output

```
Chrome

Edge
```

---

## Closures

Implemented

```groovy
def buildMessage={ name ->

    return "Welcome ${name}"

}
```

Usage

```groovy
echo buildMessage(projectName)
```

---

## Why Closure?

Groovy methods cannot be declared inside

```groovy
script{

}
```

Example (Invalid)

```groovy
def buildMessage(name){

}
```

Error

```
Method definition not expected here
```

Solution

Use a Closure.

---

## try-catch-finally

```groovy
try{

    echo "Executing"

}

catch(Exception ex){

    echo ex.getMessage()

}

finally{

    echo "Completed"

}
```

Purpose

Handle runtime failures.

---

## Script Block Example

```groovy
script{

    def browsers=[

        "Chrome",

        "Edge"

    ]

    for(browser in browsers){

        echo browser

    }

}
```

---

## Enterprise Usage

Script Block is used for

- Dynamic Pipeline Logic
- JSON Parsing
- API Response Handling
- File Processing
- Looping
- Dynamic Stage Creation

---

## Console Validation

Verified

```
Project : Selenium CI/CD

Version : 1.0

Chrome

Edge

Welcome Selenium CI/CD

Executing

Completed
```

---

## Declarative vs Scripted

| Declarative | Scripted    |
| ----------- | ----------- |
| Easy        | Flexible    |
| Structured  | Dynamic     |
| Recommended | Advanced    |
| Less Coding | Full Groovy |

---

## Enterprise Best Practices

✔ Use Declarative Pipeline as the base.

✔ Use Script Blocks only when required.

✔ Prefer Closures over methods inside script blocks.

✔ Keep business logic outside Jenkins whenever possible.

✔ Avoid excessive Groovy code.

---

## Sprint Summary

Completed

- Script Block
- Variables
- Lists
- Loops
- Closures
- try-catch-finally
- Dynamic Logic
- Declarative vs Scripted Pipeline

---