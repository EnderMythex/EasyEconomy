# EasyEconomy – Because emeralds are overrated!

**EasyEconomy** is a lightweight and fully server-side **Fabric** mod that brings a simple, diamond-based economy to your Minecraft world.
Deposit, withdraw, trade, and run your own shop — all powered by the sparkle of diamonds.

Perfect for survival servers, SMPs, and economy-driven communities.

---

## Fork Changelog (v1.1.7)

### Bug Fixes
- **Fixed item duplication** when 2 players buy the same item simultaneously — buy operations are now synchronized with a global lock and atomic listing removal
- **Fixed item loss on failed purchase** — listings are no longer removed before checking if the player has enough diamonds
- **Fixed leaderboard showing UUIDs** instead of player names for offline/crack players — added a persistent name cache (`name_cache.json`)

### New Features
- **Refresh button** in the Shop GUI (emerald icon, bottom row) to reload listings without closing the menu
- **`/leaderboard`** — Shows the top 10 richest players
- **`/shop list`** — Filter the shop by the item you're holding (e.g. hold dirt to see all dirt listings)
- **`/shopadmin set <player> <amount>`** — Set a player's balance (OP only). Accepts player names with autocomplete
- **`/shopadmin list`** — List all players and their balances (OP only)
- Autocomplete for `/shopadmin set` includes online players, cached names, and UUIDs for players without cached names

### Changes
- Renamed **Auction House** to **Shop** throughout the mod
- `/ah` is now `/shop`
- `/ah sell <price>` is now `/shop sell <price>`
- `/ah expired` has been removed
- Shop GUI title now shows "Shop" instead of "Auction House"

---

## Features

- Deposit and withdraw diamonds from your personal bank
- Securely track your diamond balance with `/balance`
- Send diamonds directly to other players with `/pay`
- Buy and sell items with a fully-featured Shop GUI
- Per-player data stored in easy-to-read JSON files
- 100% server-side — no client mod required!

---

## Commands

### Player Commands
| Command | Description |
|---|---|
| `/deposit <qty>` | Deposit diamonds from your inventory into your bank |
| `/withdraw <qty>` | Withdraw diamonds from your bank into your inventory |
| `/balance` | Check your current diamond balance |
| `/pay <player> <qty>` | Transfer diamonds to another player |
| `/shop` | Open the Shop GUI to view and buy listings |
| `/shop sell <price>` | Sell the item in your hand for the specified price |
| `/shop list` | Search the shop for the item you're holding |
| `/leaderboard` | Show the top 10 richest players |

### Admin Commands (OP required)
| Command | Description |
|---|---|
| `/shopadmin set <player> <amount>` | Set a player's diamond balance |
| `/shopadmin list` | List all players and their balances |

---

## Requirements

- [Fabric Loader](https://fabricmc.net/use/)
- [Fabric API](https://modrinth.com/mod/fabric-api)

---

## License

This mod is licensed under the GNU AGPLv3 Licence.
