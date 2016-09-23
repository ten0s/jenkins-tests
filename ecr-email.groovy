#!groovy

def BUILD_IMAGE = "312226949769.dkr.ecr.us-east-1.amazonaws.com/centos7.x86_64:latest"

node('dev_linux_awscli_docker') {
    try {
        sh 'ls -l'

        sh 'sudo yum -y install git'

        checkout scm

        def image = load "ecr-image.groovy"
        def out_dir = image.runInside(BUILD_IMAGE) { _out_dir ->
            sh "date > test"
            stash name: 'result', includes: 'test'
        }

        unstash name: 'result'
        sh "cat test"

        /*
        sh "aws s3 cp ${out_dir}/test s3://idtq-deployment-jenkins-hermes/"
        */

        deleteDir()
    }
    catch (err) {
        currentBuild.result = 'FAILURE'
        throw err
    }
}
