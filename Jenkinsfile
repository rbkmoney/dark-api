#!groovy
build('dark-api', 'java-maven') {
    checkoutRepo()
    loadBuildUtils()

    def javaServicePipeline
    runStage('load JavaService pipeline') {
        javaServicePipeline = load("build_utils/jenkins_lib/pipeDefault.groovy")
    }

    def serviceName = env.REPO_NAME
    def mvnArgs = '-DjvmArgs="-Xmx256m"'
    def useJava11 = true
    def registry = 'dr2.rbkmoney.com'
    def registryCredsId = 'jenkins_harbor'

    javaServicePipeline() {
        sh 'mvn org.cyclonedx:cyclonedx-maven-plugin:makeBom -Dcyclonedx.skipAttach=true'
    }
}
