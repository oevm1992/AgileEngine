# AgileEngine

1. Clone repository

2. Import project as maven project

3. Run the maven clean install command in the path where the pom is located

4. Run the application as a java application from AgileEngineTestApplication class

Batch Configuration

To change the batch configuration you have to change "spring.application.schedule" in application.properties using chrone expressions(https://www.freeformatter.com/cron-expression-generator-quartz.html). This will make a batch run in the time periods you want.
