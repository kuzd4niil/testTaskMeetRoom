# Test Task "Meet Room"
## Building the app (JAVA 18):
1. Set environment variable **JAVA_HOME** that specifies the path to JDK 18 in this case
2. Configure JPA in settings file *application.yml*
3. Run in terminal
```
<rootProject>$ ./gradlew build && java -jar build/libs/testTaskMeetRoom-0.0.1.jar
```
## Short description:
Go to the main page [Meet Room](localhost:8080) and you will be taken to the authorization page. There are already 3 users in the database:

|Username| Password |
|---|----------|
|dan| 100      |
|max| 100      |
|john| 100      |
You can reserve a meeting room. The reservation interval is a multiple of 30 minutes, cannot be more than 24 hours and overlaps different weeks, but can overlap two consecutive days of the same week. The minimum number of meeting participants is 2 people.
## Technical notes:
- Application doesn't support registration new user. You need create user in database directly.
- Thymeleaf is little used. The main work on rendering the table fell on JavaScript (including the use of XMLHTTPRequest, DOM);
- The task was completed with Manjaro (Arch) distribution.
- The avatar generator (AvatarCreationService) only works when requested by the user.