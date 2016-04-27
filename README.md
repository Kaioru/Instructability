# Instructabilty
Instructability is a Discord4J Command API Module written in Java 8.
Enables a simple yet complex way of adding Commands into your Discord4J Bot.

## Usage
First, add Instructability as a dependency to your project.
### Maven
#### Step 1: Add the JitPack repository to your build file
``` xml
<repositories>
	<repository>
	    <id>jitpack.io</id>
	    <url>https://jitpack.io</url>
	</repository>
</repositories>
```
#### Step 2: Add the dependency
Change the version according to your desired version of Instructability. (Commit works as well)
``` xml
<dependency>
    <groupId>com.github.Kaioru</groupId>
    <artifactId>Instructabilty</artifactId>
    <version>version</version>
</dependency>
```
More details on [JitPack](https://jitpack.io/#Kaioru/Instructabilty) for Gradle, sbt and leiningen.

### Adding Commands
``` java
Instructables.getRegistry()
		.registerCommand(new CommandBuilder("ping") // Registers the Command
				.alias("hello") // Adds an alias 'hello' to the Command
				.command(new CommandBuilder("inside") // Adds a Sub-command in the Command
						.build((event, msg, args) -> {
							msg.appendContent("Hello from the inside!");
							msg.build(); // Sends and Build the message
						}))
				.build((event, msg, args) -> {
					msg.appendContent("Hello from the outside!");
					msg.build();
				}));
```
### Cool stuff
```
.testcommand "super long argument here" secondargument 'another long argument'
```
Commands are able to have spacings in arguments and executed as shown above.
```
.testcommand help
.testcommand alias
```
All Commands have the help and alias Sub-commands to provide information on the Command.
To remove these Helper Commands, simply add a ```.skipHookingDefaults()``` method in the CommandBuilder when building the Command.

## Contributing
I'm not the best programmer out there, there will be imperfections and stuff.
So feel free to contribute and help the project out!
