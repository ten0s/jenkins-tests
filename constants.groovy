#!groovy

// git url: 'https://github.com/ten0s/jenkins-tests.git', credentialsId: '5948caec-9a2f-4a0b-a07c-3eb562985758'
// def c = load('constants.groovy')
// def recipients = c.getRecipients()
// echo recipients

def getRecipients() {
  return 'recipient1;recipient2'
}

return this
