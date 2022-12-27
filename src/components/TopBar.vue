<template>
  <div class="row">
        <div class="col-2">
            <h1 id="playerNameTopLeft">{{player}}'s Turn</h1>
        </div>
        <div class="col-8">
            <h1 class="actionDescription">Phase: {{phase}}</h1>
        </div>
        <div class="col-2">
                <!-- Button trigger modal -->
            <button type="button" class="showScoreBoardBtn btn btn-secondary" data-bs-toggle="modal" data-bs-target="#scoreBoardModal">
                <i class="bi bi-table"></i><br>
                Score
            </button>
        </div>
  </div>
</template>

<script>
export default {
  name: 'TopBar',
  props: {
    player: String,
    phase: String
  },
  mounted() {
      let recaptchaScript = document.createElement('script')
      recaptchaScript.setAttribute('src', '\'https://cdn.jsdelivr.net/npm/bootstrap@@5.2.2/dist/js/bootstrap.bundle.min.js\' integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"')
      document.head.appendChild(recaptchaScript)
    },
};
</script>

<style>
.showScoreBoardBtn {
  width: 150px !important;
  color: #debd4f !important;
  background: none !important;
  border: none !important;
  text-shadow: 1px 1px 2px black !important;
  font-size: 25px !important;
  -webkit-text-stroke-width: 1px !important;
}
</style>