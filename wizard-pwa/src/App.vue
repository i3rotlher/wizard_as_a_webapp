<template>
  <div id="waitingForPlayers" class="loading">
    <div id="waitingPlayer">
      Waiting for other players <span>.</span><span>.</span><span>.</span>
    </div>
  </div>
  <IndexView v-if="state == 'index'"></IndexView>
  <PlayerCountViewVue v-if="state == 'playercount'"></PlayerCountViewVue>
  <PlayerNameViewVue v-if="state == 'playername'"></PlayerNameViewVue>
  <WishTrumpViewVue
    v-if="state == 'wishtrump'"
    :playerName="playerName"
    :hand="hand"
  ></WishTrumpViewVue>
  <GuessTricksViewVue
    v-if="state == 'guesstrick'"
    :playerName="playerName"
    :hand="hand"
    :trumpCard="trump"
  ></GuessTricksViewVue>
  <PlayCardViewVue
    v-if="state == 'playcard'"
    :trumpCard="trump"
    :hand="hand"
    :playedCards="playedCards"
    playerName="Max"
  ></PlayCardViewVue>
</template>

<script>
import IndexView from "./components/IndexView.vue";
import PlayerCountViewVue from "./components/PlayerCountView.vue";
import PlayerNameViewVue from "./components/PlayerNameView.vue";
import WishTrumpViewVue from "./components/WishTrumpView.vue";
import GuessTricksViewVue from "./components/GuessTricksView.vue";
import PlayCardViewVue from "./components/PlayCardView.vue";
import $ from "jquery";

export default {
  name: "App",
  components: {
    IndexView,
    PlayerCountViewVue,
    PlayerNameViewVue,
    WishTrumpViewVue,
    GuessTricksViewVue,
    PlayCardViewVue,
  },
  data() {
    return {
      state: "index",
      socket: undefined,
      hand: [],
      playedCards: [],
      trump: [],
      playerName: String,
    };
  },

  methods: {
    reactToSocket: function (msg) {
      $("#waitingForPlayers").css("display", "none");
      if (msg.fetch) {
        console.log(msg.fetch);
        switch (msg.fetch) {
          case "http://localhost:9000/setTrickAmount":
            this.updateState("guesstrick");
            try {
              this.getCards();
            } catch {
              console.log();
            }
            break;
          case "http://localhost:9000/trump":
            this.updateState("wishtrump");
            try {
              this.getCards();
            } catch {
              console.log();
            }
            break;
          case "http://localhost:9000/playCard":
            this.updateState("playcard");
            try {
              this.getCards();
            } catch {
              console.log();
            }
            break;
        }
        this.addEventListeners();
        return;
      }

      if (msg.event) {
        switch (msg.event) {
          case "card_not_playable":
            window.alert(
              "Card not playable! \n First color need to be served."
            );
            break;
          case "NotYourTurn":
            $("#waitingForPlayers").css("display", "grid");
            $("#waitingPlayer").html(
              `<div id="waitingPlayer">Waiting for ${msg.activePlayer} <span>.</span><span>.</span><span>.</span></div>`
            );
            break;
          case "cardUpdate":
            this.setCards(msg);
            break;
        }
      }
    },

    updateState: function (newState) {
      this.state = newState;
      this.addEventListeners();
    },

    setTrump: function (color) {
      this.socket.send(color);
    },
    play: function () {
      fetch(`http://localhost:9000/`, {
        method: "GET",
      }).then((res) => {
        console.log(res.status);
        if (res.status === 406) {
          this.updateState("playername");
        }
      });
      this.updateState("playercount");
    },

    sendPlayerCount: function (amount) {
      fetch(`http://localhost:9000/playerCount?count=${amount}`, {
        method: "POST",
      }).then(() => {
        this.updateState("playername");
      });
    },

    connect: function () {
      const react = this.reactToSocket;
      if (this.socket === undefined) {
        this.socket = new WebSocket("ws://localhost:9000/websocket");
        this.socket.onopen = function () {
          console.log("Socket opened");
          this.socket.send("MyTurn?");
        };
        this.socket.onmessage = function (message) {
          console.log("Socket received Massage: ", JSON.parse(message.data));
          react(JSON.parse(message.data));
        };
        this.socket.onerror = function () {
          console.log("Socket received error!");
        };
        this.socket.onclose = function () {
          console.log("WebSocket closed!");
        };
      }
    },

    setName: function (name) {
      this.playerName = name;
      this.socket.send(name);
    },

    setTricks: function (amount) {
      this.socket.send(amount);
    },

    playCard: function (idx) {
      this.socket.send(idx);
    },

    addEventListeners: function () {
      const play = this.play;
      const sendPlayerCount = this.sendPlayerCount;
      const playCard = this.playCard;
      const setName = this.setName;
      const setTricks = this.setTricks;
      const setTrump = this.setTrump;

      $("#playerHandCards").on("click", ".animated-card", function () {
        playCard($(this).index());
      });

      $("#playWizard").click(function () {
        console.log("Start Game");
        document
          .getElementById("playGameContainer")
          .setAttribute("style", "display: none");
        $("#loadGameContainer").css("display", "inline");
        setTimeout(function () {
          $("#canvas").css("display", "block");
        }, 500);
        setTimeout(function () {
          play();
        }, 4050);
      });

      // $(".pla").click(function (ev) {
      //   sendPlayerCount(ev.target.innerText)
      // });
      document.querySelectorAll(".pla").forEach((item) => {
        item.addEventListener("click", (event) => {
          sendPlayerCount(event.target.innerText);
        });
      });

      $("#name").keydown(function (ev) {
        console.log("Set Player Name");
        if (ev.key === "Enter") {
          setName(ev.target.value);
        }
      });

      // $(".trumpselect").click(function (ev) {
      //   console.log("Wish Trump");
      //     setTrump(ev.target.value);
      // });

      document.querySelectorAll(".trumpselect").forEach((item) => {
        item.addEventListener("click", (event) => {
          setTrump(event.target.innerText);
        });
      });

      $("#trickOverOK").click(function () {
        console.log("Trick over ok");
      });

      $("#tricks").keydown(function (ev) {
        console.log("Set Tricks");
        if (ev.key === "Enter") {
          setTricks(ev.target.value);
        }
      });
    },

    getCards: function () {
      this.socket.send("cards");
    },

    setCards: function (msg) {
      console.log("RECIEVED CARDS: ");
      console.log(msg);
      this.playedCards = JSON.parse(msg.played);
      this.trump = JSON.parse(msg.trump);
      this.hand = JSON.parse(msg.hand);
    },
  },
  mounted() {
    this.addEventListeners();
  },
  updated() {
    console.log("Updated");
    this.addEventListeners();
    if (this.state === "playername") this.connect();
  },
};
</script>
