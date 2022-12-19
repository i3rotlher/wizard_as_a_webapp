<template>
    <div class="hor">
        <div class="container h-100 playerName">
            <h2 id="playerNameText" value="{{player}}">Player {{player}} please insert your name:</h2>
            <input class="form-control in" type="text" placeholder="your name ..." id="name" style="width:450px">
        </div>
    </div>
</template>

<script>
export default {
  name: 'PlayerName',
  props: {
    player: String
  }
};
</script>

<style>
.hor {
  min-height: 100%;  /* Fallback for browsers do NOT support vh unit */
  min-height: 100vh; /* These two lines are counted as one :-)       */
  display: flex;
  align-items: center;
}

.playerName {
  display: flex;
  align-items: center;
  flex-direction: column;
}

.in{
    opacity: 0.75;
    width: 250px;
}

h2 {
  color: #debd4f;
  font-family: 'akaPotsley', cursive;
  font-weight: normal;
  margin-bottom: 4em;
  text-align: center;
  text-shadow: 1px 1px 2px black;
}
</style>
