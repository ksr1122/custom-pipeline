package org.common

class BasePipeline implements IPipeline {

    private def script

    private def repo
    private def manifest
    private def jobResult

    def BasePipeline(script) {
        this.script = script
        println "BasePipeline"
    }

    def Setup() {
        script.stage("Setup") {
            script.sh 'echo "Setup base"'
        }
    }

    def Source() {
        script.stage("Source") {
            script.sh 'echo "Source base"'
        }
    }

    def Build() {
        script.stage("Build") {
            script.sh 'echo "Build base"'
        }
    }

    def PostBuild() {
        script.stage("PostBuild") {
            script.sh 'echo "PostBuild base"'
        }
    }

    def Run() {
        try {
            RunStages()
            jobResult = "Succeeded"
        } catch(hudson.AbortException e) {
            printExceptionString(e)
			jobResult = "Failed"
			throw e
		} catch(org.jenkinsci.plugins.workflow.steps.FlowInterruptedException e) {
			printExceptionString(e)
			def list = e.getCauses()
			for(jenkins.model.CauseOfInterruption cause : list) {
				println cause.toString()
				println cause.getShortDescription()
			}
			jobResult = e.getCauses()[0].getShortDescription()
			if(!jobResult.startsWith("Aborted") && !jobResult.startsWith("Timeout")) {
				jobResult = "Interrupted"
			}
			throw e
		} catch(e) {
			printExceptionString(e)
			jobResult = "Failed"
			throw e
		} finally {
			println "JobResult " + jobResult
		}
    }

    private def RunStages() {
        Setup()
        Source()
        Build()
        PostBuild()
    }

    private def printExceptionString(e) {
		println e.toString()
		println e.getCause().toString()
		println e.getMessage()
		println e.getResult().toString()
    }
    
}

