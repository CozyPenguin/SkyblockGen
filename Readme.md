# SkyblockGen *\[Fabric\]*

![Modrinth badge](https://img.shields.io/modrinth/dt/QNLCsQ9H?color=green&label=Modrinth) ![Environment badge](https://img.shields.io/badge/environment-server%2c%20opt%20client-c65135?label=Environment)

## Overview

This is a small mod which allows the generation of Skyblock worlds while preserving biomes. Currently it only works for the overworld dimension.
The mod also lacks support for modded biomes at spawn because the island structure can only spawn in vanilla biomes, but I'm working on resolving this.

![Skyblock Island](./images/island.png)

## How it works

To create a skyblock world in singleplayer, you have to set the world type to "Skyblock" in the world creation menu.
On servers you have to set `level-type=skyblockgen:skyblock`.

## Dependencies

<div style="max-width:200px">

[![Requires Fabric API](https://i.imgur.com/Ol1Tcf8.png)](https://modrinth.com/mod/fabric-api)

</div>

## What's next?

- Configuration
- Support for modded biomes at spawn
- Custom Islands *(technically possible if you replace the structure in a datapack)*
- Custom spawnpoint