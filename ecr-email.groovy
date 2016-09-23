#!groovy

def BUILD_IMAGE = "312226949769.dkr.ecr.us-east-1.amazonaws.com/centos7.x86_64:latest"

node('dev_linux_awscli_docker') {
    try {
        sh 'sudo yum -y install git'

        checkout scm

        def image = load "ecr-image.groovy"
        def out_dir = image.insideWith(BUILD_IMAGE) { out_dir ->
            sh "date > ${out_dir}/test"
        }

        sh 'ls -la'

        sh "cat ${out_dir}/test"

        /*
        sh "aws s3 cp ${out_dir}/test s3://idtq-deployment-jenkins-hermes/"
        */

        dir(out_dir) {
          deleteDir()
        }
    }
    catch (err) {
        currentBuild.result = 'FAILURE'
        throw err
    }
}
