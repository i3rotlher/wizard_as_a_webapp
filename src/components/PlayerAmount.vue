<script>
import PlayerAmountBtn from "@/components/PlayerAmountBtn.vue";
export default {
  name: 'PlayerAmount',
  components: {
    PlayerAmountBtn: PlayerAmountBtn
  },
  data() {
    return {
      amounts: [3,4,5,6]
    }
  },
  props: {
    title: String
  },
};
</script>

<template>
  <div class="container h-100">
    <h2>This game can be played by 3-6 Players.   <br>
      Please select the amount of players that want to play:</h2>

    <div class="container" id="player-amount-container">
      <div class="text-center">
        <PlayerAmountBtn :title="3"></PlayerAmountBtn>
        <PlayerAmountBtn :title="4"></PlayerAmountBtn>
        <PlayerAmountBtn :title="5"></PlayerAmountBtn>
        <PlayerAmountBtn :title="6"></PlayerAmountBtn>
      </div>
    </div>
  </div>
</template>


<style>
.pla{
    width: 200px;
    height: 110px;
    border-radius: 20px;
    border: none;
    background: url('https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRedn293ViBlZuQgEgmNx7lTt58pD9hFreT4XddKZhe7ZxdfH92jyncTe8-LqwJQswxc4g&usqp=CAU') no-repeat top left;
    margin: 2px 8px;
    font-size: 500%;
    opacity: 0.75;

}

.pla:hover {
  background-color: #4CAF50; /* Green */
  color: white;
}
.text-center{
  text-align: center;
}
</style>
