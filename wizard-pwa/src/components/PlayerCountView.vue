<template>
    <div class="hor">
    <div class="container h-100">
        <h2>This game can be played by 3-6 Players.   <br>
            Please select the amount of players that want to play:</h2>

        <div class="container">
            <div class="text-center">
                <button type="button" class="pla">3</button>
                <button type="button" class="pla">4</button>
                <button type="button" class="pla">5</button>
                <button type="button" class="pla">6</button>
            </div>
        </div>
    </div>
    </div>
</template>

<script>
export default {
  name: 'IndexView',
  mounted () {
    document.body.classList.add('marketPlaceBackground')
  }
}
</script>