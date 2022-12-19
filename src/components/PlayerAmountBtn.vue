<script>
export default {
  props: {
    title: String
  },
}
</script>

<template>
  <button type="button" class="pla">{{ title }}</button>
</template>
<style>
.pla{
  align-content: center;
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
</style>