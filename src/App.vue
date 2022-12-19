<script>
import indexComponent from "@/components/indexComponent.vue";
import PlayerAmount from "@/components/PlayerAmount.vue";

import "../public/javascripts/main.js";

export default {
  data() { // TODO: change to computed protperty and fetch data from backend
    return {
      gameStatus: 'playerCount',
      playerAmount: 3,
      activePlayer: 1,
      players:[],
    }
  },
  name: 'App',
  components: {
    indexComponent,
    PlayerAmount,
    //TopBar,
    //HelloWorld
  }
}
</script>

<template>
  <indexComponent v-if="gameStatus==='Index'"></indexComponent>
  <player-amount v-if="gameStatus==='playerCount'"></player-amount>


</template>

<style>
@import "./assets/style.css";
body {
  background-image: url('https://pbs.twimg.com/media/E6btcNMXoAMFHOF?format=jpg&name=large');
}
</style>
