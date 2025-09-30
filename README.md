# Spring Boot Integeration Tests

## Maven Toolchains (JDK 21)

Use Maven Toolchains to compile with JDK 21 regardless of the JDK on your PATH.

### Create the toolchains file

- Windows: `%USERPROFILE%\.m2\toolchains.xml`
- macOS/Linux: `~/.m2/toolchains.xml`

Put this content (adjust `jdkHome` to your actual JDK 21 path):

```xml
<toolchains>
  <toolchain>
    <type>jdk</type>
    <provides>
      <version>21</version>
      <!-- Optional: remove vendor to match any distribution (Temurin, Zulu, Oracle, etc.) -->
      <!-- <vendor>oracle</vendor> -->
    </provides>
    <configuration>
      <!-- Windows example -->
      <jdkHome>C:\Program Files\Java\jdk-21.0.2</jdkHome>
      <!-- macOS/Linux example -->
      <!-- <jdkHome>/Library/Java/JavaVirtualMachines/temurin-21.jdk/Contents/Home</jdkHome> -->
      <!-- <jdkHome>/usr/lib/jvm/temurin-21-jdk</jdkHome> -->
    </configuration>
  </toolchain>
</toolchains>
```

### Verify it works

This project already includes the toolchains plugin. Build with debug and check the output:

```bash
mvn -X -DskipTests compile
```

You should see lines like:

- `Required toolchain: jdk [ version='21' ]`
- `Found matching toolchain for type jdk: JDK[...21...]`

Note: `mvn -v` shows the JDK Maven runs on (e.g., 25). Toolchains ensures compilation uses JDK 21.

## Manual Integration Tests (no Failsafe)

Integration tests live under `src/integration/test/java` and are excluded from the default Maven test run. Run them manually:

1. Start the app:

   ```bash
   mvn spring-boot:run
   ```

2. In another terminal or from your IDE, run the integration test class directly. Example with Maven (overrides the default include/exclude):

   ```bash
   mvn -Dtest=org.example.web.controller.ItemControllerIT test -DfailIfNoTests=false
   ```

These tests call the running app at `http://localhost:8080`.
