def call(Map vars, Closure body=null) {
  vars = vars ?: [:]
  node(vars.get("label", null)) {
    withEnv(["WORKSPACE=${sh(script: 'pwd', returnStdout: true).trim()}"]) {
      withDockerRegistry(url: vars.get("registryUrl", "https://index.docker.io/v1/"), credentialsId: vars.get("registryCreds", "dockerbuildbot-index.docker.io")) {
        wrap([$class: 'TimestamperBuildWrapper']) {
          wrap([$class: 'AnsiColorBuildWrapper']) {
            if (body) { body() }
          }
        }
      }
    }
  }
}
