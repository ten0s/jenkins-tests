#!groovy

def BUILD_IMAGE = "312226949769.dkr.ecr.us-east-1.amazonaws.com/centos7.x86_64:latest"

node('dev_linux_awscli_docker') {
    try {
        sh 'sudo yum -y install git'

        checkout scm

        def image = load "ecr-image.groovy"
        def outDir = image.insideWith(BUILD_IMAGE) { outDir ->
            sh "date > ${outDir}/test"
        }

        sh 'ls -la'

        sh "cat ${outDir}/test"

        /*
        sh "aws s3 cp ${outDir}/test s3://idtq-deployment-jenkins-hermes/"
        */

        dir(outDir) {
          deleteDir()
        }
    }
    catch (err) {
        currentBuild.result = 'FAILURE'
        throw err
    }
}
