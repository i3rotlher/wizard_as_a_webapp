<template>
        <div class="hor">
        <div class="container h-100 playerName">
            <h2 id="playerNameText" value="{{idx}}">Player {{idx}} please insert your name:</h2>
            <input class="form-control in" type="text" placeholder="your name ..." id="name" style="width:450px">
        </div>
    </div>
</template>

<script>
export default {
  name: 'PlayerNameView',
  methods: {
    getIndex: function(){
     return "Test"
    },
  },
  mounted () {
    document.body.classList.add('marketPlaceBackground');
  },
  props: {
      idx: String
  },

}
</script>