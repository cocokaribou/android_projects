## Multiplayer Werewolf Game

- realtime multiplayer android server/client app
- rule inspired by Japanese visual novel キミガシネ - 多数決デスゲーム(Your Turn to Die - Death Game By Majority)
[official site](https://site.nicovideo.jp/atsumaru/contents/kimigashine/)


### things need to be reminded
- multi client to one server (more than one connection on the same port)
- for every client you need to start a new thread (or Coroutines with Dispatchers.IO)

### things I'm still struggling to figure out
- stack fragments or switch activities when game proceeds?
- 
