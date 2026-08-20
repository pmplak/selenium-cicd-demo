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


    /*
     * Sprint 30 / 31 timer removed.
     *
     * No automatic cron execution now.
     */


    tools {

        maven 'Name Maven-3.9.9'
    }


    environment {

        BUILD_OWNER = 'Petchimuthu Pandiyan'

        REPOSITORY =
            'git@github.com:pmplak/selenium-cicd-demo.git'

        MAVEN_GOAL =
            'clean test'
    }


    parameters {

        /*
         * Sprint 32
         *
         * Branch to build.
         */

        choice(
            name: 'GitBranch',
            choices: ['main', 'develop'],
            description: 'Select Git branch to build'
        )


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


        /*
         * Sprint 31
         *
         * Detect how the build was started and
         * resolve the actual execution settings.
         */

        stage('Resolve Execution Configuration') {

            steps {

                script {

                    def causes =
                        currentBuild.getBuildCauses()

                    echo "Build Causes : ${causes}"


                    boolean timerTriggered =
                        causes.any { cause ->

                            cause._class ==
                                'hudson.triggers.TimerTrigger$TimerTriggerCause'
                        }


                    if (timerTriggered) {

                        env.BUILD_TRIGGER =
                            'TIMER'

                        env.RUN_ENVIRONMENT =
                            'QA'

                        env.RUN_BROWSER =
                            'Chrome'

                        env.RUN_SUITE =
                            'Regression'

                        env.RUN_HEADLESS =
                            'true'
                    }
                    else {

                        env.BUILD_TRIGGER =
                            'MANUAL / SCM / WEBHOOK'

                        env.RUN_ENVIRONMENT =
                            "${params.Environment}"

                        env.RUN_BROWSER =
                            "${params.Browser}"

                        env.RUN_SUITE =
                            "${params.Suite}"

                        env.RUN_HEADLESS =
                            "${params.Headless}"
                    }


                    /*
                     * Fail-fast configuration validation.
                     */

                    if (!env.RUN_ENVIRONMENT ||
                        env.RUN_ENVIRONMENT == 'null') {

                        error(
                            "Execution configuration error: Environment is missing."
                        )
                    }


                    if (!env.RUN_BROWSER ||
                        env.RUN_BROWSER == 'null') {

                        error(
                            "Execution configuration error: Browser is missing."
                        )
                    }


                    if (!env.RUN_SUITE ||
                        env.RUN_SUITE == 'null') {

                        error(
                            "Execution configuration error: Suite is missing."
                        )
                    }


                    if (!env.RUN_HEADLESS ||
                        env.RUN_HEADLESS == 'null') {

                        error(
                            "Execution configuration error: Headless value is missing."
                        )
                    }


                    if (!params.GitBranch ||
                        params.GitBranch == 'null') {

                        error(
                            "Execution configuration error: Git branch is missing."
                        )
                    }


                    echo "===================================="
                    echo "Sprint 31/32 - Execution Configuration"
                    echo "Build Trigger : ${env.BUILD_TRIGGER}"
                    echo "Git Branch    : ${params.GitBranch}"
                    echo "Environment   : ${env.RUN_ENVIRONMENT}"
                    echo "Browser       : ${env.RUN_BROWSER}"
                    echo "Suite         : ${env.RUN_SUITE}"
                    echo "Headless      : ${env.RUN_HEADLESS}"
                    echo "===================================="
                }
            }
        }


        /*
         * Sprint 32
         *
         * Explicit branch governance information.
         */

        stage('Branch Policy') {

            steps {

                script {

                    echo "===================================="
                    echo "Sprint 32 - Branch Policy"
                    echo "Selected Branch : ${params.GitBranch}"


                    if (params.GitBranch == 'main') {

                        echo "Branch Type : MAIN"
                        echo "Deployment Eligibility : ALLOWED"
                    }
                    else if (params.GitBranch == 'develop') {

                        echo "Branch Type : DEVELOPMENT"
                        echo "Deployment Eligibility : BLOCKED"
                    }
                    else {

                        error(
                            "Unsupported Git branch: ${params.GitBranch}"
                        )
                    }


                    echo "===================================="
                }
            }
        }


        stage('Environment Information') {

            steps {

                echo "================================="
                echo "Build Owner : ${env.BUILD_OWNER}"
                echo "Repository  : ${env.REPOSITORY}"
                echo "Branch      : ${params.GitBranch}"
                echo "Maven Goal  : ${env.MAVEN_GOAL}"

                echo "Build Trigger : ${env.BUILD_TRIGGER}"

                echo "Environment : ${env.RUN_ENVIRONMENT}"
                echo "Browser     : ${env.RUN_BROWSER}"
                echo "Suite       : ${env.RUN_SUITE}"
                echo "Headless    : ${env.RUN_HEADLESS}"

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


        /*
         * Sprint 32
         *
         * Checkout the branch selected by the user.
         */

        stage('Checkout Source Code') {

            steps {

                cleanWs()

                git(
                    branch: params.GitBranch,
                    credentialsId: 'github-ssh',
                    url: env.REPOSITORY
                )

                echo "Checked out branch: ${params.GitBranch}"
            }
        }


        stage('Browser Validation') {

            steps {

                script {

                    if (env.RUN_BROWSER == "Chrome") {

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

                    def projectName =
                        "Selenium CI/CD"

                    def version =
                        "1.0"

                    echo "Project : ${projectName}"
                    echo "Version : ${version}"


                    def browsers =
                        ["Chrome", "Edge"]


                    for (browser in browsers) {

                        echo "Supported Browser : ${browser}"
                    }


                    def buildMessage = { name ->

                        return "Welcome ${name}"
                    }


                    echo buildMessage(projectName)


                    try {

                        echo "Executing Groovy Logic"

                        int value =
                            100

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


        /*
         * Sprint 24 + Sprint 31
         *
         * Test failure becomes UNSTABLE first so
         * reports can still be generated.
         */

        stage('Build & Execute Tests') {

            tools {

                maven 'Name Maven-3.9.9'
            }


            environment {

                TEST_OWNER =
                    "Automation Team"
            }


            steps {

                echo "Stage Owner : ${TEST_OWNER}"


                catchError(
                    buildResult: 'UNSTABLE',
                    stageResult: 'FAILURE'
                ) {

                    retry(2) {

                        bat """
                        mvn ${env.MAVEN_GOAL} ^
                        -Denvironment=${env.RUN_ENVIRONMENT} ^
                        -Dbrowser=${env.RUN_BROWSER} ^
                        -Dsuite=${env.RUN_SUITE} ^
                        -Dheadless=${env.RUN_HEADLESS}
                        """
                    }
                }
            }
        }


        stage('Publish Test Results') {

            steps {

                junit(
                    testResults:
                        'target/surefire-reports/*.xml',

                    allowEmptyResults:
                        false
                )
            }
        }


        stage('Verify Custom HTML Report') {

            steps {

                bat """
                if not exist target\\custom-report\\index.html (
                    echo Custom HTML report was not generated.
                    exit /b 1
                )

                echo Custom HTML report found successfully.
                echo Report Location:
                echo target\\custom-report\\index.html
                """
            }
        }


        stage('Publish Custom HTML Report') {

            steps {

                publishHTML(

                    target: [

                        allowMissing:
                            false,

                        alwaysLinkToLastBuild:
                            true,

                        keepAll:
                            true,

                        reportDir:
                            'target/custom-report',

                        reportFiles:
                            'index.html',

                        reportName:
                            'Custom Selenium Automation Report',

                        reportTitles:
                            'SauceDemo Automation Execution Report'
                    ]
                )
            }
        }


        stage('Archive Artifacts') {

            when {

                expression {

                    env.RUN_SUITE == "Smoke"
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
         * Environment-aware Quality Gate.
         */

        stage('Quality Gate') {

            steps {

                script {

                    echo "===================================="
                    echo "Sprint 25 - Quality Gate"
                    echo "Branch : ${params.GitBranch}"
                    echo "Environment : ${env.RUN_ENVIRONMENT}"
                    echo "Current Build Result : ${currentBuild.currentResult}"
                    echo "===================================="


                    if (currentBuild.currentResult != 'SUCCESS') {


                        if (env.RUN_ENVIRONMENT == 'QA') {

                            error(
                                "QA Quality Gate Failed - Automated tests did not pass."
                            )
                        }


                        else if (env.RUN_ENVIRONMENT == 'UAT') {

                            error(
                                "UAT Quality Gate Failed - UAT deployment is blocked."
                            )
                        }


                        else if (env.RUN_ENVIRONMENT == 'PROD') {

                            error(
                                "PROD Quality Gate Failed - Production deployment is blocked."
                            )
                        }
                    }


                    if (env.RUN_ENVIRONMENT == 'QA') {

                        echo "QA Quality Gate PASSED"
                        echo "QA deployment is not applicable."
                    }


                    else if (env.RUN_ENVIRONMENT == 'UAT') {

                        echo "UAT Quality Gate PASSED"
                        echo "Environment is deployment eligible."
                        echo "Branch policy will make final deployment decision."
                    }


                    else if (env.RUN_ENVIRONMENT == 'PROD') {

                        echo "PROD Quality Gate PASSED"
                        echo "Branch policy will decide whether production approval is allowed."
                    }


                    echo "===================================="
                    echo "Quality Gate Completed Successfully"
                    echo "===================================="
                }
            }
        }


        /*
         * Sprint 22 + Sprint 32
         *
         * Production approval is allowed ONLY when:
         *
         * Environment = PROD
         * AND
         * Branch = main
         *
         * develop must never request production approval.
         */

        stage('Production Approval') {

            when {

                beforeInput true


                allOf {

                    expression {

                        env.RUN_ENVIRONMENT == 'PROD'
                    }


                    expression {

                        params.GitBranch == 'main'
                    }
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
         * Sprint 32
         *
         * Deployment policy:
         *
         * QA      -> Never deploy
         *
         * UAT     -> Deploy only from main
         *
         * PROD    -> Deploy only from main
         *            and after approval
         *
         * develop -> Never deploy
         */

        stage('Deploy') {

            when {

                allOf {


                    expression {

                        env.RUN_ENVIRONMENT != 'QA'
                    }


                    expression {

                        params.GitBranch == 'main'
                    }
                }
            }


            steps {

                script {

                    echo "===================================="
                    echo "Sprint 32 - Deployment Governance"
                    echo "Branch      : ${params.GitBranch}"
                    echo "Environment : ${env.RUN_ENVIRONMENT}"
                    echo "Decision    : DEPLOYMENT ALLOWED"
                    echo "===================================="


                    deployApplication(
                        env.RUN_ENVIRONMENT
                    )
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


            emailext(

                subject:
                    "SUCCESS - ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}",

                body: """
                <h2>Jenkins Build Successful</h2>

                <p><b>Project:</b> ${env.JOB_NAME}</p>
                <p><b>Build Number:</b> ${env.BUILD_NUMBER}</p>

                <p><b>Trigger:</b> ${env.BUILD_TRIGGER}</p>

                <p><b>Git Branch:</b> ${params.GitBranch}</p>

                <p><b>Environment:</b> ${env.RUN_ENVIRONMENT}</p>
                <p><b>Browser:</b> ${env.RUN_BROWSER}</p>
                <p><b>Suite:</b> ${env.RUN_SUITE}</p>
                <p><b>Headless:</b> ${env.RUN_HEADLESS}</p>

                <p><b>Status:</b> SUCCESS</p>

                <p>
                    <a href="${env.BUILD_URL}">
                        Open Jenkins Build
                    </a>
                </p>
                """,

                mimeType:
                    'text/html',

                to:
                    'pmplak0123@gmail.com'
            )
        }


        failure {

            echo "Build Failed"


            emailext(

                subject:
                    "FAILURE - ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}",

                body: """
                <h2>Jenkins Build Failed</h2>

                <p><b>Project:</b> ${env.JOB_NAME}</p>
                <p><b>Build Number:</b> ${env.BUILD_NUMBER}</p>

                <p><b>Trigger:</b> ${env.BUILD_TRIGGER}</p>

                <p><b>Git Branch:</b> ${params.GitBranch}</p>

                <p><b>Environment:</b> ${env.RUN_ENVIRONMENT}</p>
                <p><b>Browser:</b> ${env.RUN_BROWSER}</p>
                <p><b>Suite:</b> ${env.RUN_SUITE}</p>
                <p><b>Headless:</b> ${env.RUN_HEADLESS}</p>

                <p><b>Status:</b> FAILURE</p>

                <p>
                    <a href="${env.BUILD_URL}">
                        Open Jenkins Build
                    </a>
                </p>
                """,

                mimeType:
                    'text/html',

                to:
                    'pmplak0123@gmail.com'
            )
        }


        unstable {

            echo "Build Marked UNSTABLE"


            emailext(

                subject:
                    "UNSTABLE - ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}",

                body: """
                <h2>Jenkins Build Unstable</h2>

                <p><b>Project:</b> ${env.JOB_NAME}</p>
                <p><b>Build Number:</b> ${env.BUILD_NUMBER}</p>

                <p><b>Trigger:</b> ${env.BUILD_TRIGGER}</p>

                <p><b>Git Branch:</b> ${params.GitBranch}</p>

                <p><b>Environment:</b> ${env.RUN_ENVIRONMENT}</p>
                <p><b>Browser:</b> ${env.RUN_BROWSER}</p>
                <p><b>Suite:</b> ${env.RUN_SUITE}</p>
                <p><b>Headless:</b> ${env.RUN_HEADLESS}</p>

                <p><b>Status:</b> UNSTABLE</p>

                <p>
                    <a href="${env.BUILD_URL}">
                        Open Jenkins Build
                    </a>
                </p>
                """,

                mimeType:
                    'text/html',

                to:
                    'pmplak0123@gmail.com'
            )
        }
    }
}