# Facade instanciation

The [Facade](apidocs/fr/pantheonsorbonne/miage/Facade.html) object is a singleton that can be received from the interface.

```java
Facade facade = (PlayerFacade) Facade.getFacade();
```
Once retreived from the interface, you need to wait for the facade object to be ready. For this, call the waitReady() method:

```java
facade.waitReady();
```

# Facade Flavors

There are 2 sub-interfaces to the [Facade](apidocs/fr/pantheonsorbonne/miage/Facade.html) Interface:

* [PlayerFacade](apidocs/fr/pantheonsorbonne/miage/class-use/PlayerFacade.html) that can be used for a regular player
* [HostFacade](apidocs/fr/pantheonsorbonne/miage/class-use/HostFacade.html) that can be used for the administrator of the Game.

When you retreive a facade object, it will implement both (since you get a [FacadeImpl](apidocs/fr/pantheonsorbonne/miage/FacadeImpl.html) object, follow the Interface Segregation principle, and store the [Facade](apidocs/fr/pantheonsorbonne/miage/Facade.html) object as one or this other.

## Facade

The [Facade](apidocs/fr/pantheonsorbonne/miage/Facade.html) interface allows you to perform the following operations:

* [Facade instanciation](apidocs/fr/pantheonsorbonne/miage/Facade.html#getFacade()) remember that Facade is a singleton, so whenever you call this method, the same instance will be returned
* [Facade initialization](apidocs/fr/pantheonsorbonne/miage/Facade.html#waitReady()) blocks until the facade is ready to use (it can take a few seconds)
* [Player Intialization](apidocs/fr/pantheonsorbonne/miage/Facade.html#createNewPlayer(java.lang.String)) set a name the the player. The Host also have a name.


## Player Facade

### Usecases 

The [player facade](apidocs/fr/pantheonsorbonne/miage/PlayerFacade.html) can be used for the following usecase:

* [joinning](apidocs/fr/pantheonsorbonne/miage/PlayerFacade.html#autoJoinGame(java.lang.String)) a game
* [sending](apidocs/fr/pantheonsorbonne/miage/PlayerFacade.html#sendGameCommand(fr.pantheonsorbonne.miage.model.Game,fr.pantheonsorbonne.miage.model.GameCommand)) game commands
* [receiving](apidocs/fr/pantheonsorbonne/miage/PlayerFacade.html#sendGameCommand(fr.pantheonsorbonne.miage.model.Game,fr.pantheonsorbonne.miage.model.GameCommand)) game commands

### Examples:

```java
 //get the facade as a player
PlayerFacade facade = Facade.getFacade();
facade.waitReady();
//set our palyer name
facade.createNewPlayer("Nicolas-" + new Random().nextInt());
//wait until we are able to join a new game
Game currentGame = facade.autoJoinGame("tictactoe");

//get our mark
GameCommand command = facade.receiveGameCommand(currentGame);
```

When you call any of those functions, they block until the operation is done successfully.

## Host Facade

The [host facade](apidocs/fr/pantheonsorbonne/miage/HostFacade.html) can be used by the administrator of the game for administrative purposes.

### Usecase

* [creating](apidocs/fr/pantheonsorbonne/miage/HostFacade.html#createNewGame(java.lang.String)) the game
* [waiting](apidocs/fr/pantheonsorbonne/miage/HostFacade.html#waitForPlayerCount(int)) for players to join the game

### Examples:


```java

 //get the host facade, to manage the game
HostFacade hostFacade = (HostFacade) Facade.getFacade();
//wait until we are ready to use the host facade
hostFacade.waitReady();
//set our player name
hostFacade.createNewPlayer("Nicolas" + new Random().nextInt());

//creata a new game
Game game = hostFacade.createNewGame("tictactoe");
//wait for another player to join
hostFacade.waitForPlayerCount(2);

```
