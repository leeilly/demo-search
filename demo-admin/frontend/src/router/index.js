import Vue from 'vue'
import Router from 'vue-router'
import Dictionary from '@/components/Dictionary'
import SearchDemo from '@/components/SearchDemo'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Dictionary',
      component: Dictionary
    },
    {
      path: '/search-demo',
      name: 'SearchDemo',
      component: SearchDemo
    }
  ]
})
