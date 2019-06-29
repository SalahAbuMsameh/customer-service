
pipeline {
    
    agent any
    
    stages {
        stage('Build') {
            steps {
                bat label: '', script: 'mvn clean compile'
            }
            post {
                success {
                    echo 'Now Archiving...'
                    archiveArtifacts artifacts: '**/target/*.jar'
                }
            }
        }
    }
}