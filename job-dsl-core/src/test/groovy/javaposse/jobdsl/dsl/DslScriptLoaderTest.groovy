package javaposse.jobdsl.dsl;

import spock.lang.*

public class DslScriptLoaderTest extends Specification {
    def resourcesDir = new File("src/test/resources")
    JobManagement jm = new FileJobManagement(resourcesDir)

    def 'load template from MarkupBuilder'() {
        setup:
        Job job = new Job(jm)

        // TODO
    }

    def 'load template from file'() {
        setup:
        Job job = new Job(jm)

        when:
        job.using('config') // src/test/resources/config.xml

        then:
        noExceptionThrown()
    }

    def 'configure block without template'() {
        setup:
        Job job = new Job(jm)

        when:
        job.configure {
            description = 'Another description'
        }

        then:
        noExceptionThrown()
        // TODO
        //job.xml
    }

    def 'run engine'() {
        setup:
        ScriptRequest request = new ScriptRequest('simple.dsl', null, resourcesDir.toURL(), false);

        when:
        def jobs = DslScriptLoader.runDslEngine(request, jm)

        then:
        jobs != null
        jobs.size() == 1
        jobs.iterator().next().jobName == 'test'
    }

    def 'run engine with reference to other class'() {
        setup:
        ScriptRequest request = new ScriptRequest('caller.dsl', null, resourcesDir.toURL(), false);

        when:
        def jobs = DslScriptLoader.runDslEngine(request, jm)

        then:
        jobs != null
        jobs.size() == 2
        jobs.any { it.jobName == 'test'}
        jobs.any { it.jobName == 'test2'}

    }

    def 'run engine with reference to other class from a string'() {
        setup:
        def scriptStr = '''job {
    name 'test'
}

Callee.makeJob(this, 'test2')
'''
        ScriptRequest request = new ScriptRequest(null, scriptStr, resourcesDir.toURL(), false)

        when:
        def jobs = DslScriptLoader.runDslEngine(request, jm)

        then:
        jobs != null
        jobs.size() == 2
        jobs.any { it.jobName == 'test'}
        jobs.any { it.jobName == 'test2'}

    }
//
//    def 'Able to run engine for string'() {
//        setup:
//        JobManagement jm = new FileJobManagement(new File("src/test/resources"))
//
//        when:
//        Set<GeneratedJob> results = DslScriptLoader.runDslShell(sampleDsl, jm)
//
//    }

    def 'externally facing URL from root'() {
        when:
        String host  = "JOB"
        then:
        "job/" == DslScriptLoader.createExternalRoot(host)
    }

    def 'externally facing URL from folder'() {
        when:
        String host  = "FOLDER.JOB"
        then:
        "job/FOLDER/job/" == DslScriptLoader.createExternalRoot(host)
    }

    def 'externally facing URL from nested folder'() {
        when:
        String host  = "FOLDER1.FOLDER2.JOB"
        then:
        "job/FOLDER1/job/FOLDER2/job/" == DslScriptLoader.createExternalRoot(host)
    }
}
