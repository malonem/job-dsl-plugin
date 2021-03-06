package javaposse.jobdsl.plugin

import spock.lang.*
import javaposse.jobdsl.dsl.DslScriptLoader
import javaposse.jobdsl.dsl.JobManagement
import javaposse.jobdsl.dsl.FileJobManagement
import javaposse.jobdsl.dsl.ScriptRequest

class WorkspaceProtocolSpec  extends Specification {


    def 'load workspace url'() {
        when:
        URL url = new URL(null, "workspace://JOB/dir/file.dsl", new WorkspaceUrlHandler())

        then:
        url.host == 'JOB'
        url.file == '/dir/file.dsl'
    }

    def 'determine nested job name'() {
        when:
        WorkspaceUrlConnection workspaceUrlConnection = new WorkspaceUrlConnection(new URL(null, "workspace://FOLDER.JOB/dir/file.dsl", new WorkspaceUrlHandler()))

        then:
        "FOLDER/JOB" == workspaceUrlConnection.getNestedJobName()
    }

    def 'reference workspace form dsl'() {
        def resourcesDir = new File("src/test/resources")
        JobManagement jm = new FileJobManagement(resourcesDir)
        URL url = new URL(null, "workspace://JOB/dir/file.dsl", new WorkspaceUrlHandler())

        setup:
        ScriptRequest request = new ScriptRequest('caller.dsl', null, url);

        when:
        DslScriptLoader.runDslEngine(request, jm)

        then:
        thrown(IllegalStateException)
    }
}
