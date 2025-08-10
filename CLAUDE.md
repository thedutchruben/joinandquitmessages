# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Minecraft Spigot plugin that customizes player join and quit messages. The plugin supports both static and random messages with PlaceholderAPI integration and hex color code support for Minecraft 1.16+.

## Build Commands

- **Build the plugin**: `mvn clean package`
- **Clean build**: `mvn clean` 
- **Compile only**: `mvn compile`

The default goal is set to `clean package`, so running `mvn` without arguments will clean and build the plugin.

## Architecture

### Core Components

- **JoinAndQuitMessages.java**: Main plugin class that handles initialization, configuration loading, metrics, and message retrieval logic
- **JoinAndQuitListener.java**: Event listener that processes player join/quit events and applies message formatting

### Key Features

- Static or random message selection (configured via `random.enabled`)
- PlaceholderAPI integration (soft dependency)
- Hex color code support for Minecraft 1.16+ using `<#RRGGBB>` format
- Configuration auto-migration and update checking via mccore library
- bStats metrics integration

### Dependencies

- **Spigot API 1.21.5**: Core Minecraft server API (provided)
- **PlaceholderAPI 2.11.6**: Placeholder expansion (provided, optional)
- **bStats 3.0.3**: Plugin statistics (shaded)
- **mccore 1.7.0**: Update checker and utilities (compiled)

### Configuration Structure

The plugin creates a config.yml with:
- `joinmessage`/`quitmessage`: Default messages
- `random.enabled`: Toggle for random message mode
- `random.messages.join`/`random.messages.leave`: Arrays of random messages
- `update_check`: Enable/disable update notifications

### Message Processing Flow

1. Retrieve message (static or random selection)
2. Replace `%player%` placeholder with player name
3. Apply PlaceholderAPI placeholders if available
4. Convert `&` color codes to section symbols
5. Process hex colors for MC 1.16+ using regex pattern matching
6. Set the formatted message on the event

## Development Notes

- Plugin uses Java 8 compatibility
- Maven Shade plugin relocates bStats to avoid conflicts
- The `@TDRListener` annotation is from the mccore library for automatic listener registration
- Version checking and metrics are integrated but can be disabled in config