pipeline {
  agent any
  stages {
    stage('Clone') {
      steps {
        git(url: 'https://github.com/rezapurnamaa/selenium-automation-testing.git', branch: 'inten')
      }
    }
    stage('Build') {
      tools {
        maven 'testMaven'
      }
      steps {
        sh 'mvn -B -DskipTests clean package'
        sh 'docker images'
      }
    }
    stage('Test') {
      steps {
        sh '''
              set +e

              rm -rf test-output/
              rm -rf screenshots/
              rm Test_Execution_Report.html

              docker run --env SELENIUM_HUB=10.111.100.148 --name container-test infoloblabs/gap-oracle-selenium:fail || error=true

              docker cp container-test:/usr/share/tag/test-output/ .
              docker cp container-test:/Test_Execution_Report.html .
              docker cp container-test:/screenshots/ .

              docker rm -f container-test


              if [ $error ]
              then
              	exit -1
              fi
            '''
      }
    }
    stage('Publishing') {
      steps {
        script {
          docker.withRegistry('https://registry.hub.docker.com', 'docker-hub-credentials') {
            sh 'docker push rezaarief/gap-oracle-selenium:fail'
          }
        }

      }
    }
  }
}
