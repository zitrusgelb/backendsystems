pipeline {
    agent {
        label 'docker'
    }
    tools {
        maven 'maven-3.9'
        jdk 'jdk-23'
    }
    stages {
        stage('Clone Git') {
            steps {
                checkout scm
                step([$class: 'GitHubSetCommitStatusBuilder'])
            }
        }

        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
    }

    post {
        failure {
            step([
                $class: "GitHubCommitStatusSetter",
                reposSource: [$class: "ManuallyEnteredRepositorySource", url: "https://github.com/zitrusgelb/backendsystems"],
                errorHandlers: [[$class: "ChangingBuildStatusErrorHandler", result: "UNSTABLE"]],
                statusResultSource: [ $class: "ConditionalStatusResultSource", results: [[$class: "AnyBuildResult", message: "Build failed", , state: "FAILURE"]] ]
            ])
        }

        success {
            step([
                  $class: "GitHubCommitStatusSetter",
                  reposSource: [$class: "ManuallyEnteredRepositorySource", url: "https://github.com/zitrusgelb/backendsystems"],
                  errorHandlers: [[$class: "ChangingBuildStatusErrorHandler", result: "UNSTABLE"]],
                  statusResultSource: [ $class: "ConditionalStatusResultSource", results: [[$class: "AnyBuildResult", message: "Build complete", , state: "SUCCESS"]] ]
            ])
        }
    }
}