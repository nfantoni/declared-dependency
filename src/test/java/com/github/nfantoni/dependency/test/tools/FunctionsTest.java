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

    @Test
    public void testCheckScope(){
        assertTrue(Functions.checkScope("all", "compile"));
        assertTrue(Functions.checkScope("compile,test", "compile"));
        assertFalse(Functions.checkScope("compile", "test"));
    }

    @Test
    public void testFormatDependencyList() throws Exception {

        MavenUtils mavenUtils = new MavenUtils();

        MavenProject project = mavenUtils.readMavenProject( new File( "src/test/resources/dependency/tools/" ) );

        assertNotNull(project);

        String expression = "#{groupId}:#{artifactId}:#{version}:#{scope}";

        Map<String,String> keyList = Functions.decodeExpression(expression);

        assertTrue(keyList.containsKey("#{groupId}"));
        assertTrue(keyList.containsKey("#{artifactId}"));
        assertTrue(keyList.containsKey("#{version}"));
        assertTrue(keyList.containsKey("#{scope}"));

        assertEquals("groupId", keyList.get("#{groupId}"));
        assertEquals("artifactId", keyList.get("#{artifactId}"));
        assertEquals("version", keyList.get("#{version}"));
        assertEquals("scope", keyList.get("#{scope}"));

        List<Dependency> dependencyList = project.getDependencies();

        String result = formatDependencyList(dependencyList,
                expression, "compile", keyList);

        assertEquals("com.github.nfantoni:dependency-1:1.0.0-SNAPSHOT:compile\n" +
                             "com.github.nfantoni:dependency-3:1.0.0-SNAPSHOT:compile", result);
    }

    @Test
    public void testFormatDependencyListException() throws Exception {

        MavenUtils mavenUtils = new MavenUtils();

        MavenProject project = mavenUtils.readMavenProject( new File( "src/test/resources/dependency/tools/" ) );

        assertNotNull(project);

        String expression = "#{groupId}:#{artifactId}:#{version}:#{not-valid}";

        Map<String,String> keyList = Functions.decodeExpression(expression);

        assertTrue(keyList.containsKey("#{groupId}"));
        assertTrue(keyList.containsKey("#{artifactId}"));
        assertTrue(keyList.containsKey("#{version}"));
        assertTrue(keyList.containsKey("#{not-valid}"));

        assertEquals("groupId", keyList.get("#{groupId}"));
        assertEquals("artifactId", keyList.get("#{artifactId}"));
        assertEquals("version", keyList.get("#{version}"));
        assertEquals("not-valid", keyList.get("#{not-valid}"));

        List<Dependency> dependencyList = project.getDependencies();

        try{
            formatDependencyList(dependencyList,
                    expression, "compile", keyList);
            fail("Exception expected");
        }catch (Exception ex){
            assertEquals("Error ", ex.getMessage());
        }


    }

}
