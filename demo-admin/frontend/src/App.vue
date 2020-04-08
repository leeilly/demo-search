<template>
  <div id="app">
    <Search msg="Hi"/>
  </div>
</template>

<script>
import Search from './components/Search.vue'
import request from "request"

export default {
  name: 'App',
  components: {
    Search
  },
  mounted(){
    request('http://localhost:8081/api/hello', function (error, response, body) {
      console.log('error', error);
      console.log('statusCode', response && response.statusCode);
      console.log('body', body);

    });
  }
}


</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
