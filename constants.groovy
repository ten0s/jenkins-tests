#!groovy

// def repoUrl = 'https://github.com/ten0s/jenkins-tests.git'
// def credId = '5948caec-9a2f-4a0b-a07c-3eb562985758' or null
// def nodeLabel = 'qa_linux_awscli_docker' // if '' call hangs
//
// def c;
//
// git url: repoUrl, credentialsId: credId
// def c = load('constants.groovy')
//
// OR
//
// c = fileLoader.fromGit('constants.groovy', repoUrl, 'master', credId, nodeLabel)
//
// OR
//
// fileLoader.withGit(repoUrl, 'master', credId, nodeLabel) {
//   c = load('constants.groovy')
// }
//
// def recipients = c.getRecipients()
// echo recipients

def getRecipients() {
  return 'recipient1;recipient2'
}

return this
