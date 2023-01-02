<template>
  <div id="waitingForPlayers" class="loading">
    <div id="waitingPlayer">Waiting for other players <span>.</span><span>.</span><span>.</span></div>
  </div>
  <IndexView v-if="state=='index'"></IndexView>
  <PlayerCountViewVue v-if="state=='playercount'"></PlayerCountViewVue>
  <PlayerNameViewVue v-if="state=='playername'"></PlayerNameViewVue>
  <WishTrumpViewVue v-if="state=='wishtrump'"></WishTrumpViewVue>
  <GuessTricksViewVue v-if="state=='guesstrick'"></GuessTricksViewVue>
  <PlayCardViewVue v-if="state=='playcard'" trumpCard="http://localhost:9000/assets//images/card-images/green6.png" :hand='["http://localhost:9000/assets//images/card-images/green10.png"]' :playedCards='["http://localhost:9000/assets//images/card-images/green6.png", "http://localhost:9000/assets//images/card-images/green7.png"]' playerName="Max" ></PlayCardViewVue>
</template>

<script>
import IndexView from './components/IndexView.vue';
import PlayerCountViewVue from './components/PlayerCountView.vue';
import PlayerNameViewVue from './components/PlayerNameView.vue';
import WishTrumpViewVue from './components/WishTrumpView.vue';
import GuessTricksViewVue from './components/GuessTricksView.vue';
import PlayCardViewVue from './components/PlayCardView.vue';
import $ from 'jquery';

export default {
  name: 'App',
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
      state: "playcard"
    }
	},
  methods: {
    wait: function() {
      $("#waitingForPlayers").css("display", "grid")
    }
  }, 
  mounted() {
    
  }
}
</script>

<style>
</style>
