
pipeline {
    
    agent any
    
    stages {
        stage('Build') {
            steps {
                bat label: '', script: 'mvn -X clean compile'
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