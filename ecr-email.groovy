#!groovy

node('dev_linux_awscli_docker') {
    try {
        sh 'sudo yum -y install git'

        checkout scm
        sh 'pwd'
        sh 'ls -l'

        def ecrImage = load "/home/ec2-user/workspace/pipeline/ecr-image.groovy"
        ecrImage.test("print me")

        /*
        def image = new ECRImage("312226949769.dkr.ecr.us-east-1.amazonaws.com/centos7.x86_64:latest")
        def out_dir = image.inside(this) { out_dir ->
            sh "date > ${out_dir}/test"
        }

        sh "cat ${out_dir}/test"
        sh "aws s3 cp ${out_dir}/test s3://idtq-deployment-jenkins-hermes/"
        */
    }
    catch (e) {
        currentBuild.result = 'FAILURE'
        throw e
    }
/*
    finally {
        to = env.CHANGE_AUTHOR_EMAIL ?: 'd.klionsky@belitsoft.com'
        status = currentBuild.result ?: 'SUCCESS'
        subject = "${status}: Job ${env.JOB_NAME} #${env.BUILD_NUMBER}"
        body = """
        <p>See Job ${env.JOB_NAME} #${env.BUILD_NUMBER} <a href='${env.BUILD_URL}console'>console output</a></p>
        """
        emailext to: "${to}", subject: "${subject}", body: "${body}"
    }
*/
}
