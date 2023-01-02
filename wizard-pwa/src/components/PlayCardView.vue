<template>
    <div class="container-fluid">
        <div class="row">
            <div class="col-2">
                <h1 id="playerNameTopLeft">{{playerName}}'s Turn</h1>
            </div>
            <div class="col-8">
                <h1 class="actionDescription">Phase: Play a Card</h1>
            </div>
            <div class="col-2">
                    <!-- Button trigger modal -->
                <button type="button" class="showScoreBoardBtn btn btn-secondary" data-bs-toggle="modal" data-bs-target="#scoreBoardModal">
                    <i class="bi bi-table"></i><br>
                    Score
                </button>
            </div>
        </div>
        <div class="row row-cols-3">
            <div class="playedCards col-4">
            <h2>Played Cards:</h2>
                <div class="animated-card-hand-container">
                    <div id="playedCardsStack" class="animated-card-hand-played">
                        <div class="animated-card" v-for="card in playedCards" :key="card">
                            <img class="playingCard" :src="card" />
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-4"></div>
            <div class="trumpCard col-4">
                <h2>Trump Card:</h2>
                <img class="playingCard trump" :src="trumpCard"/>
            </div>
            <div class="col-12">
                <div class="playCardBackground">
                    <div class="animated-card-hand-container">
                        <div id="playerHandCards" class="animated-card-hand">
                            <div class="animated-card" v-for="card in hand" :key="card">
                                <img class="playingCard" :src="card"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
  name: 'WishTrumpView',
  props: {
    playerName: String,
    playedCards: [],
    hand: [],
    trumpCard: String,
  },
  mounted () {
    document.body.classList.add('marketPlaceBackground');
  },
}
</script>