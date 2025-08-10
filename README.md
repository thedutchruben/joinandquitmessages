# JoinAndQuitMessages

A customizable Minecraft Spigot plugin for managing player join and quit messages with support for random messages, permission-based messages, PlaceholderAPI integration, and hex color codes.

## Features

- **Static Messages** - Set custom join and quit messages
- **Random Messages** - Display random messages from predefined lists
- **Permission-Based Messages** - Show different messages based on player permissions
- **PlaceholderAPI Support** - Use placeholders in your messages
- **Hex Color Support** - Modern color codes for Minecraft 1.16+
- **Backwards Compatible** - Existing configurations work seamlessly

## Installation

1. Download the latest release JAR file
2. Place it in your server's `plugins` folder
3. Restart your server
4. Configure the plugin in `plugins/JoinAndQuitMessages/config.yml`

## Configuration Examples

### Basic Static Messages

```yaml
joinmessage: "&7[&2+&7]&4%player%"
quitmessage: "&7[&4-&7]&4%player%"
```

### Random Messages

```yaml
random:
  enabled: true
  messages:
    join:
      - "&7[&2+&7]&4%player%"
      - "&4%player% &7has joined the server"
      - "&6Welcome &4%player% &6to the server!"
    leave:
      - "&7[&4-&7]&4%player%"
      - "&4%player% &7has left the server"
      - "&6Goodbye &4%player%&6!"
```

### Permission-Based Messages

```yaml
permission_messages:
  enabled: true
  join:
    joinandquitmessages.owner: "&7[&4OWNER+&7]&4%player%"
    joinandquitmessages.admin: "&7[&cADMIN+&7]&c%player%"
    joinandquitmessages.mod: "&7[&9MOD+&7]&9%player%"
    joinandquitmessages.vip: "&7[&6VIP+&7]&6%player%"
    joinandquitmessages.premium: "&7[&5PREMIUM+&7]&5%player%"
  leave:
    joinandquitmessages.owner: "&7[&4OWNER-&7]&4%player%"
    joinandquitmessages.admin: "&7[&cADMIN-&7]&c%player%"
    joinandquitmessages.mod: "&7[&9MOD-&7]&9%player%"
    joinandquitmessages.vip: "&7[&6VIP-&7]&6%player%"
    joinandquitmessages.premium: "&7[&5PREMIUM-&7]&5%player%"
```

### Hex Color Support (MC 1.16+)

```yaml
joinmessage: "&7[<#00FF00>+&7]<#FF6B6B>%player%"
quitmessage: "&7[<#FF0000>-&7]<#FF6B6B>%player%"
```

### PlaceholderAPI Integration

```yaml
joinmessage: "&7[&2+&7] &6%player% &7from &b%player_world% &7joined! (&a%server_online%&7/&a%server_max_players%&7)"
quitmessage: "&7[&4-&7] &6%player% &7left the server after &e%player_session_time%"
```

### Complete Configuration Example

```yaml
# Basic messages (fallback when random and permission messages are disabled)
joinmessage: "&7[&2+&7]&4%player%"
quitmessage: "&7[&4-&7]&4%player%"

# Plugin settings
version: "1.1"
update_check: true

# Random messages
random:
  enabled: false
  messages:
    join:
      - "&7[&2+&7]&4%player%"
      - "&4%player% &7has joined the server"
      - "&6Welcome back &4%player%&6!"
    leave:
      - "&7[&4-&7]&4%player%"
      - "&4%player% &7has left the server"
      - "&6See you later &4%player%&6!"

# Permission-based messages
permission_messages:
  enabled: true
  join:
    joinandquitmessages.owner: "&7[&4&lOWNER&r&7] <#FF6B6B>%player% &7joined the server"
    joinandquitmessages.admin: "&7[&c&lADMIN&r&7] <#FFA500>%player% &7joined the server"
    joinandquitmessages.vip: "&7[&6&lVIP&r&7] <#FFD700>%player% &7joined the server"
  leave:
    joinandquitmessages.owner: "&7[&4&lOWNER&r&7] <#FF6B6B>%player% &7left the server"
    joinandquitmessages.admin: "&7[&c&lADMIN&r&7] <#FFA500>%player% &7left the server"
    joinandquitmessages.vip: "&7[&6&lVIP&r&7] <#FFD700>%player% &7left the server"
```

## Commands

### `/joinandquitmessages` (alias: `/jqm`)

Base command for the plugin.

**Usage:** `/joinandquitmessages <test [message]|reload>`

