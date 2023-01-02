<template>
    <div class="container-fluid">
        <div class="row">
            <div class="col-2">
                <h1 id="playerNameTopLeft">{{playerName}}'s Turn</h1>
            </div>
            <div class="col-8">
                <h1 class="actionDescription">Phase: Trick Prediction</h1>
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
            <div class="trickInsert col-8">
                <h2 style="text-align: start">How many tricks are you going to make?</h2>
                <input
                class="form-control"
                type="number"
                id="tricks"
                value="0"
                min="0"
                    style="width: fit-content"
                />
            </div>
            <div class="trumpCard col-4">
                <h2>Trump Card:</h2>
                <img class="playingCard trump" v-for="card in trumpCard" :key="card"
                v-bind:src="card"
                />
            </div>
            <div class="col-12">
                <div class="playCardBackground">
                    <div class="animated-card-hand-container">
                        <div id="playerHandCardsTrick" class="animated-card-hand">
                            <div class="animated-card" v-for="card in hand" :key="card">
                                <img class="playingCard" :src="card" />
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
  mounted () {
    document.body.classList.add('marketPlaceBackground');
  },
  props: {
      playerName: String,
      hand: [],
      trumpCard: [],
  },

}
</script>