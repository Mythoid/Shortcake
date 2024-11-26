# Project Shortcake

An attempt to build a Minecraft SMP server to be played within a group of friends; there is currently no concept that distinguishes the server with other SMPs.

*Notice: This repository in unlicensed (All Rights Reserved) for now, this may change after a working version of all components.*

## (Planned) Features
* Custom items w/ interactions
* Custom blocks
  * Machinery
  * Cooking
  * Crops
* Custom mobs
  * Models from Animated Java (Blockbench)
  * Integration with Xaero's Minimap Radar
* Custom enchantments
* Vanilla behaviors (possible candidate: LeavesMC)
* New core progression system after The End

## Components
* `server-core`: A Paper plugin that contains all mechanics, systems and content for SMP.
* `cherry-on-top`: A client-sided Fabric/NeoForged mod that improves experience while playing in the server.

## Q&A
* What version is this being developed on?
  > 1.21.3, and hopefully it stays at that.
* Why Paper?
  > Because, it's the one I know my way around most. Fabric modding for server-sided content is also quite interesting but totally, I'm not familiar enough.
  >
  > The Bukkit API also has entire toolkit to modify vanilla behaviors too, making it faster to develop on.
* Does the server (somehow) disable map mods?
  > Yes, but actually not. I made it so only Xaero's map mods gets enabled if CherryOnTop is installed to improve player experience on the server.
* Why is your code so bad???
  > I know right? lol
  >
  > Seriously though, I wrote everything up without relying on too much of libraries this time, because I want to explore how the client-server works and all.