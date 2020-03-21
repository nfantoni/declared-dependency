# declared-dependency

Maven plugin with declared dependency utility

## Goal

### list

Goal to print project declare dependency

*How to use*

```bash
mvn com.github.nfantoni:declared-dependency:list -q
```
> The -q option (--quiet) is used to have in the standard outup only the dependency list

*Output example*

```bash
com.github.nfantoni:dependency-1:1.0.0-SNAPSHOT:compile
com.github.nfantoni:dependency-3:1.0.0-SNAPSHOT:compile
```

*Parameters*

| Name | Description | Defult |
|---|---|---|
|  *declared-dependency.list.outputFormat* | expression to format the output in according with *org.apache.maven.model.Dependency*. The fields are in the format *#!{fieldName}!#* | *#!{groupId}!#:#!{artifactId}!#:#!{version}!#:#!{scope}!#*
| *declared-dependency.list.scopes* | Comma separated value of the dependency scope to extract, use the option *all* to list every scope | *compile* |

Complete example call:

```bash
mvn com.github.nfantoni:declared-dependency:list \
    -Ddeclared-dependency.list.outputFormat="#{groupId}#:#{artifactId}#:#{version}#" \
    -Ddeclared-dependency.list.scopes=all -q
```