//#!groovy

/*
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
*/

def test(string) {
    echo "TEST: ${string}"
}