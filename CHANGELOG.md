# Changelog

## [1.4.0] - 2025-01-10

### 🎉 Major Features

#### Permission-Based Messages
- **NEW**: Players can now have custom join/quit messages based on their permissions
- Configurable permission-based message system with priority handling
- Fully backwards compatible with existing random and static message systems
- Example: VIP players get gold messages, Premium players get purple messages

#### Enhanced Command System
- **NEW**: Migrated from Bukkit CommandExecutor to mccore annotation-based command system
- **IMPROVED**: Test command now supports custom message testing
- **NEW**: `/jqm test` - Test your configured join message with full formatting
- **NEW**: `/jqm test <custom message>` - Test any custom message with colors, hex codes, and placeholders
- **NEW**: `/jqm reload` - Reload configuration with proper permission checks

#### Multi-Platform Build System
- **NEW**: GitHub Actions CI/CD pipeline for automated builds
- **NEW**: Separate builds for different distribution platforms (GitHub, Spigot, Bukkit, Modrinth)
- **NEW**: Each build tracks its download source for accurate metrics
- **NEW**: Automatic release artifact generation and deployment

### 🚀 Performance Improvements

#### Comprehensive Caching System
- **NEW**: All permission messages are now cached for optimal performance
- **NEW**: Configuration values cached to eliminate repeated file I/O
- **IMPROVED**: Message retrieval is now O(1) lookup time instead of file parsing
- **NEW**: Smart cache invalidation on configuration reload

### 🔧 Technical Improvements

#### Configuration Handling
- **FIXED**: Resolved MemorySection errors with nested YAML configurations
- **NEW**: Recursive configuration loader for proper nested structure handling
- **IMPROVED**: Robust null checking and validation throughout
- **NEW**: Automatic configuration migration for new features

#### Code Quality
- **IMPROVED**: Better error handling and edge case management
- **NEW**: Comprehensive documentation and examples
- **IMPROVED**: Consistent coding patterns and structure
- **NEW**: Type safety improvements with proper ConfigurationSection handling

### 📚 Documentation

#### New Documentation Files
- **NEW**: `README.md` - Comprehensive setup and configuration guide
- **NEW**: `CLAUDE.md` - Development guidance for future maintainers
- **NEW**: Multiple configuration examples for different use cases
- **NEW**: Command usage examples with expected outputs
- **NEW**: Color code and PlaceholderAPI integration guides

### 🛠️ Configuration Examples

#### Basic Permission Messages
```yaml
permission_messages:
  enabled: true
  join:
    "myserver.vip": "&7[&6VIP+&7] &6%player% &7joined with style!"
    "myserver.premium": "&7[&5PREMIUM+&7] &5%player% &7entered the server!"
  leave:
    "myserver.vip": "&7[&6VIP-&7] &6%player% &7left the server"
    "myserver.premium": "&7[&5PREMIUM-&7] &5%player% &7departed"
```

#### Advanced Features
- **Hex Color Support**: `<#FF6B6B>Player Name</#FF6B6B>`
- **PlaceholderAPI Integration**: `%player_displayname%`, `%server_online%`, etc.
- **Multiple Message Types**: Static, random, and permission-based all working together

### 🎯 Commands

| Command | Description | Permission |
|---------|-------------|------------|
| `/jqm test` | Test configured join message | `joinandquitmessages.test` |
| `/jqm test <message>` | Test custom message | `joinandquitmessages.test` |
| `/jqm reload` | Reload configuration | `joinandquitmessages.reload` |

### 🔐 Permissions

| Permission | Description | Default |
|------------|-------------|---------|
| `joinandquitmessages.use` | Access to main command | `true` |
| `joinandquitmessages.test` | Test message formatting | `true` |
| `joinandquitmessages.reload` | Reload configuration | `op` |

### 🏗️ Build & Development

#### GitHub Actions Workflow
- **NEW**: Automated builds on push, PR, and release
- **NEW**: Matrix builds for multiple distribution platforms
- **NEW**: Automatic version detection and artifact naming
- **NEW**: Release asset uploading with proper naming conventions

#### Maven Improvements
- **NEW**: Download source injection via Maven properties
- **NEW**: Replacer plugin for build-time source identification
- **UPDATED**: Dependencies and plugin versions
- **NEW**: Build profiles for different distribution channels

### 🐛 Bug Fixes

- **FIXED**: Permission message priority handling and fallback logic
- **FIXED**: Configuration section null pointer exceptions
- **FIXED**: Message caching inconsistencies during reloads
- **FIXED**: GitHub Actions deprecated action warnings

### ⚡ Migration Notes

#### From 1.3.0 to 1.4.0
- **Automatic**: Existing configurations will be automatically migrated
- **New Sections**: `permission_messages` section will be created if it doesn't exist
- **Commands**: Old functionality preserved, new commands added
- **Performance**: Immediate performance improvements with no configuration changes needed

#### New Optional Features
- Enable permission messages by setting `permission_messages.enabled: true`
- Add custom permission-based messages under `permission_messages.join` and `permission_messages.leave`
- Use new test command features for easier configuration testing

---

**Support:**
- [GitHub Issues](https://github.com/thedutchruben/joinandquitmessages/issues)
- [Discord Community](https://discord.gg/XXXXX)

---