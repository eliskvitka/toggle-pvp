# Toggle PVP mod
![license](https://img.shields.io/github/license/braunly/toggle-pvp)
![fabric](https://img.shields.io/badge/modloader-Fabric-1976d2?style=flat-square)
![server only](https://img.shields.io/badge/environment-server-4caf50?style=flat-square)  

Simple Fabric server-side mod (plugin), that allow your players to enable/disable PVP for themselves.  
It ignores `server.properties` pvp flag currently
## Requirements
- [Fabric loader](https://fabricmc.net/use/server/) >= 0.13.1  
- [Fabric API](https://www.curseforge.com/minecraft/mc-mods/fabric-api)  
- [PlayerAbilityLib](https://www.curseforge.com/minecraft/mc-mods/pal)  
- Also, you need some permission manager mod, that works with Fabric Permissions API. I suggest [LuckPerms](https://luckperms.net/download)  
## Installation
- Install all required libraries  
- Copy this mod to `mods/` folder  

## Commands and permissions
| Command       | Permission    | Description |
| ------------- | ------------- | ------------- |
| /pvp | togglepvp.base  | Toggle PVP status for self |
| /pvp <target_player> <on/off>  | togglepvp.others  | Toggle PVP status for other player (Suggests this for admins) |
| - | togglepvp.ignorecooldown  | Ignore command cooldown (Not implemented yet) |

## Configuration
Will be added later  

## ToDo
- `cooldown` config option  
- `ignoreServerPropertiesPvp` config option  
- `forcePvpStatusInDimmention` config option  

## License
This mod is available under the [GNU LGPL v3.0 license](LICENSE).
