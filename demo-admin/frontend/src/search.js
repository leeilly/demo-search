import Vue from 'vue'
import Search from './components/Search.vue'

Vue.config.productionTip = false

new Vue({
    render: h => h(Search),
}).$mount('#search')
