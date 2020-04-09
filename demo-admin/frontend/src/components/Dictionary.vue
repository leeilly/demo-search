<template>
  <div class="dictionary">
    <b-card
      header="동의어 목록"
      style="max-width: 40rem; margin: auto; margin-top: 10vh;"
      class="mb-2"
      border-variant="info"
      align="left">

      <b-form-group id="to-do-input">
        <b-container fluid>
          <b-row class="my-1">
            <b-col sm="10">
              <b-form-input v-model="addSynonymRequest.synonym" type="text" />
            </b-col>
            <b-col sm="2">
              <b-button variant="outline-primary" v-on:click="addSynonym">추가</b-button>
            </b-col>
          </b-row>
        </b-container>
      </b-form-group>

      <b-list-group v-if="synonymList && synonymList.length">
        <b-list-group-item
          v-for="item of synonymList"
          v-bind:data="item.synonym"
          v-bind:key="item.synonymSeq">
          <b-form-tags
            input-id="tags-separators"
            v-model="item.synonym.split(',')"
            separator=","
            placeholder=""
            no-add-on-enter
            class="mb-2">
          </b-form-tags>
        </b-list-group-item>
      </b-list-group>
    </b-card>

  </div>
</template>

<script>
  import axios from 'axios'
  let baseUrl = 'http://localhost:8018/dic/synonym'

  export default {
    name: 'dictionary',
    data: () => {
      return {
        synonymList: []
        ,addSynonymRequest: {}
        ,value: ['apple', 'orange']
      }
    },
    methods: {
      initSynonymList: function(){
        let vm = this
        axios.get(baseUrl)
          .then(response => {
            this.synonymList = response.data
            console.log('init method call')
          })
          .catch(e => {
            console.log('error: ', e)
          })
      },
      addSynonym: function(event){
        event.preventDefault()
        let vm = this
        if (!vm.addSynonymRequest.synonym) return
        axios.post(baseUrl, vm.addSynonymRequest)
          .then(response => {
            console.log(response)
            vm.initSynonymList()
            vm.addSynonymRequest = {}
          })
          .catch(error => {
            console.log(error)
          })
      }
    },
    created (){
      this.initSynonymList();
    },
  }
</script>
