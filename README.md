# Instructability [![Build Status](https://travis-ci.org/Kaioru/Instructability.svg?branch=master)](https://travis-ci.org/Kaioru/Instructability) [![](https://jitpack.io/v/Kaioru/Instructability.svg)](https://jitpack.io/#Kaioru/Instructability)
Instructability is a Commands API primarily written for Discord4J, Javacord and JDA. Though, it can be easily modified to fit in any system.

## Usage
### For Discord4J
#### As a External Module
Simply add the ```instructability-discord4j.jar``` file downloaded from the [Releases Page](https://github.com/Kaioru/Instructability/releases) to the modules directory of your Discord4J bot, along with your other external Instructability modules.

And viola! Everything is now done and working!
#### In a project
##### Step 1 - Adding the dependencies
###### Maven
```xml
<repositories>
	<repository>
	    <id>jitpack.io</id>
	    <url>https://jitpack.io</url>
	</repository>
</repositories>
```
```xml
<dependency>
    <groupId>com.github.Kaioru</groupId>
    <artifactId>instructability</artifactId>
    <version>@VERSION@</version>
</dependency>
```
Where @VERSION@ is the version you wish to use of Instructability.

Further instructions on this step can be found on [JitPack](https://jitpack.io/#Kaioru/Instructability)
##### Step 2 - Enabling the module
``` java
IDiscordClient client = new ClientBuilder()
                .withToken(token)
                .login();

client.getModuleLoader().loadModule(new InstructabilityModule());
```
##### Step 4 - Changing the command Prefix
###### @Mention
``` java
client.getDispatcher().registerListener((IListener<ReadyEvent>) event -> // Ensures 'getOurUser()' is not null
        Instructables.getRegistry().setPrefix(client.getOurUser().mention() + " ")
);
```
###### Everything else
``` java
Instructables.getRegistry().setPrefix("!"); // !<command>
Instructables.getRegistry().setPrefix("bot "); // bot <command>
```
##### Step 3 - Adding commands!
###### Using Annotations
``` java
public class AnnotationCommands {
    @Discord4JAnnotatedCommand(
            name = "demo",
            desc = "This is a demo!"
    )
    public void cmdDemo(LinkedList<String> args, MessageReceivedEvent event, MessageBuilder msg) throws Exception {
        msg.appendContent("Hello world!")
                .build();
    }
}
```
``` java
public class AnnotatedMiddlewareCommand extends Discord4JCommand {
	@Override
	public String getName() {
		return "annotated";
	}

	@Override
	public String getDesc() {
		return Defaults.DESCRIPTION;
	}

	@Override
	public void execute(LinkedList<String> args, MessageReceivedEvent event, MessageBuilder msg) throws Exception {}
}
```
``` java
AnnotatedMiddlewareCommand cmd = new AnnotatedMiddlewareCommand();
cmd.registerCommands(new AnnotationCommands());
Instructables.getRegistry().registerCommand(cmd);
```
It is currently not possible to add Annotated commands directly to the CommandRegistry. It will be fixed in the next update
###### Using the Builder
``` java
Instructables.getRegistry()
        .registerCommand(new Discord4JCommandBuilder("demo")
        .build((args, event, msg) -> {
            msg.appendContent("Hello world!")
                    .build();
        }));
```
###### Using Classes
``` java
public class DemoCommand extends Discord4JCommand {
	@Override
	public String getName() {
		return "demo";
	}

	@Override
	public String getDesc() {
		return Defaults.DESCRIPTION;
	}

	@Override
	public void execute(LinkedList<String> args, MessageReceivedEvent event, MessageBuilder msg) throws Exception {
		msg.appendContent("Hello world!")
				.build();
	}
}
```
``` java
Instructables.getRegistry().registerCommand(new DemoCommand());
```
### For Javacord and JDA
Everything is the same for Javacord and JDA as Discord4j.

Simply change ```Discord4J``` to ```Javacord``` or ```JDA``` accordingly.
For example;
``` java
public class DemoCommand extends JavacordCommand {
	@Override
	public String getName() {
		return "demo";
	}

	@Override
	public String getDesc() {
		return Defaults.DESCRIPTION;
	}

	@Override
    public void execute(LinkedList<String> args, DiscordAPI discordAPI, Message message) throws Exception {
        message.reply("Hello world!");
    }
}
```
``` java
public class DemoCommand extends JDACommand {
	@Override
	public String getName() {
		return "demo";
	}

	@Override
	public String getDesc() {
		return Defaults.DESCRIPTION;
	}

	@Override
    public void execute(LinkedList<String> args, MessageReceivedEvent event) throws Exception {
        event.getChannel().sendMessage("Hello world!");
    }
}
```

## Projects using Instructability
* [ExtendedInstructs](https://github.com/Kaioru/ExtendedInstructs) - Only supports Discord4J
* [InstructPermissions](https://github.com/Kaioru/InstructPermissions) - Only supports Discord4J, not up-to-date

## Need help?
I'm on Discord as @Kaioru, feel free to drop me a PM. I'm also on Discord4J's Discord channel.
## Contribution
I'm not the best programmer out there, there will be imperfections and stuff.

So feel free to contribute and help the project out!
