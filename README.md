# Instructability [![Build Status](https://travis-ci.org/Kaioru/Instructability.svg?branch=master)](https://travis-ci.org/Kaioru/Instructability) [![](https://jitpack.io/v/Kaioru/Instructability.svg)](https://jitpack.io/#Kaioru/Instructability)
Instructability is a Discord4J Commands API Module written in Java 8.

This Module adds a variety of ways to add Commands to your Discord4J Bot.

## Usage
### Users
To add this Module to your bot, simply head to the [Releases](https://github.com/Kaioru/Instructabilty/releases) page and download the latest, or required, release of Instructability. Once downloaded, head to your Bot's Module directory and place the .jar file there.

Aaaaaaand you're done! Happy botting!
### Developers
First, add Instructability as a dependency to your project.
#### Maven
##### Step 1: Add the JitPack repository to your build file
``` xml
<repositories>
	<repository>
	    <id>jitpack.io</id>
	    <url>https://jitpack.io</url>
	</repository>
</repositories>
```
##### Step 2: Add the dependency
Change the version according to your desired version of Instructability. (Commit works as well)
``` xml
<dependency>
    <groupId>com.github.Kaioru</groupId>
    <artifactId>Instructabilty</artifactId>
    <version>@VERSION@</version>
</dependency>
```
More details on [JitPack](https://jitpack.io/#Kaioru/Instructabilty) for Gradle, sbt and leiningen.

#### Adding Commands
##### Using annotations
``` java
public class AnnotationExample {

	@AnnotatedCommand(name = "demo")
	public void onDemoCommand(MessageReceivedEvent event, MessageBuilder msg, LinkedList<String> args) throws Exception {
		msg.appendContent("Hello world!");
		msg.build();
	}

}
```
``` java
Instructables.getRegistry().registerCommands(new AnnotationExample());
```
Due to the nature of Annotations in Java, most sub-command features will not be supported in this method. It is only recommended to use this method for basic commands.
##### Using the Builder
``` java
Instructables.getRegistry()
		.registerCommand(new CommandBuilder("ping") // Registers the Command
				.alias("hello") // Adds an alias 'hello' to the Command
				.command(new CommandBuilder("inside") // Adds a Sub-command in the Command
						.build((event, msg, args) -> {
							msg.appendContent("Hello from the inside!");
							msg.build(); // Sends and Build the message
						}))
				.command(new CommandBuilder("perms")
						.permission("you.need.this.permission") // Adds a permission to the Command
						.build((event, msg, args) -> {
							msg.appendContent("Permission found!");
							msg.build(); // Sends and Build the message
						}))
				.build((event, msg, args) -> {
					msg.appendContent("Hello from the outside!");
					msg.build();
				}));
```
##### Using classes
``` java
public class DemoCommand extends SimpleCommand {

	public DemoCommand() {
		addHelperCommands(); // Adds the 'help' and 'alias' sub-commands to your command
	}

	@Override
	public String getName() {
		return "demo"; // Name of your command
	}

	@Override
	public String getDesc() {
		return "This is a demo!"; // Description of your command
	}

	@Override
	public void onExecute(MessageReceivedEvent event, MessageBuilder msg, LinkedList<String> args) throws Exception {
        msg.appendContent("Hello world!");
        msg.build();
	}

}
```
``` java
Instructables.getRegistry().registerCommand(new DemoCommand());
```
Adding of permissions and further sub-commands is also possible with this method. However, it is not shown in the example above.
#### Cool stuff
```
.testcommand "super long argument here" secondargument 'another long argument'
```
Commands are able to have spacings in arguments and executed as shown above.
```
.testcommand help
.testcommand alias
```
All Commands have the help and alias sub-commands to provide information on the Command.
To remove these Helper Commands, simply add a ```.noHelperCommands()``` method in the CommandBuilder when building the Command.

## Need help?
You can find me on [Discord4J's Discord Channel](https://discord.gg/0SBTUU1wZTX8X8cu) as @Kaioru

## Contributing
I'm not the best programmer out there, there will be imperfections and stuff.

So feel free to contribute and help the project out!
