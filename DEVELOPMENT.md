# AI-Driven Mobs Plugin - Development Guide

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/example/aidrivenmobs/
│   │       ├── data/
│   │       │   └── PlayerMemory.java
│   │       ├── AIDrivenMobsPlugin.java
│   │       ├── CustomMobGoal.java
│   │       ├── DataPersistenceManager.java
│   │       ├── GlobalAIManager.java
│   │       ├── MobAIAdapter.java
│   │       ├── MobAIManager.java
│   │       ├── MobBehaviorListener.java
│   │       ├── MobKnowledge.java
│   │       ├── MobMemoryCommand.java
│   │       ├── MobMemoryManager.java
│   │       ├── MobPersonality.java
│   │       ├── SquadFormationGoal.java
│   │       ├── VillagerTrustSystem.java
│   │       ├── WaitForShieldDownGoal.java
│   │       ├── ZombieHordeGoal.java
│   │       ├── SpiderAmbushGoal.java
│   │       ├── EndermanTeleportGoal.java
│   │       ├── WitchPotionStrategyGoal.java
│   │       ├── SlimeCoordinationGoal.java
│   │       ├── PhantomSwoopGoal.java
│   │       ├── PiglinTradeMemoryGoal.java
│   │       ├── HoglinTrapAvoidanceGoal.java
│   │       ├── GhastStrafeGoal.java
│   │       ├── BlazeFireballWallGoal.java
│   │       ├── WitherSkeletonFormationGoal.java
│   │       ├── IronGolemProtectionGoal.java
│   │       ├── AnimalAvoidanceGoal.java
│   │       ├── BeeSwarmGoal.java
│   │       ├── WolfRevengeGoal.java
│   │       ├── EnderDragonAdaptationGoal.java
│   │       ├── WitherMutationGoal.java
│   │       ├── ElderGuardianMemoryGoal.java
│   │       └── WardenSoundTrackingGoal.java
│   └── resources/
│       └── plugin.yml
├── test/
│   └── (unit tests would go here)
├── target/
│   └── (compiled output)
├── pom.xml
├── README.md
├── CHANGELOG.md
├── LICENSE
├── build.bat
└── run_server.bat
```

## Core Architecture

### Main Plugin Class
`AIDrivenMobsPlugin.java` is the entry point that initializes all subsystems:
- MobMemoryManager - Handles player and mob data storage
- VillagerTrustSystem - Manages villager-player relationships
- MobAIManager - Coordinates AI behavior application
- GlobalAIManager - Manages server-wide learning
- MobBehaviorListener - Tracks player interactions
- MobMemoryCommand - Handles player commands

### Data Management
The plugin uses a layered data management approach:

1. **PlayerMemory** - Tracks individual player statistics and behavior patterns
2. **MobKnowledge** - Stores learning data for individual mobs
3. **MobPersonality** - Defines base traits for mob types
4. **DataPersistenceManager** - Handles YAML serialization/deserialization

### AI System
The AI system is built on custom goals that extend `CustomMobGoal`:

1. **CustomMobGoal** - Abstract base class defining the goal interface
2. **MobAIAdapter** - Integrates custom goals with mob entities
3. **MobAIManager** - Coordinates goal application and updates

## Adding New Mob Behaviors

### 1. Create a Custom Goal
Extend `CustomMobGoal` to implement new behavior:

```java
public class NewMobGoal extends CustomMobGoal {
    private MobType mob;
    private Player targetPlayer;
    
    public NewMobGoal(AIDrivenMobsPlugin plugin, MobType mob) {
        super(plugin, mob);
        this.mob = mob;
    }
    
    @Override
    public boolean canStart() {
        // Logic to determine if goal should start
        return false;
    }
    
    @Override
    public void start() {
        // Initialize goal behavior
    }
    
    @Override
    public void stop() {
        // Clean up when goal ends
    }
    
    @Override
    public void tick() {
        // Execute behavior each tick
    }
    
