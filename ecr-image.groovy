#!groovy

class ECRImage implements Serializable {
    def image = null
    def registryId = null
    def region = null

    ECRImage(image) {
        this.image = image

        def parsed = image.tokenize('.')
        this.registryId = parsed[0]
        this.region = parsed[3]
    }
}

def insideWith(image, closure) {
    def img = new ECRImage(image)

    sh "aws ecr get-login --region ${img.region} --registry-ids ${img.registryId} | sh"
    sh "docker pull ${img.image}"

    def out_dir = sh(returnStdout: true, script: 'mktemp -d -p $(pwd)').trim()

    def container = docker.image(img.image)
    container.inside("-u root -v ${out_dir}:/out/") {
        closure('/out/')
    }

    return out_dir
}

def runWith(image, closure) {
    def img = new ECRImage(image)

    sh "aws ecr get-login --region ${img.region} --registry-ids ${img.registryId} | sh"
    sh "docker pull ${img.image}"

    def out_dir = sh(returnStdout: true, script: 'mktemp -d -p $(pwd)').trim()

    def container = docker.image(img.image)
    container.withRun("-u root -v ${out_dir}:/out/") {
        closure('/out/')
    }

    return out_dir
}

return this
