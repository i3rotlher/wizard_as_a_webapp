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
                <img class="playingCard trump"
                :src="trumpCard"
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

        <!-- Modal -->
    <!-- <div class="modal fade" id="scoreBoardModal" tabindex="-1" aria-labelledby="Score" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content" style="background: none; border: none;">
                <div class="modal-header">
                    @*                    <h1 class="modal-title fs-5" id="exampleModalLabel">Modal title</h1>*@
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <table class="table table-striped scoreBoard">
                        <thead>
                            <tr>
                                <th scope="col">Round</th>
                                @for(player <- players) {
                                    <th>@player.name</th>
                                }
                            </tr>
                        </thead>
                        <tbody>
                        @for((round, i) <- rounds.zipWithIndex.dropRight(1)) {
                            <tr>
                                <th scope="row">@( )</th>
                                @for(idx <- players.indices) {
                                    <td>
                                        @round.results(idx) | @round.guessed_tricks(idx)
                                    </td>
                                }
                            </tr>
                        }
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div> -->
</template>

<script>
export default {
  name: 'WishTrumpView',
  methods: {
    getPlayerName: function(){
        return "Test"
    },
    getHand: function(){
        return ["http://localhost:9000/assets//images/card-images/green6.png", "http://localhost:9000/assets//images/card-images/yellow6.png", "http://localhost:9000/assets//images/card-images/blue8.png"]
    },
    getRounds: function() {
        return []
    }
  },
  mounted () {
    document.body.classList.add('marketPlaceBackground');
    this.playerName = this.getPlayerName();
    this.hand = this.getHand();
    this.rounds = this.getRounds();
  },
  data() {
    return {
      playerName: "unknown",
      hand: [],
      trumpCard: "http://localhost:9000/assets//images/card-images/green3.png",
    }
  },

}
</script>