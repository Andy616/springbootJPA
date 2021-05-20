node {
    def app
    stage('Clone repository'){
        checkout scm
    }

    stage('Build and run Junit test') {
        sh 'mvn clean install'
    }
    stage("Build docker image") {
        app = docker.build("andy616/springboot-test")
    }

    stage("Push image") {
        docker.withRegistry('https://registry.hub.docker.com', 'docker-hub') {
            app.push("${env.BUILD_NUMBER}")
            app.push("latest")
        }
    }
}