### `/joinandquitmessages test`

Tests your current join message with all formatting applied, or tests a custom message if provided.

**Usage:**
- `/jqm test` - Test your configured join message
- `/jqm test &7[&6VIP&7] &6%player% &7joined!` - Test a custom message

**Example Output:**
```
/jqm test
Testing configured join message:
Result: [VIP+]PlayerName

/jqm test &7[<#FF6B6B>CUSTOM</#FF6B6B>&7] &6%player% &7joined the server!
Testing custom message:
Result: [CUSTOM]PlayerName joined the server!
```

### `/joinandquitmessages reload`

Reloads the plugin configuration from disk.

**Example:**
```
/jqm reload
Configuration reloaded successfully!
```

## Permissions

| Permission | Description | Default |
|------------|-------------|---------|
| `joinandquitmessages.use` | Allows using the main command | `true` |
| `joinandquitmessages.test` | Allows testing join messages | `true` |
| `joinandquitmessages.reload` | Allows reloading configuration | `op` |

### Permission-Based Message Permissions

You can create any permission for your messages. Common examples:

- `joinandquitmessages.owner` - Server owner messages
- `joinandquitmessages.admin` - Administrator messages
- `joinandquitmessages.mod` - Moderator messages
- `joinandquitmessages.vip` - VIP player messages
- `joinandquitmessages.premium` - Premium player messages

## Message Priority System

The plugin uses the following priority order:

1. **Permission-based messages** (if enabled and player has permission)
2. **Random messages** (if enabled)
3. **Default static message**

## Color Codes

### Legacy Color Codes
Use `&` followed by a color code:
- `&0` - Black
- `&1` - Dark Blue
- `&2` - Dark Green
- `&3` - Dark Aqua
- `&4` - Dark Red
- `&5` - Dark Purple
- `&6` - Gold
- `&7` - Gray
- `&8` - Dark Gray
- `&9` - Blue
- `&a` - Green
- `&b` - Aqua
- `&c` - Red
- `&d` - Light Purple
- `&e` - Yellow
- `&f` - White

### Formatting Codes
- `&l` - Bold
- `&m` - Strikethrough
- `&n` - Underline
- `&o` - Italic
- `&r` - Reset

### Hex Color Codes (MC 1.16+)
Use `<#RRGGBB>` format:
- `<#FF6B6B>` - Light Red
- `<#4ECDC4>` - Teal
- `<#45B7D1>` - Light Blue
- `<#96CEB4>` - Light Green
- `<#FFEAA7>` - Light Yellow
- `<#DDA0DD>` - Plum

## PlaceholderAPI Placeholders

Common placeholders you can use (requires PlaceholderAPI):

### Player Placeholders
- `%player_name%` - Player's name
- `%player_displayname%` - Player's display name
- `%player_world%` - Current world name
- `%player_x%` - X coordinate
- `%player_y%` - Y coordinate
- `%player_z%` - Z coordinate
- `%player_health%` - Current health
- `%player_level%` - Experience level

### Server Placeholders
- `%server_online%` - Online player count
- `%server_max_players%` - Maximum players
- `%server_unique_joins%` - Total unique joins
- `%server_name%` - Server name

### Time Placeholders
- `%player_first_join_date%` - First join date
- `%player_session_time%` - Current session time
- `%server_time_24%` - Server time (24h format)

## Examples in Action

### Basic Setup
```yaml
joinmessage: "&7[&a+&7] &6%player% &7joined the game"
quitmessage: "&7[&c-&7] &6%player% &7left the game"
```

### VIP System
```yaml
permission_messages:
  enabled: true
  join:
    myserver.diamond: "&7[&bDIAMOND&7] &b%player% &7sparkled into the server!"
    myserver.gold: "&7[&6GOLD&7] &6%player% &7joined with style!"
    myserver.iron: "&7[&7IRON&7] &7%player% &7joined the server"
```

### Seasonal Messages
```yaml
random:
  enabled: true
  messages:
    join:
      - "&7[&2+&7] &6%player% &7joined! &cHappy Holidays!"
      - "&7[&2+&7] &6%player% &7entered the winter wonderland!"
      - "&7[&2+&7] &a🎄 &6%player% &7joined the festivities!"
```

## Support

- **GitHub Issues:** Report bugs and request features
- **Discord:** Join our community server
- **Wiki:** Detailed documentation and tutorials

## Dependencies

- **Spigot/Paper:** 1.8+
- **PlaceholderAPI:** Optional (for placeholder support)
- **Java:** 8+