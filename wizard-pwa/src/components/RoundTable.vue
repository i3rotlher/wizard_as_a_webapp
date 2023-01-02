<template>
    <div class="modal fade" id="scoreBoardModal" tabindex="-1" aria-labelledby="Score" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content" style="background: none; border: none;">
                <div class="modal-header">
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <table class="table table-striped scoreBoard">
                        <thead>
                            <tr>
                                <th scope="col">Round</th>
                                    <th v-for="player in players" :key="player">{{player}}</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="(round, index) in rounds" :key="round">
                                <th scope="row">{{index}}</th>
                                    <td v-for="(p, index) in players" :key="p">
                                        {{round.results(index)}} | {{round.guessed_tricks(index)}}
                                    </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
  name: 'RoundTable',
  props: {
    rounds: [],
    players: [],
  },
  mounted () {
    document.body.classList.add('marketPlaceBackground');
  },
}
</script>