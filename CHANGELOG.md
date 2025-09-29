# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0] - 2025-09-28

### Added
- Initial production release
- Complete implementation of adaptive AI behaviors for all mob types
- Player memory system for tracking combat statistics and behavior patterns
- Mob knowledge system for individual mob learning and experiences
- Global AI memory for server-wide learning patterns
- Villager trust system with relationship tracking
- Comprehensive data persistence using YAML storage
- Event-driven architecture for real-time behavior adaptation
- Custom AI goals for each mob type:
  - WaitForShieldDownGoal for Creepers
  - SquadFormationGoal for Skeletons
  - ZombieHordeGoal for Zombies
  - SpiderAmbushGoal for Spiders
  - EndermanTeleportGoal for Endermen
  - WitchPotionStrategyGoal for Witches
  - SlimeCoordinationGoal for Slimes
  - PhantomSwoopGoal for Phantoms
  - PiglinTradeMemoryGoal for Piglins
  - HoglinTrapAvoidanceGoal for Hoglins
  - GhastStrafeGoal for Ghasts
  - BlazeFireballWallGoal for Blazes
  - WitherSkeletonFormationGoal for Wither Skeletons
  - IronGolemProtectionGoal for Iron Golems
  - AnimalAvoidanceGoal for Animals
  - BeeSwarmGoal for Bees
  - WolfRevengeGoal for Wolves
  - EnderDragonAdaptationGoal for Ender Dragon
  - WitherMutationGoal for Wither
  - ElderGuardianMemoryGoal for Elder Guardians
  - WardenSoundTrackingGoal for Wardens
- Command system with `/mobmemory` command
- Full documentation including README, LICENSE, and this CHANGELOG
- Maven build configuration for easy compilation
- Plugin metadata in plugin.yml

### Changed
- Enabled all event listeners in main plugin class
- Implemented actual AI behavior modifications through MobAIAdapter
- Enhanced MobBehaviorListener with comprehensive event tracking
- Improved data persistence with automatic loading/saving
- Optimized performance with efficient goal updating system

### Fixed
- Uncommented event registration in main plugin class
- Resolved issues with command registration
- Fixed data persistence implementation
- Addressed memory management for mob knowledge

## [0.1.0] - 2025-09-25

### Added
- Initial development version
- Basic framework structure
- Placeholder implementations for core components
- Initial documentation