<template>
  <div class="search-demo">
    <b-card
      header=""
      style="max-width: 40rem; margin: auto; margin-top: 10vh;"
      class="mb-2"
      border-variant="info"
      align="left">

      <b-form-group id="search-input">
        <b-container fluid>
          <b-row class="my-1">
            <b-col sm="10">
              <b-form-input v-model="keyword" type="text" v-on:keyup="searchAc" v-on:keypress="searchAc"/>
            </b-col>
            <b-col sm="2">
              <b-button variant="outline-primary" v-on:click="search">검색</b-button>
            </b-col>
          </b-row>
        </b-container>
      </b-form-group>

      <b-list-group id="search-ac-result" v-if="acResult && acResult.length">
        <b-list-group-item
          v-for="item of acResult"
          v-bind:data="item.goodsName"
          v-bind:key="item.goodsNo">
          {{item.highlight}}
        </b-list-group-item>
      </b-list-group>
    </b-card>

    <div>
      <b-list-group id="search-result" v-if="result && result.length">
        <b-list-group-item
          v-for="item of result"
          v-bind:data="item.goodsName"
          v-bind:key="item.goodsNo">
          {{item.goodsName}}
        </b-list-group-item>
      </b-list-group>
    </div>

  </div>
</template>

<script>
  import axios from 'axios'
  let baseUrl = 'http://localhost:8016/v1/search/goods'

  export default {
    name: 'search-demo',
    data: () => {
      return {
        keyword: '',
        acResult: {},
        result: {}
      }
    },
    methods: {
      search: function(){
        axios.get(baseUrl + "?keyword="+this.keyword)
          .then(response => {
            console.log(response.data.document)
            this.result = response.data.document
          })
          .catch(e => {
            console.log('error: ', e)
          })
      },
      searchAc: function(){
        axios.get(baseUrl + "/ac?keyword="+this.keyword)
          .then(response => {
            this.acResult = response.data.document
          })
          .catch(e => {
            console.log('error: ', e)
          })
      }
    }
  }
</script>
