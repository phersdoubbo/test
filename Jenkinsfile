#!groovy​

// checkout changes
stage 'checkout'
node{
    checkout scm
    sh './app create-log-group'
}

if (params["ACTION"] == "Cleanup") {
  // cleaup docker
  stage 'cleanup'
  node{
      sh './app clean'
  }
}

if (params["ACTION"] == "Info") {
  // get info
  stage 'info'
  node{
      if (params['DEV_TARGET_GROUP_ARN'] == "NONE") {
        sh './app info'
      } else {
        sh "./app info ${params['DEV_TARGET_GROUP_ARN']}"
      }
  }
}

if (params["ACTION"] == "Build" || params["ACTION"] == null) {
  // start application container and compile application
  stage 'package'
  node {
      sh './app package'
  }
  stage 'execute coverage report'
  node {
      sh './app mvn clean verify'
  }
  stage 'run sonar analysis'
  node {
      try {
          def sonarqubeScannerHome = tool name: 'sonar-runner', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
          sh "${sonarqubeScannerHome}/bin/sonar-scanner -Dsonar.host.url=http://sonarqube.ss-prod.us-east-1.schl.local/"
      } catch (err) {
          echo "Sonar Analysis phase failed. Continuing with deployment"
      }
  }
  // build new image with nested assets
  stage 'build'
  node {
      sh './app build'
  }
  // push new image/configurations and deploy dev application container
  stage 'publish'
  node {
      if (params['DEV_TARGET_GROUP_ARN'] == "NONE" || params['DEV_TARGET_GROUP_ARN'] == null) {
        sh './app publish'
      } else {
        sh "./app publish ${params['DEV_TARGET_GROUP_ARN']}"
      }
  }
  // stop dev container and remove all assets related to build process
  stage 'clean'
  node {
      sh './app clean'
  }
  // display service details
  stage 'info'
  node{
      if (params['DEV_TARGET_GROUP_ARN'] == "NONE") {
        sh './app info'
      } else {
        sh "./app info ${params['DEV_TARGET_GROUP_ARN']}"
      }
  }
}

if (params["ACTION"] == "Promote to QA") {
  // stage 'approve'
  // timeout(time: 15, unit: 'DAYS') {
  //    input message: 'Do you want to promote to QA stage?'
  //}
  stage 'promote to QA stage'
  node{
      if (params['QA_TARGET_GROUP_ARN'] == "NONE") {
        sh './app promote qa'
      } else {
        sh "./app promote qa ${params['QA_TARGET_GROUP_ARN']} ${params['SNAPSHOT_ID']}"
      }
  }

  // display service details
  stage 'info'
  node{
      if (params['QA_TARGET_GROUP_ARN'] == "NONE") {
        sh './app info'
      } else {
        sh "./app info ${params['QA_TARGET_GROUP_ARN']}"
      }
  }

}
/*stage 'approve'
timeout(time: 15, unit: 'DAYS') {
    input message: 'Do you want to promote to PROD stage?'
}

stage 'promote to PROD stage'
node {
    sh './app promote prod'
}*/
