Release History
===
## Version 9.3

- Plugin now needs at least Jenkin 2.361.1
- Compatibility to TPT 19
- Taking test set condition into account when using master slave mode. This can only be done for TPT versions equal or newer than TPT 16, else a warning is printed.

## Version 9.2

-   Added pipeline support
-   Fixed the behavior in of the setting "Number of slave jobs" for values less than 1 in
    master slave mode. It will now start a job for every test case as mentionend in the
    help.

## Version 9.1

-   When converting test results to JUnit format the execution config name and platform config name are now used as package names.

## Version 9.0

-   Plugin now needs at least Jenkins 2.200
-   Removed "Trust Slaves and Users to modify Jenkins workspace" option. You
    have to set up a "Resource Root URL" that is available since Jenkins 2.200
    to serve reports securely. See
    https://ci.jenkins.io/descriptor/jenkins.security.ResourceDomainConfiguration/help/url
    for more information.
-   Disabled trend graphs


## Version 8.8

-   **Master slave mode now needs at least TPT 11**
-   Fixed problems with paths when Jenkins controller runs on Linux.
-   Fixed problems with realative paths when using master slave mode.
-   Introduced uniqe IDs for each configured test execution. These
    IDs are used to generate unique paths to store test data and report
    data. **History of old runs will be lost.**
-   Environment variables are now expanded in context of the agent and
    not the Jenkins controller.
-   TPT API calls made in master slave mode are now done directly on
    the agents. So instead of doing calls from the jenkins controller
    via an addictional network connection, the calls are now done via
    localhost.

## Version 8.7

-   File descriptor leaks fixed that could prevent the plugin running
    multiple times in a short period.

## Version 8.6

-   Last minute changes of TPT 13 API added.

## Version 8.5

-   Lifted TPT API version to TPT 13.
-   If you use master slave mode and have multiple execution
    configurations added to a TPT build step only the last test results
    were tranformed to JUnit xml.

## Version 8.4

-   Lifted TPT API version to TPT 12.

## Version 8.3

-   Null pointer exception fixed if runnig older projects that have not
    seen the test set feature introduced in 8.0.
-   Retry for slave jobs will now work again.

## Version 8.2

-   TPT Report publisher now works on Linux nodes
-   TPT Report publisher is now compatible with folder plugin
-   TPT Report publisher now finds testdata created by TPT build steps
    embedded in other steps (e.g. conditional build step)

## Version 8.1

-   Fixed a Bug that prevented tables of new publisher from showing any
    content

## Version 8.0

-   Publisher for TPT reports added
-   Removed dependencies to other plugins: Copy-to-slave and
    Parametrized Trigger
-   It is now possible to explicitly set the test set to execute.

## Version 7.8

-   It is now possible to reduce the TPT log entries that are added to
    failed JUnit tests by setting a severitiy level.

## Version 7.7

-   Retry logic was inverted: Successful jobs were scheduled again and
    faild jobs not.
-   If a TPT execution configuration the multi core feature enabled the
    execution with master and slave jobs failed because the testdata dir
    coul not be cleaned
-   The TPT file is now closed on the master after execution, too.

## Version 7.6

-   A slave job can know accept more than one TPT test case and it is
    now possible to define the number of slave jobs started. The test
    cases will be distributed evenly to all started slave jobs so you
    can use the TPT multicore feature running multiple test cases in
    paralell in every TPT instance.
-   If a slave job execution fails (red ball) it is now possible to
    configure the master job to reschedule it up to the specified number
    of times.

## Version 7.5

-   Variables in tpt file field are now expanded as well.

## Version 7.4

-   When upgrading from a version prior to 6.5 the "Path to tpt.exe"
    configuration was lost and a null pointer exception occured. Fix now
    works for newer Jenkins versions.

## Version 7.3

-   When upgrading from a version prior to 6.5 the "Path to tpt.exe"
    configuration was lost and a null pointer exception occured.

## Version 7.1

-   After execution TPT projects are now closed on master and slave
    nodes.
-   If a test set of an execution configuration entry does not contain
    the test case to execute the entry will be temporarily deactivated.
-   If a TPT project cannot be opened the job fails and the errors are
    dumped to the Jenkins console.

## Version 7.0

-   Added master-slave functionality for distributing TPT test workload.

## Version 6.7

-   Execution on Salve-Nodes could fail because existence check and
    creation of neccessary folders were always executed on Master-Node.

## Version 6.6

-   A 6h timeout was hardcoded for TPT to finish. The timeout is now
    configurable.

## Version 6.5

-   The Field "Path to tpt.exe" now accepts a comma seperated list of
    paths. The first existing one is used for testexecution.

## Version 6.4

-   Variables are now expanded so you can use them in the configuration
    fields
-   Help files are now accessable again
-   more robust against misplaced quotes
-   least needed Jenkins version set to LTS 1.625.1

## Version 6.3

-   Initial public release