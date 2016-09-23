#!groovy

class ECRImage implements Serializable {
    def image
    def registryId
    def region

    ECRImage(image) {
        this.image = image

        def parsed = image.tokenize('.')
        this.registryId = parsed[0]
        this.region = parsed[3]
    }

    // https://support.cloudbees.com/hc/en-us/articles/217736618-How-do-I-access-Pipeline-DSLs-from-inside-a-Groovy-class
    def pull(script) {
      script.sh "aws ecr get-login --region ${region} --registry-ids ${registryId} | sh"
      script.sh "docker pull ${image}"
    }

    def inside(script, closure) {
        pull(script)

        // create tmp dir and return its name
        def out_dir = script.sh(returnStdout: true, script: 'mktemp -d -p $(pwd)').trim()

        def container = script.docker.image(image)
        container.inside("-u root -v ${out_dir}:/out/") {
            closure('/out/')
        }

        return out_dir
    }
}

node('dev_linux_awscli_docker') {
    try {
        def image = new ECRImage("312226949769.dkr.ecr.us-east-1.amazonaws.com/centos7.x86_64:latest")
        def out_dir = image.inside(this) { out_dir ->
            sh "date > ${out_dir}/test"
        }

        sh "cat ${out_dir}/test"
        sh "aws s3 cp ${out_dir}/test s3://idtq-deployment-jenkins-hermes/"
    }
    catch (e) {
        currentBuild.result = 'FAILURE'
        error "${e}"
    }
    finally {
        to = env.CHANGE_AUTHOR_EMAIL ?: 'd.klionsky@belitsoft.com'
        status = currentBuild.result ?: 'SUCCESS'
        subject = "${status}: Job ${env.JOB_NAME} #${env.BUILD_NUMBER}"
        body = """
        <p>See Job ${env.JOB_NAME} #${env.BUILD_NUMBER} <a href='${env.BUILD_URL}console'>console output</a></p>
        """
        emailext to: "${to}", subject: "${subject}", body: "${body}"
    }
}
