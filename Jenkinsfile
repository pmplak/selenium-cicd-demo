def deployApplication(String environmentName) {

    echo "===================================="
    echo "Starting Deployment"
    echo "Target Environment : ${environmentName}"
    echo "Deployment Started..."
    echo "Deployment Completed Successfully."
    echo "===================================="

}

pipeline {

    agent any

    options {

        timestamps()

        buildDiscarder(
            logRotator(
                numToKeepStr: '20',
                artifactNumToKeepStr: '10'
            )
        )

        disableConcurrentBuilds()

        timeout(
            time: 30,
            unit: 'MINUTES'
        )

        skipDefaultCheckout()

    }

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

        choice(
            name: 'Environment',
            choices: ['QA', 'UAT', 'PROD'],
            description: 'Select Environment'
        )

        choice(
            name: 'Browser',
            choices: ['Chrome', 'Edge'],
            description: 'Select Browser'
        )

        choice(
            name: 'Suite',
            choices: ['Smoke', 'Regression'],
            description: 'Select Test Suite'
        )

        booleanParam(
            name: 'Headless',
            defaultValue: true,
            description: 'Run Browser Headless'
        )

    }

    stages {

        stage('Environment Information') {

            steps {

                echo "================================="
                echo "Build Owner : ${env.BUILD_OWNER}"
                echo "Repository  : ${env.REPOSITORY}"
                echo "Branch      : ${env.GIT_BRANCH}"
                echo "Maven Goal  : ${env.MAVEN_GOAL}"
                echo "================================="

            }

        }

        stage('Credential Demonstration') {

            steps {

                withCredentials([
                    sshUserPrivateKey(
                        credentialsId: 'github-ssh',
                        keyFileVariable: 'SSH_KEY',
                        usernameVariable: 'SSH_USER'
                    )
                ]) {

                    echo "Git User : ${SSH_USER}"
                    echo "SSH Credential Loaded"

                }

            }

        }

        stage('Checkout Source Code') {

            steps {

                cleanWs()

                git(
                    branch: env.GIT_BRANCH,
                    credentialsId: 'github-ssh',
                    url: env.REPOSITORY
                )

            }

        }

        stage('Browser Validation') {

            steps {

                script {

                    if (params.Browser == "Chrome") {

                        echo "Executing Chrome Tests"

                    }
                    else {

                        echo "Executing Edge Tests"

                    }

                }

            }

        }

        stage('Scripted Pipeline Demo') {

            steps {

                script {

                    echo "===== Scripted Pipeline Demo ====="

                    def projectName = "Selenium CI/CD"
                    def version = "1.0"

                    echo "Project : ${projectName}"
                    echo "Version : ${version}"

                    def browsers = ["Chrome", "Edge"]

                    for (browser in browsers) {

                        echo "Supported Browser : ${browser}"

                    }

                    def buildMessage = { name ->

                        return "Welcome ${name}"

                    }

                    echo buildMessage(projectName)

                    try {

                        echo "Executing Groovy Logic"

                        int value = 100

                        echo "Value = ${value}"

                    }
                    catch (Exception ex) {

                        echo "Exception : ${ex.getMessage()}"

                    }
                    finally {

                        echo "Script Block Completed"

                    }

                }

            }

        }

        stage('Build & Execute Tests') {

            tools {

                maven 'Name Maven-3.9.9'

            }

            environment {

                TEST_OWNER = "Automation Team"

            }

            steps {

                echo "Stage Owner : ${TEST_OWNER}"

                retry(2) {

                    bat """
                    mvn ${env.MAVEN_GOAL} ^
                    -Denvironment=${params.Environment} ^
                    -Dbrowser=${params.Browser} ^
                    -Dsuite=${params.Suite} ^
                    -Dheadless=${params.Headless}
                    """

                }

            }

        }

        stage('Publish Test Results') {

            steps {

                junit 'target/surefire-reports/*.xml'

            }

        }

        /*
         * Sprint 23
         *
         * Generate HTML test report from
         * Maven Surefire results.
         */

        stage('Generate HTML Report') {

            steps {

                bat """
                mvn surefire-report:report-only
                """

            }

        }

        /*
         * Sprint 23
         *
         * Publish the generated HTML report
         * inside Jenkins.
         */

        stage('Publish HTML Report') {

            steps {

                publishHTML(
                    target: [
                        allowMissing: false,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'target/reports',
                        reportFiles: 'surefire.html',
                        reportName: 'Selenium Test Report',
                        reportTitles: 'Selenium Automation Test Report'
                    ]
                )

            }

        }

        stage('Archive Artifacts') {

            when {

                expression {

                    params.Suite == "Smoke"

                }

            }

            steps {

                archiveArtifacts(
                    artifacts: 'target/**/*',
                    fingerprint: true
                )

            }

        }

        /*
         * Sprint 22
         *
         * Production approval is required
         * only for PROD.
         */

        stage('Production Approval') {

            when {

                beforeInput true

                expression {

                    params.Environment == 'PROD'

                }

            }

            input {

                message 'Approve Production Deployment?'

                ok 'Approve'

            }

            steps {

                echo 'Production deployment approved.'

            }

        }

        /*
         * Sprint 22
         *
         * Deploy only when:
         *
         * Environment != QA
         * Branch == main
         */

        stage('Deploy') {

            when {

                allOf {

                    expression {

                        params.Environment != 'QA'

                    }

                    expression {

                        env.GIT_BRANCH == 'main'

                    }

                }

            }

            steps {

                script {

                    deployApplication(params.Environment)

                }

            }

            post {

                success {

                    echo "Deployment Successful"

                }

                failure {

                    echo "Deployment Failed"

                }

            }

        }

        stage('Use Secret Credential') {

            steps {

                withCredentials([

                    string(
                        credentialsId: 'dummy-api-key',
                        variable: 'API_KEY'
                    )

                ]) {

                    echo "Credential Loaded Successfully"

                    bat '''
                    echo Secret Credential Used
                    '''

                }

            }

        }

        stage('Parallel Demo') {

            parallel {

                stage('Chrome Validation') {

                    steps {

                        echo "Chrome Stage Running"

                    }

                }

                stage('Edge Validation') {

                    steps {

                        echo "Edge Stage Running"

                    }

                }

            }

        }

        stage('Matrix Demo') {

            matrix {

                axes {

                    axis {

                        name 'BrowserName'

                        values 'Chrome', 'Edge'

                    }

                }

                stages {

                    stage('Matrix Execution') {

                        steps {

                            echo "Running on ${BrowserName}"

                        }

                    }

                }

            }

        }

        stage('Stash Demo') {

            steps {

                stash(
                    name: 'project-files',
                    includes: 'target/**/*'
                )

                echo "Files Stashed"

            }

        }

        stage('Unstash Demo') {

            steps {

                deleteDir()

                unstash 'project-files'

                echo "Files Restored"

            }

        }

    }

    post {

        always {

            echo "Pipeline Execution Completed"

        }

        success {

            echo "Build Completed Successfully"

        }

        failure {

            echo "Build Failed"

        }

    }

}