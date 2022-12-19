<template>
    <div class="col-12">
        <div class="playCardBackground">
            <div class="animated-card-hand-container">
                <div id="playerHandCardsTrick" class="animated-card-hand">            
                    <div class="animated-card" v-for="card in cards.cards" v-bind:key="card">
                        <img class="playingCard" :src="card" />  
                    </div>                       
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
  name: 'CardsRow',
  props: {
    cards: {"cards": Array}
  }
};
</script>

<style>
.playCardBackground{
  background-size: cover;
  border-radius: 20px;
  padding: 15px;
  margin-top: 45px;
  background-color: #737419bd;
  background-blend-mode: overlay;
  background-repeat: no-repeat;
  background-image: url(https://img.freepik.com/free-vector/flat-design-wood-texture-illustration_23-2149273581.jpg?w=1480&t=st=1667914799~exp=1667915399~hmac=175b2fe76c35a913c0f9715bf99072fb96357c831d39a14134409feefe246924);
}

.playingCard{
  cursor: pointer;
  width: 150px;
  height: 230px;
  filter: drop-shadow(3px 3px 5px black);
  border: solid rgba(0, 0, 0, 0.23);
  border-radius: 15px;
}

.animated-card-hand-container {
  display: grid;
  height: 240px;
  margin: 0;
  place-items: center;
}

.animated-card-hand,
.animated-card {
  aspect-ratio: 5 / 7;
  width:  inherit;
  position: relative;
  height: 150px;
  top: -50%;
}

.animated-card {
  border-radius: 2px;
  position: absolute;
  top: 50%;
  transition: all 600ms cubic-bezier(.17,.67,.6,.97);
}
.animated-card:hover {
  top: 30%;
}

.animated-card:nth-of-type(1) {
  transform: translateX(-440%);
}
.animated-card:nth-of-type(2) {
  transform: translateX(-400%);
}
.animated-card:nth-of-type(3) {
  transform: translateX(-360%) ;
}
.animated-card:nth-of-type(4) {
  transform: translateX(-320%) ;
}

.animated-card:nth-of-type(5) {
  transform: translateX(-280%) ;
}

.animated-card:nth-of-type(6) {
  transform: translateX(-240%) ;
}

.animated-card:nth-of-type(7) {
  transform: translateX(-200%) ;
}

.animated-card:nth-of-type(8) {
  transform: translateX(-160%) ;
}

.animated-card:nth-of-type(9) {
  transform: translateX(-120%) ;
}

.animated-card:nth-of-type(10) {
  transform: translateX(-80%) rotate(-3.2deg);
}

.animated-card:nth-of-type(11) {
  transform: translateX(-40%) rotate(2.2deg);
}

.animated-card:nth-of-type(12) {
  transform: translateX(0%) rotate(-2.2deg);
}

.animated-card:nth-of-type(13) {
  transform: translateX(40%) rotate(3.2deg);
}

.animated-card:nth-of-type(14) {
  transform: translateX(80%) rotate(-3.2deg);
}

.animated-card:nth-of-type(15) {
  transform: translateX(120%) rotate(3.2deg);
}

.animated-card:nth-of-type(16) {
  transform: translateX(160%) rotate(-3.2deg);
}

.animated-card:nth-of-type(17) {
  transform: translateX(200%) rotate(2.2deg);
}

.animated-card:nth-of-type(18) {
  transform: translateX(240%) rotate(-2.2deg);
}

.animated-card:nth-of-type(19) {
  transform: translateX(280%) rotate(3.2deg);
}

.animated-card:nth-of-type(20) {
  transform: translateX(320%) rotate(-3.2deg);
}



.animated-card-hand:hover > .animated-card:nth-of-type(1) {
  transform: translate(-440%) rotate(2deg);
}
.animated-card-hand:hover > .animated-card:nth-of-type(2) {
  transform: translate(-400%) rotate(-2deg);
}
.animated-card-hand:hover > .animated-card:nth-of-type(3) {
  transform: translate(-360%) rotate(-3deg);
}
.animated-card-hand:hover > .animated-card:nth-of-type(4) {
  transform: translate(-320%) rotate(3deg);
}
.animated-card-hand:hover > .animated-card:nth-of-type(5) {
  transform: translate(-280%) rotate(-3deg);
}
.animated-card-hand:hover > .animated-card:nth-of-type(6) {
  transform: translate(-240%) rotate(3deg);
}
.animated-card-hand:hover > .animated-card:nth-of-type(7) {
  transform: translate(-200%) rotate(-2deg);
}
.animated-card-hand:hover > .animated-card:nth-of-type(8) {
  transform: translate(-160%) rotate(2deg);
}
.animated-card-hand:hover > .animated-card:nth-of-type(9) {
  transform: translate(-120%) rotate(-2deg);
}
.animated-card-hand:hover > .animated-card:nth-of-type(10) {
  transform: translate(-80%) rotate(2deg);
}
.animated-card-hand:hover > .animated-card:nth-of-type(11) {
  transform: translate(-40%) rotate(-3deg);
}
.animated-card-hand:hover > .animated-card:nth-of-type(12) {
  transform: translate(0%) rotate(3deg);
}
.animated-card-hand:hover > .animated-card:nth-of-type(13) {
  transform: translate(40%) rotate(-2deg);
}
.animated-card-hand:hover > .animated-card:nth-of-type(14) {
  transform: translate(80%) rotate(2deg);
}
.animated-card-hand:hover > .animated-card:nth-of-type(15) {
  transform: translate(120%) rotate(-3deg);
}
.animated-card-hand:hover > .animated-card:nth-of-type(16) {
  transform: translate(160%) rotate(3deg);
}
.animated-card-hand:hover > .animated-card:nth-of-type(17) {
  transform: translate(200%) rotate(-3deg);
}
.animated-card-hand:hover > .animated-card:nth-of-type(18) {
  transform: translate(240%) rotate(3deg);
}
.animated-card-hand:hover > .animated-card:nth-of-type(19) {
  transform: translate(280%) rotate(-3deg);
}
.animated-card-hand:hover > .animated-card:nth-of-type(20) {
  transform: translate(320%) rotate(3deg);
}
</style>