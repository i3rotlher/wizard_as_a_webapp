<template>
    <div class="container-fluid">
        <div class="row">
            <div class="col-2">
                <h1>{{playerName}}'s Turn</h1>
            </div>
            <div class="col-8">
                <h1 class="actionDescription">Phase: Wish a Trump</h1>
            </div>
            <div class="col-2">
                    <!-- Button trigger modal -->
                <button type="button" class="showScoreBoardBtn btn btn-secondary" data-bs-toggle="modal" data-bs-target="#scoreBoardModal">
                    <i class="bi bi-table"></i><br>
                    Score
                </button>
            </div>
        </div>
        <div class="row row-cols-12">
            <div class="col-12">
                <div class="hor" style="margin-top: 100px; margin-bottom: 25px; min-height: auto;">
                    <div class="container h-100">
                        <h1>A Wizard is Trump! You can wish a Trumpcolor:</h1>
                        <div class="text-center">
                            <button class="trumpSelect" id="yellowTrump" value="yellow">Yellow</button>
                            <button class="trumpSelect" id="greenTrump" value="green">Green</button>
                            <button class="trumpSelect" id="blueTrump" value="blue"> Blue</button>
                            <button class="trumpSelect" id="redTrump" value="red">Red</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-12">
                <div class="playCardBackground">
                    <div class="animated-card-hand-container">
                        <div class="animated-card-hand">
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
  },

}
</script>