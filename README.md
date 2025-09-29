# AI-Driven Mobs Plugin - Production Build

A Minecraft plugin that makes mobs smarter by allowing them to learn from player behavior and adapt their tactics accordingly.

## Features

### Adaptive Mob Behaviors

#### Passive / Neutral Mobs
- **Villager** → trust system (betray player if low trust, gossip between villages)
- **Iron Golem** → protects villagers but remembers players who harmed villagers (attacks on sight even days later)
- **Animals** (Cow, Sheep, Pig) → learn to avoid the player if you farm them too aggressively. Chickens might even "scatter" in flocks
- **Wolf** → if your wolf sees you die repeatedly to the same mob type, it becomes aggressive vs that mob
- **Bees** → learn swarm tactics; if one stings you, others nearby join in

#### Hostile Mobs
- **Zombie** → adapts by forming hordes and breaking doors only if you hide too much indoors
- **Creeper** → waits for distraction, hides behind walls, or flanks when you're fighting another mob
- **Skeleton** → squads form ambushes, shoot from high ground, or switch to melee if cornered
- **Spider** → learns to attack from ceilings, drop ambushes, and avoid lit areas if you torch-spam
- **Enderman** → teleports more unpredictably if you stare too much; can steal placed torches if you spam them
- **Witch** → switches potions based on your style (regen if you melee, harming if you bow, weakness if you shield)
- **Slime/Magma Cube** → coordinate split forms, surrounding you instead of just bouncing randomly
- **Phantom** → if you block with shield often, they swoop lower to knock it down first

#### Nether / End Mobs
- **Piglin** → tracks your gold trades → if you scam them, they remember & attack next time
- **Hoglin** → learns to avoid pits/traps after seeing one of their kind die
- **Ghast** → stops shooting predictable fireballs, tries to strafe around cover
- **Blaze** → coordinates with other blazes to form bullet-hell walls of fireballs
- **Wither Skeleton** → adapts into squad formations, one distracts while others flank
- **Ender Dragon** → learns your attack patterns → if you always bow, she divebombs instead of circling

#### Bosses / Rares
- **Wither** → mutates attack phases based on what killed it in previous fights (tracked globally)
- **Elder Guardian** → remembers player who raided its monument and sends stronger mining fatigue next time
- **Wardens** → track sound pattern of the player → if you keep sneaking, it waits patiently; if you rush, it pre-charges sonic boom

## Technical Implementation

### Core Components
1. **Player Memory System** - Tracks player behavior patterns and combat statistics
2. **Mob Knowledge System** - Stores individual mob learning and experiences
3. **Adaptive AI Goals** - Custom behaviors that modify mob tactics based on learned knowledge
4. **Global AI Memory** - Server-wide learning for boss mobs
5. **Villager Trust System** - Relationship tracking between players and villagers

### Data Persistence
- YAML-based storage for player statistics and mob knowledge
- Automatic loading on player join
- Automatic saving on player quit/server shutdown
- Global memory for server-wide learning patterns

### Event-Driven Architecture
- Listens to Minecraft events to track player interactions
- Applies adaptive behaviors when mobs spawn
- Updates memory systems in real-time

## Installation

1. Download the latest release JAR file
2. Place the JAR file in your server's `plugins` folder
3. Restart the server
4. The plugin will automatically begin tracking player interactions and applying adaptive behaviors

## Commands

- `/mobmemory stats` - View your combat statistics
- `/mobmemory reset` - Reset your combat statistics

## Configuration

The plugin generates data files in the `plugins/AIDrivenMobs/` directory:
- `playerdata/` - Contains individual player statistics
- `mobdata/` - Contains mob knowledge and experiences
- `globaldata/` - Contains server-wide learning patterns

## Development

### Building from Source
1. Clone the repository
2. Ensure you have Maven installed
3. Run `mvn clean package`
4. The compiled JAR will be in the `target/` directory

### API Dependencies
- Spigot/Paper API 1.16.5 or higher
- Java 8 or higher

### Extending the Plugin
The plugin is designed with modularity in mind:
1. Create new custom goals by extending `CustomMobGoal`
2. Register new behaviors in `MobAIAdapter`
3. Add new tracking events in `MobBehaviorListener`
4. Extend data storage in `MobMemoryManager` and `PlayerMemory`

## Performance Considerations

- Lightweight event processing with minimal impact on server performance
- Efficient data storage with lazy loading
- Goal updating optimized to run once per tick
- Memory cleanup when mobs die or players quit

## Compatibility

- Minecraft versions: 1.16.5 and higher
- Server software: Spigot, Paper, and compatible forks
- Other plugins: Designed to be compatible with most plugins (non-invasive approach)

## License

This plugin is released under the MIT License. See the LICENSE file for more information.

## Support

For issues, feature requests, or questions:
1. Check the GitHub issues page
2. Contact the development team through Discord
3. Refer to the documentation for common questions