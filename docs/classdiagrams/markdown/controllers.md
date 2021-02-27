


// NOTE
// This file is broken down by subsystem.
// Otherwise the diagram is too hard to read.




classDiagram
class GetReplayExitRoute {
// Nothing
}

GetReplayExitRoute..>UserSession

class GetReplayRoute {
	- templateEngine: templateEngine
	- playerLobby: PlayerLobby
	- gameCenter: GameCenter
	- replayList: ReplaysList
	- gson: Gson
}

GetReplayRoute..>UserSession
GetReplayRoute..>Player
GetReplayRoute..>Game
GetReplayRoute..>Replay
GetReplayRoute..>BoardView



class PostReplayNextTurnRoute {
	- gson: Gson
	- replaysList: ReplaysList
	- gameCenter: GameCenter
}

PostReplayNextTurnRoute..>UserSession
PostReplayNextTurnRoute..>Replay
PostReplayNextTurnRoute..>Move

class PostReplayPreviousTurnRoute {
	- gson: Gson
	- replaysList: ReplaysList
	- gameCenter: GameCenter
}

PostReplayPreviousTurnRoute..>UserSession
PostReplayPreviousTurnRoute..>Replay
PostReplayPreviousTurnRoute..>Move
PostReplayPreviousTurnRoute..>Game






classDiagram

class GetHomeRoute {
	- templateEngine: templateEngine
	- playerLobby: PlayerLobby
	- replayList: ReplaysList
}

GetHomeRoute..>UserSession
GetHomeRoute..>Player

class GetSignInRoute {
	- templateEngine: templateEngine
}

GetSignInRoute..>UserSession


class PostSignInRoute {
	- templateEngine: TemplateEngine
	- playerLobby: PlayerLobby
}

PostSignInRoute..>UserSession
PostSignInRoute..>Player

class PostSignOutRoute {
	- playerLobby: PlayerLobby
	- gson: Gson
}

PostSignOutRoute..>UserSession
PostSignOutRoute..>Player







classDiagram

class GetGameRoute {
	- templateEngine: templateEngine
	- playerLobby: PlayerLobby
	- gameCenter: GameCenter
	- replayList: ReplaysList
	- winner: Player
	- enum Mode [PLAY, SPECTATOR, REPLAY];
}

GetGameRoute..>UserSession
GetGameRoute..>Player
GetGameRoute..>Game
GetGameRoute..>BoardView


class PostResignGameRoute {
	- gson: Gson
	- gameCenter: GameCenter
}

PostResignGameRoute..>UserSession
PostResignGameRoute..>Player
PostResignGameRoute..>Game


class PostGameRoute {
	- playerLobby: PlayerLobby
	- gameCenter: GameCenter
}

PostGameRoute..>UserSession
PostGameRoute..>Player
PostGameRoute..>Game








classDiagram


class PostSubmitTurnRoute {
	- gson: Gson
	- gameCenter: GameCenter
}

PostSubmitTurnRoute..>UserSession
PostSubmitTurnRoute..>Player
PostSubmitTurnRoute..>Game
PostSubmitTurnRoute..>Board
PostSubmitTurnRoute..>Move

class PostValidateTurnRoute {
	- gson: Gson
}

PostValidateTurnRoute..>Position
PostValidateTurnRoute..>UserSession
PostValidateTurnRoute..>Move
PostValidateTurnRoute..>Piece


class PostBackupMoveRoute {
	- gson: Gson
}

PostBackupMoveRoute..>UserSession


class PostCheckTurnRoute {
	- gson: Gson
	- gameCenter: GameCenter
}

PostCheckTurnRoute..>UserSession
PostCheckTurnRoute..>Game
PostCheckTurnRoute..>Piece
PostCheckTurnRoute..>Player
