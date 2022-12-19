<template>
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
            <img class="playingCard trump" :src="path"
            />
        </div>
</template>

<script>
export default {
  name: 'GuessRow',
  props: {
    path: String
  }
};
</script>

<style>
.trumpCard {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.trickInsert{
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin-top: 144px;
}

h2 {
  color: #debd4f;
  font-family: 'akaPotsley', cursive;
  font-weight: normal;
  margin-bottom: 4em;
  text-align: center;
  text-shadow: 1px 1px 2px black;
}

.playingCard{
  cursor: pointer;
  width: 150px;
  height: 230px;
  filter: drop-shadow(3px 3px 5px black);
  border: solid rgba(0, 0, 0, 0.23);
  border-radius: 15px;
}

.trump {
  cursor: default;
}
</style>