    @Override
    public boolean shouldContinue() {
        // Determine if goal should continue
        return false;
    }
}
```

### 2. Register the Goal
Add the goal to `MobAIAdapter.applyAdaptiveBehaviors()`:

```java
} else if (entity instanceof MobType) {
    applyNewMobBehaviors((MobType) entity);
}
```

And create the corresponding method:

```java
private void applyNewMobBehaviors(MobType mob) {
    if (mob.getTarget() != null) {
        NewMobGoal goal = new NewMobGoal(plugin, mob);
        if (goal.canStart()) {
            goal.start();
            activeGoals.put(mob.getUniqueId(), goal);
            plugin.getLogger().info("Applied NewMobGoal to mob at " + mob.getLocation());
        }
    }
}
```

### 3. Track Relevant Events
Add event tracking in `MobBehaviorListener.onEntityDamageByEntity()`:

```java
} else if (event.getDamager() instanceof Player && event.getEntity() instanceof MobType) {
    Player player = (Player) event.getDamager();
    PlayerMemory memory = plugin.getMemoryManager().getPlayerMemory(player);
    
    // Track relevant statistics
    memory.incrementStat("hits_mobtype");
}
```

## Data Persistence

### Player Data
Player data is stored in `plugins/AIDrivenMobs/playerdata/[UUID].yml`:

```yaml
combatStats:
  melee_hits: 15
  ranged_hits: 8
  shield_blocks: 12
  # ... other stats
behavioralPatterns:
  # ... behavioral data
```

### Mob Data
Mob knowledge is stored in memory during the mob's lifetime and can be persisted to disk.

### Global Data
Server-wide learning is stored in `plugins/AIDrivenMobs/globaldata/`.

## Performance Considerations

1. **Event Processing** - Events are processed asynchronously when possible
2. **Memory Management** - Mob knowledge is cleaned up when mobs die
3. **Goal Updates** - Custom goals are updated once per tick using Bukkit's scheduler
4. **Data Loading** - Player data is loaded lazily on join
5. **Data Saving** - Player data is saved on quit and periodically during gameplay

## Testing

### Unit Tests
Unit tests should be added to the `src/test/` directory following JUnit conventions.

### Integration Tests
Integration tests should verify:
1. Event tracking accuracy
2. Data persistence correctness
3. AI behavior activation conditions
4. Command functionality
5. Performance under load

## Extending the Plugin

### Adding New Statistics
1. Add new fields to `PlayerMemory`
2. Update `incrementStat()` and `getStat()` methods
3. Add tracking in `MobBehaviorListener`
4. Update `MobMemoryCommand` to display new stats

### Adding New Mob Types
1. Create a new custom goal class
2. Register the goal in `MobAIAdapter`
3. Add tracking in `MobBehaviorListener`
4. Add personality traits in `MobPersonality`

### Adding New Data Sources
1. Extend `PlayerMemory` or `MobKnowledge`
2. Update `DataPersistenceManager` for serialization
3. Add new event listeners as needed
4. Update commands to display new data

## API Compatibility

The plugin is designed to be compatible with:
- Spigot API 1.16.5 and higher
- Paper API 1.16.5 and higher
- Compatible forks (Purpur, Airplane, etc.)

For compatibility with newer Minecraft versions:
1. Update the API dependency in `pom.xml`
2. Check for deprecated methods in event listeners
3. Verify custom goal implementations still work
4. Update plugin metadata in `plugin.yml`

## Troubleshooting

### Common Issues
1. **Plugin not loading** - Check server logs for dependency errors
2. **Commands not working** - Verify command registration in `plugin.yml`
3. **AI behaviors not activating** - Check event tracking and goal conditions
4. **Data not saving** - Verify file permissions and disk space

### Debugging
1. Enable debug logging in server.properties
2. Use `/mobmemory stats` to check player data
3. Check server logs for plugin messages
4. Add temporary logging in custom goals

## Contributing

1. Fork the repository
2. Create a feature branch
3. Implement your changes
4. Add tests if applicable
5. Update documentation
6. Submit a pull request

## Release Process

1. Update version in `pom.xml`
2. Update `CHANGELOG.md`
3. Create Git tag
4. Build with `mvn clean package`
5. Create GitHub release
6. Update documentation if needed