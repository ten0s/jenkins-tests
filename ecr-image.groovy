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

def runInside(image, closure) {
    def img = new ECRImage(image)

    sh "aws ecr get-login --region ${img.region} --registry-ids ${img.registryId} | sh"
    sh "docker pull ${img.image}"

    def container = docker.image(img.image)
    container.inside("-u root") {
        closure()
    }
}

return this
