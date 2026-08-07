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

                echo "========================================="
                echo "Build Owner : ${env.BUILD_OWNER}"
                echo "Repository  : ${env.REPOSITORY}"
                echo "Branch      : ${env.GIT_BRANCH}"
                echo "Maven Goal  : ${env.MAVEN_GOAL}"
                echo "========================================="

            }

        }

        stage('Checkout Source Code') {

            steps {

                git branch: env.GIT_BRANCH,
                    credentialsId: 'github-ssh',
                    url: env.REPOSITORY

            }

        }

        stage('Build & Execute Tests') {

            steps {

                bat """
                mvn ${env.MAVEN_GOAL} ^
                -Denvironment=${params.Environment} ^
                -Dbrowser=${params.Browser} ^
                -Dsuite=${params.Suite} ^
                -Dheadless=${params.Headless}
                """

            }

        }

        stage('Publish Test Results') {

            steps {

                junit 'target/surefire-reports/*.xml'

            }

        }

        stage('Archive Artifacts') {

            steps {

                archiveArtifacts artifacts: 'target/**/*',
                                 fingerprint: true

            }

        }

    }

    post {

        always {

            echo 'Pipeline execution completed.'

        }

        success {

            echo 'Build completed successfully.'

        }

        failure {

            echo 'Build failed.'

        }

    }

}