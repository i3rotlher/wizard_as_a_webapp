<template>
  <IndexView v-if="state=='index'"></IndexView>
  <PlayerCountViewVue v-if="state=='playercount'"></PlayerCountViewVue>
  <PlayerNameViewVue v-if="state=='playername'"></PlayerNameViewVue>
  <WishTrumpViewVue v-if="state=='wishtrump'"></WishTrumpViewVue>
  <GuessTricksViewVue v-if="state=='guesstrick'"></GuessTricksViewVue>
</template>

<script>
import IndexView from './components/IndexView.vue';
import PlayerCountViewVue from './components/PlayerCountView.vue';
import PlayerNameViewVue from './components/PlayerNameView.vue';
import WishTrumpViewVue from './components/WishTrumpView.vue';
import GuessTricksViewVue from './components/GuessTricksView.vue';

export default {
  name: 'App',
  components: {
    IndexView,
    PlayerCountViewVue,
    PlayerNameViewVue,
    WishTrumpViewVue, 
    GuessTricksViewVue
  },
  data() {
    return {
      state: "guesstrick"
    }
	}
}
</script>

<style>
</style>
