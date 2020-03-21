package com.github.nfantoni.dependency.test.tools;

import com.github.nfantoni.dependency.utilis.MavenUtils;
import com.github.nfantoni.dependency.tools.Functions;
import org.apache.maven.model.Dependency;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.apache.maven.project.MavenProject;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.Map;

import static com.github.nfantoni.dependency.tools.Functions.formatDependencyList;

public class FunctionsTest extends AbstractMojoTestCase {

    private static final String GROUPID_PATTERN = "#{groupId}#";
    private static final String ARTIFACTID_PATTERN = "#{artifactId}#";
    private static final String VERSION_PATTERN = "#{version}#";
    private static final String COMPILE = "compile";

    @Test
    public void testCheckScope(){
        assertTrue(Functions.checkScope("all", COMPILE));
        assertTrue(Functions.checkScope("compile,test", COMPILE));
        assertFalse(Functions.checkScope(COMPILE, "test"));
    }

    @Test
    public void testFormatDependencyList() throws Exception {

        MavenUtils mavenUtils = new MavenUtils();

        MavenProject project = mavenUtils.readMavenProject( new File( "src/test/resources/dependency/tools/" ) );

        assertNotNull(project);

        String expression = "#{groupId}#:#{artifactId}#:#{version}#:#{scope}#";

        Map<String,String> keyList = Functions.decodeExpression(expression);

        assertTrue(keyList.containsKey(GROUPID_PATTERN));
        assertTrue(keyList.containsKey(ARTIFACTID_PATTERN));
        assertTrue(keyList.containsKey(VERSION_PATTERN));
        assertTrue(keyList.containsKey("#{scope}#"));

        assertEquals("groupId", keyList.get(GROUPID_PATTERN));
        assertEquals("artifactId", keyList.get(ARTIFACTID_PATTERN));
        assertEquals("version", keyList.get(VERSION_PATTERN));
        assertEquals("scope", keyList.get("#{scope}#"));

        List<Dependency> dependencyList = project.getDependencies();

        String result = formatDependencyList(dependencyList,
                expression, COMPILE, keyList);

        assertEquals("com.github.nfantoni:dependency-1:1.0.0-SNAPSHOT:compile\n" +
                             "com.github.nfantoni:dependency-3:1.0.0-SNAPSHOT:compile", result);
    }

    @Test
    public void testFormatDependencyListException() throws Exception {

        MavenUtils mavenUtils = new MavenUtils();

        MavenProject project = mavenUtils.readMavenProject( new File( "src/test/resources/dependency/tools/" ) );

        assertNotNull(project);

        String expression = "#{groupId}#:#{artifactId}#:#{version}#:#{not-valid}#";

        Map<String,String> keyList = Functions.decodeExpression(expression);

        assertTrue(keyList.containsKey(GROUPID_PATTERN));
        assertTrue(keyList.containsKey(ARTIFACTID_PATTERN));
        assertTrue(keyList.containsKey(VERSION_PATTERN));
        assertTrue(keyList.containsKey("#{not-valid}#"));

        assertEquals("groupId", keyList.get(GROUPID_PATTERN));
        assertEquals("artifactId", keyList.get(ARTIFACTID_PATTERN));
        assertEquals("version", keyList.get(VERSION_PATTERN));
        assertEquals("not-valid", keyList.get("#{not-valid}#"));

        List<Dependency> dependencyList = project.getDependencies();

        try{
            formatDependencyList(dependencyList,
                    expression, COMPILE, keyList);
            fail("Exception expected");
        }catch (Exception ex){
            assertEquals("Error ", ex.getMessage());
        }


    }

}
