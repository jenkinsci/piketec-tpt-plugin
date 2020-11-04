# About this plugin

TPT is a testing and verification tool for embedded control systems. TPT
can test MATLAB Simulink or dSPACE TargetLink models, ETAS ASCET models,
C-Code or test via MiL, SiL, PiL and HiL. The PikeTec TPT Plugin allows
users to execute tests modeled in [TPT (Time Partition
Testing)](https://piketec.com/tpt/) via Jenkins. A XML file
can be generated in JUnit format for the reporting of test results. You
can also display the TPT test report in Jenkins.  

# Usage

We will give you a short overview here. If you need a more detailed
description please refere to the chapter *Jenkins Continuous
Integration* of TPT manual.

The plugin provides two build steps:

1.  *Execute TPT test cases:* The TPT test cases are executed and the
    test results are converted into JUnit-XML files. If Jenkins is
    operating as master in master-slave-mode, it will delegate the work
    to slave jobs.
2.  *Execute TPT tests slave:* Execute the work delegated to it by
    *Execute TPT test cases* in master slave mode.

You have two options to publish the TPT test results:

1.  Add a *TPT Report* post build action to your job to display the TPT
    report in Jenkins.
2.  Configure the build steps to transform the test results into the
    JUnit format and then publish the results in Jenkins using the
    *Publish JUnit test result report* post build action provided by the
    [JUnit
    Plugin](https://plugins.jenkins.io/junit/).

## Using only *Execute TPT test cases* (not master slave mode)

Simply create a new Jenkins Job and add the *Execute TPT test cases*
build step and configure it as needed. Do not set the option "Distribute
work to TPT slave jobs".

Make sure:

-   The TPT software is installed on the computers where tests should be
    run.
-   All 3^(rd) party tools needed on the Jenkins node where the job will
    run are available.
-   The correct TPT project file is available (use e.g. SCM checkout to
    check this).
-   The Junit XML directory is placed in the workspace of the job in
    case you want to publish the test results to Jenkins.

## Using only *Execute TPT test cases* and *Execute TPT tests slave* (master slave mode)

You can configure the *Execute TPT test cases* build step to delegate
the work to a slave job by setting the option "Distribute work to TPT
slave jobs". The slave job must contain a *Execute TPT tests slave*
build step.

The *Execute TPT test cases* build step will open the specified TPT
file, lookup the tests, split them in packages with an equal number of
tests and starts for each package a build of the slave job.

When all slave jobs are finished the *Execute TPT test cases* build step
will copy the test data into its workspace. The job will now generate an
overall report and If you configured the job for publishing via JUnit
the JUNit XML files.

Make sure:

-   Only one slave job build runs on a node at any given time.
-   The TPT software is installed on the computers where tests should be
    run.
-   All 3^(rd) party tools needed on the Jenkins node where the job will
    run are available.
-   The correct TPT project file is available (use e.g. SCM checkout to
    check this).
-   The Junit XML directory is placed in the workspace of the job in
    case you want to publish the test results to Jenkins.

The *Execute TPT tests slave* will communicate with TPT via a network
protocol (Java RMI). If two builds are using the same port and would run
on the same node the outcome of the execution would be nondeterministic.
Use e.g. the [Throttle Concurrent Builds
Plugin](https://plugins.jenkins.io/throttle-concurrents/)
to prevent builds from runnig on the same node.

# FAQ

*TPT hangs/does not start when I use Jenkins. If I start TPT normally it
works fine.*

This is usually a problem with the licensing. Since Jenkins normally
runs as a service with a different user TPT will access other settings
than when executed manually. If you have a correctly configured TPT you
can copy the license configuration file
"%LOCALAPPDATA%\\TPT\\$InstallDirName\_hash$\\license\_serverconfig.cfg"
(please replace $InstallDirName\_hash$ by your TPT installation
directory name and the hash) into the TPT installation directory and
rename it to "license\_default.cfg". If TPT does not find a license
configuration it will use these instead.

Please note that other 3rd party tools as MATLAB Simulink or ETAS ASCET
may have their own issues running in a service environment.

  

*I cannot publish the TPT test results. The JUnit publisher cannot find
anything.*

The JUnit publisher can only find files in the workspace of the Job
while the *Execute TPT test cases* allows you to create the XMLfiles
anywhere in the file system. Please ensure you configured the paths
correctly. You can use a relative path. A valid configuration would be
"junit" as the path given to the build step and "junit/\*.xml" given to
the publisher.
