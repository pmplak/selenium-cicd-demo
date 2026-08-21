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

        REPOSITORY =
            'git@github.com:pmplak/selenium-cicd-demo.git'

        MAVEN_GOAL =
            'clean test'
    }


    parameters {

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
         * Sprint 33
         *
         * Determine whether this execution is:
         *
         * 1. Normal Pipeline
         * 2. Multibranch branch build
         * 3. Pull Request build
         */

        stage('Resolve Execution Configuration') {

            steps {

                script {

                    def causes =
                        currentBuild.getBuildCauses()

                    echo "Build Causes : ${causes}"


                    /*
                     * Jenkins automatically supplies CHANGE_ID
                     * for Pull Request builds.
                     */

                    boolean pullRequestBuild =
                        env.CHANGE_ID != null &&
                        env.CHANGE_ID.trim() != ''


                    /*
                     * BRANCH_NAME is normally populated by
                     * Multibranch Pipeline.
                     */

                    boolean multibranchBuild =
                        env.BRANCH_NAME != null &&
                        env.BRANCH_NAME.trim() != ''


                    if (pullRequestBuild) {

                        /*
                         * PR builds are always validation builds.
                         *
                         * We intentionally force safe values.
                         */

                        env.BUILD_TRIGGER =
                            'PULL REQUEST'

                        env.RUN_BUILD_TYPE =
                            'PR'

                        env.RUN_GIT_BRANCH =
                            "${env.CHANGE_BRANCH}"

                        env.RUN_ENVIRONMENT =
                            'QA'

                        env.RUN_BROWSER =
                            'Chrome'

                        env.RUN_SUITE =
                            'Smoke'

                        env.RUN_HEADLESS =
                            'true'
                    }


                    else if (multibranchBuild) {

                        /*
                         * Normal Multibranch branch build.
                         */

                        env.BUILD_TRIGGER =
                            'MULTIBRANCH'

                        env.RUN_BUILD_TYPE =
                            'BRANCH'

                        env.RUN_GIT_BRANCH =
                            "${env.BRANCH_NAME}"

                        env.RUN_ENVIRONMENT =
                            "${params.Environment}"

                        env.RUN_BROWSER =
                            "${params.Browser}"

                        env.RUN_SUITE =
                            "${params.Suite}"

                        env.RUN_HEADLESS =
                            "${params.Headless}"
                    }


                    else {

                        /*
                         * Original single Pipeline job.
                         */

                        env.BUILD_TRIGGER =
                            'MANUAL / SCM / WEBHOOK'

                        env.RUN_BUILD_TYPE =
                            'STANDARD'

                        env.RUN_GIT_BRANCH =
                            "${params.GitBranch}"

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
                     * Fail-fast validation.
                     */

                    if (!env.RUN_GIT_BRANCH ||
                        env.RUN_GIT_BRANCH == 'null') {

                        error(
                            "Execution configuration error: Git branch is missing."
                        )
                    }


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
                            "Execution configuration error: Headless is missing."
                        )
                    }


                    echo "===================================="
                    echo "Sprint 33 - Execution Configuration"
                    echo "Build Type    : ${env.RUN_BUILD_TYPE}"
                    echo "Build Trigger : ${env.BUILD_TRIGGER}"
                    echo "Git Branch    : ${env.RUN_GIT_BRANCH}"
                    echo "Environment   : ${env.RUN_ENVIRONMENT}"
                    echo "Browser       : ${env.RUN_BROWSER}"
                    echo "Suite         : ${env.RUN_SUITE}"
                    echo "Headless      : ${env.RUN_HEADLESS}"
                    echo "===================================="


                    /*
                     * PR-specific Jenkins variables.
                     */

                    if (pullRequestBuild) {

                        echo "===================================="
                        echo "Pull Request Information"
                        echo "PR Number     : ${env.CHANGE_ID}"
                        echo "Source Branch : ${env.CHANGE_BRANCH}"
                        echo "Target Branch : ${env.CHANGE_TARGET}"
                        echo "BRANCH_NAME   : ${env.BRANCH_NAME}"
                        echo "===================================="
                    }
                }
            }
        }


        /*
         * Sprint 32 + Sprint 33
         *
         * Deployment governance.
         */

        stage('Branch Policy') {

            steps {

                script {

                    echo "===================================="
                    echo "Sprint 33 - Branch / PR Policy"


                    if (env.RUN_BUILD_TYPE == 'PR') {

                        echo "Build Type : PULL REQUEST"
                        echo "PR Number  : ${env.CHANGE_ID}"
                        echo "Source     : ${env.CHANGE_BRANCH}"
                        echo "Target     : ${env.CHANGE_TARGET}"
                        echo "Deployment Eligibility : BLOCKED"
                    }


                    else if (env.RUN_GIT_BRANCH == 'main') {

                        echo "Selected Branch : main"
                        echo "Branch Type : MAIN"
                        echo "Deployment Eligibility : ALLOWED"
                    }


                    else if (env.RUN_GIT_BRANCH == 'develop') {

                        echo "Selected Branch : develop"
                        echo "Branch Type : DEVELOPMENT"
                        echo "Deployment Eligibility : BLOCKED"
                    }


                    else {

                        /*
                         * Any other branch is treated safely
                         * as a non-deployment branch.
                         */

                        echo "Selected Branch : ${env.RUN_GIT_BRANCH}"
                        echo "Branch Type : FEATURE / OTHER"
                        echo "Deployment Eligibility : BLOCKED"
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

                echo "Build Type  : ${env.RUN_BUILD_TYPE}"
                echo "Branch      : ${env.RUN_GIT_BRANCH}"

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
         * Sprint 33
         *
         * IMPORTANT:
         *
         * Multibranch / PR:
         *     checkout scm
         *
         * Standard Pipeline:
         *     git(branch: ...)
         */

        stage('Checkout Source Code') {

            steps {

                script {

                    cleanWs()


                    if (env.RUN_BUILD_TYPE == 'PR' ||
                        env.RUN_BUILD_TYPE == 'BRANCH') {

                        echo "Using Jenkins Multibranch SCM context"

                        checkout scm


                        if (env.RUN_BUILD_TYPE == 'PR') {

                            echo "Checked out Pull Request #${env.CHANGE_ID}"
                            echo "PR Source Branch : ${env.CHANGE_BRANCH}"
                            echo "PR Target Branch : ${env.CHANGE_TARGET}"
                        }
                        else {

                            echo "Checked out Multibranch branch: ${env.BRANCH_NAME}"
                        }
                    }


                    else {

                        echo "Using standard parameterized checkout"

                        git(
                            branch: env.RUN_GIT_BRANCH,
                            credentialsId: 'github-ssh',
                            url: env.REPOSITORY
                        )

                        echo "Checked out branch: ${env.RUN_GIT_BRANCH}"
                    }
                }
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


        stage('Quality Gate') {

            steps {

                script {

                    echo "===================================="
                    echo "Quality Gate"
                    echo "Build Type : ${env.RUN_BUILD_TYPE}"
                    echo "Branch : ${env.RUN_GIT_BRANCH}"
                    echo "Environment : ${env.RUN_ENVIRONMENT}"
                    echo "Current Build Result : ${currentBuild.currentResult}"
                    echo "===================================="


                    if (currentBuild.currentResult != 'SUCCESS') {


                        if (env.RUN_BUILD_TYPE == 'PR') {

                            error(
                                "PR Quality Gate Failed - Pull Request validation failed."
                            )
                        }


                        else if (env.RUN_ENVIRONMENT == 'QA') {

                            error(
                                "QA Quality Gate Failed - Automated tests did not pass."
                            )
                        }


                        else if (env.RUN_ENVIRONMENT == 'UAT') {

                            error(
                                "UAT Quality Gate Failed - Deployment blocked."
                            )
                        }


                        else if (env.RUN_ENVIRONMENT == 'PROD') {

                            error(
                                "PROD Quality Gate Failed - Production deployment blocked."
                            )
                        }
                    }


                    if (env.RUN_BUILD_TYPE == 'PR') {

                        echo "PR Quality Gate PASSED"
                        echo "Pull Request is technically validated."
                        echo "Deployment is not permitted for Pull Requests."
                    }


                    else if (env.RUN_ENVIRONMENT == 'QA') {

                        echo "QA Quality Gate PASSED"
                        echo "QA deployment is not applicable."
                    }


                    else if (env.RUN_ENVIRONMENT == 'UAT') {

                        echo "UAT Quality Gate PASSED"
                        echo "Branch policy will make final deployment decision."
                    }


                    else if (env.RUN_ENVIRONMENT == 'PROD') {

                        echo "PROD Quality Gate PASSED"
                        echo "Branch policy will decide production eligibility."
                    }


                    echo "===================================="
                    echo "Quality Gate Completed Successfully"
                    echo "===================================="
                }
            }
        }


        /*
         * PRs can NEVER request production approval.
         */

        stage('Production Approval') {

            when {

                beforeInput true


                allOf {

                    expression {

                        env.RUN_BUILD_TYPE != 'PR'
                    }


                    expression {

                        env.RUN_ENVIRONMENT == 'PROD'
                    }


                    expression {

                        env.RUN_GIT_BRANCH == 'main'
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
         * Sprint 33 deployment policy:
         *
         * PR       → NEVER
         * develop  → NEVER
         * feature  → NEVER
         * QA       → NEVER
         *
         * main + UAT/PROD → eligible
         */

        stage('Deploy') {

            when {

                allOf {


                    expression {

                        env.RUN_BUILD_TYPE != 'PR'
                    }


                    expression {

                        env.RUN_ENVIRONMENT != 'QA'
                    }


                    expression {

                        env.RUN_GIT_BRANCH == 'main'
                    }
                }
            }


            steps {

                script {

                    echo "===================================="
                    echo "Deployment Governance"
                    echo "Build Type  : ${env.RUN_BUILD_TYPE}"
                    echo "Branch      : ${env.RUN_GIT_BRANCH}"
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

                <p><b>Build Type:</b> ${env.RUN_BUILD_TYPE}</p>
                <p><b>Trigger:</b> ${env.BUILD_TRIGGER}</p>

                <p><b>Git Branch:</b> ${env.RUN_GIT_BRANCH}</p>

                <p><b>PR Number:</b> ${env.CHANGE_ID ?: 'N/A'}</p>
                <p><b>PR Target:</b> ${env.CHANGE_TARGET ?: 'N/A'}</p>

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

                <p><b>Build Type:</b> ${env.RUN_BUILD_TYPE}</p>
                <p><b>Trigger:</b> ${env.BUILD_TRIGGER}</p>

                <p><b>Git Branch:</b> ${env.RUN_GIT_BRANCH}</p>

                <p><b>PR Number:</b> ${env.CHANGE_ID ?: 'N/A'}</p>
                <p><b>PR Target:</b> ${env.CHANGE_TARGET ?: 'N/A'}</p>

                <p><b>Environment:</b> ${env.RUN_ENVIRONMENT}</p>
                <p><b>Browser:</b> ${env.RUN_BROWSER}</p>
                <p><b>Suite:</b> ${env.RUN_SUITE}</p>

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

                <p><b>Build Type:</b> ${env.RUN_BUILD_TYPE}</p>
                <p><b>Trigger:</b> ${env.BUILD_TRIGGER}</p>

                <p><b>Git Branch:</b> ${env.RUN_GIT_BRANCH}</p>

                <p><b>PR Number:</b> ${env.CHANGE_ID ?: 'N/A'}</p>
                <p><b>PR Target:</b> ${env.CHANGE_TARGET ?: 'N/A'}</p>

                <p><b>Environment:</b> ${env.RUN_ENVIRONMENT}</p>
                <p><b>Browser:</b> ${env.RUN_BROWSER}</p>
                <p><b>Suite:</b> ${env.RUN_SUITE}</p>

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